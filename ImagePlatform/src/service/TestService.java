package service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ApplicationContextAware{

	@Autowired
	private ViewAndDownloadService service;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if(service == null){
			System.out.println("service wei null");
		}
		//applicationContext.getBean("fileOperatorService");
	}
	
	
}
