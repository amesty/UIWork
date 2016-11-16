package com.example.fragment.my;

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
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.cxw.utils.Uris.NoticeType;
import com.example.adapter.FollowAdapter;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.fragment.BasicFragment;
import com.example.uiwork.R;
import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;

public class FensiFragment extends BasicFragment{
	private volatile List<AVUser> newFensiList;
    private ListView newFensiLv;
    private FollowAdapter mAdapter;
    
	
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    // Override this method to do custom remote calls
	    @Override
	    protected Void doInBackground(Void... params) {
	    	try {
	    		//查询粉丝
	    		AVUser currentUser = AVUser.getCurrentUser();
	    		AVQuery<AVUser> followerQuery = AVUser.followerQuery(currentUser.getObjectId(), AVUser.class);
	    		followerQuery.findInBackground(new FindCallback<AVUser>() {
	    		  
	    			@Override
	    			public void done(List<AVUser> avObjects, AVException e) {
	    				// TODO Auto-generated method stub
	    				
	    			}
	    		});
	    		newFensiList = followerQuery.find();
	    		System.out.println("粉丝查询结果："+newFensiList);
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

	      newFensiLv = (ListView) getActivity().findViewById(R.id.my_fensi_lv);
	      TextView noNetwork = (TextView) getActivity().findViewById(R.id.fensi_empty_tv);
	      newFensiLv.setEmptyView(noNetwork);
		  
	      mAdapter = new FollowAdapter(getActivity(), newFensiList);
	     
	      newFensiLv.setAdapter(mAdapter);
	      mAdapter.notifyDataSetChanged(); 
	      //注册上下文菜单
	      //registerForContextMenu(newProblemLv);

	      
	      if (newFensiList != null && !newFensiList.isEmpty()) {
	    	  noNetwork.setVisibility(View.GONE);
	      } else {
	    	  noNetwork.setVisibility(View.VISIBLE);
	      }
	      newFensiLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> listView, View itemView, int index, long id) {

					/*Appointment a = newGuanzhuyonghuList.get(index);
					Bundle bundle = new Bundle();
					bundle.putSerializable("APPOINTMENT", a);*/
					//UiHelper.showFragment(MyDingdanFragment.this, bundle, new AppointDetailFragment());
				}			
			});
	      newFensiLv.setOnScrollListener(new OnScrollListener() {
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
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_m_fensi);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("我的粉丝");

		new RemoteDataTask().execute();
		
    }
	
	private boolean isLoadFinish = false;
	
	private void addDataAtBackground(){
	}
}
