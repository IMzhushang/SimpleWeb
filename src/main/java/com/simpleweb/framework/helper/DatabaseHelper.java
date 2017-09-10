package com.simpleweb.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 操作数据库
 * 
 * @author Administrator
 *
 */
public class DatabaseHelper {

	/**
	 * 存放每一个线程自己的Conection
	 */
	private static ThreadLocal<Connection> THREAD_CONN = new ThreadLocal<Connection>();

	private static final String dirver = "";

	private static final String user = "";
	private static final String pwd = "";

	private static final String url = "";

	/**
	 * 开始事务
	 */
	public static void beginTranscation() {

		Connection conn = getConnection();

		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				THREAD_CONN.set(conn);
			}
		}

	}

	private static Connection getConnection() {

		return null;
	}

	/**
	 * 提交事务
	 */
	public static void commitTranscation() {
		Connection connection = getConnection();
		if (connection != null) {
			try {
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				THREAD_CONN.remove();
			}
		}
	}

	/**
	 * 事务回滚
	 */
	public static void rollbackTranscation() {
		Connection connection = getConnection();
		if (connection != null) {
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				THREAD_CONN.remove();
			}

		}
	}

}
