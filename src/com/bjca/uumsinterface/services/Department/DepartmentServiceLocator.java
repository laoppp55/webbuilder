/**
 * DepartmentServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.Department;

public class DepartmentServiceLocator extends org.apache.axis.client.Service implements DepartmentService {

    // Use to get a proxy class for Department
//    private final java.lang.String Department_address = "http://192.168.2.249:7002/uumsinterface/services/Department?wsdl"; //weblogic8i
//    private final java.lang.String Department_address = "http://192.168.2.249:7001/uumsinterface/services/Department";//weblogic9i
//    private final java.lang.String Department_address = "http://localhost:7001/uumsinterface/services/Department";//weblogic9i  localhost
        private final String Department_address = "http://localhost:9001/uumsinterface/services/Department";//jboss3
//        private final java.lang.String Department_address = "http://192.168.2.249:8080/uumsinterface/services/Department";//jboss4
//        private final java.lang.String Department_address = "http://192.168.2.249:9080/uumsinterface/services/Department?wsdl";//websphere6.0
//    private final java.lang.String Department_address = "http://localhost:7007/uumsinterface/services/Department"; //weblogic8i---������


    public String getDepartmentAddress() {
        return Department_address;
    }

    // The WSDD service name defaults to the port name.
    private String DepartmentWSDDServiceName = "Department";

    public String getDepartmentWSDDServiceName() {
        return DepartmentWSDDServiceName;
    }

    public void setDepartmentWSDDServiceName(String name) {
        DepartmentWSDDServiceName = name;
    }

    public Department getDepartment() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Department_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDepartment(endpoint);
    }

    public Department getDepartment(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub _stub = new com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub(portAddress, this);
            _stub.setPortName(getDepartmentWSDDServiceName());
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
            if (Department.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub _stub = new com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub(new java.net.URL(Department_address), this);
                _stub.setPortName(getDepartmentWSDDServiceName());
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
        if ("Department".equals(inputPortName)) {
            return getDepartment();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "DepartmentService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("Department"));
        }
        return ports.iterator();
    }

}
