package com.sgai.vbp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MixDateFormat extends SimpleDateFormat{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1682198768099242506L;
	

	private static final String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	
	private static final String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	private static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	private static final String FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	private static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	
	private static final String FORMAT_YYYY_MM = "yyyy-MM";
	
	/* (non-Javadoc)
	 * @see java.text.DateFormat#parse(java.lang.String)
	 */
	public MixDateFormat() {
		super(FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
	}


	@Override
	public Date parse(String source) throws ParseException {
		try {
			return super.parse(source);
		} catch (ParseException e) {}
		try {
			if (source.length() == FORMAT_YYYY_MM_DD_HH_MM_SS_SSS_1.length()) {
				this.applyPattern(FORMAT_YYYY_MM_DD_HH_MM_SS_SSS_1);
			} else {
				this.applyPattern(FORMAT_YYYY_MM_DD_HH_MM_SS);
			}
			return super.parse(source);
		} catch (ParseException e) {}
		
		try {
			this.applyPattern(FORMAT_YYYY_MM_DD_HH_MM);
			return super.parse(source);
		} catch (ParseException e) {}
		try {
			this.applyPattern(FORMAT_YYYY_MM_DD_HH);
			return super.parse(source);
		} catch (ParseException e) {}
		try {
			this.applyPattern(FORMAT_YYYY_MM_DD);
			return super.parse(source);
		} catch (ParseException e) {}
		
		try {
			this.applyPattern(FORMAT_YYYY_MM);
			return super.parse(source);
		} catch (ParseException e) {
			throw e;
		}
		
		
	}
}
