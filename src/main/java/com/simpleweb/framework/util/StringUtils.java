package com.simpleweb.framework.util;

public class StringUtils {
	public static final String SEPARATOR = String.valueOf((char) 29);

	public static Boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
}
