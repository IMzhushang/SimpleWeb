package com.simpleweb.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.simpleweb.framework.annotation.Controller;
import com.simpleweb.framework.transcation.annoacton.Service;
import com.simpleweb.framework.util.ClassUtils;

/**
 * 类加载器
 * 
 * 加载类，扫描注解
 * 
 * @author Administrator
 *
 */
public class ClassHelper {

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
	 * 返回需要实例化的class
	 * 
	 * @return 包含了@sevice @ conteoller 的注解的Class
	 */
	public static Set<Class<?>> getBeanClass() {
		Set<Class<?>> beanSet = new HashSet<Class<?>>();

		beanSet.addAll(getControllerClassSet());
		beanSet.addAll(getServiceClassSet());

		return beanSet;

	}

	/**
	 * 获得标注了某个注解的类
	 * 
	 * @param clz
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(
			Class<? extends Annotation> clz) {

		Set<Class<?>> sets = new HashSet<Class<?>>();

		for (Class<?> clazz : SET_CLASS) {
			if (clazz.isAnnotationPresent(clz)) {
				sets.add(clazz);
			}

		}

		return sets;

	}

	/**
	 * 获得摸一个接口或者父类的所有子类
	 * 
	 * @param clz
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuperClass(Class<?> clz) {

		Set<Class<?>> sets = new HashSet<Class<?>>();

		for (Class<?> clazz : SET_CLASS) {
			// TODO 判断一个类是否某个类的子类
			if (clz.isAssignableFrom(clazz) && !clz.equals(clazz)) {
				sets.add(clazz);
			}
		}

		return sets;
	}

}
