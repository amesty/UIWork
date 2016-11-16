package com.example;


import com.example.uiwork.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;


public class BaseActivity extends FragmentActivity implements BackHandledInterface{
	public String TAG = "BaseActivity";
	public static MainApplication mApp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = MainApplication.INSTANCE;
        //mApp.registerEvent(this);
        TAG = this.getClass().getName();
    }

    
    
    @Override
	protected void onDestroy() {
    	//mApp.unregisterEvent(this);
		super.onDestroy();
	}

    
    
	@Override
	protected void onResume() {
		super.onResume();
	}



	@Override
	protected void onPause() {
		super.onPause();
	}



	/**
     * 返回，在XML中定义
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 设置返回按钮点击事件
     */
    public void setBackButtonOnClick(){
    	View backButton = (View)findViewById(R.id.btn_back);
    	if(backButton != null){
	    	backButton.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	finish();
	            }
	        });
    	}
    }


    
    private BackHandledFragment mBackHandedFragment; 
	@Override
	public void setSelectedFragment(BackHandledFragment selectedFragment) {
		this.mBackHandedFragment = selectedFragment; 
	}


	public boolean onBackPress() {
		if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {

			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

				final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.setContentView(R.layout.dialog_confirm);
				dialog.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

				dialog.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			} else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
				super.onBackPressed();
				System.out.println(">>>已经返回到首页了");
				return false;
			} else {
				getSupportFragmentManager().popBackStack();
				System.out.println(">>>退出Fragment界面");
				return true;
			}
		}
		return false;
	}

	
	
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
        		System.out.println(">>>点击了返回键");
        		return onBackPress();//这是自定义的代码                
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
