package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName(Test.TEST_CLASS)
public class Test extends AVObject implements Serializable{
	
	static final String TEST_CLASS = "test";
	
	public static final String TEST_ID = "objectId";
	public static final String TEST_COMMENT_NUM = "test_commentNum";
	public static final String TEST_GOOD_NUM = "test_goodNum";
	public static final String TEST_NUM = "test_num";
	public static final String TEST_PHOTO = "test_photo";
	public static final String TEST_TITLE = "test_title";
	public static final String TEST_TYPE = "test_type";
	public static final String TO_QUESTION = "toQuestion";
	public static final String TEST_KEY = "test_key";
	
	public void setCommentCount(int count) {
		this.put(TEST_COMMENT_NUM, count);
	}
	
	public String getKey(){
		return this.getString(TEST_KEY);
	}
	
	public String getTestID(){
		return this.getString(TEST_ID);
	}
	
	public int getCommentNum(){
		return this.getInt(TEST_COMMENT_NUM);
	}
	public int getGoodNum(){
		return this.getInt(TEST_GOOD_NUM);
	}
	public int getTestNum(){
		return this.getInt(TEST_NUM);
	}
	public String getTestTitle(){
		return this.getString(TEST_TITLE);
	}
	public String getTestType(){
		return this.getString(TEST_TYPE);
	}
	public JSONArray getQuestion(){
		return this.getJSONArray(TO_QUESTION);
	}
	public String getTestPhotoUrl(){
		AVFile testPhoto = getAVFile(TEST_PHOTO);
	    if (testPhoto != null) {
	      return testPhoto.getUrl();
	    } else {
	      return null;
	    }
	}
	
}
