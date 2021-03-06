package com.simpleweb.framework;

import com.simpleweb.framework.aop.AopHelper;
import com.simpleweb.framework.helper.BeanHelper;
import com.simpleweb.framework.helper.ClassHelper;
import com.simpleweb.framework.helper.ControllerHelper;
import com.simpleweb.framework.helper.IOCHelper;
import com.simpleweb.framework.orm.EntryHelper;
import com.simpleweb.framework.util.ClassUtils;

/**
 * 完成整个框架的初始化过程
 * 
 * @author Administrator
 *
 */
public class HelperLoder {
	/**
	 * 初始化框架
	 */
	public static void init() {
		Class<?>[] classList = { ClassHelper.class, EntryHelper.class,
				BeanHelper.class, AopHelper.class, IOCHelper.class,
				ControllerHelper.class };

		for (int i = 0; i < classList.length; i++) {
			ClassUtils.loadClass(classList[i].getName(), true);
		}
		System.err.println("---------simple web init------------------");
	}
}
