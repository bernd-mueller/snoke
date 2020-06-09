/**
 * DBManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.nih.nlm.mor.axis.services.RxNormDBService;

public class DBManagerServiceLocator extends org.apache.axis.client.Service implements gov.nih.nlm.mor.axis.services.RxNormDBService.DBManagerService {

    public DBManagerServiceLocator() {
    }


    public DBManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DBManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RxNormDBService
    private java.lang.String RxNormDBService_address = "https://mor.nlm.nih.gov/axis/services/RxNormDBService";

    public java.lang.String getRxNormDBServiceAddress() {
        return RxNormDBService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RxNormDBServiceWSDDServiceName = "RxNormDBService";

    public java.lang.String getRxNormDBServiceWSDDServiceName() {
        return RxNormDBServiceWSDDServiceName;
    }

    public void setRxNormDBServiceWSDDServiceName(java.lang.String name) {
        RxNormDBServiceWSDDServiceName = name;
    }

    public gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager getRxNormDBService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RxNormDBService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRxNormDBService(endpoint);
    }

    public gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager getRxNormDBService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nlm.mor.axis.services.RxNormDBService.RxNormDBServiceSoapBindingStub _stub = new gov.nih.nlm.mor.axis.services.RxNormDBService.RxNormDBServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRxNormDBServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRxNormDBServiceEndpointAddress(java.lang.String address) {
        RxNormDBService_address = address;
    }

    /*
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nlm.mor.axis.services.RxNormDBService.RxNormDBServiceSoapBindingStub _stub = new gov.nih.nlm.mor.axis.services.RxNormDBService.RxNormDBServiceSoapBindingStub(new java.net.URL(RxNormDBService_address), this);
                _stub.setPortName(getRxNormDBServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /*
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RxNormDBService".equals(inputPortName)) {
            return getRxNormDBService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mor.nlm.nih.gov/axis/services/RxNormDBService", "DBManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mor.nlm.nih.gov/axis/services/RxNormDBService", "RxNormDBService"));
        }
        return ports.iterator();
    }

    /*
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RxNormDBService".equals(portName)) {
            setRxNormDBServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /*
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
