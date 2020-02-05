/**
 * Department.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.Department;

public interface Department extends java.rmi.Remote {
    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartID(String in0) throws java.rmi.RemoteException;
    public java.util.Collection getAllDepart() throws java.rmi.RemoteException;
    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartCode(String in0) throws java.rmi.RemoteException;
    public com.bjca.uums.client.bean.DepartmentInformation findDepartByDepartCodeForDC(String in0) throws java.rmi.RemoteException;
    public java.util.Collection getAllDepartForDC() throws java.rmi.RemoteException;
}
