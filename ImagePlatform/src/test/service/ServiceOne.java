package test.service;

import interf.mapper.FileInfoMapper;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import pojo.FileInfo;

public class ServiceOne implements ApplicationContextAware {
	Logger log = Logger.getLogger(ServiceOne.class);
	private FileInfoMapper mapper;

	public void setMapper(FileInfoMapper mapper) {
		this.mapper = mapper;
	}

	public String actionOne() {
		log.info("sadasdasdasdasd");
		if (mapper != null) {
			System.out.println("excute actionOne");
			FileInfo info = new FileInfo();
			info.setFileName("第二张图片");
			info.setFilePath("download/sd/ds.png");
			mapper.insertFile(info);
		}
		return "actionOne";
	}

	public String actionTwo() {
		System.out.println("excute actionTwo");
		return "actionTwo";
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String, FileInfoMapper> map = applicationContext
				.getBeansOfType(FileInfoMapper.class);
		if (map.isEmpty()) {
			System.out
					.println("=======================================================");
		}
		System.out.println(applicationContext.toString());
	}
}
