package uk.co.quidos.gdsap.evaluation.utils;

import java.io.UnsupportedEncodingException;

public class Base64 {
	/**
	 * 将原始数据编码为base64编码
	 */
	static public char[] encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * 将base64编码的数据解码成原始数据
	 */
	static public byte[] decode(char[] data) {
		int len = ((data.length + 3) / 4) * 3;
		if (data.length > 0 && data[data.length - 1] == '=')
			--len;
		if (data.length > 1 && data[data.length - 2] == '=')
			--len;
		byte[] out = new byte[len];
		int shift = 0;
		int accum = 0;
		int index = 0;
		for (int ix = 0; ix < data.length; ix++) {
			int value = codes[data[ix] & 0xFF];
			if (value >= 0) {
				accum <<= 6;
				shift += 6;
				accum |= value;
				if (shift >= 8) {
					shift -= 8;
					out[index++] = (byte) ((accum >> shift) & 0xff);
				}
			}
		}
		if (index != out.length)
			throw new Error("miscalculated data length!");
		return out;
	}

	static private char[]	alphabet	= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	static private byte[]	codes		= new byte[256];
	static {
		for (int i = 0; i < 256; i++)
			codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			codes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			codes[i] = (byte) (52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	}
	
	public static String getDefaultBase64String(String strSrc) {
		return new String(encode(strSrc.getBytes()));
	}
	
	/**
	 * 编码
	 * @param strSrc
	 * @return
	 */
	public static String dotNetStrToUnicodeBase64(String strSrc) {
		try {
			return new String(Base64.encode(strSrc.getBytes("UTF-16LE")));
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}
	
	public static String dotNetUnicodeBase64ToStr(String strEncode){
		try {
			return new String(Base64.decode(strEncode.toCharArray()), "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * 解码
	 * @param strEncode
	 * @return
	 */
	public static String base64ToStr(String strEncode){
		try {
			return new String(Base64.decode(strEncode.toCharArray()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}
	
	public static void main(String[] args) {

		// Convert.ToBase64String(Encoding.Unicode.GetBytes(membershipNumber)); // C#
		
		// 加密成base64
		String strSrc = "TEST200001";
		String strOut = dotNetStrToUnicodeBase64(strSrc);

		System.out.println(strOut);
		System.out.println(dotNetUnicodeBase64ToStr(strOut));
	}
}