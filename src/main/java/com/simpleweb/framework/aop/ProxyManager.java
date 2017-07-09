package com.simpleweb.framework.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 创建一个代理
 * 
 * @author Administrator
 *
 */
public class ProxyManager {

	/**
	 * 返回一个代理
	 * 
	 * @param targetClass
	 * @param proxyList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass,
			final List<Proxy> proxyList) {

		return (T) Enhancer.create(targetClass, new MethodInterceptor() {

			public Object intercept(Object targetObject, Method targetMethod,
					Object[] methodParams, MethodProxy methodProxy)
					throws Throwable {

				return new ProxyChain(targetClass, targetObject, targetMethod,
						methodProxy, methodParams, proxyList).doProxyChain();
			}

		});

	}

}
