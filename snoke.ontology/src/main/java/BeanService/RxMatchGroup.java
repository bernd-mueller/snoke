/**
 * RxMatchGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxMatchGroup  implements java.io.Serializable {
    private java.lang.String comment;

    private BeanService.RxMatchInfo[] rxMatchInfo;

    public RxMatchGroup() {
    }

    public RxMatchGroup(
           java.lang.String comment,
           BeanService.RxMatchInfo[] rxMatchInfo) {
           this.comment = comment;
           this.rxMatchInfo = rxMatchInfo;
    }


    /**
     * Gets the comment value for this RxMatchGroup.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this RxMatchGroup.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the rxMatchInfo value for this RxMatchGroup.
     * 
     * @return rxMatchInfo
     */
    public BeanService.RxMatchInfo[] getRxMatchInfo() {
        return rxMatchInfo;
    }


    /**
     * Sets the rxMatchInfo value for this RxMatchGroup.
     * 
     * @param rxMatchInfo
     */
    public void setRxMatchInfo(BeanService.RxMatchInfo[] rxMatchInfo) {
        this.rxMatchInfo = rxMatchInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxMatchGroup)) return false;
        RxMatchGroup other = (RxMatchGroup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.rxMatchInfo==null && other.getRxMatchInfo()==null) || 
             (this.rxMatchInfo!=null &&
              java.util.Arrays.equals(this.rxMatchInfo, other.getRxMatchInfo())));
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
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getRxMatchInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRxMatchInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRxMatchInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxMatchGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxMatchGroup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxMatchInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxMatchInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxMatchInfo"));
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
