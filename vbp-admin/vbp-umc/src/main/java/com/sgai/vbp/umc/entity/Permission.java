package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 权限表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_permissions
 * Table Description : 权限表
 * Data Access Object: PermissionDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_permissions")
public class Permission implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(Permission.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_permissions column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 权限名称 **/
	@Column(value="cnName", isNull =false, desc="权限名称")
	private String cnName;
	/** 英文名称 **/
	@Column("enName")
	private String enName;
	/** 资源类型 **/
	@Column(value="perType", isNull =false, desc="资源类型")
	private int perType;
	/** 资源ID **/
	@Column(value="resourceId", isNull =false, desc="资源ID")
	private Long resourceId;
	/** 类型 **/
	@Column(value="resource", isNull =false, desc="类型")
	private String resource;
	/** 资料创建时间 **/
	@Column("createTime")
	private Timestamp createTime;
	/** 资料创建人 **/
	@Column("creator")
	private String creator;
	/** 资料更新时间 **/
	@Column("modifyTime")
	private Timestamp modifyTime;
	/** 资料更新人 **/
	@Column("modifier")
	private String modifier;
	/** 资料删除时间 **/
	@Column("deleteTime")
	private Timestamp deleteTime;
	/** 资料删除人 **/
	@Column("deletor")
	private String deletor;

/*----------------------------------------------------------------------------*/
/* Creates new PermissionBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public Permission() {
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
     * @param cnName 权限名称
     */
    public void setCnName(String cnName) {
		this.cnName = cnName;
    }

    /**
     * 取得权限名称
     * @return cnName
     */
    public String getCnName() {
    	return this.cnName;
    }
    
    /**
     * 设定英文名称
     * @param enName 英文名称
     */
    public void setEnName(String enName) {
		this.enName = enName;
    }

    /**
     * 取得英文名称
     * @return enName
     */
    public String getEnName() {
    	return this.enName;
    }
    
    /**
     * 设定资源类型
     * @param perType 资源类型
     */
    public void setPerType(int perType) {
		this.perType = perType;
    }

    /**
     * 取得资源类型
     * @return perType
     */
    public int getPerType() {
    	return this.perType;
    }
    
    /**
     * 设定资源ID
     * @param resourceId 资源ID
     */
    public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
    }

    /**
     * 取得资源ID
     * @return resourceId
     */
    public Long getResourceId() {
		if (resourceId == null)
			return new Long(0);
    	return this.resourceId;
    }
    
    /**
     * 设定类型
     * @param resource 类型
     */
    public void setResource(String resource) {
		this.resource = resource;
    }

    /**
     * 取得类型
     * @return resource
     */
    public String getResource() {
    	return this.resource;
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
     * 设定资料更新时间
     * @param modifyTime 资料更新时间
     */
    public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
    }

    /**
     * 取得资料更新时间
     * @return modifyTime
     */
    public Timestamp getModifyTime() {
    	return this.modifyTime;
    }
    
    /**
     * 设定资料更新人
     * @param modifier 资料更新人
     */
    public void setModifier(String modifier) {
		this.modifier = modifier;
    }

    /**
     * 取得资料更新人
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
