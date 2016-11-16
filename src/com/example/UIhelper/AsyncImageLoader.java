package com.example.UIhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncImageLoader extends AsyncTask<byte[], Void, Bitmap> {

	private Context mContext;
	private ImageView image;
	private int mIndex;
	private OnLoadFinishListener mListener;

	/**
	 * 构造方法，需要把ImageView控件和LruCache 对象传进来
	 * 
	 * @param image
	 *            加载图片到此 {@code}TextView
	 * @param lruCache
	 *            缓存图片的对象
	 */
	public AsyncImageLoader(Context context, ImageView image, int index,
			OnLoadFinishListener listener) {
		mContext = context;
		this.image = image;
		mIndex = index;
		mListener = listener;
	}

	@Override
	protected Bitmap doInBackground(byte[]... params) {
		// TODO Auto-generated method stub

		return getBitmap(params[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		int inx = (Integer) image.getTag();
		if (mIndex == inx) {
			image.setImageBitmap(bitmap);
			if (mListener != null) {
				mListener.onLoadFinish(bitmap, mIndex);
			}
		}
	}

	// 解析图片
	private Bitmap getBitmap(byte[] icon) {
		return BitmapFactory.decodeByteArray(icon, 0, icon.length);
	}

	public interface OnLoadFinishListener {
		public void onLoadFinish(Bitmap bitmap, int index);
	}
}
