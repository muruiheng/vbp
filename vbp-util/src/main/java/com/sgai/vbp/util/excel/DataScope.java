package com.sgai.vbp.util.excel;

public enum DataScope {

	TITLE_DATA("A", " 标题和数据",1),
	DATA("B", "数据", 2);
	
	
	private String name;
	private String desc;
	private int index;
	
	
	private DataScope(String name, String desc, int index) {
		this.name = name;
		this.desc = desc;
		this.index = index;
	}


	public String getName() {
		return name;
	}


	public String getDesc() {
		return desc;
	}


	public int getIndex() {
		return index;
	}
}
