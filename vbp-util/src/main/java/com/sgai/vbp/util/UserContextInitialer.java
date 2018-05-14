package com.sgai.vbp.util;

import com.alibaba.fastjson.JSONObject;

public class UserContextInitialer {
	
		public static void initialUserContext(JSONObject json) {
			if (!AssertUtil.isVal(json)) {
				throw new IllegalArgumentException("User Info cann't be null!");
			}
			ThreadLocalMap.put(UserContext.USER_CONTEXT, json);
		}
}
