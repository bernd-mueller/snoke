/**
 * RxTree.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class RxTree  implements java.io.Serializable {
    private BeanService.RxEdge[] rxEdge;

    private BeanService.RxNode[] rxNode;

    private java.lang.String title;

    public RxTree() {
    }

    public RxTree(
           BeanService.RxEdge[] rxEdge,
           BeanService.RxNode[] rxNode,
           java.lang.String title) {
           this.rxEdge = rxEdge;
           this.rxNode = rxNode;
           this.title = title;
    }


    /**
     * Gets the rxEdge value for this RxTree.
     * 
     * @return rxEdge
     */
    public BeanService.RxEdge[] getRxEdge() {
        return rxEdge;
    }


    /**
     * Sets the rxEdge value for this RxTree.
     * 
     * @param rxEdge
     */
    public void setRxEdge(BeanService.RxEdge[] rxEdge) {
        this.rxEdge = rxEdge;
    }


    /**
     * Gets the rxNode value for this RxTree.
     * 
     * @return rxNode
     */
    public BeanService.RxNode[] getRxNode() {
        return rxNode;
    }


    /**
     * Sets the rxNode value for this RxTree.
     * 
     * @param rxNode
     */
    public void setRxNode(BeanService.RxNode[] rxNode) {
        this.rxNode = rxNode;
    }


    /**
     * Gets the title value for this RxTree.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this RxTree.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RxTree)) return false;
        RxTree other = (RxTree) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rxEdge==null && other.getRxEdge()==null) || 
             (this.rxEdge!=null &&
              java.util.Arrays.equals(this.rxEdge, other.getRxEdge()))) &&
            ((this.rxNode==null && other.getRxNode()==null) || 
             (this.rxNode!=null &&
              java.util.Arrays.equals(this.rxNode, other.getRxNode()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        if (getRxEdge() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRxEdge());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRxEdge(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRxNode() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRxNode());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRxNode(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RxTree.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxTree"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxEdge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxEdge"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxEdge"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxNode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxNode"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "RxNode"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
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
