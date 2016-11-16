package com.example.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName(Reply.REPLY_CLASS)
public class Reply extends AVObject{

	static final String REPLY_CLASS = "Reply";
	
	public static final String CONTENT = "reply_content";
	public static final String USER = "user_id";
	public static final String ANSWER = "answer_id";
	public static final String REPLY_TO = "reply_to";
	
	public String getContent() {
		return this.getString(CONTENT);
	}
	
	public void setContent(String content) {
		this.put(CONTENT, content);
	}
	
	public Answer getAnswer() {
		try {
			return this.getAVObject(ANSWER, Answer.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void setAnswer(Answer answer) {
		this.put(ANSWER, answer);
	}
	
	public AVUser getUser() {
		return this.getAVUser(USER);
	}
	
	public void setUser(AVUser user) {
		this.put(USER, user);
	}
	
	public String getReplyTo() {
		return this.getString(REPLY_TO);
	}
	
	public void setReplyTo(String replyto) {
		this.put(REPLY_TO, replyto);
	}
}
