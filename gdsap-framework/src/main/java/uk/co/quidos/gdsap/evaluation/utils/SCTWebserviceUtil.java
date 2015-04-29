/**
 * 
 */
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.dom4j.Document;
import org.dom4j.Element;

import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.sysconf.WebserviceEnvironment;

/**
 * @author peng.shi@argylesoftware.co.uk
 */
public class SCTWebserviceUtil {

	// public static final String urlString =
	// "https://dom.scottishepcregister-ote.org.uk/EPC/RRN/9120-2848-6290-9422-3431";

	// public static final String urlString =
	// "https://dom.scottishepcregister.org.uk/EPC/RRN/9459-1022-6205-7467-3904";

	// public static final String eaCertNo = "QUID201890";
	// public static final String eaCertNo = "QUID202817";
	// public static final String xmlData = null;

	/**
	 * 苏格兰获取OA xml
	 * @param url
	 * @param certfile
	 * @param certPassword
	 * @param authUsername
	 * @param authPassword
	 * @param assessorOrganisationId
	 * @param adviserId
	 * @return
	 */
	public static SCTWSResponse getOAXml(String url, String certfile, String certPassword, String authUsername, String authPassword, String assessorOrganisationId, String adviserId) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		
		String method = "GET";
		System.out.println("-----> sct : url -> " + url);
		System.out.println("-----> sct : cert -> " + certfile);
		System.out.println("-----> sct : cert password -> " + certPassword);
		System.out.println("-----> sct : auth username -> " + authUsername);
		System.out.println("-----> sct : auth password -> " + authPassword);
		System.out.println("-----> sct : assessorOrganisationId -> " + assessorOrganisationId);
		System.out.println("-----> sct : AdviserId -> " + adviserId);
		System.out.println("-----> sct : method ->" + method);
		
		try {
			HttpsURLConnection connection = _openSecureConnection(url, certfile, certPassword);

			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty("Accept", "text/xml");
			// "Khawar2.Awan:PassW0rd01"
			connection.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(authUsername + ":" + authPassword));
			// STRA000000
			connection.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(assessorOrganisationId));
			// STRO123456
			connection.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));

			connection.setDoOutput(true);
			connection.setDoInput(true);

			int code = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();

			System.out.println("SCTWebserviceUtil -> getOAXml()-> response: " + code + ": " + responseMessage);
			SCTWSResponse response = new SCTWSResponse();
			response.setResponseCode(code);

			if (code == HttpsURLConnection.HTTP_OK || code == HttpsURLConnection.HTTP_CREATED) {
				response.setResponseStatus(true);
				response.setSuccessContent(_readInput(connection.getInputStream()));
				connection.disconnect();
				return response;
			} else {
				response.setResponseStatus(false);
				if (code == 401) {
					response.setResponseMessage(responseMessage);
					connection.disconnect();
					return response;
				}
				String errorContent = _readInput(connection.getErrorStream());
				System.out.println(errorContent);
				Document doc = DocumentUtil.readDocument(errorContent);
				Element rootElement = doc.getRootElement();
				Element errorCodeElement = rootElement.element("ErrorCode");
				String errorCode = errorCodeElement.getTextTrim();
				Element errorMessageElement = rootElement.element("ErrorMessage");
				String errorMessage = errorMessageElement.getTextTrim();
				Element commentElement = rootElement.element("Comment");
				String comment = commentElement != null ? commentElement.getTextTrim():null;
				response.setErrorCode(errorCode);
				response.setErrorMessage(errorMessage);
				response.setComment(comment);
				connection.disconnect();
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SCTWSResponse tmpResponse = new SCTWSResponse();
			tmpResponse.setResponseCode(500);
			tmpResponse.setResponseMessage(e.getMessage());
			tmpResponse.setResponseStatus(false);
			return tmpResponse;
		}
	}

	/**
	 * 苏格兰获取OA pdf
	 * @param url
	 * @param certfile
	 * @param certPassword
	 * @param authUsername
	 * @param authPassword
	 * @param assessorOrganisationId
	 * @param adviserId
	 * @param pdfPath
	 * @return
	 */
	public static SCTWSResponse getOAPdf(String url, String certfile, String certPassword, String authUsername, String authPassword, String assessorOrganisationId, String adviserId, String pdfPath) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		
		String method = "GET";
		System.out.println("-----> sct : url -> " + url);
		System.out.println("-----> sct : cert -> " + certfile);
		System.out.println("-----> sct : cert password -> " + certPassword);
		System.out.println("-----> sct : auth username -> " + authUsername);
		System.out.println("-----> sct : auth password -> " + authPassword);
		System.out.println("-----> sct : assessorOrganisationId -> " + assessorOrganisationId);
		System.out.println("-----> sct : AdviserId -> " + adviserId);
		System.out.println("-----> sct : method ->" + method);
		
		try {
			HttpsURLConnection connection = _openSecureConnection(url, certfile, certPassword);

			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty("Accept", "application/pdf");
			// "Khawar2.Awan:PassW0rd01"
			connection.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(authUsername + ":" + authPassword));
			// STRA000000
			connection.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(assessorOrganisationId));
			// STRO123456
			connection.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));

			connection.setDoOutput(true);
			connection.setDoInput(true);

			int code = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();

			System.out.println("SCTWebserviceUtil -> getOAPdf()-> response: " + code + ": " + responseMessage);
			SCTWSResponse response = new SCTWSResponse();
			response.setResponseCode(code);

			if (code == HttpsURLConnection.HTTP_OK || code == HttpsURLConnection.HTTP_CREATED) {
				response.setResponseStatus(true);
				_saveToFileAfterDecode(pdfPath, connection.getInputStream());
				connection.disconnect();
				return response;
			} else {
				response.setResponseStatus(false);
				if (code == 401) {
					response.setResponseMessage(responseMessage);
					connection.disconnect();
					return response;
				}
				String errorContent = _readInput(connection.getErrorStream());
				System.out.println(errorContent);
				Document doc = DocumentUtil.readDocument(errorContent);
				Element rootElement = doc.getRootElement();
				Element errorCodeElement = rootElement.element("ErrorCode");
				String errorCode = errorCodeElement.getTextTrim();
				Element errorMessageElement = rootElement.element("ErrorMessage");
				String errorMessage = errorMessageElement.getTextTrim();
				Element commentElement = rootElement.element("Comment");
				String comment = commentElement != null ? commentElement.getTextTrim():null;
				response.setErrorCode(errorCode);
				response.setErrorMessage(errorMessage);
				response.setComment(comment);
				connection.disconnect();
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SCTWSResponse tmpResponse = new SCTWSResponse();
			tmpResponse.setResponseCode(500);
			tmpResponse.setResponseMessage(e.getMessage());
			tmpResponse.setResponseStatus(false);
			return tmpResponse;
		}
	}
	
	private static HttpURLConnection openConnection(final String urlString) throws Exception {

		final URL url = new URL(urlString);
		final HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		return connection;
	}
	
	public static SCTWSResponse sctLodgeOAxml(String url, String certfile, String certPassword, String authUsername, String authPassword, String assessorOrganisationId, String adviserId, String xmlContent) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		String method = "PUT";
		System.out.println("-----> sct : url -> " + url);
		System.out.println("-----> sct : cert -> " + certfile);
		System.out.println("-----> sct : cert password -> " + certPassword);
		System.out.println("-----> sct : auth username -> " + authUsername);
		System.out.println("-----> sct : auth password -> " + authPassword);
		System.out.println("-----> sct : assessorOrganisationId -> " + assessorOrganisationId);
		System.out.println("-----> sct : AdviserId -> " + adviserId);
		System.out.println("-----> sct : xmlcontent -> " + xmlContent);
		System.out.println("-----> sct : method ->" + method);
		
		try {
			HttpURLConnection connection = null;
			WebserviceEnvironment webserviceEnvirment = GlobalConfig.getInstance().getCurrentWebserviceEnvironment();
			if (webserviceEnvirment.equals(WebserviceEnvironment.Live))
			{
//				HttpsURLConnection connection = _openSecureConnection(url, certfile, certPassword);
				connection  = _openSecureConnection(url, certfile, certPassword);
			}
			else if(webserviceEnvirment.equals(WebserviceEnvironment.Ote))
			{
				connection  = _openSecureConnection(url, certfile, certPassword);
			}
			else
			{
				connection = openConnection(url);
			}
			
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			//connection.setRequestProperty("Accept", "text/xml");
			// "Khawar2.Awan:PassW0rd01"
			connection.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(authUsername + ":" + authPassword));
			// STRA000000
			connection.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(assessorOrganisationId));
			// STRO123456
			connection.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));

			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			if (xmlContent != null) {
				final OutputStream outputStream = connection.getOutputStream();
				_writeOutput(outputStream, xmlContent);
				outputStream.close();
			}
			
			int code = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();

			System.out.println("SCTWebserviceUtil -> lodgeOAxml()-> response: " + code + ": " + responseMessage);
			SCTWSResponse response = new SCTWSResponse();
			response.setResponseCode(code);

			if (code == HttpsURLConnection.HTTP_OK || code == HttpsURLConnection.HTTP_CREATED) {
				response.setResponseStatus(true);
				response.setSuccessContent(_readInput(connection.getInputStream()));
				connection.disconnect();
				return response;
			} else {
				response.setResponseStatus(false);
				if (code == 401) {
					response.setResponseMessage(responseMessage);
					connection.disconnect();
					return response;
				}
				String errorContent = _readInput(connection.getErrorStream());
				System.out.println(errorContent);
				Document doc = DocumentUtil.readDocument(errorContent);
				Element rootElement = doc.getRootElement();
				Element errorCodeElement = rootElement.element("ErrorCode");
				String errorCode = errorCodeElement.getTextTrim();
				Element errorMessageElement = rootElement.element("ErrorMessage");
				String errorMessage = errorMessageElement.getTextTrim();
				Element commentElement = rootElement.element("Comment");
				String comment = commentElement != null ? commentElement.getTextTrim():null;
				response.setErrorCode(errorCode);
				response.setErrorMessage(errorMessage);
				response.setComment(comment);
				connection.disconnect();
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SCTWSResponse tmpResponse = new SCTWSResponse();
			tmpResponse.setResponseCode(500);
			tmpResponse.setResponseMessage(e.getMessage());
			tmpResponse.setResponseStatus(false);
			return tmpResponse;
		}
	}
	
	public static SCTWSResponse lodgeOAxml(String url, String certfile, String certPassword, String authUsername, String authPassword, String assessorOrganisationId, String adviserId, String xmlContent) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		String method = "PUT";
		System.out.println("-----> sct : url -> " + url);
		System.out.println("-----> sct : cert -> " + certfile);
		System.out.println("-----> sct : cert password -> " + certPassword);
		System.out.println("-----> sct : auth username -> " + authUsername);
		System.out.println("-----> sct : auth password -> " + authPassword);
		System.out.println("-----> sct : assessorOrganisationId -> " + assessorOrganisationId);
		System.out.println("-----> sct : AdviserId -> " + adviserId);
		System.out.println("-----> sct : xmlcontent -> " + xmlContent);
		System.out.println("-----> sct : method ->" + method);
		
		try {
			HttpsURLConnection connection = _openSecureConnection(url, certfile, certPassword);
			
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			//connection.setRequestProperty("Accept", "text/xml");
			// "Khawar2.Awan:PassW0rd01"
			connection.setRequestProperty("Authorization", "Basic " + Base64.dotNetStrToUnicodeBase64(authUsername + ":" + authPassword));
			// STRA000000
			connection.setRequestProperty("AssessorOrganisationId", Base64.dotNetStrToUnicodeBase64(assessorOrganisationId));
			// STRO123456
			connection.setRequestProperty("AdviserId", Base64.dotNetStrToUnicodeBase64(adviserId));

			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			if (xmlContent != null) {
				final OutputStream outputStream = connection.getOutputStream();
				_writeOutput(outputStream, xmlContent);
				outputStream.close();
			}
			
			int code = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();

			System.out.println("SCTWebserviceUtil -> lodgeOAxml()-> response: " + code + ": " + responseMessage);
			SCTWSResponse response = new SCTWSResponse();
			response.setResponseCode(code);

			if (code == HttpsURLConnection.HTTP_OK || code == HttpsURLConnection.HTTP_CREATED) {
				response.setResponseStatus(true);
				response.setSuccessContent(_readInput(connection.getInputStream()));
				connection.disconnect();
				return response;
			} else {
				response.setResponseStatus(false);
				if (code == 401) {
					response.setResponseMessage(responseMessage);
					connection.disconnect();
					return response;
				}
				String errorContent = _readInput(connection.getErrorStream());
				System.out.println(errorContent);
				Document doc = DocumentUtil.readDocument(errorContent);
				Element rootElement = doc.getRootElement();
				Element errorCodeElement = rootElement.element("ErrorCode");
				String errorCode = errorCodeElement.getTextTrim();
				Element errorMessageElement = rootElement.element("ErrorMessage");
				String errorMessage = errorMessageElement.getTextTrim();
				Element commentElement = rootElement.element("Comment");
				String comment = commentElement != null ? commentElement.getTextTrim():null;
				response.setErrorCode(errorCode);
				response.setErrorMessage(errorMessage);
				response.setComment(comment);
				connection.disconnect();
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SCTWSResponse tmpResponse = new SCTWSResponse();
			tmpResponse.setResponseCode(500);
			tmpResponse.setResponseMessage(e.getMessage());
			tmpResponse.setResponseStatus(false);
			return tmpResponse;
		}
	}
	
	public static void main(String[] args) throws Exception{
//		String urlString = "";
//		String xmlContent = FileUtils.readFileToString(new File("D:/gdsap/upload/2013/05/17/6e838c52-c583-4aac-8177-35aedb1b92a0.xml"), "utf-16");
//		SCTWSResponse lodgeResponse = lodgeOAxml(urlString, certfile, certPassword, "Khawar2.Awan", "PassW0rd01", "STRA000000", "STRO123456", xmlContent);
//		System.out.println(lodgeResponse.isResponseStatus());
//		SCTWSResponse oapdf = getOAPdf(urlString, certfile, certPassword, "Khawar2.Awan", "PassW0rd01", "STRA000000", "STRO123456", "d:/1.pdf");
//		System.out.println(oapdf.isResponseStatus());
//		if (oapdf.isResponseStatus()) {
//			
//		}
//		SCTWSResponse oaxml = getOAXml(urlString, certfile, certPassword, "Khawar2.Awan", "PassW0rd01", "STRA000000", "STRO123456");
//		System.out.println(oaxml.isResponseStatus());
//		if (oaxml.isResponseStatus()) {
//			System.out.println(oaxml.getSuccessContent());
//		}
	}

	private static HttpsURLConnection _openSecureConnection(final String urlString, final String certPath, final String certPassword) throws Exception {

		final URL url = new URL(urlString);

		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		final KeyStore keyStore = KeyStore.getInstance("PKCS12");

		final InputStream keyInput = new FileInputStream(certPath);
		keyStore.load(keyInput, certPassword.toCharArray());
		keyInput.close();

		keyManagerFactory.init(keyStore, certPassword.toCharArray());
		final SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		final SSLSocketFactory socketFactory = context.getSocketFactory();

		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setSSLSocketFactory(socketFactory);
		return connection;
	}

	private static void _writeOutput(final OutputStream outputStream, String xmlData) throws Exception {

		ByteArrayInputStream inputStram = new ByteArrayInputStream(xmlData.getBytes("utf-16"));

		final byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStram.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
	}

	private static void _saveToFileAfterDecode(String filePath, InputStream in) {

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

				for (int i = 0; i < totalBb.length; i++) {
					if (i < decodeBb.length)
						totalBb[i] = decodeBb[i];
					else
						totalBb[i] = buf[i - decodeBb.length];
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

	private static String _readInput(final InputStream is) {

		if (is == null)
			return "";

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

	private static class DefaultTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			System.out.println("client");
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			System.out.println("server");
		}

		public X509Certificate[] getAcceptedIssuers() {
			System.out.println("accepted issuers");
			return null;
		}
	}

}
