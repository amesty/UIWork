package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.model.Counselor;
import com.example.model.FollowCounselor;
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

public class FollowCounselorAdapter extends BaseAdapter{

	private Context context;
	private List<FollowCounselor> guanzhuClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
     
    
     public FollowCounselorAdapter(Context context, List<FollowCounselor> guanzhuClass) {  
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
		FollowCounselor fc = guanzhuClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_follow_counselor, null);
			holder.counselorIv = (ImageView) convertView.findViewById(R.id.follow_counselor_tx);
			holder.counselorTv = (TextView) convertView.findViewById(R.id.follow_counselor_tv);
			convertView.setTag(holder);
		} else {
			
		}

		if (fc != null) {
			
			fc.fetchInBackground("Counselor_id", new GetCallback<AVObject>(){
		          
				@Override
				public void done(AVObject avobject, AVException e) {
					Counselor counselor = avobject.getAVObject("Counselor_id");
					System.out.println("counselorguanzhu:"+counselor);
					holder.counselorTv.setText(counselor.getName());
					Picasso.with(getContext()).load(counselor.getCounselorUrl()).into(holder.counselorIv);
				}
	       });
			
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
		ImageView counselorIv;
		TextView counselorTv;
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
