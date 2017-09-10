package com.simpleweb.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 通过反射的方式来获得Bean ,并放到容器中去
 * 
 * 主要的功能有 1 反射生成一个Bean 的实例 2. 反射调用方法 3. 反射设置字段的值
 * 
 * 
 * @author Administrator
 *
 */
public class ReflectionUtils {

	private static Object invoke;

	/**
	 * 反射生成Bean
	 * 
	 * @param clazz
	 * @return 出错返回null
	 */
	public static Object newInstance(Class<?> clazz) {
		try {
			Object newInstance = clazz.newInstance();
			return newInstance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反射调用方法
	 * 
	 * @param object
	 *            调用的实例
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMathod(Object object, Method method,
		  List<Object> args) {

		try {
			method.setAccessible(true);
		invoke = method.invoke(object, args.toArray());
		//	method.invoke(obj, args)
	//	invoke = method.invoke(object, "zhushang","123");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invoke;

	}

	/**
	 * 反射來給字段設置值
	 * 
	 * @param target
	 * @param field
	 * @param value
	 */
	public static void setField(Object target, Field field, Object value) {

		try {
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
