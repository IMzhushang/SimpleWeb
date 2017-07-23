package com.simpleweb.framework.dao;

/**
 * 定义了数据访问的接口
 * 
 * @author Administrator
 *
 */
public interface DataAccessor {

	/**
	 * 查询一条数据
	 * 
	 * @param entry
	 * @param sql
	 * @param params
	 * @return
	 */
	<T> T selectOne(Class<T> entry, String sql, Object... params);

	/**
	 * 插入一条数据
	 * 
	 * @param object
	 * @return 影响数据的条数
	 */
	int insertOne(String  sql,Object ... params);
}
