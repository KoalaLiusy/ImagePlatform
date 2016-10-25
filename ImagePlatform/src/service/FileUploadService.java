package service;

import interf.mapper.PicInfoMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.UploadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import exception.PicBaseException;

import pojo.PicInfo;
import pojo.enums.BizType;
import pojo.enums.DateType;
import pojo.enums.ExceptionType;
import pojo.message.FileDeleteBody;
import pojo.message.FileUploadAndUpdateBody;
import pojo.message.RequestDataBase;

import sun.misc.BASE64Decoder;
import task.ZoomImageTask;
import util.PropertUtil;
import util.StringUtil;

/**
 * 处理文件上传服务，文件流与业务报文结合上传,文件更新删除等
 */
@Service
public class FileUploadService {
	@Autowired
	private PicInfoMapper mapper;
	@Autowired
	private ThreadPoolTaskExecutor pool;//线程池 
	private final String sep = File.separator;
	/**
	 * 上传单张图片附带业务报文
	 * @param request
	 * @param response
	 * @return
	 */
	public void uploadSinglePicWithJson(RequestDataBase<FileUploadAndUpdateBody> jsonObj,
			HttpServletRequest request) throws IllegalStateException,
			IOException {
		FileUploadAndUpdateBody body = jsonObj.getBody();
		PicInfo picInfo = uploadPic(request);
		picInfo.setPicId(jsonObj.getHeader().getPicId());//单张取报文头里的picId
		picInfo.setExpiryDate(body.getExpiryDate());
		picInfo.setReservedValue1(body.getReservedValue1());
		picInfo.setReservedValue2(body.getReservedValue2());
		picInfo.setReservedValue3(body.getReservedValue3());
		try {
			mapper.addFile(picInfo);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.STORE0005,e);
		}
		
	}
	
	/**
	 * 上传单张图片无报文
	 * 目前先做成单张
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public PicInfo uploadPic(HttpServletRequest request) throws IllegalStateException, IOException  
			 {
		PicInfo result = null;
		CommonsMultipartResolver multipartResolver = 
				new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile(iter.next());
				if(file != null){
					result = new PicInfo();
					String fileName = file.getOriginalFilename();
					//建父文件夹
					String filePath = mkPicDir() + sep + fileName;
					File localFile = new File(filePath);
					file.transferTo(localFile);
					result.setPicPath(localFile.getPath());
					result.setPicName(fileName);
					result.setPicType(fileName.substring(fileName.lastIndexOf(".")));
					pool.execute(new ZoomImageTask(filePath));//生成缩略图
				}
			}
		}
		return result;
	}
	
	/**
	 * 传入的是base64转码后的图片信息
	 */
	public void uploadByBase64(RequestDataBase<FileUploadAndUpdateBody> jsonObj){
		FileUploadAndUpdateBody body = jsonObj.getBody();
		String base64Img = body.getBase64Img();//base64转码的字符串
		String picType = body.getPicType();
		String picId = jsonObj.getHeader().getPicId();
		if(!StringUtil.isNotNull(base64Img)){
			throw new PicBaseException(ExceptionType.STORE0009);
		}
		BASE64Decoder decoder  = new BASE64Decoder();
		String filePath = null;
		String imgName = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base64Img);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
				}
			}
			// 生成jpeg图片
			if(!StringUtil.isNotNull(picType) || !StringUtil.isNotNull(picId)){
				throw new PicBaseException(ExceptionType.VALID0001);
			}
			imgName = picId + "." + picType;// 新生成的图片
			filePath = mkPicDir() + sep + imgName;
			OutputStream out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.STORE0010,e);
		}
		pool.execute(new ZoomImageTask(filePath));//生成缩略图
		PicInfo picInfo = new PicInfo();
		picInfo.setPicPath(filePath);
		picInfo.setPicType(picType);
		picInfo.setPicName(imgName);
		picInfo.setPicId(jsonObj.getHeader().getPicId());//单张取报文头里的picId
		picInfo.setExpiryDate(body.getExpiryDate());
		picInfo.setReservedValue1(body.getReservedValue1());
		picInfo.setReservedValue2(body.getReservedValue2());
		picInfo.setReservedValue3(body.getReservedValue3());
		try {
			mapper.addFile(picInfo);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.STORE0005,e);
		}
	}

	/**
	 * 文件更新，分重新上传文件与只改动参数的更新
	 */
	public void updatePic(RequestDataBase<FileUploadAndUpdateBody> jsonObj,
			HttpServletRequest request) throws IllegalStateException,
			IOException {
		FileUploadAndUpdateBody body = jsonObj.getBody();
		PicInfo picInfo = uploadPic(request);
		if(picInfo == null){//没有文件上传的更新
			picInfo = new PicInfo();
		}
		picInfo.setPicId(jsonObj.getHeader().getPicId());//单张取报文头里的picId
		picInfo.setExpiryDate(body.getExpiryDate());
		picInfo.setReservedValue1(body.getReservedValue1());
		picInfo.setReservedValue2(body.getReservedValue2());
		picInfo.setReservedValue3(body.getReservedValue3());
		int result = 0;
		try {
			result = mapper.updateByPicId(picInfo);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.STORE0007,e);
		}
		if(result == 0){
			throw new PicBaseException(ExceptionType.STORE0008);
		}
	}
	
	/**
	 * 文件删除
	 */
	public boolean deletePic(RequestDataBase<FileDeleteBody> jsonObj){
		String picId = jsonObj.getHeader().getPicId();
		PicInfo info = new PicInfo();
		info.setStatus("1");
		info.setPicId(picId);
		int result = mapper.updateByPicId(info);
		if(result == 1){
			return true;
		}
		return false;
	}
	/**
	 * 创建文件存储所需目录
	 */
	private String mkPicDir(){
		String basePath = PropertUtil.getCommonMessage("mkDirParent");//基路径
		DateType type = DateType.valueOf(PropertUtil.getCommonMessage("mkDirMode"));//创建文件夹方式
		Calendar now = Calendar.getInstance();//当前日期
		switch(type){
		case year:
			basePath = basePath + sep + now.get(Calendar.YEAR);
			break;
		case month:
			basePath = basePath + sep + now.get(Calendar.YEAR) + 
			sep + (now.get(Calendar.MONTH) + 1);
			break;
		case day:
			basePath = basePath + sep + now.get(Calendar.YEAR) + 
			sep + (now.get(Calendar.MONTH) + 1) +
			sep + now.get(Calendar.DAY_OF_MONTH);
			break;
		case hour:			
			basePath = basePath + sep + now.get(Calendar.YEAR) + 
				sep + (now.get(Calendar.MONTH) + 1) +
				sep + now.get(Calendar.DAY_OF_MONTH) + 
				sep + now.get(Calendar.HOUR_OF_DAY);
				break;
		}
		File file = new File(basePath);
		File zoomPicFile = new File(basePath + sep + PropertUtil.getCommonMessage("zoomPicBagName"));//缩略图文件夹
		if(!file.exists()){
			file.mkdirs();
		}
		if(!zoomPicFile.exists()){
			zoomPicFile.mkdirs();
		}
		return file.getPath();
	}
}
