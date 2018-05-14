package com.sgai.vbp.util.page;

import java.util.List;

/**
 * 分页查询使用
 * <pre>
 *  1、开始--		start
 *  2、最大返回结果集 --	length
 *  3、过滤后最大行数	recordsFiltered
 *  4、数据库最大行数	recordsTotal
 *  4、当前页记录	list
 * </pre>
 * 
 * @author mrh 2016年3月24日
 */
public class PageBean {
	
	/**
	 * 作为返回页面使用的key值（转为JSON对象时的变量）
	 */
	public static final String PAGE_BEAN_KEY = "data";
	
	private int start;   
	private int length;  
	private List<?> list;
	private int recordsTotal;
	/**
	 * 默认构造方法
	 */
	public PageBean() {
		super();
	}

	/**
	 * 构造方法
	 * @param draw
	 * @param start
	 * @param length
	 */
	public PageBean(int start, int length) {
		this.start = start;
		this.length = length;
	}


	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * recordsTotal
	 * @return
	 */
	public int getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * 
	 * @param recordsTotal
	 */
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
}
