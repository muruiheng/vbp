package com.sgai.vbp.web.http.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.sgai.vbp.util.AssertUtil;
import com.sgai.vbp.util.json.JSONUtil;

import jodd.io.StreamUtil;

public class ReadableHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;
	private static final String JSON = "{}";
	
	public ReadableHttpServletRequestWrapper(HttpServletRequest request)
			throws IOException {
		super(request);
		if ("POST".equals(request.getMethod().toUpperCase())) {
			byte[] tempbody = StreamUtil.readBytes(request.getReader(), "UTF-8");
			
			if (!AssertUtil.isVal(tempbody)) {
				tempbody = JSON.getBytes();
			}
			
			if (!this.isJson(tempbody)) {
				tempbody = this.convert2JsonString(tempbody);
			}
			body = tempbody;
		} else {
			body = JSON.getBytes();
		}
		
	}

	
	/**
	 * 
	 * @param body
	 * @return
	 */
	private boolean isJson(byte[] body) {
		try {
			String json = new String(body, "UTF-8");
			return JSONUtil.validate(json);
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 返回json在byte数组
	 * @param body
	 * @return
	 */
	private byte[] convert2JsonString(byte[] body) {
		if (!AssertUtil.isVal(body)) {
			return "{}".getBytes();
		}
		String json = new String(body);
		String[] paramArr = json.split("&");
		String res = "";
		for(int i = 0; i < paramArr.length; i++) {
			String[] kv = paramArr[i].split("=");
			if(kv.length == 2) {
				res = res +"," + "\""+kv[0]+"\""+":"+"\""+kv[1]+"\"";
			} else {
				res = res +"," + "\""+kv[0]+"\""+":"+"\"\"";
			}
		}
		res = "{" + res.substring(1) + "}";
		return res.getBytes();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {
			}

		};
	}
}
