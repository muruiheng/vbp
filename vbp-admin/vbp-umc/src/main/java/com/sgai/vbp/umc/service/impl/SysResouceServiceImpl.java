package com.sgai.vbp.umc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgai.vbp.umc.dao.ResourceDAO;
import com.sgai.vbp.umc.entity.Resource;
import com.sgai.vbp.umc.service.SysResourceService;
import com.sgai.vbp.util.AssertUtil;
import com.sgai.vbp.util.exception.CheckException;
import com.sgai.vbp.util.tree.TreeNode;

@Service
public class SysResouceServiceImpl implements SysResourceService {

	@Autowired
	private ResourceDAO resourceDAO;
	
	@Transactional
	@Override
	public void doSave(Resource resource) {
		if (!AssertUtil.isVal(resource)) {
			throw new CheckException("资源信息不能为空");
		}
		if (!AssertUtil.isVal(resource.getId()) || resource.getId().longValue() == 0l) {
			//TODO ADD
		} else {
			
		}
	}

	@Transactional
	@Override
	public void doDelete(Long id) {
		// TODO Auto-generated method stub
		Resource r = new Resource();
		r.setId(id);
		resourceDAO.delete(r);
	}

	@Override
	public List<TreeNode> getResouceTree() {
		int level = 0;
		TreeNode parent = new TreeNode();
		parent.setId(Long.valueOf(0l));
		return this.getChildResource(parent, level);
	}

	public List<TreeNode> getChildResource(TreeNode parent, int level) {
		List<Resource> children = resourceDAO.getNodeByParentId(parent.getId());
		List<TreeNode> trees = new ArrayList<>();
		if (!AssertUtil.isVal(children)) {
			return null;
		}
		int nextLevel = level+1;
		for (Resource r : children) {
			TreeNode treeNode = new TreeNode(parent, r.getId(), r.getCnName(), r.getMCode(), level);
			TreeNode current = new TreeNode();
			BeanUtils.copyProperties(treeNode, current);
			treeNode.setChildren(this.getChildResource(current, nextLevel));
			trees.add(treeNode);
		}
		return trees;
	}

	@Override
	public Resource getResource(Long id) {
		if (!AssertUtil.isVal(id)) {
			return null;
		}
		return this.resourceDAO.queryByPK(id);
	}
	
	

}
