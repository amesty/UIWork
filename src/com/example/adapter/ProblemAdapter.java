package com.example.adapter;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.example.HelpClass.Problem_Username;
import com.example.UIhelper.AsyncImageLoader;
import com.example.UIhelper.AsyncImageLoader.OnLoadFinishListener;
import com.example.UIhelper.LogUtils;
import com.example.model.Problem;
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

public class ProblemAdapter extends BaseAdapter {
	private Context context;
	private List<Problem> problemClass;
	private LayoutInflater mInflater;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public ProblemAdapter(Context context, List<Problem> problemClass) {
		this.context = context;
		this.problemClass = problemClass;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		// LruCache閫氳繃鏋勯€犲嚱鏁颁紶鍏ョ紦瀛樺€硷紝浠B涓哄崟浣嶃€?
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 浣跨敤鏈€澶у彲鐢ㄥ唴瀛樺€肩殑1/8浣滀负缂撳瓨鐨勫ぇ灏忋€?
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 閲嶅啓姝ゆ柟娉曟潵琛￠噺姣忓紶鍥剧墖鐨勫ぇ灏忥紝榛樿杩斿洖鍥剧墖鏁伴噺銆?
				return bitmap.getByteCount() / 1024;
			}
		};	
	}
	
	@Override
	public int getCount() {
		return problemClass != null ? problemClass.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		
		if (problemClass != null)
		      return problemClass.get(position);
		    else
		      return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void clearList() {
		this.problemClass.clear();
	}

	public void updateList(List<Problem> problemList) {
		this.problemClass.addAll(problemList);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1鑾峰彇item,鍐嶅緱鍒版帶浠?
		// 2 鑾峰彇鏁版嵁
		// 3缁戝畾鏁版嵁鍒癷tem
		ViewHolder holder = new ViewHolder();
		Problem cc = problemClass.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_question, null);
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
		
		if (cc.getAnonymous()==true) {
			holder.userIv.setImageResource(R.drawable.usercenter);
		} else {
			
			final Bitmap bitmap = getBitmapFromMemCache("" + cc.getIndex());
			if (bitmap == null) {
				LogUtils.i("getView-----bitmap == null");
				holder.userIv.setImageResource(R.drawable.usercenter);
				// 寮傛鐨勫姞杞藉浘鐗?(绾跨▼姹?+ Handler ) ---> AsyncTask
				asyncloadImage(holder.userIv, cc);
			} else {
				LogUtils.i("getView-----bitmap != null");
				holder.userIv.setImageBitmap(bitmap);
			}
			
		}
		
		if (cc != null) {
	    	if (cc.getAnonymous()==true) {
	    		holder.usernameTv.setText("匿名用户");
	    	} else {
	    		Problem_Username pu = new Problem_Username();
	    		pu.getUsername(cc);
	    		try {
					holder.usernameTv.setText(pu.result.take().toString());
				} catch (InterruptedException e) {
					 //TODO Auto-generated catch block
					e.printStackTrace();
	    		holder.usernameTv.setText("afa");
				}
	    	}
	
			holder.contentTv.setText(cc.getContent());
			holder.timeTv.setText(cc.getCreatTime());
			holder.questionTypeTv.setText(cc.getType());
			holder.answerCountTv.setText("" + cc.getCommentCount());
		}
		return convertView;
	}

	
	private void asyncloadImage(ImageView iv_header, Problem cc) {
		final AsyncImageLoader task = new AsyncImageLoader(context, iv_header,
				cc.getIndex(), new LoadFinish());
		
	    AVUser user = cc.getUser();
	    
	    user.fetchInBackground("userinfo", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
               
                AVObject f = avObject.getAVObject("userinfo");
                AVFile file = f.getAVFile("userPhoto");
                file.getDataInBackground(new GetDataCallback() {
	            	@Override
	    			public void done(byte[] bytes,AVException e) {
	            		byte[] bitmap = bytes;
	            		task.execute(bitmap);
	    			}
	    		});
            }
        });
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
}

