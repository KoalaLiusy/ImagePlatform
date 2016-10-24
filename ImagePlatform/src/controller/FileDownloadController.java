package controller;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import exception.PicBaseException;

import pojo.PicInfo;
import pojo.message.FileViewBody;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;

import test.service.ViewAndDownloadService;
import util.FileUtil;
import util.JsonUtil;
import util.PropertUtil;
import util.SecretUtil;
import util.StringUtil;
import util.ZoomPicUtil;

@Controller
@RequestMapping("file")
public class FileDownloadController {
	@Autowired
	private ViewAndDownloadService service;
	
	private Logger log = Logger.getLogger(FileDownloadController.class);
	/**
	 * 下载缩略图及全图url
	 * @param bytes
	 * @return
	 */
	@RequestMapping("viewPic")
	public String viewPic(String requestMessage,HttpServletRequest request){
		StringBuilder failInfo = new StringBuilder("");
		ResponseDataBase<FileViewBody> response = new ResponseDataBase<FileViewBody>();
		RequestDataBase<FileViewBody> jsonObj = null;
		List<PicInfo> result = null;
		boolean success = false;
		try {
			jsonObj = JsonUtil.jsonToReferenceObject(requestMessage, 
							new TypeReference<RequestDataBase<FileViewBody>>(){});//映射请求报文到对象
			result = service.viewPic(jsonObj);
			success = true;
		} catch (IllegalStateException e) {
			failInfo.append("文件IO流被占用，请稍后再试！");
		}catch (PicBaseException e){
			failInfo.append(e.getBizMessage());
		}
		if(result == null || !(result.size() > 0)){
			success = false;
			failInfo.append("无符合条件的数据!");
		}else{
			String path = null;
			String baseUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() +
					request.getSession().getServletContext().getContextPath() + "/file/download.do?path=";
			for(PicInfo info:result){
				path = info.getPicPath();
				info.setUrl(baseUrl+SecretUtil.encode(path));
				info.setPicPath(null);
			}
		}
		FileViewBody body = null;
		if(jsonObj != null){
			response.setVersion(jsonObj.getVersion());
			response.setHeader(jsonObj.getHeader());
			body = jsonObj.getBody();
			body.setFiles(result);
		}else{
			body = new FileViewBody();
		}
		response.setSuccess(success);
		response.setFailInfo(failInfo.toString());
		response.setBody(body);
		
		String responseJson = JsonUtil.objectToJson(response);
		return responseJson;
	}
	
	/**
	 * 根据传入的path写入文件流
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("download")
	public void downloadPic(HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, IOException{
		String path = request.getParameter("path");
		String zoomPic = request.getParameter("zoomPic");
		StringBuilder failInfo = new StringBuilder("");
		if(path == null){
			failInfo.append("文件地址错误！");
		}else{
			path = SecretUtil.decode(path);
			if(StringUtil.isNotNull(zoomPic) && "true".equals(zoomPic)){//下载缩略图
				String zoomPath = FileUtil.getZoomPath(path);
				File zoomFile = new File(zoomPath);
				if(!zoomFile.exists()){
					ZoomPicUtil.zoomImageScale(new File(path), zoomPath, Integer.valueOf(PropertUtil.getCommonMessage("zoomPicWidth")));
				}
				path = zoomPath;
			}
			log.info("下载图片path=================" + path);
			try {
				service.download(path, request, response);
			} catch (IOException e) {
				failInfo.append("文件IO流异常！");
			} catch(PicBaseException e) {
				failInfo.append(e.getBizMessage());
			}
		}
	}
}
