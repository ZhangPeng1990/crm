package uk.co.quidos.gdsap.evaluation.services.impl;


import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import uk.co.quidos.common.util.xml.XSDErrorVO;
import uk.co.quidos.common.util.xml.XmlUtil;

/**
 * 根据xsd批量验证xml是否合格
 * @author ZP
 *
 */
public class XmlValidation {

	//xsd文件的绝对路径
	private static final String xsdPath = "C:\\Users\\tyr\\Desktop\\work\\ECO\\ECO-Schema_2-0\\ECO-Report.xsd";
	//xml文件所在文件夹的绝对路径
	private static final String xmlDirPath = "D:\\data\\report\\eco\\";
	
	public static void main(String[] args) {
		valiadtion();
	}
	
	private static void valiadtion()
	{
		File folder = new File(xmlDirPath);
		if(folder.exists() && folder.isDirectory())
		{
			StringBuffer sb = new StringBuffer();
			File[] xmls = folder.listFiles();
			int errorNum = 0;
			for(File xml : xmls)
			{
				Document doc = getDocumentByURI(xml.getPath());
				List<XSDErrorVO> errorList = XmlUtil.validateXmlByXsd(xsdPath, doc);
				if (errorList != null && errorList.size() > 0){
					String message = "";
					for (XSDErrorVO error : errorList) {
						message += "Line " + error.getLine() + ":" + error.getErrorMsg();
					}
					sb.append("------------------------------------------" + xml.getName() + "验证失败------------------------------------------").append("\r\n");
					sb.append(message).append("\r\n").append("\r\n");
					errorNum++;
				}
			}
			if(errorNum > 0)
			{
				System.out.println("验证未通过的xml共" + errorNum + "份");
				System.out.println(sb.toString());
			}
			else
			{
				System.out.println("验证全部通过！");
			}
		}	
	}
	
	private static Document getDocumentByURI(String path){
		SAXReader reader = new SAXReader();
        Document document = null;
		try {
			document = reader.read(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return document;
	}
}
