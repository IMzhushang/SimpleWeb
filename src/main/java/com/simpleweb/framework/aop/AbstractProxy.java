package com.simpleweb.framework.aop;

import java.lang.reflect.Method;

/**
 * 切面类的模版类， 自定义切面是需要继承此类
 * 
 * @author Administrator
 *
 */
public abstract class AbstractProxy implements Proxy {
	public Object doProxy(ProxyChain proxyChain) throws Throwable {

		Method targetMethod = proxyChain.getTargetMethod();
		Object[] methodParams = proxyChain.getMethodParams();
		Class<?> targetClass = proxyChain.getTargetClass();

		begin();

		Object result = null;

		// 判断是否拦截
		try {
			if (intercept(targetClass, targetMethod, methodParams)) {
				before(targetClass, targetMethod, methodParams);
				result = proxyChain.doProxyChain();
				after(targetClass, targetMethod, methodParams, result);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			e.printStackTrace();
			error(targetClass, targetMethod, methodParams, e);
		} finally {
			end();
		}

		return result;
	}

	/**
	 * 执行结束
	 */
	protected void end() {
	}

	/**
	 * 错误通知
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param methodParams
	 * @param e
	 */
	private void error(Class<?> targetClass, Method targetMethod,
			Object[] methodParams, Exception e) {

	}

	/**
	 * 后置通知
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param methodParams
	 * @param result
	 */
	protected void after(Class<?> targetClass, Method targetMethod,
			Object[] methodParams, Object result) {

	}

	/**
	 * 前置通知
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param methodParams
	 */
	protected void before(Class<?> targetClass, Method targetMethod,
			Object[] methodParams) {

	}

	/**
	 * 判断是否拦截该方法，子类可以重写该方法类实现自定义的拦截
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param methodParams
	 * @return
	 */
	protected boolean intercept(Class<?> targetClass, Method targetMethod,
			Object[] methodParams) {
		return true;
	}

	/**
	 * 开始执行
	 */
	protected void begin() {

	}
}
