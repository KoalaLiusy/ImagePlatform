package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import interf.mapper.PicInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.PicBaseException;

import pojo.PicInfo;
import pojo.enums.ExceptionType;
import pojo.message.FileDeleteBody;
import pojo.message.FileViewBody;
import pojo.message.RequestDataBase;


/**
 * 处理文件的所有操作 
 */
@Service
public class ViewAndDownloadService{
	private static ResourceBundle resources = ResourceBundle.getBundle("config.properties.MimeMap");
	
	@Autowired
	private PicInfoMapper mapper;
	/**
	 * 调阅
	 * 返回此picId对应的图片url
	 */
	public List<PicInfo> viewPic(RequestDataBase<FileViewBody> jsonObj){
		PicInfo param = new PicInfo();
		FileViewBody body = jsonObj.getBody();
		param.setPicId(jsonObj.getHeader().getPicId());
		param.setPicName(body.getPicName());
		param.setPicType(body.getPicType());
		param.setExpiryDate(body.getExpiryDate());
		param.setReservedValue1(body.getReservedValue1());
		param.setReservedValue2(body.getReservedValue2());
		param.setReservedValue3(body.getReservedValue3());
		param.setStatus("0");
		return mapper.viewByCondition(param);
	}
	
	/**
	 * 下载图片
	 * @param path
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void download(String path,HttpServletRequest request,HttpServletResponse response) throws IOException{
		OutputStream out = null;
		byte[] byteData = null;
		String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
		response.setContentType(getMimeType(fileName));
		response.setHeader("Content-Disposition","attachment;fileName=" + fileName);
		out = response.getOutputStream();
		File file = new File(path);
		if(!file.exists()){
			throw new PicBaseException(ExceptionType.STORE0006);
		}
		FileInputStream fis = null; 
		fis = new FileInputStream(file);
		int fileReaderLength = 0;
		byte[] fileData = new byte[4096];
		while((fileReaderLength = fis.read(fileData)) > 0){
			out.write(fileData);
		}
		out.flush();
		if(fis != null){
			fis.close();
		}
		if(out != null){
			out.close();
		}
	}
	
	private String getMimeType(String fileName){
        String responseHeader = "application/octet-stream";
        int i = fileName.lastIndexOf(".");
        String extendFileName = fileName.substring(i+1);
        if(extendFileName.length()>0){
        	try{
        		if(resources==null){
        			resources = ResourceBundle.getBundle("MimeMap");
        		}
        		responseHeader = resources.getString(extendFileName.toLowerCase());
        	}catch(Exception e){
        		
           	}
        }
        return responseHeader;
    }
}
