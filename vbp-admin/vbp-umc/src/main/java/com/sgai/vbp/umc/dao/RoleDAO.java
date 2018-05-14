package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.Role;


/**
 * 角色表 DAO Object.
 * <pre>
 * Table Name        : tb_roles
 * Table Description : 角色表
 * Data Access Object: RoleDAO
 * </pre>
 * @author mrh
 */
@Repository
public class RoleDAO  extends MysqlDAOSupport<Role>{
   
}
