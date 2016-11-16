package com.example.fragment;

import com.example.uiwork.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utils.UiHelper;

public class FMFragment extends BasicFragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_fm);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		//initViews();
    }
}
