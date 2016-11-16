package com.example.model;

import java.io.Serializable;

import android.text.format.DateFormat;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName(CommentTest.COMMENTTEST_CLASS)
public class CommentTest extends AVObject implements Serializable{

	static final String COMMENTTEST_CLASS = "CommentTest";
	
	public static final String COMMENT_CONTENT = "comment_content";
	public static final String TO_USER = "to_user";
	public static final String REPLY_USER = "reply_user";
	public static final String TO_TEST = "to_test";
	public static final String GOOD_NUM = "good_num";
	public static final String COMMENT_NUM = "comment_num";
	
	public Test getTest() {
		try {
			return this.getAVObject(TO_TEST, Test.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void setTest(Test test) {
		this.put(TO_TEST, test);
	}

	public void setUser(AVUser user) {
		this.put(TO_USER, user);
	}
	
	public void setContent(String content) {
		this.put(COMMENT_CONTENT, content);
	}
	
	public void setGoodCount(int count) {
		this.put(GOOD_NUM, count);
	}
	
	public String getCommentContent(){
		return this.getString(COMMENT_CONTENT);
	}
	
	public String getCommentUserId(){
		return this.getString(TO_USER);		
	}
	public String getCommentReplyUserId(){
		return this.getString(REPLY_USER);
	}
	public String getCommentTestId(){
		return this.getString(TO_TEST);
	}
	
	public int getGoodNum(){
		return this.getInt(GOOD_NUM);
	}
	
	public int getCommentNum(){
		return this.getInt(COMMENT_NUM);
	}
	
	public String getCreatTime() {
		return DateFormat.format("yyyy-MM-dd HH:mm",this.getCreatedAt()).toString();
	}

}
