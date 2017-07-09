package com.simpleweb.framework.helper;

import java.util.HashMap;
import java.util.Set;

import com.simpleweb.framework.util.ReflectionUtils;

/**
 * 类似一个IOC的容器，用来保存Bean ,以及对外提供bean
 * 
 * 保存 class --- bean 的映射关系
 * 
 * @author Administrator
 *
 */
public class BeanHelper {

	/**
	 * 存儲Bean
	 */
	private static final HashMap<Class<?>, Object> CLASS_BEAN;

	static {
		CLASS_BEAN = new HashMap<Class<?>, Object>();
		Set<Class<?>> beanClass = ClassHelper.getBeanClass();
		for (Class<?> clazz : beanClass) {
			Object newInstance = ReflectionUtils.newInstance(clazz);
			CLASS_BEAN.put(clazz, newInstance);
		}
	}

	/**
	 * 查找bean
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		if (!CLASS_BEAN.containsKey(clazz)) {
			throw new RuntimeException(" can not find bean");
		}
		return (T) CLASS_BEAN.get(clazz);
	}

	/**
	 * 将bean 放入容器
	 * 
	 * @param clazz
	 * @param object
	 */
	public static void setBean(Class<?> clazz, Object object) {
		
			CLASS_BEAN.put(clazz, object);
		
	}

	public static HashMap<Class<?>, Object> getBeanMap() {
		return CLASS_BEAN;
	}

}
