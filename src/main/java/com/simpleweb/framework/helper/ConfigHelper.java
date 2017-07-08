package com.simpleweb.framework.helper;

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

	public static String getAppBasePackage() {
		System.out.println("---->appBase" + PropsUtils.getValueBykey(ConfigConstant.APP_BASE_PACKAGE, PROPER));
		return PropsUtils.getValueBykey(ConfigConstant.APP_BASE_PACKAGE, PROPER);

	}

	public static String getAppJspPath() {
		return PropsUtils.getValueBykey(ConfigConstant.APP_JSP_PATH, PROPER);
	}

	public static String getAppAssetPath() {
	//	System.out.println("---->appBase" + PropsUtils.getValueBykey(ConfigConstant.APP_BASE_PACKAGE, PROPER));
		return PropsUtils.getValueBykey(ConfigConstant.APP_ASSET_PATH, PROPER);
	}

}
