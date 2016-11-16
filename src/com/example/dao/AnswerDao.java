package com.example.dao;

import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.model.Answer;
import com.example.model.CommentTest;
import com.example.model.Problem;
import com.example.model.Reply;
import com.example.model.TestReply;

import android.util.Log;

public class AnswerDao {

	public void addOneAnswer(AVUser currentUser, String content, Problem cc) {
		
		final Answer answer = new Answer();
		answer.setContent(content);
		answer.setProblem(cc);
		answer.setUser(currentUser);
		
		answer.saveInBackground(new SaveCallback(){
			@Override
			public void done(AVException e) {
				if (e==null) {
					Log.d("AddAnswer", answer.getObjectId());
				}else {
					// 失败的话，请检查网络环境以及 SDK 配置是否正确
					System.out.println("fail加回答");
				}
			}
		});
	}
	
	public List<Answer> findAnswers(Problem problem) {

	    AVQuery<Answer> query = AVQuery.getQuery(Answer.class);
	    query.whereEqualTo("problem_id", problem);
	    query.orderByDescending("answer_igood");
	    query.limit(1000);
	    try {
	      return query.find();
	    } catch (AVException exception) {
	      Log.e("tag", "Query problems failed.", exception);
	      return Collections.emptyList();
	    }
	}
	
	public List<Reply> findReplys(Answer answer) {
		
		AVQuery<Reply> query = AVQuery.getQuery(Reply.class);
		query.whereEqualTo(Reply.ANSWER, answer);
		query.orderByDescending("updatedAt");
	    query.limit(1000);
	    
	    try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query problems failed.", exception);
		      return Collections.emptyList();
		    }
	}
	
	public List<TestReply> findTestReplys(CommentTest commentTest) {
			
			AVQuery<TestReply> query = AVQuery.getQuery(TestReply.class);
			query.whereEqualTo(TestReply.ANSWER_ID,commentTest);
			query.orderByDescending("updatedAt");
		    query.limit(1000);
		    
		    try {
			      return query.find();
			    } catch (AVException exception) {
			      Log.e("tag", "Query TestReply failed.", exception);
			      return Collections.emptyList();
			    }
		}
	
	public void setReplyList(Answer answer,List<Reply> replyList) {
		for (Reply reply:replyList) {
			reply.setAnswer(answer);
		}
	}
	
	public void setReplyList(CommentTest comment,List<TestReply> replyList) {
		for (TestReply reply:replyList) {
			reply.setCommentTest(comment);
		}
	}
}
