package com.simpleweb.framework.transcation;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;

import com.simpleweb.framework.aop.Proxy;
import com.simpleweb.framework.aop.ProxyChain;
import com.simpleweb.framework.dao.DataBaseHelper;
import com.simpleweb.framework.transcation.annoacton.Transcation;

public class TranscationProxy implements Proxy {

	/**
	 * 每个线程持有一个是否进行了事务处理的标志
	 */
	private static final ThreadLocal<Boolean> FLAG = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	/**
	 * 判断方法是否添加了@Transtion注解
	 */
	public Object doProxy(ProxyChain proxyChain) throws Throwable {

		Method method = proxyChain.getTargetMethod();
		Object result = null;
		Boolean flag = FLAG.get();
		if (!flag && method.isAnnotationPresent(Transcation.class)) {
			// 表示当前线程已经开始了事务的处理
			FLAG.set(true);
			try {
				DataBaseHelper.beginTransaction();
				System.err.println("开始了事务--------------");
				result = proxyChain.doProxyChain();
				DataBaseHelper.commitTranscation();
				System.err.println("提交了事务--------------");
			} catch (Exception e) {
				DataBaseHelper.rollbackTranscation();
				System.err.println("回滚了事务--------------");
				e.printStackTrace();
			} finally {
				FLAG.remove();
			}
		} else {
			result = proxyChain.doProxyChain();
		}

		return result;
	}

}
