package com.example.model;

import java.io.Serializable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName(TestScore.TESTSCORE_CLASS)
public class TestScore  extends AVObject implements Serializable{

static final String TESTSCORE_CLASS = "test_score";
	
	public static final String STATE = "state";
	public static final String RESULT = "result";

	public String getTestState(){
		return this.getString(STATE);
	}
	
	public String getTestResult(){
		return this.getString(RESULT);		
	}
}
