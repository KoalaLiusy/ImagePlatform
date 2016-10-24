package util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import pojo.enums.ExceptionType;
import pojo.message.RequestDataBase;
import pojo.message.ResponseDataBase;
import exception.PicBaseException;

/**
 * json操作工具类
 */
public final class JsonUtil {
	public static ObjectMapper objectMapper;

	/**
	 * json字符串转化为对象
	 * 
	 * @param jsonStr
	 * @param targetType
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> targetType) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.readValue(jsonStr, targetType);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.VALID0001,e);
		}
	}

	/**
	 * json数组转换为泛型复杂对象
	 * list new TypeReference<List<UserBean>>
	 * @param jsonStr
	 * @param valueTypeRef
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T jsonToReferenceObject(String jsonStr, TypeReference<T> valueTypeRef) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		if(jsonStr == null || "".equals(jsonStr)){
			throw new PicBaseException(ExceptionType.VALID0002);
		}
		try {
			return objectMapper.readValue(jsonStr, valueTypeRef);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.VALID0001,e);
		}
	}

	/**
	 * json转Map
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map jsonToMap(String jsonStr) {
		return jsonToObject(jsonStr, Map.class);
	}

	/**
	 * | 对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String objectToJson(Object obj) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new PicBaseException(ExceptionType.VALID0001,e);
		}
	}
	
	/**
	 * 构建报文
	 * @param <A>
	 * @return
	 */
	public static <T, A> String transportMessage(RequestDataBase<T> request,ResponseDataBase<A> response,
			boolean success,String failInfo){
		if(request != null){
			response.setVersion(request.getVersion());
			response.setHeader(request.getHeader());
		}
		response.setSuccess(success);
		response.setFailInfo(failInfo);
		return objectToJson(response);
	}
}
