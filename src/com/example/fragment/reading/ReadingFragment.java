package com.example.fragment.reading;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.adapter.FragmentAdapter;
import com.example.fragment.BasicFragment;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

public class ReadingFragment extends BasicFragment implements OnClickListener{

	private ViewPager mVPager;
	private List<Fragment> fragmentset;
	private ImageView coverPic;
	//屏幕宽度  
    int screenWidth;  
    //当前选中的项  
    int currenttab = 0;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_reading_main);
        
	}
	
	
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(v, savedInstanceState);
		initialView(v);		
	}
	
	public void initialView(View v){
		
		//获取导航栏的按钮
		Button bt_family = (Button)v.findViewById(R.id.bt_family_topic);
		v.findViewById(R.id.bt_all_topic).setOnClickListener(this);		
		bt_family.setOnClickListener(this);
		v.findViewById(R.id.bt_health_topic).setOnClickListener(this);
		v.findViewById(R.id.bt_job_topic).setOnClickListener(this);
		v.findViewById(R.id.bt_marrige_topic).setOnClickListener(this);
				
		//获取要加入viewpage当中的fragment的实例
		mVPager = (ViewPager)v.findViewById(R.id.viewpager);	
		fragmentset = new ArrayList<Fragment>();
		
		fragmentset.add(new AllTopicFragment());
		fragmentset.add(new FamilyTopicFragment());
		fragmentset.add(new HealthTopicFragment());
		fragmentset.add(new JobTopicFragment());
		fragmentset.add(new MarriageTopicFragment());
		
		//获取屏幕宽度，为导航栏的动态标记做准备
		screenWidth=getResources().getDisplayMetrics().widthPixels;
		
		bt_family.measure(0, 0);
		coverPic=(ImageView)v.findViewById(R.id.cover_image);  
		
		//动态布局，代码中改变xml文件的布局方式，两个参数一个是目标控件的要设置的宽度，一个是高度
        RelativeLayout.LayoutParams imageParams=new RelativeLayout.LayoutParams(screenWidth/5, 3);  
        imageParams.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.read_guide);  
        
        //对用于标记当前位置的图片空间施加上面的布局
        coverPic.setLayoutParams(imageParams);  
        
        FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentset);
        mVPager.setAdapter(adapter); 
       
        mVPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				changeView(arg0);
				currenttab = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
				
			}
		});
        
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())  
        {  
        case R.id.bt_all_topic:  
            changeView(0);  
            break;  
        case R.id.bt_family_topic:  
            changeView(1);  
            break;  
        case R.id.bt_health_topic:  
            changeView(2);  
            break;  
        case R.id.bt_job_topic:  
            changeView(3);  
            break;  
        case R.id.bt_marrige_topic:
        	changeView(4);
        default:  
            break;  
        }  
	}

	private void imageMove(int moveToTab)  
    {  
        int startPosition=0;  
        int movetoPosition=0;  
        
        startPosition=currenttab*(screenWidth/5);  
        movetoPosition=moveToTab*(screenWidth/5);  
        //平移动画  
        TranslateAnimation translateAnimation=new TranslateAnimation(startPosition,movetoPosition, 0, 0);  
        translateAnimation.setFillAfter(true);  
        translateAnimation.setDuration(200);  
        coverPic.startAnimation(translateAnimation);  
    }  
	
	private void changeView(int desTab){  
	     mVPager.setCurrentItem(desTab, true); 
	     //currenttab = mVPager.getCurrentItem();
	     imageMove(desTab);
	  }
	

	
	
	
	
	
}
