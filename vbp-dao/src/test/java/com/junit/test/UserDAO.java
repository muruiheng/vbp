package com.junit.test;

import java.sql.SQLException;

import org.junit.Test;

import com.sgai.vbp.dao.DAOSupport;
import com.sgai.vbp.dao.VbpJdbcTemplate;

public class UserDAO extends DAOSupport<User>{

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
	public void main() {
		try {
			System.out.println(this.getDeletePreSql());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			
			System.out.println(new UserDAO().getDeletePreSql());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
