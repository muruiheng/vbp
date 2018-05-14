package com.sgai.vbp.dao.hana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;
import com.sgai.vbp.dao.exception.DAOException;


/**
 * 
 * 新架构共用组件和spring结合
 */
public abstract class HanaDAOSupport<T> extends DAOSupport<T>{
	
	@Autowired
	private HanaVbpJdbcTemplate hanaVbpJdbcTemplate;

	/* (non-Javadoc)
	 * @see com.sgai.vbp.dao.DAOSupport#getJdbcTemplate()
	 */
	@Override
	protected VbpJdbcTemplate getJdbcTemplate() {
		return hanaVbpJdbcTemplate;
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
			result .append(queryString).append(" limit ").append(pageSize).append(" offset ").append(startIndex);
		} else if (null == startIndex && null != pageSize) {
			result .append(queryString).append(" limit pageSize ").append(" offset 0");
		} else if (null != startIndex && null == pageSize) {
			throw new DAOException("the pageSize can not be null");
		} else {
			result .append(queryString);
		}
		return result.toString();
	}
}
