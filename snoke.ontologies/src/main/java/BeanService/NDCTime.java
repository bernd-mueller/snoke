/**
 * NDCTime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class NDCTime  implements java.io.Serializable {
    private java.lang.String ENDDATE;

    private java.lang.String NDC;

    private java.lang.String STARTDATE;

    public NDCTime() {
    }

    public NDCTime(
           java.lang.String ENDDATE,
           java.lang.String NDC,
           java.lang.String STARTDATE) {
           this.ENDDATE = ENDDATE;
           this.NDC = NDC;
           this.STARTDATE = STARTDATE;
    }


    /**
     * Gets the ENDDATE value for this NDCTime.
     * 
     * @return ENDDATE
     */
    public java.lang.String getENDDATE() {
        return ENDDATE;
    }


    /**
     * Sets the ENDDATE value for this NDCTime.
     * 
     * @param ENDDATE
     */
    public void setENDDATE(java.lang.String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }


    /**
     * Gets the NDC value for this NDCTime.
     * 
     * @return NDC
     */
    public java.lang.String getNDC() {
        return NDC;
    }


    /**
     * Sets the NDC value for this NDCTime.
     * 
     * @param NDC
     */
    public void setNDC(java.lang.String NDC) {
        this.NDC = NDC;
    }


    /**
     * Gets the STARTDATE value for this NDCTime.
     * 
     * @return STARTDATE
     */
    public java.lang.String getSTARTDATE() {
        return STARTDATE;
    }


    /**
     * Sets the STARTDATE value for this NDCTime.
     * 
     * @param STARTDATE
     */
    public void setSTARTDATE(java.lang.String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NDCTime)) return false;
        NDCTime other = (NDCTime) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ENDDATE==null && other.getENDDATE()==null) || 
             (this.ENDDATE!=null &&
              this.ENDDATE.equals(other.getENDDATE()))) &&
            ((this.NDC==null && other.getNDC()==null) || 
             (this.NDC!=null &&
              this.NDC.equals(other.getNDC()))) &&
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
        if (getENDDATE() != null) {
            _hashCode += getENDDATE().hashCode();
        }
        if (getNDC() != null) {
            _hashCode += getNDC().hashCode();
        }
        if (getSTARTDATE() != null) {
            _hashCode += getSTARTDATE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NDCTime.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "NDCTime"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ENDDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ENDDATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NDC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NDC"));
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
