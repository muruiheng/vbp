package com.sgai.vbp.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sgai.vbp.dao.datasource.VbpDruidDatasource;

public abstract class VbpJdbcTemplate extends JdbcTemplate {


	/**
	 * @return the dbtype
	 */
	 public DBType dbtype() {
		 VbpDruidDatasource dataSource = (VbpDruidDatasource)this.getDataSource();
		 return dataSource.dbType();
	 }

}
