package com.example.dao;

import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.example.model.CommentTest;

public class TestCommentDao {


	public List<CommentTest> findTestCommentByType(String key)throws AVException {
		
		AVQuery<CommentTest> query = AVQuery.getQuery(CommentTest.class);
		query.whereEqualTo("to_test", key);		
		System.out.print("\n这里的testid为空\n");
		 try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query CommentTest failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	
	
}
