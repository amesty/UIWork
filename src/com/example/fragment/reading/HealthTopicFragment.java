package com.example.fragment.reading;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.avos.avoscloud.AVException;
import com.cxw.utils.Uris.NoticeType;
import com.example.adapter.ReadingAdapter;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.dao.ArticleDao;
import com.example.fragment.BasicFragment;
import com.example.model.Article;
import com.example.uiwork.R;
import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;

public class HealthTopicFragment extends BasicFragment{
	private volatile List<Article> newArticleList;
	private ListView mArticleLv;
	private ReadingAdapter mAdapter;
	
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    // Override this method to do custom remote calls
	    @Override
	    protected Void doInBackground(Void... params) {
	    	try {
	    		newArticleList = (new ArticleDao()).findArticleByType("health");
			} catch (AVException e) {
				e.printStackTrace();
			}
	    	return null;
	    }
	    
	    @Override
	    protected void onPreExecute() {
	     // ToDoListActivity.this.progressDialog =
	          //ProgressDialog.show(ToDoListActivity.this, "", "Loading...", true);
	      super.onPreExecute();
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) {
	      super.onProgressUpdate(values);
	    }

	    @Override
	    protected void onPostExecute(Void result) {

	      mArticleLv = (ListView) getActivity().findViewById(R.id.reading_listview_health);
	      TextView noNetwork = (TextView) getActivity().findViewById(R.id.reading_health_empty_tv);
	      mArticleLv.setEmptyView(noNetwork);
		  
	      mAdapter = new ReadingAdapter(getActivity(), newArticleList);
	     
	      mArticleLv.setAdapter(mAdapter);
	      mAdapter.notifyDataSetChanged(); 
	      //注册上下文菜单
	      //registerForContextMenu(newProblemLv);

	      
	      if (newArticleList != null && !newArticleList.isEmpty()) {
	    	  noNetwork.setVisibility(View.GONE);
	      } else {
	    	  noNetwork.setVisibility(View.VISIBLE);
	      }
	      mArticleLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					Article article = newArticleList.get(index);
					Bundle bundle = new Bundle();
					bundle.putSerializable("ARTICLE", article);
					UiHelper.showFragment(HealthTopicFragment.this, bundle, new ArticleDetailFragment());
				}			
			});
	      mArticleLv.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) { }
				
				@Override
				public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					int lastItemIndex = listView.getLastVisiblePosition();//当前屏幕最后一条记录ID
					if(lastItemIndex + 1 == totalItemCount && isLoadFinish){//判断往下是否达到数据最后一条记录
						//锁定加载完成标志
						isLoadFinish = false;
						//根据页码获取最新数据
						try {
							addDataAtBackground();
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			});
			
			if(NetWorkHelper.isNetworkConnected(mApp)){
	    		addDataAtBackground();
			}else{
				noNetwork.setText("网络未连接，请检查移动数据是否打开或点击标题栏刷新");
				noNetwork.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mApp.postEvent(new OnReceivedRefreshNewsShownEvent(NoticeType.NOTIFICATION));
					}
				});
			} 
	    }
	  }
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_reading_listview_health);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		//TextView title = (TextView) v.findViewById(R.id.title_text);
		//title.setText("我的订单");
		
		new RemoteDataTask().execute();
    }
	
	private boolean isLoadFinish = false;
	
	private void addDataAtBackground(){
		//newProblemLv.addFooterView(layoutViewFootProgressBar);
		/*new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					List<Question> newList = NoticeTitleService.getNewsList(listQuestionPageNow, noticeType);
					if(newList != null && newList.size() > 0){
						newQuestionList.addAll(newList);		
						mApp.postEvent(new OnReceivedNoticesEvent(1));
					}else{
						mApp.postEvent(new OnReceivedNoticesEvent(2));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();*/
	}
	
}
