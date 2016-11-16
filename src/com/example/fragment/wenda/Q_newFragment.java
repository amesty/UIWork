package com.example.fragment.wenda;

import java.util.List;

import com.cxw.utils.Uris.NoticeType;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.adapter.ProblemAdapter;
import com.example.core.Events.OnReceivedNoticesEvent;
import com.example.dao.QuestionDao;
import com.example.fragment.BasicFragment;
import com.example.model.Problem;
import com.example.uiwork.R;

import android.app.Dialog;
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


import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;
import com.squareup.otto.Subscribe;


public class Q_newFragment extends BasicFragment {
	
	public volatile List<Problem> newProblemList;
	private ListView newProblemLv;
	public ProblemAdapter mAdapter;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    // Override this method to do custom remote calls
	    @Override
	    protected Void doInBackground(Void... params) {
	    	newProblemList = QuestionDao.findQuestionsByTime();
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

	      newProblemLv = (ListView) getActivity().findViewById(R.id.new_question_lv);
	      TextView empty = (TextView) getActivity().findViewById(R.id.new_empty_tv);
		  newProblemLv.setEmptyView(empty);
		  mAdapter = new ProblemAdapter(getActivity(), newProblemList);
	     
	      newProblemLv.setAdapter(mAdapter);

	      if (newProblemList != null && !newProblemList.isEmpty()) {
	        empty.setVisibility(View.GONE);
	      } else {
	        empty.setVisibility(View.VISIBLE);
	      }
	      newProblemLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					Problem p = newProblemList.get(index);
					final Bundle bundle = new Bundle();
					bundle.putSerializable("PROBLEM", p);
					UiHelper.showFragment(Q_newFragment.this, bundle, new QuestionDetailFragment());
				}			
			});
	      newProblemLv.setOnScrollListener(new OnScrollListener() {
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
				empty.setText("网络未连接，请检查移动数据是否打开或点击标题栏刷新");
				empty.setOnClickListener(new OnClickListener() {
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
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_w_new);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		new RemoteDataTask().execute();		
    }
	
	private boolean isLoadFinish = false;
	private Dialog dialog;

	/*private void setListView(View v) {
		newProblemLv = (ListView) v.findViewById(R.id.new_question_lv);
		emptyView = (TextView) v.findViewById(R.id.new_empty_tv);
		newProblemLv.setEmptyView(emptyView);
		QuestionDao dao = new QuestionDao();
		newProblemList = QuestionDao.findQuestionsByTime();
		mAdapter = new ProblemAdapter(getActivity(), newProblemList);
		 
		layoutViewFootProgressBar = UiHelper.getLightThemeView(getActivity(), R.layout.item_listview_foot_progressbar);
		// 添加页脚进度条
		newProblemLv.addFooterView(layoutViewFootProgressBar);
		newProblemLv.setAdapter(mAdapter);
		newProblemLv.removeFooterView(layoutViewFootProgressBar);
		
		newProblemLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {
				if(isLoadFinish){
					Bundle bundle = new Bundle();
					bundle.putString("windowTitle", newProblemList.get(index).getUsername() + "的提问");
					UiHelper.showFragment(Q_newFragment.this, bundle, new QuestionDetailFragment());
				}
			}			
		});
		
		newProblemLv.setOnScrollListener(new OnScrollListener() {
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
			emptyView.setText("网络未连接，请检查移动数据是否打开或点击标题栏刷新");
			emptyView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mApp.postEvent(new OnReceivedRefreshNewsShownEvent(NoticeType.NOTIFICATION));
				}
			});
		} 
	 }*/
	@Subscribe
	public void refresh(OnReceivedRefreshNewsShownEvent e){
    	if(e.msgType == NoticeType.NOTIFICATION){
    		listProblemPageNow = 1;
    		newProblemList.clear();
    		addDataAtBackground();
    	}
	}

	@SuppressWarnings("unused")
	private int listProblemPageNow = 1;
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
	
	@Subscribe
	public void onReceivedNews(OnReceivedNoticesEvent e){
		if(dialog != null && dialog.isShowing())
			dialog.dismiss();
		
		//newProblemLv.removeFooterView(layoutViewFootProgressBar);
		if(e.msgType == 1){
			listProblemPageNow++;
			mAdapter.notifyDataSetChanged();
			isLoadFinish = true;
		}else if(e.msgType == 2){
			mApp.showMessage("后面没有数据了");
		}
	}
}



