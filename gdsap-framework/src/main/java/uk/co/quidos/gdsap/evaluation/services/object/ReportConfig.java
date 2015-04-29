package uk.co.quidos.gdsap.evaluation.services.object;

public class ReportConfig {

	public static final String[] EAWRootElements = {"ConditionReportCreateRequest_1","ConditionReportRetrieveResponse_1"};
	public static final String[] SCTRootElements = {"Energy-Performance-Certificate"};
	public static final String[] NewEpcRootElements = {"RdSAP-Report"};
	
	public static final String[] prefixs = {"xsi","HIP","SAP","SAP09","CS","ERR","pfdt","bdt","SAP05"};
	
	public static boolean isExist(String[] arg, String vale){
		if(arg == null || arg.length < 1){
			return false;
		}
		
		for(String temp : arg){
			if(temp.equals(vale)){
				return true;
			}
		}
		
		return false;
	}
}
