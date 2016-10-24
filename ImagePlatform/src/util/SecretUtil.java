package util;

import java.nio.charset.Charset;



/**
 * 简单加密算法，用于加密返回给界面的下载图片路径
 */
public class SecretUtil {
	private static final String key0 = "a";//秘钥  
    private static final Charset charset = Charset.forName("UTF-8");  
    private static byte[] keyBytes = key0.getBytes(charset);
    
    public static String encode(String enc){  
        byte[] b = enc.getBytes(charset);  
        for(int i=0,size=b.length;i<size;i++){  
            for(byte keyBytes0:keyBytes){  
                b[i] = (byte) (b[i]^3);  
            }  
        }  
        return new String(b);  
    }  
      
    public static String decode(String dec){  
        byte[] e = dec.getBytes(charset);  
        byte[] dee = e;  
        for(int i=0,size=e.length;i<size;i++){  
            for(byte keyBytes0:keyBytes){  
                e[i] = (byte) (dee[i]^3);  
            }  
        }  
        return new String(e);  
    }
    /**
     * Base64加密
     * @param key
     * @return
     * @throws Exception
     */
//    public static byte[] decryptBASE(String key) throws Exception { 
//    		return (new BASE64Decoder()).decodeBuffer(key); 
//    }
//    
//    public static String encryptBASE(byte[] key) throws Exception { 
//    	return (new BASE64Encoder()).encodeBuffer(key); 
//    } 
    
    public static void main(String[] args) {
    	String in = "d:/2015/23/23/sdsad324234234.png";
    	String secret = encode(in);
    	System.out.println(secret);
    	System.out.println(decode(secret));
	}
}
