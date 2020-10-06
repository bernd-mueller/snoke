/**
 * NDCProperty.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package BeanService;

public class NDCProperty  implements java.io.Serializable {
    private java.lang.String ndc;

    private java.lang.String ndc10;

    private java.lang.String ndc9;

    private java.lang.String[] packaging;

    private BeanService.PropertyConcept[] properties;

    private java.lang.String rxcui;

    private java.lang.String splSetId;

    public NDCProperty() {
    }

    public NDCProperty(
           java.lang.String ndc,
           java.lang.String ndc10,
           java.lang.String ndc9,
           java.lang.String[] packaging,
           BeanService.PropertyConcept[] properties,
           java.lang.String rxcui,
           java.lang.String splSetId) {
           this.ndc = ndc;
           this.ndc10 = ndc10;
           this.ndc9 = ndc9;
           this.packaging = packaging;
           this.properties = properties;
           this.rxcui = rxcui;
           this.splSetId = splSetId;
    }


    /**
     * Gets the ndc value for this NDCProperty.
     * 
     * @return ndc
     */
    public java.lang.String getNdc() {
        return ndc;
    }


    /**
     * Sets the ndc value for this NDCProperty.
     * 
     * @param ndc
     */
    public void setNdc(java.lang.String ndc) {
        this.ndc = ndc;
    }


    /**
     * Gets the ndc10 value for this NDCProperty.
     * 
     * @return ndc10
     */
    public java.lang.String getNdc10() {
        return ndc10;
    }


    /**
     * Sets the ndc10 value for this NDCProperty.
     * 
     * @param ndc10
     */
    public void setNdc10(java.lang.String ndc10) {
        this.ndc10 = ndc10;
    }


    /**
     * Gets the ndc9 value for this NDCProperty.
     * 
     * @return ndc9
     */
    public java.lang.String getNdc9() {
        return ndc9;
    }


    /**
     * Sets the ndc9 value for this NDCProperty.
     * 
     * @param ndc9
     */
    public void setNdc9(java.lang.String ndc9) {
        this.ndc9 = ndc9;
    }


    /**
     * Gets the packaging value for this NDCProperty.
     * 
     * @return packaging
     */
    public java.lang.String[] getPackaging() {
        return packaging;
    }


    /**
     * Sets the packaging value for this NDCProperty.
     * 
     * @param packaging
     */
    public void setPackaging(java.lang.String[] packaging) {
        this.packaging = packaging;
    }


    /**
     * Gets the properties value for this NDCProperty.
     * 
     * @return properties
     */
    public BeanService.PropertyConcept[] getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this NDCProperty.
     * 
     * @param properties
     */
    public void setProperties(BeanService.PropertyConcept[] properties) {
        this.properties = properties;
    }


    /**
     * Gets the rxcui value for this NDCProperty.
     * 
     * @return rxcui
     */
    public java.lang.String getRxcui() {
        return rxcui;
    }


    /**
     * Sets the rxcui value for this NDCProperty.
     * 
     * @param rxcui
     */
    public void setRxcui(java.lang.String rxcui) {
        this.rxcui = rxcui;
    }


    /**
     * Gets the splSetId value for this NDCProperty.
     * 
     * @return splSetId
     */
    public java.lang.String getSplSetId() {
        return splSetId;
    }


    /**
     * Sets the splSetId value for this NDCProperty.
     * 
     * @param splSetId
     */
    public void setSplSetId(java.lang.String splSetId) {
        this.splSetId = splSetId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NDCProperty)) return false;
        NDCProperty other = (NDCProperty) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ndc==null && other.getNdc()==null) || 
             (this.ndc!=null &&
              this.ndc.equals(other.getNdc()))) &&
            ((this.ndc10==null && other.getNdc10()==null) || 
             (this.ndc10!=null &&
              this.ndc10.equals(other.getNdc10()))) &&
            ((this.ndc9==null && other.getNdc9()==null) || 
             (this.ndc9!=null &&
              this.ndc9.equals(other.getNdc9()))) &&
            ((this.packaging==null && other.getPackaging()==null) || 
             (this.packaging!=null &&
              java.util.Arrays.equals(this.packaging, other.getPackaging()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              java.util.Arrays.equals(this.properties, other.getProperties()))) &&
            ((this.rxcui==null && other.getRxcui()==null) || 
             (this.rxcui!=null &&
              this.rxcui.equals(other.getRxcui()))) &&
            ((this.splSetId==null && other.getSplSetId()==null) || 
             (this.splSetId!=null &&
              this.splSetId.equals(other.getSplSetId())));
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
        if (getNdc() != null) {
            _hashCode += getNdc().hashCode();
        }
        if (getNdc10() != null) {
            _hashCode += getNdc10().hashCode();
        }
        if (getNdc9() != null) {
            _hashCode += getNdc9().hashCode();
        }
        if (getPackaging() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPackaging());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPackaging(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRxcui() != null) {
            _hashCode += getRxcui().hashCode();
        }
        if (getSplSetId() != null) {
            _hashCode += getSplSetId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NDCProperty.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "NDCProperty"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ndc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ndc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ndc10");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ndc10"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ndc9");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ndc9"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packaging");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packaging"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:BeanService", "PropertyConcept"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rxcui");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rxcui"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("splSetId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "splSetId"));
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
