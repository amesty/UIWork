package com.example.model;

import android.text.format.DateFormat;

import java.io.Serializable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@SuppressWarnings("serial")
@AVClassName(Problem.PROBLEM_CLASS)
public class Problem extends AVObject implements Serializable{
	
	static final String PROBLEM_CLASS = "Problem";
	
	public static final String INDEX_KEY = "problem_index";
	public static final String CONTENT_KEY = "problem_content";
	public static final String TYPE_KEY = "problem_type";
	public static final String ANONYMOUS_KEY = "isAnonymous";
	public static final String COMMENTCOUNT_KEY = "problem_icomment";
	public static final String USER_KEY = "user_id";

	public int getIndex() {
		return this.getInt(INDEX_KEY);
	}
	
	public String getContent() {
		return this.getString(CONTENT_KEY);
	}
	
	public void setContent(String content) {
		this.put(CONTENT_KEY, content);
	}
	
	public String getType() {
		return this.getString(TYPE_KEY);
	}
	
	public void setType(String type) {
		this.put(TYPE_KEY, type);
	}
	
	public boolean getAnonymous() {
		return this.getBoolean(ANONYMOUS_KEY);
	}
	
	public void setAnonymous(boolean isAnonymous) {
		this.put(ANONYMOUS_KEY, isAnonymous);
	}
	
	public int getCommentCount() {
		return this.getInt(COMMENTCOUNT_KEY);
	}
	
	public void setCommentCount(int count) {
		this.put(COMMENTCOUNT_KEY, count);
	}
	
	public AVUser getUser() {
		
		return this.getAVUser(USER_KEY);
	}
	
	public String getCreatTime() {
		return DateFormat.format("yyyy-MM-dd HH:mm",this.getCreatedAt()).toString();
	}
}