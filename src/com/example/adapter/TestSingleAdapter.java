package com.example.adapter;

import java.util.List;

import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.adapter.ReadingAdapter.ViewHolder;
import com.example.model.Test;
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

public class TestSingleAdapter extends BaseAdapter{


	private Context context;
	private List<Test> testClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public TestSingleAdapter(Context context, List<Test> testClass) {
		this.setContext(context);
		this.testClass = testClass;
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
		return testClass != null ? testClass.size() : 0;
	}
	@Override
	public Object getItem(int position) {
		if (testClass != null)
		      return testClass.get(position);
		    else
		      return null;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = new ViewHolder();
		Test test = testClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_test, null);	
			holder.testImage = (ImageView) convertView.findViewById(R.id.item_test_photo);
			holder.testTitle = (TextView) convertView.findViewById(R.id.item_test_title);
			holder.testCommentCount = (TextView) convertView.findViewById(R.id.item_test_commentcount);
			holder.testLookCount = (TextView) convertView.findViewById(R.id.item_test_lookcount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}		
			Picasso.with(getContext()).load(test.getTestPhotoUrl()).into(holder.testImage);
			holder.testTitle.setText(test.getTestTitle());  
			holder.testCommentCount.setText(test.getCommentNum()+"");
			holder.testLookCount.setText(test.getGoodNum()+"");
		
		return convertView;
	}
		
	class ViewHolder {
		ImageView testImage;
		TextView testTitle;
		TextView testCommentCount;
		TextView testLookCount;
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}
	
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

	class LoadFinish implements OnLoadFinishListener {

		@Override
		public void onLoadFinish(Bitmap bitmap, int index) {

			LogUtils.i("onLoadFinish-----index = " + index);
			addBitmapToMemoryCache(String.valueOf(index), bitmap);
		}
	}



}
