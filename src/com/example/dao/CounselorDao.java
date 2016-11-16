package com.example.dao;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.example.model.Counselor;


import android.util.Log;

public class CounselorDao {
	
	public static List<Counselor> findLianaihunyinByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "恋爱婚姻");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findZiwochengzhangByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "自我成长");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findZhichangwentiByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "职场问题");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findQingxuguanliByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "情绪管理");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findQinzijiatingByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "亲子家庭");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findRenjigoutongByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "人际沟通");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}

	public static List<Counselor> findXinlizhangaiByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "心理障碍");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findXuexikunraoByTime() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("updatedAt");
	    query.whereEqualTo("c_speciality", "学习困扰");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findCounselors(String counselorName,String speciality,String city) {

	    AVQuery<Counselor> query1 = AVQuery.getQuery(Counselor.class);
	    if(speciality == "不限"){
	    	speciality = "";
	    }
	    query1.whereContains(Counselor.SPECIALITY, speciality);
	    
	    AVQuery<Counselor> query2 = AVQuery.getQuery(Counselor.class);
	    if(city == "不限"){
	    	city = "";
	    }
	    query2.whereContains(Counselor.CITY, city);
	    
	    AVQuery<Counselor> query3 = AVQuery.getQuery(Counselor.class);
	    query2.whereContains(Counselor.NAME, counselorName);
	    /*List<AVQuery<Counselor>> queries = new ArrayList<>();
	    queries.add(query1);
	    queries.add(query2);*/
	    
	    AVQuery<Counselor> query = AVQuery.and(Arrays.asList(query1, query2,query3));
	    query.orderByDescending("updatedAt");
	    query.limit(1000);
	    try {
	      System.out.println("database list size:" + query.find().size());
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Counselor> findCounselorByHot() {

	    AVQuery<Counselor> query = AVQuery.getQuery(Counselor.class);
	    query.orderByDescending("c_hot");

	    query.limit(5);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query Counselors failed.", exception);
	      return Collections.emptyList();
	    }
	}
}
