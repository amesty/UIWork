package com.example.fragment;



import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.BackHandledFragment;
import com.example.MainApplication;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

/**
 * Fragment继承此类可以防止点击穿透事件，重写了onStart()方法，即所有的Fragment都会继承该方法，防止点击穿透
 * @author CXW-HP
 *
 */
public class BasicFragment extends BackHandledFragment implements OnTouchListener{
    public String TAG = "BasicFragment";
	public static MainApplication mApp;

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			System.out.println(this.getClass().getName() + "点击了: " + v.getClass().getName());
			UiHelper.shutKeyboardIfNeed(getActivity());
		}
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getName();
		TAG = TAG.substring(TAG.lastIndexOf(".") + 1);
        Log.d(TAG, "onCreate");
        System.out.println(TAG + "-->onCreate");
        
        mApp = MainApplication.INSTANCE;
        //mApp.registerEvent(this);
	}

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        System.out.println(TAG + "-->onDestroy");
        //mApp.unregisterEvent(this);
        super.onDestroy();
    }

	/**
	 * 视图创建完成后，
	 * 1、设置监听触摸，防止点击穿透
	 * 2、如果有返回按钮，设置返回按钮动作响应
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.setOnTouchListener(this);
		setBackButtonOnClick(view);
	}

    /**
     * 设置返回按钮点击事件
     */
    public void setBackButtonOnClick(View view){
    	View backButton = view.findViewById(R.id.btn_back);
    	if(backButton != null){
	    	backButton.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	goBackStack();
	            }
	        });
    	}
    }
    
    /**
     * 返回上一个界面
     */
    public void goBackStack(){
    	//隐藏输入法界面
    	UiHelper.shutKeyboardIfNeed(getActivity());
    	getActivity().getSupportFragmentManager().popBackStack();
    }

	@Override
	protected boolean onBackPressed() {
		return false;
	}
	  
}
