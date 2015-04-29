/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.dom4j.DocumentException;

import uk.co.quidos.gdsap.evaluation.utils.SCTWebserviceUtil;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;

/**
 * @author peng.shi@argylesoftware.co.uk
 */
public class MainApp {
	
	private static final String OA_RRN = "4317-8353-8100-0627-3992" ;
	private static final String USERNAME = "Mahmood Ullah";
	private static final String PASSWORD = "I)(?smpY^vrF";
	private static final String ASSESSOR_ORGANISATIONID = "QUIA00033";
	private static final String ADVISERID = "QUID300230";
	
	public static void main(String[] args) throws DocumentException {
		
		SCTWebserviceUtil.getOAPdf(GlobalConfig.getInstance().getGetOAPDFWebserviceAddressOfSCT(OA_RRN), GlobalConfig.getInstance().getCertOfSCT(), GlobalConfig.getInstance().getCertPasswordOfSCT(), 
				USERNAME, PASSWORD, ASSESSOR_ORGANISATIONID, ADVISERID, 
				"d:\1.pdf");
		
	}
}
