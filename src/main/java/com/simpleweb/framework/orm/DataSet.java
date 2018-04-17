package com.simpleweb.framework.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.simpleweb.framework.dao.DataBaseHelper;
import com.simpleweb.framework.dao.SqlHelper;
import com.simpleweb.framework.domain.Page;

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
	

	/**
	 * 查询数量
	 * @param clz
	 * @return
	 */
	public static <T> long queryCount(Class<T> clz,Map<String, Object> condition) {
		
     String sql =    SqlHelper.genetateCount(clz, condition);

		return   DataBaseHelper.queryCount(clz, sql);
	}
	
	/**
	 *  更新记录
	 * @param clz
	 * @param update
	 * @param condition
	 * @return
	 */
	public static <T> int update (Class<T> clz,Map<String, Object> update,Map<String, Object> condition) {
		
		if( update == null  || update.size() < 1  ) {
			throw new RuntimeException("update field is null");
		}
		
		List<String> updateFiedName = new ArrayList<String>();
		List<Object> updateFiedValue = new ArrayList<Object>();
		
		for(Map.Entry<String, Object> entry: update.entrySet() ) {
			updateFiedName.add(entry.getKey());
			updateFiedValue.add(entry.getValue());
		}

	  String sql = 	SqlHelper.generateUpdata(clz, updateFiedName)+" " + SqlHelper.generateWhereCondition(condition) ;
		
		return DataBaseHelper.update(clz, sql, updateFiedValue.toArray());
	}
	
	/**
	 *  分页查询
	 * @param page
	 * @param pageSize
	 * @param clz
	 * @param condition
	 * @param order
	 * @return
	 */
	public static <T> Page<T>  queryByPage(int  page,int pageSize,Class<T> clz,Map<String, Object> condition,String order) {
		
		Page< T> dataPage = new Page<T>();
		dataPage.setCurPageIndex(page);
		dataPage.setOffset(pageSize);
		
		//出查询总数量
	  long count = 	queryCount(clz, condition);
	  dataPage.setTotalCount(count);
	 
	  if( count  <= 0 ) {
		  return dataPage;
	  }
	  dataPage.setTotalPageIndex(Math.ceil(count*1.0 / pageSize));
	  String sql = 	SqlHelper.generateSelectSql(clz, condition, order)+" " + SqlHelper.genetateLimit((page -1 )*pageSize, pageSize);
	  List<T> data =  DataBaseHelper.queryByPage(clz, sql);
	  dataPage.setData(data);
	  
		return dataPage;
	}
	

	
	
}
