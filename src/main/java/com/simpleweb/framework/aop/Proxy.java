package com.simpleweb.framework.aop;

/**
 * 
 * @author Administrator
 *
 */
public interface Proxy {

	/**
	 * 执行链式代理
	 */
	Object doProxy(ProxyChain proxyChain)  throws Throwable;

}
