package com.sgai.vbp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSONObject;
import com.sgai.vbp.util.json.JSONUtil;

/**
 * http 请求处理客户端
 * @author mrh
 *
 */
public class HttpClientUtil {
	private static final String CHARSET = "UTF-8";
	/**
	 * 200 访问正常
	 */
	private static final int STATU_CODE_NOMAL = 200;
	
	/**
	 * http request method GET
	 */
	private static final String HTTP_METHOD_GET = "GET";
	
	/**
	 * http request method POST
	 */
	private static final String HTTP_METHOD_POST = "POST";
	
	/**
	 * 执行GET方式的请求
	 * @param urlStr 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static final JSONObject get(String urlStr, Map<String, Object> paramMap) throws Exception {
		return excute(urlStr, HTTP_METHOD_GET, paramMap);
	}
	
	/**
	 * 执行GET方式的请求 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static final JSONObject get(String urlStr) throws Exception {
		return excute(urlStr, HTTP_METHOD_GET, null);
	}
	
	
	/**
	 * 执行POST方式的请求
	 * @param urlStr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @
	 */
	public static final JSONObject post(String urlStr, Map<String, Object> paramMap) throws Exception {
		return excute(urlStr, HTTP_METHOD_POST, paramMap);
	}
	
	/**
	 * 
	 * @param urlStr
	 * @param httpMethod
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private static JSONObject excute(String urlStr, String httpMethod, Map<String, Object> paramMap) throws Exception{
		return excuteBase(urlStr, httpMethod, paramMap);
	}
	/**
	 * 执行http请求
	 * @param urlStr String
	 * @param httpMethod String
	 * @param paramMap  Map<String, String> 
	 * @return the json Object String
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static JSONObject excuteBase(String urlStr, String httpMethod, Map paramMap) throws Exception{
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod(httpMethod);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
		connection.setRequestProperty("Cookie", "dsid=81fd887c-6932-4672-bb15-559cd923d845;");
		
		connection.connect();
		if (AssertUtil.isVal(paramMap)) {
			JSONObject obj = new JSONObject();
			obj.putAll(paramMap);
			OutputStreamWriter water = new OutputStreamWriter(connection.getOutputStream(), CHARSET);
			water.write(obj.toJSONString());
			water.flush();
			water.close();
		}
		int responseCode = connection.getResponseCode();
		if (responseCode != STATU_CODE_NOMAL) {
			throw new ConnectException(urlStr + "连接异常！状态吗：" + responseCode);
		}
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), CHARSET));
		String line ="";
		StringBuffer strBuffer = new StringBuffer();
		while ((line = bufferedReader.readLine()) != null) {
			strBuffer.append(line);
		}
		if (JSONUtil.validate(strBuffer.toString())) {
			return JSONObject.parseObject(strBuffer.toString());
		}
		return null;
	}
	
	
	
	
	/**
	 * 执行GET方式的请求
	 * @param urlStr 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static final JSONObject httpsGget(String urlStr, Map<String, Object> paramMap) throws Exception {
		return httpsExeBase(urlStr, HTTP_METHOD_GET, paramMap);
	}
	
	/**
	 * 执行GET方式的请求 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static final JSONObject httpsGet(String urlStr) throws Exception {
		return httpsExeBase(urlStr, HTTP_METHOD_GET, null);
	}
	
	
	/**
	 * 执行POST方式的请求
	 * @param urlStr
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @
	 */
	public static final JSONObject httpsPost(String urlStr, Map<String, Object> paramMap) throws Exception {
		return httpsExeBase(urlStr, HTTP_METHOD_POST, paramMap);
	}
	
	/**
	 * 执行https请求
	 * @param urlStr String
	 * @param httpMethod String
	 * @param paramMap  Map<String, String> 
	 * @return the json Object String
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static JSONObject httpsExeBase(String urlStr, String httpMethod, Map paramMap) throws Exception{
		
		SSLContext sslcontext = SSLContext.getInstance("TLS"); 
        sslcontext.init(null, new TrustManager[]{myX509TrustManager}, null);
         
		URL url = new URL(urlStr);
		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setSSLSocketFactory(sslcontext.getSocketFactory());
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod(httpMethod);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
		connection.connect();
		
		try (DataOutputStream out = new DataOutputStream(connection.getOutputStream());) {
			JSONObject obj = new JSONObject();
			if (AssertUtil.isVal(paramMap))
				obj.putAll(paramMap);
			out.writeBytes(obj.toJSONString());
			out.flush();
			out.close();
		}
		
		int responseCode = connection.getResponseCode();
		if (responseCode != STATU_CODE_NOMAL) {
			throw new ConnectException(urlStr + "连接异常！状态吗：" + responseCode);
		}
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), CHARSET));
		String line ="";
		StringBuffer strBuffer = new StringBuffer();
		while ((line = bufferedReader.readLine()) != null) {
			strBuffer.append(line);
		}
		if (JSONUtil.validate(strBuffer.toString())) {
			return JSONObject.parseObject(strBuffer.toString());
		}
		return null;
	}
	
	private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	};
	
}
