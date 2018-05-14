package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 部门表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_departments
 * Table Description : 部门表
 * Data Access Object: DepartmentDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_departments")
public class Department implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(Department.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_departments column Name
/*----------------------------------------------------------------------------*/

	/** 部门ID **/
	@PrimaryKey("id")
	private Long id;
	/** 公司ID **/
	@Column(value="compId", isNull =false, desc="公司ID")
	private Long compId;
	/** 部门主管 **/
	@Column("managerid")
	private Long managerid;
	/** 部门名称 **/
	@Column(value="deptName", isNull =false, desc="部门名称")
	private String deptName;
	/** 部门代码 **/
	@Column(value="deptNo", isNull =false, desc="部门代码")
	private String deptNo;
	/** 状态 **/
	@Column("status")
	private int status;
	/** 上级部门ID **/
	@Column("parentId")
	private Long parentId;
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
/* Creates new DepartmentBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public Department() {
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
     * 设定部门ID
     * @param id 部门ID
     */
    public void setId(Long id) {
		this.id = id;
    }

    /**
     * 取得部门ID
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
     * 设定部门主管
     * @param managerid 部门主管
     */
    public void setManagerid(Long managerid) {
		this.managerid = managerid;
    }

    /**
     * 取得部门主管
     * @return managerid
     */
    public Long getManagerid() {
		if (managerid == null)
			return new Long(0);
    	return this.managerid;
    }
    
    /**
     * 设定部门名称
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
		this.deptName = deptName;
    }

    /**
     * 取得部门名称
     * @return deptName
     */
    public String getDeptName() {
    	return this.deptName;
    }
    
    /**
     * 设定部门代码
     * @param deptNo 部门代码
     */
    public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
    }

    /**
     * 取得部门代码
     * @return deptNo
     */
    public String getDeptNo() {
    	return this.deptNo;
    }
    
    /**
     * 设定状态
     * @param status 状态
     */
    public void setStatus(int status) {
		this.status = status;
    }

    /**
     * 取得状态
     * @return status
     */
    public int getStatus() {
    	return this.status;
    }
    
    /**
     * 设定上级部门ID
     * @param parentId 上级部门ID
     */
    public void setParentId(Long parentId) {
		this.parentId = parentId;
    }

    /**
     * 取得上级部门ID
     * @return parentId
     */
    public Long getParentId() {
		if (parentId == null)
			return new Long(0);
    	return this.parentId;
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
