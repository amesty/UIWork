package com.example.adapter;

import java.util.List;

import com.example.uiwork.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class TestAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	/*List<Test> listTests;
	LayoutInflater layoutInflater;
	private DataWrapper dataWrapper;

	public TestAdapter(Context context, List<Test> listTests) {
		this.listTests = listTests;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	// �õ���������
	@Override
	public int getCount() {
		return listTests.size();
	}

	// �������������õ������ж�Ӧ������
	@Override
	public Object getItem(int index) {
		return listTests.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	private final class DataWrapper {
		ImageView imageViewIcon;
		TextView testTitle;
		TextView testLook;
		TextView testComment; 
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		final Test nm = listTests.get(index);

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_test, null);
			dataWrapper = new DataWrapper();
			dataWrapper.imageViewIcon = (ImageView) convertView.findViewById(R.id.icon);
			dataWrapper.testTitle = (TextView) convertView.findViewById(R.id.test_title);
			dataWrapper.testLook = (TextView) convertView.findViewById(R.id.test_lookcount);
			dataWrapper.testComment = (TextView) convertView.findViewById(R.id.test_commentcount);
			convertView.setTag(dataWrapper);
		} else {
			dataWrapper = (DataWrapper) convertView.getTag();
		}
		
		dataWrapper.imageViewIcon.setImageResource(nm.getId());
		dataWrapper.testTitle.setText(nm.getTitle());
		dataWrapper.testLook.setText("" + nm.getLookCount());
		dataWrapper.testComment.setText("" + nm.getCommentCount());

		return convertView;
	}*/
}
