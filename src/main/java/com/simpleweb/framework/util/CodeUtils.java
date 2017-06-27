package com.simpleweb.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL 编解码工具
 * 
 * @author Administrator
 *
 */
public class CodeUtils {

	/**
	 *  url 
	 * @param source
	 * @return
	 */
	public static String encodeURL(String source) {
		String encode = null;
		try {
			encode = URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encode;
	}

	public static String decodeURL(String source) {
		String decode = null;
		try {
			decode = URLDecoder.decode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return decode;
	}
}
