package uk.co.quidos.common.util.xml;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;

import uk.co.quidos.gdsap.evaluation.utils.FileUtil;


public class XmlUtil {
	public static String changtToUtf8Header(String xml) {
		return xml.replaceFirst("encoding=\"(UTF-16|utf-16)\"", "encoding=\"UTF-8\"");
	}
	
	public static List<XSDErrorVO> validateXmlByXsd(String xsdFilePath,
			String xmlFilePath) {
		SAXReader xmlReader = new SAXReader();
		try {
			Document xmlDocument = (Document) xmlReader.read(new File(
					xmlFilePath));
			return validateXmlByXsd(xsdFilePath, xmlDocument);
		} catch (Exception ex) {
			System.out.println("XML文件: " + xmlFilePath + " 通过XSD文件:"
					+ xsdFilePath + "检验失败。\n原因： " + ex.getMessage());
			ex.printStackTrace();
			return new ArrayList<XSDErrorVO>();
		}
	}

	public static List<XSDErrorVO> validateXmlByXsd(String xsdFilePath, Document xmlDocument) {
		List<XSDErrorVO> xsdErrorVOList = new ArrayList<XSDErrorVO>();

		try {
			// 创建默认的XML错误处理器
			XMLErrorHandler errorHandler = new XMLErrorHandler();

			// 获取基于 SAX 的解析器的实例
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 解析器在解析时验证 XML 内容。
			factory.setValidating(true);
			// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
			factory.setNamespaceAware(true);
			// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
			SAXParser parser = factory.newSAXParser();
			// 创建一个读取工具
			
			// 设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在
			// [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
			parser.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
			parser.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaSource",
					new File(xsdFilePath).toURI().toString());
			// 创建一个SAXValidator校验工具，并设置校验工具的属性
			SAXValidator validator = new SAXValidator(parser.getXMLReader());
			// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
			validator.setErrorHandler(errorHandler);

			// 校验
			validator.validate(xmlDocument);

			if (errorHandler.getErrors().hasContent()) {
				// 验证失败
				String strErrors = errorHandler.getErrors().asXML();
				Document errorDocument = DocumentHelper.parseText(strErrors);
				xsdErrorVOList = getErrors(errorDocument);
			}
			return xsdErrorVOList;
				
		} catch (Exception ex) {
			return xsdErrorVOList;
		}
	}
	
	//validateXmlByXsd 、validateXmlByXsd2 两种验证方式都可以
	public static List<XSDErrorVO> validateXmlByXsd2(String xsdPath, String xmlPath) {
		List<XSDErrorVO> xsdErrorVOList = new ArrayList<XSDErrorVO>();

		try {
			// 创建默认的XML错误处理器
			XMLErrorHandler errorHandler = new XMLErrorHandler();

			// 1. Lookup a factory for the W3C XML Schema language
	        SchemaFactory factory = 
	            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	        
	        // 2. Compile the schema. 
	        // Here the schema is loaded from a java.io.File, but you could use 
	        // a java.net.URL or a javax.xml.transform.Source instead.
	        File schemaLocation = new File(xsdPath);
	        Schema schema = factory.newSchema(schemaLocation);
	    
	        // 3. Get a validator from the schema.
	        Validator validator = schema.newValidator();
	        
	        // 4. Parse the document you want to check.
	        Source source = new StreamSource(xmlPath);

	        // 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
			validator.setErrorHandler(errorHandler);
			
			// 校验
			validator.validate(source);

			if (errorHandler.getErrors().hasContent()) {
				// 验证失败
				String strErrors = errorHandler.getErrors().asXML();
				Document errorDocument = DocumentHelper.parseText(strErrors);
				xsdErrorVOList = getErrors(errorDocument);
			}
			return xsdErrorVOList;
				
		} catch (Exception ex) {
			return xsdErrorVOList;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<XSDErrorVO> getErrors(Document errorDoc) {
		if (errorDoc == null) {
			throw new java.lang.IllegalArgumentException();
		}
		List<XSDErrorVO> xsdErrorVOList = new ArrayList<XSDErrorVO>();
		Element errorsElement = errorDoc.getRootElement();
		Iterator<Element> elementIterator = (Iterator<Element>) errorsElement
				.elementIterator("error");
		while (elementIterator.hasNext()) {
			Element errorElement = elementIterator.next();
			String errorMsg = errorElement.getTextTrim();
			String columnAttr = errorElement.attributeValue("column");
			String lineAttr = errorElement.attributeValue("line");
			XSDErrorVO xsdErrorVO = new XSDErrorVO(errorMsg, Integer
					.parseInt(columnAttr), Integer.parseInt(lineAttr));
			xsdErrorVOList.add(xsdErrorVO);
		}
		return xsdErrorVOList;
	}

	
	public static void main(String[] args) {

		String systemDir = System.getProperty("user.dir");
		String xmlPath = systemDir + "/doc/ws_xml/test/0100000000.xml";
		String xsdPath = systemDir + "/doc/ws_xml/RdSapRequest.xsd";
		
//		List<XSDErrorVO> list = validateXmlByXsd2(xsdPath, xmlPath);
//		for (XSDErrorVO vo:list){
//			System.out.println(vo.getColumn());
//			System.out.println(vo.getLine());
//			System.out.println(vo.getErrorMsg());
//		}
		
		SAXReader xmlReader = new SAXReader();
		try {
			Document document = xmlReader.read(new ByteArrayInputStream(FileUtil.getFileContent(xmlPath).getBytes()));

			List<XSDErrorVO> list = validateXmlByXsd(xsdPath, document);
			for (XSDErrorVO vo:list){
				System.out.println(vo.getColumn());
				System.out.println(vo.getLine());
				System.out.println(vo.getErrorMsg());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
