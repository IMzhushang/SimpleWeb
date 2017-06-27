package com.simpleweb.framework.domain;
/**
 *  返回的是文本类型的数据 比如说 json
 * @author Administrator
 *
 */
public class Data {

	/**
	 *  模型数据
	 */
  private  Object model;

	public Data(Object model) {
		super();
		this.model = model;
	}

	public Object getModel() {
		return model;
	}
  
  
	
}
