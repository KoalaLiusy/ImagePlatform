package pojo.message;

public class AskPicIdBody {
	private String num;//请求id个数
	private String picId;//生成的id起始值
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
}
