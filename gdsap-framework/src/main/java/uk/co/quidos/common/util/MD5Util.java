package uk.co.quidos.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class MD5Util {
	
	public  static String MD5(String s) {
		if (StringUtils.isEmpty(s) || StringUtils.isBlank(s))
		{
			return null;
		}
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getHashCode(Object object) throws IOException{
		if(object == null) return "";
		
		String ss = null;
		ObjectOutputStream s = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
			try {
				s = new ObjectOutputStream(bo);
				s.writeObject(object);
				s.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(s != null) {
					s.close();
					s = null;
				}
			}
		ss = MD5(bo.toString());
		return ss;
	}
	
	public static void main(String[] args) {
		String str = "5";
		try
		{
			List<String> lines = IOUtils.readLines(new FileInputStream(new File("C:/Users/peng.shi/Desktop/1111.txt")));
			StringBuffer sb = new StringBuffer();
			for (String str1 : lines)
			{
				sb.append(str1);
			}
			System.out.println(sb);
			System.err.println(sb.toString());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(MD5(str));
		System.out.println("f4de502e58723e6252e8856d4dc8fc3b".length());
	}
}
