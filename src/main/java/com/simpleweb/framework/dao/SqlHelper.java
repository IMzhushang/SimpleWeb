package com.simpleweb.framework.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static String generateSelectSql(Class<?> entry, Map<String, Object> condition,
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

	public static String generateWhereCondition(String condition) {
		String where = "";
		if (condition != null && condition.length() > 0) {
			where += "where   " + condition;
		}
		return where;
	}
	
	public static String generateWhereCondition(Map<String, Object> condaition) {
	
		if(condaition != null && condaition.size() > 0 ) {
			StringBuilder sb  = new  StringBuilder();
			sb.append(" where ");
			int i= 0;
			for(Map.Entry<String, Object> entry : condaition.entrySet() ) {
				if(i  == condaition.size()-1) {
					 sb.append(entry.getKey()).append("\'"+entry.getValue()+"\'").append(" ");
				}else {
					 sb.append(entry.getKey()).append("\'"+entry.getValue()+"\'").append(" and ");
				}
				i++;
			}
			return sb.toString();
		}
		return "";
	}

	private static String getTableName(Class<?> entry) {
	//	return "testTable";
	  	return EntryHelper.getTableName(entry);
	}
	
	/**
	 *  生成新语句
	 * @return
	 */
	public static String generateUpdata(Class<?> entry,List<String>  updtaField) {
		
       if( updtaField == null ||  updtaField.size() <  1 ){
			 throw new RuntimeException("update field is null");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append( "UPDATE  ").append(getTableName(entry)+" ").append("SET ");
		
		for( int  i=0  ; i <  updtaField.size() ; i++  ) {
		
			 if(  i == updtaField.size() - 1 ) {
				 sb.append(updtaField.get(i)).append("=?");
			 }else {
				 sb.append(updtaField.get(i)).append("=?,");
			 }
		}
		
		// 添加需要的字段'
	
	   return sb.toString();
		 
	}
	
	/**
	 * 生成查询数量的语句
	 * @return
	 */
	public  static String genetateCount(Class<?> entry,Map<String, Object> condaition) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from").append(" ").append(getTableName(entry)).append(generateWhereCondition(condaition));
		return sql.toString();
		
	}
	
	public static String genetateLimit(int start, int offset) {
		 StringBuilder sb = new StringBuilder();
		 sb.append(" LIMIT ").append(start + ", "+ offset);
		 return sb.toString();
	}
	
	 public static void main(String[] args) {
		      // 测试查询数量
		 Map<String, Object> condaition = new HashMap<String, Object>();
		 condaition.put("a>", 10);
		 condaition.put("b=", 100);
		String countSql =  genetateCount(null, condaition);
		
		System.out.println(countSql);
		
		// 测试更新
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("b");
		 String updateSql = generateUpdata(null, list);
		
		 System.out.println(updateSql);
		 
		
		 
		String selectSql = generateSelectSql(null, condaition, null)+" " + genetateLimit(1, 10);
		System.out.println(selectSql);
		 
	}
	 
	
	
}
