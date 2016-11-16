package com.example.model;

import java.io.Serializable;

import android.text.format.DateFormat;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName(Article.ARTICLE_CLASS)
public class Article extends AVObject implements Serializable{

	static final String ARTICLE_CLASS = "article";
	
	public static final String ARTICLE_COMMENT_COUNT = "article_commentCount";
	public static final String ARTICLE_LOOK_COUNT = "article_lookCount";
	public static final String ARTICLE_PHOTO = "article_photo";
	public static final String ARTICLE_PUBLISHER = "article_publisher";
	public static final String ARTICLE_TAG = "article_tag";
	public static final String ARTICLE_TITLE = "article_title"; 
	public static final String ARTICLE_TYPE = "article_type";
	public static final String ARTICLE_CONTENT = "article_content";
	public static final String ARTICLE_ID = "objectId";
	
	
	public int getCommentCount() {
		return this.getInt(ARTICLE_COMMENT_COUNT);
	}
	
	public int getLookCount(){
		return this.getInt(ARTICLE_LOOK_COUNT);
	}
	
	public String getTitle(){
		return this.getString(ARTICLE_TITLE);
	}
	
	public String getArticlePhotoUrl() {
		    AVFile articlePhoto = getAVFile(ARTICLE_PHOTO);
		    if (articlePhoto != null) {
		      return articlePhoto.getUrl();
		    } else {
		      return null;
		    }
		  }
	
	 
	 
	public String getPublisher(){
		return this.getString(ARTICLE_PUBLISHER);
	}
	
	public String getTag() {
		return this.getString(ARTICLE_TAG);
	}
	
	public String getType(){
		return this.getString(ARTICLE_TYPE);
	}
	
	public String getArticleContent(){
		return this.getString(ARTICLE_CONTENT);
	}
	
	public String getCreatTime() {
		return DateFormat.format("yyyy-MM-dd HH:mm",this.getCreatedAt()).toString();
	}
	
	public String getId(){
		return this.getObjectId();
	}
}
