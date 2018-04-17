package com.simpleweb.framework.dao;

import java.util.List;

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
	
	
	public <T> List<T> queryList(Class<T> entry, String sql, Object... params) ;
	
	/**
	 *  更具条件查询数量
	 * @param entry
	 * @param sql
	 * @return
	 */
	public <T> long  queryCount(Class<T> entry, String sql) ;
	
	/**
	 *  更新记录
	 * @param clz
	 * @param sql
	 * @param params
	 * @return
	 */
	public  <T> int  update(Class<T> clz,String sql,Object ... params) ;
	
	/**
	 *  分页查询
	 * @param clz
	 * @param sql
	 * @return
	 */
	public  <T> List<T>  queryByPage(Class<T> clz, String sql) ;
	
}


