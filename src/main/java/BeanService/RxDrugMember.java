/**
 * RxDrugMember.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxDrugMember  implements java.io.Serializable {
    private BeanService.AttributeGroup[] attributes;

    private BeanService.RxMinimalConcept rxMinimalConcept;

    public RxDrugMember() {
    }

    public RxDrugMember(
           BeanService.AttributeGroup[] attributes,
           BeanService.RxMinimalConcept rxMinimalConcept) {
           this.attributes = attributes;
           this.rxMinimalConcept = rxMinimalConcept;
    }


    /**
     * Gets the attributes value for this RxDrugMember.
     * 
     * @return attributes
     */
    public BeanService.AttributeGroup[] getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this RxDrugMember.
     * 
     * @param attributes
     */
    public void setAttributes(BeanService.AttributeGroup[] attributes) {
        this.attributes = attributes;
    }


    /**
     * Gets the rxMinimalConcept value for this RxDrugMember.
     * 
     * @return rxMinimalConcept
     */
    public BeanService.RxMinimalConcept getRxMinimalConcept() {
        return rxMinimalConcept;
    }


    /**
     * Sets the rxMinimalConcept value for this RxDrugMember.
     * 
     * @param rxMinimalConcept
     */
    public void setRxMinimalConcept(BeanService.RxMinimalConcept rxMinimalConcept) {
        this.rxMinimalConcept = rxMinimalConcept;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxDrugMember)) return false;
        RxDrugMember other = (RxDrugMember) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              java.util.Arrays.equals(this.attributes, other.getAttributes()))) &&
            ((this.rxMinimalConcept==null && other.getRxMinimalConcept()==null) || 
             (this.rxMinimalConcept!=null &&
              this.rxMinimalConcept.equals(other.getRxMinimalConcept())));
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
        if (getAttributes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttributes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttributes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRxMinimalConcept() != null) {
            _hashCode += getRxMinimalConcept().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxDrugMember.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxDrugMember"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "AttributeGroup"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxMinimalConcept");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxMinimalConcept"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxMinimalConcept"));
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
