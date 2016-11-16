package com.example.fragment.zixun;



import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.MainActivity;
import com.example.activity.AVBaseActivity;
import com.example.activity.AVSingleChatActivity;
import com.example.chat.Constants;
import com.example.fragment.BasicFragment;
import com.example.fragment.XinggeCeshiActivity;
import com.example.model.Counselor;
import com.example.uiwork.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.UiHelper;
import com.squareup.picasso.Picasso;

public class CounselorDetailFragment extends BasicFragment {
	
	private Counselor counselor;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_c_counselordeteail);
    }
	
	@SuppressLint("NewApi")
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("咨询师详情");
		
		Bundle bundle = getArguments();
		counselor = (Counselor) bundle.getSerializable("COUNSELOR");
		
		initView(v);
    }

	
	public void initView(View v) {
		
		ImageView counselor_tv = (ImageView) v.findViewById(R.id.counselor_pic);
		Picasso.with(getActivity()).load(counselor.getCounselorUrl()).into(counselor_tv);
		
		TextView counselor_name = (TextView) v.findViewById(R.id.tv_counselor_name);
		counselor_name.setText(counselor.getName());
		TextPaint counselor_name_paint = counselor_name.getPaint(); 
		counselor_name_paint.setFakeBoldText(true);
		
		TextView counselor_motto = (TextView) v.findViewById(R.id.tv_counselor_motto);
		counselor_motto.setText(counselor.getMotto());
		
		TextView counselor_rank_tab = (TextView) v.findViewById(R.id.tv_person_zizhi_tab);
		TextPaint counselor_rank_tab_paint = counselor_rank_tab.getPaint(); 
		counselor_rank_tab_paint.setFakeBoldText(true);
		TextView counselor_rank = (TextView) v.findViewById(R.id.counselor_rank);
		counselor_rank.setText(counselor.getRank());
		
		TextView counselor_intro_tab = (TextView) v.findViewById(R.id.tv_counselor_intro_tab);
		TextPaint counselor_intro_tab_paint = counselor_intro_tab.getPaint(); 
		counselor_intro_tab_paint.setFakeBoldText(true);
		TextView counselor_intro = (TextView) v.findViewById(R.id.couselor_intro);
		counselor_intro .setText(counselor.getIntroduction());
		
		TextView counselor_background_tab = (TextView) v.findViewById(R.id.tv_person_background_tab);
		TextPaint counselor_background_tab_paint = counselor_background_tab.getPaint(); 
		counselor_background_tab_paint.setFakeBoldText(true);
		TextView counselor_background = (TextView) v.findViewById(R.id.counselor_background);
		counselor_background.setText(counselor.getBackground());
		
		
		/**
		 * 添加关注与取消关注
		 */
		/*AVUser.getCurrentUser().followInBackground("57dd52fd128fe10064c8b28e", new FollowCallback() {
	        @Override
	        public void done(AVObject object, AVException e) {
	            if (e == null) {
	                Log.i(TAG, "关注神成功.");
	            } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
	                Log.w(TAG, "Already followed.");
	            }
	        }
	    });*/
		RelativeLayout Tianjiaguanzhu = (RelativeLayout) v.findViewById(R.id.btn_guanzhu_counselor);
		Tianjiaguanzhu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				AVUser currentUser = AVUser.getCurrentUser();
				
				final AVObject object = new AVObject("FollowCounselor");
				object.put("Counselor_id", counselor);
				object.put("user_id", currentUser);
				object.saveInBackground(new SaveCallback(){
					@Override
					public void done(AVException e) {
						if (e==null) {
							Toast.makeText(getActivity(), "关注成功！", Toast.LENGTH_SHORT).show();
							Log.d("Guanzhuzhuanjia", object.getObjectId());
							
							object.fetchInBackground("Counselor_id", new GetCallback<AVObject>(){

								@Override
								public void done(AVObject avobject, AVException arg1) {
									Counselor counselor = avobject.getAVObject("Counselor_id");
									System.out.println("counselor:"+counselor);
									//counselor.increaseHot(counselor);
									int newHotValue = counselor.getInt("c_hot")+1;
									System.out.println("newHotValue:"+newHotValue);
									counselor.setHotValue(newHotValue);
									counselor.saveInBackground();
								}
								
							});
						}else {
							// 失败的话，请检查网络环境以及 SDK 配置是否正确
							System.out.println("fail to guanzhu zhuanjia");
						}
					}
				});
			}
		});
		
		/**
		 * 私信专家
		 */
		RelativeLayout btn_sixin_counselor = (RelativeLayout) v.findViewById(R.id.btn_sixin_counselor);
		btn_sixin_counselor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String memberId = counselor.getName();
				System.out.println("MEMBER_ID:"+memberId);
				Intent intent = new Intent(getActivity(), AVSingleChatActivity.class);
				intent.putExtra(Constants.MEMBER_ID, memberId);
			    startActivity(intent);
			}
			
		});
		/**
		 * 预约专家
		 */
		RelativeLayout btn_yuyue_counselor = (RelativeLayout) v.findViewById(R.id.btn_yuyue_counselor);
		btn_yuyue_counselor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("COUNSELOR",counselor);
				UiHelper.showFragment(CounselorDetailFragment.this, bundle, new C_Yuyuezhuanjia());
			}
			
		});
	}
}
