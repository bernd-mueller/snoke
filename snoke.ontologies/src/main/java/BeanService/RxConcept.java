/**
 * RxConcept.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxConcept  implements java.io.Serializable {
    private java.lang.String CUI;

    private java.lang.String LAT;

    private java.lang.String RXCUI;

    private java.lang.String STR;

    private java.lang.String SUPPRESS;

    private java.lang.String SY;

    private java.lang.String TTY;

    public RxConcept() {
    }

    public RxConcept(
           java.lang.String CUI,
           java.lang.String LAT,
           java.lang.String RXCUI,
           java.lang.String STR,
           java.lang.String SUPPRESS,
           java.lang.String SY,
           java.lang.String TTY) {
           this.CUI = CUI;
           this.LAT = LAT;
           this.RXCUI = RXCUI;
           this.STR = STR;
           this.SUPPRESS = SUPPRESS;
           this.SY = SY;
           this.TTY = TTY;
    }


    /**
     * Gets the CUI value for this RxConcept.
     * 
     * @return CUI
     */
    public java.lang.String getCUI() {
        return CUI;
    }


    /**
     * Sets the CUI value for this RxConcept.
     * 
     * @param CUI
     */
    public void setCUI(java.lang.String CUI) {
        this.CUI = CUI;
    }


    /**
     * Gets the LAT value for this RxConcept.
     * 
     * @return LAT
     */
    public java.lang.String getLAT() {
        return LAT;
    }


    /**
     * Sets the LAT value for this RxConcept.
     * 
     * @param LAT
     */
    public void setLAT(java.lang.String LAT) {
        this.LAT = LAT;
    }


    /**
     * Gets the RXCUI value for this RxConcept.
     * 
     * @return RXCUI
     */
    public java.lang.String getRXCUI() {
        return RXCUI;
    }


    /**
     * Sets the RXCUI value for this RxConcept.
     * 
     * @param RXCUI
     */
    public void setRXCUI(java.lang.String RXCUI) {
        this.RXCUI = RXCUI;
    }


    /**
     * Gets the STR value for this RxConcept.
     * 
     * @return STR
     */
    public java.lang.String getSTR() {
        return STR;
    }


    /**
     * Sets the STR value for this RxConcept.
     * 
     * @param STR
     */
    public void setSTR(java.lang.String STR) {
        this.STR = STR;
    }


    /**
     * Gets the SUPPRESS value for this RxConcept.
     * 
     * @return SUPPRESS
     */
    public java.lang.String getSUPPRESS() {
        return SUPPRESS;
    }


    /**
     * Sets the SUPPRESS value for this RxConcept.
     * 
     * @param SUPPRESS
     */
    public void setSUPPRESS(java.lang.String SUPPRESS) {
        this.SUPPRESS = SUPPRESS;
    }


    /**
     * Gets the SY value for this RxConcept.
     * 
     * @return SY
     */
    public java.lang.String getSY() {
        return SY;
    }


    /**
     * Sets the SY value for this RxConcept.
     * 
     * @param SY
     */
    public void setSY(java.lang.String SY) {
        this.SY = SY;
    }


    /**
     * Gets the TTY value for this RxConcept.
     * 
     * @return TTY
     */
    public java.lang.String getTTY() {
        return TTY;
    }


    /**
     * Sets the TTY value for this RxConcept.
     * 
     * @param TTY
     */
    public void setTTY(java.lang.String TTY) {
        this.TTY = TTY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxConcept)) return false;
        RxConcept other = (RxConcept) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.CUI==null && other.getCUI()==null) || 
             (this.CUI!=null &&
              this.CUI.equals(other.getCUI()))) &&
            ((this.LAT==null && other.getLAT()==null) || 
             (this.LAT!=null &&
              this.LAT.equals(other.getLAT()))) &&
            ((this.RXCUI==null && other.getRXCUI()==null) || 
             (this.RXCUI!=null &&
              this.RXCUI.equals(other.getRXCUI()))) &&
            ((this.STR==null && other.getSTR()==null) || 
             (this.STR!=null &&
              this.STR.equals(other.getSTR()))) &&
            ((this.SUPPRESS==null && other.getSUPPRESS()==null) || 
             (this.SUPPRESS!=null &&
              this.SUPPRESS.equals(other.getSUPPRESS()))) &&
            ((this.SY==null && other.getSY()==null) || 
             (this.SY!=null &&
              this.SY.equals(other.getSY()))) &&
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
        if (getCUI() != null) {
            _hashCode += getCUI().hashCode();
        }
        if (getLAT() != null) {
            _hashCode += getLAT().hashCode();
        }
        if (getRXCUI() != null) {
            _hashCode += getRXCUI().hashCode();
        }
        if (getSTR() != null) {
            _hashCode += getSTR().hashCode();
        }
        if (getSUPPRESS() != null) {
            _hashCode += getSUPPRESS().hashCode();
        }
        if (getSY() != null) {
            _hashCode += getSY().hashCode();
        }
        if (getTTY() != null) {
            _hashCode += getTTY().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxConcept.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxConcept"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LAT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LAT"));
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
        elemField.setFieldName("STR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUPPRESS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SUPPRESS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SY"));
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
