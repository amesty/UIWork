package com.example.model;

import java.io.Serializable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

//@AVClassName(User.USER_CLASS)
//public class User extends AVObject{
public class User extends AVUser{
	//static final String USER_CLASS = "_User";

	public static final String USERNAME_KEY = "username";
	public static final String PHOTO_KEY = "userPhoto";
	
	public String getAvatarUrl() {
	    AVFile userPhoto = getAVFile(PHOTO_KEY);
	    if (userPhoto != null) {
	      return userPhoto.getUrl();
	    } else {
	      return null;
	    }
	 }
	
	public AVFile getPhoto() {
		if (getAVFile(PHOTO_KEY)==null) {
			System.out.println("getAVFile null");
		}else {
			System.out.println("getAVFile not null");
		}
		return getAVFile(PHOTO_KEY);
	}
	
	public String getUserPhotoUrl() {
	    AVFile articlePhoto = getAVFile(PHOTO_KEY);
	    if (articlePhoto != null) {
	      return articlePhoto.getUrl();
	    } else {
	      return null;
	    }
	  }
	
	public String getUsername() {
		return this.getString(USERNAME_KEY);
	}
	
	public void setUsername(String username) {
		this.put(USERNAME_KEY, username);
	}
	
	public String getPortraitUrl() {
        return super.getAVFile(PHOTO_KEY).getThumbnailUrl(true, 100, 100);
    }	
}
