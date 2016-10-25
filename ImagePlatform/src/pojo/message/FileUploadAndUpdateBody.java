package pojo.message;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import pojo.PicInfo;

/**
 * 上传与更新时报文体
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class FileUploadAndUpdateBody implements Serializable{
	private String expiryDate;//图片失效日期 2016-10-10格式
	private String base64Img;
	private String picType;
	private String reservedValue1;
	private String reservedValue2;
	private String reservedValue3;
	
	private List<PicInfo> files;//每个文件信息
	public String getReservedValue1() {
		return reservedValue1;
	}
	public void setReservedValue1(String reservedValue1) {
		this.reservedValue1 = reservedValue1;
	}
	public String getReservedValue2() {
		return reservedValue2;
	}
	public void setReservedValue2(String reservedValue2) {
		this.reservedValue2 = reservedValue2;
	}
	public String getReservedValue3() {
		return reservedValue3;
	}
	public void setReservedValue3(String reservedValue3) {
		this.reservedValue3 = reservedValue3;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public List<PicInfo> getFiles() {
		return files;
	}
	public void setFiles(List<PicInfo> files) {
		this.files = files;
	}
	public String getBase64Img() {
		return base64Img;
	}
	public void setBase64Img(String base64Img) {
		this.base64Img = base64Img;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
}
