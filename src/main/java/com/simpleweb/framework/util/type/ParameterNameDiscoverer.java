package com.simpleweb.framework.util.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 *  
 * @author zhushang
 *
 */
public interface  ParameterNameDiscoverer {

	/**
	 * 从方法中获取到参数名
	 * @param method
	 * @return
	 */
	String[] getParameterNames(Method method);
	
	/**
	 * 从构造方法中获取到参数名
	 * @param constructor
	 * @return
	 */
	String[] getParameterNames(Constructor<?> constructor);
	
}
