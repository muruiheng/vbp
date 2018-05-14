package com.sgai.vbp.dao.sequence;

import java.sql.Timestamp;

import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.PrimaryKey;

@Entity("tb_sequences")
public class SequenceBean {

	@PrimaryKey("sysCode")
	private String sysCode;
	
	@Column(value="sequence", isNull=false, desc="ID序列")
	private Long sequence;
	
	@Column(value="step", isNull=false, desc="ID步长")
	private Integer step;
	
	@Column(value="createTime")
	private Timestamp createTime;
	
	@Column(value="modifyTime")
	private Timestamp modifyTime;

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
