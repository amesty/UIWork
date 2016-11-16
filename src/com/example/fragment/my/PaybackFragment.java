package com.example.fragment.my;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.fragment.my.login.HeartFragment;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PaybackFragment extends HeartFragment {
	Button submitButton;
	EditText submitEditText;
	ListView mUserResponseListView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_payback);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("意见反馈");
		
		
		submitButton = (Button) v.findViewById(R.id.button_about_app_submit_user_input);
		submitEditText = (EditText) v.findViewById(R.id.editText_about_app_user_input);
		mUserResponseListView = (ListView) v.findViewById(R.id.listView_user_back);
		submitButton.setOnClickListener(buttonListener);

		AVQuery<AVObject> query = new AVQuery<AVObject>("SuggestionByUser");
		query.whereEqualTo("UserObjectId", getUserId());
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Message msg = new Message();
					msg.what = 3;
					msg.obj = avObjects;
					mHandler.sendMessage(msg);
				} else {
					showError(getActivity().getString(R.string.network_error));
				}
			}
		});
	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AVObject doing = new AVObject("SuggestionByUser");
			doing.put("UserObjectId", getUserId());
			doing.put("UserSuggestion", submitEditText.getText().toString());
			doing.saveInBackground(new SaveCallback() {
				@Override
				public void done(AVException e) {
					if (e == null) {
						mHandler.obtainMessage(1).sendToTarget();
					} else {
						mHandler.obtainMessage(2).sendToTarget();
					}
				}
			});
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				new AlertDialog.Builder(getActivity())
						.setTitle(
								getActivity().getResources().getString(
										R.string.dialog_message_success))
						.setMessage(
								getActivity().getResources()
										.getString(
												R.string.action_about_app_submit_message_success))
						.setNegativeButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										submitEditText.setText("");
									}
								}).show();
				break;
			case 2:
				showError(getActivity()
						.getString(R.string.action_about_app_submit_message_error));
				break;
			default:
				break;
			}
		}
	};

	/*private void showResponseList(List<AVObject> responseList) {
		if (responseList != null && responseList.size() != 0) {
			UserResponseListAdapter adapter = new UserResponseListAdapter(
					responseList, getActivity());
			mUserResponseListView.setAdapter(adapter);
			mUserResponseListView.setVisibility(View.VISIBLE);
		}
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			getActivity().finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
