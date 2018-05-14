package sqlserver.junit;

import java.io.Serializable;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.PrimaryKey;

@Entity("tb_users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5494074952299129097L;

	private String userName;
	
	private String pwd;
	
	@PrimaryKey("userid")
	private String id;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
