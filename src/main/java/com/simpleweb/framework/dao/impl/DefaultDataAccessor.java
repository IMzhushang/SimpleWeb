package com.simpleweb.framework.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.simpleweb.framework.dao.DataAccessor;
import com.simpleweb.framework.dao.DataBaseHelper;
import com.simpleweb.framework.orm.EntryHelper;

/**
 * 数据访问器的默认实现 使用DbUtils
 * 
 * BasicRowProcessor RowProcessor接口的基本实现类。 BeanProcessor
 * BeanProcessor匹配列明到Bean属性名，并转换结果集列到Bean对象的属性中。 DbUtils 一个JDBC辅助工具集合。
 * ProxyFactory 产生JDBC接口的代理实现。 QueryLoader 属性文件加载器，主要用于加载属性文件中的 SQL 到内存中。
 * QueryRunner 使用可插拔的策略执行SQL查询并处理结果集。 ResultSetIterator 包装结果集为一个迭代器
 * 
 * @author Administrator
 *
 */
public class DefaultDataAccessor implements DataAccessor {

	/**
	 * DBUtils 操作数据库
	 */
	private final QueryRunner queryRunner;

	public DefaultDataAccessor() {
		DataSource dataSource = DataBaseHelper.getDataSource();
		queryRunner = new QueryRunner(dataSource);
	}

	public <T> T selectOne(Class<T> entry, String sql, Object... params) {

		System.err.println("sql ----- > " + sql);

		T result = null;
		try {
			Map<String, String> columnMap = EntryHelper.getColumnMap(entry);
			if (columnMap != null) {
				result = queryRunner.query(sql, new BeanHandler<T>(entry,
						new BasicRowProcessor(new BeanProcessor(columnMap))),
						params);
			} else {
				result = queryRunner.query(sql, new BeanHandler<T>(entry),
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int insertOne(String  sql,Object ...param ) {
		System.err.println("sql ----- > " + sql);
		int  count = 0;
		try {
			count = queryRunner.update(DataBaseHelper.getConnection(), sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
