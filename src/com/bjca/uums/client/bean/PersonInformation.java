/**
 * PersonInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class PersonInformation  extends UserInformation implements java.io.Serializable {
    private String departCode;
    private java.util.Collection departs;
    private String userAddress;
    private String userCertType;
    private String userDefault1;
    private String userDefault2;
    private String userDefault3;
    private String userDefault4;
    private String userDefault5;
    private String userDefault6;
    private String userDegree;
    private String userDuty;
    private String userEmail;
    private String userIdcardNum;
    private String userMobile;
    private String userName;
    private String userNation;
    private String userPhone;
    private String userPostcode;
    private String userTitle;

    public PersonInformation() {
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public java.util.Collection getDeparts() {
        return departs;
    }

    public void setDeparts(java.util.Collection departs) {
        this.departs = departs;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCertType() {
        return userCertType;
    }

    public void setUserCertType(String userCertType) {
        this.userCertType = userCertType;
    }

    public String getUserDefault1() {
        return userDefault1;
    }

    public void setUserDefault1(String userDefault1) {
        this.userDefault1 = userDefault1;
    }

    public String getUserDefault2() {
        return userDefault2;
    }

    public void setUserDefault2(String userDefault2) {
        this.userDefault2 = userDefault2;
    }

    public String getUserDefault3() {
        return userDefault3;
    }

    public void setUserDefault3(String userDefault3) {
        this.userDefault3 = userDefault3;
    }

    public String getUserDefault4() {
        return userDefault4;
    }

    public void setUserDefault4(String userDefault4) {
        this.userDefault4 = userDefault4;
    }

    public String getUserDefault5() {
        return userDefault5;
    }

    public void setUserDefault5(String userDefault5) {
        this.userDefault5 = userDefault5;
    }

    public String getUserDefault6() {
        return userDefault6;
    }

    public void setUserDefault6(String userDefault6) {
        this.userDefault6 = userDefault6;
    }

    public String getUserDegree() {
        return userDegree;
    }

    public void setUserDegree(String userDegree) {
        this.userDegree = userDegree;
    }

    public String getUserDuty() {
        return userDuty;
    }

    public void setUserDuty(String userDuty) {
        this.userDuty = userDuty;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIdcardNum() {
        return userIdcardNum;
    }

    public void setUserIdcardNum(String userIdcardNum) {
        this.userIdcardNum = userIdcardNum;
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

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation;
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

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PersonInformation)) return false;
        PersonInformation other = (PersonInformation) obj;
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
            ((this.departs==null && other.getDeparts()==null) || 
             (this.departs!=null &&
              this.departs.equals(other.getDeparts()))) &&
            ((this.userAddress==null && other.getUserAddress()==null) || 
             (this.userAddress!=null &&
              this.userAddress.equals(other.getUserAddress()))) &&
            ((this.userCertType==null && other.getUserCertType()==null) || 
             (this.userCertType!=null &&
              this.userCertType.equals(other.getUserCertType()))) &&
            ((this.userDefault1==null && other.getUserDefault1()==null) || 
             (this.userDefault1!=null &&
              this.userDefault1.equals(other.getUserDefault1()))) &&
            ((this.userDefault2==null && other.getUserDefault2()==null) || 
             (this.userDefault2!=null &&
              this.userDefault2.equals(other.getUserDefault2()))) &&
            ((this.userDefault3==null && other.getUserDefault3()==null) || 
             (this.userDefault3!=null &&
              this.userDefault3.equals(other.getUserDefault3()))) &&
            ((this.userDefault4==null && other.getUserDefault4()==null) || 
             (this.userDefault4!=null &&
              this.userDefault4.equals(other.getUserDefault4()))) &&
            ((this.userDefault5==null && other.getUserDefault5()==null) || 
             (this.userDefault5!=null &&
              this.userDefault5.equals(other.getUserDefault5()))) &&
            ((this.userDefault6==null && other.getUserDefault6()==null) || 
             (this.userDefault6!=null &&
              this.userDefault6.equals(other.getUserDefault6()))) &&
            ((this.userDegree==null && other.getUserDegree()==null) || 
             (this.userDegree!=null &&
              this.userDegree.equals(other.getUserDegree()))) &&
            ((this.userDuty==null && other.getUserDuty()==null) || 
             (this.userDuty!=null &&
              this.userDuty.equals(other.getUserDuty()))) &&
            ((this.userEmail==null && other.getUserEmail()==null) || 
             (this.userEmail!=null &&
              this.userEmail.equals(other.getUserEmail()))) &&
            ((this.userIdcardNum==null && other.getUserIdcardNum()==null) || 
             (this.userIdcardNum!=null &&
              this.userIdcardNum.equals(other.getUserIdcardNum()))) &&
            ((this.userMobile==null && other.getUserMobile()==null) || 
             (this.userMobile!=null &&
              this.userMobile.equals(other.getUserMobile()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.userNation==null && other.getUserNation()==null) || 
             (this.userNation!=null &&
              this.userNation.equals(other.getUserNation()))) &&
            ((this.userPhone==null && other.getUserPhone()==null) || 
             (this.userPhone!=null &&
              this.userPhone.equals(other.getUserPhone()))) &&
            ((this.userPostcode==null && other.getUserPostcode()==null) || 
             (this.userPostcode!=null &&
              this.userPostcode.equals(other.getUserPostcode()))) &&
            ((this.userTitle==null && other.getUserTitle()==null) || 
             (this.userTitle!=null &&
              this.userTitle.equals(other.getUserTitle())));
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
        if (getDeparts() != null) {
            _hashCode += getDeparts().hashCode();
        }
        if (getUserAddress() != null) {
            _hashCode += getUserAddress().hashCode();
        }
        if (getUserCertType() != null) {
            _hashCode += getUserCertType().hashCode();
        }
        if (getUserDefault1() != null) {
            _hashCode += getUserDefault1().hashCode();
        }
        if (getUserDefault2() != null) {
            _hashCode += getUserDefault2().hashCode();
        }
        if (getUserDefault3() != null) {
            _hashCode += getUserDefault3().hashCode();
        }
        if (getUserDefault4() != null) {
            _hashCode += getUserDefault4().hashCode();
        }
        if (getUserDefault5() != null) {
            _hashCode += getUserDefault5().hashCode();
        }
        if (getUserDefault6() != null) {
            _hashCode += getUserDefault6().hashCode();
        }
        if (getUserDegree() != null) {
            _hashCode += getUserDegree().hashCode();
        }
        if (getUserDuty() != null) {
            _hashCode += getUserDuty().hashCode();
        }
        if (getUserEmail() != null) {
            _hashCode += getUserEmail().hashCode();
        }
        if (getUserIdcardNum() != null) {
            _hashCode += getUserIdcardNum().hashCode();
        }
        if (getUserMobile() != null) {
            _hashCode += getUserMobile().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getUserNation() != null) {
            _hashCode += getUserNation().hashCode();
        }
        if (getUserPhone() != null) {
            _hashCode += getUserPhone().hashCode();
        }
        if (getUserPostcode() != null) {
            _hashCode += getUserPostcode().hashCode();
        }
        if (getUserTitle() != null) {
            _hashCode += getUserTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "PersonInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://util.java", "Collection"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userCertType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userCertType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDefault6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDefault6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDegree");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDegree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDuty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDuty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userIdcardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userIdcardNum"));
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
        elemField.setFieldName("userNation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userNation"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userTitle"));
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
