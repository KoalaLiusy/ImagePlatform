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
	private String ReservedValue1;//保留字段1
	private String ReservedValue2;//保留字段2
	private String ReservedValue3;//保留字段3
	private List<PicInfo> files;//每个文件信息
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
	public List<PicInfo> getFiles() {
		return files;
	}
	public void setFiles(List<PicInfo> files) {
		this.files = files;
	}
}
