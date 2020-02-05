/**
 * UserService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.User;

public interface UserService extends javax.xml.rpc.Service {
    public String getUserAddress();

    public User getUser() throws javax.xml.rpc.ServiceException;

    public User getUser(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
