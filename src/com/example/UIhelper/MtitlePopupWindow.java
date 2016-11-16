package com.example.UIhelper;

import java.util.ArrayList;
import java.util.List;

import com.example.uiwork.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MtitlePopupWindow extends PopupWindow{
	
	private Context mContext;
	
	private OnPopupWindowClickListener listener;
	
	private ArrayAdapter adapter;
	
	private List<String> list = new ArrayList<String>();
	
	private TextView textView;
	
	private int width = 0;
	
	public MtitlePopupWindow(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = inflater.inflate(R.layout.title_popupwindow, null);
		setContentView(popupView);
		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(600);
		
		setAnimationStyle(R.style.mypopwindow_anim_style);
	    
	    this.setFocusable(true);
	    this.setBackgroundDrawable(new BitmapDrawable());
	    this.setOutsideTouchable(true);
	    
	    textView = (TextView) popupView.findViewById(R.id.popwindow_title);
	    ListView listView = (ListView) popupView.findViewById(R.id.popupwindow);
	    adapter = new ArrayAdapter(mContext, R.layout.item_popupwindow, R.id.popup_item, list);
	    listView.setAdapter(adapter);
	    
	    listView.setOnItemClickListener(new OnItemClickListener() {
	    	
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View view,
	    			int position, long id) {
	    		MtitlePopupWindow.this.dismiss();
	    		if(listener != null) {
	    			listener.onPopupWindowItemClick(position);
	    		}
	    	}
		});
	}
	
	public void setOnPopupWindowClickListener(OnPopupWindowClickListener listener) {
		this.listener = listener;
	}
	
	public void changeData(String text, List<String> mList) {
		textView.setText(text);
		list.addAll(mList);
		adapter.notifyDataSetChanged();
	}
	
	public interface OnPopupWindowClickListener {
		void onPopupWindowItemClick(int position);
	}	

}
