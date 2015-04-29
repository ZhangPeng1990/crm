package uk.co.quidos.gdsap.evaluation.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.dom4j.Document;

public class RestClientUtil {
	
	public static void main(String[] args) {
		//Lodge GDIP
		String url = "https://www.gdregister.com/secure/atr/store";
		String url2 = "https://www.gdregister.com/secure/atr/000-0000-00000-0000-0000/xml";
		String certPath = "D:/client_QUIDOS_OA_LODGEMENT.P12";
		String certPassword = "PjPQWN6qhqMvtbBV";
		String gdipLigPath = "C:/Users/tyr/Desktop/work/GDOA/OA_v2/GDIP/GDIP 2014 XML_GDIP 2014-10-04/IP-1d-different advisor_GDIP.xml";
		Document xmlDoc = DocumentUtil.readDocument(new File(gdipLigPath));
		String xmlData = xmlDoc.asXML();
		
//		String mess = post(url, certPath, certPassword, xmlData);
//		System.out.println(mess);
		
		String mess2 = get(url2, certPath, certPassword);
		System.out.println(mess2);
	}
	
	public static String post(String urlString, String xmlData) {
		return post(urlString, null, null, xmlData);
	}
	public static String post(String urlString, String certPath, String certPassword, String xmlData) {
		return request("POST", urlString, certPath, certPassword, xmlData);
	}
	
	public static String get(String urlString) {
		return get(urlString, null, null);
	}
	public static String get(String urlString, String certPath, String certPassword) {
		return request("GET", urlString, certPath, certPassword, null);
	}
	
	public static String put(String urlString) {
		return get(urlString, null, null);
	}
	public static String put(String urlString, String certPath, String certPassword) {
		return request("PUT", urlString, certPath, certPassword, null);
	}
	
	/**
	 * 
	 * @param method: GET/PUT/POST default GET
	 * @param urlString: requried
	 * @param certPath: if not null secure request
	 * @param certPassword:
	 * @param xmlData: default null
	 * @return
	 * @throws ClgLodgeException
	 */
	private static String request(String method, String urlString, String certPath, String certPassword, String xmlData) {
		System.out.println(new Date() + " REST url: " + urlString);
		System.out.println("REST certPath: " + certPath);
		final HttpURLConnection connection;
		try {
			if (certPath != null && certPath.length()>0) {
				System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
				connection = openSecureConnection(urlString, certPath, certPassword);
			} else {
				connection = openConnection(urlString);
			}
			
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setDoOutput(true);

			if (xmlData != null) {
				final OutputStream outputStream = connection.getOutputStream();
				writeOutput(outputStream, xmlData);
				outputStream.close();
			}

			System.out.println("RestClientUtil response: "+connection.getResponseCode() + ": " + connection.getResponseMessage());

			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				return readInput(connection.getInputStream());
			} else {
				return readInput(connection.getErrorStream());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	private static HttpsURLConnection openSecureConnection(final String urlString, final String certPath, final String certPassword) throws Exception {

		final URL url = new URL(urlString);

		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		final KeyStore keyStore = KeyStore.getInstance("PKCS12");

		final InputStream keyInput = new FileInputStream(certPath);
		keyStore.load(keyInput, certPassword.toCharArray());
		keyInput.close();

		keyManagerFactory.init(keyStore, certPassword.toCharArray());
		final SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		final SSLSocketFactory socketFactory = context.getSocketFactory();

		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setSSLSocketFactory(socketFactory);
		return connection;
	}
	
	private static HttpURLConnection openConnection(final String urlString) throws Exception {

		final URL url = new URL(urlString);
		final HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		return connection;
	}

	private static void writeOutput(final OutputStream outputStream, final String xmlData) throws Exception {
		ByteArrayInputStream inputStram = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));

		final byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStram.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
	}

	private static String readInput(final InputStream is) {

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
}
