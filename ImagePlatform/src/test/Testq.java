package test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;

import pojo.enums.BizType;
import pojo.message.AskPicIdBody;
import pojo.message.FileUploadAndUpdateBody;
import pojo.message.RequestDataBase;

import test.service.TestRep;
import util.ApplicationUtil;
import util.JsonUtil;
import util.ZoomPicUtil;

public class Testq {
	public static void main(String[] args) throws SQLException, IOException {
//		test1();
//		test2();
//		test3();
//		test4();
//		test5(); 
//		test6();
//		for(int i=0;i<20;i++){
//			Thread t1 = new Thread(new Task("C:\\Users\\刘世玉\\Desktop\\传输图片\\zoom"+ i + "\\"));
//			t1.start();
//		}
		test7();
	}
	
	private static void test1(){
		String json = "{\"version\":\"1.00\"," +
				"\"header\":{\"id\":\"201610100234343434324578\",\"appId\":\"34343\",\"bizType\":\"jsd\"}," +
				"\"body\":{\"pageTotol\":\"30\",\"pageNum\":2,\"failInfo\":\"上传失败\"}}";
		RequestDataBase<FileUploadAndUpdateBody> map = 
				JsonUtil.jsonToReferenceObject(json, new TypeReference<RequestDataBase<FileUploadAndUpdateBody>>() {
				});
		FileUploadAndUpdateBody body = map.getBody();
		System.out.println();
		RequestDataBase<String> sd = new RequestDataBase<String>();
	}
	
	private static void test2(){
		String str = "ADDFILE";
		if(str.equals(BizType.addFile.name())){
			System.out.println("========");
		}
		if("addFile".equals(BizType.addFile.name())){
			System.out.println("========2");
		}
		BizType t = BizType.valueOf("addFile");
		System.out.println(t);
		switch(t){
		case addFile:
			System.out.println("addFile1111"); break;
		case showView:System.out.println("showView1111"); break;
		}
	}
	
	private static void test3(){
		File file = new File("C:\\Users\\刘世玉\\Desktop\\传输图片");
		final List<String> list = new ArrayList<String>();
		String[] str = file.list(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				System.out.println(dir.getPath());
				String sffix = name.substring(name.lastIndexOf("."));
				System.out.println(sffix);
				return false;
			}
		});
	}
	
	private static void test4(){
		String name = "201610100234343434324579_23.jpg";
		String seq = name.substring(name.lastIndexOf("_")+1,
				name.lastIndexOf("."));
		System.out.println(seq);
	}
	
	private static void test5() throws SQLException{
//		String jsonStr = "{\"version\":\"1.00\"," +
//				"\"header\":{\"id\":\"201610100234343434324575\",\"appId\":\"34343\",\"bizType\":\"jsd\"}," +
//				"\"body\":{\"pageTotol\":\"30\",\"pageNum\":2,\"failInfo\":\"上传失败\"}}";
//		TestRep rep = ApplicationUtil.getBean(TestRep.class);
//		rep.test(jsonStr);
		String path = "D:\\upload\\2016\\10\\14\\01.jpg";
		String fileName = path.substring(path.lastIndexOf(File.separator)+1);
		System.out.println(fileName);
	}
	
	private static void test6() throws IOException{
		File parentFile = new File("C:\\Users\\刘世玉\\Desktop\\传输图片");
		File[] list = parentFile.listFiles();
		Long l1 = System.currentTimeMillis();
		for(int i = 0;i<list.length;i++){
			File sun = list[i];
			if(sun.getName().endsWith("jpg") || sun.getName().endsWith("png") || sun.getName().endsWith("gif")){
				ZoomPicUtil.zoomImageScale(sun, "C:\\Users\\刘世玉\\Desktop\\传输图片\\zoom\\" + sun.getName(), 200);
			}
		}
		Long l2 = System.currentTimeMillis();
		System.out.println("==="+(l2-l1));
//		File pic = new File("C:\\Users\\刘世玉\\Desktop\\传输图片\\e1a754846a75172db62c9354bb88e12e.jpg");
//		ZoomPicUtil.zoomImageScale(pic, "C:\\Users\\刘世玉\\Desktop\\传输图片\\zoom3.gif", 200);
//		String originalPath = "D:\\upload\\2016\\10\\14\\01.jpg";
//		String zoomFile = StringUtils.substringAfterLast(originalPath, File.separator);
//		System.out.println(zoomFile);
	}
	
	private static void test7() throws IOException{
//		String str = JsonUtil.transportMessage(jsonObj);
//		System.out.println(str);
		String picId = "201610100234343434324571";
		BigDecimal b1 = new BigDecimal(picId);
		System.out.println(b1.add(new BigDecimal(2-1)).toString());
	}

}
class Task implements Runnable{
	private String newPath;
	public Task(String newPath){
		this.newPath = newPath;
	}
	public void run() {
		File parentFile = new File("C:\\Users\\刘世玉\\Desktop\\传输图片");
		File[] list = parentFile.listFiles();
		Long l1 = System.currentTimeMillis();
		File f1 = new File(newPath);
		if(!f1.exists()){
			f1.mkdirs();
		}
		for(int i = 0;i<list.length;i++){
			File sun = list[i];
			if(sun.getName().endsWith("jpg") || sun.getName().endsWith("png") || sun.getName().endsWith("gif")){
				try {
					ZoomPicUtil.zoomImageScale(sun, newPath + sun.getName(), 200);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Long l2 = System.currentTimeMillis();
		System.out.println("==="+(l2-l1));
	}
	
}
