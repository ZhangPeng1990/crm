package uk.co.quidos.gdsap.evaluation.utils;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.quidos.common.util.DateUtil;

public class RRNBuilder {

	private static Log log = LogFactory.getLog(RRNBuilder.class);

	public static enum NdReportType {
		DEC(1), AR(2), EPC(3), RR(4), ACR(5);
		private final int code;

		NdReportType(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}

	public static enum DReportType {
		HCR(1), RdSAP(2), SAP(3),GDOA_TYPE(5), GDIP_TYPE(154);
		private final int code;

		DReportType(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}

	private static String datePattern = "yyMMdd";
	private static String datePattern2 = "yyMMdd HH:mm:ss.SSS";
	private static int modulus = 7;

	private static int[] sort04 = new int[] { 5, 19, 11, 7, 8, 17, 15, 13, 9,
			4, 18, 16, 6, 12, 1, 3, 2, 14, 10 };
	private static int[] sort15 = new int[] { 2, 4, 12, 10, 18, 14, 7, 3, 9, 6,
			13, 16, 1, 11, 15, 8, 19, 17, 5 };
	private static int[] sort26 = new int[] { 3, 13, 11, 10, 17, 6, 14, 9, 5,
			1, 18, 4, 12, 8, 19, 16, 15, 2, 7 };
	private static int[] sort3 = new int[] { 17, 4, 9, 14, 2, 6, 10, 8, 16, 1,
			12, 19, 3, 18, 15, 7, 5, 13, 11 };

	private static String convertAllIntegerArray(List<Integer> allInteger) {
		int allCount = 0;
		for (int i = 0; i < allInteger.size(); i++) {
			allCount = allCount + allInteger.get(i);
		}

		int checkSum = allCount % modulus;
		allInteger.add(checkSum);

		Integer[] rrnIntegerArray = new Integer[20];
		int[] tmpSort = null;
		if (checkSum == 0 || checkSum == 4) {
			tmpSort = sort04;
		} else if (checkSum == 1 || checkSum == 5) {
			tmpSort = sort15;
		} else if (checkSum == 2 || checkSum == 6) {
			tmpSort = sort26;
		} else if (checkSum == 3) {
			tmpSort = sort3;
		}

		for (int i = 0; i < tmpSort.length; i++) {
			int pos = tmpSort[i] - 1;
			rrnIntegerArray[pos] = allInteger.get(i);
		}
		rrnIntegerArray[19] = checkSum;
		StringBuffer rrn = new StringBuffer();
		for (int i = 0; i < rrnIntegerArray.length; i++) {
			rrn.append(rrnIntegerArray[i]);
			if (i % 4 == 3 && i < rrnIntegerArray.length - 1) {
				rrn.append("-");
			}
		}

		return rrn.toString();
	}

	private static int[] convertInspectionDateArray(Date inspectionDate) {

		int[] inspectionDateArray = new int[6];
		String strInspectionDate = DateUtil.date2String(inspectionDate,
				datePattern);
		for (int i = 0; i < strInspectionDate.length(); i++) {
			inspectionDateArray[i] = Integer.parseInt(String
					.valueOf(strInspectionDate.charAt(i)));
		}

		for (int i = 0; i < inspectionDateArray.length; i++) {
			int tmpI = inspectionDateArray[i];
			int tmp = tmpI % 2;
			if (tmp != 0) {
				tmpI = 10 - tmpI;
				inspectionDateArray[i] = tmpI;
			}
		}

		return inspectionDateArray;
	}


	public static String buildNdRrn(NdReportType ndReportType, String uprn,
			Date inspectionDate){

		if (uprn == null || uprn.length() != 12) {
			log.error("RRN Condition UPRN Illegal");
			throw new IllegalArgumentException("RRN Condition UPRN Illegal");
		}
		if (inspectionDate == null) {
			log.error("RRN Condition Inpsection Date Illegal");
			throw new IllegalArgumentException("RRN Condition Inpsection Date Illegal");
		}

		int[] inspectionDateArray = convertInspectionDateArray(inspectionDate);

		// uprn Convert
		List<Integer> allInteger = Collections
				.synchronizedList(new LinkedList<Integer>());
		for (int i = 0; i < uprn.length(); i++) {
			allInteger.add(Integer.parseInt(String.valueOf(uprn.charAt(i))));
		}

		for (int i = 0; i < inspectionDateArray.length; i++) {
			allInteger.add(inspectionDateArray[i]);
		}

		allInteger.add(ndReportType.getCode());
	
		return convertAllIntegerArray(allInteger);
	}

	private static String buildGDIP_RRN(String[] gdip_element)
	{
		StringBuffer sb = new StringBuffer();
		if(gdip_element == null || gdip_element.length != 5)
		{
			throw new IllegalArgumentException("gdip_element Illegal");
		}
		int time = 0;
		for(String i : gdip_element)
		{
			if(time < 4)
			{
				sb.append(i).append("-");
			}
			else
			{
				sb.append(i);
			}
			time ++;
		}
		return sb.toString();
	}
	
	public static String buildDRrn(DReportType dReportType, String uprn,
			Date inspectionDate)  {

		if (inspectionDate == null) {
			log.error("RRN Condition Inpsection Date Illegal");
			throw new IllegalArgumentException("RRN Condition Inpsection Date Illegal");
		}
		
		if(DReportType.GDIP_TYPE.equals(dReportType))
		{
			String[] gdip_element = new String[5];
			String softwareVendorCode = String.valueOf(dReportType.getCode());//uniquely assigned by DECC (via BRE) to the software vendor – Pattern: [0-9]{3}
			gdip_element[0] = softwareVendorCode;
			String strInspectionDate = DateUtil.date2String(inspectionDate, datePattern2);
			String[] temp = strInspectionDate.split(" ");
			if(temp != null && temp.length == 2)
			{
				String hhmmss_sss = temp[1];
				String hh = hhmmss_sss.split(":")[0];
				String mm_t = hhmmss_sss.split(":")[1];
				gdip_element[1] = hh + mm_t;
				String ss = hhmmss_sss.split(":")[2].split("\\.")[0];
				String sss = hhmmss_sss.split(":")[2].split("\\.")[1];
				gdip_element[2] = ss + sss;
				
				String yymmdd = temp[0];
				String yy = yymmdd.substring(0, 2);
				String mm = yymmdd.substring(2, 4);
				String dd = yymmdd.substring(4, 6);
				gdip_element[3] = dd + mm;
				String random = String.valueOf(NumberUtils.random(0,9)) + String.valueOf(NumberUtils.random(0,9));
				gdip_element[4] = yy + random;
				return buildGDIP_RRN(gdip_element);
			}
			return null;
		}
		else
		{
			if (uprn == null || uprn.length() != 10) {
				log.error("RRN Condition UPRN Illegal");
				throw new IllegalArgumentException("RRN Condition UPRN Illegal");
			}

			int[] inspectionDateArray = convertInspectionDateArray(inspectionDate);

			// uprn Convert
			List<Integer> allInteger = Collections
					.synchronizedList(new LinkedList<Integer>());
			for (int i = 0; i < uprn.length(); i++) {
				allInteger.add(Integer.parseInt(String.valueOf(uprn.charAt(i))));
			}

			for (int i = 0; i < inspectionDateArray.length; i++) {
				allInteger.add(inspectionDateArray[i]);
			}

			// random 2 int
			int i1 = NumberUtils.random(0,9);
			int i2 = NumberUtils.random(0,9);
			System.out.println("i1 = " + i1 + " i2=" + i2);
			allInteger.add(i1);
			allInteger.add(i2);
			//int random2 =  Calendar.getInstance().get(Calendar.YEAR)%100;
			//allInteger.add(random2/10);
			//allInteger.add(random2%10);
			
			allInteger.add(dReportType.getCode());
		
			return convertAllIntegerArray(allInteger);
		}
	}

	public static void main(String[] args) {

//		for (int i = 1; i <= 30; i++) {
//			String str = "0901";
//			if (i <= 9) {
//				str = str + "0" + i;
//			} else {
//				str = str + i;
//			}
//			System.out.println(buildNdRrn(NdReportType.ACR,
//					"862589250000", DateUtil.string2Date(str, "yyMMdd")));
//		}
//		
//		System.out.println("=================================");
//		
//		for (int i = 1; i <= 30; i++) {
//			System.out.println(buildDRrn(DReportType.RdSAP,
//					"8625892500", new Date()));
//		}
		//0212-9623-8430-2713-5996
		//0212-9653-8430-2713-5992
		String uprn = "1234567890";
		Date d = new Date();
//		Date iDate = DateUtil.string2Date(d.toString(), "yyyy-MM-dd hh:mm:ss SSS");
		for (int i=0;i<1000;i++) {
			System.out.println(buildDRrn(DReportType.GDIP_TYPE, uprn, d));
		}
	}
}
