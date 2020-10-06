/**
 * ReverseRxcuiStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class ReverseRxcuiStatus  implements java.io.Serializable {
    private java.lang.Object[] reverseRxcuis;

    private java.lang.String status;

    public ReverseRxcuiStatus() {
    }

    public ReverseRxcuiStatus(
           java.lang.Object[] reverseRxcuis,
           java.lang.String status) {
           this.reverseRxcuis = reverseRxcuis;
           this.status = status;
    }


    /**
     * Gets the reverseRxcuis value for this ReverseRxcuiStatus.
     * 
     * @return reverseRxcuis
     */
    public java.lang.Object[] getReverseRxcuis() {
        return reverseRxcuis;
    }


    /**
     * Sets the reverseRxcuis value for this ReverseRxcuiStatus.
     * 
     * @param reverseRxcuis
     */
    public void setReverseRxcuis(java.lang.Object[] reverseRxcuis) {
        this.reverseRxcuis = reverseRxcuis;
    }


    /**
     * Gets the status value for this ReverseRxcuiStatus.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ReverseRxcuiStatus.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReverseRxcuiStatus)) return false;
        ReverseRxcuiStatus other = (ReverseRxcuiStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reverseRxcuis==null && other.getReverseRxcuis()==null) || 
             (this.reverseRxcuis!=null &&
              java.util.Arrays.equals(this.reverseRxcuis, other.getReverseRxcuis()))) &&
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
        if (getReverseRxcuis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReverseRxcuis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReverseRxcuis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReverseRxcuiStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "ReverseRxcuiStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reverseRxcuis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reverseRxcuis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
