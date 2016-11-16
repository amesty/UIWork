package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.example.UIhelper.AsyncImageLoader;
import com.example.UIhelper.BackgroundTask;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.model.Counselor;
import com.example.uiwork.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CounselorAdapter extends BaseAdapter {
	private Context context;
	private List<Counselor> counselorClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public CounselorAdapter(Context context, List<Counselor> counselorClass) {
		this.context = context;
		this.counselorClass = counselorClass;
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
		return counselorClass != null ? counselorClass.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		
		if (counselorClass != null)
		      return counselorClass.get(position);
		    else
		      return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();
		Counselor cc = counselorClass.get(position);
		System.out.println("咨询师："+cc);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_counselor, null);
			holder.counselorIv = (ImageView) convertView.findViewById(R.id.counselor_image);
			holder.counselorNameTv = (TextView) convertView.findViewById(R.id.counselor_name);
			holder.counselorCityTv = (TextView) convertView.findViewById(R.id.counselor_address);
			holder.counselorRankTv = (TextView) convertView.findViewById(R.id.counselor_rank);
			holder.counselorSpecialityTv = (TextView) convertView.findViewById(R.id.counselor_speciality);
			holder.counselorMottoTv = (TextView) convertView.findViewById(R.id.counselor_motto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.counselorIv.setTag(cc.getIndex());
		
		final Bitmap bitmap = getBitmapFromMemCache("" + cc.getIndex());
		if (bitmap == null) {
			LogUtils.i("getView-----bitmap == null");
			holder.counselorIv.setImageResource(R.drawable.usercenter);
			// 异步的加载图片 (线程池 + Handler ) ---> AsyncTask
			asyncloadImage(holder.counselorIv, cc);
		} else {
			LogUtils.i("getView-----bitmap != null");
			holder.counselorIv.setImageBitmap(bitmap);
		}
		
		if (cc != null) {
			holder.counselorNameTv.setText(cc.getName());
			holder.counselorCityTv.setText(cc.getCity());
			holder.counselorRankTv.setText(cc.getRank());
			holder.counselorSpecialityTv.setText(cc.getSpeciality());
			holder.counselorMottoTv.setText(cc.getMotto());
		}
		return convertView;
	}

	
	private void asyncloadImage(ImageView iv_header, final Counselor cc) {
		final AsyncImageLoader task = new AsyncImageLoader(context, iv_header,
				cc.getIndex(), new LoadFinish());
		
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
				AVFile file = cc.getPic();
	    		file.getDataInBackground(new GetDataCallback() {
	            	@Override
	    			public void done(byte[] bytes,AVException e) {
	            		byte[] bitmap = bytes;
	            		task.execute(bitmap);
	    			}
	    		});
			}
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();	
	}
	
	class ViewHolder {
		ImageView counselorIv;
		TextView counselorNameTv;
		TextView counselorCityTv;
		TextView counselorRankTv;
		TextView counselorSpecialityTv;
		TextView counselorMottoTv;
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