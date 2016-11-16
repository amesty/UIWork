package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtil {
	public static final String SUCCESS = "1";
	public static final String FAILED = "-1";
	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static final String VERIFY = "verify";
	public static final String VALUE = "value";
	public static final String ERRMSG = "errmessage";
	
	/**
	 * 获取网页原始内容
	 * @param url
	 * @return
	 */
	public static String getHtmlStringByHttpGet(String url){
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);	
			System.out.println(httpGet.toString());
			
			HttpResponse response = httpClient.execute(httpGet);	
			// 如果成功标示为200，则返回Value值，否则返回空
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			httpClient.getConnectionManager().shutdown();			
		}
		return null;
	}
	
	/**
     * 根据网址得到返回的内容
     * @param url
     * @return
     */
    public static JSONObject getRootJsonByHttpGet(String url){
    	String json = getHtmlStringByHttpGet(url);		
		try {
			return new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static String getRequestStatus(JSONObject result){
    	if(result.optString("returnCode").equals("0000"))
    		return SUCCESS;
    	return FAILED;
    }
    
    public static JSONArray getJsonsByHttpGet(String url, String key){
    	JSONObject result = getRootJsonByHttpGet(url);
    	if(result == null){  	
    		return null;
    	}
    	return getValuesFromResponse(result, key);
    }

    public static JSONObject getJsonByHttpGet(String url, String key){
    	JSONObject result = getRootJsonByHttpGet(url);    	
    	if(result == null)
    		return null;
    	return getValueFromResponse(result, key);
    }
    
    /**
     * 得到响应数据中的单值
     * @param response
     * @return
     */
    public static JSONObject getValueFromResponse(JSONObject response, String key){
        JSONObject value = null;
        try {
            value = new JSONObject(response.optString(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 得到响应数据中的多个值集合
     * @param response
     * @return
     */
    public static JSONArray getValuesFromResponse(JSONObject response, String key){
        JSONArray value = null;
        try {
            value = new JSONArray(response.optString(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    /**
     * 发送POST请求，普通编码
     * @param url
     * @return
     */
    public static String getHtmlStringByHttpPostJSONWithoutUrlEncoding(String url, String jsonParams){
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);	
			// 设置请求头
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			// 设置数据实体
			StringEntity se = new StringEntity(jsonParams);
			
			// 设置实体类型为文本类型
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			// 设置内容编码
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
	        // 设置实体
	        httpPost.setEntity(se);
			System.out.println("url = " + url);
			System.out.println("参数为：" + jsonParams);
			
			// 执行POST请求，得到返回结果
			HttpResponse response = httpClient.execute(httpPost);	

			// 如果成功标示为200，则返回Value值，否则返回空
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			httpClient.getConnectionManager().shutdown();			
		}
		return null;
	}
    
    /**
     * 使用URL编码访问网络
     * @param url
     * @param jsonParams
     * @return
     */
    public static String getHtmlStringByHttpPostJSON(String url, String jsonParams){
		// 对中文参数进行编码
		String encoderJson = "";
		try {
			encoderJson = URLEncoder.encode(jsonParams, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return getHtmlStringByHttpPostJSONWithoutUrlEncoding(url, encoderJson);
    }
    
    
    /**
     * POST请求复合类型参数
     * @param url
     * @param multiEntity
     * @return
     */
//    public static String getHtmlStringByHttpPostMartipart(String url, MultipartEntity multiEntity){
//		HttpClient httpClient = new DefaultHttpClient();
//		try {
//			HttpPost httpPost = new HttpPost(url);	
//			System.out.println(httpPost.toString());
//	        httpPost.setEntity(multiEntity);
//			// 执行POST请求，得到返回结果
//			HttpResponse response = httpClient.execute(httpPost, new BasicHttpContext());	
//			// 如果成功标示为200，则返回Value值，否则返回空
//			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				HttpEntity entity = response.getEntity();
//				return EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//		} finally{
//			httpClient.getConnectionManager().shutdown();			
//		}
//		return null;
//	}
    
    /**
     * 根据网址得到返回的内容
     * @param url
     * @return
     */
//    public static JSONObject getRootJsonByHttpPost(String url, MultipartEntity multiEntity){
//    	String json = getHtmlStringByHttpPostMartipart(url, multiEntity);		
//		try {
//			return new JSONObject(json);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//    public static JSONArray getJsonsByHttpPost(String url, MultipartEntity multiEntity, String key){
//    	JSONObject result = getRootJsonByHttpPost(url, multiEntity);
//    	if(result == null){  	
//    		return null;
//    	}
//    	return getValuesFromResponse(result, key);
//    }
//
//    public static JSONObject getJsonByHttpPost(String url, MultipartEntity multiEntity, String key){
//    	JSONObject result = getRootJsonByHttpPost(url, multiEntity);
//    	if(result == null)
//    		return null;
//    	return getValueFromResponse(result, key);
//    }
    

    public static JSONArray getJsonsByHttpPostJsonParam(String url, String jsonParams, String key){
    	String json = getHtmlStringByHttpPostJSON(url, jsonParams);
    	System.out.println("收到的结果为：" + json);
    	if(json == null) return null;
    	
    	try {    		
    		JSONObject result = new JSONObject(json);
    		return getValuesFromResponse(result, key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public static JSONObject getJsonByHttpPostJsonParam(String url, String jsonParams, String key){
    	String json = getHtmlStringByHttpPostJSON(url, jsonParams);
    	if(json == null) return null;

    	try {    		
    		JSONObject result = new JSONObject(json);
    		return getValueFromResponse(result, key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
