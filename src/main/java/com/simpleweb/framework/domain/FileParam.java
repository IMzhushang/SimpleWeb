package com.simpleweb.framework.domain;

import java.io.InputStream;

/**
 * 一个封转上传文件的Bean
 * 
 * @author Administrator
 *
 */
public class FileParam {

	private String fieldName;
	private String fileName;
	private Long size;
	private String contentType;
	private InputStream inputStream;

	public FileParam(String fieldName, String fileName, Long size,
			String contentType, InputStream inputStream) {
		super();
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.size = size;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getSize() {
		return size;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

}
