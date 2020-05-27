/**
 * HistoricalNDCTime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class HistoricalNDCTime  implements java.io.Serializable {
    private BeanService.NDCTime[] ndcTimes;

    private java.lang.String rxcui;

    private java.lang.String status;

    public HistoricalNDCTime() {
    }

    public HistoricalNDCTime(
           BeanService.NDCTime[] ndcTimes,
           java.lang.String rxcui,
           java.lang.String status) {
           this.ndcTimes = ndcTimes;
           this.rxcui = rxcui;
           this.status = status;
    }


    /**
     * Gets the ndcTimes value for this HistoricalNDCTime.
     * 
     * @return ndcTimes
     */
    public BeanService.NDCTime[] getNdcTimes() {
        return ndcTimes;
    }


    /**
     * Sets the ndcTimes value for this HistoricalNDCTime.
     * 
     * @param ndcTimes
     */
    public void setNdcTimes(BeanService.NDCTime[] ndcTimes) {
        this.ndcTimes = ndcTimes;
    }


    /**
     * Gets the rxcui value for this HistoricalNDCTime.
     * 
     * @return rxcui
     */
    public java.lang.String getRxcui() {
        return rxcui;
    }


    /**
     * Sets the rxcui value for this HistoricalNDCTime.
     * 
     * @param rxcui
     */
    public void setRxcui(java.lang.String rxcui) {
        this.rxcui = rxcui;
    }


    /**
     * Gets the status value for this HistoricalNDCTime.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this HistoricalNDCTime.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HistoricalNDCTime)) return false;
        HistoricalNDCTime other = (HistoricalNDCTime) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ndcTimes==null && other.getNdcTimes()==null) || 
             (this.ndcTimes!=null &&
              java.util.Arrays.equals(this.ndcTimes, other.getNdcTimes()))) &&
            ((this.rxcui==null && other.getRxcui()==null) || 
             (this.rxcui!=null &&
              this.rxcui.equals(other.getRxcui()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getNdcTimes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNdcTimes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNdcTimes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRxcui() != null) {
            _hashCode += getRxcui().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HistoricalNDCTime.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "HistoricalNDCTime"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ndcTimes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ndcTimes"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "NDCTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxcui");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxcui"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
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
