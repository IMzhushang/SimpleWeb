package com.simpleweb.framework.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.simpleweb.framework.ConfigConstant;
import com.simpleweb.framework.util.PropsUtils;

/**
 * 用来读取基本的配置文件
 * 
 * @author Administrator
 *
 */
public final class ConfigHelper {

	private static final Properties PROPER = PropsUtils
			.LoadProperties(ConfigConstant.CONFING_FILES);

	public static List<String> getAppBasePackage() {
		System.out.println("---->appBase"
				+ PropsUtils.getValueBykey(ConfigConstant.APP_BASE_CONTROLLER_PACKAGE,
						PROPER));
		// controller 包
		String baseController = PropsUtils
		.getValueBykey(ConfigConstant.APP_BASE_CONTROLLER_PACKAGE, PROPER);
		
		
		String baseService = PropsUtils
				.getValueBykey(ConfigConstant.APP_BASE_SERVICE_PACKAGE, PROPER);
		
		List<String> basePackageList = new ArrayList<String>();
		
		if( baseController != null ) {
			basePackageList.addAll(Arrays.asList(baseController.split(",")));
		}
		
		if( baseService  != null ) {
			basePackageList.addAll(Arrays.asList(baseService.split(",")));
		}
		
		return basePackageList;

	}

	public static String getAppJspPath() {
		return PropsUtils.getValueBykey(ConfigConstant.APP_JSP_PATH, PROPER);
	}

	public static String getAppAssetPath() {
		// System.out.println("---->appBase" +
		// PropsUtils.getValueBykey(ConfigConstant.APP_BASE_PACKAGE, PROPER));
		return PropsUtils.getValueBykey(ConfigConstant.APP_ASSET_PATH, PROPER);
	}

	/**
	 * 根据key 拿到value
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return PropsUtils.getValueBykey(key, PROPER);
	}

	public static int getFileUploadSizeLimit() {
		return Integer.parseInt(PropsUtils.getValueBykey(
				ConfigConstant.APP_UPLOAD_FILE_LIMIT, PROPER));
	}
	
	
	/**
	 *  得到模版的路径
	 * @return
	 */
	public static String  getTemplatePath() {
		return PropsUtils.getValueBykey(ConfigConstant.TEMPLATE_PATH, PROPER);
	}
	
	/**
	 *  得到文件的后缀
	 * @return
	 */
	public static String  getSuffix() {
		return PropsUtils.getValueBykey(ConfigConstant.TEMPLATE_SUFFIX, PROPER);
	}
	
	public static   String getTemplateResolver() {
		return PropsUtils.getValueBykey(ConfigConstant.TEMPLATE_RESOLVER, PROPER);
	}

}
