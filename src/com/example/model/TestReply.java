package com.example.model;

import java.io.Serializable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName(TestReply.TESTREPLY_CLASS)
public class TestReply extends AVObject implements Serializable{

static final String TESTREPLY_CLASS = "test_reply";
	
	public static final String CONTENT = "test_reply_content";
	public static final String ANSWER_ID = "answer_id";
	public static final String USER_ID = "user_id";
	public static final String REPLY_TO = "test_reply_to";
	
	public void setContent(String content) {
		this.put(CONTENT, content);
	}
	
	public void setReplyTo(String replyto) {
		this.put(REPLY_TO, replyto);
	}
	
	public void setCommentTest(CommentTest commenttest) {
		this.put(ANSWER_ID, commenttest);
	}
	
	public void setUser(AVUser user) {
		this.put(USER_ID, user);
	}
	
	public String getTestReplyContent(){
		return this.getString(CONTENT);
	}
	
	public String getTestReplyUserId(){
		return this.getString(USER_ID);		
	}
	public String getTestReplyId(){
		return this.getString(ANSWER_ID);		
	}
	public AVUser getUser() {
		return this.getAVUser(USER_ID);
	}
	public String getReplyTo() {
		return this.getString(REPLY_TO);
	}
}
