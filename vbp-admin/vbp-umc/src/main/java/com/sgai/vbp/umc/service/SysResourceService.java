package com.sgai.vbp.umc.service;

import java.util.List;

import com.sgai.vbp.umc.entity.Resource;
import com.sgai.vbp.util.tree.TreeNode;

public interface SysResourceService {

	public void doSave(Resource resource);
	
	public void doDelete(Long id);
	
	public List<TreeNode> getResouceTree();
	
	public Resource getResource(Long id);
}
