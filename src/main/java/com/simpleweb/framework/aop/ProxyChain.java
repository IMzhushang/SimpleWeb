package com.simpleweb.framework.aop;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 考虑到一个类可能会有多个切面，因此采用 链的方式来执行
 * 
 * @author Administrator
 *
 */
public class ProxyChain {

	/**
	 * 目标类字节码
	 */
	private final Class<?> targetClass;
	/**
	 * 目标类的方法
	 */
	private final Method targetMethod;
	/**
	 * 代理方法
	 */
	private final MethodProxy methodProxy;
	/**
	 * 方法的参数
	 */
	private final Object[] methodParams;
	/**
	 * 目标类的实例
	 */
	private final Object targetObject;

	/**
	 * 存放所有的代理
	 */
	private List<Proxy> proxyList = new LinkedList<Proxy>();

	public ProxyChain(Class<?> targetClass, Object targetObject,
			Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	private int proxyIndex = 0;

	public Object doProxyChain() throws Throwable {

		Object result;
		if (proxyIndex < proxyList.size()) {
			result = proxyList.get(proxyIndex++).doProxy(this);
		} else {
			result = methodProxy.invokeSuper(targetObject, methodParams);
		}

		return result;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	public Object getTargetObject() {
		return targetObject;
	}

}
