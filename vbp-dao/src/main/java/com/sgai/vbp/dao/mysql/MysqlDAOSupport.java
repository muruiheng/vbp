package com.sgai.vbp.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;


/**
 * 
 * 新架构共用组件和spring结合
 */
public abstract class MysqlDAOSupport<T> extends DAOSupport<T>{
	
	@Autowired
	private MysqlVbpJdbcTemplate mysqlJdbcTemplate;

	/* (non-Javadoc)
	 * @see com.sgai.vbp.dao.DAOSupport#getJdbcTemplate()
	 */
	@Override
	protected VbpJdbcTemplate getJdbcTemplate() {
		return mysqlJdbcTemplate;
	}
	
	/**
	 * 构造MySQL数据分页SQL
	 * 
	 * @param queryString
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		if (StringUtils.isEmpty(queryString)) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		if (null != startIndex && null != pageSize) {
			result .append(queryString).append(" limit ").append(startIndex).append(",").append(pageSize);
		} else if (null != startIndex && null == pageSize) {
			result .append(queryString).append(" limit ").append(startIndex);
		} else {
			result .append(queryString);
		}
		return result.toString();
	}
}
