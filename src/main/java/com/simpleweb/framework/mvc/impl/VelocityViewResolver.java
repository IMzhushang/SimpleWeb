package com.simpleweb.framework.mvc.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.simpleweb.framework.helper.ConfigHelper;
import com.simpleweb.framework.mvc.ViewResolver;

public class VelocityViewResolver implements ViewResolver {

	public void resovler(HttpServletRequest request,
			HttpServletResponse response, String path,
			Map<String, Object> values) throws Exception {

		// 初始化并取得Velocity引擎
		VelocityEngine ve = new VelocityEngine();
     
		// 处理中文问题
		ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");

		ve.init();
		// 取得velocity的模版
		String templatePath = "/src/main/webapp"
				+ ConfigHelper.getTemplatePath() + path
				+ ConfigHelper.getSuffix();
		Template t = ve.getTemplate(templatePath);
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		// 把数据填入上下文
		if( values != null ) {
			for (Map.Entry<String, Object> entry : values.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
	
		// 转换输出
		t.merge(context, response.getWriter());

	}

}
