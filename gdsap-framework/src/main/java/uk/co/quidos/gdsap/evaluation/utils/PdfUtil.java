package uk.co.quidos.gdsap.evaluation.utils;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfUtil {
	public static void buildPDFWater(String srcPath,
			String destPath, String waterText) {
		try {
			PdfReader reader = new PdfReader(srcPath);
			int n = reader.getNumberOfPages();
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					destPath));
			int i = 0;
			// Image img = Image.getInstance("d:/WAV.png");
			// img.setAbsolutePosition(200, 400);
			BaseFont base = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
					"utf-8", BaseFont.EMBEDDED);

			while (i < n) {
				i++;
				PdfContentByte under = stamp.getUnderContent(i);
//				Rectangle pageSize = reader.getPageSizeWithRotation(i);
				// under.addImage(img);
				under.beginText();
				under.setFontAndSize(base, 150);
				under.setColorFill(new Color(210, 211, 213));
//				under.showTextAligned(Element.ALIGN_LEFT, waterText, 140, 180, 45);
				under.showTextAligned(Element.ALIGN_LEFT, waterText, 180, 180, 45);
				under.endText();
			}
			stamp.close();
		} catch (Exception de) {
			de.printStackTrace();
		}
	}
	
	public static void xhtml2Pdf(String uri, String pdfPath) {
		System.out.println(uri);
		OutputStream os = null;
		try {
			os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocument(uri);
			renderer.layout();
			renderer.createPDF(os, true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void xhtml2Pdf(File xhtmlFile,String pdfPath,String fontPath) {
		System.out.println("fontPath: "+fontPath);
		System.out.println("pdfPath: "+pdfPath);
		OutputStream os = null;
		try {
			os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
//			ITextUserAgent callback = new ITextUserAgent(renderer.getOutputDevice());
//			callback.setSharedContext(renderer.getSharedContext());
//			renderer.getSharedContext().setUserAgentCallback(callback);

			ITextFontResolver fontResolver = renderer.getFontResolver();
			
			if (fontPath !=null && fontPath.length()>0){
				fontResolver.addFontDirectory(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}

			renderer.setDocument(xhtmlFile);
			renderer.layout();
			renderer.createPDF(os, true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void xhtml2Pdf(String content, String pdfPath, String basePath) {

		File tmpFile = new File(basePath + "/" + UUID.randomUUID()+".html");
		
		FileUtil.setFileContent(tmpFile.getPath(), content);
		xhtml2Pdf(tmpFile, pdfPath, basePath + "/fonts");

		tmpFile.delete();
	}
	
//	public static void xhtml2Pdf(String content, String pdfPath, String basePath) {
//		System.out.println("basePath: "+basePath);
//		System.out.println("pdfPath: "+pdfPath);
//
//		OutputStream os = null;
//		try {
//			os = new FileOutputStream(pdfPath);
//			ITextRenderer renderer = new ITextRenderer();
//
//			ITextFontResolver fontResolver = renderer.getFontResolver();
//			fontResolver.addFontDirectory(basePath + "/fonts",
//					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//
//			renderer.setDocumentFromString(content);
//			
//			// 解决图片的相对路径问题  
//			renderer.getSharedContext().setBaseURL(basePath);  
//			
//			renderer.layout();
//			renderer.createPDF(os, true);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (os != null) {
//				try {
//					os.close();
//					os = null;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	public static void main(String[] args) {
//		HttpClient c = new HttpClient();
//		GetMethod getMethod = new GetMethod("http://www.knauf.dev/as-erf-auditor-webroot/gdoa/report/view-page-report/4");
//		try {
//			int code = c.executeMethod(getMethod);
//			String content = getMethod.getResponseBodyAsString();
//			System.out.println(content);
//			xhtml2Pdf(content, "d:/test.pdf","");
//		} catch (HttpException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//xhtml2Pdf("file:///d:/test.html", "d:/test.pdf");
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod("http://localhost:8080/myaccount/report/view-page-report/882");
		try {
			int code = httpClient.executeMethod(getMethod);
			String content = getMethod.getResponseBodyAsString();
			System.out.println(content);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xhtml2Pdf("http://localhost:8080/myaccount/report/view-page-report/882", "d:/test.pdf");
	}
}
