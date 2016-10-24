package task;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import util.FileUtil;
import util.PropertUtil;
import util.ZoomPicUtil;

/**
 * 生成缩略图任务
 */
public class ZoomImageTask implements Runnable{
	private String originalPath;//原图路径
	private Logger log = Logger.getLogger(ZoomImageTask.class);
	
	public ZoomImageTask(String path){
		this.originalPath = path;
	}
	public void run() {
		File imageFile = new File(originalPath);
		String zoomFile = FileUtil.getZoomPath(originalPath);
		try {
			ZoomPicUtil.zoomImageScale(imageFile, zoomFile, Integer.valueOf(PropertUtil.getCommonMessage("zoomPicWidth")));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
