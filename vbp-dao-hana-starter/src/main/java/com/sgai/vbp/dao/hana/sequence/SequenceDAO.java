package com.sgai.vbp.dao.hana.sequence;

import org.springframework.transaction.annotation.Transactional;

import com.sgai.vbp.dao.exception.DAOException;
import com.sgai.vbp.dao.hana.HanaDAOSupport;
import com.sgai.vbp.dao.sequence.IdSequenceService;
import com.sgai.vbp.dao.sequence.SequenceBean;
import com.sgai.vbp.util.AssertUtil;
import com.sgai.vbp.util.DateTimeUtils;

public class SequenceDAO extends HanaDAOSupport<SequenceBean> implements IdSequenceService {

	private static final String DEFAULT_SYSCODE = "VBP";
	private static int step = 10;
	
	private static long sequence = 0;
	
	private static long currentSeq = 1;
	
	/**
	 * 获取某个系统的下一级ID
	 * @param sysCode
	 * @return
	 */
	@Transactional
	@Override
	public Long nextId(String sysCode) {
		try {
			if (!AssertUtil.isVal(sysCode)) {
				sysCode = DEFAULT_SYSCODE;
			}
			SequenceBean sequenceBean = super.queryByPK(sysCode);
			
			if (!AssertUtil.isVal(sequenceBean)) {
				sequenceBean = this.create();
				sequence = sequenceBean.getSequence().longValue();
				currentSeq = 1l;
			} else if (currentSeq == sequence) {
				sequenceBean = getNext(sequenceBean);
				currentSeq = sequenceBean.getSequence().longValue() ;
			}
			
			currentSeq ++;
			
			return Long.valueOf(currentSeq);
		} catch (Exception e) {
			throw new DAOException("ID生成失败！请稍后再试，或联系管理员！");
		}
		
	}
	
	private SequenceBean create() {
		currentSeq =1;
		SequenceBean sequenceBean = new SequenceBean();
		sequenceBean.setModifyTime(DateTimeUtils.getCurTimestamp());
		sequenceBean.setCreateTime(DateTimeUtils.getCurTimestamp());
		sequenceBean.setStep(step);
		sequenceBean.setSequence((long)step);
		super.create(sequenceBean);
		return sequenceBean;
	}
	
	private SequenceBean getNext(SequenceBean sequenceBean) {
		sequenceBean.setModifyTime(DateTimeUtils.getCurTimestamp());
		sequenceBean.setSequence(sequenceBean.getSequence()+step);
		step = sequenceBean.getStep();
		super.update(sequenceBean);
		return sequenceBean;
	}
}
