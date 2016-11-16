package com.example.dao;


import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.model.Appointment;
import com.example.model.Counselor;

public class AppointmentDao {
	
	public static List<Appointment> findAppointmentByID() throws AVException {

		AVUser currentUser = AVUser.getCurrentUser();
		//String userId = currentUser.getObjectId();
		System.out.println("appointmentUser:"+currentUser);
		
	    AVQuery<Appointment> query = AVQuery.getQuery(Appointment.class);
	    query.whereEqualTo("user_id", currentUser);
	    
	    try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query Appointments failed.", exception);
		      return Collections.emptyList();
		    }
	}

	public void yuyuezhuanjia(AVUser currentUser,Counselor counselorId,
			String consultWay,String userChenhu,String consultantSex,String consultantAge,String consultantProblem){
		
		final AVObject object = new AVObject("Appointment");
		object.put("user_id", currentUser);
		object.put("counselor_id", counselorId);
		object.put("apt_consultWay", consultWay);
		object.put("user_chenhu", userChenhu);
		object.put("apt_isDiagnose", false);
		object.put("apt_age", consultantAge);
		object.put("apt_sex", consultantSex);
		object.put("apt_descriprion", consultantProblem);
		
		object.saveInBackground(new SaveCallback(){
			@Override
			public void done(AVException e) {
				if (e==null) {
					Log.d("YueyueZixun", object.getObjectId());
				}else {
					// 失败的话，请检查网络环境以及 SDK 配置是否正确
					System.out.println("fail to yuyue zixun");
				}
			}
		});
	}
}
