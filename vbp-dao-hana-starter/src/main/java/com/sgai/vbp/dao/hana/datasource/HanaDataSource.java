package com.sgai.vbp.dao.hana.datasource;

import com.sap.db.jdbcext.DataSourceSAP;
import com.sgai.vbp.dao.DBType;
import com.sgai.vbp.dao.datasource.VbpDatasource;

/**
 * 数据源
 * @author mrh
 *
 */
public class HanaDataSource extends DataSourceSAP implements VbpDatasource  {


	@Override
	public DBType dbType() {
		return DBType.Hana;
	}

}
