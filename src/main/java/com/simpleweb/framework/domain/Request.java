package com.simpleweb.framework.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 对一个请求的分装
 * 
 * 请求的方法 请求的参数 请求的路径
 * 
 * 
 * 注意 ： Request 需要放置在Map 作为key ,需要重写其hascode
 * 
 * @author Administrator
 *
 */
public class Request {

	/**
	 * 请求的类型
	 */
	private String requestType;

	/**
	 * 请求的路径
	 */
	private String requestPath;

	public Request(String requestType, String requestPath) {
		super();
		this.requestType = requestType;
		this.requestPath = requestPath;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	//
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
