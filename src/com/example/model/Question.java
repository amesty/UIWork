package com.example.model;

import java.io.Serializable;

import org.json.JSONArray;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName(Question.QUESTION_CLASS)
public class Question extends AVObject implements Serializable{
	static final String QUESTION_CLASS = "test_question";
	
	public static final String QUESTION_CONTENT = "question_content";
	public static final String QUESTION_ID = "objectId";
	public static final String QUESTION_NUM = "answer_num";
	public static final String TEST_QUESTION_KEY = 	"test_question_key";
	
	public String getQuestionKey(){
		return this.getString(TEST_QUESTION_KEY);
	}

	public String getQuestionContent(){
		return this.getString(QUESTION_CONTENT);
	}
	
	
	public String getQuestionId(){
		return this.getObjectId();
	}
	
	public int getAnswerNum(){
		return this.getInt(QUESTION_NUM);
	}
	
}
