package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.example.UIhelper.LogUtils;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.model.Appointment;
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

public class DingdanAdapter extends BaseAdapter{
	private Context context;
	private List<Appointment> appointmentClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public DingdanAdapter(Context context, List<Appointment> appointmentClass) {
		this.setContext(context);
		this.appointmentClass = appointmentClass;
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
		return appointmentClass != null ? appointmentClass.size() : 0;
	}
	@Override
	public Object getItem(int position) {
		if (appointmentClass != null)
		      return appointmentClass.get(position);
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
		Appointment ap = appointmentClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_appointment, null);
			holder.appointIv = (ImageView) convertView.findViewById(R.id.appoint_counselor_tx);
			holder.appointNameTv = (TextView) convertView.findViewById(R.id.appoint_counselor_name);
			holder.appintCousultWay = (TextView) convertView.findViewById(R.id.appoint_cousult_way);
			holder.appointTimeTv = (TextView) convertView.findViewById(R.id.appoint_time);
			convertView.setTag(holder);
		} else {
			//holder = (ViewHolder) convertView.getTag();
		}
		
		if (ap != null) {
 		
			ap.fetchInBackground("counselor_id", new GetCallback<AVObject>(){
		          
					@Override
					public void done(AVObject avobject, AVException e) {
						Counselor counselor = avobject.getAVObject("counselor_id");
						System.out.println("counselordingdan:"+counselor);
						holder.appointNameTv.setText(counselor.getName());
						Picasso.with(getContext()).load(counselor.getCounselorUrl()).into(holder.appointIv);
					}
		       });
			holder.appintCousultWay.setText(ap.getConsultWay());  
			holder.appointTimeTv.setText(ap.getCreatTime());;
		}
		return convertView;
	}
		
	class ViewHolder {
		ImageView appointIv;
		TextView appointNameTv;
		TextView appintCousultWay;
		TextView appointTimeTv;
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
