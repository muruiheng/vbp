package com.sgai.vbp.umc.entity;

import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.sgai.vbp.util.json.JSONUtil;

import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import java.sql.Timestamp;

/**
 * 部门用户关联表 Value Object.
 * <pre>
 * Don't Extend Your Code above , or All changes will lose after next Generating Code
 * Table Name        : tb_dept_user_links
 * Table Description : 部门用户关联表
 * Data Access Object: DeptUserLinkDAO
 * </pre>
 * @author mrh
 */
@Entity ("tb_dept_user_links")
public class DeptUserLink implements Serializable {
    private static final long serialVersionUID = ObjectStreamClass.lookup(DeptUserLink.class).getSerialVersionUID();

/*----------------------------------------------------------------------------*/
/* tb_dept_user_links column Name
/*----------------------------------------------------------------------------*/

	/** ID **/
	@PrimaryKey("id")
	private Long id;
	/** 部门ID **/
	@Column(value="deptId", isNull =false, desc="部门ID")
	private Long deptId;
	/** 用户ID **/
	@Column(value="userId", isNull =false, desc="用户ID")
	private Long userId;
	/** 资料创建时间 **/
	@Column("createTime")
	private Timestamp createTime;
	/** 资料创建人 **/
	@Column("creator")
	private String creator;

/*----------------------------------------------------------------------------*/
/* Creates new DeptUserLinkBase
/*----------------------------------------------------------------------------*/

    /**
     * 构造函数
     */
    public DeptUserLink() {
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
     * 设定部门ID
     * @param deptId 部门ID
     */
    public void setDeptId(Long deptId) {
		this.deptId = deptId;
    }

    /**
     * 取得部门ID
     * @return deptId
     */
    public Long getDeptId() {
		if (deptId == null)
			return new Long(0);
    	return this.deptId;
    }
    
    /**
     * 设定用户ID
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
		this.userId = userId;
    }

    /**
     * 取得用户ID
     * @return userId
     */
    public Long getUserId() {
		if (userId == null)
			return new Long(0);
    	return this.userId;
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
