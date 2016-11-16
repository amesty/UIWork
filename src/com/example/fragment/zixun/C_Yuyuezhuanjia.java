package com.example.fragment.zixun;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.avos.avoscloud.AVUser;
import com.example.dao.AppointmentDao;
import com.example.fragment.BasicFragment;
import com.example.fragment.my.MyDingdanFragment;
import com.example.model.Counselor;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

public class C_Yuyuezhuanjia extends BasicFragment{

	private String apt_consultWay;
	private String apt_age;
	private String user_chenhu;
	private String apt_sex_s;
	private String apt_description;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_c_yuyuezhuanjia);
		
	}
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("预约咨询");
	
		final EditText C_consult_age = (EditText) v.findViewById(R.id.yuyue_age);
	    final EditText C_consult_name =(EditText) v.findViewById(R.id.yuyue_name);
		final EditText C_consult_problem =(EditText) v.findViewById(R.id.yuyue_problem);
		final RadioGroup consultwayRadioGroup = (RadioGroup) v.findViewById(R.id.radio_zixun_consultway);
		final RadioGroup sexRadioGroup = (RadioGroup) v.findViewById(R.id.radio_zixun_sex); 
		Button btnSubmit = (Button) v.findViewById(R.id.consult_submit);
		btnSubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AVUser currentUser = AVUser.getCurrentUser();
				
				Bundle bundle = getArguments();
				Counselor counselor = (Counselor) bundle.getSerializable("COUNSELOR");
			
				apt_age = C_consult_age.getText().toString();
				user_chenhu = C_consult_name.getText().toString();
				apt_description = C_consult_problem.getText().toString();
				for(int i=0;i<sexRadioGroup.getChildCount();i++){
					RadioButton sexRadioButton = (RadioButton)sexRadioGroup.getChildAt(i);
					if(sexRadioButton.isChecked()){
						apt_sex_s = sexRadioButton.getText().toString();	
					}
				}
				for(int j=0;j<consultwayRadioGroup.getChildCount();j++){
					RadioButton consultwayRadioButton = (RadioButton)consultwayRadioGroup.getChildAt(j);
					if(consultwayRadioButton.isChecked()){
						apt_consultWay = consultwayRadioButton.getText().toString();
					}
				}
				
				if(user_chenhu.isEmpty()){
					Toast.makeText(getActivity(), "请输入称呼！", Toast.LENGTH_SHORT).show();
				}
				if(apt_description.isEmpty()){
					Toast.makeText(getActivity(), "请输入问题描述！", Toast.LENGTH_SHORT).show();
				}
				if(!user_chenhu.isEmpty() && !apt_description.isEmpty()){
					AppointmentDao dao = new AppointmentDao();
					dao.yuyuezhuanjia(currentUser, counselor, apt_consultWay, user_chenhu, apt_sex_s, apt_age, apt_description);
				
					final Dialog dialog = new Dialog(getActivity());
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					dialog.setContentView(R.layout.dialog_yuyuechenggong);
					dialog.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							UiHelper.showFragment(C_Yuyuezhuanjia.this, null, new MyDingdanFragment());
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
			}
			
		});
	}
}
