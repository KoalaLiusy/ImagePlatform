package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import exception.PicBaseException;
import pojo.message.AskPicIdBody;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;
import test.service.PicIdService;
import util.JsonUtil;
import util.StringUtil;

@Controller
public class PicIdController {
	@Autowired
	private PicIdService service;
	private Logger log = Logger.getLogger(PicIdController.class);
	
	@RequestMapping("getPicId")
	public String getStartPicID(@RequestBody RequestDataBase<AskPicIdBody> jsonObj2,HttpServletRequest request){
		boolean success = false;
		String requestMessage="";
		StringBuilder failInfo = new StringBuilder("");
		RequestDataBase<AskPicIdBody> jsonObj = null;
		String startPicId = "";
		try {
			jsonObj = JsonUtil.jsonToReferenceObject(requestMessage, 
							new TypeReference<RequestDataBase<AskPicIdBody>>(){});
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
		
		String str = JsonUtil.transportMessage(jsonObj,response,success,failInfo.toString());
		Map resultMap = new HashMap();
		resultMap.put("responseMessage", str);
		return str;
	}
}
