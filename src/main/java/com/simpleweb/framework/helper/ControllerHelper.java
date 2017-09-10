package com.simpleweb.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.simpleweb.framework.annotation.Action;
import com.simpleweb.framework.domain.Handler;
import com.simpleweb.framework.domain.Request;

/**
 * 完成对 request 和 handler 之间的映射
 * 
 * 
 * @author Administrator
 *
 */
public final class ControllerHelper {

	private static final Map<Request, Handler> REQUEST_HANDLER = new HashMap<Request, Handler>();

	static {
		// 遍历所有的controller 类 ，拿到标注有Action的方法
		Set<Class<?>> controllerClassSet = ClassHelper
				.getControllerClassSet();
		if (controllerClassSet != null && controllerClassSet.size() > 0) {
			for (Class<?> clazz : controllerClassSet) {
				Method[] declaredMethods = clazz.getDeclaredMethods();
				if (declaredMethods != null && declaredMethods.length > 0) {
					for (int i = 0; i < declaredMethods.length; i++) {
						Method m = declaredMethods[i];
						if (m.isAnnotationPresent(Action.class)) {
							Action action = m.getAnnotation(Action.class);
							// 请求映射的路径
							String requestMapping = action.value();

							if (requestMapping != null && requestMapping.length() > 0 ) {
								String[] split = requestMapping.split(":");
								String requestType = split[0];
								String requestPath = split[1];

								Request request = new Request(requestType,
										requestPath);
								Handler handler = new Handler(clazz, m);

								REQUEST_HANDLER.put(request, handler);
							}

						}
					}
				}
			}
		}
	}

	/**
	 * 拿到处理器
	 * 
	 * @param requestType
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestType, String requestPath) {
		Request request = new Request(requestType, requestPath);
		return REQUEST_HANDLER.get(request);
	}

}
