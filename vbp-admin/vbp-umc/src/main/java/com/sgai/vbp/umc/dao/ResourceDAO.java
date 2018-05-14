package com.sgai.vbp.umc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sgai.vbp.dao.mysql.MysqlDAOSupport;
import com.sgai.vbp.umc.entity.Resource;


/**
 * 菜单按钮资源 DAO Object.
 * <pre>
 * Table Name        : tb_resources
 * Table Description : 菜单按钮资源
 * Data Access Object: ResourceDAO
 * </pre>
 * @author mrh
 */
@Repository
public class ResourceDAO  extends MysqlDAOSupport<Resource>{
   
	/**
	 * 通过ParentId查询资源树节点
	 * @param parentiId
	 * @return
	 */
	public List<Resource> getNodeByParentId(Long parentiId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * from TB_RESOURCES where parentId = ?");
		return super.queryObjList(sql.toString(), parentiId);
	}
}
