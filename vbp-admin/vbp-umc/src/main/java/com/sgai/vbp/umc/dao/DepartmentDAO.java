package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.Department;


/**
 * 部门表 DAO Object.
 * <pre>
 * Table Name        : tb_departments
 * Table Description : 部门表
 * Data Access Object: DepartmentDAO
 * </pre>
 * @author mrh
 */
@Repository
public class DepartmentDAO  extends MysqlDAOSupport<Department>{
   
}
