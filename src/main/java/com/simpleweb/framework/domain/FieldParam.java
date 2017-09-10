package com.simpleweb.framework.domain;

/**
 * 普通表单字段
 * 
 * @author Administrator
 *
 */
public class FieldParam {

	private String fieldName;
	private Object filedValue;

	public FieldParam(String fieldName, Object filedValue) {
		super();
		this.fieldName = fieldName;
		this.filedValue = filedValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFiledValue() {
		return filedValue;
	}

}
