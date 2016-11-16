package com.example.model;

import java.io.Serializable;

import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

@SuppressWarnings("serial")
@AVClassName(Counselor.COUNSELOR_CLASS)
public class Counselor extends AVObject implements Serializable{
	
static final String COUNSELOR_CLASS = "Counselor";

	public static final String INDEX = "c_index";
	public static final String SPECIALITY = "c_speciality";
	public static final String NAME = "c_name";
	public static final String MOTTO = "c_motto"; 
	public static final String RANK = "c_rank";
	public static final String CITY = "c_city";
	public static final String INTRODUCTION = "c_intro";
	public static final String PIC = "c_pic";
	private static final String BACKGROUND = "c_background";
	private static final String COUNSELORPHOTO = "c_pic";
	private static final String CHOT = "c_hot";
	
	public String getCounselorId(){
		return this.getObjectId();
	}

	public int getIndex() {
		return this.getInt(INDEX);
	}
	
	public void setIndex(int index) {
		this.put(INDEX, index);
	}
	
	public String getName() {
		return this.getString(NAME);
	}
	
	public void setName(String name) {
		this.put(NAME, name);
	}
	
	public String getRank() {
		return this.getString(RANK);
	}
	
	public void setRank(String rank) {
		this.put(RANK, rank);
	}
	
	public String getSpeciality() {
		return this.getString(SPECIALITY);
	}
	
	public void setSpeciality(String speciality) {
		this.put(SPECIALITY, speciality);
	}
	
	public String getMotto() {
		return this.getString(MOTTO);
	}
	
	public void setMotto(String motto) {
		this.put(MOTTO, motto);
	}
	
	public String getCity() {
		return this.getString(CITY);
	}
	
	public String getIntroduction() {
		return this.getString(INTRODUCTION);
	}
	
	public String getBackground() {
		return this.getString(BACKGROUND);
	}
	
	public void setCity(String city) {
		this.put(CITY, city);
	}
	
	public void setHotValue(int newHotValue) {
		this.put(CHOT, newHotValue);
	}
	
	public AVFile getPic() {
		return this.getAVFile(PIC);
	}
	
	public String getCounselorUrl() {
		    AVFile counselorPhoto = getAVFile(COUNSELORPHOTO);
		    if (counselorPhoto != null) {
		      return counselorPhoto.getUrl();
		    } else {
		      return null;
		    }
    }
	
	public void increaseHot(Counselor counselor){
		int newHotValue = this.getInt(CHOT)+1;
		System.out.println("newHotValue:"+newHotValue);
		counselor.setHotValue(newHotValue);
		counselor.saveInBackground(new SaveCallback()
		{
			@Override
			public void done(AVException e) 
			{
				if (e==null) 
				{
					Log.d("IncreseHotValue","");
				}else 
				{
					// 失败的话，请检查网络环境以及 SDK 配置是否正确
					System.out.println("fail to IncreseHotValue");
				}
			}
		});
	}
}
			
			
			