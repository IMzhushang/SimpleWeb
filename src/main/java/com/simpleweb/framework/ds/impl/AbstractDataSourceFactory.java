package com.simpleweb.framework.ds.impl;

import javax.sql.DataSource;

import com.simpleweb.framework.domain.Data;
import com.simpleweb.framework.ds.DataSourceFactory;
import com.simpleweb.framework.helper.ConfigHelper;

/**
 * 
 * 提供数据源
 * 
 * @author Administrator
 *
 */
public abstract class AbstractDataSourceFactory<T extends DataSource>
		implements DataSourceFactory {

	protected final String driver = ConfigHelper
			.getString("simpleweb.framework.jdbc.driver");
	protected final String url = ConfigHelper
			.getString("simpleweb.framework.jdbc.url");
	protected final String username = ConfigHelper
			.getString("simpleweb.framework.jdbc.username");
	protected final String password = ConfigHelper
			.getString("simpleweb.framework.jdbc.password");

	public DataSource getDataSource() {

		// 创建数据源
		T ds = createDataSource();

		// 设置基础属性
		setDriver(ds, driver);
		setUrl(ds, url);
		setUsername(ds, username);
		setPassword(ds, password);

		return ds;
	}

	/**
	 * 创建具体的DataSource
	 * 
	 * @return
	 */
	protected abstract T createDataSource();

	public abstract void setDriver(T ds, String driver);

	public abstract void setUrl(T ds, String url);

	public abstract void setUsername(T ds, String username);

	public abstract void setPassword(T ds, String password);

}
