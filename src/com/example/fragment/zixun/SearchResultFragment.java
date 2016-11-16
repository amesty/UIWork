package com.example.fragment.zixun;

import java.util.List;

import com.cxw.utils.Uris.NoticeType;
import com.example.adapter.CounselorAdapter;
import com.example.core.Events.OnReceivedNoticesEvent;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.dao.CounselorDao;
import com.example.fragment.BasicFragment;
import com.example.model.Counselor;
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

public class SearchResultFragment extends BasicFragment {
	
	private volatile List<Counselor> counselorsList;
	private ListView counselorsLv;
	private CounselorAdapter mAdapter; 
	private String counselorName;
	private String shanchang;
	private String diqu;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... params) {
	    	System.out.println("counselorName:"+counselorName);
	    	System.out.println("shanchang:" + shanchang);
	    	System.out.println("diqu:" + diqu);
	    	counselorsList = CounselorDao.findCounselors(counselorName,shanchang,diqu);
	    	if (counselorsList == null) {
	    		System.out.println("counselorsList == null");
	    	} else {
	    		System.out.println("list size:" + counselorsList.size());
	    	}
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

	      counselorsLv = (ListView) getActivity().findViewById(R.id.counselors_lv);
	      TextView empty = (TextView) getActivity().findViewById(R.id.empty_tv);
	      counselorsLv.setEmptyView(empty);
		  
	      mAdapter = new CounselorAdapter(getActivity(), counselorsList);
	     
	      counselorsLv.setAdapter(mAdapter);
	      mAdapter.notifyDataSetChanged(); 

	      if (counselorsList != null && !counselorsList.isEmpty()) {
	        empty.setVisibility(View.GONE);
	      } else {
	        empty.setVisibility(View.VISIBLE);
	      }
	      counselorsLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					Counselor p = counselorsList.get(index);
					Bundle bundle = new Bundle();
					bundle.putSerializable("COUNSELOR", p);
					UiHelper.showFragment(SearchResultFragment.this, bundle, new CounselorDetailFragment());
				}			
			});
	      counselorsLv.setOnScrollListener(new OnScrollListener() {
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
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_c_sousuojieguo);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		this.getArgsPre();
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("搜索结果");
		new RemoteDataTask().execute();
    }
	
	private void getArgsPre() {
		Bundle bundle = getArguments();
		counselorName = bundle.getString("COUNSELORNAME");
		shanchang = bundle.getString("SHANCHANG");
    	diqu = bundle.getString("DIQU");
	}
	
	private boolean isLoadFinish = false;
	private Dialog dialog;
	
	@Subscribe
	public void refresh(OnReceivedRefreshNewsShownEvent e){
    	if(e.msgType == NoticeType.NOTIFICATION){
    		listProblemPageNow = 1;
    		counselorsList.clear();
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
	    
	  
