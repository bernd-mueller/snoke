/**
 * RxEdge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxEdge  implements java.io.Serializable {
    private java.lang.String ID1;

    private java.lang.String ID2;

    private java.lang.String REL;

    public RxEdge() {
    }

    public RxEdge(
           java.lang.String ID1,
           java.lang.String ID2,
           java.lang.String REL) {
           this.ID1 = ID1;
           this.ID2 = ID2;
           this.REL = REL;
    }


    /**
     * Gets the ID1 value for this RxEdge.
     * 
     * @return ID1
     */
    public java.lang.String getID1() {
        return ID1;
    }


    /**
     * Sets the ID1 value for this RxEdge.
     * 
     * @param ID1
     */
    public void setID1(java.lang.String ID1) {
        this.ID1 = ID1;
    }


    /**
     * Gets the ID2 value for this RxEdge.
     * 
     * @return ID2
     */
    public java.lang.String getID2() {
        return ID2;
    }


    /**
     * Sets the ID2 value for this RxEdge.
     * 
     * @param ID2
     */
    public void setID2(java.lang.String ID2) {
        this.ID2 = ID2;
    }


    /**
     * Gets the REL value for this RxEdge.
     * 
     * @return REL
     */
    public java.lang.String getREL() {
        return REL;
    }


    /**
     * Sets the REL value for this RxEdge.
     * 
     * @param REL
     */
    public void setREL(java.lang.String REL) {
        this.REL = REL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxEdge)) return false;
        RxEdge other = (RxEdge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ID1==null && other.getID1()==null) || 
             (this.ID1!=null &&
              this.ID1.equals(other.getID1()))) &&
            ((this.ID2==null && other.getID2()==null) || 
             (this.ID2!=null &&
              this.ID2.equals(other.getID2()))) &&
            ((this.REL==null && other.getREL()==null) || 
             (this.REL!=null &&
              this.REL.equals(other.getREL())));
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
        if (getID1() != null) {
            _hashCode += getID1().hashCode();
        }
        if (getID2() != null) {
            _hashCode += getID2().hashCode();
        }
        if (getREL() != null) {
            _hashCode += getREL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxEdge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxEdge"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "REL"));
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
