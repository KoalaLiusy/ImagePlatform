package pojo.message;

import java.util.List;

import pojo.PicInfo;

/**
 * 调阅报文体
 */
public class FileViewBody {
	private String picName;
	private String picType;
	private String expiryDate;//图片失效日期 2016-10-10格式
	private String reservedValue1;
	private String reservedValue2;
	private String reservedValue3;
	
	private List<PicInfo> files;//返回的调阅图片的信息 
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
	public void setFiles(List<PicInfo> files) {
		this.files = files;
	}

	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public List getFiles() {
		return files;
	}

	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
}
