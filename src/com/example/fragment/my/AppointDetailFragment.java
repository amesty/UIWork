package com.example.fragment.my;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.example.activity.AVSingleChatActivity;
import com.example.chat.Constants;
import com.example.fragment.BasicFragment;
import com.example.model.Appointment;
import com.example.model.Counselor;
import com.example.uiwork.R;
import com.example.utils.UiHelper;
import com.squareup.picasso.Picasso;

public class AppointDetailFragment extends BasicFragment{
	private Appointment appointment;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_m_appointdetail);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("预约详情");
		
		Bundle bundle = getArguments();
		appointment = (Appointment) bundle.getSerializable("APPOINTMENT");
		
		initView(v);
	}

	public void initView(View v) {
		
		final TextView counselor_name = (TextView) v.findViewById(R.id.appointdeatil_counselor_name);
		final ImageView counselor_tv = (ImageView) v.findViewById(R.id.appointdetail_counselor_tx);
		appointment.fetchInBackground("counselor_id", new GetCallback<AVObject>(){
	          
			@Override
			public void done(AVObject avobject, AVException e) {
				Counselor counselor = avobject.getAVObject("counselor_id");
				System.out.println("counselordingdan:"+counselor);
				counselor_name.setText(counselor.getName());
				Picasso.with(getActivity()).load(counselor.getCounselorUrl()).into(counselor_tv);
				
			}
       });
		
		TextView cousult_way = (TextView) v.findViewById(R.id.appointdetail_cousult_way);
		cousult_way.setText(appointment.getConsultWay());
		TextView counsult_time = (TextView) v.findViewById(R.id.appointdetail_time);
		counsult_time.setText(appointment.getCreatTime());
		TextView counsult_problem = (TextView) v.findViewById(R.id.appointdetail_description);
		counsult_problem.setText(appointment.getDescription());
		
		/**
		 * 取消预约
		 */
		RelativeLayout btn_quxiao_yuyue = (RelativeLayout) v.findViewById(R.id.btn_quxiao_yuyue);
		btn_quxiao_yuyue.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.setContentView(R.layout.dialog_confirm_delete_yuyue);
				dialog.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						appointment.deleteInBackground();
						UiHelper.showFragment(AppointDetailFragment.this, null, new MyDingdanFragment());
					}
				});
					
				dialog.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
				});
				dialog.show();
			}
			
		});
		
		/**
		 * 马上咨询
		 */
		RelativeLayout btn_zixun_now = (RelativeLayout) v.findViewById(R.id.btn_appoint_zixun_now);
		btn_zixun_now.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				appointment.fetchInBackground("counselor_id", new GetCallback<AVObject>(){
			          
					@Override
					public void done(AVObject avobject, AVException e) {
						Counselor counselor = avobject.getAVObject("counselor_id");
						String memberId = counselor.getName();

						Intent intent = new Intent(getActivity(), AVSingleChatActivity.class);
						intent.putExtra(Constants.MEMBER_ID, memberId);
					    startActivity(intent);
					}
		       });
			}
			
		});
	}
}
