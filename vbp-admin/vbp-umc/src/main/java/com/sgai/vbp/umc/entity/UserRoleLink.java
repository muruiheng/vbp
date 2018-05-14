package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 角色人员关系表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_user_role_link
 * Table Description : 角色人员关系表
 * Data Access Object: UserRoleLinkDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_user_role_link")
public class UserRoleLink implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(UserRoleLink.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_user_role_link column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 用户 **/
	@Column(value="userid", isNull =false, desc="用户")
	private Long userid;
	/** 角色 **/
	@Column(value="roleid", isNull =false, desc="角色")
	private Long roleid;
	/** 资料创建时间 **/
	@Column("createTime")
	private Timestamp createTime;
	/** 资料创建人 **/
	@Column("creator")
	private String creator;

/*----------------------------------------------------------------------------*/
/* Creates new UserRoleLinkBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public UserRoleLink() {
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
     * 设定用户
     * @param userid 用户
     */
    public void setUserid(Long userid) {
		this.userid = userid;
    }

    /**
     * 取得用户
     * @return userid
     */
    public Long getUserid() {
		if (userid == null)
			return new Long(0);
    	return this.userid;
    }
    
    /**
     * 设定角色
     * @param roleid 角色
     */
    public void setRoleid(Long roleid) {
		this.roleid = roleid;
    }

    /**
     * 取得角色
     * @return roleid
     */
    public Long getRoleid() {
		if (roleid == null)
			return new Long(0);
    	return this.roleid;
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
    
}
