package com.simpleweb.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.simpleweb.framework.dao.impl.DefaultDataAccessor;
import com.simpleweb.framework.ds.DataSourceFactory;
import com.simpleweb.framework.ds.impl.DbcpDataScourceFactory;

/**
 * 提供数据库的相关操作 具体是由dataaccessor实现的
 * 
 * @author Administrator
 *
 */
public class DataBaseHelper {

	// 保存连接的ThredLoacl,每个线程保持一个
	private static final ThreadLocal<Connection> CONNECTIONS = new ThreadLocal<Connection>();

	// 数据源工程
	// TODO 以后改为工厂模式
	private static final DataSourceFactory DATA_SOURCE_FACTORY = new DbcpDataScourceFactory();

	// TODO 以后改为工厂模式
	// 数据访问器
	private static final DataAccessor DATA_ACCESSOR = new DefaultDataAccessor();

	// 获得数据源
	public static DataSource getDataSource() {
		return DATA_SOURCE_FACTORY.getDataSource();
	}

	// 获得连接
	public static Connection getConnection() {
		Connection connection = CONNECTIONS.get();
		if (connection == null) {
			try {
				connection = getDataSource().getConnection();
				if (connection != null) {
					CONNECTIONS.set(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	// 事务的开启
	public static void beginTransaction() {
		Connection connection = getConnection();

		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// TODO why?
				CONNECTIONS.set(connection);
			}
		}

	}

	// 事务提交
	public static void commitTranscation() {
		Connection connection = getConnection();
		if (connection != null) {
			try {
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CONNECTIONS.remove();
			}
		}
	}

	// 事务的回滚
	public static void rollbackTranscation() {
		Connection connection = getConnection();
		if (connection != null) {
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// 事务结束移除
				CONNECTIONS.remove();
			}
		}
	}

	// 查询单条记录
	public static <T> T selectOne(Class<T> clz, String sql, Object... params) {
		return DATA_ACCESSOR.selectOne(clz, sql, params);

	}

	// 插入一条记录
	public static int insertOne(String sql, Object... params) {
		return DATA_ACCESSOR.insertOne(sql, params);

	}

}
