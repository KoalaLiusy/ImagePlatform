package pojo.message;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 请求报文基类
 * 忽略此类未知的属性
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestDataBase<RequestBody> implements Serializable{
	private String version;//版本信息
	private MessageHeader header;//消息头
	private RequestBody body;//请求报文体信息
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
	public RequestBody getBody() {
		return body;
	}
	public void setBody(RequestBody body) {
		this.body = body;
	}
	
	
}
