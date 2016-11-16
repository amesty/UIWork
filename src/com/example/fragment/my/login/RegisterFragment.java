package com.example.fragment.my.login;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterFragment extends HeartFragment {

	Button registerButton;
	EditText userName;
	EditText userEmail;
	EditText userPassword;
	EditText userPasswordAgain;
	private ProgressDialog progressDialog;

	@SuppressLint("NewApi")
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_register);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		registerButton = (Button) v.findViewById(R.id.need_register_button);
		userName = (EditText) v.findViewById(R.id.editText_register_userName);
		userEmail = (EditText) v.findViewById(R.id.editText_register_email);
		userPassword = (EditText) v.findViewById(R.id.editText_register_userPassword);
		userPasswordAgain = (EditText) v.findViewById(R.id.editText_register_userPassword_again);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (userPassword.getText().toString()
						.equals(userPasswordAgain.getText().toString())) {
					if (!userPassword.getText().toString().isEmpty()) {
						if (!userName.getText().toString().isEmpty()) {
							if (!userEmail.getText().toString().isEmpty()) {
								progressDialogShow();
								register();
							} else {
								showError(getActivity()
										.getString(R.string.error_register_email_address_null));
							}
						} else {
							showError(getActivity()
									.getString(R.string.error_register_user_name_null));
						}
					} else {
						showError(getActivity()
								.getString(R.string.error_register_password_null));
					}
				} else {
					showError(getActivity()
							.getString(R.string.error_register_password_not_equals));
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent LoginIntent = new Intent(getActivity(), LoginFragment.class);
			startActivity(LoginIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void register() {
		AVUser user = new AVUser();
		user.setUsername(userName.getText().toString());
		user.setPassword(userPassword.getText().toString());
		user.setEmail(userEmail.getText().toString());
		user.signUpInBackground(new SignUpCallback() {
			public void done(AVException e) {
				progressDialogDismiss();
				if (e == null) {
					showRegisterSuccess();
					UiHelper.showFragment(RegisterFragment.this, null, new LoginFragment());
				} else {
					switch (e.getCode()) {
					case 202:
						showError(getActivity()
								.getString(R.string.error_register_user_name_repeat));
						break;
					case 203:
						showError(getActivity()
								.getString(R.string.error_register_email_repeat));
						break;
					default:
						showError(getActivity()
								.getString(R.string.network_error));
						break;
					}
				}
			}
		});
	}

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

	private void showRegisterSuccess() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getActivity().getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						getActivity().getResources().getString(
								R.string.success_register_success))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}

