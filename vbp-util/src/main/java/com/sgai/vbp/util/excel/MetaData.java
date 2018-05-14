package com.sgai.vbp.util.excel;

public class MetaData {

	private int index;
	private String enName;
	private String cnName;
	private CellType cellType;
	public MetaData(int index, String enName, String cnName, CellType cellType) {
		super();
		this.index = index;
		this.enName = enName;
		this.cnName = cnName;
		this.cellType = cellType;
	}
	public MetaData() {
		super();
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
}
