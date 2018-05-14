package com.sgai.vbp.util.excel;

public enum StyleKey {

	Title("title", "表头", 1), Right("right", "右对齐", 2),Left("left", "左对齐", 3),Center("center", "居中", 3);
	private String name;
	private String desc;
	private int index;
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public int getIndex() {
		return index;
	}
	private StyleKey(String name, String desc, int index) {
		this.name = name;
		this.desc = desc;
		this.index = index;
	}
}
