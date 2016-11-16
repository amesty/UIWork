package com.example.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Session状态类
 * @author cxw
 *
 */
public class SessionUser {
    public String  mUserType;
    public String  mUsername;
    public String  mNo;
    public String  mPassword;
    private static final String KEY_SESSION_TOKEN = "session.username";
    private static final String KEY_SESSION_PASSWORD = "session.password";
    private static final String KEY_SESSION_NO = "session.no";
    private static final String KEY_SESSION_TYPE = "session.type";

    public SessionUser(String mUserType, String mUserName, String mNo, String mPassword) {
		this.mUserType = mUserType;
		this.mUsername = mUserName;
		this.mNo = mNo;
		this.mPassword = mPassword;
	}

	/**
     * 加载Session状态
     * @param context
     * @return
     */
    public static SessionUser load(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SessionUser user = new SessionUser(sp.getString(KEY_SESSION_TYPE, ""), sp.getString(KEY_SESSION_TOKEN, ""),
        		sp.getString(KEY_SESSION_NO, ""), sp.getString(KEY_SESSION_PASSWORD, ""));
        return user;
    }

    /**
     * 保存token和密码信息到Session
     * @param context
     */
    public void save(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_SESSION_TOKEN, mUsername);
        editor.putString(KEY_SESSION_PASSWORD, mPassword);
        editor.putString(KEY_SESSION_NO, mNo);
        editor.putString(KEY_SESSION_TYPE, mUserType);
        editor.commit();
    }

}