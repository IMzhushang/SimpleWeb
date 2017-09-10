package com.simpleweb.framework.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图解析器接口
 * 
 * @author Administrator
 *
 */
public interface ViewResolver {

	void resovler(HttpServletRequest request, HttpServletResponse response,
			String path, Map<String, Object> values) throws Exception;
}
