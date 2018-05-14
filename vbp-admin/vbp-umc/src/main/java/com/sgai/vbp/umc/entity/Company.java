package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 公司表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_companys
 * Table Description : 公司表
 * Data Access Object: CompanyDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_companys")
public class Company implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(Company.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_companys column Name
/*----------------------------------------------------------------------------*/

	/** 公司ID **/
	@PrimaryKey("id")
	private Long id;
	/** 公司全名 **/
	@Column(value="fullName", isNull =false, desc="公司全名")
	private String fullName;
	/** 公司名缩写 **/
	@Column(value="shortName", isNull =false, desc="公司名缩写")
	private String shortName;
	/** 统一信用代码（组织机构代码） **/
	@Column(value="ucc", isNull =false, desc="统一信用代码（组织机构代码）")
	private String ucc;
	/** 公司英文名 **/
	@Column("enName")
	private String enName;
	/** 上级公司ID， **/
	@Column("parentId")
	private Long parentId;
	/** 状态 0-正常 1-禁用 **/
	@Column("status")
	private int status;
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
/* Creates new CompanyBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public Company() {
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
     * 设定公司ID
     * @param id 公司ID
     */
    public void setId(Long id) {
		this.id = id;
    }

    /**
     * 取得公司ID
     * @return id
     */
    public Long getId() {
		if (id == null)
			return new Long(0);
    	return this.id;
    }
    
    /**
     * 设定公司全名
     * @param fullName 公司全名
     */
    public void setFullName(String fullName) {
		this.fullName = fullName;
    }

    /**
     * 取得公司全名
     * @return fullName
     */
    public String getFullName() {
    	return this.fullName;
    }
    
    /**
     * 设定公司名缩写
     * @param shortName 公司名缩写
     */
    public void setShortName(String shortName) {
		this.shortName = shortName;
    }

    /**
     * 取得公司名缩写
     * @return shortName
     */
    public String getShortName() {
    	return this.shortName;
    }
    
    /**
     * 设定统一信用代码（组织机构代码）
     * @param ucc 统一信用代码（组织机构代码）
     */
    public void setUcc(String ucc) {
		this.ucc = ucc;
    }

    /**
     * 取得统一信用代码（组织机构代码）
     * @return ucc
     */
    public String getUcc() {
    	return this.ucc;
    }
    
    /**
     * 设定公司英文名
     * @param enName 公司英文名
     */
    public void setEnName(String enName) {
		this.enName = enName;
    }

    /**
     * 取得公司英文名
     * @return enName
     */
    public String getEnName() {
    	return this.enName;
    }
    
    /**
     * 设定上级公司ID，
     * @param parentId 上级公司ID，
     */
    public void setParentId(Long parentId) {
		this.parentId = parentId;
    }

    /**
     * 取得上级公司ID，
     * @return parentId
     */
    public Long getParentId() {
		if (parentId == null)
			return new Long(0);
    	return this.parentId;
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
