package com.sgai.vbp.dao.sequence;

public interface IdSequenceService {

	/**
	 * 获取ID
	 * @return
	 */
	public Long nextId(String sysCode);
	
}
