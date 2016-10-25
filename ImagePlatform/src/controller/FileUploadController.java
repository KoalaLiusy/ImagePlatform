package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.message.FileUploadAndUpdateBody;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;
import service.FileUploadService;
import util.JsonUtil;
import exception.PicBaseException;

@Controller
@RequestMapping("file")
public class FileUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	private Logger log = Logger.getLogger(FileUpdateController.class);
	/**
	 * 上传文件，使用springmvc提供api
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("upload")
	public @ResponseBody ResponseDataBase<FileUploadAndUpdateBody> uploadFile(String requestMessage,HttpServletRequest request){
		StringBuilder failInfo = new StringBuilder("");
		ResponseDataBase<FileUploadAndUpdateBody> response = new ResponseDataBase<FileUploadAndUpdateBody>();
		RequestDataBase<FileUploadAndUpdateBody> jsonObj = null;
		boolean success = false;
		try {
			jsonObj = 
					JsonUtil.jsonToReferenceObject(requestMessage, 
							new TypeReference<RequestDataBase<FileUploadAndUpdateBody>>(){});//映射请求报文到对象
			fileUploadService.uploadSinglePicWithJson(jsonObj,request);
			success = true;
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
			failInfo.append("文件IO流被占用，请稍后再试！");	
		} catch (IOException e) {
			log.error(e.getMessage());
			failInfo.append("文件IO异常！");
		} catch (PicBaseException e){
			failInfo.append(e.getBizMessage());
		}
		if(jsonObj != null){
			response.setBody(jsonObj.getBody());
		}
		if(jsonObj != null){
			response.setVersion(jsonObj.getVersion());
			response.setHeader(jsonObj.getHeader());
		}
		response.setSuccess(success);
		response.setFailInfo(failInfo.toString());
		return response;
	}
	
	@RequestMapping("upload2")
	public @ResponseBody ResponseDataBase<FileUploadAndUpdateBody> uploadFile(
			@RequestBody RequestDataBase<FileUploadAndUpdateBody> jsonObj,HttpServletRequest request){
		StringBuilder failInfo = new StringBuilder("");
		ResponseDataBase<FileUploadAndUpdateBody> response = new ResponseDataBase<FileUploadAndUpdateBody>();
		boolean success = false;
		try {
			fileUploadService.uploadSinglePicWithJson(jsonObj,request);
			success = true;
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
			failInfo.append("文件IO流被占用，请稍后再试！");	
		} catch (IOException e) {
			log.error(e.getMessage());
			failInfo.append("文件IO异常！");
		} catch (PicBaseException e){
			failInfo.append(e.getBizMessage());
		}
		if(jsonObj != null){
			response.setBody(jsonObj.getBody());
		}
		return JsonUtil.transportMessage(jsonObj,response,success,failInfo.toString());
	}	
	
	@RequestMapping("base64Upload")
	public @ResponseBody ResponseDataBase<FileUploadAndUpdateBody> uploadBase64File(
			@RequestBody RequestDataBase<FileUploadAndUpdateBody> jsonObj){
		StringBuilder failInfo = new StringBuilder("");
		ResponseDataBase<FileUploadAndUpdateBody> response = new ResponseDataBase<FileUploadAndUpdateBody>();
		boolean success = false;
		try {
			fileUploadService.uploadByBase64(jsonObj);
			success = true;
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
			failInfo.append("文件IO流被占用，请稍后再试！");	
		} catch (PicBaseException e){
			failInfo.append(e.getBizMessage());
		}
		if(jsonObj != null){
			response.setBody(jsonObj.getBody());
		}
		return JsonUtil.transportMessage(jsonObj,response,success,failInfo.toString());
	}
}
