package com.example.fragment.wenda;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.FragmentAdapter;
import com.example.fragment.BasicFragment;
import com.example.uiwork.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.utils.UiHelper;

public class QuestionFragment extends BasicFragment {
	
	private ViewPager mPageVp;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	private TextView mTabHotTv, mTabNewTv;

	private ImageView mTabLineIv;

	private Q_hotFragment mHotFg;
	public Q_newFragment mNewFg;

	private int currentIndex;
	private int screenWidth;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_w_question);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		initViews(v);
		initTabLineWidth();
    }
	
	private void initViews(View v) {
		mTabHotTv = (TextView) v.findViewById(R.id.id_hot_tv);
		mTabNewTv = (TextView) v.findViewById(R.id.id_new_tv);
		mTabLineIv = (ImageView) v.findViewById(R.id.id_tab_line_iv);
		mPageVp = (ViewPager) v.findViewById(R.id.id_page_vp);
		
		mHotFg = new Q_hotFragment();
		mNewFg = new Q_newFragment();
		mFragmentList.add(mNewFg);
		mFragmentList.add(mHotFg);
		
		mFragmentAdapter = new FragmentAdapter(
				getActivity().getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {

			}

			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
						.getLayoutParams();

				Log.e("offset:", offset + "");
		

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				}
				mTabLineIv.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					mTabHotTv.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 1:
					mTabNewTv.setTextColor(getResources().getColor(R.color.blue));
					break;
				}
				currentIndex = position;
			}
		});
	}
	
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getActivity().getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / 2;
		mTabLineIv.setLayoutParams(lp);
	}

	private void resetTextView() {
		mTabHotTv.setTextColor(Color.GRAY);
		mTabNewTv.setTextColor(Color.GRAY);
	}
}

