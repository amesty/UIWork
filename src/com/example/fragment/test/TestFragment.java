package com.example.fragment.test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fragment.BasicFragment;
import com.example.fragment.my.FollowFragment;
import com.example.fragment.my.WodeFragment;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

public class TestFragment extends BasicFragment implements OnClickListener{

		
	//private ImageButton imgBtHuiyuan,imgBtLove,imgBtCaifu,imgBtQuwei,imgBtShejiao,imgBtXingge,imgBtZhichang,imgBtZhishang;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.aiqingceshi:
			UiHelper.showFragment(TestFragment.this, null, new FragmentLove());
			break;
		case R.id.zhuanyeceshi:
			UiHelper.showFragment(TestFragment.this, null, new FragmentHuiyuan());
			break;	
		case R.id.xinggeceshi:
			UiHelper.showFragment(TestFragment.this, null, new FragmentXingge());
			break;
		case R.id.start_match:
			UiHelper.showFragment(TestFragment.this, null, new MatchFragment());
			break;
			
		}
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initialView(view);
	}

	private void initialView(View v) {
		v.findViewById(R.id.start_match).setOnClickListener(this);
		
		v.findViewById(R.id.aiqingceshi).setOnClickListener(this);
		
		Button bt = (Button)v.findViewById(R.id.zhuanyeceshi);
		
		bt.setOnClickListener(this);
		
		v.findViewById(R.id.xinggeceshi).setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return  UiHelper.getLightThemeView(getActivity(), R.layout.fragment_test_main);
	}
	
	
	

}
