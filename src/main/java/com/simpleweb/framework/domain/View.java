package com.simpleweb.framework.domain;

import java.util.Map;

/**
 * 视图类型返回的粉砖的对象 比如返回对应的 jsp velocity
 * 
 * @author Administrator
 *
 */
public class View {
	/**
	 * 视图路径
	 */
	private String viewPath;

	/**
	 * 模型数据
	 */
	private Map<String, Object> model;

	public View(String viewPath, Map<String, Object> model) {
		super();
		this.viewPath = viewPath;
		this.model = model;
	}

	public String getViewPath() {
		return viewPath;
	}

	public Map<String, Object> getModel() {
		return model;
	}

}
