package com.example.model;

import java.io.Serializable;

import android.text.format.DateFormat;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@SuppressWarnings("serial")
@AVClassName(Appointment.APPOINTMENT_CLASS)
public class Appointment extends AVObject implements Serializable{

	static final String APPOINTMENT_CLASS = "Appointment";
	
	public static final String CONSELOR_ID = "conselor_id";
	public static final String CONSULTANT_WAY= "apt_consultWay";
	public static final String DESCRIPETION = "apt_descriprion";
	public static final String USER_ID ="user_id";
	public static final String APT_SEX = "apt_sex";
	public static final String APT_AGE = "apt_age";
	private static final String INDEX_KEY = "appoint_index";
	
	public int getIndex() {
		return this.getInt(INDEX_KEY);
	}
	
	public Boolean getAptSex(){
		return this.getBoolean(APT_SEX);
	}
	public String getDescription(){
		return this.getString(DESCRIPETION);
	}
	public AVUser getUser() {
		return this.getAVUser(USER_ID);
	}
	
	public String getConsultWay(){
		return this.getString(CONSULTANT_WAY);
	}
	
	public String getCreatTime() {
		return DateFormat.format("yyyy-MM-dd HH:mm",this.getCreatedAt()).toString();
	}
}
