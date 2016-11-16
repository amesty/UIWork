package com.example.fragment;


import com.example.fragment.my.login.LoginFragment;
import com.example.uiwork.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.utils.UiHelper;

public class UserCenterFragment extends BasicFragment {
	
	private TextView reg_log;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_usercenter);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("个人中心");
		
		reg_log = (TextView) v.findViewById(R.id.reg_log);
		reg_log.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(UserCenterFragment.this, null, new LoginFragment());
			}
		});
		
    }
}
