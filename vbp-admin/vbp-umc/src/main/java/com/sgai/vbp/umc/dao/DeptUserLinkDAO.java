package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.DeptUserLink;


/**
 * 部门用户关联表 DAO Object.
 * <pre>
 * Table Name        : tb_dept_user_links
 * Table Description : 部门用户关联表
 * Data Access Object: DeptUserLinkDAO
 * </pre>
 * @author mrh
 */
@Repository
public class DeptUserLinkDAO  extends MysqlDAOSupport<DeptUserLink>{
   
}
