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
}
