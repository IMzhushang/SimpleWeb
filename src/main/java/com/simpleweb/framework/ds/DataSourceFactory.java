package com.simpleweb.framework.ds;

import javax.sql.DataSource;

/**
 * 获取数据源
 * 
 * @author Administrator
 *
 */
public interface DataSourceFactory {

	/**
	 * 获取到数据源
	 * 
	 * @return
	 */
	DataSource getDataSource();

}
