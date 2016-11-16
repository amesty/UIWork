package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.TestAdapter;
import com.example.uiwork.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class XinggeCeshiActivity extends Activity{
	
	/*private List<Test> listTests = new ArrayList<Test>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_t_xinggeceshi);
		Button search = (Button) findViewById(R.id.title_search);
		search.setVisibility(View.INVISIBLE);
		search.setClickable(false);
		TextView title = (TextView) findViewById(R.id.title_text);
		title.setText("�Ը����");
		
		initTest();
		TestAdapter adapter = new TestAdapter(XinggeCeshiActivity.this,listTests);
		ListView listView = (ListView) findViewById(R.id.xingge_test_list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Test test = listTests.get(position);
				int data1 = test.getId();
				Intent intent = new Intent(XinggeCeshiActivity.this, TestDetailActivity.class);
				intent.putExtra("extra_data1", data1);
				startActivity(intent);
			}
		});
	}
	
	private void initTest() {
		Test test = new Test(R.drawable.example,"���ж�ᡰ���ˡ���"," "," ","2016-07-11", 
				"�����ȷָ�������˵�����Ǻ��κ�һ��İ����֮����������˲��ᳬ�������������������Ϣʱ���"
				+ "��δ�����˻���ϵ��÷ǳ���Ҫ������������ͨ������������˽��Լ���"
				+ "�˼�״̬�ɣ�","�Ը����",2035,56);
		listTests.add(test);
	}*/

}
