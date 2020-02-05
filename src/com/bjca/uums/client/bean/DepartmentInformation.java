/**
 * DepartmentInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class DepartmentInformation  implements java.io.Serializable {
    private String departAddress;
    private String departCode;
    private String departDefault;
    private String departDescript;
    private String departName;
    private String departPhone;
    private String departPostCode;
    private String departSortNum;
    private String departUnitCode;
    private String departUpcode;
    private String departWEBAddress;

    public DepartmentInformation() {
    }

    public String getDepartAddress() {
        return departAddress;
    }

    public void setDepartAddress(String departAddress) {
        this.departAddress = departAddress;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getDepartDefault() {
        return departDefault;
    }

    public void setDepartDefault(String departDefault) {
        this.departDefault = departDefault;
    }

    public String getDepartDescript() {
        return departDescript;
    }

    public void setDepartDescript(String departDescript) {
        this.departDescript = departDescript;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartPhone() {
        return departPhone;
    }

    public void setDepartPhone(String departPhone) {
        this.departPhone = departPhone;
    }

    public String getDepartPostCode() {
        return departPostCode;
    }

    public void setDepartPostCode(String departPostCode) {
        this.departPostCode = departPostCode;
    }

    public String getDepartSortNum() {
        return departSortNum;
    }

    public void setDepartSortNum(String departSortNum) {
        this.departSortNum = departSortNum;
    }

    public String getDepartUnitCode() {
        return departUnitCode;
    }

    public void setDepartUnitCode(String departUnitCode) {
        this.departUnitCode = departUnitCode;
    }

    public String getDepartUpcode() {
        return departUpcode;
    }

    public void setDepartUpcode(String departUpcode) {
        this.departUpcode = departUpcode;
    }

    public String getDepartWEBAddress() {
        return departWEBAddress;
    }

    public void setDepartWEBAddress(String departWEBAddress) {
        this.departWEBAddress = departWEBAddress;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof DepartmentInformation)) return false;
        DepartmentInformation other = (DepartmentInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.departAddress==null && other.getDepartAddress()==null) || 
             (this.departAddress!=null &&
              this.departAddress.equals(other.getDepartAddress()))) &&
            ((this.departCode==null && other.getDepartCode()==null) || 
             (this.departCode!=null &&
              this.departCode.equals(other.getDepartCode()))) &&
            ((this.departDefault==null && other.getDepartDefault()==null) || 
             (this.departDefault!=null &&
              this.departDefault.equals(other.getDepartDefault()))) &&
            ((this.departDescript==null && other.getDepartDescript()==null) || 
             (this.departDescript!=null &&
              this.departDescript.equals(other.getDepartDescript()))) &&
            ((this.departName==null && other.getDepartName()==null) || 
             (this.departName!=null &&
              this.departName.equals(other.getDepartName()))) &&
            ((this.departPhone==null && other.getDepartPhone()==null) || 
             (this.departPhone!=null &&
              this.departPhone.equals(other.getDepartPhone()))) &&
            ((this.departPostCode==null && other.getDepartPostCode()==null) || 
             (this.departPostCode!=null &&
              this.departPostCode.equals(other.getDepartPostCode()))) &&
            ((this.departSortNum==null && other.getDepartSortNum()==null) || 
             (this.departSortNum!=null &&
              this.departSortNum.equals(other.getDepartSortNum()))) &&
            ((this.departUnitCode==null && other.getDepartUnitCode()==null) || 
             (this.departUnitCode!=null &&
              this.departUnitCode.equals(other.getDepartUnitCode()))) &&
            ((this.departUpcode==null && other.getDepartUpcode()==null) || 
             (this.departUpcode!=null &&
              this.departUpcode.equals(other.getDepartUpcode()))) &&
            ((this.departWEBAddress==null && other.getDepartWEBAddress()==null) || 
             (this.departWEBAddress!=null &&
              this.departWEBAddress.equals(other.getDepartWEBAddress())));
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
        if (getDepartAddress() != null) {
            _hashCode += getDepartAddress().hashCode();
        }
        if (getDepartCode() != null) {
            _hashCode += getDepartCode().hashCode();
        }
        if (getDepartDefault() != null) {
            _hashCode += getDepartDefault().hashCode();
        }
        if (getDepartDescript() != null) {
            _hashCode += getDepartDescript().hashCode();
        }
        if (getDepartName() != null) {
            _hashCode += getDepartName().hashCode();
        }
        if (getDepartPhone() != null) {
            _hashCode += getDepartPhone().hashCode();
        }
        if (getDepartPostCode() != null) {
            _hashCode += getDepartPostCode().hashCode();
        }
        if (getDepartSortNum() != null) {
            _hashCode += getDepartSortNum().hashCode();
        }
        if (getDepartUnitCode() != null) {
            _hashCode += getDepartUnitCode().hashCode();
        }
        if (getDepartUpcode() != null) {
            _hashCode += getDepartUpcode().hashCode();
        }
        if (getDepartWEBAddress() != null) {
            _hashCode += getDepartWEBAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepartmentInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "DepartmentInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departDefault");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departDefault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departDescript");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departDescript"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departPostCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departPostCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departSortNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departSortNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departUnitCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departUnitCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departUpcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departUpcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departWEBAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departWEBAddress"));
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
