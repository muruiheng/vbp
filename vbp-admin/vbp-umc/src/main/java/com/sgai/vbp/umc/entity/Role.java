package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 角色表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_roles
 * Table Description : 角色表
 * Data Access Object: RoleDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_roles")
public class Role implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(Role.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_roles column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 公司ID **/
	@Column(value="compId", isNull =false, desc="公司ID")
	private Long compId;
	/** 中文名 **/
	@Column(value="cnName", isNull =false, desc="中文名")
	private String cnName;
	/** 英文名 **/
	@Column("enName")
	private String enName;
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
/* Creates new RoleBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public Role() {
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
     * 设定中文名
     * @param cnName 中文名
     */
    public void setCnName(String cnName) {
		this.cnName = cnName;
    }

    /**
     * 取得中文名
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
