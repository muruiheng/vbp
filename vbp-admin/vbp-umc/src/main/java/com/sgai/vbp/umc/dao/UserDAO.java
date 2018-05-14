package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.User;


/**
 * 用户表 DAO Object.
 * <pre>
 * Table Name        : tb_users
 * Table Description : 用户表
 * Data Access Object: UserDAO
 * </pre>
 * @author mrh
 */
@Repository
public class UserDAO  extends MysqlDAOSupport<User>{
   
}
