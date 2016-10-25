package controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.message.AskPicIdBody;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;
import service.PicIdService;
import util.JsonUtil;
import util.StringUtil;
import exception.PicBaseException;

@Controller
public class PicIdController {
	@Autowired
	private PicIdService service;
	private Logger log = Logger.getLogger(PicIdController.class);
	
	@RequestMapping("getPicId")
	public @ResponseBody ResponseDataBase<AskPicIdBody> getStartPicID(@RequestBody RequestDataBase<AskPicIdBody> jsonObj,HttpServletRequest request){
		boolean success = false;
		StringBuilder failInfo = new StringBuilder("");
		String startPicId = "";
		try {
			startPicId = service.getNewPicId(jsonObj);
			success = true;
		} catch (PicBaseException e) {
			failInfo.append(e.getBizMessage());
		} catch (Exception e){
			log.error(StringUtil.getExceptionStack(e));
			failInfo.append("未知异常");
		}
		AskPicIdBody body = null;
		if(jsonObj != null){
			body = jsonObj.getBody();
			body.setPicId(startPicId);
		}
		ResponseDataBase<AskPicIdBody> response = new ResponseDataBase<AskPicIdBody>();
		response.setBody(body);
		
		response = JsonUtil.transportMessage(jsonObj,response,success,failInfo.toString());
		return response;
	}
}
