package pojo.message;

import java.util.List;

import pojo.PicInfo;

/**
 * 调阅报文体
 */
public class FileViewBody {
	private String bizId;//业务唯一Id
	private String picName;
	private String picType;
	private String expiryDate;//图片失效日期 2016-10-10格式
	private String ReservedValue1;//保留字段1
	private String ReservedValue2;//保留字段2
	private String ReservedValue3;//保留字段3
	private List<PicInfo> files;//返回的调阅图片的信息 
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getReservedValue1() {
		return ReservedValue1;
	}
	public void setReservedValue1(String reservedValue1) {
		ReservedValue1 = reservedValue1;
	}
	public String getReservedValue2() {
		return ReservedValue2;
	}
	public void setReservedValue2(String reservedValue2) {
		ReservedValue2 = reservedValue2;
	}
	public String getReservedValue3() {
		return ReservedValue3;
	}
	public void setReservedValue3(String reservedValue3) {
		ReservedValue3 = reservedValue3;
	}
	public List getFiles() {
		return files;
	}
	public void setFiles(List files) {
		this.files = files;
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
