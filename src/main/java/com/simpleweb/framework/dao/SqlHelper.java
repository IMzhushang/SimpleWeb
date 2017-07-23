package com.simpleweb.framework.dao;

import java.util.Collection;

import com.simpleweb.framework.orm.EntryHelper;

/**
 * 动态生成Sql
 * 
 * @author Administrator
 *
 */
public class SqlHelper {
	public static String generateSelectSql(Class<?> entry, String condition,
			String order) {

		StringBuilder sql = new StringBuilder();
		sql.append("select * from").append(" ").append(getTableName(entry)).append(" ");
		sql.append(generateWhereCondition(condition)).append(" ");
		sql.append(generateOrderCondition(order));
		return sql.toString();

	}
	
	 public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames) {
	        StringBuilder sql = new StringBuilder("insert into ").append(getTableName(entityClass));
	        if ( fieldNames != null && fieldNames.size() > 0) {
	            int i = 0;
	            StringBuilder columns = new StringBuilder(" ");
	            StringBuilder values = new StringBuilder(" values ");
	            for (String fieldName : fieldNames) {
	                String columnName = EntryHelper.getColumnName(entityClass, fieldName);
	                if (i == 0) {
	                    columns.append("(").append(columnName);
	                    values.append("(?");
	                } else {
	                    columns.append(", ").append(columnName);
	                    values.append(", ?");
	                }
	                if (i == fieldNames.size() - 1) {
	                    columns.append(")");
	                    values.append(")");
	                }
	                i++;
	            }
	            sql.append(columns).append(values);
	        }
	        return sql.toString();
	    }

	private static Object generateOrderCondition(String sort) {
		String order = "";
		if (sort != null && sort.length() > 0) {
			order += "   order by   " + sort;
		}

		return order;
	}

	private static String generateWhereCondition(String condition) {
		String where = "";
		if (condition != null && condition.length() > 0) {
			where += "where   " + condition;
		}
		return where;
	}

	private static Object getTableName(Class<?> entry) {
		return EntryHelper.getTableName(entry);
	}
}
