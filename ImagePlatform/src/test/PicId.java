package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class PicId {
	
	private volatile int seqId = 0;//启动时去数据库获取最新的起点
	/**
	 * 生成唯一PicId。考虑并发，唯一，索引查询效率
	 * @param args
	 */
	public static void main(String[] args) {
		final PicId sd = new PicId();
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				while(true){
					System.out.println("T1=="+sd.getTraceNo());
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				while(true){
					System.out.println("T2=="+sd.getTraceNo());
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t1.start();
		t2.start();
	}
	
	//获取TraceNo
	public synchronized String getTraceNo(){
//		String seq = String.valueOf(seqId);
//		if(seq.length() > 3){
//			seqId = 0;
//		}
		seqId++;
		if(seqId > 1000){
			seqId = 0;
		}
		return seqId + "";
	}
	
	class MyTask implements Runnable{
		public void run() {
			String header = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());//到毫秒值 
			System.out.println(header);//20160927145341112   4+2+2+2+2+2+3=17位
			seqId++;
			String seq = String.valueOf(seqId);
			if(seq.length() > 5){
				seqId = 0;
			}
			String picId = header + "0000000".substring(seq.length()) + seq;
			System.out.println(picId);
		}
	}
	
}
