package com.simpleweb.framework.util.type;

import java.lang.reflect.Member;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.simpleweb.framework.util.ClassUtils;


public class ParameterNameDiscoveringVisitor extends ClassVisitor {

	private Class<?> clazz;
	private Map<Member, String[]> mumberMap;
	
	private static final String STATIC_CLASS_INIT = "<clinit>";

	public ParameterNameDiscoveringVisitor(Class<?> clazz,Map<Member, String[]> mumberMap) {
		super(Opcodes.ASM5);
		this.clazz = clazz;
		this.mumberMap = mumberMap;
	}
	
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		
		// 判断是否是符合条件的方法  判断是否是桥方法或者是默认的构造方法
		if( !isSyntheticOrBridged(access) && !STATIC_CLASS_INIT.equals(name) ) {
			 // 符合条件的方法
			return new LocalVariableTableVisitor(clazz, mumberMap, name, desc, isStatic(access));
		}
		
		return null;
	}

	/**
	 *  判断是否是桥方法
	 * @param access
	 * @return
	 */
	public Boolean isSyntheticOrBridged (int access) {
		return  ( (Opcodes.ACC_SYNTHETIC & access) | (Opcodes.ACC_BRIDGE & access)) > 0 ;
	}
	
	public  Boolean   isStatic(int access) {
		return (Opcodes.ACC_STATIC & access) > 0 ;
	}
	
	
	static class LocalVariableTableVisitor extends MethodVisitor {

		private static final String CONSTRUCTOR = "<init>";

		private  final Class<?> clazz;
		private  final Map<Member, String[]> mumberMap;
		private  final String name;
		
		private  final Boolean isStatic;
		
		private   final Type[] args;
		private final   String[] paramsterNames;
		
		private  final int[] lvtIndex ;
		
	   private  boolean hasLvt  = false;

		public LocalVariableTableVisitor(Class<?> clazz,Map<Member, String[]> mumberMap,String name, String desc,Boolean isStatic) {
			super(Opcodes.ASM5);
			this.clazz = clazz;
			this.mumberMap = mumberMap;
			this.name = name;
			this.isStatic = isStatic;
		

			this.args = Type.getArgumentTypes(desc);
			this.paramsterNames = new String[this.args.length];
			// 计算字段的索引位置
			this.lvtIndex = computeLvtSlotIndices(isStatic, args);
	
		}
		
		@Override
		public void visitLocalVariable(String name, String desc,
				String signature, Label start, Label end, int index) {
			 // 说明有lvt 
			hasLvt =  true ;
			for ( int i = 0 ; i < this.args.length ; i++ ) {
				if( this.lvtIndex[i] == index ) {
					this.paramsterNames[i] = name;
				}
			}
		}
		
		@Override
		public void visitEnd() {
			 if( hasLvt ) {
				  this.mumberMap.put(resolveMember(name), paramsterNames);
			 }
		}
		
		
		private Member  resolveMember(String name) {
			  ClassLoader classLoader = this.clazz.getClassLoader();
			  Class[] classType  = new   Class[this.args.length];
			  
			  for( int i = 0 ; i  < this.args.length ; i++ ) {
				  classType[i] = ClassUtils.resolveClassName(this.args[i].getClassName(),classLoader);
			  }
			  
			  // 判断是构造方法还是普通的方法
			  try {
					if (CONSTRUCTOR.equals(this.name)) {
						return this.clazz.getDeclaredConstructor(classType);
					}
					return this.clazz.getDeclaredMethod(this.name, classType);
				}
				catch (NoSuchMethodException ex) {
					throw new IllegalStateException("Method [" + name +
							"] was discovered in the .class file but cannot be resolved in the class object", ex);
				}
			  }
			  
		}
		
		
		public  static  int[]  computeLvtSlotIndices(Boolean  isStatic,Type[]  paramTypes) {
			int[] lvtIndex = new int[paramTypes.length];
			int nextIndex = (isStatic ? 0 : 1);
			for (int i = 0; i < paramTypes.length; i++) {
				lvtIndex[i] = nextIndex;
				if (isWideType(paramTypes[i])) {
					nextIndex += 2;
				}
				else {
					nextIndex++;
				}
			}
			return lvtIndex;
		}
		
		/**
		 *  判断是否是占两个字节的长类型
		 *  long 和  double
		 * @param type
		 * @return
		 */
		public static Boolean  isWideType(Type type) {
			return (type == Type.LONG_TYPE || type == Type.DOUBLE_TYPE);
		}
	
	}
	
