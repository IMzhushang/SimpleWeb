package com.simpleweb.framework.helper;

import java.util.HashSet;
import java.util.Set;

import com.simpleweb.framework.annotation.Controller;
import com.simpleweb.framework.annotation.Service;
import com.simpleweb.framework.util.ClassUtils;

/**
 * 类加载器
 * 
 * 加载类，扫描注解
 * 
 * @author Administrator
 *
 */
public class ClassLoaderHelper {

	private static final Set<Class<?>> SET_CLASS;

	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		SET_CLASS = ClassUtils.getClassSet(basePackage);
	}

	/**
	 * 得到所有标注为service的class
	 * 
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet() {

		Set<Class<?>> sets = new HashSet<Class<?>>();
		
		
		Set<Class<?>> sets2 = new HashSet<Class<?>>();
		
               
		for (Class<?> clazz : SET_CLASS) {
			// 判断一个类文件上是否有service注解
			if (clazz.isAnnotationPresent(Service.class)) {
				sets.add(clazz);
			}

		}

		return sets;

	}

	/**
	 * 得到所有标注为controller的class
	 * 
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> sets = new HashSet<Class<?>>();

		for (Class<?> clazz : SET_CLASS) {
			// 判断一个类文件上是否有service注解
			if (clazz.isAnnotationPresent(Controller.class)) {
				sets.add(clazz);
			}

		}

		return sets;
	}

	/**
	 *   返回需要实例化的class
	 * @return  包含了@sevice @ conteoller 的注解的Class
	 */
	public static Set<Class<?>> getBeanClass() {
		Set<Class<?>> beanSet = new HashSet<Class<?>>();

		beanSet.addAll(getControllerClassSet());
		beanSet.addAll(getServiceClassSet());

		return beanSet;

	}

}
