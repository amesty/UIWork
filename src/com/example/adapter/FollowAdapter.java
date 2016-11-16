package com.example.adapter;

import java.util.List;



import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.example.UIhelper.BackgroundTask;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.dao.UserDao;
import com.example.model.User;
import com.example.uiwork.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 关注适配器
 * @author 张静
 *
 */
public class FollowAdapter extends BaseAdapter{

	private Context context;
	private List<AVUser> guanzhuClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
     
    
     public FollowAdapter(Context context, List<AVUser> guanzhuClass) {  
    	this.setContext(context);
 		this.guanzhuClass = guanzhuClass;
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
 		return guanzhuClass != null ? guanzhuClass.size() : 0;
 	}
 	@Override
 	public Object getItem(int position) {
 		if (guanzhuClass != null)
 		      return guanzhuClass.get(position);
 		    else
 		      return null;
 	}
 	@Override
 	public long getItemId(int position) {
 		return position;
 	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder = new ViewHolder();
		final AVUser cuser = guanzhuClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_follow, null);
			holder.userIv = (ImageView) convertView.findViewById(R.id.follow_user_tx);
			holder.userNameTv = (TextView) convertView.findViewById(R.id.follow_username_tv);
			convertView.setTag(holder);
		} else {
			//holder = (ViewHolder) convertView.getTag();
		}

		if (cuser != null) {
			
			new BackgroundTask() {
				@Override
				protected void doInBack() throws Exception {
					try {
						String UserID = cuser.getObjectId();
						User follow = UserDao.getUserById(UserID);
						holder.userNameTv.setText(follow.getUsername());
						Picasso.with(getContext()).load(follow.getAvatarUrl()).into(holder.userIv);
						
					} catch (AVException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				protected void onPost(Exception e) {
					
				}
			}.execute();		
		}
		return convertView;
	}
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

	class ViewHolder {
		ImageView userIv;
		TextView userNameTv;
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
