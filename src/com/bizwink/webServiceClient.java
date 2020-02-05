package com.bizwink;

import com.bizwink.po.Users;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class webServiceClient {
    public static void main(String args[]) {
        int errcode = 0;
        List<Users> ulist = new ArrayList<Users>();
        int tempno = 0;
        String contactor = null;
        int samsiteid = 0;

        try {
            String url="http://localhost:81/webbuilder/services/NjlUserWebService";
            //String url="http://192.168.1.53/webbuilder/services/NjlUserWebService";
            Service serv = new Service();
            Call call = (Call)serv.createCall();

            QName qn = new QName("urn:NjlUserWebService","Users");
            call.registerTypeMapping(Users.class, qn, new BeanSerializerFactory(Users.class, qn), new BeanDeserializerFactory(Users.class, qn));
            call.setTargetEndpointAddress(new URL(url));
            call.setOperationName(new QName("NjlUserWebService", "registerNjlUser"));
            call.setReturnClass(ArrayList.class);
            call.addParameter("ulist", XMLType.XSD_ANYTYPE, ParameterMode.IN);
            call.addParameter("tempno", XMLType.XSD_ANYTYPE, ParameterMode.IN);
            call.addParameter("contactor",XMLType.XSD_ANYTYPE, ParameterMode.IN);
            call.addParameter("samsiteid", XMLType.XSD_ANYTYPE, ParameterMode.IN);
            List<String> list = (ArrayList)call.invoke(new Object[] {ulist,tempno,contactor,samsiteid});
            errcode = list.size();
        } catch(RemoteException e) {
            e.printStackTrace();
            errcode = -2;
        } catch(ServiceException exp1) {
            exp1.printStackTrace();
            errcode = -2;
        } catch (MalformedURLException exp2) {
            exp2.printStackTrace();
            errcode = -2;
        }
    }
}
