package com.example.dao;

import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.example.model.FollowCounselor;

public class FollowCounselorDao {


	public static List<FollowCounselor> findFollowCounselorByID() throws AVException {

		AVUser currentUser = AVUser.getCurrentUser();
		
	    AVQuery<FollowCounselor> query = AVQuery.getQuery(FollowCounselor.class);
	    query.whereEqualTo("user_id", currentUser);
	    
	    try {
		      return query.find();
		    } catch (AVException exception) {
		      Log.e("tag", "Query Appointments failed.", exception);
		      return Collections.emptyList();
		    }
	}
}
