/**
 * CustomContentInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class CustomContentInfo  implements java.io.Serializable {
    private long contentFlowno;
    private long informationSystem;
    private String shareName;
    private String shareUserType;

    public CustomContentInfo() {
    }

    public long getContentFlowno() {
        return contentFlowno;
    }

    public void setContentFlowno(long contentFlowno) {
        this.contentFlowno = contentFlowno;
    }

    public long getInformationSystem() {
        return informationSystem;
    }

    public void setInformationSystem(long informationSystem) {
        this.informationSystem = informationSystem;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getShareUserType() {
        return shareUserType;
    }

    public void setShareUserType(String shareUserType) {
        this.shareUserType = shareUserType;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CustomContentInfo)) return false;
        CustomContentInfo other = (CustomContentInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.contentFlowno == other.getContentFlowno() &&
            this.informationSystem == other.getInformationSystem() &&
            ((this.shareName==null && other.getShareName()==null) || 
             (this.shareName!=null &&
              this.shareName.equals(other.getShareName()))) &&
            ((this.shareUserType==null && other.getShareUserType()==null) || 
             (this.shareUserType!=null &&
              this.shareUserType.equals(other.getShareUserType())));
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
        _hashCode += new Long(getContentFlowno()).hashCode();
        _hashCode += new Long(getInformationSystem()).hashCode();
        if (getShareName() != null) {
            _hashCode += getShareName().hashCode();
        }
        if (getShareUserType() != null) {
            _hashCode += getShareUserType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomContentInfo.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "CustomContentInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentFlowno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentFlowno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informationSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "informationSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shareName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shareName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shareUserType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shareUserType"));
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
