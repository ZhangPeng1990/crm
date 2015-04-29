package uk.co.quidos.gdsap.evaluation.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

public class ClgResponse {

	private ClgResponse(String responseXml) {
		this.responseXml = responseXml;
	}

	private String responseXml;
	private String transactionId;
	private String timestamp;
	private String rrn;
	private boolean success = false;
	private ArrayList<ClgErrorMessage> errorMessages = new ArrayList<ClgErrorMessage>();

	private Document getDocument() {
		try {
			SAXReader reader = new SAXReader();
			try{
				return reader.read(new ByteArrayInputStream(responseXml.getBytes("UTF-8")));
			} catch (Exception e) {
				return reader.read(new ByteArrayInputStream(responseXml.getBytes("UTF-16")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getRrn() {
		return rrn;
	}

	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success){
		this.success = success;
	}

	public ArrayList<ClgErrorMessage> getErrorMessages() {
		return errorMessages;
	}
	
	public String getFirstErrorMessage() {
		if (errorMessages != null && errorMessages.size() > 0) {
			return errorMessages.get(0).getMessage();
		}
		return "";
	}

	public String getResponseXml() {
		return responseXml;
	}
	
	public static ClgResponse nonDomesticResponse(String responseXml) {
		System.out.println("nonDomesticResponse: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;

		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-CEPC/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-CEPC/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element messageIdentifierRoot = rootElement
				.element("MessageIdentifier");

		Element transactionIdElement = messageIdentifierRoot.element(new QName(
				"TransactionID", cs));
		Element timestampElement = messageIdentifierRoot.element(new QName(
				"Timestamp", cs));

		Element rrnElement = rootElement.element("RRN");
		Element successElement = rootElement.element("Success");

		clgReturn.transactionId = transactionIdElement.getText();
		clgReturn.timestamp = timestampElement.getText();
		clgReturn.rrn = rrnElement.getText();

		if (successElement != null && "Y".equals(successElement.getText()))
			clgReturn.success = true;

		Element exceptionListElement = rootElement.element("ExceptionList");
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}

	public static ClgResponse domesticResponse(String responseXml) {
		System.out.println("domesticResponse: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-HIP/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-HIP/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element identifierRoot = rootElement.element("Identification");
		Element transactionElement = identifierRoot.element(new QName(
				"TransactionDetails", cs));
		Element transactionIdElement = transactionElement.element(new QName(
				"TransactionID", cs));
		Element timestampElement = transactionElement.element(new QName(
				"Timestamp", cs));
		
		Element rrnElement = identifierRoot.element("Identifier").element("RRN");
		

		if (transactionIdElement!=null) clgReturn.transactionId = transactionIdElement.getText();
		clgReturn.timestamp = timestampElement.getText();
		clgReturn.rrn = rrnElement.getText();
		
		Element succeseContent = rootElement.element("Content");
//		Element successElement = succeseContent != null ? succeseContent
//				.element("RequestSuccesful") : rootElement
//				.element("RequestSuccesful");
//		if (successElement != null && "Y".equals(successElement.getText()))
//			clgReturn.isSuccess = true;
				
		if(succeseContent != null){
			clgReturn.success = true;
		}

		Element exceptionListElement = rootElement.element("ExceptionList");
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}
	
	public static ClgResponse amsDomResponse(String responseXml) {
		System.out.println("amsResponse: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-HIP/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-HIP/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element identifierRoot = rootElement.element("Identification");
		Element transactionElement = identifierRoot.element(new QName(
				"TransactionDetails", cs));

		Element timestampElement = transactionElement.element(new QName(
				"Timestamp", cs));
	
		clgReturn.timestamp = timestampElement.getText();
		
		Element succeseContent = rootElement.element("Content");
		Element successElement = succeseContent != null ? succeseContent
				.element("RequestSuccesful") : rootElement
				.element("RequestSuccesful");
		if (successElement != null && "Y".equals(successElement.getText()))
			clgReturn.success = true;

		Element exceptionListElement = succeseContent.element("ExceptionList");
		System.out.println("list size 0 = "+ (exceptionListElement==null));
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			System.out.println("list size="+exceptionElementList.size());
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}
	
	public static ClgResponse amsNDomResponse(String responseXml) {
		System.out.println("amsndResponse: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-CEPC/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-CEPC/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element identifierRoot = rootElement.element("MessageIdentifier");

		Element timestampElement = identifierRoot.element(new QName(
				"Timestamp", cs));
	
		clgReturn.timestamp = timestampElement.getText();
		
		Element successElement = rootElement.element("Success");
		if (successElement != null && "Y".equals(successElement.getText()))
			clgReturn.success = true;

		Element exceptionListElement = rootElement.element("ExceptionList");
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}
	
	public static ClgResponse cancelDomesticReportResponse(String responseXml) {
		System.out.println("Domestic Response: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-HIP/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-HIP/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element identifierRoot = rootElement.element("Identification");
		Element transactionElement = identifierRoot.element(new QName(
				"TransactionDetails", cs));

		Element timestampElement = transactionElement.element(new QName(
				"Timestamp", cs));
		
		Element rrnElement = identifierRoot.element("Identifier").element("RRN");
		
		clgReturn.timestamp = timestampElement.getText();
		clgReturn.rrn = rrnElement.getText();
		
		Element succeseContent = rootElement.element("Content");
		Element successElement = succeseContent != null ? succeseContent
				.element("Success") : rootElement
				.element("Success");
		if (successElement != null && "Y".equals(successElement.getTextTrim()))
			clgReturn.success = true;

		Element exceptionListElement = succeseContent.element("ExceptionList");
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}

	public static ClgResponse cancelNonDomesticReportResponse(String responseXml) {
		System.out.println("Non-Domestic Response: " + responseXml);
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();
		Namespace ns = new Namespace("ERR", "DCLG-CEPC/Exceptions");
		Namespace cs = new Namespace("CS", "DCLG-CEPC/CommonStructures");
		Element rootElement = doc.getRootElement();

		Element identifierRoot = rootElement.element("MessageIdentifier");
		Element transactionElement = identifierRoot.element(new QName(
				"TransactionID", cs));

		Element timestampElement = identifierRoot.element(new QName(
				"Timestamp", cs));
		
		Element rrnElement = rootElement.element("RRN");
		
		clgReturn.timestamp = timestampElement.getText();
		clgReturn.rrn = rrnElement.getText();

		Element successElement = rootElement.element("Success");
		if (successElement != null && "Y".equals(successElement.getTextTrim()))
			clgReturn.success = true;

		Element exceptionListElement = rootElement.element("ExceptionList");
		QName qName = new QName("Exception", ns);
		QName errorCodeQName = new QName("ErrorCode", ns);
		QName errorMessageQName = new QName("ErrorMessage", ns);
		if (exceptionListElement != null) {
			List<Element> exceptionElementList = exceptionListElement
					.elements(qName);
			for (Element exceptionElement : exceptionElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
				Element errorCode = exceptionElement.element(errorCodeQName);
				if (errorCode != null) {
					cem.setCode(errorCode.getTextTrim());
				}
				Element errorMessage = exceptionElement
						.element(errorMessageQName);
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		}

		return clgReturn;
	}
	
	public static ClgResponse restResponse(String responseXml) {
		System.out.println("RESTful Response: " + responseXml);
		
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) return clgReturn;
		
		Document doc = clgReturn.getDocument();

		Element rootElement = doc.getRootElement();

		Element timestampElement = rootElement.element("Start-Date-Time");
		clgReturn.timestamp = timestampElement.getText();
		clgReturn.transactionId = rootElement.element("Upload-ID").getText();

		List<Element> errorElementList = rootElement.elements("Error");

		if (errorElementList != null && errorElementList.size() > 0) {

			for (Element errorElement : errorElementList) {
				ClgErrorMessage cem = new ClgErrorMessage();
//				Element errorCode = errorElement.element(errorCodeQName);
//				if (errorCode != null) {
//					cem.setCode(errorCode.getTextTrim());
//				}
				Element errorMessage = errorElement.element("Message");
				if (errorMessage != null) {
					cem.setMessage(errorMessage.getTextTrim());
				}
				clgReturn.errorMessages.add(cem);
			}
		} else if (!"Failed".equalsIgnoreCase(rootElement.element("Status").getText())){
			clgReturn.success = true;
		}

		return clgReturn;
	}
	
	public static ClgResponse restResponseSCT(String responseXml) {
		System.out.println("SCT RESTful Response: " + responseXml);
		
		ClgResponse clgReturn = new ClgResponse(responseXml);
		if (responseXml == null || responseXml.length() < 1) {
			clgReturn.success = true;
			return clgReturn;
		}
		
		Document doc = clgReturn.getDocument();

		Element rootElement = doc.getRootElement();

		Element errorCode = rootElement.element("ErrorCode");
		Element errorMessage = rootElement.element("ErrorMessage");
		if (errorCode != null && errorMessage != null) {
			String message = errorMessage.getTextTrim();
			Element errorComment = rootElement.element("Comment");
			if (errorComment != null) {
				message += ":" + errorComment.getTextTrim();
			}
			
			ClgErrorMessage cem = new ClgErrorMessage();
			cem.setCode(errorCode.getTextTrim());
			cem.setMessage(message);
			clgReturn.errorMessages.add(cem);
		} else {
			clgReturn.success = true;
		}

		return clgReturn;
	}
	

}
