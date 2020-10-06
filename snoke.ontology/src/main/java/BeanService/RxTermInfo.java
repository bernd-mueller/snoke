/**
 * RxTermInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxTermInfo  implements java.io.Serializable {
    private java.lang.String CODE;

    private java.lang.String RXCUI;

    private java.lang.String SAB;

    private java.lang.String STR;

    private java.lang.String TTY;

    public RxTermInfo() {
    }

    public RxTermInfo(
           java.lang.String CODE,
           java.lang.String RXCUI,
           java.lang.String SAB,
           java.lang.String STR,
           java.lang.String TTY) {
           this.CODE = CODE;
           this.RXCUI = RXCUI;
           this.SAB = SAB;
           this.STR = STR;
           this.TTY = TTY;
    }


    /**
     * Gets the CODE value for this RxTermInfo.
     * 
     * @return CODE
     */
    public java.lang.String getCODE() {
        return CODE;
    }


    /**
     * Sets the CODE value for this RxTermInfo.
     * 
     * @param CODE
     */
    public void setCODE(java.lang.String CODE) {
        this.CODE = CODE;
    }


    /**
     * Gets the RXCUI value for this RxTermInfo.
     * 
     * @return RXCUI
     */
    public java.lang.String getRXCUI() {
        return RXCUI;
    }


    /**
     * Sets the RXCUI value for this RxTermInfo.
     * 
     * @param RXCUI
     */
    public void setRXCUI(java.lang.String RXCUI) {
        this.RXCUI = RXCUI;
    }


    /**
     * Gets the SAB value for this RxTermInfo.
     * 
     * @return SAB
     */
    public java.lang.String getSAB() {
        return SAB;
    }


    /**
     * Sets the SAB value for this RxTermInfo.
     * 
     * @param SAB
     */
    public void setSAB(java.lang.String SAB) {
        this.SAB = SAB;
    }


    /**
     * Gets the STR value for this RxTermInfo.
     * 
     * @return STR
     */
    public java.lang.String getSTR() {
        return STR;
    }


    /**
     * Sets the STR value for this RxTermInfo.
     * 
     * @param STR
     */
    public void setSTR(java.lang.String STR) {
        this.STR = STR;
    }


    /**
     * Gets the TTY value for this RxTermInfo.
     * 
     * @return TTY
     */
    public java.lang.String getTTY() {
        return TTY;
    }


    /**
     * Sets the TTY value for this RxTermInfo.
     * 
     * @param TTY
     */
    public void setTTY(java.lang.String TTY) {
        this.TTY = TTY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxTermInfo)) return false;
        RxTermInfo other = (RxTermInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.CODE==null && other.getCODE()==null) || 
             (this.CODE!=null &&
              this.CODE.equals(other.getCODE()))) &&
            ((this.RXCUI==null && other.getRXCUI()==null) || 
             (this.RXCUI!=null &&
              this.RXCUI.equals(other.getRXCUI()))) &&
            ((this.SAB==null && other.getSAB()==null) || 
             (this.SAB!=null &&
              this.SAB.equals(other.getSAB()))) &&
            ((this.STR==null && other.getSTR()==null) || 
             (this.STR!=null &&
              this.STR.equals(other.getSTR()))) &&
            ((this.TTY==null && other.getTTY()==null) || 
             (this.TTY!=null &&
              this.TTY.equals(other.getTTY())));
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
        if (getCODE() != null) {
            _hashCode += getCODE().hashCode();
        }
        if (getRXCUI() != null) {
            _hashCode += getRXCUI().hashCode();
        }
        if (getSAB() != null) {
            _hashCode += getSAB().hashCode();
        }
        if (getSTR() != null) {
            _hashCode += getSTR().hashCode();
        }
        if (getTTY() != null) {
            _hashCode += getTTY().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxTermInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxTermInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RXCUI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RXCUI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SAB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SAB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TTY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
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
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
