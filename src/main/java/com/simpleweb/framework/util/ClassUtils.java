package com.simpleweb.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 读取类问件的工具类，读取类文件的二进制流
 * 
 * @author Administrator
 *
 */
public class ClassUtils {

	/**
	 * 获取到类加载器
	 * 
	 * @return 返回的是上下文类加载器
	 */
	public static ClassLoader getClassLoader() {

		return Thread.currentThread().getContextClassLoader();

	}

	/**
	 * 获得指定包路径下的所有的类文件
	 * 
	 * @param packegeName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packegeName) {

		Set<Class<?>> classSet = new HashSet<Class<?>>();

		try {
			// 读取一个目录下的文件
			Enumeration<URL> resources = getClassLoader().getResources(
					packegeName.replace(".", "/"));

			while (resources.hasMoreElements()) {
				URL nextElement = resources.nextElement();
				if (nextElement != null) {
					String protocol = nextElement.getProtocol();
					if (protocol.equals("file")) {
						// 普通文件
						String packagePath = nextElement.getPath().replace(
								"%20", " ");
						addClass(classSet, packagePath, packegeName);
					} else if (protocol.equals("jar")) {
						// jar 包
						JarURLConnection jarURLConnection = (JarURLConnection) nextElement
								.openConnection();

						if (jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> jarEntrys = jarFile
										.entries();
								while (jarEntrys.hasMoreElements()) {
									JarEntry jaeEntry = jarEntrys.nextElement();
									String jarEntryName = jaeEntry.getName();
									if (jarEntryName.endsWith(".class")) {
										String className = jarEntryName

										.substring(0,
												jarEntryName.lastIndexOf("."))
												.replace("/", ".");
										addClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return classSet;

	}

	private static void addClass(Set<Class<?>> classSet, String className) {
		Class<?> clazz = loadClass(className, false);
		classSet.add(clazz);
	}

	/**
	 * 
	 * @param classSet
	 * @param packagePath  A/B/C
	 * @param packegeName a.b.c
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath,
			String packegeName) {

		/* 过滤一下子文件 */
		File[] files = new File(packagePath).listFiles(new FileFilter() {

			public boolean accept(File file) {

				boolean result = (file.isFile() && file.getName().endsWith(
						".class"))
						|| (file.isDirectory());

				return result;
			}
		});

		for (File file : files) {

			String fileName = file.getName();

			if (file.isDirectory()) {
				// 是一个文件夹则递归处理
				String subPackagePath = fileName;

				if (StringUtils.isNotEmpty(subPackagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}

				String subPackageName = fileName;

				if (StringUtils.isNotEmpty(subPackageName)) {
					subPackageName = packegeName + "." + subPackageName;
				}

				addClass(null, subPackagePath, packegeName);

			} else {
				String className = fileName.substring(0,
						fileName.lastIndexOf("."));

				if (StringUtils.isNotEmpty(className)) {
					className = packegeName + "." + className;
				}
				addClass(classSet, className);
			}
		}

	}

	/**
	 * 加载指定的类 ( 通过反射 Class.forName)
	 * 
	 * @param className
	 * @param isInitialized
	 *            是否初始化
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {

		try {
			Class<?> clazz = Class.forName(className, isInitialized,
					getClassLoader());
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

}
