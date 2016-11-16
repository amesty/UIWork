package com.example.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import android.text.format.DateFormat;

@AVClassName(Answer.ANSWER_CLASS)
public class Answer extends AVObject{
	
	static final String ANSWER_CLASS = "Answer";
	
	private static final String CONTENT_KEY = "answer_content";
	private static final String GOODCOUNT_KET = "answer_igood";
	private static final String USER_KEY = "user_id";
	private static final String PROBLEM_KEY = "problem_id";
	private static final String INDEX_KEY = "answer_index";
	
	public String getCreatTime() {
		return DateFormat.format("yyyy-MM-dd HH:mm",this.getCreatedAt()).toString();
	}
	
	public String getContent() {
		return this.getString(CONTENT_KEY);
	}
	
	public void setContent(String content) {
		this.put(CONTENT_KEY, content);
	}
	
	public int getGoodCount() {
		return this.getInt(GOODCOUNT_KET);
	}
	
	public void setGoodCount(int count) {
		this.put(GOODCOUNT_KET, count);
	}
	
	public void setUser(AVUser user) {
		this.put(USER_KEY, user);
	}
	
	public AVUser getUser() {
		return this.getAVUser(USER_KEY);
	}

	public Problem getProblem() {
		try {
			return this.getAVObject(PROBLEM_KEY, Problem.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void setProblem(Problem cc) {
		this.put(PROBLEM_KEY, cc);
	}
	
	public int getIndex() {
		return this.getInt(INDEX_KEY);
	}
	
	public void setIndex(int index) {
		this.put(INDEX_KEY, index);
	}
}
