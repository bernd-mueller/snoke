/**
 * RxMatchInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxMatchInfo  implements java.io.Serializable {
    private int RANK;

    private java.lang.String RXAUI;

    private java.lang.String RXCUI;

    private int SCORE;

    public RxMatchInfo() {
    }

    public RxMatchInfo(
           int RANK,
           java.lang.String RXAUI,
           java.lang.String RXCUI,
           int SCORE) {
           this.RANK = RANK;
           this.RXAUI = RXAUI;
           this.RXCUI = RXCUI;
           this.SCORE = SCORE;
    }


    /**
     * Gets the RANK value for this RxMatchInfo.
     * 
     * @return RANK
     */
    public int getRANK() {
        return RANK;
    }


    /**
     * Sets the RANK value for this RxMatchInfo.
     * 
     * @param RANK
     */
    public void setRANK(int RANK) {
        this.RANK = RANK;
    }


    /**
     * Gets the RXAUI value for this RxMatchInfo.
     * 
     * @return RXAUI
     */
    public java.lang.String getRXAUI() {
        return RXAUI;
    }


    /**
     * Sets the RXAUI value for this RxMatchInfo.
     * 
     * @param RXAUI
     */
    public void setRXAUI(java.lang.String RXAUI) {
        this.RXAUI = RXAUI;
    }


    /**
     * Gets the RXCUI value for this RxMatchInfo.
     * 
     * @return RXCUI
     */
    public java.lang.String getRXCUI() {
        return RXCUI;
    }


    /**
     * Sets the RXCUI value for this RxMatchInfo.
     * 
     * @param RXCUI
     */
    public void setRXCUI(java.lang.String RXCUI) {
        this.RXCUI = RXCUI;
    }


    /**
     * Gets the SCORE value for this RxMatchInfo.
     * 
     * @return SCORE
     */
    public int getSCORE() {
        return SCORE;
    }


    /**
     * Sets the SCORE value for this RxMatchInfo.
     * 
     * @param SCORE
     */
    public void setSCORE(int SCORE) {
        this.SCORE = SCORE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxMatchInfo)) return false;
        RxMatchInfo other = (RxMatchInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.RANK == other.getRANK() &&
            ((this.RXAUI==null && other.getRXAUI()==null) || 
             (this.RXAUI!=null &&
              this.RXAUI.equals(other.getRXAUI()))) &&
            ((this.RXCUI==null && other.getRXCUI()==null) || 
             (this.RXCUI!=null &&
              this.RXCUI.equals(other.getRXCUI()))) &&
            this.SCORE == other.getSCORE();
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
        _hashCode += getRANK();
        if (getRXAUI() != null) {
            _hashCode += getRXAUI().hashCode();
        }
        if (getRXCUI() != null) {
            _hashCode += getRXCUI().hashCode();
        }
        _hashCode += getSCORE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxMatchInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxMatchInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RANK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RANK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RXAUI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RXAUI"));
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
        elemField.setFieldName("SCORE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SCORE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
