package com.simpleweb.framework.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 *  对Bean 属性的操作
 * @author Administrator
 *
 */
public class BeanUtils {
	  
	 /**
	  *  填充Bean
	  * @param clazz
	  * @param field
	  */
        public static Object FillBean(Class<?> clazz ,Map<String, Object> field) {
        	 // 拿到属性直接填充
        	try {
				Object obj = clazz.newInstance();
			   
				for(Map.Entry<String, Object> entry : field.entrySet()) {
					Field  f =   clazz.getDeclaredField(entry.getKey());
					String className = f.getType().getName();
					f.setAccessible(true);
					Object param = null;
					Object value  =   entry.getValue();
					// 判断f 的类型
					if(f.getType().isPrimitive()  || className.equals(Integer.class.getName()) || className.equals(Double.class.getName() )
							  ||  className.equals(Float.class.getName() ) || className.equals(Long.class.getName() ) || className.equals(String.class.getName() )
							  ||  className.equals( Boolean.class.getName()) ) {
						// 是否是基本类型
						  if( className.equals(Integer.class.getName()) || className.equals(int.class.getName())) {
							  param = Integer.valueOf((String)value);
						  }else if( className.equals(Double.class.getName() ) || className.equals(double.class.getName() ) ) {
							  param =Double.valueOf((String) value);
						  }else if( className.equals(Float.class.getName() ) || className.equals(float.class.getName() ) ) {
							
							  param =Float.valueOf((String) value);
						  }else if( className.equals(Long.class.getName() ) || className.equals(long.class.getName() )) {
							  param =Long.valueOf((String)value);
						  }else if( className.equals(String.class.getName() )) {
							  param =(String) value;
						  }else if( className.equals( Boolean.class.getName()) || className.equals(Boolean.class.getName())) {
							  param = Boolean.valueOf((String) value);
						  }else {
							  //报错
							  throw new  RuntimeException("can not support this type"+ className);
						  }
					}else if( f.getType().isAssignableFrom(List.class) ) {
						// 是否是List
						param =  (List<String>)value ;
					}else if(f.getType().isArray() ) {
						// 是否是数组
						 Class<?> arrayType =   f.getType().getComponentType();
						 String componentType = arrayType.getName();
						 List<String> oneField = (List<String>) value;
						 
						 if( componentType.equals(Integer.class.getName()) || componentType.equals(int.class.getName())) {
							  Integer[] newFields  = new Integer[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
								   newFields[index] = Integer.valueOf(oneField.get(index));
							  }
							  param = newFields;
						  }else if( componentType.equals(Double.class.getName() ) || componentType.equals(double.class.getName() ) ) {
							  Double[] newFields  = new Double[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
								  newFields[index] = Double.valueOf(oneField.get(index));
							  }
							  param = newFields;
						  }else if( componentType.equals(Float.class.getName() ) || componentType.equals(float.class.getName() ) ) {
							  Float[] newFields  = new Float[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
								  newFields[index] = Float.valueOf(oneField.get(index));
							  }
							  param = newFields;
						  }else if( componentType.equals(Long.class.getName() ) || componentType.equals(long.class.getName() )) {
							  Long[] newFields  = new Long[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
								  newFields[index] = Long.valueOf(oneField.get(index));
							  }
							  param = newFields;
						  }else if( componentType.equals(String.class.getName() )) {
							  String[] newFields  = new String[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
								  newFields[index] = oneField.get(index);
							  }
							  param = newFields;
						  }else if( componentType.equals( Boolean.class.getName()) || componentType.equals(Boolean.class.getName())) {
							  Boolean[] newFields  = new Boolean[oneField.size()];
							  for(int index=0 ; index<oneField.size() ; index++ ) {
									    newFields[index] = Boolean.valueOf(oneField.get(index));
							  }
							  param = newFields;
						  }else {
							  //报错
							  throw new  RuntimeException("can not support this type"+ className);
						  }
					}else {
						// 不支持的类型
						throw new  RuntimeException("can not support this type"+ className);
					}
					f.set(obj, param);
				}
				return obj;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
        	return null;
        }
        
 
        
        
}
