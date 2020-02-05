/**
 * UserSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.User;

import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;


public class UserSoapBindingStub extends org.apache.axis.client.Stub implements User {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[32];
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
        oper.setName("findDepartsByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findDepartsByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findSystemInfoBySystemId");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "SystemInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.SystemInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findSystemInfoBySystemIdReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLoginInformationByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "LoginInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.LoginInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getLoginInformationByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateUserpwByUserNameAndPwd");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        oper.setReturnClass(int.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "UpdateUserpwByUserNameAndPwdReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findAllSystemInfos");
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findAllSystemInfosReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findSystemInfosAccessedByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findSystemInfosAccessedByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findCustomContentInfoByCustomID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "CustomContentInfo"));
        oper.setReturnClass(com.bjca.uums.client.bean.CustomContentInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findCustomContentInfoByCustomIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfosBySystemCodeAndUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfosBySystemCodeAndUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findCredenceInfoByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserCredenceInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UserCredenceInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findCredenceInfoByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[9] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findCustomContentInfosBySystemCodeAndUserType");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findCustomContentInfosBySystemCodeAndUserTypeReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findSystemInfoBySystemCode");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "SystemInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.SystemInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findSystemInfoBySystemCodeReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuthorityAndSystemIDByUsernameAndPw");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getAuthorityAndSystemIDByUsernameAndPwReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findWholeUserInfosByUserIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UserInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findWholeUserInfosByUserIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUserInfosByUserSIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UserInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUserInfosByUserSIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUserInfosByUserIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UserInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUserInfosByUserIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUserInfosBySystemIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUserInfosBySystemIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUnitInfosByUserIDFroDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UnitInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UnitInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUnitInfosByUserIDFroDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findWholePersonInfosByUserIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "PersonInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.PersonInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findWholePersonInfosByUserIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findPersonInfosByUserIDForDC");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "PersonInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.PersonInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findPersonInfosByUserIDForDCReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[19] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuthorityByUserIDAndPw");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getAuthorityByUserIDAndPwReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfosByUserIDForStrType");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfosByUserIDForStrTypeReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfoByRoleId");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "RoleInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.RoleInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfoByRoleIdReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfoByRoleCode");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "RoleInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.RoleInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfoByRoleCodeReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUnitInfosByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UnitInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UnitInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUnitInfosByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findPersonInfosByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "PersonInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.PersonInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findPersonInfosByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateUserpw");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        oper.setReturnClass(int.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "UpdateUserpwReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuthorityByUsernameAndPw");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("", "in1"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getAuthorityByUsernameAndPwReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUserInfosBySystemID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUserInfosBySystemIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfosBySystemID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfosBySystemIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[29] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findRoleInfosByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        oper.setReturnClass(java.util.Collection.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findRoleInfosByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findUserInfosByUserID");
        oper.addParameter(new javax.xml.namespace.QName("", "in0"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserInformation"));
        oper.setReturnClass(com.bjca.uums.client.bean.UserInformation.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "findUserInfosByUserIDReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[31] = oper;

    }

    public UserSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public UserSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public UserSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.DepartmentInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "PersonInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.PersonInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "CustomContentInfo");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.CustomContentInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserCredenceInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.UserCredenceInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.UserInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UnitInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.UnitInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "LoginInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.LoginInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "RoleInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.RoleInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://util.java", "Collection");
            cachedSerQNames.add(qName);
            cls = java.util.Collection.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "SystemInformation");
            cachedSerQNames.add(qName);
            cls = com.bjca.uums.client.bean.SystemInformation.class;
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
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findDepartByDepartID"));

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

    public java.util.Collection findDepartsByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findDepartsByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public com.bjca.uums.client.bean.SystemInformation findSystemInfoBySystemId(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findSystemInfoBySystemId"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.SystemInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.SystemInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.SystemInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.LoginInformation getLoginInformationByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "getLoginInformationByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.LoginInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.LoginInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.LoginInformation.class);
            }
        }
    }

    public int updateUserpwByUserNameAndPwd(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "UpdateUserpwByUserNameAndPwd"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((Integer) _resp).intValue();
            } catch (Exception _exception) {
                return ((Integer) org.apache.axis.utils.JavaUtils.convert(_resp, int.class)).intValue();
            }
        }
    }

    public java.util.Collection findAllSystemInfos() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findAllSystemInfos"));

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

    public java.util.Collection findSystemInfosAccessedByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findSystemInfosAccessedByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public com.bjca.uums.client.bean.CustomContentInfo findCustomContentInfoByCustomID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findCustomContentInfoByCustomID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.CustomContentInfo) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.CustomContentInfo) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.CustomContentInfo.class);
            }
        }
    }

    public java.util.Collection findRoleInfosBySystemCodeAndUserID(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfosBySystemCodeAndUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

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

    public com.bjca.uums.client.bean.UserCredenceInformation findCredenceInfoByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findCredenceInfoByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UserCredenceInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UserCredenceInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UserCredenceInformation.class);
            }
        }
    }

    public java.util.Collection findCustomContentInfosBySystemCodeAndUserType(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findCustomContentInfosBySystemCodeAndUserType"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

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

    public com.bjca.uums.client.bean.SystemInformation findSystemInfoBySystemCode(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findSystemInfoBySystemCode"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.SystemInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.SystemInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.SystemInformation.class);
            }
        }
    }

    public String getAuthorityAndSystemIDByUsernameAndPw(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "getAuthorityAndSystemIDByUsernameAndPw"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (String) _resp;
            } catch (Exception _exception) {
                return (String) org.apache.axis.utils.JavaUtils.convert(_resp, String.class);
            }
        }
    }

    public com.bjca.uums.client.bean.UserInformation findWholeUserInfosByUserIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findWholeUserInfosByUserIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UserInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UserInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UserInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.UserInformation findUserInfosByUserSIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUserInfosByUserSIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UserInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UserInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UserInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.UserInformation findUserInfosByUserIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUserInfosByUserIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UserInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UserInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UserInformation.class);
            }
        }
    }

    public java.util.Collection findUserInfosBySystemIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUserInfosBySystemIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public com.bjca.uums.client.bean.UnitInformation findUnitInfosByUserIDFroDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUnitInfosByUserIDFroDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UnitInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UnitInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UnitInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.PersonInformation findWholePersonInfosByUserIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findWholePersonInfosByUserIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.PersonInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.PersonInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.PersonInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.PersonInformation findPersonInfosByUserIDForDC(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findPersonInfosByUserIDForDC"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.PersonInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.PersonInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.PersonInformation.class);
            }
        }
    }

    public String getAuthorityByUserIDAndPw(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "getAuthorityByUserIDAndPw"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (String) _resp;
            } catch (Exception _exception) {
                return (String) org.apache.axis.utils.JavaUtils.convert(_resp, String.class);
            }
        }
    }

    public String findRoleInfosByUserIDForStrType(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfosByUserIDForStrType"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (String) _resp;
            } catch (Exception _exception) {
                return (String) org.apache.axis.utils.JavaUtils.convert(_resp, String.class);
            }
        }
    }

    public com.bjca.uums.client.bean.RoleInformation findRoleInfoByRoleId(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfoByRoleId"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.RoleInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.RoleInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.RoleInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.RoleInformation findRoleInfoByRoleCode(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfoByRoleCode"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.RoleInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.RoleInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.RoleInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.UnitInformation findUnitInfosByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUnitInfosByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UnitInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UnitInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UnitInformation.class);
            }
        }
    }

    public com.bjca.uums.client.bean.PersonInformation findPersonInfosByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findPersonInfosByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.PersonInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.PersonInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.PersonInformation.class);
            }
        }
    }

    public int updateUserpw(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "UpdateUserpw"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((Integer) _resp).intValue();
            } catch (Exception _exception) {
                return ((Integer) org.apache.axis.utils.JavaUtils.convert(_resp, int.class)).intValue();
            }
        }
    }

    public String getAuthorityByUsernameAndPw(String in0, String in1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "getAuthorityByUsernameAndPw"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (String) _resp;
            } catch (Exception _exception) {
                return (String) org.apache.axis.utils.JavaUtils.convert(_resp, String.class);
            }
        }
    }

    public java.util.Collection findUserInfosBySystemID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUserInfosBySystemID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public java.util.Collection findRoleInfosBySystemID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfosBySystemID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public java.util.Collection findRoleInfosByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findRoleInfosByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

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

    public com.bjca.uums.client.bean.UserInformation findUserInfosByUserID(String in0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://192.168.2.249:7002/uumsinterface/services/User", "findUserInfosByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjca.uums.client.bean.UserInformation) _resp;
            } catch (Exception _exception) {
                return (com.bjca.uums.client.bean.UserInformation) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjca.uums.client.bean.UserInformation.class);
            }
        }
    }

}
