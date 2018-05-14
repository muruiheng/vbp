package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.UserRoleLink;


/**
 * 角色人员关系表 DAO Object.
 * <pre>
 * Table Name        : tb_user_role_link
 * Table Description : 角色人员关系表
 * Data Access Object: UserRoleLinkDAO
 * </pre>
 * @author mrh
 */
@Repository
public class UserRoleLinkDAO  extends MysqlDAOSupport<UserRoleLink>{
   
}
