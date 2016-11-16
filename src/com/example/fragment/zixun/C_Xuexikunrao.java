package com.example.fragment.zixun;

import java.util.List;

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

import com.cxw.utils.Uris.NoticeType;
import com.example.adapter.CounselorAdapter;
import com.example.core.Events.OnReceivedNoticesEvent;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.dao.CounselorDao;
import com.example.fragment.BasicFragment;
import com.example.model.Counselor;
import com.example.uiwork.R;
import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;
import com.squareup.otto.Subscribe;

public class C_Xuexikunrao extends BasicFragment{

	private volatile List<Counselor> XuexikunraoList;
	private ListView XuexikunraoLv;
	private CounselorAdapter mAdapter;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... params) {
	    	XuexikunraoList = CounselorDao.findXuexikunraoByTime();
	    	return null;
	    }
	    
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) {
	      
	      super.onProgressUpdate(values);
	    }
	    
	    @Override
	    protected void onPostExecute(Void result) {

	      XuexikunraoLv = (ListView) getActivity().findViewById(R.id.xuexikunrao_lv);
	      TextView empty = (TextView) getActivity().findViewById(R.id.empty_tv);
	      XuexikunraoLv.setEmptyView(empty);
		  
	      mAdapter = new CounselorAdapter(getActivity(), XuexikunraoList);
	     
	      XuexikunraoLv.setAdapter(mAdapter);
	      mAdapter.notifyDataSetChanged(); 

	      if (XuexikunraoList != null && !XuexikunraoList.isEmpty()) {
	        empty.setVisibility(View.GONE);
	      } else {
	        empty.setVisibility(View.VISIBLE);
	      }
	      XuexikunraoLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					Counselor p = XuexikunraoList.get(index);
					Bundle bundle = new Bundle();
					bundle.putSerializable("COUNSELOR", p);
					UiHelper.showFragment(C_Xuexikunrao.this, bundle, new CounselorDetailFragment());
				}			
			});
	      XuexikunraoLv.setOnScrollListener(new OnScrollListener() {
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
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_c_xuexikunrao);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("学习困扰");
		new RemoteDataTask().execute();
    }
	
	private boolean isLoadFinish = false;
	private Dialog dialog;

	
	@Subscribe
	public void refresh(OnReceivedRefreshNewsShownEvent e){
    	if(e.msgType == NoticeType.NOTIFICATION){
    		listProblemPageNow = 1;
    		XuexikunraoList.clear();
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
