package com.sgai.vbp.dao.mysql.datasource;

import com.sgai.vbp.dao.DBType;
import com.sgai.vbp.dao.datasource.VbpDruidDatasource;

/**
 * 数据源
 * @author mrh
 *
 */
public class MysqlDataSource extends VbpDruidDatasource {

	/**
	 *  Mysql 数据源
	 */
	private static final long serialVersionUID = 8286847131710566657L;

	@Override
	public DBType dbType() {
		return DBType.Mysql;
	}

}
