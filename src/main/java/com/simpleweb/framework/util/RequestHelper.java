package com.simpleweb.framework.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.simpleweb.framework.domain.FieldParam;
import com.simpleweb.framework.domain.FormParam;

/**
 * 解析不包含文件的普通请求
 * 
 * @author Administrator
 *
 */
public class RequestHelper {

	public static FormParam createParam(HttpServletRequest request)
			throws IOException {

		List<FieldParam> fieldParams = new ArrayList<FieldParam>();

		fieldParams.addAll(parseParameterNames(request));
		fieldParams.addAll(parseInputStream(request));

		return new FormParam(fieldParams);

	}

	// Post 请求
	private static List<FieldParam> parseInputStream(HttpServletRequest request)
			throws IOException {

		List<FieldParam> fieldParams = new ArrayList<FieldParam>();

		String body = CodeUtils.decodeURL(StreamUtils.getString(request
				.getInputStream()));

		if (StringUtils.isNotEmpty(body)) {
			String[] params = body.split("&");

			if (params != null && params.length > 0) {
				for (String param : params) {
					String[] array = param.split("=");
					if (array != null && array.length == 2) {
						String paramName = array[0];
						String paramValue = array[1];
						fieldParams.add(new FieldParam(paramName, paramValue));
					}
				}
			}
		}

		return fieldParams;
	}

	// Get请求
	private static List<FieldParam> parseParameterNames(
			HttpServletRequest request) {

		List<FieldParam> formParams = new ArrayList<FieldParam>();
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String fieldName = parameterNames.nextElement();
			String[] parameterValues = request.getParameterValues(fieldName);
			if (parameterValues != null && parameterValues.length > 0) {
				Object value = null;

				if (parameterValues.length == 1) {
					value = parameterValues[0];
				} else {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < parameterValues.length; i++) {
						sb.append(parameterValues[i]);
						if (i != parameterValues.length - 1) {
							sb.append(StringUtils.SEPARATOR);
						}
					}
					value = sb.toString();
				}
				formParams.add(new FieldParam(fieldName, value));
			}
		}

		return formParams;
	}

}
