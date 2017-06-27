package com.simpleweb.framework.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.simpleweb.framework.annotation.Inject;
import com.simpleweb.framework.util.ReflectionUtils;

/**
 * 实现基本的IOC 功能
 * 
 * 通过反射变量每一个字段查找是否有@Inject 注解，如果有的话通过 反射注入值
 * 
 * @author Administrator
 *
 */
public class IOCHelper {
	/**
	 * 一初始化就实现实例的注入
	 */
	static {

		HashMap<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

		for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
			Class<?> clazz = entry.getKey();
			Object target = entry.getValue();

			Field[] declaredFields = clazz.getDeclaredFields();

			if (declaredFields != null && declaredFields.length > 0) {
				for (int i = 0; i < declaredFields.length; i++) {
					Field field = declaredFields[i];
					field.setAccessible(true);
					// 需要进行注入的字段
					if (field.isAnnotationPresent(Inject.class)) {
						// 取出字段的类型
						Class<?> filedType = field.getType();

						Object injectObject = beanMap.get(filedType);

						if (injectObject == null) {
							throw new RuntimeException(" can inject bean");
						}
						// 反射注入值
						ReflectionUtils.setField(target, field, injectObject);

					}
				}
			}

		}

	}

}
