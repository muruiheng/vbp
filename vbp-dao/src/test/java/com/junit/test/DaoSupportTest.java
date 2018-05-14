package com.junit.test;

import java.sql.SQLException;

import org.junit.Test;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;

public class DaoSupportTest extends DAOSupport<User>{

	@Override
	protected VbpJdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
	public void testDelete() {
		try {
			System.out.println(this.getDeletePreSql());
			System.out.println(this.getCreatePreSql());
			System.out.println(this.getPKQueryPreSql());
			System.out.println(this.getUpdatePreSql());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			
			System.out.println(new DaoSupportTest().getDeletePreSql());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
