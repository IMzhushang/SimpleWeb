package com.simpleweb.framework.mvc.impl;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.simpleweb.framework.helper.ConfigHelper;
import com.simpleweb.framework.mvc.ViewResolver;

/**
 * thymeleaf 模版的视图解析器
 * 
 * @author Administrator
 *
 */
public class ThymeleafViewResolver implements ViewResolver {

	public void resovler(HttpServletRequest request,
			HttpServletResponse response, String path, Map<String, Object> value)
			throws Exception {

		// Create Template Resolver
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
				request.getServletContext());
		// ClassLoaderTemplateResolver templateResolver = new
		// ClassLoaderTemplateResolver();
		// XHTML is the default mode, but we will set it anyway for better
		// understanding of code
		templateResolver.setTemplateMode("XHTML");
		// This will convert "home" to "/WEB-INF/templates/home.html"
		// ConfigHelper
		templateResolver.setPrefix(ConfigHelper.getTemplatePath());
		templateResolver.setSuffix(ConfigHelper.getSuffix());
		// Set template cache TTL to 1 hour. If not set, entries would live in
		// cache until expelled by LRU
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		// Cache is set to true by default. Set to false if you want templates
		// to
		// be automatically updated when modified.
		templateResolver.setCacheable(true);
		// Create Template Engine
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		// Write the response headers
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		WebContext ctx = parseParamFromRequest(request, response, value);

		// Executing template engine
		templateEngine.process(path, ctx, response.getWriter());

	}

	private WebContext parseParamFromRequest(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> params) {

		WebContext ctx = new WebContext(request, response,
				request.getServletContext(), request.getLocale());

		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			Object value = request.getAttribute(attributeName);
			ctx.setVariable(attributeName, value);
		}

		ctx.setVariables(params);

		return ctx;

	}

}
