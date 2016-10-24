package pojo.message;

import java.io.Serializable;

public class MessageHeader implements Serializable {
	private String bizId;// 业务Id
	private String picId;// 图片所属业务唯一标识id
	private String appId;// 业务归属id
	private String bizType;// 请求业务类型

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}
	
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
}
