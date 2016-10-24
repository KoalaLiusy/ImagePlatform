package messageReceived.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class FileReceivedServer {
	private ThreadPoolTaskExecutor pool;
	
	private Logger log = Logger.getLogger(FileReceivedServer.class);
	
	
	public void setPool(ThreadPoolTaskExecutor pool) {
		this.pool = pool;
	}

	private ServerSocket serverSocket;//socket服务
	
	//启动入口
	public boolean init() throws IOException{
		serverSocket = new ServerSocket(9001,10000);//最大连接数10000，超过拒绝
		Thread listener = new Thread(new SocketListener());
		listener.setDaemon(true);
		listener.start();
		return false;
	}
	
	class SocketListener implements Runnable{

		public void run() {
			while(true){
				Socket socket = null;
				try {
					socket = serverSocket.accept()	;
				} catch (IOException e) {
					e.printStackTrace();
				}
				pool.execute(new StoreFileTask(socket));
			}
		}
	}
	
	/**
	 * 解析byte
	 */
	class StoreFileTask implements Runnable{
		private Socket socket;
		public StoreFileTask(Socket socket){
			this.socket = socket;
		}
		public void run() {
			log.debug("=============" + "客户端地址" + socket.getInetAddress().toString() + "==========================");
			DataInputStream in = null;
			DataOutputStream out = null;
			FileOutputStream fileOutputStream = null;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				byte[] bytes = new byte[52];//读取52位报文头
				byte[] picId = new byte[24];//24位picId
				byte[] flag = new byte[1];//1位的处理类型
				byte[] picNum = new byte[3];//3位的文件个数
				in.read(bytes);
				System.arraycopy(bytes, 0, picId, 0, picId.length);//picId前24个字节
				System.arraycopy(bytes, 36, flag, 0, flag.length);//第36位
				System.arraycopy(bytes, 37, picNum, 0, picNum.length);//第37位
				log.debug("+++++++++++++++" + "报文头开始" + "++++++++++++++");
				log.debug("+++++++++++++++" + new String(bytes,"utf-8") + "++++++++++++++");
				log.debug("+++++++++++++++" + "报文头结束" + "++++++++++++++");
				
				//上传文件  requestType=5
				String requestType = new String(flag,"utf-8");
				if("5".equals(requestType)){
					int num = Integer.valueOf(new String(picNum).trim());//几个文件，循环几次写文件
					for(int i = 0;i<num;i++){
						byte[] filePackage = new byte[4096];//文件数据包大小
						byte[] fileheaderPackage = new byte[80];//80位文件头信息
						in.read(fileheaderPackage);//一个文件的头信息
						byte[] picNameArray = new byte[48];//48位的文件名
						byte[] picLengthArray = new byte[32];//32位的文件长度
						System.arraycopy(fileheaderPackage, 0, picNameArray, 0, picNameArray.length);//picName前48个字节
						System.arraycopy(fileheaderPackage, 48, picLengthArray, 0, picLengthArray.length);//第48-80位
						String picName = new String(picNameArray,"utf-8").trim();
						File reciewFile = new File("E:\\uploadPic\\" + picName);
						fileOutputStream = new FileOutputStream(reciewFile);
						Long picLength = Long.valueOf(new String(picLengthArray,"utf-8").trim());
						log.debug("第" + i+1 + "个文件名称：" + picName + "长度：" + picLength);
						Long digit = picLength % (filePackage.length);//求几个包
						int bag =  (int) ((digit == 0) ? picLength/filePackage.length:picLength/filePackage.length + 1);
						for(int packet = 0;packet < bag;packet++){//循环读这一个文件的几个包
							in.read(filePackage);
							fileOutputStream.write(filePackage);
						}
						if(fileOutputStream != null){
							try {
								fileOutputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					System.arraycopy("0".getBytes(), 0, bytes, 40, 1);//加入成功标志0
					out.write(bytes);
					out.flush();//上传返回报文
				}
				//下载文件
				if("4".equals(requestType)){
//					检索传入picId下所有图片，按序号排序
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
