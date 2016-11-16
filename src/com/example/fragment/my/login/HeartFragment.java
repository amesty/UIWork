package com.example.fragment.my.login;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.example.fragment.BasicFragment;
import com.example.uiwork.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class HeartFragment extends BasicFragment {

	public HeartFragment fragment;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment = this;
		userId = null;
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
		}
	}

	public String getUserId() {
		return userId;
	}

	@SuppressWarnings("unused")
	protected void showError(String errorMessage) {
		Dialog alertDialog = new AlertDialog.Builder(getActivity())
				.setTitle(
						fragment.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(errorMessage)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	public void onPause() {
		super.onPause();
		AVAnalytics.onFragmentEnd("heart-fragment");
	}

	public void onResume() {
		super.onResume();
		AVAnalytics.onFragmentStart("heart-fragment");
	}
}
