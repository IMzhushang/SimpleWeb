package com.simpleweb.framework.ds.impl;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * dbcp 数据源
 * 
 * @author Administrator
 *
 */
public class DbcpDataScourceFactory extends
		AbstractDataSourceFactory<BasicDataSource> {

	@Override
	public void setDriver(BasicDataSource ds, String driver) {
		ds.setDriverClassName(driver);
	}

	@Override
	public void setUrl(BasicDataSource ds, String url) {
		ds.setUrl(url);
	}

	@Override
	public void setUsername(BasicDataSource ds, String username) {
		ds.setUsername(username);
	}

	@Override
	public void setPassword(BasicDataSource ds, String password) {
		ds.setPassword(password);
	}

	@Override
	protected BasicDataSource createDataSource() {
		return new BasicDataSource();
	}

}
