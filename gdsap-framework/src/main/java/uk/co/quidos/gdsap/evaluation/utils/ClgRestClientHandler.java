package uk.co.quidos.gdsap.evaluation.utils;


public class ClgRestClientHandler {
//	private static enum ClgType {DEW, DNI, DSC, NDEW, NDNI, NDSC};
//	
//	private static Configuration config;
//	private String certPath;
//	private String certPwd;
//	private String suffix;
//	
//	private ClgType type;
//	
//	private ClgRestClientHandler(String certPrefix, ClgType type){
//		if (config == null) {
//			config = BusinessFactory.getFactory().retrieveConfiguration();
//		}
//		String env = config.getString("app.clgwsc.env");
//		String certDir = config.getString("app.clgwsc.cert.path");
//
//		this.suffix = certPrefix + "_" + type + "_" + env;
//		this.certPath = certDir+"/"+config.getString("app.clgwsc.cert.name."+suffix);
//		this.certPwd = config.getString("app.clgwsc.cert.pwd."+suffix);
//		this.type = type;
//	}
//	
//	public static ClgRestClientHandler getHandlerDEW(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.DEW);
//	}
//	public static ClgRestClientHandler getHandlerDNI(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.DNI);
//	}
//	public static ClgRestClientHandler getHandlerDSC(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.DSC);
//	}
//	public static ClgRestClientHandler getHandlerNDEW(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.NDEW);
//	}
//	public static ClgRestClientHandler getHandlerNDNI(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.NDNI);
//	}
//	public static ClgRestClientHandler getHandlerNDSC(String certPrefix){
//		return new ClgRestClientHandler(certPrefix, ClgType.NDSC);
//	}
//	//
//	public String uploadEas(String xmlData) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.uploadEas."+suffix);
//		
//		if (ClgType.DSC.equals(type) || ClgType.NDSC.equals(type)) {
//			return ClgRestClientUtil.put(url, certPath, certPwd, xmlData);
//		}
//		return ClgRestClientUtil.post(url, certPath, certPwd, xmlData);
//	}
//	
//	//SCT Assessors，Advisers 分别是2个不同的webservice，但不区分Domestic，Non-Domestic 
//	public String uploadSctAdvisers(String xmlData) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.uploadAdvisers."+suffix);
//		
//		if (ClgType.DSC.equals(type)) {
//			return ClgRestClientUtil.put(url, certPath, certPwd, xmlData);
//		}
//
//		throw new ClgLodgeException("System error.");
//	}
//	
//	public void pdfCertGen(String eaCertNo, String rrn, String xmlData, String pdfSavePath) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.draftReport."+suffix).replaceAll("\\{RRN\\}",rrn);
//		if (ClgType.DSC.equals(type) || ClgType.NDSC.equals(type)) {
//			ClgRestClientUtil.putPdfCertGen(url, eaCertNo, certPath, certPwd, xmlData, pdfSavePath);
//		} else {
//			ClgRestClientUtil.postPdfCertGen(url, certPath, certPwd, xmlData, pdfSavePath);
//		}
//	}
//	
//	public String lodgeReport(String eaCertNo, String rrn, String xmlData) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.lodgeReport."+suffix).replaceAll("\\{RRN\\}",rrn);
//		if (ClgType.DSC.equals(type) || ClgType.NDSC.equals(type)) {
//			return ClgRestClientUtil.put(url, eaCertNo, certPath, certPwd, xmlData);
//		}
//		return ClgRestClientUtil.post(url, certPath, certPwd, xmlData);
//	}
//	
//	public String cancelReport(String eaCertNo, String rrn, String xmlData) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.cancelReport."+suffix).replaceAll("\\{RRN\\}",rrn);
//		if (ClgType.DSC.equals(type) || ClgType.NDSC.equals(type)) {
//			return ClgRestClientUtil.put(url, eaCertNo, certPath, certPwd, xmlData);
//		}
//		return ClgRestClientUtil.post(url, certPath, certPwd, xmlData);
//	}
//	
//	public String address(String xmlData) throws ClgLodgeException{
//		String url = config.getString("app.clgwsc.cert.address."+suffix);
//		if (ClgType.DSC.equals(type) || ClgType.NDSC.equals(type)) {
//			return ClgRestClientUtil.put(url, certPath, certPwd, xmlData);
//		}
//		return ClgRestClientUtil.post(url, certPath, certPwd, xmlData);
//	}
}
