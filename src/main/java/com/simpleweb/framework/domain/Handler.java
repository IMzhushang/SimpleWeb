package com.simpleweb.framework.domain;

import java.lang.reflect.Method;

import com.simpleweb.framework.util.type.HandlerMethod;
import com.simpleweb.framework.util.type.HandlerMethod.MethodParameterPair;

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
	
	private MethodParameterPair[] parameter;

	public Handler(Class<?> controllerClass, Method actionMethod) {
		super();
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
		this.parameter = HandlerMethod.getParameterNames(actionMethod);
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

	public MethodParameterPair[] getParameter() {
		return parameter;
	}

	public void setParameter(MethodParameterPair[] parameter) {
		this.parameter = parameter;
	}
	
	

}
