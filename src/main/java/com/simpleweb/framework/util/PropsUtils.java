package com.simpleweb.framework.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properite文件中的内容
 * 
 * @author Administrator
 *
 */
public class PropsUtils {

	/**
	 *  根据路径获取到Properties对象
	 * @param path
	 * @return
	 */
	public static Properties LoadProperties(String path) {
		Properties properties = new Properties();
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("path"));
			properties.load(in);
			return properties;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 *  更具key 拿到 value 
	 * @param key
	 * @param properties
	 * @return
	 */
	public static String  getValueBykey(String key,Properties properties) {
		 return properties.getProperty(key);
	}

}
