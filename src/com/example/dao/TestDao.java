package com.example.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.example.model.Question;
import com.example.model.Test;
import com.example.model.TestAnswer;
import com.example.model.TestScore;

public class TestDao {
	
	public List<Test> findTestByType(String type) throws AVException {

		//AVUser currentUser = AVUser.getCurrentUser();
		//String userId = currentUser.getObjectId();
		//System.out.println("appointmentUser:"+currentUser);
		AVQuery<Test> query = AVQuery.getQuery(Test.class);
		query.whereEqualTo("test_type", type);
		
	    try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query Test failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	
	public List<Question> findQuestionByType(String key)throws AVException {
		
		AVQuery<Question> query = AVQuery.getQuery(Question.class);
		query.whereEqualTo("to_testKey", key);		
		
		 try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query Question failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	public List<TestAnswer> findAnswerByType(String key)throws AVException {
	
		AVQuery<TestAnswer> query = AVQuery.getQuery(TestAnswer.class);
		query.whereEqualTo("to_questionKey", key);		
		
		 try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query TestAnswer failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	public List<TestScore> getTestResultByGrade(char grade)throws AVException {

		AVQuery<TestScore> query = AVQuery.getQuery(TestScore.class);
		query.whereEqualTo("result", grade);		
		
		 try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query TestScore failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	
}
