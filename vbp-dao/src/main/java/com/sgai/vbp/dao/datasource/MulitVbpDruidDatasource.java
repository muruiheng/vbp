package com.sgai.vbp.dao.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.sgai.vbp.dao.DBType;
import com.sgai.vbp.util.AssertUtil;

public abstract class MulitVbpDruidDatasource extends DruidDataSource implements VbpDatasource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455135250459924175L;
	private static final String DATASOURCE_NAME = "com.sgai.vbp.datasource.name";
	private DBType dbType;
	
	private Map<String, VbpDruidDatasource> datasources = null;
	
	
	
	public MulitVbpDruidDatasource(DBType dbType, Properties...properties) {
		super();
		this.dbType = dbType;
		
		datasources = new HashMap<>();
		
		if (!AssertUtil.isVal(properties)) {
			for (Properties property : properties) {
				String name = property.getProperty(DATASOURCE_NAME);
				if (!AssertUtil.isVal(name)) {
					name = datasources.size() + 1 + "";
				}
				datasources.put(name, this.createDatasource(property));
			}
		}
	}



	abstract protected VbpDruidDatasource createDatasource(Properties property);

	@Override
	public DBType dbType() {
		return dbType;
	}
	
	/**
	 * 获取数据源
	 * @param name
	 * @return
	 */
	public DataSource getDatasource(String name) {
		if (!AssertUtil.isVal(this.datasources)) {
			return null;
		}
		
		return this.datasources.get(name);
	}
}
