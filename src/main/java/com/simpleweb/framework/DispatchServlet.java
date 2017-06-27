package com.simpleweb.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simpleweb.framework.domain.Data;
import com.simpleweb.framework.domain.Handler;
import com.simpleweb.framework.domain.Param;
import com.simpleweb.framework.domain.View;
import com.simpleweb.framework.helper.BeanHelper;
import com.simpleweb.framework.helper.ConfigHelper;
import com.simpleweb.framework.helper.ControllerHelper;
import com.simpleweb.framework.util.CodeUtils;
import com.simpleweb.framework.util.JsonUtils;
import com.simpleweb.framework.util.ReflectionUtils;
import com.simpleweb.framework.util.StreamUtils;
import com.simpleweb.framework.util.StringUtils;

/**
 * 完成对请求的分发，结果的返回
 * 
 * @author Administrator
 *
 */
public class DispatchServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 初始化相关的helper
		HelperLoder.init();
		// 获servletContext 用于注册servlet
		ServletContext servletContext = config.getServletContext();

		// 动态的注册jsp Servlet
		ServletRegistration jspServlet = servletContext
				.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		// 处理静态资源的servlet
		ServletRegistration defaultServlet = servletContext
				.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 请求的类型
		String requestType = request.getMethod().toLowerCase();
		// 得到请求的路径
		String requestPath = request.getPathInfo();

		Handler handler = ControllerHelper.getHandler(requestType, requestPath);

		// 找到了相对应的处理器
		if (handler != null) {

			// 创建请求参数对象 先处理get请求
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();

				String paramValue = request.getParameter(paramName);

				paramMap.put(paramName, paramValue);
			}

			// 处理post请求

			String body = CodeUtils.decodeURL(StreamUtils.getString(request
					.getInputStream()));

			if (StringUtils.isNotEmpty(body)) {
				String[] params = body.split("&");

				if (params != null && params.length > 0) {
					for (String param : params) {
						String[] array = param.split("=");
						if (array != null && array.length > 0) {
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}

			// 要调用方法的参数

			Param param = new Param(paramMap);

			// 要调用的方法
			Method actionMethod = handler.getActionMethod();

			// 获取handler 对于的controller
			Class<?> controllerClass = handler.getControllerClass();
			Object controlleBean = BeanHelper.getBean(controllerClass);

			// 调用方法
			Object result = ReflectionUtils.invokeMathod(controlleBean,
					actionMethod, param);

			if (result instanceof View) {
				// 视图类型
				View view = (View) result;
				String path = view.getViewPath();
				if (path != null) {
					if (path.startsWith("/")) {
						response.sendRedirect(request.getContextPath() + path);
					} else {
						Map<String, Object> model = view.getModel();
						for (Map.Entry<String, Object> entry : model.entrySet()) {
							request.setAttribute(entry.getKey(),
									entry.getValue());
						}
						// TODO  why 
						request.getRequestDispatcher(
								ConfigHelper.getAppJspPath() + path).forward(
								request, response);
					}
				}

			} else {
				// 数据类型
				Data data = (Data) result;
				Object model = data.getModel();

				if (model != null) {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					String json = JsonUtils.toJson(data);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}

		}

	}

}
