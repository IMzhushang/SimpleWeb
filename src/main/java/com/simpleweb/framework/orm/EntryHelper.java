package com.simpleweb.framework.orm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.simpleweb.framework.helper.ClassHelper;
import com.simpleweb.framework.orm.annotation.Column;
import com.simpleweb.framework.orm.annotation.Id;
import com.simpleweb.framework.orm.annotation.Table;

/**
 * 提供 类 --- 表的装换
 * 
 * @author Administrator
 *
 */
public class EntryHelper {

	// 类名 ---- 表名的映射

	private static final Map<String, String> ENTRYNAME2TABLENAMEMAP = new HashMap<String, String>();

	// 表名 ---- （字段名 --- 表列名）

	private static final Map<String, Map<String, String>> FILEDNAME2CLOUMNNAME = new HashMap<String, Map<String, String>>();

	// 主键的映射
	private static final Map<String, Map<String, String>> PERMARYKEYMAP = new HashMap<String, Map<String, String>>();

	static {
		// 拿到Table 注解的类
		Set<Class<?>> classSetByAnnotation = ClassHelper
				.getClassSetByAnnotation(Table.class);
		// 初始化 类名 ---- 表名的映射
		// 初始化 表名 ---- （字段名 --- 表列名）
		for (Class<?> clz : classSetByAnnotation) {
			// 初始化 类名 ---- 表名的映射
			initTableNameMap(clz);
			// 初始化 表名 ---- （字段名 --- 表列名）
			itnitCloumNameMap(clz);
		}
	}

	private static void initTableNameMap(Class<?> clz) {
		// 判断是否有Tbale注解
		// TODO 驼峰命名法
		if (clz.isAnnotationPresent(Table.class)) {
			Table annotation = clz.getAnnotation(Table.class);
			String tableName = annotation.value();
			if (tableName != null && tableName.length() > 0) {
				ENTRYNAME2TABLENAMEMAP.put(clz.getCanonicalName(), tableName);
			}
		}
	}

	private static void itnitCloumNameMap(Class<?> clz) {
		// 没有表映射
		if (ENTRYNAME2TABLENAMEMAP.get(clz.getCanonicalName()) == null) {
			return;
		}
		Map<String, String> filedMap = new HashMap<String, String>();
		Map<String, String> permaryKeyMap = new HashMap<String, String>();
		// 拿到所有的字段
		Field[] declaredFields = clz.getDeclaredFields();
		if (declaredFields != null) {
			for (Field field : declaredFields) {
				field.setAccessible(true);
				// 普通的字段
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					if (column.value() != null) {
						filedMap.put(field.getName(), column.value());
					}
				} else if (field.isAnnotationPresent(Id.class)) {
					Id id = field.getAnnotation(Id.class);
					if (id.value() != null) {
						permaryKeyMap.put(field.getName(), id.value());
						filedMap.put(field.getName(), id.value());
					}
				}

			}
		}

		FILEDNAME2CLOUMNNAME.put(clz.getCanonicalName(), filedMap);
		PERMARYKEYMAP.put(clz.getCanonicalName(), permaryKeyMap);
	}

	/**
	 * 拿到对应的表名
	 * 
	 * @param clz
	 * @return
	 */
	public static String getTableName(Class<?> clz) {
		return ENTRYNAME2TABLENAMEMAP.get(clz.getCanonicalName());
	}

	/**
	 * 拿到字段的映射 字段 --- 列名
	 * 
	 * @param clz
	 * @return
	 */
	public static Map<String, String> getFiledMap(Class<?> clz) {
		return FILEDNAME2CLOUMNNAME.get(clz.getCanonicalName());
	}

	/**
	 * 拿到列的映射 列名 --- 字段
	 * 
	 * @param clz
	 * @return
	 */
	public static Map<String, String> getColumnMap(Class<?> clz) {

		Map<String, String> filedMap = getFiledMap(clz);
		Map<String, String> columnMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : filedMap.entrySet()) {
			columnMap.put(entry.getValue(), entry.getKey());
		}

		return columnMap;

	}

	/**
	 * 根据字段名拿到对应的列名
	 * 
	 * @param entityClass
	 * @param fieldName
	 * @return
	 */
	public static String getColumnName(Class<?> entityClass, String fieldName) {
		Map<String, String> filedMap = FILEDNAME2CLOUMNNAME.get(entityClass
				.getCanonicalName());
		if (filedMap != null) {
			return filedMap.get(fieldName);
		}
		return null;
	}

}
