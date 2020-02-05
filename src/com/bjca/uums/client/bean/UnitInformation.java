/**
 * UnitInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class UnitInformation  extends com.bjca.uums.client.bean.UserInformation  implements java.io.Serializable {
    private String departCode;
    private String departName;
    private java.util.Collection departs;
    private String unitAddress;
    private String unitCode;
    private String unitDefault1;
    private String unitDefault2;
    private String unitDefault3;
    private String unitDefault4;
    private String unitDefault5;
    private String userEmail;
    private String userMobile;
    private String userName;
    private String userPhone;
    private String userPostcode;

    public UnitInformation() {
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public java.util.Collection getDeparts() {
        return departs;
    }

    public void setDeparts(java.util.Collection departs) {
        this.departs = departs;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitDefault1() {
        return unitDefault1;
    }

    public void setUnitDefault1(String unitDefault1) {
        this.unitDefault1 = unitDefault1;
    }

    public String getUnitDefault2() {
        return unitDefault2;
    }

    public void setUnitDefault2(String unitDefault2) {
        this.unitDefault2 = unitDefault2;
    }

    public String getUnitDefault3() {
        return unitDefault3;
    }

    public void setUnitDefault3(String unitDefault3) {
        this.unitDefault3 = unitDefault3;
    }

    public String getUnitDefault4() {
        return unitDefault4;
    }

    public void setUnitDefault4(String unitDefault4) {
        this.unitDefault4 = unitDefault4;
    }

    public String getUnitDefault5() {
        return unitDefault5;
    }

    public void setUnitDefault5(String unitDefault5) {
        this.unitDefault5 = unitDefault5;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPostcode() {
        return userPostcode;
    }

    public void setUserPostcode(String userPostcode) {
        this.userPostcode = userPostcode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UnitInformation)) return false;
        UnitInformation other = (UnitInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.departCode==null && other.getDepartCode()==null) || 
             (this.departCode!=null &&
              this.departCode.equals(other.getDepartCode()))) &&
            ((this.departName==null && other.getDepartName()==null) || 
             (this.departName!=null &&
              this.departName.equals(other.getDepartName()))) &&
            ((this.departs==null && other.getDeparts()==null) || 
             (this.departs!=null &&
              this.departs.equals(other.getDeparts()))) &&
            ((this.unitAddress==null && other.getUnitAddress()==null) || 
             (this.unitAddress!=null &&
              this.unitAddress.equals(other.getUnitAddress()))) &&
            ((this.unitCode==null && other.getUnitCode()==null) || 
             (this.unitCode!=null &&
              this.unitCode.equals(other.getUnitCode()))) &&
            ((this.unitDefault1==null && other.getUnitDefault1()==null) || 
             (this.unitDefault1!=null &&
              this.unitDefault1.equals(other.getUnitDefault1()))) &&
            ((this.unitDefault2==null && other.getUnitDefault2()==null) || 
             (this.unitDefault2!=null &&
              this.unitDefault2.equals(other.getUnitDefault2()))) &&
            ((this.unitDefault3==null && other.getUnitDefault3()==null) || 
             (this.unitDefault3!=null &&
              this.unitDefault3.equals(other.getUnitDefault3()))) &&
            ((this.unitDefault4==null && other.getUnitDefault4()==null) || 
             (this.unitDefault4!=null &&
              this.unitDefault4.equals(other.getUnitDefault4()))) &&
            ((this.unitDefault5==null && other.getUnitDefault5()==null) || 
             (this.unitDefault5!=null &&
              this.unitDefault5.equals(other.getUnitDefault5()))) &&
            ((this.userEmail==null && other.getUserEmail()==null) || 
             (this.userEmail!=null &&
              this.userEmail.equals(other.getUserEmail()))) &&
            ((this.userMobile==null && other.getUserMobile()==null) || 
             (this.userMobile!=null &&
              this.userMobile.equals(other.getUserMobile()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.userPhone==null && other.getUserPhone()==null) || 
             (this.userPhone!=null &&
              this.userPhone.equals(other.getUserPhone()))) &&
            ((this.userPostcode==null && other.getUserPostcode()==null) || 
             (this.userPostcode!=null &&
              this.userPostcode.equals(other.getUserPostcode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDepartCode() != null) {
            _hashCode += getDepartCode().hashCode();
        }
        if (getDepartName() != null) {
            _hashCode += getDepartName().hashCode();
        }
        if (getDeparts() != null) {
            _hashCode += getDeparts().hashCode();
        }
        if (getUnitAddress() != null) {
            _hashCode += getUnitAddress().hashCode();
        }
        if (getUnitCode() != null) {
            _hashCode += getUnitCode().hashCode();
        }
        if (getUnitDefault1() != null) {
            _hashCode += getUnitDefault1().hashCode();
        }
        if (getUnitDefault2() != null) {
            _hashCode += getUnitDefault2().hashCode();
        }
        if (getUnitDefault3() != null) {
            _hashCode += getUnitDefault3().hashCode();
        }
        if (getUnitDefault4() != null) {
            _hashCode += getUnitDefault4().hashCode();
        }
        if (getUnitDefault5() != null) {
            _hashCode += getUnitDefault5().hashCode();
        }
        if (getUserEmail() != null) {
            _hashCode += getUserEmail().hashCode();
        }
        if (getUserMobile() != null) {
            _hashCode += getUserMobile().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getUserPhone() != null) {
            _hashCode += getUserPhone().hashCode();
        }
        if (getUserPostcode() != null) {
            _hashCode += getUserPostcode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnitInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "UnitInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitDefault1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitDefault1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitDefault2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitDefault2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitDefault3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitDefault3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitDefault4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitDefault4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitDefault5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitDefault5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userMobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userMobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userPostcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userPostcode"));
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
