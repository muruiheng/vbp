package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 角色资源关系表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_role_permissions_link
 * Table Description : 角色资源关系表
 * Data Access Object: RolePermissionLinkDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_role_permissions_link")
public class RolePermissionLink implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(RolePermissionLink.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_role_permissions_link column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 权限名称 **/
	@Column(value="permissionId", isNull =false, desc="权限名称")
	private Long permissionId;
	/** 英文名称 **/
	@Column("roleId")
	private Long roleId;
	/** 资料创建时间 **/
	@Column("createTime")
	private Timestamp createTime;
	/** 资料创建人 **/
	@Column("creator")
	private String creator;

/*----------------------------------------------------------------------------*/
/* Creates new RolePermissionLinkBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public RolePermissionLink() {
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
     * 设定权限名称
     * @param permissionId 权限名称
     */
    public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
    }

    /**
     * 取得权限名称
     * @return permissionId
     */
    public Long getPermissionId() {
		if (permissionId == null)
			return new Long(0);
    	return this.permissionId;
    }
    
    /**
     * 设定英文名称
     * @param roleId 英文名称
     */
    public void setRoleId(Long roleId) {
		this.roleId = roleId;
    }

    /**
     * 取得英文名称
     * @return roleId
     */
    public Long getRoleId() {
		if (roleId == null)
			return new Long(0);
    	return this.roleId;
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
