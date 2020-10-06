/**
 * RxNode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxNode  implements java.io.Serializable {
    private java.lang.String[] ATTR;

    private java.lang.String ID;

    private java.lang.String STR;

    public RxNode() {
    }

    public RxNode(
           java.lang.String[] ATTR,
           java.lang.String ID,
           java.lang.String STR) {
           this.ATTR = ATTR;
           this.ID = ID;
           this.STR = STR;
    }


    /**
     * Gets the ATTR value for this RxNode.
     * 
     * @return ATTR
     */
    public java.lang.String[] getATTR() {
        return ATTR;
    }


    /**
     * Sets the ATTR value for this RxNode.
     * 
     * @param ATTR
     */
    public void setATTR(java.lang.String[] ATTR) {
        this.ATTR = ATTR;
    }


    /**
     * Gets the ID value for this RxNode.
     * 
     * @return ID
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this RxNode.
     * 
     * @param ID
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the STR value for this RxNode.
     * 
     * @return STR
     */
    public java.lang.String getSTR() {
        return STR;
    }


    /**
     * Sets the STR value for this RxNode.
     * 
     * @param STR
     */
    public void setSTR(java.lang.String STR) {
        this.STR = STR;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxNode)) return false;
        RxNode other = (RxNode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ATTR==null && other.getATTR()==null) || 
             (this.ATTR!=null &&
              java.util.Arrays.equals(this.ATTR, other.getATTR()))) &&
            ((this.ID==null && other.getID()==null) || 
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            ((this.STR==null && other.getSTR()==null) || 
             (this.STR!=null &&
              this.STR.equals(other.getSTR())));
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
        if (getATTR() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getATTR());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getATTR(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getID() != null) {
            _hashCode += getID().hashCode();
        }
        if (getSTR() != null) {
            _hashCode += getSTR().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxNode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxNode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ATTR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ATTR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STR"));
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
