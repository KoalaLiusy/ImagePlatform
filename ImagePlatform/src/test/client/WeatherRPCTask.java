package test.client;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class WeatherRPCTask extends Thread{

	@Override
	public void run() {
		super.run();
        for(int i = 0;i<100;i++){
            EndpointReference targetEPR = new EndpointReference("http://localhost:8080/TestTransport/services/LessonAction");  
            RPCServiceClient serviceClient = null;
			try {
				serviceClient = new RPCServiceClient();
 
            Options options = serviceClient.getOptions();  
           options.setTo(targetEPR);  
     
            QName opGetWeather = new QName("http://testAxis2.org/xsd", "getNewPicId");  
           Object[] opGetWeatherArgs = new Object[] { };  
            Class[] returnTypes = new Class[] { String.class };
            Long l1 = System.currentTimeMillis();
            Object[] response = serviceClient.invokeBlocking(opGetWeather,opGetWeatherArgs, returnTypes);  
      
            String result = (String) response[0]; 
            Long l2 = System.currentTimeMillis();
            if (result == null) {  
                System.out.println("Weather didn't initialize!");  
            }else{  
               System.out.println(result+"耗时"+(l2-l1));  
            }
			} catch (AxisFault e) {
				e.printStackTrace();
			} 
        }
	}
	
}
