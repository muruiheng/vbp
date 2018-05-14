package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.RolePermissionLink;


/**
 * 角色资源关系表 DAO Object.
 * <pre>
 * Table Name        : tb_role_permissions_link
 * Table Description : 角色资源关系表
 * Data Access Object: RolePermissionLinkDAO
 * </pre>
 * @author mrh
 */
@Repository
public class RolePermissionLinkDAO  extends MysqlDAOSupport<RolePermissionLink>{
   
}
