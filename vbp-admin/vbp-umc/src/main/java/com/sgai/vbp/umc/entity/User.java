package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 用户表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_users
 * Table Description : 用户表
 * Data Access Object: UserDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_users")
public class User implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(User.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_users column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 用户名 **/
	@Column(value="userName", isNull =false, desc="用户名")
	private String userName;
	/** 密码 **/
	@Column(value="password", isNull =false, desc="密码")
	private String password;
	/** 真是中文姓名 **/
	@Column(value="cnName", isNull =false, desc="真是中文姓名")
	private String cnName;
	/** 真是姓名拼音 **/
	@Column(value="cnNamePy", isNull =false, desc="真是姓名拼音")
	private String cnNamePy;
	/** 英文名 **/
	@Column(value="enName", isNull =false, desc="英文名")
	private String enName;
	/** 用户类型 **/
	@Column(value="userType", isNull =false, desc="用户类型")
	private String userType;
	/** 邮箱 **/
	@Column(value="email", isNull =false, desc="邮箱")
	private String email;
	/** 移动电话（手机） **/
	@Column(value="telephone", isNull =false, desc="移动电话（手机）")
	private String telephone;
	/** 身份证编号 **/
	@Column(value="cerNo", isNull =false, desc="身份证编号")
	private String cerNo;
	/** 公司ID **/
	@Column(value="compId", isNull =false, desc="公司ID")
	private Long compId;
	/** 状态 0-正常 1-禁用 **/
	@Column("status")
	private int status;
	/** 资料创建时间 **/
	@Column("createTime")
	private Timestamp createTime;
	/** 资料创建人 **/
	@Column("creator")
	private String creator;
	/** 资料创建时间 **/
	@Column("modifyTime")
	private Timestamp modifyTime;
	/** 资料创建人 **/
	@Column("modifier")
	private String modifier;
	/** 资料删除时间 **/
	@Column("deleteTime")
	private Timestamp deleteTime;
	/** 资料删除人 **/
	@Column("deletor")
	private String deletor;

/*----------------------------------------------------------------------------*/
/* Creates new UserBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public User() {
    }

/*----------------------------------------------------------------------------*/
/* function methods
/*----------------------------------------------------------------------------*/


    /**
     * 覆写toString,以利 Debug
     * @return 物件内容值
     */
    public String toString() {
        return JSONUtil.toString(this);
    }

/*----------------------------------------------------------------------------*/
/* get and set methods for the instance variables
/*----------------------------------------------------------------------------*/
    
    /**
     * 设定ID
     * @param id ID
     */
    public void setId(Long id) {
		this.id = id;
    }

    /**
     * 取得ID
     * @return id
     */
    public Long getId() {
		if (id == null)
			return new Long(0);
    	return this.id;
    }
    
    /**
     * 设定用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
		this.userName = userName;
    }

    /**
     * 取得用户名
     * @return userName
     */
    public String getUserName() {
    	return this.userName;
    }
    
    /**
     * 设定密码
     * @param password 密码
     */
    public void setPassword(String password) {
		this.password = password;
    }

    /**
     * 取得密码
     * @return password
     */
    public String getPassword() {
    	return this.password;
    }
    
    /**
     * 设定真是中文姓名
     * @param cnName 真是中文姓名
     */
    public void setCnName(String cnName) {
		this.cnName = cnName;
    }

    /**
     * 取得真是中文姓名
     * @return cnName
     */
    public String getCnName() {
    	return this.cnName;
    }
    
    /**
     * 设定真是姓名拼音
     * @param cnNamePy 真是姓名拼音
     */
    public void setCnNamePy(String cnNamePy) {
		this.cnNamePy = cnNamePy;
    }

    /**
     * 取得真是姓名拼音
     * @return cnNamePy
     */
    public String getCnNamePy() {
    	return this.cnNamePy;
    }
    
    /**
     * 设定英文名
     * @param enName 英文名
     */
    public void setEnName(String enName) {
		this.enName = enName;
    }

    /**
     * 取得英文名
     * @return enName
     */
    public String getEnName() {
    	return this.enName;
    }
    
    /**
     * 设定用户类型
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
		this.userType = userType;
    }

    /**
     * 取得用户类型
     * @return userType
     */
    public String getUserType() {
    	return this.userType;
    }
    
    /**
     * 设定邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
		this.email = email;
    }

    /**
     * 取得邮箱
     * @return email
     */
    public String getEmail() {
    	return this.email;
    }
    
    /**
     * 设定移动电话（手机）
     * @param telephone 移动电话（手机）
     */
    public void setTelephone(String telephone) {
		this.telephone = telephone;
    }

    /**
     * 取得移动电话（手机）
     * @return telephone
     */
    public String getTelephone() {
    	return this.telephone;
    }
    
    /**
     * 设定身份证编号
     * @param cerNo 身份证编号
     */
    public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
    }

    /**
     * 取得身份证编号
     * @return cerNo
     */
    public String getCerNo() {
    	return this.cerNo;
    }
    
    /**
     * 设定公司ID
     * @param compId 公司ID
     */
    public void setCompId(Long compId) {
		this.compId = compId;
    }

    /**
     * 取得公司ID
     * @return compId
     */
    public Long getCompId() {
		if (compId == null)
			return new Long(0);
    	return this.compId;
    }
    
    /**
     * 设定状态 0-正常 1-禁用
     * @param status 状态 0-正常 1-禁用
     */
    public void setStatus(int status) {
		this.status = status;
    }

    /**
     * 取得状态 0-正常 1-禁用
     * @return status
     */
    public int getStatus() {
    	return this.status;
    }
    
    /**
     * 设定资料创建时间
     * @param createTime 资料创建时间
     */
    public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
    }

    /**
     * 取得资料创建时间
     * @return createTime
     */
    public Timestamp getCreateTime() {
    	return this.createTime;
    }
    
    /**
     * 设定资料创建人
     * @param creator 资料创建人
     */
    public void setCreator(String creator) {
		this.creator = creator;
    }

    /**
     * 取得资料创建人
     * @return creator
     */
    public String getCreator() {
    	return this.creator;
    }
    
    /**
     * 设定资料创建时间
     * @param modifyTime 资料创建时间
     */
    public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
    }

    /**
     * 取得资料创建时间
     * @return modifyTime
     */
    public Timestamp getModifyTime() {
    	return this.modifyTime;
    }
    
    /**
     * 设定资料创建人
     * @param modifier 资料创建人
     */
    public void setModifier(String modifier) {
		this.modifier = modifier;
    }

    /**
     * 取得资料创建人
     * @return modifier
     */
    public String getModifier() {
    	return this.modifier;
    }
    
    /**
     * 设定资料删除时间
     * @param deleteTime 资料删除时间
     */
    public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
    }

    /**
     * 取得资料删除时间
     * @return deleteTime
     */
    public Timestamp getDeleteTime() {
    	return this.deleteTime;
    }
    
    /**
     * 设定资料删除人
     * @param deletor 资料删除人
     */
    public void setDeletor(String deletor) {
		this.deletor = deletor;
    }

    /**
     * 取得资料删除人
     * @return deletor
     */
    public String getDeletor() {
    	return this.deletor;
    }
    
}
