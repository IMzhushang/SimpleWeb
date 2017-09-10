package com.simpleweb.framework.domain;

import java.util.ArrayList;
import java.util.List;
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

	public List<Object> getParams() {
		List<Object> params = new ArrayList<Object>();

		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			 params.add(entry.getValue());
		}

		return params;
	}

}
