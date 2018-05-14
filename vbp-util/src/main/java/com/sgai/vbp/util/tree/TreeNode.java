package com.sgai.vbp.util.tree;

import java.util.List;

/**
 * 页面属性操作
 * @author mrh
 *
 */
public class TreeNode {

	private TreeNode parent;
	
	private Long id;
	
	private String label;
	
	private String code;
	
	private int level;
	
	private List<TreeNode> children;


	
	public TreeNode(TreeNode parent, Long id, String label, String code, int level) {
		super();
		this.parent = parent;
		this.id = id;
		this.label = label;
		this.code = code;
		this.level = level;
	}



	public TreeNode() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public List<TreeNode> getChildren() {
		return children;
	}



	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public TreeNode getParent() {
		return parent;
	}



	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
}
