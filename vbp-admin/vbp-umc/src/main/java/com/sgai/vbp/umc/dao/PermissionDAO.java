package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.Permission;


/**
 * 权限表 DAO Object.
 * <pre>
 * Table Name        : tb_permissions
 * Table Description : 权限表
 * Data Access Object: PermissionDAO
 * </pre>
 * @author mrh
 */
@Repository
public class PermissionDAO  extends MysqlDAOSupport<Permission>{
   
}
