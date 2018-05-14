package com.sgai.vbp.umc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgai.vbp.umc.entity.Resource;
import com.sgai.vbp.umc.service.SysResourceService;
import com.sgai.vbp.util.tree.TreeNode;
import com.sgai.vbp.web.http.JSONRequest;
import com.sgai.vbp.web.http.JSONResponse;

@RestController
@RequestMapping("/resource")
public class ResourceManagerController {

	@Autowired
	private SysResourceService resourceManager;
	
	/**
	 * 查询所有的根节点
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/0101", method=RequestMethod.GET)//url方法代码格式：00 00 前两位标识操作类型 01-查询 02-保存 03-删除 
	public JSONResponse getRootResource(JSONResponse response) {
		List<TreeNode> tree = resourceManager.getResouceTree();
		response.setMessage("查询成功");
		response.put("tree", tree);
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value="/0102/{id}", method=RequestMethod.GET)//url方法代码格式：00 00 前两位标识操作类型 01-查询 02-保存 03-删除 
	public JSONResponse getTreeNode(@PathVariable("id") Long id, JSONResponse response) {
		Resource resource = resourceManager.getResource(id);
		response.setMessage("查询成功");
		response.put("node", resource);
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value="/0103", method=RequestMethod.GET)//url方法代码格式：00 00 前两位标识操作类型 01-查询 02-保存 03-删除 
	public JSONResponse saveTreeNode(@RequestBody JSONRequest request, JSONResponse response) {
		Resource resource = request.get(Resource.class);
		resourceManager.doSave(resource);
		response.setMessage("查询成功");
		response.put("node", resource);
		return response;
	}
}
