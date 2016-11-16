package com.example.fragment.zixun;

import java.util.Arrays;

import com.example.UIhelper.MtitlePopupWindow;
import com.example.UIhelper.MtitlePopupWindow.OnPopupWindowClickListener;
import com.example.fragment.BasicFragment;
import com.example.uiwork.R;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.UiHelper;

public class SearchFragment extends BasicFragment {
	
	private TextView sctext;
	private TextView dqtext;
	private EditText searchInput;
	
	MtitlePopupWindow mtitlePopupWindow;
	MtitlePopupWindow ntitlePopupWindow;
	String text1 = "请选择擅长类型";
	String text2 = "请选择地区";
	String[] items = {"不限","恋爱婚姻","情绪管理","亲子家庭","人际沟通","职场问题",
			"心理障碍","学习困扰","自我成长"};
	String[] items2 = {"不限","北京市","上海市","天津市","重庆市","成都市","武汉市","西安市",
			"广州市","深圳市","郑州市","杭州市","沈阳市","南京市","长沙市","昆明市",
			"青岛市","合肥市","济南市","福州市","太原市"};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_c_sousuo);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("搜索咨询师");
		
		LinearLayout shanchang = (LinearLayout) v.findViewById(R.id.shanchang);
		LinearLayout diqu = (LinearLayout) v.findViewById(R.id.diqu);
		Button ok = (Button) v.findViewById(R.id.ok);
		
		sctext = (TextView) v.findViewById(R.id.shanchangTextview);
		dqtext = (TextView) v.findViewById(R.id.diquTextview);
		searchInput = (EditText) v.findViewById(R.id.search_input);
		
		shanchang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mtitlePopupWindow.showAtLocation(v.findViewById(R.id.shanchang), 
						Gravity.BOTTOM, 0, 0);
				backgroundAlpha(0.7f);
			}
		});
		
		mtitlePopupWindow = new MtitlePopupWindow(this.getActivity());
		mtitlePopupWindow.changeData(text1, Arrays.asList(items));
		mtitlePopupWindow.setOnPopupWindowClickListener(new OnPopupWindowClickListener() {
			
			@Override
			public void onPopupWindowItemClick(int position) {
				Toast.makeText(getActivity().getApplication(), "您选择了"+items[position], Toast.LENGTH_SHORT).show();
				backgroundAlpha(1f);
				sctext.setText(items[position]);
			}
		});
		
		diqu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ntitlePopupWindow.showAtLocation(v.findViewById(R.id.diqu), 
						Gravity.BOTTOM, 0, 0);
				backgroundAlpha(0.7f);
			}
		});
		
		ntitlePopupWindow = new MtitlePopupWindow(this.getActivity());
		ntitlePopupWindow.changeData(text2, Arrays.asList(items2));
		ntitlePopupWindow.setOnPopupWindowClickListener(new OnPopupWindowClickListener() {
			
			@Override
			public void onPopupWindowItemClick(int position) {
				Toast.makeText(getActivity().getApplication(), "您选择了"+items2[position], Toast.LENGTH_SHORT).show();
				backgroundAlpha(1f);
				dqtext.setText(items2[position]);	
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("COUNSELORNAME", searchInput.getText().toString());
				bundle.putString("SHANCHANG", sctext.getText().toString());
				bundle.putString("DIQU", dqtext.getText().toString());
				UiHelper.showFragment(SearchFragment.this, bundle, new SearchResultFragment());
			}
		});
	}
	
	public void backgroundAlpha(float bgAlpha)
    {
		WindowManager.LayoutParams lp = this.getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getActivity().getWindow().setAttributes(lp);
    }
}
