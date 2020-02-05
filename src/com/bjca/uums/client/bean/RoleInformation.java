/**
 * RoleInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class RoleInformation  implements java.io.Serializable {
    private String urFlowno;
    private String userRoleCode;
    private String userRoleDescribe;
    private String userRoleName;
    private String userRoleState;

    public RoleInformation() {
    }

    public String getUrFlowno() {
        return urFlowno;
    }

    public void setUrFlowno(String urFlowno) {
        this.urFlowno = urFlowno;
    }

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public String getUserRoleDescribe() {
        return userRoleDescribe;
    }

    public void setUserRoleDescribe(String userRoleDescribe) {
        this.userRoleDescribe = userRoleDescribe;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleState() {
        return userRoleState;
    }

    public void setUserRoleState(String userRoleState) {
        this.userRoleState = userRoleState;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RoleInformation)) return false;
        RoleInformation other = (RoleInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.urFlowno==null && other.getUrFlowno()==null) || 
             (this.urFlowno!=null &&
              this.urFlowno.equals(other.getUrFlowno()))) &&
            ((this.userRoleCode==null && other.getUserRoleCode()==null) || 
             (this.userRoleCode!=null &&
              this.userRoleCode.equals(other.getUserRoleCode()))) &&
            ((this.userRoleDescribe==null && other.getUserRoleDescribe()==null) || 
             (this.userRoleDescribe!=null &&
              this.userRoleDescribe.equals(other.getUserRoleDescribe()))) &&
            ((this.userRoleName==null && other.getUserRoleName()==null) || 
             (this.userRoleName!=null &&
              this.userRoleName.equals(other.getUserRoleName()))) &&
            ((this.userRoleState==null && other.getUserRoleState()==null) || 
             (this.userRoleState!=null &&
              this.userRoleState.equals(other.getUserRoleState())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUrFlowno() != null) {
            _hashCode += getUrFlowno().hashCode();
        }
        if (getUserRoleCode() != null) {
            _hashCode += getUserRoleCode().hashCode();
        }
        if (getUserRoleDescribe() != null) {
            _hashCode += getUserRoleDescribe().hashCode();
        }
        if (getUserRoleName() != null) {
            _hashCode += getUserRoleName().hashCode();
        }
        if (getUserRoleState() != null) {
            _hashCode += getUserRoleState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoleInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "RoleInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urFlowno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "urFlowno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userRoleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userRoleCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userRoleDescribe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userRoleDescribe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userRoleName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userRoleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userRoleState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userRoleState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
