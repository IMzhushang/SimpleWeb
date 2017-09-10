package com.simpleweb.framework.orm;

import java.util.List;
import java.util.Map;

import com.simpleweb.framework.dao.DataBaseHelper;
import com.simpleweb.framework.dao.SqlHelper;

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

	/**
	 * 批量查询数据
	 * 
	 * @param clz
	 * @param condaition
	 * @param params
	 * @return
	 */
	public static  <T> List<T> queryList(Class<T> clz, String[] condaition,
			Object... params) {

		String whereConditio = null;
		String orderCondition = null;

		if (condaition != null && condaition.length > 0) {

			whereConditio = condaition[0];
			if (condaition.length > 1) {
				orderCondition = condaition[1];
			}
		}
		String sql = SqlHelper.generateSelectSql(clz, whereConditio,
				orderCondition);
		List<T> queryList = DataBaseHelper.queryList(clz, sql, params);
		return queryList;

	}

}
