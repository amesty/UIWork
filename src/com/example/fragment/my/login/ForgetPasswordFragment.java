package com.example.fragment.my.login;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.MainActivity;
import com.example.uiwork.R;
import com.example.utils.UiHelper;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordFragment extends HeartFragment {

	EditText emailText;
	Button findPasswordButton;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_forget_password);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		emailText = (EditText) v.findViewById(R.id.editText_forget_password_email);
		findPasswordButton = (Button) v.findViewById(R.id.button_find_password);
		findPasswordButton.setOnClickListener(findPasswordListener);
	}

	OnClickListener findPasswordListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (emailText.getText().toString() != null) {
				AVUser.requestPasswordResetInBackground(emailText.getText()
						.toString(), new RequestPasswordResetCallback() {
					public void done(AVException e) {
						if (e == null) {
							Toast.makeText(getActivity(),
									R.string.forget_password_send_email,
									Toast.LENGTH_LONG).show();
							Intent mainIntent = new Intent(getActivity(),
									MainActivity.class);
							startActivity(mainIntent);
							getActivity().finish();
						} else {
							showError(getActivity()
									.getString(R.string.forget_password_email_error));
						}
					}
				});
			} else {
				showError(getActivity().getResources().getString(
						R.string.error_register_email_address_null));
			}
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent LoginIntent = new Intent(getActivity(), LoginFragment.class);
			startActivity(LoginIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
