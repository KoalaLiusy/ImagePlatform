package exception;

import org.apache.log4j.Logger;

import pojo.enums.BizType;
import pojo.enums.ExceptionType;
import util.PropertUtil;
import util.StringUtil;

public class PicBaseException extends RuntimeException{
	private ExceptionType type;
	private Logger log = Logger.getLogger(PicBaseException.class);
	
	public PicBaseException(ExceptionType type){
		super();
		this.type = type;
		log.error(PropertUtil.getErrorMessage(type));
	}
	
	public PicBaseException(ExceptionType type,Exception e){
		super(e);
		this.type = type;
		log.error(PropertUtil.getErrorMessage(type)+ "\n" + StringUtil.getExceptionStack(e));
	}
	
	public PicBaseException(String message){
		super(message);
		log.error(message);
	}
	
	public PicBaseException(ExceptionType type,String message,Throwable cause){
		super(message,cause);
		log.error(message);
	}
	
	public PicBaseException(String message,Throwable cause){
		super(message,cause);
		log.error(message);
	}

	public ExceptionType getType() {
		return type;
	}
	
	public String getBizMessage(){
		return PropertUtil.getErrorMessage(type);
	}

	public void setType(ExceptionType type) {
		this.type = type;
	}
}
