package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import pojo.enums.ExceptionType;

public class PropertUtil {
	private static Properties errorMessageProp = null;
	private static Properties commonProp = null;
	public static String getErrorMessage(ExceptionType type){
		if(errorMessageProp == null){
			loadErrorMessageProp("config/properties/errorMessage_zh_CN.properties");
		}
		return errorMessageProp.getProperty(type.name());
	}
	
	public static String getCommonMessage(String key){
		if(commonProp == null){
			loadCommonProp("config/properties/common.properties");
		}
		return commonProp.getProperty(key);
	}
	
	/**
	 * 加载propertis文件
	 */
	public static void loadErrorMessageProp(String path){
		errorMessageProp = new Properties();
		synchronized (errorMessageProp) {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			try {
				errorMessageProp.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadCommonProp(String path){
		commonProp = new Properties();
		synchronized (commonProp) {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			try {
				commonProp.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		loadErrorMessageProp("config/properties/errorMessage_en_US.properties");
		System.out.println(errorMessageProp.getProperty("sdf"));
		System.out.println(new String(errorMessageProp.getProperty("sdf").getBytes("ISO-8859-1"),"gbk"));
	}
}
