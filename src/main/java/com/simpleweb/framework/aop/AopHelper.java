package com.simpleweb.framework.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simpleweb.framework.helper.BeanHelper;
import com.simpleweb.framework.helper.ClassHelper;

/**
 * 来实现Aop
 * 
 * @author Administrator
 *
 */
public class AopHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AopHelper.class);

	static {

		try {
			Map<Class<?>, Set<Class<?>>> createProxyTargetMap = createProxyTargetMap();
			Map<Class<?>, List<Proxy>> createTargetProxyMap = createTargetProxyMap(createProxyTargetMap);
			for (Map.Entry<Class<?>, List<Proxy>> entry : createTargetProxyMap
					.entrySet()) {

				Object createProxy = ProxyManager.createProxy(entry.getKey(),
						entry.getValue());

				BeanHelper.setBean(entry.getKey(), createProxy);
				LOGGER.error("--" + entry.getKey().getName()
						+ "--- 被创建了一个AOP代理 ---"
						+ createProxy.getClass().getName() + "---放入了bean容器");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 拿到所有的目标类
	 * 
	 * @param aspect
	 * @return
	 */
	public static Set<Class<?>> createTargetClassSet(Aspect aspect) {

		Set<Class<?>> sets = new HashSet<Class<?>>();

		Class<? extends Annotation> targetAnnotation = aspect.value();

		if (targetAnnotation != null && !targetAnnotation.equals(Aspect.class)) {
			Set<Class<?>> classSetByAnnotatio = ClassHelper
					.getClassSetByAnnotation(targetAnnotation);
			sets.addAll(classSetByAnnotatio);
		}

		return sets;

	}

	/**
	 * 得到 切面类 和 目标类的关系
	 * 
	 * @param targetClass
	 * @return
	 */
	public static Map<Class<?>, Set<Class<?>>> createProxyTargetMap() {

		Map<Class<?>, Set<Class<?>>> proxyTargetMap = new HashMap<Class<?>, Set<Class<?>>>();

		/*
		 * Set<Class<?>> classSetBySuperClass = ClassHelper
		 * .getClassSetBySuperClass(Proxy.class); for (Class<?> clz :
		 * classSetBySuperClass) { Aspect aspect =
		 * clz.getAnnotation(Aspect.class); if (aspect == null) { continue; }
		 * Class<? extends Annotation> targetAnnotation = aspect.value(); if
		 * (targetAnnotation == null || targetAnnotation.equals(Aspect.class)) {
		 * continue; } List<Class<?>> list = new ArrayList<Class<?>>(); for
		 * (Class<?> class1 : targetClass) { if
		 * (class1.isAnnotationPresent(targetAnnotation)) { list.add(class1); }
		 * } proxyTargetMap.put(clz, list); }
		 */

		// 得到所有AbstractProxy的子类
		Set<Class<?>> classSetBySuperClass = ClassHelper
				.getClassSetBySuperClass(AbstractProxy.class);
		for (Class<?> clz : classSetBySuperClass) {
			// 判断是否标注了Asparct的注解
			if (clz.isAnnotationPresent(Aspect.class)) {
				Aspect annotation = clz.getAnnotation(Aspect.class);
				Set<Class<?>> classSetByAnnotation = createTargetClassSet(annotation);
				proxyTargetMap.put(clz, classSetByAnnotation);
			}
		}

		return proxyTargetMap;

	}

	/**
	 * 得到 目标类和 切面类之间的关系
	 * 
	 * @param map
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Map<Class<?>, List<Proxy>> createTargetProxyMap(
			Map<Class<?>, Set<Class<?>>> map) throws Exception {

		Map<Class<?>, List<Proxy>> targetProxyMap = new HashMap<Class<?>, List<Proxy>>();

		for (Map.Entry<Class<?>, Set<Class<?>>> entry : map.entrySet()) {
			Class<?> proxyClass = entry.getKey();
			for (Class<?> clz : entry.getValue()) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetProxyMap.containsKey(clz)) {
					targetProxyMap.get(clz).add(proxy);
				} else {
					List<Proxy> proxies = new ArrayList<Proxy>();
					proxies.add(proxy);
					targetProxyMap.put(clz, proxies);
				}
			}
		}

		return targetProxyMap;

	}

}
