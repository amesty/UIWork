package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.chat.AVImClientManager;
import com.example.uiwork.R;
import com.example.utils.UiHelper;
import com.example.chat.Constants;
import com.example.fragment.ChatFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;

/**
 * Created by wli on 15/8/14.
 * 一对一单聊的页面，需要传入 Constants.MEMBER_ID
 */
public class AVSingleChatActivity extends AVBaseActivity {

  protected ChatFragment chatFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);  
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_square);
    
    chatFragment = (ChatFragment)getFragmentManager().findFragmentById(R.id.fragment_chat);

    String memberId = getIntent().getStringExtra(Constants.MEMBER_ID);
    TextView title = (TextView)findViewById(R.id.title_text);
	title.setText(memberId);
    getConversation(memberId);
 
    ImageButton titleBack = (ImageButton) findViewById(R.id.btn_back);
	titleBack.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	});
  }
 
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Bundle extras = intent.getExtras();
    if (null != extras && extras.containsKey(Constants.MEMBER_ID)) {
      String memberId = extras.getString(Constants.MEMBER_ID);
      TextView title = (TextView)findViewById(R.id.title_text);
      title.setText(memberId);
      System.out.println("memberId:"+memberId);
      getConversation(memberId);
    }
  }

  /**
   * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
   * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
   */
  private void getConversation(final String memberId) {
	  AVUser user = AVUser.getCurrentUser();
	  String UserName = user.getUsername();
	  final AVIMClient client = AVIMClient.getInstance(UserName);
	 
	  AVImClientManager.getInstance().open(user.getUsername(), new AVIMClientCallback(){

		@Override
		public void done(AVIMClient avimClient, AVIMException e) 
		{
			if (filterException(e)) {
	        	AVIMConversationQuery conversationQuery = client.getQuery();
	            conversationQuery.withMembers(Arrays.asList(memberId), true);
	            conversationQuery.whereEqualTo("customConversationType",1);
	            conversationQuery.findInBackground(new AVIMConversationQueryCallback() 
	            {
	                @Override
	                public void done(List<AVIMConversation> list, AVIMException e) 
	                {
	                  if (filterException(e)) 
	                  {
	                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
	                    if (null != list && list.size() > 0) 
	                    {
	                      chatFragment.setConversation(list.get(0));
	                    } else 
	                    {
	                      HashMap<String,Object> attributes=new HashMap<String, Object>();
	                      attributes.put("customConversationType",1);
	                      client.createConversation(Arrays.asList(memberId),"jj", null, new AVIMConversationCreatedCallback() {
	                        @Override
	                        public void done(AVIMConversation avimConversation, AVIMException e) {
	                          chatFragment.setConversation(avimConversation);
	                        }
	                      });
	                    }
	                  }
	                }
	              });
	            }
	          }
	  });
  }
}
