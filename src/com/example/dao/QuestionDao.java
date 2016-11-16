package com.example.dao;

import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.model.Problem;
import android.text.TextUtils;
import android.util.Log;

public class QuestionDao {
	
	public void addOneQuestion(AVUser currentUser, String content, String type, boolean isAnonymous) {
		
		final AVObject object = new AVObject("Problem");
		object.put("user_id", currentUser);
		object.put("problem_content", content);
		object.put("problem_type", type);
		object.put("isAnonymous", isAnonymous);
		
		object.saveInBackground(new SaveCallback(){
			@Override
			public void done(AVException e) {
				if (e==null) {
					Log.d("AddQuestion", object.getObjectId());
				}else {
					// 失败的话，请检查网络环境以及 SDK 配置是否正确
					System.out.println("fail加问题");
				}
			}
		});
	}
	
	public static void fetchQuestionById(String objectId,GetCallback<AVObject> getCallback) {
		Problem problem = new Problem();
		problem.setObjectId(objectId);
		problem.fetchInBackground(getCallback);
	}
	
	public static void createOrUpdateTodo(String objectId, String content, SaveCallback saveCallback) {
	    final Problem problem = new Problem();
	    if (!TextUtils.isEmpty(objectId)) {
	    	problem.setObjectId(objectId);
	    }
	    problem.setContent(content);

	    problem.saveInBackground(saveCallback);
	}
	
	public static List<Problem> findQuestionsByTime() {

	    AVQuery<Problem> query = AVQuery.getQuery(Problem.class);
	    query.orderByDescending("updatedAt");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query problems failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static List<Problem> findQuestionsByHot() {

	    AVQuery<Problem> query = AVQuery.getQuery(Problem.class);
	    query.orderByDescending("problem_icomment");

	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query problems failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public static void createOrUpdateQuestion(String objectId, AVUser user, String content, 
			String type, boolean isAnonymous, SaveCallback saveCallback) {
		
	    final Problem problem = new Problem();
	    if (!TextUtils.isEmpty(objectId)) {
	      // 如果存在objectId，保存会变成更新操作
	    	problem.setObjectId(objectId);
	    }
	    problem.setContent(content);
	    problem.setType(type);
	    problem.setAnonymous(isAnonymous);
	    // 异步保存
	    problem.saveInBackground(saveCallback);
	}
	
}
