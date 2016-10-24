package pojo;

import java.io.Serializable;

public class PicInfo implements Serializable{
	private Long id;
	private String picId;
	private String picName;
	private String picType;
	private String status;
	private String picPath;
	private String expiryDate;
	private String reservedValue1;
	private String reservedValue2;
	private String reservedValue3;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
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
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
}
