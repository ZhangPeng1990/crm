package uk.co.quidos.gdsap.evaluation.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import uk.co.quidos.gdsap.evaluation.wsclient.ResponseErrorType;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseMessage;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseObject;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseStatus;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;

public class ClgRestClientUtil {

	public static ResponseObject get(String urlString, String eaCertNo,String certPath, String certPassword) throws ClgLodgeException {
		return request("GET", urlString, eaCertNo, certPath, certPassword, null);
	}
	
	public static ResponseObject get(String urlString) throws ClgLodgeException {
		return request("GET", urlString);
	}

	public static ResponseObject post(String urlString, String eaCertNo ,String certPath, String certPassword, String xmlData) throws ClgLodgeException {
		return request("POST", urlString, eaCertNo, certPath, certPassword, xmlData);
	}

	public static ResponseObject put(String urlString, String certPath, String certPassword, String xmlData) throws ClgLodgeException {
		return request("PUT", urlString, null, certPath, certPassword, xmlData);
	}
	public static ResponseObject put(String urlString, String eaCertNo, String certPath, String certPassword, String xmlData) throws ClgLodgeException {
		return request("PUT", urlString, eaCertNo, certPath, certPassword, xmlData);
	}
	
//	public ResponseObject sctRequestPdf(String serviceUrl,String httpAuthUsername,String httpAuthPassword,String organisationId,String adviserId,String xmlData,String pdfPath) {
//		
//		try {
//			URL url = new URL(serviceUrl);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//			con.setDoOutput(true);
//			con.setDoInput(true);
//			//con.setRequestProperty("Accept", "text/xml");
//			con.setRequestProperty("Accept", "application/pdf");
//			con.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(httpAuthUsername + ":" + httpAuthPassword));
//			con.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(organisationId));
//			con.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));
//			System.out.println(con.getRequestMethod());
//			System.out.println(con.getRequestProperties());
//			
//			final OutputStream outputStream = con.getOutputStream();
//			writeOutput(outputStream, xmlData);
//			outputStream.close();
//			
//			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
//				ro.setSuccessContent(readInput(con.getInputStream()));
//				return ro;
//			}else if (con.getResponseCode() == 403) {
//				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_403);
//				return ro;
//			}else {
//				String xmlContent = readInput(con.getErrorStream());
//				System.out.println(xmlContent);
//				StringReader sr = new StringReader(xmlContent);
//				SAXReader saxReader = new SAXReader();
//				Document doc = saxReader.read(sr);
//				Element rootElement = doc.getRootElement();
//				
//				Element errorCodeElement = rootElement.element("ErrorCode");
//				Element errorMessageElement = rootElement.element("ErrorMessage");
//				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
//				ResponseMessage rm = new ResponseMessage();
//				rm.setResponseErrorType(ResponseErrorType.ValidationError);
//				rm.setMessage(errorCodeElement.getTextTrim() + " : " + errorMessageElement.getTextTrim());
//				ro.setResponseMessage(rm);
//				return ro;
//			}
//		} catch (ProtocolException e) {
//			e.printStackTrace();
//			throw new IllegalArgumentException();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new IllegalArgumentException();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//			throw new IllegalArgumentException();
//		}
//	}
	
	public static ResponseObject sctLodge(String serviceUrl,String httpAuthUsername,String httpAuthPassword,String organisationId,String adviserId,String xmlData) {
		System.out.println("service url sct lodge -> " + serviceUrl);
		System.out.println("http auth username -> " + httpAuthUsername);
		System.out.println("http auth password -> " + httpAuthPassword);
		System.out.println("organisationid -> " + organisationId);
		System.out.println("adviserid -> " + adviserId);
		
		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Accept", "text/xml");
			// con.setRequestProperty("Accept", "application/pdf");
			con.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(httpAuthUsername + ":" + httpAuthPassword));
			con.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(organisationId));
			con.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));
			
			System.out.println(con.getRequestMethod());
			System.out.println(con.getRequestProperties());
			final OutputStream outputStream = con.getOutputStream();
			writeOutput(outputStream, xmlData);
			outputStream.close();
			
			int responseCode = con.getResponseCode();
			if (responseCode == 201) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				ro.setSuccessContent(readInput(con.getInputStream()));
				return ro;
			}else if (responseCode == 403) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_403);
				System.out.println(readInput(con.getErrorStream()));
				return ro;
			}else {
				String xmlContent = readInput(con.getErrorStream());
				System.out.println(xmlContent);
				StringReader sr = new StringReader(xmlContent);
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(sr);
				Element rootElement = doc.getRootElement();
				
				Element errorCodeElement = rootElement.element("ErrorCode");
				Element errorMessageElement = rootElement.element("ErrorMessage");
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				ResponseMessage rm = new ResponseMessage();
				rm.setResponseErrorType(ResponseErrorType.ValidationError);
				rm.setMessage(errorCodeElement.getTextTrim() + " : " + errorMessageElement.getTextTrim());
				ro.setResponseMessage(rm);
				return ro;
			}
			
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public static ResponseObject sctRequestPdf(String serviceUrl,String httpAuthUsername,String httpAuthPassword,String organisationId,String adviserId,String pdfFilePath) {
		System.out.println("service url request pdf -> " + serviceUrl);
		System.out.println("http auth username -> " + httpAuthUsername);
		System.out.println("http auth password -> " + httpAuthPassword);
		System.out.println("organisationid -> " + organisationId);
		System.out.println("adviserid -> " + adviserId);
		
		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Accept", "application/pdf");
			con.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(httpAuthUsername + ":" + httpAuthPassword));
			con.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(organisationId));
			con.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				saveToFileAfterDecode(GlobalConfig.getInstance().getFSDir() + pdfFilePath, con.getInputStream());
				return ro;
			}else if (responseCode == 403) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_403);
				System.out.println(readInput(con.getErrorStream()));
				return ro;
			}else {
				String xmlContent = readInput(con.getErrorStream());
				System.out.println(xmlContent);
				StringReader sr = new StringReader(xmlContent);
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(sr);
				Element rootElement = doc.getRootElement();
				
				Element errorCodeElement = rootElement.element("ErrorCode");
				Element errorMessageElement = rootElement.element("ErrorMessage");
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				ResponseMessage rm = new ResponseMessage();
				rm.setResponseErrorType(ResponseErrorType.ValidationError);
				rm.setMessage(errorCodeElement.getTextTrim() + " : " + errorMessageElement.getTextTrim());
				ro.setResponseMessage(rm);
				return ro;
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @param rrn
	 * @return
	 */
	public static ResponseObject sctRequestOAXmlFromServer(String serviceUrl,String httpAuthUsername,String httpAuthPassword,String organisationId,String adviserId){
		System.out.println("service url request oa xml -> " + serviceUrl);
		System.out.println("http auth username -> " + httpAuthUsername);
		System.out.println("http auth password -> " + httpAuthPassword);
		System.out.println("organisationid -> " + organisationId);
		System.out.println("adviserid -> " + adviserId);
		
		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(httpAuthUsername + ":" + httpAuthPassword));
			con.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(organisationId));
			con.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				ro.setSuccessContent(readInput(con.getInputStream()));
				return ro;
			}else if (responseCode == 403) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_403);
				System.out.println(readInput(con.getErrorStream()));
				return ro;
			}else {
				String xmlContent = readInput(con.getErrorStream());
				System.out.println(xmlContent);
				StringReader sr = new StringReader(xmlContent);
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(sr);
				Element rootElement = doc.getRootElement();
				
				Element errorCodeElement = rootElement.element("ErrorCode");
				Element errorMessageElement = rootElement.element("ErrorMessage");
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				ResponseMessage rm = new ResponseMessage();
				rm.setResponseErrorType(ResponseErrorType.ValidationError);
				rm.setMessage(errorCodeElement.getTextTrim() + " : " + errorMessageElement.getTextTrim());
				ro.setResponseMessage(rm);
				return ro;
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @param rrn
	 * @return
	 */
	public static ResponseObject sctRequestEPCXmlFromServer(String serviceUrl,String httpAuthUsername,String httpAuthPassword,String organisationId,String adviserId){
		System.out.println("service url request epc xml -> " + serviceUrl);
		System.out.println("http auth username -> " + httpAuthUsername);
		System.out.println("http auth password -> " + httpAuthPassword);
		System.out.println("organisationid -> " + organisationId);
		System.out.println("adviserid -> " + adviserId);
		
		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(adviserId));
			//con.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(organisationId));
			//con.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				ro.setSuccessContent(readInput(con.getInputStream()));
				return ro;
			}else if (responseCode == 403) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_403);
				System.out.println(readInput(con.getErrorStream()));
				return ro;
			}else {
				String xmlContent = readInput(con.getErrorStream());
				System.out.println(xmlContent);
				StringReader sr = new StringReader(xmlContent);
				SAXReader saxReader = new SAXReader();
				Document doc = saxReader.read(sr);
				Element rootElement = doc.getRootElement();
				
				Element errorCodeElement = rootElement.element("ErrorCode");
				Element errorMessageElement = rootElement.element("ErrorMessage");
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				ResponseMessage rm = new ResponseMessage();
				rm.setResponseErrorType(ResponseErrorType.ValidationError);
				rm.setMessage(errorCodeElement.getTextTrim() + " : " + errorMessageElement.getTextTrim());
				ro.setResponseMessage(rm);
				return ro;
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	private static ResponseObject request(String method, String urlString){
		URL url;
		final HttpURLConnection connection;
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/html");
			connection.setDoOutput(true);

			System.out.println("ClgRestClientUtil.request() response: "+connection.getResponseCode() + ": " + connection.getResponseMessage());
			
			if (connection.getResponseCode() == 200) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				ro.setSuccessContent(readInput(connection.getInputStream()));
				return ro;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static ResponseMessage _getResponseMessage(HttpsURLConnection connection)
	{
		ResponseMessage rm = new ResponseMessage();
		
		String strError = readInput(connection.getErrorStream());
		System.out.println(strError);
		
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(new StringReader(strError));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = doc.getRootElement();
		
		boolean isGdipLodge = false;
		if(rootElement.getName().equals("GDIP-Response"))
		{
			isGdipLodge = true;
		}
		String schemaValidationError = isGdipLodge ? "Schema-Validation-Error" : "SchemaValidationError";
		String validationError = isGdipLodge ? "Validation-Error" : "ValidationError";
		String errorMessageElementName = isGdipLodge ? "Error-Message" : "ErrorMessage";
		String lineNo = isGdipLodge ? "Line-No" : "LineNo";
		String columnNo = isGdipLodge ? "Column-No" : "ColumnNo";
		
		Element errorElement = isGdipLodge ? rootElement.element("GDIP-Error").element(schemaValidationError) : rootElement.element(schemaValidationError);
		
		if (errorElement == null) {
			if(isGdipLodge)
			{
				errorElement = rootElement.element("GDIP-Error").element(validationError);
			}
			else
			{
				errorElement = rootElement.element(validationError);
			}
			rm.setResponseErrorType(ResponseErrorType.ValidationError);
		}
		Element errorMessageElement = errorElement.element(errorMessageElementName);
		Element lineNoElement = errorElement.element(lineNo);
		Element columnNoElement = errorElement.element(columnNo);
		
		String errorMessage = errorMessageElement.getTextTrim();
		rm.setMessage(errorMessage);
		rm.setLineNo(lineNoElement != null ? Integer.parseInt(lineNoElement.getTextTrim()) : 0);
		rm.setColumnNo(columnNoElement != null ? Integer.parseInt(columnNoElement.getTextTrim()) : 0);
		
		return rm;
	}
	
	/**
	 * 
	 * @param method:GET/PUT/POST default GET
	 * @param urlString
	 * @param certPath
	 * @param certPassword
	 * @param xmlData
	 * @return
	 * @throws ClgLodgeException
	 */
	private static ResponseObject request(String method, String urlString, String eaCertNo, String certPath, String certPassword, String xmlData) throws ClgLodgeException {
		System.out.println(method + " REST url: " + urlString);
		System.out.println("CertNO : " + eaCertNo);
		System.out.println("REST certPath: " + certPath);
		System.out.println("certPassword : " + certPassword); 
		
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		
		//System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "false");
		//System.setProperty("sun.security.ssl.allowLegacyHelloMessages", "false");
		try {
			//final HttpsURLConnection connection = openSecureConnection(urlString, certPath, certPassword);
			////////////////////////////
			URL url = new URL(urlString);
			char[] passwKey = certPassword.toCharArray();
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(new FileInputStream(certPath), passwKey);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, passwKey);
			SSLContext sslContext = SSLContext.getInstance("SSLv3");
			sslContext.init(kmf.getKeyManagers(), new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			SSLSocketFactory sslFactory = sslContext.getSocketFactory();
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(sslFactory);
			
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			if (eaCertNo != null && eaCertNo.length()>0) { // SCT
				System.out.println("eaCertNO : " + eaCertNo);
				connection.setRequestProperty("Authorization", "Basic " + Base64.getDefaultBase64String(eaCertNo));
			}
			
			connection.setDoOutput(true);

			if (xmlData != null) {
				final OutputStream outputStream = connection.getOutputStream();
				writeOutput(outputStream, xmlData);
				outputStream.close();
			}
			
			System.out.println("ClgRestClientUtil.request() response: "+connection.getResponseCode() + ": " + connection.getResponseMessage());
			
//			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//				return readInput(connection.getInputStream());
//			}else {
//				return readInput(connection.getErrorStream());
//			}
			if (connection.getResponseCode() == 201) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Success);
				ro.setSuccessContent(readInput(connection.getInputStream()));
				return ro;
			} else if (connection.getResponseCode() == 400){
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				ResponseMessage rm = _getResponseMessage(connection);
				rm.setResponseErrorType(ResponseErrorType.SchemaValidationError);
				ro.setResponseMessage(rm);
				return ro;
			} else if (connection.getResponseCode() == 401) {
				ResponseMessage rm = _getResponseMessage(connection);
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_401);
				ro.setResponseMessage(rm);
				return ro;
			}else if (connection.getResponseCode() == 400) {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_400);
				return ro;
			}else {
				ResponseObject ro = new ResponseObject(ResponseStatus.Fail_500);
				return ro;
			}
		} catch (Exception e) {
			throw new ClgLodgeException(e);
		}
	}
	
	public static void postPdfCertGen(String urlString, String certPath, String certPassword, String xmlData, String pdfSavePath) throws ClgLodgeException {
		pdfCertGen("POST", urlString, null, certPath, certPassword, xmlData, pdfSavePath);
	}

	public static void putPdfCertGen(String urlString, String eaCertNo, String certPath, String certPassword, String xmlData, String pdfSavePath) throws ClgLodgeException {
		pdfCertGen("PUT", urlString, eaCertNo, certPath, certPassword, xmlData, pdfSavePath);
	}
	
	private static void pdfCertGen(String method, String urlString, String eaCertNo, String certPath, String certPassword, String xmlData, String pdfSavePath) throws ClgLodgeException {
		System.out.println(new Date() + " " + method + " REST url: " + urlString);
		System.out.println("REST certPath: " + certPath);

		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

		try {
			final HttpsURLConnection connection = openSecureConnection(urlString, certPath, certPassword);

			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");

			if (eaCertNo != null && eaCertNo.length()>0) { // SCT
				connection.setRequestProperty("Authorization", Base64.dotNetStrToUnicodeBase64(eaCertNo));
				connection.setRequestProperty("Accept", "application/pdf");
//				connection.setRequestProperty("RRN", "0000-0000-0000-0000-0000");
			}
			
			connection.setDoOutput(true);
			connection.setDoInput(true);

			System.out.println(connection.getRequestProperties());
			final OutputStream outputStream = connection.getOutputStream();
			writeOutput(outputStream, xmlData);
			outputStream.close();

			System.out.println("ClgRestClientUtil.pdfCertGen() response: "+connection.getResponseCode() + ": " + connection.getResponseMessage());

			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				System.out.println(pdfSavePath);
				if (eaCertNo != null && eaCertNo.length()>0) { // SCT
					saveToFileAfterDecode(pdfSavePath, connection.getInputStream());
				} else {
					saveToFile(pdfSavePath, connection.getInputStream());
				}
			} else {
				String errorMsg = connection.getResponseCode() + " error: " + connection.getResponseMessage();
				String responseXml = readInput(connection.getErrorStream());
				System.out.println(errorMsg+"\n"+responseXml);
				if (eaCertNo != null && eaCertNo.length()>0) { // SCT
					ClgResponse clgResponse = ClgResponse.restResponseSCT(responseXml);
					errorMsg += clgResponse.getFirstErrorMessage();
				}
				throw new ClgLodgeException(errorMsg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClgLodgeException(e);
		}
	}

	private static HttpsURLConnection openSecureConnection(final String urlString, final String certPath, final String certPassword) throws Exception {

		final URL url = new URL(urlString);

//		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
//		final KeyStore keyStore = KeyStore.getInstance("PKCS12");
//
//		final InputStream keyInput = new FileInputStream(certPath);
//		keyStore.load(keyInput, certPassword.toCharArray());
//		keyInput.close();
//
//		keyManagerFactory.init(keyStore, certPassword.toCharArray());
//		final SSLContext context = SSLContext.getInstance("SSLv3");
//		context.init(keyManagerFactory.getKeyManagers(), null, null);
//		final SSLSocketFactory socketFactory = context.getSocketFactory();
//
//		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//		connection.setSSLSocketFactory(socketFactory);
		
		
		// /////////////////////////
		char[] passwKey = certPassword.toCharArray();
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(new FileInputStream(certPath), passwKey);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, passwKey);
		SSLContext sslContext = SSLContext.getInstance("SSLv3");
		sslContext.init(kmf.getKeyManagers(), null, null);
		SSLSocketFactory sslFactory = sslContext.getSocketFactory();
		
		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setSSLSocketFactory(sslFactory);
		
		return connection;
	}

	private static void writeOutput(final OutputStream outputStream, String xmlData){
		
		try {
			ByteArrayInputStream inputStram = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));

			final byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = inputStram.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	private static String readInput(final InputStream is) {

		if (is == null) return "";
		
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}
	
	private static void saveToFileAfterDecode(String filePath, InputStream in) {
		
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(in);
			
			byte[] decodeBb = new byte[0];
			int BUFFER_SIZE = 1024;
			byte[] buf = new byte[BUFFER_SIZE];
			int size = 0;
			
			while ((size = bis.read(buf)) != -1) { 
				byte[] totalBb = new byte[decodeBb.length + size];
				
				for(int i = 0; i<totalBb.length; i++) {
					if (i<decodeBb.length) totalBb[i] = decodeBb[i];
					else totalBb[i] = buf[i-decodeBb.length];
				}
				
				decodeBb = totalBb;
			}
			
			String base64str = new String(decodeBb);

			byte[] bb = Base64.decode(base64str.toCharArray());
			
			fos = new FileOutputStream(filePath);
			fos.write(bb); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void saveToFile(String filePath, InputStream in) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;

		try {
			int BUFFER_SIZE = 1024;
			byte[] buf = new byte[BUFFER_SIZE];
			int size = 0;

			bis = new BufferedInputStream(in);

			fos = new FileOutputStream(filePath);

			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {System.out.println("client");}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {System.out.println("server");}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
        	System.out.println("accepted issuers");
            return null;
        }
    }
	
//	public static void main(String[] args) {
//		String[] uprns = { "6311442468", "9070562468", "6619652468", "3336252468", "3753863887", "2753863887", "1753863887", "8683452468", "6619652468" };
//		for (String uprn : uprns) {
//			ResponseObject r = get("https://uat.gdregister.com/secure/address/" + uprn, "philip@quidos.co.uk:Dfgjk3409ssdjij3409sd",
//					"/var/www/CLGCert/client_QUIDOS_OA_LODGEMENT.P12", "lkjs40990sdlj23wrsf");
//
//			String successContent = (r != null ? r.getSuccessContent() : "null");
//			String status = (r != null && r.getResponseStatus() != null ? r.getResponseStatus().getDesc() : "null");
//			String responseMessage = (r != null && r.getResponseMessage() != null ? r.getResponseMessage().getMessage() : "null");
//
//			System.out.println("successContent:" + successContent);
//			System.out.println("status:" + status);
//			System.out.println("responseMessage:" + responseMessage);
//		}
//	}
}

