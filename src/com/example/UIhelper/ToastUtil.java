package com.example.UIhelper;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ToastUtil {
	private static final String TAG = "ToastUtil";
	private static Handler handler = new Handler();

	private static Toast toast = null;

	private static Object synObj = new byte[0];

	public static void showMessage(final Context act, final String msg) {
		showMessage(act, msg, Toast.LENGTH_LONG);
	}

	public static void showMessage(final Context act, final String msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast == null) {
								toast = Toast.makeText(act, msg, len);
							}
							toast.setText(msg);
							// toast.setGravity(Gravity.CENTER, 0, 0);
							toast.setDuration(len);
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void cancelCurrentToast() {
		if (toast != null) {
			Log.i(TAG, "[ToastUtils]: toast is Hided");
			toast.cancel();
		} else {
			Log.i(TAG, "[ToastUtils]: toast is null");
		}
	}
}
