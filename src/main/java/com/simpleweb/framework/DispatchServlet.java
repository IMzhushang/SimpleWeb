package com.simpleweb.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simpleweb.framework.domain.Data;
import com.simpleweb.framework.domain.FileParam;
import com.simpleweb.framework.domain.FormParam;
import com.simpleweb.framework.domain.Handler;
import com.simpleweb.framework.domain.View;
import com.simpleweb.framework.helper.BeanHelper;
import com.simpleweb.framework.helper.ConfigHelper;
import com.simpleweb.framework.helper.ControllerHelper;
import com.simpleweb.framework.helper.UploadHelper;
import com.simpleweb.framework.mvc.ViewResolver;
import com.simpleweb.framework.util.BeanUtils;
import com.simpleweb.framework.util.ClassUtils;
import com.simpleweb.framework.util.JsonUtils;
import com.simpleweb.framework.util.ReflectionUtils;
import com.simpleweb.framework.util.RequestHelper;
import com.simpleweb.framework.util.type.HandlerMethod.MethodParameterPair;

/**
 * 完成对请求的分发，结果的返回
 * 
 * @author Administrator
 *
 */
// @WebServlet( urlPatterns="/", loadOnStartup=0)
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.err.println("---------simple web start------------------");
		System.err.println("---------simple web start------------------");
		System.err.println("---------simple web start------------------");
		System.err.println("---------simple web start------------------");
		System.err.println("---------simple web start------------------");
		System.err.println("---------simple web start------------------");
		// 初始化相关的helper
		HelperLoder.init();
		// 获servletContext 用于注册servlet
		ServletContext servletContext = config.getServletContext();

		// 动态的注册jsp Servlet
		if(ConfigHelper.getAppJspPath() != null ) {
			ServletRegistration jspServlet = servletContext
					.getServletRegistration("jsp");
			jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		}

		// 处理静态资源的servlet
		if( ConfigHelper.getAppAssetPath() != null ) {
			ServletRegistration defaultServlet = servletContext
					.getServletRegistration("default");
			defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		}
	

		// 文件上传的初始化
		UploadHelper.init(servletContext);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 请求的类型
		String requestType = request.getMethod().toLowerCase();
		// 得到请求的路径
		String requestPath = request.getPathInfo();

		if (requestPath.equals("/favicon.ico")) {
			return;
		}

		Handler handler = ControllerHelper.getHandler(requestType, requestPath);

		System.out.println("------>>>>> " + request.getRequestURL().toString());

		// 找到了相对应的处理器
		if (handler != null) {

			// 创建请求参数对象 先处理get请求
			/*
			 * Map<String, Object> paramMap = new HashMap<String, Object>();
			 * Enumeration<String> parameterNames = request.getParameterNames();
			 * 
			 * while (parameterNames.hasMoreElements()) { String paramName =
			 * parameterNames.nextElement();
			 * 
			 * String paramValue = request.getParameter(paramName);
			 * 
			 * paramMap.put(paramName, paramValue); }
			 * 
			 * // 处理post请求
			 * 
			 * String body = CodeUtils.decodeURL(StreamUtils.getString(request
			 * .getInputStream()));
			 * 
			 * if (StringUtils.isNotEmpty(body)) { String[] params =
			 * body.split("&");
			 * 
			 * if (params != null && params.length > 0) { for (String param :
			 * params) { String[] array = param.split("="); if (array != null &&
			 * array.length > 0) { String paramName = array[0]; String
			 * paramValue = array[1]; paramMap.put(paramName, paramValue); } } }
			 * }
			 */
			FormParam param;
			if (UploadHelper.isMultipart(request)) {
				param = UploadHelper.createParam(request);
			} else {
				param = RequestHelper.createParam(request);
			}

			// 要调用方法的参数

			// 要调用的方法
			Method actionMethod = handler.getActionMethod();

			// 获取handler 对于的controller
			Class<?> controllerClass = handler.getControllerClass();
			Object controlleBean = BeanHelper.getBean(controllerClass);

			// 调用方法
			// 目前的两个问题
			// 1. 方法参数的顺序问题
			// 2. 参数类型问题
			
			Map<String, Object> paramList = param.getFieldMap();
			if( param.getFileMap() != null && param.getFieldMap().size() > 0 ) {
			    paramList.putAll(param.getFileMap());
			}
			Object result =   invokeHander(controlleBean, handler, request, response,paramList);
			
			if (result instanceof View) {
				// 视图类型
				View view = (View) result;
				String path = view.getViewPath();

				// 判断是什么视图解析器
				ViewResolver viewResolver = getViewResolver();
				if (viewResolver == null) {
					return;
				}

				// TODO 视图解析器待修改
				request.setCharacterEncoding("UTF-8");
		         response.setContentType("text/html;charset=UTF-8");
				if (ConfigHelper
						.getTemplateResolver()
						.equals("com.simpleweb.framework.mvc.impl.ThymeleafViewResolver")) {
					// ThymeleafView
					try {
						viewResolver.resovler(request, response, path,
								view.getModel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (ConfigHelper
						.getTemplateResolver()
						.equals("com.simpleweb.framework.mvc.impl.VelocityViewResolver")) {
					try {
						viewResolver.resovler(request, response, path,
								view.getModel());
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					// jsp
					if (path != null) {
						if (path.startsWith("/")) {
							response.sendRedirect(request.getContextPath()
									+ path);
						} else {
							Map<String, Object> model = view.getModel();
							for (Map.Entry<String, Object> entry : model
									.entrySet()) {
								request.setAttribute(entry.getKey(),
										entry.getValue());
							}
							// TODO why
							request.getRequestDispatcher(
									ConfigHelper.getAppJspPath() + path
											+ ".jsp")
									.forward(request, response);
						}
					}

				}

			} else {
				// 数据类型
				System.err.println("-----Response--- Data");
				Data data = (Data) result;
				Object model = data.getModel();

				if (model != null) {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					String json = JsonUtils.toJson(model);
					System.err.println("-----Response---" + json);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}

		}

	}

	public ViewResolver getViewResolver() {
		String templateResolver = ConfigHelper.getTemplateResolver();
		try {
			return (ViewResolver) Class.forName(templateResolver).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  执行方法
	 * @param controllerBean
	 * @param handler
	 * @param request
	 * @param response
	 * @return 
	 */
	public Object  invokeHander(Object controllerBean,Handler handler,HttpServletRequest request,
			HttpServletResponse response ,Map<String, Object> fields) {
		   // 拿到要执行方法的参数
		MethodParameterPair[] methodParameterPairs = handler.getParameter();
		if( methodParameterPairs == null || methodParameterPairs.length < 1 ) {
			// 报错
			throw new RuntimeException(" methodParameterPairs is null");
		}
		Object[] params  = new Object[methodParameterPairs.length];
		
		for( int i= 0 ; i<methodParameterPairs.length ; i++ ) {
			    Class<?> type = methodParameterPairs[i].getType();
			    String fieldName = methodParameterPairs[i].getName();
			    String className = type.getName();
			    // 支持的几个基本类型
		/*	  if( className.equals(Integer.class.getName()) || className.equals(int.class.getName())) {
				  
			  }else if( className.equals(Double.class.getName() ) || className.equals(double.class.getName() ) ) {
				  
			  }else if( className.equals(Float.class.getName() ) || className.equals(float.class.getName() ) ) {
				  
			  }else if( className.equals(Long.class.getName() ) || className.equals(long.class.getName() )) {
				  
			  }else if( className.equals(String.class.getName() )) {
				  
			  }else {
				  // 自定义类型
			  }*/
			  
			  if( type.isArray() ) {
				  // 数组类型 只支持基本类型的一维数组
				  //  判断具体是什么类型的数组
				 Class<?> arrayType =   type.getComponentType();
				 String componentType = arrayType.getName();
				 List<String> oneField = (List<String>) fields.get(fieldName);
				 
				 if( componentType.equals(Integer.class.getName()) || componentType.equals(int.class.getName())) {
					  Integer[] newFields  = new Integer[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
						   newFields[index] = Integer.valueOf(oneField.get(index));
					  }
					  params[i] = newFields;
				  }else if( componentType.equals(Double.class.getName() ) || componentType.equals(double.class.getName() ) ) {
					  Double[] newFields  = new Double[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
						  newFields[index] = Double.valueOf(oneField.get(index));
					  }
					  params[i] = newFields;
				  }else if( componentType.equals(Float.class.getName() ) || componentType.equals(float.class.getName() ) ) {
					  Float[] newFields  = new Float[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
						  newFields[index] = Float.valueOf(oneField.get(index));
					  }
					  params[i] = newFields;
				  }else if( componentType.equals(Long.class.getName() ) || componentType.equals(long.class.getName() )) {
					  Long[] newFields  = new Long[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
						  newFields[index] = Long.valueOf(oneField.get(index));
					  }
					  params[i] = newFields;
				  }else if( componentType.equals(String.class.getName() )) {
					  String[] newFields  = new String[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
						  newFields[index] = oneField.get(index);
					  }
					  params[i] = newFields;
				  }else if( componentType.equals( Boolean.class.getName()) || componentType.equals(Boolean.class.getName())) {
					  Boolean[] newFields  = new Boolean[oneField.size()];
					  for(int index=0 ; index<oneField.size() ; index++ ) {
							    newFields[index] = Boolean.valueOf(oneField.get(index));
					  }
					  params[i] = newFields;
				  }else {
					  //报错
					  throw new  RuntimeException("can not support this type"+ className);
				  }
				 
				 
			  }else if( type.isAssignableFrom(List.class)) {
				  // List集合 只支持String的List集合
				  // 判断List的泛型
				params[i] =  (List<String>) fields.get(fieldName);
			  }else if (type.isPrimitive() || className.equals(Integer.class.getName()) || className.equals(Double.class.getName() )
					  ||  className.equals(Float.class.getName() ) || className.equals(Long.class.getName() ) || className.equals(String.class.getName() )
					  ||  className.equals( Boolean.class.getName())) {
				  // 基本类型
				  if( className.equals(Integer.class.getName()) || className.equals(int.class.getName())) {
					  params[i] = Integer.valueOf((String) fields.get(fieldName));
				  }else if( className.equals(Double.class.getName() ) || className.equals(double.class.getName() ) ) {
					  params[i] =Double.valueOf((String) fields.get(fieldName));
				  }else if( className.equals(Float.class.getName() ) || className.equals(float.class.getName() ) ) {
					
					  params[i] =Float.valueOf((String) fields.get(fieldName));
				  }else if( className.equals(Long.class.getName() ) || className.equals(long.class.getName() )) {
					  
					  params[i] =Long.valueOf((String) fields.get(fieldName));
				  }else if( className.equals(String.class.getName() )) {
					  params[i] =(String) fields.get(fieldName);
				  }else if( className.equals( Boolean.class.getName()) || className.equals(Boolean.class.getName())) {
					  params[i] = Boolean.valueOf((String) fields.get(fieldName));
				  }else {
					  //报错
					  throw new  RuntimeException("can not support this type"+ className);
				  }
			  }else {
				  // 自定义类型
				  // 判断 是否是 request  response session
				  if( className.equals(HttpServletRequest.class.getName()) ) {
					  params[i] = request;
				  }else if( className.equals(HttpServletResponse.class.getName())) {
					  params[i] = response;
				  }else if( className.equals(HttpSession.class.getName() ) ) {
					  params[i] = request.getSession();
				  }else if(className.equals(FileParam.class.getName())) {
					  List<FileParam> fileParams = (List<FileParam>) fields.get(fieldName);
					  params[i] = fileParams.get(0);
				  }else {
					  params[i] = BeanUtils.FillBean(type, fields);
				  }
			  }
			  
		}
		
	 return 	ReflectionUtils.invokeMathod(controllerBean, handler.getActionMethod(), params);
	}
}
