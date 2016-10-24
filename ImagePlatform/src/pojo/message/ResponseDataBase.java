package pojo.message;

import java.io.Serializable;


/**
 * 返回报文基类
 */

public class ResponseDataBase<ResponseBody> implements Serializable{
	private String version;//版本信息
	private MessageHeader header;//消息头
	private ResponseBody body;//返回报文体信息
	private boolean success;//成功标志
	private String failInfo;//错误信息
	
	public String getFailInfo() {
		return failInfo;
	}
	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public MessageHeader getHeader() {
		return header;
	}
	public void setHeader(MessageHeader header) {
		this.header = header;
	}
	public ResponseBody getBody() {
		return body;
	}
	public void setBody(ResponseBody body) {
		this.body = body;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
