package test.client;

import javax.xml.namespace.QName;  
import org.apache.axis2.AxisFault;  
import org.apache.axis2.addressing.EndpointReference;  
import org.apache.axis2.client.Options;  
import org.apache.axis2.rpc.client.RPCServiceClient;  

public class WeatherRPCClient {  
  
    public static void main(String[] args1) throws AxisFault {  
        for(int i = 0;i<1;i++){
            EndpointReference targetEPR = new EndpointReference("http://localhost:8080/TestTransport/services/LessonAction");  
            RPCServiceClient serviceClient = new RPCServiceClient();  
            Options options = serviceClient.getOptions();  
           options.setTo(targetEPR);  
     
            QName opGetWeather = new QName("http://testAxis2.org/xsd", "testTx");  
           Object[] opGetWeatherArgs = new Object[1];  
           opGetWeatherArgs[0] = "{\"version\":\"1.00\"," +
				"\"header\":{\"id\":\"201610100234343434324574\",\"appId\":\"34343\",\"bizType\":\"jsd\"}," +
				"\"body\":{\"pageTotol\":\"30\",\"pageNum\":2,\"failInfo\":\"上传失败\"}}";
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
        }
    }  
}  

