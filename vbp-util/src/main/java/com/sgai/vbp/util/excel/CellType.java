package com.sgai.vbp.util.excel;

public enum CellType {
	
	String("S", "字符类型", 1), Number("N", "数值类型", 2);
	private String name;
	private String desc;
	private int index;
	private CellType(java.lang.String name, java.lang.String desc, int index) {
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
