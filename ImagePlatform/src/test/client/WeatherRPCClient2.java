package test.client;

import javax.xml.namespace.QName;  
import org.apache.axis2.AxisFault;  
import org.apache.axis2.addressing.EndpointReference;  
import org.apache.axis2.client.Options;  
import org.apache.axis2.rpc.client.RPCServiceClient;  

public class WeatherRPCClient2 {  
  
    public static void main(String[] args1) throws AxisFault {  
    	WeatherRPCTask t1 = new WeatherRPCTask();
    	WeatherRPCTask t2 = new WeatherRPCTask();
    	WeatherRPCTask t3 = new WeatherRPCTask();
    	t1.start();
    	t2.start();
    	t3.start();
    }  
}  

