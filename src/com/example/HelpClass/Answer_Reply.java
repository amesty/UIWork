package com.example.HelpClass;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.example.UIhelper.BackgroundTask;
import com.example.dao.AnswerDao;
import com.example.model.Answer;
import com.example.model.CommentTest;
import com.example.model.Reply;
import com.example.model.TestReply;

public class Answer_Reply {
	
	public final BlockingQueue<Object> result = new ArrayBlockingQueue<Object>(1);
	public final BlockingQueue<Object> result1 = new ArrayBlockingQueue<Object>(1);
	
	
	public void getReplyList(final Answer answer) {
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
				AnswerDao dao = new AnswerDao();
				List<Reply> list = dao.findReplys(answer);
	    		result.put(list);
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
	}
	public void getCommentReplyList(final CommentTest commenttest) {
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
				AnswerDao dao = new AnswerDao();
				List<TestReply> list = dao.findTestReplys(commenttest);
	    		result1.put(list);
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
	}
}
