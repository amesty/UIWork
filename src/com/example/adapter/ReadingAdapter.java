package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.adapter.DingdanAdapter.ViewHolder;
import com.example.model.Appointment;
import com.example.model.Article;
import com.example.model.Counselor;
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

public class ReadingAdapter extends BaseAdapter{

	private Context context;
	private List<Article> articleClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public ReadingAdapter(Context context, List<Article> articleClass) {
		this.setContext(context);
		this.articleClass = articleClass;
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
		return articleClass != null ? articleClass.size() : 0;
	}
	@Override
	public Object getItem(int position) {
		if (articleClass != null)
		      return articleClass.get(position);
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
		Article article = articleClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_reading_lv, null);	
			holder.articleImage = (ImageView) convertView.findViewById(R.id.articleImage);
			holder.articleTitle = (TextView) convertView.findViewById(R.id.title);
			holder.commentCount = (TextView) convertView.findViewById(R.id.commentcount);
			holder.lookCount = (TextView) convertView.findViewById(R.id.lookcount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}		
			Picasso.with(getContext()).load(article.getArticlePhotoUrl()).into(holder.articleImage);
			holder.articleTitle.setText(article.getTitle());  
			holder.commentCount.setText(article.getCommentCount()+"");
			holder.lookCount.setText(article.getLookCount()+"");
		
		return convertView;
	}
		
	class ViewHolder {
		ImageView articleImage;
		TextView articleTitle;
		TextView commentCount;
		TextView lookCount;
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
