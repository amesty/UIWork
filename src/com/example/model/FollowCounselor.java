package com.example.model;


import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;


@AVClassName(FollowCounselor.FOLLOWCOUNSELOR_CLASS)
public class FollowCounselor extends AVObject{

	static final String FOLLOWCOUNSELOR_CLASS = "FollowCounselor";
	
	public static final String CONSELOR_ID = "Conselor_id";
	public static final String USER_ID ="user_id";

	private static final String INDEX_KEY = "fc_index";
	
	public int getIndex() {
		return this.getInt(INDEX_KEY);
	}
}
