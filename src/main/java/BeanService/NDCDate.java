/**
 * NDCDate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class NDCDate  implements java.io.Serializable {
    private java.lang.String CURRENTRXCUI;

    private java.lang.String ENDDATE;

    private java.lang.String ORIGINALRXCUI;

    private java.lang.String STARTDATE;

    public NDCDate() {
    }

    public NDCDate(
           java.lang.String CURRENTRXCUI,
           java.lang.String ENDDATE,
           java.lang.String ORIGINALRXCUI,
           java.lang.String STARTDATE) {
           this.CURRENTRXCUI = CURRENTRXCUI;
           this.ENDDATE = ENDDATE;
           this.ORIGINALRXCUI = ORIGINALRXCUI;
           this.STARTDATE = STARTDATE;
    }


    /**
     * Gets the CURRENTRXCUI value for this NDCDate.
     * 
     * @return CURRENTRXCUI
     */
    public java.lang.String getCURRENTRXCUI() {
        return CURRENTRXCUI;
    }


    /**
     * Sets the CURRENTRXCUI value for this NDCDate.
     * 
     * @param CURRENTRXCUI
     */
    public void setCURRENTRXCUI(java.lang.String CURRENTRXCUI) {
        this.CURRENTRXCUI = CURRENTRXCUI;
    }


    /**
     * Gets the ENDDATE value for this NDCDate.
     * 
     * @return ENDDATE
     */
    public java.lang.String getENDDATE() {
        return ENDDATE;
    }


    /**
     * Sets the ENDDATE value for this NDCDate.
     * 
     * @param ENDDATE
     */
    public void setENDDATE(java.lang.String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }


    /**
     * Gets the ORIGINALRXCUI value for this NDCDate.
     * 
     * @return ORIGINALRXCUI
     */
    public java.lang.String getORIGINALRXCUI() {
        return ORIGINALRXCUI;
    }


    /**
     * Sets the ORIGINALRXCUI value for this NDCDate.
     * 
     * @param ORIGINALRXCUI
     */
    public void setORIGINALRXCUI(java.lang.String ORIGINALRXCUI) {
        this.ORIGINALRXCUI = ORIGINALRXCUI;
    }


    /**
     * Gets the STARTDATE value for this NDCDate.
     * 
     * @return STARTDATE
     */
    public java.lang.String getSTARTDATE() {
        return STARTDATE;
    }


    /**
     * Sets the STARTDATE value for this NDCDate.
     * 
     * @param STARTDATE
     */
    public void setSTARTDATE(java.lang.String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NDCDate)) return false;
        NDCDate other = (NDCDate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.CURRENTRXCUI==null && other.getCURRENTRXCUI()==null) || 
             (this.CURRENTRXCUI!=null &&
              this.CURRENTRXCUI.equals(other.getCURRENTRXCUI()))) &&
            ((this.ENDDATE==null && other.getENDDATE()==null) || 
             (this.ENDDATE!=null &&
              this.ENDDATE.equals(other.getENDDATE()))) &&
            ((this.ORIGINALRXCUI==null && other.getORIGINALRXCUI()==null) || 
             (this.ORIGINALRXCUI!=null &&
              this.ORIGINALRXCUI.equals(other.getORIGINALRXCUI()))) &&
            ((this.STARTDATE==null && other.getSTARTDATE()==null) || 
             (this.STARTDATE!=null &&
              this.STARTDATE.equals(other.getSTARTDATE())));
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
        if (getCURRENTRXCUI() != null) {
            _hashCode += getCURRENTRXCUI().hashCode();
        }
        if (getENDDATE() != null) {
            _hashCode += getENDDATE().hashCode();
        }
        if (getORIGINALRXCUI() != null) {
            _hashCode += getORIGINALRXCUI().hashCode();
        }
        if (getSTARTDATE() != null) {
            _hashCode += getSTARTDATE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NDCDate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "NDCDate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CURRENTRXCUI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CURRENTRXCUI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ENDDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ENDDATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ORIGINALRXCUI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ORIGINALRXCUI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STARTDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STARTDATE"));
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
