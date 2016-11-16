package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.example.HelpClass.Answer_Reply;
import com.example.HelpClass.Answer_Username;
import com.example.HelpClass.MyListView;
import com.example.UIhelper.AsyncImageLoader;
import com.example.UIhelper.BackgroundTask;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.dao.UserDao;
import com.example.model.Answer;
import com.example.model.CommentTest;
import com.example.model.Reply;
import com.example.model.TestReply;
import com.example.model.User;
import com.example.uiwork.R;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TestCommentAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private OnClickListener goodToCommentListener;
	private OnClickListener replyToCommentListener;
	private OnClickListener replyToReplyListener;
	private List<CommentTest> commentTestList;
	private LruCache<String, Bitmap> mMemoryCache;
	private User user;
	private CommentTest commenttest;

	public TestCommentAdapter(Context context, List<CommentTest> commentList,
			OnClickListener replyToCommentListener,CommentReplyAdapter myAdapter, 
			OnClickListener goodToCommentListener, OnClickListener replyToReplyListener) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.commentTestList = new ArrayList<CommentTest>();
		this.commentTestList.addAll(commentList);
		this.replyToCommentListener = replyToCommentListener;
		this.goodToCommentListener = goodToCommentListener;
		this.replyToReplyListener = replyToReplyListener;
		
		// LruCache通过构造函数传入缓存值，以KB为单位。
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 使用最大可用内存值的1/8作为缓存的大小。
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量。
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	public void clearList() {
		this.commentTestList.clear();
	}

	public void updateList(List<CommentTest> commentList) {
		this.commentTestList.addAll(commentList);
	}

	@Override
	public int getCount() {
		return commentTestList.size();
	}

	@Override
	public CommentTest getItem(int position) {
		return commentTestList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		commenttest = getItem(position);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_comment, null);
			viewHolder.iv_user_photo = (ImageView) convertView
					.findViewById(R.id.iv_user_photo);
			viewHolder.tv_user_name = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.time_tv);
			viewHolder.iv_good = (ImageView) convertView
					.findViewById(R.id.good_iv);
			viewHolder.iv_good.setTag(position);
			viewHolder.tv_goodcount = (TextView) convertView
					.findViewById(R.id.goodcount_tv);
			viewHolder.tv_user_comment = (TextView) convertView
					.findViewById(R.id.content_tv);
			viewHolder.btn_comment_reply = (TextView) convertView
					.findViewById(R.id.tv_user_reply);
			viewHolder.lv_user_comment_replys = (MyListView) convertView
					.findViewById(R.id.lv_user_comment_replys);
			viewHolder.btn_comment_reply.setTag(position);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		new BackgroundTask() {
					
					@Override
					protected void onPost(Exception e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					protected void doInBack() throws Exception {
						// TODO Auto-generated method stub
						user = UserDao.getUserById(commenttest.getCommentUserId());
					}
				};
		
		Picasso.with(context).load(user.getUserPhotoUrl()).into(viewHolder.iv_user_photo);
		viewHolder.tv_goodcount.setText("" + commenttest.getGoodNum());
		viewHolder.tv_time.setText(commenttest.getCreatTime());
		viewHolder.tv_user_comment.setText(commenttest.getCommentContent());
		// 设置评论列表的点击效果透明
		viewHolder.lv_user_comment_replys.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		
		//判断当前评论是否有回复
		Answer_Reply ar = new Answer_Reply();   
		ar.getCommentReplyList(commentTestList.get(position));
		List<TestReply> ReplyList;
		try {
			ReplyList = (List<TestReply>) ar.result.take();
			if (ReplyList != null && ReplyList.size() != 0) {
				System.out.println("sgsgf99999:" + ReplyList.size());
				viewHolder.lv_user_comment_replys
						.setAdapter(new TestCommentReplyAdapter(context, ReplyList, position,
								replyToReplyListener));
			}else {
				System.out.println("replylist null");
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		viewHolder.iv_good.setTag(position);
		viewHolder.iv_good.setOnClickListener(goodToCommentListener);
		//记录点击回复按钮时对应的position,用于确定所回复的对象
		viewHolder.btn_comment_reply.setTag(position);
		viewHolder.btn_comment_reply.setOnClickListener(replyToCommentListener);
		
		return convertView;
	}
	
	

	public class ViewHolder {
		private ImageView iv_user_photo; // 评论者 头像
		private TextView tv_user_name; // 评论者 昵称
		private TextView tv_time;
		private TextView tv_goodcount;
		private ImageView iv_good;
		private TextView tv_user_comment; // 评论者 一级品论内容
		private TextView btn_comment_reply; // 评论者 二级评论按钮
		private MyListView lv_user_comment_replys; // 评论者 二级品论内容列表
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	class LoadFinish implements OnLoadFinishListener {

		@Override
		public void onLoadFinish(Bitmap bitmap, int index) {

			LogUtils.i("onLoadFinish-----index = " + index);
			addBitmapToMemoryCache(String.valueOf(index), bitmap);
		}
	}
}

