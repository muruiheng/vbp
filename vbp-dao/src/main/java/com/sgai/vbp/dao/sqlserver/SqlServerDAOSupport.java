package com.sgai.vbp.dao.sqlserver;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;


/**
 * 
 * 新架构共用组件和spring结合
 */
public abstract class SqlServerDAOSupport<T> extends DAOSupport<T>{
	
	@Autowired
	private SqlServerVbpJdbcTemplate sqlServerJdbcTemplate;

	/* (non-Javadoc)
	 * @see com.sgai.vbp.dao.DAOSupport#getJdbcTemplate()
	 */
	@Override
	protected VbpJdbcTemplate getJdbcTemplate() {
		return sqlServerJdbcTemplate;
	}
	
	@Override
	protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		StringBuffer sql = new StringBuffer();
		Map<String, Field> primaryKeys = this.getPrimaryKey(this.getGenericType());
		sql.append("SELECT * from (select row_number()over(order by ");
		for (String key : primaryKeys.keySet()) {
			sql.append(key).append(", ");
		}
		sql = new StringBuffer(sql.substring(0, sql.length() -1));
		sql.append(") rownumber, a1.* ");
		sql.append(" from (").append(queryString).append(") as a1) ");
		sql.append("where rownumber > ").append(startIndex);
		sql.append(" and rownumber <= ").append(startIndex.intValue()+pageSize.intValue());
		return sql.toString();
	}
}
