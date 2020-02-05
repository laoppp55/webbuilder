/**
 * SystemInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uums.client.bean;

public class SystemInformation  implements java.io.Serializable {
    private String indexUrl;
    private String indexUrlStatus;
    private String indexurlencrypt;
    private String isadinfo;
    private String issupfield;
    private String soapUrl;
    private String synchCode;
    private String systemCert;
    private String systemClass;
    private String systemCode;
    private String systemDescribe;
    private String systemEncrypt;
    private String systemFlowno;
    private String systemName;
    private String systemPhaseFlag;
    private String systemSign;
    private String systemUrl;

    public SystemInformation() {
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getIndexUrlStatus() {
        return indexUrlStatus;
    }

    public void setIndexUrlStatus(String indexUrlStatus) {
        this.indexUrlStatus = indexUrlStatus;
    }

    public String getIndexurlencrypt() {
        return indexurlencrypt;
    }

    public void setIndexurlencrypt(String indexurlencrypt) {
        this.indexurlencrypt = indexurlencrypt;
    }

    public String getIsadinfo() {
        return isadinfo;
    }

    public void setIsadinfo(String isadinfo) {
        this.isadinfo = isadinfo;
    }

    public String getIssupfield() {
        return issupfield;
    }

    public void setIssupfield(String issupfield) {
        this.issupfield = issupfield;
    }

    public String getSoapUrl() {
        return soapUrl;
    }

    public void setSoapUrl(String soapUrl) {
        this.soapUrl = soapUrl;
    }

    public String getSynchCode() {
        return synchCode;
    }

    public void setSynchCode(String synchCode) {
        this.synchCode = synchCode;
    }

    public String getSystemCert() {
        return systemCert;
    }

    public void setSystemCert(String systemCert) {
        this.systemCert = systemCert;
    }

    public String getSystemClass() {
        return systemClass;
    }

    public void setSystemClass(String systemClass) {
        this.systemClass = systemClass;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemDescribe() {
        return systemDescribe;
    }

    public void setSystemDescribe(String systemDescribe) {
        this.systemDescribe = systemDescribe;
    }

    public String getSystemEncrypt() {
        return systemEncrypt;
    }

    public void setSystemEncrypt(String systemEncrypt) {
        this.systemEncrypt = systemEncrypt;
    }

    public String getSystemFlowno() {
        return systemFlowno;
    }

    public void setSystemFlowno(String systemFlowno) {
        this.systemFlowno = systemFlowno;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemPhaseFlag() {
        return systemPhaseFlag;
    }

    public void setSystemPhaseFlag(String systemPhaseFlag) {
        this.systemPhaseFlag = systemPhaseFlag;
    }

    public String getSystemSign() {
        return systemSign;
    }

    public void setSystemSign(String systemSign) {
        this.systemSign = systemSign;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SystemInformation)) return false;
        SystemInformation other = (SystemInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.indexUrl==null && other.getIndexUrl()==null) || 
             (this.indexUrl!=null &&
              this.indexUrl.equals(other.getIndexUrl()))) &&
            ((this.indexUrlStatus==null && other.getIndexUrlStatus()==null) || 
             (this.indexUrlStatus!=null &&
              this.indexUrlStatus.equals(other.getIndexUrlStatus()))) &&
            ((this.indexurlencrypt==null && other.getIndexurlencrypt()==null) || 
             (this.indexurlencrypt!=null &&
              this.indexurlencrypt.equals(other.getIndexurlencrypt()))) &&
            ((this.isadinfo==null && other.getIsadinfo()==null) || 
             (this.isadinfo!=null &&
              this.isadinfo.equals(other.getIsadinfo()))) &&
            ((this.issupfield==null && other.getIssupfield()==null) || 
             (this.issupfield!=null &&
              this.issupfield.equals(other.getIssupfield()))) &&
            ((this.soapUrl==null && other.getSoapUrl()==null) || 
             (this.soapUrl!=null &&
              this.soapUrl.equals(other.getSoapUrl()))) &&
            ((this.synchCode==null && other.getSynchCode()==null) || 
             (this.synchCode!=null &&
              this.synchCode.equals(other.getSynchCode()))) &&
            ((this.systemCert==null && other.getSystemCert()==null) || 
             (this.systemCert!=null &&
              this.systemCert.equals(other.getSystemCert()))) &&
            ((this.systemClass==null && other.getSystemClass()==null) || 
             (this.systemClass!=null &&
              this.systemClass.equals(other.getSystemClass()))) &&
            ((this.systemCode==null && other.getSystemCode()==null) || 
             (this.systemCode!=null &&
              this.systemCode.equals(other.getSystemCode()))) &&
            ((this.systemDescribe==null && other.getSystemDescribe()==null) || 
             (this.systemDescribe!=null &&
              this.systemDescribe.equals(other.getSystemDescribe()))) &&
            ((this.systemEncrypt==null && other.getSystemEncrypt()==null) || 
             (this.systemEncrypt!=null &&
              this.systemEncrypt.equals(other.getSystemEncrypt()))) &&
            ((this.systemFlowno==null && other.getSystemFlowno()==null) || 
             (this.systemFlowno!=null &&
              this.systemFlowno.equals(other.getSystemFlowno()))) &&
            ((this.systemName==null && other.getSystemName()==null) || 
             (this.systemName!=null &&
              this.systemName.equals(other.getSystemName()))) &&
            ((this.systemPhaseFlag==null && other.getSystemPhaseFlag()==null) || 
             (this.systemPhaseFlag!=null &&
              this.systemPhaseFlag.equals(other.getSystemPhaseFlag()))) &&
            ((this.systemSign==null && other.getSystemSign()==null) || 
             (this.systemSign!=null &&
              this.systemSign.equals(other.getSystemSign()))) &&
            ((this.systemUrl==null && other.getSystemUrl()==null) || 
             (this.systemUrl!=null &&
              this.systemUrl.equals(other.getSystemUrl())));
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
        if (getIndexUrl() != null) {
            _hashCode += getIndexUrl().hashCode();
        }
        if (getIndexUrlStatus() != null) {
            _hashCode += getIndexUrlStatus().hashCode();
        }
        if (getIndexurlencrypt() != null) {
            _hashCode += getIndexurlencrypt().hashCode();
        }
        if (getIsadinfo() != null) {
            _hashCode += getIsadinfo().hashCode();
        }
        if (getIssupfield() != null) {
            _hashCode += getIssupfield().hashCode();
        }
        if (getSoapUrl() != null) {
            _hashCode += getSoapUrl().hashCode();
        }
        if (getSynchCode() != null) {
            _hashCode += getSynchCode().hashCode();
        }
        if (getSystemCert() != null) {
            _hashCode += getSystemCert().hashCode();
        }
        if (getSystemClass() != null) {
            _hashCode += getSystemClass().hashCode();
        }
        if (getSystemCode() != null) {
            _hashCode += getSystemCode().hashCode();
        }
        if (getSystemDescribe() != null) {
            _hashCode += getSystemDescribe().hashCode();
        }
        if (getSystemEncrypt() != null) {
            _hashCode += getSystemEncrypt().hashCode();
        }
        if (getSystemFlowno() != null) {
            _hashCode += getSystemFlowno().hashCode();
        }
        if (getSystemName() != null) {
            _hashCode += getSystemName().hashCode();
        }
        if (getSystemPhaseFlag() != null) {
            _hashCode += getSystemPhaseFlag().hashCode();
        }
        if (getSystemSign() != null) {
            _hashCode += getSystemSign().hashCode();
        }
        if (getSystemUrl() != null) {
            _hashCode += getSystemUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SystemInformation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.client.uums.bjca.com", "SystemInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indexUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indexUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indexUrlStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indexUrlStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indexurlencrypt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indexurlencrypt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isadinfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isadinfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issupfield");
        elemField.setXmlName(new javax.xml.namespace.QName("", "issupfield"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soapUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soapUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("synchCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "synchCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCert");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemCert"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemClass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemDescribe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemDescribe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemEncrypt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemEncrypt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemFlowno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemFlowno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemPhaseFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemPhaseFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemSign");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemUrl"));
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
