package sqlserver.junit;

import org.junit.Test;

import com.sgai.vbp.dao.sqlserver.SqlServerDAOSupport;

public class SqlServerDAOTEst extends SqlServerDAOSupport<User>{

	
	@Test
	public void testDAOSupport() {
		String sql = "select * from tb_users";
		
		String pageSQL = this.getPageSQL(sql, 20, 10);
		
		System.out.println(pageSQL);
	}
}
