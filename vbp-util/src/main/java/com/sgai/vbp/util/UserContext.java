package com.sgai.vbp.util;

import com.alibaba.fastjson.JSONObject;

public class UserContext {
	
	public static final String USER_CONTEXT = UserContext.class.getName();
	public static final String USER_CONTEXT_TOKEN = UserContext.class.getName() + ".token";
	public static final String USER_CONTEXT_OPENID = UserContext.class.getName() + ".openid";
	public static final String USER_CONTEXT_IP = UserContext.class.getName() + ".ip";
	
	
	/**
	 * 构造方法，用户访问系统时，通过UserInfoInterceptor自动构造
	 */
	private UserContext() {
	}
	
	/**
	 * 当前登录人ID
	 * @return
	 */
	public static Long getUserId() {
		return getLongValue("userid");
	}
	
	public static String getUserName() {
		return getStringValue("userName");
	}
	
	/**
	 * 获取用户姓名
	 * @return
	 */
	public static String getCnName() {
		return getStringValue("cnName");
	}
	
	/**
	 * 当前登录人性别 0-女性 1-男性
	 * @return
	 */
	public static String getSex() {
		return getStringValue("sex");
	}

	/**
	 * 当前登录人联系电话
	 * @return
	 */
	public static String getTelephone() {
		return getStringValue("telephone");
	}
	
	/**
	 * 当前登录人的openid
	 * @return
	 */
	public static String getOpenID() {
		return ThreadLocalMap.get(USER_CONTEXT_OPENID);
	}
	
	/**
	 * 当前登录人的token
	 * @return
	 */
	public static String getToken() {
		return ThreadLocalMap.get(USER_CONTEXT_TOKEN);
	}
	
	/**
	 * 用户IP
	 * @return
	 */
	public static String getUserIP() {
		return ThreadLocalMap.get(USER_CONTEXT_IP);
	}
	
	public static boolean isLoginSuccess(String token) {
		 String openid = ThreadLocalMap.get(USER_CONTEXT_TOKEN);
		 if (!AssertUtil.isVal(openid))
			 return false;
		 String userOpenid = getStringValue("openid");
		 if (!AssertUtil.isVal(userOpenid)) 
			 return false;
		 if (openid.equals(userOpenid) && token.equals(getToken())) 
			 return true;
		 else 
			 return false;
	}
	/**
	 * 获取String
	 * @param key
	 * @return
	 */
	private static String getStringValue(String key) {
		JSONObject user = ThreadLocalMap.get(USER_CONTEXT);
		if (AssertUtil.isVal(user))
			return user.getString(key);
		else
			return null;
	}
	
	/**
	 * 获取String
	 * @param key
	 * @return
	 */
	private static Long getLongValue(String key) {
		String  val = getStringValue(key);
		if (AssertUtil.isVal(val))
			return new Long(val);
		else
			return new Long(-1);
	}
}
