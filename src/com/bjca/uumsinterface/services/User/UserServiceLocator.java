/**
 * UserServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.User;

public class UserServiceLocator extends org.apache.axis.client.Service implements UserService {

    // Use to get a proxy class for User
//    private final java.lang.String User_address = "http://192.168.2.249:7002/uumsinterface/services/User"; //weblogic8i
//    private final java.lang.String User_address = "http://192.168.2.249:7001/uumsinterface/services/User";//weblogic9i
//    private final java.lang.String User_address = "http://localhost:7001/uumsinterface/services/User";//weblogic9i  localhost
        private final String User_address = "http://localhost:9001/uumsinterface/services/User";//jboss3
//        private final java.lang.String User_address = "http://192.168.2.249:8080/uumsinterface/services/User";//jboss4
//        private final java.lang.String User_address = "http://192.168.2.249:9080/uumsinterface/services/User?wsdl";//websphere6.0
//    private final java.lang.String User_address = "http://localhost:7007/uumsinterface/services/User"; //weblogic8i---������
    public String getUserAddress() {
        return User_address;
    }

    // The WSDD service name defaults to the port name.
    private String UserWSDDServiceName = "User";

    public String getUserWSDDServiceName() {
        return UserWSDDServiceName;
    }

    public void setUserWSDDServiceName(String name) {
        UserWSDDServiceName = name;
    }

    public User getUser() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(User_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUser(endpoint);
    }

    public User getUser(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bjca.uumsinterface.services.User.UserSoapBindingStub _stub = new com.bjca.uumsinterface.services.User.UserSoapBindingStub(portAddress, this);
            _stub.setPortName(getUserWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (User.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bjca.uumsinterface.services.User.UserSoapBindingStub _stub = new com.bjca.uumsinterface.services.User.UserSoapBindingStub(new java.net.URL(User_address), this);
                _stub.setPortName(getUserWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("User".equals(inputPortName)) {
            return getUser();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "UserService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("User"));
        }
        return ports.iterator();
    }

}
