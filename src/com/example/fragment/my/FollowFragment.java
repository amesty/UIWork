package com.example.fragment.my;

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

public class FollowFragment extends BasicFragment {
	
	private ViewPager mPageVp;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	private TextView  mTabGuanzhuYonghuTv, mTabGuanzhuCounselorTv;

	private ImageView mTabLineIv;

	private GuanzhuYonghuFragment mGuanzhuYonghuFg;;
	private GuanzhuCounselorFragment mGuanzhuCounselorFg;;

	private int currentIndex;
	private int screenWidth;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_follow);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("我的关注");
		
		initViews(v);
		initTabLineWidth();
    }
	
	private void initViews(View v) {
		mTabGuanzhuYonghuTv = (TextView) v.findViewById(R.id.id_guanzhu_yonghu_tv);;
		mTabGuanzhuCounselorTv = (TextView) v.findViewById(R.id.id_guanzhu_counselor_tv);
		mTabLineIv = (ImageView) v.findViewById(R.id.id_tab_line_iv_f);
		mPageVp = (ViewPager) v.findViewById(R.id.id_page_follow);
		
		
		mGuanzhuCounselorFg= new GuanzhuCounselorFragment();
		mGuanzhuYonghuFg = new GuanzhuYonghuFragment();
		mFragmentList.add(mGuanzhuYonghuFg);
		mFragmentList.add(mGuanzhuCounselorFg);
		
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
					mTabGuanzhuYonghuTv.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 1:
					mTabGuanzhuCounselorTv.setTextColor(getResources().getColor(R.color.blue));
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
		mTabGuanzhuYonghuTv.setTextColor(Color.GRAY);
		mTabGuanzhuCounselorTv.setTextColor(Color.GRAY);
	}
}
