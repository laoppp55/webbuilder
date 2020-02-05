/**
 * UserCredenceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class UserCredenceInformation  implements java.io.Serializable {
    private String credenceAppend;
    private String credenceCert;
    private String credenceClass;
    private String credenceDefault1;
    private String credenceDescribe;
    private String credenceState;
    private String credenceUniqueid;
    private String loginFailNum;
    private String uniqueid;

    public UserCredenceInformation() {
    }

    public String getCredenceAppend() {
        return credenceAppend;
    }

    public void setCredenceAppend(String credenceAppend) {
        this.credenceAppend = credenceAppend;
    }

    public String getCredenceCert() {
        return credenceCert;
    }

    public void setCredenceCert(String credenceCert) {
        this.credenceCert = credenceCert;
    }

    public String getCredenceClass() {
        return credenceClass;
    }

    public void setCredenceClass(String credenceClass) {
        this.credenceClass = credenceClass;
    }

    public String getCredenceDefault1() {
        return credenceDefault1;
    }

    public void setCredenceDefault1(String credenceDefault1) {
        this.credenceDefault1 = credenceDefault1;
    }

    public String getCredenceDescribe() {
        return credenceDescribe;
    }

    public void setCredenceDescribe(String credenceDescribe) {
        this.credenceDescribe = credenceDescribe;
    }

    public String getCredenceState() {
        return credenceState;
    }

    public void setCredenceState(String credenceState) {
        this.credenceState = credenceState;
    }

    public String getCredenceUniqueid() {
        return credenceUniqueid;
    }

    public void setCredenceUniqueid(String credenceUniqueid) {
        this.credenceUniqueid = credenceUniqueid;
    }

    public String getLoginFailNum() {
        return loginFailNum;
    }

    public void setLoginFailNum(String loginFailNum) {
        this.loginFailNum = loginFailNum;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UserCredenceInformation)) return false;
        UserCredenceInformation other = (UserCredenceInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.credenceAppend==null && other.getCredenceAppend()==null) || 
             (this.credenceAppend!=null &&
              this.credenceAppend.equals(other.getCredenceAppend()))) &&
            ((this.credenceCert==null && other.getCredenceCert()==null) || 
             (this.credenceCert!=null &&
              this.credenceCert.equals(other.getCredenceCert()))) &&
            ((this.credenceClass==null && other.getCredenceClass()==null) || 
             (this.credenceClass!=null &&
              this.credenceClass.equals(other.getCredenceClass()))) &&
            ((this.credenceDefault1==null && other.getCredenceDefault1()==null) || 
             (this.credenceDefault1!=null &&
              this.credenceDefault1.equals(other.getCredenceDefault1()))) &&
            ((this.credenceDescribe==null && other.getCredenceDescribe()==null) || 
             (this.credenceDescribe!=null &&
              this.credenceDescribe.equals(other.getCredenceDescribe()))) &&
            ((this.credenceState==null && other.getCredenceState()==null) || 
             (this.credenceState!=null &&
              this.credenceState.equals(other.getCredenceState()))) &&
            ((this.credenceUniqueid==null && other.getCredenceUniqueid()==null) || 
             (this.credenceUniqueid!=null &&
              this.credenceUniqueid.equals(other.getCredenceUniqueid()))) &&
            ((this.loginFailNum==null && other.getLoginFailNum()==null) || 
             (this.loginFailNum!=null &&
              this.loginFailNum.equals(other.getLoginFailNum()))) &&
            ((this.uniqueid==null && other.getUniqueid()==null) || 
             (this.uniqueid!=null &&
              this.uniqueid.equals(other.getUniqueid())));
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
        if (getCredenceAppend() != null) {
            _hashCode += getCredenceAppend().hashCode();
        }
        if (getCredenceCert() != null) {
            _hashCode += getCredenceCert().hashCode();
        }
        if (getCredenceClass() != null) {
            _hashCode += getCredenceClass().hashCode();
        }
        if (getCredenceDefault1() != null) {
            _hashCode += getCredenceDefault1().hashCode();
        }
        if (getCredenceDescribe() != null) {
            _hashCode += getCredenceDescribe().hashCode();
        }
        if (getCredenceState() != null) {
            _hashCode += getCredenceState().hashCode();
        }
        if (getCredenceUniqueid() != null) {
            _hashCode += getCredenceUniqueid().hashCode();
        }
        if (getLoginFailNum() != null) {
            _hashCode += getLoginFailNum().hashCode();
        }
        if (getUniqueid() != null) {
            _hashCode += getUniqueid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserCredenceInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UserCredenceInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceAppend");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceAppend"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceCert");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceCert"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceClass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceDefault1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceDefault1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceDescribe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceDescribe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credenceUniqueid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credenceUniqueid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginFailNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginFailNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uniqueid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uniqueid"));
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
