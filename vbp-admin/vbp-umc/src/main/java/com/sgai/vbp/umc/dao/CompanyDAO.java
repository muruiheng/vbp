package com.sgai.vbp.umc.dao;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.Company;


/**
 * 公司表 DAO Object.
 * <pre>
 * Table Name        : tb_companys
 * Table Description : 公司表
 * Data Access Object: CompanyDAO
 * </pre>
 * @author mrh
 */
@Repository
public class CompanyDAO  extends MysqlDAOSupport<Company>{
   
}
