package com.simpleweb.framework.util.type;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;

import com.simpleweb.framework.util.ClassUtils;

/**
 *  从本地class文件中解析出参数名
 * @author  zhushang
 *
 */
public class LocalVariableTableParameterNameDiscoverer  implements ParameterNameDiscoverer {

	private final Map<Class<?>, Map<Member, String[]>> parameterNamesCache =
			new ConcurrentHashMap<Class<?>, Map<Member, String[]>>(32);
	
	// marker object for classes that do not have any debug info
		private static final Map<Member, String[]> NO_DEBUG_INFO_MAP = Collections.emptyMap();
	
	public String[] getParameterNames(Method method) {
		
		// 判断是不是桥方法，拿到桥方法
		// TODO 桥方法的判断是在是太复杂了，等待后续迭代
		
		Class<?> declaringClass = method.getDeclaringClass();
		Map<Member, String[]> map = this.parameterNamesCache.get(declaringClass);
		if (map == null) {
			map = inspectClass(declaringClass);
			this.parameterNamesCache.put(declaringClass, map);
		}
		if (map != NO_DEBUG_INFO_MAP) {
			return map.get(method);
		}
		return null;
	}

	public String[] getParameterNames(Constructor<?> constructor) {
		Class<?> declaringClass = constructor.getDeclaringClass();
		Map<Member, String[]> map = this.parameterNamesCache.get(declaringClass);
		if (map == null) {
			map = inspectClass(declaringClass);
			this.parameterNamesCache.put(declaringClass, map);
		}
		if (map != NO_DEBUG_INFO_MAP) {
			return map.get(constructor);
		}
		return null;
	}
	
	
	private  Map<Member,  String[]>  inspectClass(Class<?> clazz) {
		
		// 根据类名拿到class文件的输入流
		InputStream in = clazz.getResourceAsStream(ClassUtils.getClassFileName(clazz));
		
		if( in == null ) {
			throw new RuntimeException("can not find file");
		}
		try {
			ClassReader classReader = new ClassReader(in);
			Map<Member, String[]> map = new ConcurrentHashMap<Member, String[]>();
			classReader.accept(new ParameterNameDiscoveringVisitor(clazz, map), 0);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if( in != null ) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
     
}
