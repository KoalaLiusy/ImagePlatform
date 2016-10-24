package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import exception.PicBaseException;

import pojo.message.FileDeleteBody;
import pojo.message.FileUploadAndUpdateBody;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;

import test.service.FileUploadService;
import util.JsonUtil;

@Controller
@RequestMapping("file")
public class FileUpdateController {
	@Autowired
	private FileUploadService service;
	private Logger log =Logger.getLogger(FileUpdateController.class);
	
	@RequestMapping("delete")
	public String delete(String requestMessage){
		RequestDataBase<FileDeleteBody> jsonObj = null;
		ResponseDataBase<FileDeleteBody> response = new ResponseDataBase<FileDeleteBody>();
		boolean success = false;
		StringBuilder failInfo = new StringBuilder("");
		try {
			jsonObj = JsonUtil.jsonToReferenceObject(requestMessage, 
							new TypeReference<RequestDataBase<FileDeleteBody>>(){});//映射请求报文到对象
			success = service.deletePic(jsonObj);
		} catch (PicBaseException e){
			failInfo.append(e.getBizMessage());
		}
		if(jsonObj != null){
			response.setBody(jsonObj.getBody());
		}
		return JsonUtil.transportMessage(jsonObj,response,success,failInfo.toString());
	}
	
	@RequestMapping("update")
	public String update(String requestMessage,HttpServletRequest request){
		RequestDataBase<FileUploadAndUpdateBody> jsonObj = null;
		boolean success = false;
		StringBuilder failInfo = new StringBuilder("");
		ResponseDataBase<FileUploadAndUpdateBody> response = new ResponseDataBase<FileUploadAndUpdateBody>();
		try {
			jsonObj = JsonUtil.jsonToReferenceObject(requestMessage, 
					new TypeReference<RequestDataBase<FileUploadAndUpdateBody>>(){});
			service.updatePic(jsonObj, request);
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
