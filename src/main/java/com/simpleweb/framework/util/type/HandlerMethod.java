package com.simpleweb.framework.util.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.simpleweb.framework.util.FileUtils;

/**
 *  用来获取一个方法的参数类型和参数名
 * @author zhushang 
 *
 */
public class HandlerMethod  {

	private  static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
	
	/**
	 * 
	 *  方法参数类型和名称
	 * @author Administrator
	 *
	 */
	public  static class  MethodParameterPair{
		
		  
		private Class<?> type;
		private String name;

		public MethodParameterPair( Class<?> type, String  name) {
			this.type = type;
			this.name = name;
		  }

		public Class<?> getType() {
			return type;
		}

		public String getName() {
			return name;
		}
		
		
	}
	
	public static  MethodParameterPair[] getParameterNames(Method method) {
		
	  String[] parameterNames = 	discoverer.getParameterNames(method);
	 if( parameterNames != null && parameterNames.length > 0 ) {
		     Class<?>[] types =  method.getParameterTypes();
		     MethodParameterPair[] methodParameterPairs = new MethodParameterPair[types.length];
		     for( int i= 0 ; i<types.length ; i++ ) {
		    	  methodParameterPairs[i] = new MethodParameterPair(types[i], parameterNames[i]);
		     }
		     return methodParameterPairs;
	 }
		return null;
	}

	public static  MethodParameterPair[] getParameterNames(Constructor<?> constructor) {
		 String[] parameterNames = 	discoverer.getParameterNames(constructor);
		 if( parameterNames != null && parameterNames.length > 0 ) {
			     Class<?>[] types =  constructor.getParameterTypes();
			     MethodParameterPair[] methodParameterPairs = new MethodParameterPair[types.length];
			     for( int i= 0 ; i<types.length ; i++ ) {
			    	  methodParameterPairs[i] = new MethodParameterPair(types[i], parameterNames[i]);
			     }
			     return methodParameterPairs;
		 }
			return null;
	}
	
	/*public static void main(String[] args) {
	 	LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		Method[] ms =   ReflectionUtils.class.getDeclaredMethods();
		for(Method m :ms ) {
			StringBuffer sb = new StringBuffer();
			String[] parameterNames = discoverer.getParameterNames(m);
			Class<?>[] paramType =  m.getParameterTypes();
			
			sb.append(m.getName()+"(");
			
			for(int i= 0; i < parameterNames.length ; i++ ) {
				sb.append(paramType[i].getCanonicalName() +" " +parameterNames[i]).append(",");
			}
			sb.append(")");
			System.out.println(sb.toString());
		}
		Method[] ms =   FileUtils.class.getDeclaredMethods();
		for(Method m :ms ) {
			MethodParameterPair[] methodParameterPairs = getParameterNames(m);
			System.out.println("OK");
		}
		
		
  }*/
	
}
