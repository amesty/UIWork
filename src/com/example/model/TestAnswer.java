package com.example.model;

import java.io.Serializable;

import org.json.JSONArray;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName(TestAnswer.TESTANSWER_CLASS)
public class TestAnswer extends AVObject implements Serializable{
	static final String TESTANSWER_CLASS = "test_answer";
	
	public static final String TESTANSWER_CONTENT = "answer_content";
	public static final String TESTANSWER_SCORE = "score";

	public String getAnswerContent(){
		return this.getString(TESTANSWER_CONTENT);
	}
	
	public int getScore(){
		return this.getInt(TESTANSWER_SCORE);		
	}
}
