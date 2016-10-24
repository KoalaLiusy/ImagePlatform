package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerializationUtil {

	/**
	 * 功能描述：把对象序列化成为字节数组
	 * 
	 * @param Object
	 *            可序列化对象
	 * @return byte[] 字节数组
	 * @throws IOException
	 */
	public static byte[] toBytes(Serializable obj) throws IOException {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
			oos.flush();
			return os.toByteArray();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 
	 * 功能描述：把字节数组反序列化成为对象
	 * 
	 * @param byte[] 字节数组
	 * @return Object 可序列化对象
	 * @throws IOException
	 * 
	 */
	public static <T> T toObject(byte[] bytes, Class<T> targetType)
			throws IOException {
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		return toObject(bis, targetType);
	}

	/**
	 * 
	 * 功能描述：把输入流反序列化成为对象
	 * 
	 * @param InputStream
	 *            输入流
	 * 
	 * @return Object 可序列化对象
	 * @throws IOException
	 * 
	 */
	public static <T> T toObject(InputStream is, Class<T> targetType)
			throws IOException {
		if (is == null) {
			return null;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			return (T) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("错误", e);
		} finally {
			if (ois != null)
				try {
					ois.close();
				} catch (Exception e) {
				}
		}
	}
}
