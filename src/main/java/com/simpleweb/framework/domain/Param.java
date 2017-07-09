package com.simpleweb.framework.domain;

import java.util.Map;

/**
 * 对应于一个请求参数的封转
 * 
 * @author Administrator
 *
 */
public class Param {
	/**
	 * 参数的 K-V对
	 */
	private Map<String, Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		super();
		this.paramMap = paramMap;
	}

	public Object[] getParams() {
		Object[] params = new Object[paramMap.size()];

		int index = 0;
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			params[index++] = entry.getValue();
		}

		return params;
	}

}
