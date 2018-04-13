package com.simpleweb.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
 //	private static final Map<Class<?>, Method[]> declaredMethodsCache =
	//		new ConcurrentReferenceHashMap<Class<?>, Method[]>(256);
			
	private static final Map<Class<?>, Method[]> declaredMethodsCache =
					new ConcurrentHashMap <Class<?>, Method[]>(256);

	public interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * @param method the method to operate on
		 */
		void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
	}
	
	/**
	 *  过滤掉不符合条件的方法
	 * @author Administrator
	 *
	 */
	public interface MethodFilter {

		/**
		 * Determine whether the given method matches.
		 * @param method the method to check
		 */
		boolean matches(Method method);
	}
	
	
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
	
	public static Object invokeMathod(Object object, Method method,
			  Object[] args) {

			try {
				method.setAccessible(true);
			invoke = method.invoke(object, args);
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

	public static Method[] getAllDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException {
		final List<Method> methods = new ArrayList<Method>(32);
		doWithMethods(leafClass,new MethodCallback() {
			
			public void doWith(Method method) throws IllegalArgumentException,
					IllegalAccessException {
			         methods.add(method);
			  }
		});
		return methods.toArray(new Method[methods.size()]);
	}
	
	public static void doWithMethods(Class<?> clazz, MethodCallback mc) throws IllegalArgumentException {
		doWithMethods(clazz, mc, null);
	}
	
	public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf)
			throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		Method[] methods = getDeclaredMethods(clazz);
		for (Method method : methods) {
			if (mf != null && !mf.matches(method)) {
				continue;
			}
			try {
				mc.doWith(method);
			}
			catch (IllegalAccessException ex) {
				throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName() + "': " + ex);
			}
		}
		if (clazz.getSuperclass() != null) {
			doWithMethods(clazz.getSuperclass(), mc, mf);
		}
		else if (clazz.isInterface()) {
			for (Class<?> superIfc : clazz.getInterfaces()) {
				doWithMethods(superIfc, mc, mf);
			}
		}
	}

	private static Method[] getDeclaredMethods(Class<?> clazz) {
		Method[] result = declaredMethodsCache.get(clazz);
		if (result == null) {
			result = clazz.getDeclaredMethods();
			declaredMethodsCache.put(clazz, result);
		}
		return result;
	}

}
