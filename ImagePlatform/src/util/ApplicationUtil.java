package util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationUtil implements ApplicationContextAware{

	private static ApplicationContext ctx = null;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationUtil.ctx = applicationContext;
	} 
	
	public static <T> T getBean(Class<T> target,String name){
		return (T) ctx.getBean(name);
	}
	
	public static <T> T getBean(Class<T> target){
		return ctx.getBean(target);
	}
	
}
