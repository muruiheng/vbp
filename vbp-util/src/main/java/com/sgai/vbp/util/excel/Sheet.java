package com.sgai.vbp.util.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单
 * 
 * @author mrh
 *
 */
public class Sheet {
	private String sheetName = "";
	private String sheetRef = "";
	private List<Row> rowList = new ArrayList<Row>();
	private Map<Integer, Row> rowMap = new HashMap<Integer, Row>();
	private List<Merge> mergeList = new ArrayList<Merge>();
	private Map<Integer, Integer> columnWidthMap = new HashMap<Integer, Integer>();
	private int rows = 0;

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<Row> getRowList() {
		return this.rowList;
	}

	public Map<Integer, Row> getRowMap() {
		return this.rowMap;
	}

	public List<Merge> getMergeList() {
		return this.mergeList;
	}

	public Map<Integer, Integer> getColumnWidthMap() {
		return this.columnWidthMap;
	}

	public Row getRow(int rowIndex) {
		return ((Row) this.rowMap.get(Integer.valueOf(rowIndex)));
	}

	protected String getSheetRef() {
		return this.sheetRef;
	}

	protected void setSheetRef(String sheetRef) {
		this.sheetRef = sheetRef;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}