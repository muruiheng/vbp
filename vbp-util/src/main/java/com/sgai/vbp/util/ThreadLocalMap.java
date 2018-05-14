package com.sgai.vbp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程缓存连接使用
 * 
 * @author mrh
 *
 */
public class ThreadLocalMap {
	
	private static final Logger log = LoggerFactory.getLogger(ThreadLocalMap.class);
	protected final static ThreadLocal<Map<Object, Object>> threadContext = new MapThreadLocal();

	private ThreadLocalMap() {
	};

	public static void put(Object key, Object value) {
		getContextMap().put(key, value);
	}

	public static Object remove(Object key) {
		return getContextMap().remove(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object key) {
		return (T) getContextMap().get(key);
	}

	public static boolean containsKey(Object key) {
		return getContextMap().containsKey(key);
	}

	private static class MapThreadLocal extends ThreadLocal<Map<Object, Object>> {
		protected Map<Object, Object> initialValue() {
			return new HashMap<Object, Object>() {
				private static final long serialVersionUID = 3637958959138295593L;
				public Object put(Object key, Object value) {
					if (log.isDebugEnabled()) {
						if (containsKey(key)) {
							log.debug("Overwritten attribute to thread context: " + key + " = " + value);
						} else {
							log.debug("Added attribute to thread context: " + key + " = " + value);
						}
					}
					return super.put(key, value);
				}
			};
		}
	}

	/**
	 * 取得thread context Map的实例。
	 *
	 * @return thread context Map的实例
	 */
	protected static Map<Object, Object> getContextMap() {
		return (Map<Object, Object>) threadContext.get();
	}

	/**
	 * 清理线程所有被hold住的对象。以便重用！
	 */

	public static void reset() {
		getContextMap().clear();
	}

	/**
	 * 缓存的所有key
	 * 
	 * @return Set<Object>
	 */
	public static Set<Object> keySet() {
		return getContextMap().keySet();
	}
}
