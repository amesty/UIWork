package com.example.fragment.reading;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.example.fragment.BasicFragment;
import com.example.fragment.my.AppointDetailFragment;
import com.example.fragment.my.MyDingdanFragment;
import com.example.model.Appointment;
import com.example.model.Article;
import com.example.model.Counselor;
import com.example.uiwork.R;
import com.example.utils.UiHelper;
import com.squareup.picasso.Picasso;

public class ArticleDetailFragment extends BasicFragment implements OnClickListener{

	private Article article;
	private ImageButton bt_zan,bt_comment,bt_share;//页面内的所有的按钮声明
	private TextView lookCount_tv,commentCount_tv;//显示点赞和评论数量的textview
	private Boolean state = false;
	private String article_id;
	 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_reading_article_detail);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		//设置标题栏的提示文字
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("文章详情");
		
		//获取文章对象
		Bundle bundle = getArguments();
		article = (Article) bundle.getSerializable("ARTICLE");
		
		//获取文章对象后展开对象并初始化
		initView(v);
		
	}

	public void initView(View v) {
		
		final TextView tag = (TextView) v.findViewById(R.id.article_tag);
		final TextView publisher = (TextView) v.findViewById(R.id.article_publisher);
		final TextView publishDate = (TextView) v.findViewById(R.id.article_publish_date);
		final TextView content = (TextView) v.findViewById(R.id.article_content);	
		final TextView title = (TextView) v.findViewById(R.id.article_detail_title);
		final ImageView photo = (ImageView) v.findViewById(R.id.article_pic);		
		final TextView lookCount_tv = (TextView)v.findViewById(R.id.dianzan_tv);
		final TextView commentCount_tv = (TextView)v.findViewById(R.id.commentcount_tv);
		
		
		tag.setText(article.getTag());
		publisher.setText(article.getPublisher());
		publishDate.setText(article.getCreatTime());
		content.setText(article.getArticleContent());
		title.setText(article.getTitle());
		Picasso.with(getContext()).load(article.getArticlePhotoUrl()).into(photo);
		lookCount_tv.setText(article.getLookCount()+"");
		commentCount_tv.setText(article.getCommentCount()+"");
		

		//针对点赞和评论的点击事件
		v.findViewById(R.id.dianzan_imageBt).setOnClickListener(this);
		v.findViewById(R.id.comment_imageBt).setOnClickListener(this);
		v.findViewById(R.id.fenxiang_imageBt).setOnClickListener(this);
		
		
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.dianzan_imageBt:
			if(state ==false){
				Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
				state = true;
				/*article.setFetchWhenSave(true);
				article.increment("article_lookCount");
				article.saveInBackground();
				AVQuery query = AVQuery.getQuery(Article.class);
				query.selectKeys(Arrays.asList("atticle_lookCount"));
				query.findInBackground(new FindCallback<AVObject>() {

					@Override
					public void done(List<AVObject> arg0, AVException arg1) {
						// TODO Auto-generated method stub
						AVObject obj = arg0.get(0);
						lookCount_tv.setText(obj.getInt("article_lookCount")+"");
					}
				});
				*/
				
				AVObject avobj = AVObject.createWithoutData("article", article.getId());
				avobj.increment("article_lookCount");
				avobj.setFetchWhenSave(true);
		        avobj.saveInBackground();
				
				
			}
			else{
				Toast.makeText(getContext(), "您已经点赞", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.comment_imageBt:
			break;
		case R.id.fenxiang_imageBt:
			shareForFriend();
			break;
			
		}
		
	}


	public void shareForFriend() {
		Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
		intent.setType("text/plain");
		
		String text= article.getTitle()+"\n";		
		text += "\t\t"+article.getArticleContent();
		//intent.putExtra(Intent.EXTRA_SUBJECT, article.getTitle()); // 分享的标题		
		intent.putExtra(Intent.EXTRA_TEXT,text); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(Intent.createChooser(intent, "分享  "+ article.getTitle()+" 给好友")); // 目标应用选择对话框的标题
	}
}