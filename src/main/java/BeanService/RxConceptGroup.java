/**
 * RxConceptGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxConceptGroup  implements java.io.Serializable {
    private BeanService.RxConcept[] rxConcept;

    private java.lang.String type;

    public RxConceptGroup() {
    }

    public RxConceptGroup(
           BeanService.RxConcept[] rxConcept,
           java.lang.String type) {
           this.rxConcept = rxConcept;
           this.type = type;
    }


    /**
     * Gets the rxConcept value for this RxConceptGroup.
     * 
     * @return rxConcept
     */
    public BeanService.RxConcept[] getRxConcept() {
        return rxConcept;
    }


    /**
     * Sets the rxConcept value for this RxConceptGroup.
     * 
     * @param rxConcept
     */
    public void setRxConcept(BeanService.RxConcept[] rxConcept) {
        this.rxConcept = rxConcept;
    }


    /**
     * Gets the type value for this RxConceptGroup.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this RxConceptGroup.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxConceptGroup)) return false;
        RxConceptGroup other = (RxConceptGroup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rxConcept==null && other.getRxConcept()==null) || 
             (this.rxConcept!=null &&
              java.util.Arrays.equals(this.rxConcept, other.getRxConcept()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getRxConcept() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRxConcept());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRxConcept(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxConceptGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxConceptGroup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxConcept");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxConcept"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxConcept"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
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
