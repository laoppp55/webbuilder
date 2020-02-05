/**
 * DepartmentSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.Department;

import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;

public class DepartmentSoapBindingStub extends org.apache.axis.client.Stub implements Department {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findDepartByDepartID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.DepartmentInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findDepartByDepartIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllDepart");
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getAllDepartReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findDepartByDepartCode");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.DepartmentInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findDepartByDepartCodeReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findDepartByDepartCodeForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.DepartmentInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findDepartByDepartCodeForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllDepartForDC");
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getAllDepartForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[4] = oper;

    }

    public DepartmentSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public DepartmentSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public DepartmentSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            Class cls;
            javax.xml.namespace.QName qName;
            Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://util.java", "Collection");
            cachedSerQNames.add(qName);
            cls = java.util.Collection.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.DepartmentInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    private org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        Class sf = (Class)
                                 cachedSerFactories.get(i);
                        Class df = (Class)
                                 cachedDeserFactories.get(i);
                        _call.registerTypeMapping(cls, qName, sf, df, false);
                    }
                }
            }
            return _call;
        }
        catch (Throwable t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", t);
        }
    }

    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "findDepartByDepartID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.DepartmentInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.DepartmentInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.DepartmentInformation.class);
            }
        }
    }

    public java.util.Collection getAllDepart() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "getAllDepart"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.util.Collection) _resp;
            } catch (Exception _exception) {
                return (java.util.Collection) org.apache.axis.utils.JavaUtils.convert(_resp, java.util.Collection.class);
            }
        }
    }

    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartCode(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "findDepartByDepartCode"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.DepartmentInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.DepartmentInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.DepartmentInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartCodeForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "findDepartByDepartCodeForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.DepartmentInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.DepartmentInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.DepartmentInformation.class);
            }
        }
    }

    public java.util.Collection getAllDepartForDC() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://localhost:9001/uumsinterface/services/Department", "getAllDepartForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.util.Collection) _resp;
            } catch (Exception _exception) {
                return (java.util.Collection) org.apache.axis.utils.JavaUtils.convert(_resp, java.util.Collection.class);
            }
        }
    }

}
