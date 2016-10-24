package pojo;

public class BizInfo {
	private long id;
	private String bizId;// 业务id
	private String picId;// 24位业务ID
	private String appId;// 业务归属机构ID

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
