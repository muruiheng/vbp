package com.sgai.vbp.dao.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;


/**
 * 
 * 新架构共用组件和spring结合
 */
public abstract class OracleDAOSupport<T> extends DAOSupport<T>{
	
	@Autowired
	private OracleVbpJdbcTemplate oracleJdbcTemplate;

	/* (non-Javadoc)
	 * @see com.sgai.vbp.dao.DAOSupport#getJdbcTemplate()
	 */
	@Override
	protected VbpJdbcTemplate getJdbcTemplate() {
		return oracleJdbcTemplate;
	}
	
	@Override
	protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		if (StringUtils.isEmpty(queryString)) {
			return null;
		}
		int endIndex = startIndex + pageSize;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("select rOraclePageSQL.*, ROWNUM as currentRow ");
		sql.append("from (").append(queryString).append(") ");
		sql.append("rOraclePageSQL where rownum <=").append(endIndex);
		sql.append(") where currentRow > ").append(startIndex);
		return sql.toString();
	}
}
