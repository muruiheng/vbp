package com.sgai.vbp.web.http;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.sgai.vbp.util.AssertUtil;
import com.sgai.vbp.util.DateTimeUtils;
import com.sgai.vbp.util.MixDateFormat;
import com.sgai.vbp.util.json.JSONUtil;
import com.sgai.vbp.util.page.PageBean;

/**
 * <pre>
 * 功能： 用于封装用户的请求，将请求转换为相应的对象
 * </pre>
 * @author mrh 2016年3月24日
 */
public class JSONRequest extends LinkedHashMap<String, Object>{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9005858241290023408L;

	/**
	 * LOGGER - log4j
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONRequest.class);
	
	
	/**
	 * 根据Class获取对应的VO对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, String voName) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		Object obj =  this.get(voName);
		if (!AssertUtil.isVal(obj)) {
			return null;
		}
		if (obj instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) this.get(voName);
			return this.getVOFormMap(clazz, map);
		} if (obj instanceof String && JSONUtil.validate(obj.toString())) {
			return  this.parseObject(JSONObject.parseObject(obj.toString()), clazz);
		} else {
			throw new RuntimeException(voName + "的目标值的数据类型不是" + clazz.getName() + "，请确认数据类型!");
		}
	}
	
	/**
	 * 根据Class获取对应的VO对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T get(Class<T> clazz) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		if (!AssertUtil.isVal(fields)) {
			return null;
		}
		JSONObject json = new JSONObject();
		for (Field field : fields) {
			json.put(field.getName(), this.get(field.getName()));
		}
		return this.parseObject(json, clazz);
	}
	
	/**
	 * 根据Class获取对应的VO对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> clazz, String listName) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>)this.get(listName);
		if (!AssertUtil.isVal(list)) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (int index = 0; index < list.size(); index++) {
			result.add(this.getVOFormMap(clazz, list.get(index)));
		}
		return result;
	}
	
	/**
	 * 根据Class获取对应的VO对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getBaseList(Class<T> clazz, String listName) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		List<T> list = (List<T>)this.get(listName);
		if (!AssertUtil.isVal(list)) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (int index = 0; index < list.size(); index++) {
			T t = list.get(index);
			if (t instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)t;
				result.add(this.getVOFormMap(clazz, map));
			}
			else if(clazz.equals(Long.class)){
				Long valueOf = Long.valueOf(t.toString());
				result.add((T)valueOf);
			}
			else if (clazz.equals(Integer.class)) {
				Integer valueOf = Integer.valueOf(t.toString());
				result.add((T)valueOf);
			}
			else if (clazz.equals(String.class)) {
				String valueOf = String.valueOf(t);
				result.add((T)valueOf);
			}
			else {
				result.add(t);
			}
		}
		return result;
	}
	
	/**
	 * 返回String类型的请求参数 
	 * @param parameter
	 * @return
	 */
	public String getString(String parameter) {
		if (!AssertUtil.isVal(this.values())) {
			return null;
		}
		Object obj = this.get(parameter);
		if (!AssertUtil.isVal(obj)) {
			return null;
		}
		if (!(obj instanceof String)) {
			throw new RuntimeException("目标值的数据类型不是String，请确认目标值的数据类型！");
		}
		return obj.toString(); 
	}
	
	/**
	 * 返回String类型的请求参数 
	 * @param parameter
	 * @return
	 */
	public int getInt(String parameter) {
		if (!AssertUtil.isVal(this.values())) {
			return 0;
		}
		Object obj = this.get(parameter);
		if (!AssertUtil.isVal(obj)) {
			return 0;
		}
		if (obj instanceof String) {
			return Integer.parseInt(obj.toString()); 
		}
		if (obj instanceof Integer) {
			return ((Integer)obj).intValue(); 
		} 
		return Integer.parseInt(obj.toString()); 
	}
	
	public Long getLong(String parameter) {
		if (!AssertUtil.isVal(this.values())) {
			return new Long(0);
		}
		Object obj = this.get(parameter);
		if (!AssertUtil.isVal(obj)) {
			return new Long(0);
		}
		if (obj instanceof String) {
			return Long.parseLong(obj.toString()); 
		}
		return Long.parseLong(obj.toString()); 
	}
	/**
	 * 获取前台的 BigDecimal 类型的参数
	 * @param parameter String
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String parameter) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		Object obj = this.get(parameter);
		if (!AssertUtil.isVal(obj)) {
			return null;
		}
		
		if (obj instanceof BigDecimal) {
			return (BigDecimal)obj;
		}
		if (obj instanceof String) {
			return new BigDecimal(this.getString(parameter));
		}
		if (obj instanceof Double) {
			return new BigDecimal(obj.toString());
		}
		if (obj instanceof Integer) {
			return new BigDecimal(obj.toString());
		}
		if (obj instanceof Long) {
			return new BigDecimal(obj.toString());
		}
		if (obj instanceof Float) {
			return new BigDecimal(obj.toString());
		}
		throw new RuntimeException("目标值的数据类型不是BigDecimal，请确认目标值的数据类型！");
	}
	
	/**
	 * 获取前台的 Timestamp 类型的参数
	 * @param parameter
	 * @return
	 */
	public Timestamp getTimestamp(String parameter) {
		if (!AssertUtil.isVal(this.values())) {
			LOGGER.debug("the val is null");
			return null;
		}
		Object obj = this.get(parameter);
		if (!AssertUtil.isVal(obj)) {
			return null;
		}
		
		if (!AssertUtil.isVal(obj.toString())) {
			return null;
		}
		if (obj instanceof Timestamp) {
			return (Timestamp)obj;
		}
		if (obj instanceof String) {
			return DateTimeUtils.parseDate(this.getString(parameter));
		}
		if (obj instanceof Long) {
			return new Timestamp(((Long) obj).longValue());
		}
		throw new RuntimeException("目标值的数据类型不是Timestamp，请确认目标值的数据类型！");
	}
	
	public PageBean getPageBean() {
		int currentPage = this.getInt("currentPage");
		currentPage = currentPage > 0 ? currentPage : 1;
		int pageSize = this.getInt("itemsPerPage");
		return new PageBean((currentPage -1) * pageSize,  pageSize);
	}
	
	/**
	 * 从Map集合中获取javaBean对象
	 * @param clazz Class
	 * @param map Map<String, Object> 
	 * @return T
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	protected <T> T getVOFormMap(Class<T> clazz, Map<String, Object> map) {
		if (!AssertUtil.isVal(map)) {
			LOGGER.debug("the map is null");
			return null;
		}
		JSONObject json = new JSONObject(map);
		return this.parseObject(json, clazz);
	}
	
	/**
	 * 将json对象转换成javaBean
	 * @param json
	 * @param Class
	 * @return
	 */
	protected <T> T parseObject(JSONObject json, Class<T> clazz) {
		String input = json.toJSONString();
		if (input == null) {
			return null;
		}

		DefaultJSONParser parser = new DefaultJSONParser(input, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
		MixDateFormat dataFormat = new MixDateFormat();
		parser.setDateFomrat(dataFormat);
		
		T value = (T) parser.parseObject(clazz);
		handleResovleTask(parser, value);
		parser.close();
		return (T) value;
	}
	
	/**
	 * 根据配置信息将数据进行格式化
	 * @param parser
	 * @param value
	 */
	protected <T> void handleResovleTask(DefaultJSONParser parser, T value) {
		if (parser.isEnabled(Feature.DisableCircularReferenceDetect)) {
			return;
		}
		int size = parser.getResolveTaskList().size();
		for (int i = 0; i < size; ++i) {
			ResolveTask task = parser.getResolveTaskList().get(i);

			Object object = null;
			if (task.ownerContext != null) {
				object = task.ownerContext.object;
			}

			String ref = task.referenceValue;
			Object refValue;
			if (ref.startsWith("$")) {
				refValue = parser.getObject(ref);
			} else {
				refValue = task.context.object;
			}
			task.fieldDeserializer.setValue(object, refValue);
		}
	}
}
