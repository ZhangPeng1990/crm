/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author shipeng
 */
public class DocumentUtil {

	/**
	 * Reader document from xml file
	 * @param file
	 * @return
	 */
	public static Document readDocument(File file) {
		Document doc = null;
		
		try {
			InputStream is = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("utf-8");
			doc = saxReader.read(is);
		} catch (DocumentException e) {
			try {
				InputStream is = new FileInputStream(file);
				SAXReader saxReader = new SAXReader();
				saxReader.setEncoding("UTF-16");
				doc = saxReader.read(is);
			} catch (DocumentException e1) {
				try {
					InputStream is = new FileInputStream(file);
					SAXReader saxReader = new SAXReader();
					saxReader.setEncoding("UTF-16LE");
					doc = saxReader.read(is);
				} catch (DocumentException e2) {
					throw new IllegalArgumentException(e1);
				} catch (FileNotFoundException e2) {
					throw new IllegalArgumentException(e);
				}
			} catch (FileNotFoundException e1) {
				throw new IllegalArgumentException(e);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return doc;
	}
	
	/**
	 * Reader document from xml content
	 * @param xmlContent
	 * @return
	 */
	public static Document readDocument(String xmlContent) {
		Document doc = null;
		try {
			InputStream is = new ByteArrayInputStream(xmlContent.getBytes());
			//StringReader stringReader = new StringReader(xmlContent);
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("utf-8");
			doc = saxReader.read(is);
		} catch (DocumentException e) {
			try {
				StringReader stringReader = new StringReader(xmlContent);
				SAXReader saxReader = new SAXReader();
				saxReader.setEncoding("UTF-16");
				doc = saxReader.read(stringReader);
			} catch (DocumentException e1) {
				throw new IllegalArgumentException(e1);
			}
		} 
		return doc;
	}
	
	/**
	 * 把String类型的xml转换成Document对象
	 * @param xml
	 * @return
	 * @throws DocumentException 
	 */
	public static Document stringToDocument(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
		return document;
	}
	
	/**
	 * 把Document对象转换成字符串
	 * @param doc
	 * @return
	 */
	public static String documentToString(Document doc){
		if(doc != null){
			return doc.asXML();
		}
		return null;
	}
	
	/**
	 * 把Document对象写进系统文件的xml文件中
	 * @param document
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static File writeDocToXMLFile(Document document, String uri, boolean isFormat) throws IOException{
		XMLWriter writer;
		
		if(isFormat){
			
			//通过 org.dom4j.io.OutputFormat 来设置XML文档输出格式
			OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
//			format.setEncoding("UTF-8"); //设置XML文档的编码类型
//			format.setSuppressDeclaration(true);
//			format.setIndent(true); //设置是否缩进
//			format.setIndent(" "); //以空格方式实现缩进
//			format.setNewlines(true); //设置是否换行
			writer = new XMLWriter(new FileWriter(uri),format);
		}else{
			writer = new XMLWriter(new FileWriter(uri));
		}
		
		// lets write to a file
        writer.write(document);
        writer.close();
        
        File file = new File(uri);
		return file;
	}
	
	public static void main(String[] args) {
		Document doc = readDocument(new File("D:/qube-workspace/argylesoftware-qube-reportdata/document/OA-1_EPC.xml"));
		System.out.println(doc.getRootElement().getName());
	}
}
