package com.sgai.vbp.dao.oracle.datasource;

import com.sgai.vbp.dao.DBType;
import com.sgai.vbp.dao.datasource.VbpDruidDatasource;

/**
 * Oracle 数据源
 * @author mrh
 *
 */
public class OracleDataSource extends VbpDruidDatasource {

	/**
	 * Oracle 数据源
	 */
	private static final long serialVersionUID = 8286847131710566657L;

	@Override
	public DBType dbType() {
		return DBType.Oracle;
	}

}
