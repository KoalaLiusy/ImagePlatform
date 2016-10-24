package test.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * socket�ͻ��� 
 */
public class PicSocketClient {
	
	public static void main(String[] args) throws IOException {
		Socket clientSocket = null;
		File picFile1 = new File("C:\\Users\\刘世玉\\Desktop\\传输图片\\tes01t.txt");
		File picFile2 = new File("C:\\Users\\刘世玉\\Desktop\\传输图片\\test02.txt");
		System.out.println(InetAddress.getLocalHost().toString());
		BufferedOutputStream bos =null;
		byte[] sendHeader = new byte[52];//52位发送报文头 
		try {
			clientSocket = new Socket();
			byte[] uuid = UUID.randomUUID().toString().getBytes();//36位的uuid唯一标识(24+12扩展)
			byte[] type = "5".getBytes();//1位处理类型，5表示上传
			byte[] fileNum = "2".getBytes();//3位的文件个数，  测试上传两个文件
			System.arraycopy(uuid, 0, sendHeader, 0, uuid.length);
			System.arraycopy(type, 0, sendHeader, 36, type.length);//36位处理类型开始
			System.out.println(new String(sendHeader,"utf-8"));
			System.arraycopy(fileNum, 0, sendHeader, 37, fileNum.length);//第三十七位文件个数开始
			System.out.println(new String(sendHeader,"utf-8"));
			
			clientSocket.connect(new InetSocketAddress("130.252.9.65",9001),6000);//��ʱ����
			bos = new BufferedOutputStream(clientSocket.getOutputStream());
			bos.write(sendHeader);//发送108位的报文头
			
			byte[] bagHeader1 = new byte[80];//文件头
			byte[] filnameArray1 = ("测试图片01.txt").getBytes();//48位的文件名
			byte[] fileLengthArray1 = (picFile1.length()+"").getBytes();//32位的文件长度
			System.arraycopy(filnameArray1, 0, bagHeader1, 0, filnameArray1.length);
			System.arraycopy(fileLengthArray1, 0, bagHeader1, 48, fileLengthArray1.length);
			bos.write(bagHeader1);
			
			FileInputStream fis = null; 
			fis = new FileInputStream(picFile1);
			int fileReaderLength = 0;
			byte[] fileData = new byte[4096];
			while((fileReaderLength = fis.read(fileData)) > 0){
				bos.write(fileData);
			}
			
			byte[] bagHeader2 = new byte[80];//文件头
			byte[] filnameArray2 = ("测试图片02.txt").getBytes();//48位的文件名
			byte[] fileLengthArray2 = (picFile2.length()+"").getBytes();//32位的文件长度
			System.arraycopy(filnameArray2, 0, bagHeader2, 0, filnameArray2.length);
			System.arraycopy(fileLengthArray2, 0, bagHeader2, 48, fileLengthArray2.length);
			bos.write(bagHeader2);
			FileInputStream fis2 = null; 
			fis2 = new FileInputStream(picFile2);
			int fileReaderLength2 = 0;
			byte[] fileData2 = new byte[4096];
			while((fileReaderLength2 = fis2.read(fileData2)) > 0){
				bos.write(fileData2);
			}
			bos.flush();
			
//			if(clientSocket.getInputStream()!=null){
//				byte[] returnResult = new byte[4098];
//				System.out.println(new String(returnResult,"utf-8"));
//			}
			if(fis != null){
				 fis.close();
			}
			if(fis2 != null){
				 fis2.close();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(bos != null){
				bos.close();
			}
		}
	}
}
