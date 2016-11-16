package com.example.fragment.my.login;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.MainActivity;
import com.example.fragment.my.login.ForgetPasswordFragment;
import com.example.fragment.my.login.RegisterFragment;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends HeartFragment{
	Button loginButton;
	Button registerButton;
	Button forgetPasswordButton;
	EditText userNameEditText;
	EditText userPasswordEditText;
	private ProgressDialog progressDialog;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_login);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		AVAnalytics.trackAppOpened(getActivity().getIntent());

		//PushService.setDefaultPushCallback(this,LoginFragment.class);
		//PushService.subscribe(this, "public", LoginFragment.class);
		AVInstallation.getCurrentInstallation().saveInBackground();

		loginButton = (Button) v.findViewById(R.id.login_button);
		registerButton = (Button) v.findViewById(R.id.register_button);
		forgetPasswordButton = (Button) v.findViewById(R.id.forget_button);
		userNameEditText = (EditText) v.findViewById(R.id.username_edit);
		userPasswordEditText = (EditText) v.findViewById(R.id.password_edit);

		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
		forgetPasswordButton.setOnClickListener(forgetPasswordListener);
	}

	OnClickListener loginListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onClick(View arg0) {
			if (userNameEditText.getText().toString().isEmpty()) {
				showUserNameEmptyError();
				return;
			}
			if (userPasswordEditText.getText().toString().isEmpty()) {
				showUserPasswordEmptyError();
				return;
			}
			progressDialogShow();
			AVUser.logInInBackground(userNameEditText.getText().toString(),
					userPasswordEditText.getText().toString(),
					new LogInCallback() {
						public void done(AVUser user, AVException e) {
							if (user != null) {
								progressDialogDismiss();
								Intent mainIntent = new Intent(getActivity(),MainActivity.class);
								startActivity(mainIntent);
								getActivity().finish();
								
							} else {
								progressDialogDismiss();
								showLoginError();
							}
						}
					});
		}
	};

	OnClickListener forgetPasswordListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			UiHelper.showFragment(LoginFragment.this, null, new ForgetPasswordFragment());
		}
	};
	
	OnClickListener registerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			UiHelper.showFragment(LoginFragment.this,null,new RegisterFragment());
		}
	};

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(getActivity(),
						getActivity().getResources().getText(
								R.string.dialog_message_title),
								getActivity().getResources().getText(
								R.string.dialog_text_wait), true, false);
	}

	private void showLoginError() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getActivity().getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						getActivity().getResources().getString(
								R.string.error_login_error))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserPasswordEmptyError() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getActivity().getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						getActivity().getResources().getString(
								R.string.error_register_password_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserNameEmptyError() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getActivity().getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						getActivity().getResources().getString(
								R.string.error_register_user_name_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}
