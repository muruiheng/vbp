package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 菜单按钮资源 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_resources
 * Table Description : 菜单按钮资源
 * Data Access Object: ResourceDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_resources")
public class Resource implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(Resource.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_resources column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 菜单名 **/
	@Column(value="cnName", isNull =false, desc="菜单名")
	private String cnName;
	/** 英文名 **/
	@Column("enName")
	private String enName;
	/** 菜单编码 **/
	@Column(value="mCode", isNull =false, desc="菜单编码")
	private String mCode;
	/** 上级菜单 **/
	@Column("parentId")
	private Long parentId;
	/** 资源类型 **/
	@Column(value="resType", isNull =false, desc="资源类型")
	private int resType;
	/** 菜单地址 **/
	@Column(value="src", isNull =false, desc="菜单地址")
	private String src;
	/** 菜单图标 **/
	@Column("icon")
	private String icon;
	/** 状态 **/
	@Column("state")
	private int state;
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
/* Creates new ResourceBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public Resource() {
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
     * 设定菜单名
     * @param cnName 菜单名
     */
    public void setCnName(String cnName) {
		this.cnName = cnName;
    }

    /**
     * 取得菜单名
     * @return cnName
     */
    public String getCnName() {
    	return this.cnName;
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
     * 设定菜单编码
     * @param mCode 菜单编码
     */
    public void setMCode(String mCode) {
		this.mCode = mCode;
    }

    /**
     * 取得菜单编码
     * @return mCode
     */
    public String getMCode() {
    	return this.mCode;
    }
    
    /**
     * 设定上级菜单
     * @param parentId 上级菜单
     */
    public void setParentId(Long parentId) {
		this.parentId = parentId;
    }

    /**
     * 取得上级菜单
     * @return parentId
     */
    public Long getParentId() {
		if (parentId == null)
			return new Long(0);
    	return this.parentId;
    }
    
    /**
     * 设定资源类型
     * @param resType 资源类型
     */
    public void setResType(int resType) {
		this.resType = resType;
    }

    /**
     * 取得资源类型
     * @return resType
     */
    public int getResType() {
    	return this.resType;
    }
    
    /**
     * 设定菜单地址
     * @param src 菜单地址
     */
    public void setSrc(String src) {
		this.src = src;
    }

    /**
     * 取得菜单地址
     * @return src
     */
    public String getSrc() {
    	return this.src;
    }
    
    /**
     * 设定菜单图标
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
		this.icon = icon;
    }

    /**
     * 取得菜单图标
     * @return icon
     */
    public String getIcon() {
    	return this.icon;
    }
    
    /**
     * 设定状态
     * @param state 状态
     */
    public void setState(int state) {
		this.state = state;
    }

    /**
     * 取得状态
     * @return state
     */
    public int getState() {
    	return this.state;
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
