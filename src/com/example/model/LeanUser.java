package com.example.model;

import java.io.IOException;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

public class LeanUser extends AVUser {

	public static final String USERNAME = "username";
	public static final String CITY = "city";
	public static final String SEX = "sex";
	public static final String BREIF_INTRODUCE = "brief_introduce";
	public static final String USERPHOTO = "userPhoto";
	


	  public  String getCurrentUserId () {
	    LeanUser currentUser = getCurrentUser(LeanUser.class);
	    return (null != currentUser ? currentUser.getObjectId() : null);
	  }


	  public String getAvatarUrl() {
	    AVFile userPhoto = getAVFile(USERPHOTO);
	    if (userPhoto != null) {
	      return userPhoto.getUrl();
	    } else {
	      return null;
	    }
	  }


	  public void saveAvatar(String path, final SaveCallback saveCallback) {
	    final AVFile file;
	    try {
	      file = AVFile.withAbsoluteLocalPath(getUsername(), path);
	      put(USERPHOTO, file);
	      file.saveInBackground(new SaveCallback() {
	        public void done(AVException e) {
	          if (null == e) {
	            saveInBackground(saveCallback);
	          } else {
	            if (null != saveCallback) {
	              saveCallback.done(e);
	            }
	          }
	        }
	      });
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  public static LeanUser getCurrentUser() {
	    return getCurrentUser(LeanUser.class);
	  }

}
