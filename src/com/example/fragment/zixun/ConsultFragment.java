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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.example.utils.UiHelper;
import com.squareup.otto.Subscribe;

public class ConsultFragment extends BasicFragment {
	
	private volatile List<Counselor> bestCounselorList;
	private ListView bestCounselorLv;
	private CounselorAdapter mAdapter;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... params) {
	    	bestCounselorList = CounselorDao.findCounselorByHot();
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

	      bestCounselorLv = (ListView) getActivity().findViewById(R.id.best_counselor_lv);
		  
	      mAdapter = new CounselorAdapter(getActivity(), bestCounselorList);
	     
	      bestCounselorLv.setAdapter(mAdapter);
	      mAdapter.notifyDataSetChanged(); 

	      bestCounselorLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					Counselor p = bestCounselorList.get(index);
					Bundle bundle = new Bundle();
					bundle.putSerializable("COUNSELOR", p);
					UiHelper.showFragment(ConsultFragment.this, bundle, new CounselorDetailFragment());
				}			
			});
	      bestCounselorLv.setOnScrollListener(new OnScrollListener() {
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
	      setListViewHeightBasedOnChildren(bestCounselorLv);
	    }
	  }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_consult);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		initViews(v);
		new RemoteDataTask().execute();
    }
	
	private boolean isLoadFinish = false;
	private Dialog dialog;

	
	@Subscribe
	public void refresh(OnReceivedRefreshNewsShownEvent e){
    	if(e.msgType == NoticeType.NOTIFICATION){
    		listProblemPageNow = 1;
    		bestCounselorList.clear();
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

	private void initViews(View v) {
		
		ImageView lianaihunyin = (ImageView) v.findViewById(R.id.lianaihunyin);
		lianaihunyin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Lianaihunyin());
			}
		});
		
		ImageView ziwochengzhang = (ImageView) v.findViewById(R.id.ziwochengzhang);
		ziwochengzhang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Ziwochengzhang());
			}
		});
		
		ImageView zhichangwenti = (ImageView) v.findViewById(R.id.zhichangwenti);
		zhichangwenti.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Zhichangwenti());
			}
		});
		
		ImageView qingxuguanli = (ImageView) v.findViewById(R.id.qingxuguanli);
		qingxuguanli.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Qingxuguanli());
			}
		});
		
		ImageView qinzijiating = (ImageView) v.findViewById(R.id.qinzijiating);
		qinzijiating.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Qinzijiating());
			}
		});
		
		ImageView renjigoutong = (ImageView) v.findViewById(R.id.renjigoutong);
		renjigoutong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Renjigoutong());
			}
		});
		
		ImageView xinlizhangai = (ImageView) v.findViewById(R.id.xinlizhangai);
		xinlizhangai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Xinlizhangai());
			}
		});
		
		ImageView xuexikunrao = (ImageView) v.findViewById(R.id.xuexikunrao);
		xuexikunrao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(ConsultFragment.this, null, new C_Xuexikunrao());
			}
		});
		
	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    if(listView == null) return;
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        
	        return;
	    }
	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += listItem.getMeasuredHeight();
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	}

}
