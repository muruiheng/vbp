package com.sgai.vbp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * yyyy-MM
	 */
	public static final String MONTH_FORMAT = "yyyy-MM";
	
	/**
	 * yyyy
	 */
	public static final String YEAR_FORMAT = "yyyy";

	/**
	 * 获取当前时间串，格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static final String getCurrDatetime() {
		return format(new Date(), DATETIME_FORMAT);
	}

	/**
	 * 当前时间
	 * @return Timestamp
	 */
	public static final Timestamp getCurTimestamp() {
		return parseDate(getCurrDatetime());
	}
	/**
	 * 获取当前日期串，格式为yyyy-MM-dd
	 * @return String
	 */
	public static final String getCurrDate() {
		return format(new Date(), DATE_FORMAT);
	}
	
	/**
	 * 获取当前日期串，格式为yyyy-MM
	 * @return String
	 */
	public static final String getCurrMonth() {
		return format(new Date(), MONTH_FORMAT);
	}
	
	/**
	 * 获取当前日期串，格式为yyyy
	 * @return String
	 */
	public static final String getCurrYear() {
		return format(new Date(), YEAR_FORMAT);
	}

	/**
	 * @param date
	 *            时间
	 * @param formatStr
	 *            格式化串
	 * @return
	 */
	private static String format(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	/**
	 * @param date
	 *            时间
	 * @param formatStr
	 *            格式化串
	 * @return
	 */
	public static String format(Timestamp datetime, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(datetime);
	}
	
	/**
	 * @param datetime Timestamp 时间 默认格式： yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Timestamp datetime) {
		return format(datetime, DATETIME_FORMAT);
	}
	/**
	 * {@code time1}是否小于{@code time2},即类似于
	 * 
	 * <pre>
	 * time1 &lt; time2
	 * </pre>
	 * 
	 * 。 如果{@code time2}为<code>null</code>， 则视为最小。
	 *
	 * @param time1
	 *            时间字符串，格式为 yyyy-MM-dd HH-mm-ss，不足19位后补0
	 * @param time2
	 *            时间字符串，格式为 yyyy-MM-dd HH-mm-ss，不足19位后补0
	 * @return
	 */
	public static boolean lessThan(String time1, String time2) {
		if (!AssertUtil.isVal(time1)) {
			if (!AssertUtil.isVal(time2)) {
				return false;
			} else {
				return true;
			}
		} else {
			return time1.compareTo(time2) < 0;
		}
	}

	/**
	 * {@code time1}是否大于{@code time2},即类似于
	 * 
	 * <pre>
	 * time1 &gt; time2
	 * </pre>
	 * 
	 * 。如果{@code time2}为<code>null</code>， 则视为最大。
	 *
	 * @param time1
	 *            时间字符串，格式为 yyyyMMddHHmmss，不足14位后补9
	 * @param time2
	 *            时间字符串，格式为 yyyyMMddHHmmss，不足14位后补9
	 * @return
	 */
	public static boolean greaterThan(String time1, String time2) {
		if (!AssertUtil.isVal(time1)) {
			if (!AssertUtil.isVal(time2)) {
				return false;
			} else {
				return true;
			}
		} else {
			return time1.compareTo(time2) > 0;
		}
	}

	/**
	 * 将<code>datetime</code>字符串时间转换为毫秒数
	 * 
	 * @param datetime
	 *            长度必须大于等于8而小于等于14，格式为 yyyyMMddHHmmss，不足14位后补0
	 * @return
	 */
	public static long toMilliseconds(String datetime) {
		return parseDate(datetime).getTime();
	}

	/**
	 * 将格式为{@link #SHOW_DATETIME_FORMAT}的时间格式解析为Date对象,{@code datetime}
	 * 的长度必须大于8小于14.
	 * 
	 * @param datetime
	 * @return
	 */
	public static Timestamp parseDate(String datetime) {
		if (!AssertUtil.isVal(datetime)) 
			return null;
		MixDateFormat dataFormat = new MixDateFormat(); 
		try {
			Date date = dataFormat.parse(datetime);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("入参datetime：" + datetime
					+ "解析异常，请检查格式必须为：" + DATETIME_FORMAT);
		}
	}
	
	
	/**
	 * 添加年份
	 * @param dateTime Timestamp
	 * @param years int
	 * @return Timestamp
	 */
	public static Timestamp addYears(Timestamp dateTime, int years) {
		return addDateTime(Calendar.YEAR, dateTime, years);
	}
	
	/**
	 * 添加月份
	 * @param dateTime Timestamp
	 * @param months int
	 * @return Timestamp
	 */
	public static Timestamp addMonths(Timestamp dateTime, int months) {
		return addDateTime(Calendar.MONTH, dateTime, months);
	}
	
	/**
	 * 添加工作周
	 * @param dateTime Timestamp
	 * @param months int
	 * @return Timestamp
	 */
	public static Timestamp addWeeks(Timestamp dateTime, int weeks) {
		return addDateTime(Calendar.WEEK_OF_YEAR, dateTime, weeks);
	}
	
	/**
	 * 日 加减
	 * @param dateTime Timestamp
	 * @param months int
	 * @return Timestamp
	 */
	public static Timestamp addDays(Timestamp dateTime, int days) {
		return addDateTime(Calendar.DAY_OF_YEAR, dateTime, days);
	}
	
	/**
	 * 小时 的加减
	 * @param dateTime Timestamp
	 * @param hours int
	 * @return Timestamp
	 */
	public static Timestamp addHours(Timestamp dateTime, int hours) {
		return addDateTime(Calendar.HOUR, dateTime, hours);
	}
	
	
	/**
	 * 小时 的加减
	 * @param dateTime Timestamp
	 * @param hours int
	 * @return Timestamp
	 */
	public static Timestamp addMinutes(Timestamp dateTime, int minutes) {
		return addDateTime(Calendar.MINUTE, dateTime, minutes);
	}
	
	/**
	 * 根据传入时间获取改天起始时间 
	 * <pre>
	 *  例如： 当前时间 2015-2-27 13:30:50 返回时间为：2015-2-27 00:00:00
	 * </pre>
	 * @param dateTime
	 * @return
	 */
	public static String getDayBegin(Timestamp dateTime) {
		dateTime = parseDate(format(dateTime, DATE_FORMAT) + " 00:00:00");
		return format(dateTime);
	}
	
	/**
	 * 根据传入时间获取改天截止时间 
	 * <pre>
	 *  例如： 当前时间 2015-2-27 13:30:50 返回时间为：2015-2-27 23:59:59
	 * </pre>
	 * @param dateTime
	 * @return
	 */
	public static String getDayEnd(Timestamp dateTime) {
		dateTime = parseDate(format(dateTime, DATE_FORMAT) + " 23:59:59");
		return format(dateTime);
	}
	/**
	 * 年月日的加减
	 * @param field Calendar.DAY_OF_MONTH 
	 * @param dateTime Timestamp
	 * @param date int
	 * @return Timestamp
	 */
	private static Timestamp addDateTime(int field, Timestamp dateTime, int date) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(dateTime);
		calendar.add(field, date);
		return new Timestamp(calendar.getTime().getTime());
	}
	
	/**
	 * 
	 * @param date yyyy-MM-dd /yyyy-MM
	 * @return
	 */
	public static int getMonthDays(String date) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		return getMonthDays(parseDate(date));
	}
	
	/**
	 * 
	 * @param date Timestamp
	 * @return
	 */
	public static int getMonthDays(Timestamp date) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		return getDays(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @param date Timestamp
	 * @return
	 */
	public static int getYearDays(Timestamp date) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		return getDays(date, Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 
	 * @param date Timestamp
	 * @return
	 */
	public static int getWeekDays(Timestamp date) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		return getDays(date, Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @param date Timestamp
	 * @return
	 */
	public static int getWeekOfMonth(Timestamp date) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		return getDays(date, Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	
	/**
	 * 获取天数
	 * @param date
	 * @param field
	 * @return
	 */
	private static int getDays(Timestamp date, int field) {
		if (!AssertUtil.isVal(date)) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		return calendar.getMaximum(field);
	}
	
}
