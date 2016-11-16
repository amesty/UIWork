package com.example.HelpClass;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.example.UIhelper.BackgroundTask;
import com.example.model.Reply;
import com.example.model.TestReply;

public class Reply_ReplyTo {
	
	public final BlockingQueue<Object> result = new ArrayBlockingQueue<Object>(1);
	
	public void getReplyTo(final Reply reply) {
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
	    		String reply_to = reply.getReplyTo();
	    		result.put(reply_to);
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
	}
	
	public void getTestReplyTo(final TestReply reply) {
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
	    		String reply_to = reply.getReplyTo();
	    		result.put(reply_to);
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
	}
}
