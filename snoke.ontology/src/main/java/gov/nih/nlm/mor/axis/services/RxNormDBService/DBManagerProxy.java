package gov.nih.nlm.mor.axis.services.RxNormDBService;

public class DBManagerProxy implements gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager {
  private String _endpoint = null;
  private gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager dBManager = null;
  
  public DBManagerProxy() {
    _initDBManagerProxy();
  }
  
  public DBManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initDBManagerProxy();
  }
  
  private void _initDBManagerProxy() {
    try {
      dBManager = (new gov.nih.nlm.mor.axis.services.RxNormDBService.DBManagerServiceLocator()).getRxNormDBService();
      if (dBManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dBManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dBManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dBManager != null)
      ((javax.xml.rpc.Stub)dBManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager getDBManager() {
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager;
  }
  
  public java.lang.String[] findRxcuiByString(java.lang.String term) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRxcuiByString(term);
  }
  
  public java.lang.String[] findRxcuiByString(java.lang.String term, java.lang.String[] sources, int allSourcesFlag) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRxcuiByString(term, sources, allSourcesFlag);
  }
  
  public java.lang.String[] findRxcuiByString(java.lang.String term, java.lang.String[] sources, int allSourcesFlag, int matchType) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRxcuiByString(term, sources, allSourcesFlag, matchType);
  }
  
  public java.lang.String[] findRxcuiById(java.lang.String idType, java.lang.String id) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRxcuiById(idType, id);
  }
  
  public java.lang.String[] findRxcuiById(java.lang.String idType, java.lang.String id, int allSourcesFlag) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRxcuiById(idType, id, allSourcesFlag);
  }
  
  public java.lang.String[] getSpellingSuggestions(java.lang.String term) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getSpellingSuggestions(term);
  }
  
  public BeanService.RxConcept getRxConceptProperties(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRxConceptProperties(rxcui);
  }
  
  public BeanService.RxConceptGroup[] getRelatedByRelationship(java.lang.String rxcui, java.lang.String[] rela_list) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRelatedByRelationship(rxcui, rela_list);
  }
  
  public BeanService.RxConceptGroup[] getRelatedByType(java.lang.String rxcui, java.lang.String[] termType_list) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRelatedByType(rxcui, termType_list);
  }
  
  public BeanService.RxConceptGroup[] getAllRelatedInfo(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllRelatedInfo(rxcui);
  }
  
  public BeanService.RxConceptGroup[] getDrugs(java.lang.String name) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getDrugs(name);
  }
  
  public java.lang.String[] getNDCs(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getNDCs(rxcui);
  }
  
  public BeanService.NDCTime[] getAllNDCs(java.lang.String rxcui, int allNDC) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllNDCs(rxcui, allNDC);
  }
  
  public BeanService.HistoricalNDCTime[] getAllHistoricalNDCs(java.lang.String rxcui, int allNDC) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllHistoricalNDCs(rxcui, allNDC);
  }
  
  public java.lang.String getRxNormVersion() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRxNormVersion();
  }
  
  public java.lang.String[] getIdTypes() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getIdTypes();
  }
  
  public java.lang.String[] getRelaTypes() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRelaTypes();
  }
  
  public java.lang.String[] getSourceTypes() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getSourceTypes();
  }
  
  public java.lang.String[] getTermTypes() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getTermTypes();
  }
  
  public BeanService.RxTermInfo[] getProprietaryInformation(java.lang.String rxcui, java.lang.String[] sources, java.lang.String proxyTicket) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getProprietaryInformation(rxcui, sources, proxyTicket);
  }
  
  public BeanService.RxTermInfo[] getProprietaryInformation(java.lang.String rxcui, java.lang.String[] sources, java.lang.String proxyTicket, java.lang.String rxaui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getProprietaryInformation(rxcui, sources, proxyTicket, rxaui);
  }
  
  public BeanService.RxConcept[] getMultiIngredBrand(java.lang.String[] ids) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getMultiIngredBrand(ids);
  }
  
  public java.lang.String[] getDisplayTerms() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getDisplayTerms();
  }
  
  public java.lang.String[] findRemapped(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.findRemapped(rxcui);
  }
  
  public BeanService.RxMinimalConcept[] getAllConceptsByTTY(java.lang.String[] ttyList) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllConceptsByTTY(ttyList);
  }
  
  public java.lang.String[] getPropCategories() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getPropCategories();
  }
  
  public java.lang.String[] getPropNames() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getPropNames();
  }
  
  public BeanService.RxPropertyConcept[] getAllProperties(java.lang.String rxcui, java.lang.String[] propCat) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllProperties(rxcui, propCat);
  }
  
  public java.lang.String filterByProperty(java.lang.String rxcui, java.lang.String prop_name, java.lang.String[] prop_value) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.filterByProperty(rxcui, prop_name, prop_value);
  }
  
  public BeanService.RxPropertyConcept[] getRxProperty(java.lang.String rxcui, java.lang.String prop_name) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRxProperty(rxcui, prop_name);
  }
  
  public BeanService.NDCStatus getNDCStatus(java.lang.String ndc, java.lang.String startDate, java.lang.String endDate, int option) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getNDCStatus(ndc, startDate, endDate, option);
  }
  
  public BeanService.RxcuiStatus getRxcuiStatus(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getRxcuiStatus(rxcui);
  }
  
  public BeanService.ReverseRxcuiStatus getReverseRxcuiStatus(java.lang.String rxcui) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getReverseRxcuiStatus(rxcui);
  }
  
  public BeanService.RxMatchGroup getApproximateMatch(java.lang.String term, int ranks, int options) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getApproximateMatch(term, ranks, options);
  }
  
  public BeanService.RxNode[] getAllClasses(java.lang.String src, java.lang.String type) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getAllClasses(src, type);
  }
  
  public BeanService.RxTree getClassHierarchy(java.lang.String rxcui, java.lang.String src, java.lang.String type, int oneLevel) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getClassHierarchy(rxcui, src, type, oneLevel);
  }
  
  public BeanService.RxDrugMember[] getClassMembers(java.lang.String id, java.lang.String src, java.lang.String rela, java.lang.String trans, java.lang.String[] ttyfilter) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getClassMembers(id, src, rela, trans, ttyfilter);
  }
  
  public BeanService.NDCProperty[] getNDCProperties(java.lang.String id) throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    return dBManager.getNDCProperties(id);
  }
  
  public void close() throws java.rmi.RemoteException{
    if (dBManager == null)
      _initDBManagerProxy();
    dBManager.close();
  }
  
  
}