package com.simpleweb.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamUtils {
	/**
	 * 从流中提取出字符串
	 * 
	 * @param in
	 * @return
	 */
	public static String getString(InputStream in) {
		StringBuffer sb = new StringBuffer();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String line = null;
		try {
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void copyStream(InputStream inputStream,
			OutputStream outputStream) {
		try {
			int length;
			byte[] buffer = new byte[4 * 1024];
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
			}
		}
	}
}
