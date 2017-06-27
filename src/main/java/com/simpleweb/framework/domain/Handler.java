package com.simpleweb.framework.domain;

import java.lang.reflect.Method;

/**
 * 对应于一个请求的处理器
 * 
 * @author Administrator
 *
 */
public class Handler {
	/**
	 * 对应于那个controller
	 */
	private Class<?> controllerClass;

	/**
	 * 对应的处理方法
	 */
	private Method actionMethod;

	public Handler(Class<?> controllerClass, Method actionMethod) {
		super();
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(Method actionMethod) {
		this.actionMethod = actionMethod;
	}

}
