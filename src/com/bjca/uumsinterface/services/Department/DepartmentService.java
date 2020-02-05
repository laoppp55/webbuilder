/**
 * DepartmentService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.Department;

public interface DepartmentService extends javax.xml.rpc.Service {
    public String getDepartmentAddress();

    public Department getDepartment() throws javax.xml.rpc.ServiceException;

    public Department getDepartment(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
