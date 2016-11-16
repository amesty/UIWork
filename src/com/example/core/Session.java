package com.example.core;

import com.example.MainApplication;

import android.text.TextUtils;


public class Session {
    private static final String TAG = "Session";
    private SessionUser mUser;

    private MainApplication mApp;

    public static class UserType{
    	public static final int USER = 1;
    	public static final int COUNSELOR = 2;
    }
    
	/**
     * 初始化Session
     * @param app
     */
    public Session(MainApplication app) {
        this.mApp = app;
        mUser = SessionUser.load(mApp);
    }
    
    /**
     * 根据Session中的token是否有值判断用户是否登录
     * @return
     */
    public boolean isUserTypeSettinged() {
        return !TextUtils.isEmpty(mUser.mUserType);
    }
    /**
     * 根据Session中的token是否有值判断用户是否登录
     * @return
     */
    public boolean isUserNameSettinged() {
        return !TextUtils.isEmpty(mUser.mUsername);
    }
    
    public int getUserType(){
    	return Integer.valueOf(mUser.mUserType);
    }

    /**
     * 得到密码
     * @return
     */
    public String getPassword() {
        return mUser.mPassword;
    }
    
    public void setPassword(String password){
    	mUser.mPassword = password;
    }
    
    
    public void setNo(String no){
    	mUser.mNo = no;
    }
    
    public void setUsername(String username){
    	mUser.mUsername = username;
    }
    
    public String getUsername() {
        return mUser.mUsername;
    }
    
    public String getUserNo() {
        return mUser.mNo;
    }


    public void saveContext(SessionUser user){
    	mUser = user;
    	mUser.save(mApp);
    }

    public MainApplication getContext(){
    	return mApp;
    }

}
