package com.simpleweb.framework.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义的一二个表单 包含有普通字段和文件字段
 * 
 * @author Administrator
 *
 */
public class FormParam {

	private List<FileParam> fileParamList;
	private List<FieldParam> fieldParaList;

	public List<FileParam> getFileParamList() {
		return fileParamList;
	}

	public FormParam(List<FieldParam> fieldParaList) {
		super();
		this.fieldParaList = fieldParaList;
	}

	public FormParam(List<FileParam> fileParamList,
			List<FieldParam> fieldParaList) {
		super();
		this.fileParamList = fileParamList;
		this.fieldParaList = fieldParaList;
	}

	// 获取请求参数的映射

	public Map<String, Object> getFieldMap() {

		Map<String, Object> filedMap = new HashMap<String, Object>();

		if (fieldParaList != null && fieldParaList.size() > 0) {
			for (FieldParam fieldParam : fieldParaList) {
				String fieldName = fieldParam.getFieldName();
				Object fieldValue = fieldParam.getFiledValue();

				if (filedMap.containsKey(fieldName)) {
					fieldValue = filedMap.get(fieldValue) + "_" + fieldValue;
				}
				filedMap.put(fieldName, fieldValue);
			}
		}

		return filedMap;
	}

	// 获取上传文件的映射

	public Map<String, List<FileParam>> getFileMap() {

		Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();

		if (fileParamList != null && fileParamList.size() > 0) {
			for (FileParam fileParam : fileParamList) {
				String fieldName = fileParam.getFieldName();
				List<FileParam> list;
				if (fileMap.containsKey(fieldName)) {
					list = fileMap.get(fieldName);
				} else {
					list = new ArrayList<FileParam>();
				}

				list.add(fileParam);
				fileMap.put(fieldName, list);

			}
		}

		return fileMap;

	}

	// 获取到所有的上传的文件

	// 获取唯一的上传文件

	// 验证参数是否为空
	public Boolean isEmpty() {

		if (fileParamList == null || fieldParaList == null) {
			return true;
		}
		return false;
	}

	public void setFileParamList(List<FileParam> fileParamList) {

		this.fileParamList = fileParamList;
	}

	public List<FieldParam> getFieldParaList() {
		return fieldParaList;
	}

	public void setFieldParaList(List<FieldParam> fieldParaList) {
		this.fieldParaList = fieldParaList;
	}

}
