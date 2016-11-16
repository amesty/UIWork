package com.example.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName(UserInfo.USERInfo_CLASS)
public class UserInfo extends AVObject{
	
	static final String USERInfo_CLASS = "UserInfo";
	
	private static final String USERPHOTO_KEY = "userPhoto";
	private static final String USERNAME_KEY = "username";
	
	public AVFile getPhoto() {
		return this.getAVFile(USERPHOTO_KEY);
	}
	
	public void setPhoto(AVFile photo) {
		this.put(USERPHOTO_KEY, photo);
	}
	
	 public String getUrl() {
		    AVFile userPhoto = getAVFile(USERPHOTO_KEY);
		    if (userPhoto != null) {
		      return userPhoto.getUrl();
		    } else {
		      return null;
		    }
	 }
	 
	 public String getUsername() {
		 return this.getString(USERNAME_KEY);
	 }
	 
	 public void setUsername(String name) {
		 this.put(USERNAME_KEY, name);
	 }
}
