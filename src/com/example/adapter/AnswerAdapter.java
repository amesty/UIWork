package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.GetDataCallback;
import com.example.UIhelper.AsyncImageLoader;
import com.example.UIhelper.BackgroundTask;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.adapter.ProblemAdapter.LoadFinish;
import com.example.adapter.ProblemAdapter.ViewHolder;
import com.example.dao.UserDao;
import com.example.model.Answer;
import com.example.model.Problem;
import com.example.model.User;
import com.example.uiwork.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerAdapter extends BaseAdapter{
	
	private ViewHolder holder = null;
	private byte[] bitmap;
	private Context context;
	private List<Answer> answerClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public AnswerAdapter(Context context, List<Answer> answerClass) {
		this.context = context;
		this.answerClass = answerClass;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
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
	@Override
	public int getCount() {
		return answerClass != null ? answerClass.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		
		if (answerClass != null)
		      return answerClass.get(position);
		    else
		      return null;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override 
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1获取item,再得到控件
		// 2 获取数据
		// 3绑定数据到item
		//ViewHolder holder = null;
		Answer cc = answerClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_question, null);
			holder = new ViewHolder();
			holder.userIv = (ImageView) convertView.findViewById(R.id.user_iv);
			holder.usernameTv = (TextView) convertView.findViewById(R.id.username_tv);
			holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
			holder.contentTv = (TextView) convertView.findViewById(R.id.content_tv);
			holder.questionTypeTv = (TextView) convertView.findViewById(R.id.question_type_tv);
			holder.answerCountTv = (TextView) convertView.findViewById(R.id.answer_count_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.userIv.setTag(cc.getIndex());
		
		final Bitmap bitmap = getBitmapFromMemCache("" + cc.getIndex());
		if (bitmap == null) {
			LogUtils.i("getView-----bitmap == null");
			holder.userIv.setImageResource(R.drawable.usercenter);
			// 异步的加载图片 (线程池 + Handler ) ---> AsyncTask
			//asyncloadImage(holder.userIv, cc);
		} else {
			LogUtils.i("getView-----bitmap != null");
			if (cc.getAnonymous()) {
				holder.userIv.setImageResource(R.drawable.usercenter);
			} else {
				holder.userIv.setImageBitmap(bitmap);
			}
		}
		
	    if (cc != null) {
	    	if (cc.getAnonymous()) {
	    		holder.usernameTv.setText("匿名用户");
	    	} else {
	    		user = cc.getUser();
				new BackgroundTask() {
					@Override
					protected void doInBack() throws Exception {
			    		temp = UserDao.getUserById(user.getObjectId());
			    		String username = temp.getUsername();
						holder.usernameTv.setText(username);
			    		System.out.println("username: " + username);
					}
					
					@Override
					protected void onPost(Exception e) {
						
					}
				}.execute();
	    	}
	
			holder.contentTv.setText(cc.getContent());
			holder.timeTv.setText(cc.getCreatTime());
			holder.questionTypeTv.setText(cc.getType());
			holder.answerCountTv.setText("" + cc.getCommentCount());
	    }
		return convertView;
	}

	private void asyncloadImage(ImageView iv_header, Problem cc) {
		AsyncImageLoader task = new AsyncImageLoader(context, iv_header,
				cc.getIndex(), new LoadFinish());
		
		user = cc.getUser();
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
	    		temp = UserDao.getUserById(user.getObjectId());
	    		file = temp.getAVFile(User.PHOTO_KEY);
	    		file.getDataInBackground(new GetDataCallback() {
	            	@Override
	    			public void done(byte[] bytes,AVException e) {
	    				bitmap = bytes;
	    			}
	    		});
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
		if (bitmap==null) {
			System.out.println("bitmap null!");
		}
		task.execute(bitmap);
	}

	class ViewHolder {
		ImageView userIv;
		TextView usernameTv;
		TextView timeTv;
		TextView contentTv;
		TextView questionTypeTv;
		TextView answerCountTv;
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
}*/
}
