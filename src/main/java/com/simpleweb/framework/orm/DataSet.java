package com.simpleweb.framework.orm;

import java.util.Map;

import com.simpleweb.framework.dao.DataBaseHelper;
import com.simpleweb.framework.dao.SqlHelper;
import com.simpleweb.framework.helper.DatabaseHelper;

/**
 * 数据库的相关操作
 * 
 * @author Administrator
 *
 */
public class DataSet {

	/**
	 * 查询单条数据 并转换为实体类
	 * 
	 * @param clz
	 * @param condaition
	 * @param params
	 * @return
	 */
	public static <T> T selectOne(Class<T> clz, String condaition,
			Object... params) {

		// 动态的生成SQL
		String sql = SqlHelper.generateSelectSql(clz, condaition, null);

		T selectOne = DataBaseHelper.selectOne(clz, sql, params);

		return selectOne;

	}

	/**
	 * 插入数据库
	 * 
	 * @param bean
	 */
	public static int insertOne(Class<?> clz, Map<String, Object> params) {
		if (params == null || params.size() < 1) {
			return -1;
		}
		String sql = SqlHelper.generateInsertSql(clz, params.keySet());
		int insertOne = DataBaseHelper
				.insertOne(sql, params.values().toArray());
		return insertOne;
	}

}
