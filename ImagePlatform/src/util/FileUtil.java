package util;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class FileUtil {
	
	/**
	 * 根据原图地址查找缩略图路径
	 * @return
	 */
	public static String getZoomPath(String originalPath){
		String zoomFile = StringUtils.substringBeforeLast(originalPath, File.separator) + 
				File.separator + PropertUtil.getCommonMessage("zoomPicBagName") + 
				File.separator + StringUtils.substringAfterLast(originalPath, File.separator);
		return zoomFile;
	}
	
	/**
	 * 根据缩略图查找原图
	 * @return
	 */
	public static String getOriginalPath(String zoomPath){
		String zoomPicBagName = PropertUtil.getCommonMessage("zoomPicBagName");
		return StringUtils.substringBeforeLast(zoomPath,zoomPicBagName) + 
				StringUtils.substringAfterLast(zoomPath,File.separator);
	}
}
