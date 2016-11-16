package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Util {
	
	private Util(){}
	

	public static String doHttpGet(HttpClient httpClient, String url) {
		
		String pageContent = null;
		try {

			HttpGet httpGet = new HttpGet(url);	
			HttpResponse response = httpClient.execute(httpGet);		
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

				HttpEntity entity = response.getEntity();
				pageContent = EntityUtils.toString(entity, "GB2312");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return pageContent;
		
	}

	public static String doHttpGet(String url) {

		HttpClient httpClient = new DefaultHttpClient();	
		
		return doHttpGet(httpClient, url);
		
	}
	

	public static String doHttpPost(HttpClient httpClient, String url, UrlEncodedFormEntity param) {
		
		String pageContent = null;
		try {

			HttpPost httpPost = new HttpPost(url);	
			httpPost.setEntity(param);
			HttpResponse response = httpClient.execute(httpPost);		
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

				HttpEntity entity = response.getEntity();
				pageContent = EntityUtils.toString(entity, "UTF-8");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return pageContent;
		
	}

	public static String doHttpPost(String url, UrlEncodedFormEntity param) {
		
		HttpClient httpClient = new DefaultHttpClient();	
		
		return doHttpPost(httpClient, url, param);
		
	}

	public static List<String> regexFind(String sourceString, String regexString){
        List<String> list = new ArrayList<String>();
        
        Pattern p = Pattern.compile(regexString);  
        Matcher m = p.matcher(sourceString);   
                
        while (m.find()) { 
        	String s = m.group(1);
        	list.add(s);
        }   
        return list;  
	}


	public static String regexReplace(String src, String reg, String repStr){
        
        Pattern p = Pattern.compile(reg);  
        Matcher m = p.matcher(src);   
        String res = m.replaceAll(repStr);
        
        return res;
	}
	
	
}
