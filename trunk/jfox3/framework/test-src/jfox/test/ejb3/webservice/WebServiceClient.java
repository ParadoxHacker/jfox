package jfox.test.ejb3.webservice;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class WebServiceClient {

    public static void main(String[] args) throws Exception{
        Service serviceModel = new ObjectServiceFactory().create(Calculator.class);
        String url = "http://localhost:8080/jfox3/webservice/Calculator";

        XFireProxyFactory factory = new XFireProxyFactory();
        Calculator example = (Calculator)factory.create(serviceModel, url);

        System.out.println("Soap invoke Calculator.add(1,1): " + example.add(1,1));
        System.out.println("Soap invoke Calculator.substract(2,1): " + example.subtract(2,1));
    }
}
