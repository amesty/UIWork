package com.example.dao;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.model.User;


public class UserDao {
	
	public static void fetchUserById(String objectId,GetCallback<AVObject> getCallback) {
		User user = new User();
		user.setObjectId(objectId);
		user.fetchInBackground(getCallback);
	}
	
	public static User getUserById(String id) throws AVException {
		
		AVQuery<User> query = AVUser.getQuery(User.class);
		User result = query.get(id);
		return result;
	}
	
}
