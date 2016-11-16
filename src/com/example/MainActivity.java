package com.example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;




import com.avos.avoscloud.AVUser;
import com.example.MainApplication;
import com.example.utils.UiHelper;
import com.squareup.otto.Subscribe;
import com.example.core.Events;
import com.example.fragment.BasicFragment;
import com.example.fragment.FMFragment;
import com.example.fragment.UserCenterFragment;
import com.example.fragment.my.WodeFragment;
import com.example.fragment.reading.ReadingFragment;
import com.example.fragment.test.TestFragment;
import com.example.fragment.wenda.QuestionFragment;
import com.example.fragment.wenda.WriteQuestionFragment;
import com.example.fragment.zixun.ConsultFragment;
import com.example.fragment.zixun.SearchFragment;
import com.example.model.Problem;
import com.example.uiwork.R;

public class MainActivity extends BaseActivity {

	private TextView textViewTitle;
	private Button btnSearch;
	private Button btnUser;
	private View[] tabViewType;
	private View selectedView;
	private String userId;

	private BasicFragment oldFragment;
	private BasicFragment currentFragment;
	private ConsultFragment consultFragment;
	private QuestionFragment questionFragment;
	private ReadingFragment readingFragment;
	private TestFragment testFragment;
	private FragmentTransaction trx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// 保存主页面以便引用
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MainApplication.mainActivity = this;

		setContentView(R.layout.activity_main);

        userId = null;
		
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getObjectId();
		}

		// 初始化控件
		initView();
	}

	private void initView() {
		// 设置标题
		textViewTitle = (TextView) findViewById(R.id.tv_title);

		// 设置偏号设置按钮
		// 设置偏号设置按钮
				if (getUserId() != null) {
					btnUser = (Button) findViewById(R.id.btn_own);
					btnUser.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							UiHelper.showFragment(getSupportFragmentManager(), null, new WodeFragment());
						}
					});
				}
				else {
					btnUser = (Button) findViewById(R.id.btn_own);
					btnUser.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							UiHelper.showFragment(getSupportFragmentManager(), null, new UserCenterFragment());
						}
					});
				}
				
		
		btnSearch = (Button) findViewById(R.id.btn_search);
		
		consultFragment = new ConsultFragment();
		questionFragment = new QuestionFragment();
		readingFragment = new ReadingFragment();
		testFragment = new TestFragment();
		currentFragment = null;
		oldFragment = null;

		// 找到按钮
		tabViewType = new View[4];
		tabViewType[0] = findViewById(R.id.btn_one);
		tabViewType[1] = findViewById(R.id.btn_two);
		tabViewType[2] = findViewById(R.id.btn_three);
		tabViewType[3] = findViewById(R.id.btn_four);

		// 将第一个按钮设置为选中状态
		onClickTab(tabViewType[0]);
		textViewTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UiHelper.shutKeyboardIfNeed(MainActivity.this);
			}
		});
	}

	private String getUserId() {
		return userId;
	}

	/**
	 * button点击事件，此方法在 XML文件中引用，无需显示调用
	 * 
	 * @param view
	 */
	public void onClickTab(View view) {
		if (view == null) {
			if (selectedView != null) {
				selectedView.setSelected(false);
				selectedView = null;
			}
			return;
		}

		if (view == selectedView)
			return;

		if (selectedView != null)
			selectedView.setSelected(false);
		view.setSelected(true);
		selectedView = view;

		switchUI(view);
	}

	@SuppressLint("NewApi")
	private void switchUI(View view) {

		String title = ((TextView) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(1)).getText().toString();
		
		// 设置新界面
		switch (view.getId()) {
		case R.id.btn_one:
			currentFragment = consultFragment;
			btnSearch.setBackground(getResources().getDrawable(R.drawable.search_bg));
			btnSearch.setVisibility(View.VISIBLE);
			btnSearch.setClickable(true);
			btnSearch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					UiHelper.showFragment(getSupportFragmentManager(), null, new SearchFragment());
				}
			});
			break;
		case R.id.btn_two:
			currentFragment = questionFragment;
			btnSearch.setBackground(getResources().getDrawable(R.drawable.write_question));
			btnSearch.setVisibility(View.VISIBLE);
			btnSearch.setClickable(true);
			btnSearch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					WriteQuestionFragment fragment = WriteQuestionFragment.newInstance(null);
	                fragment.setResultListener(new WriteQuestionFragment.ICustomDialogEventListener() {
	                    @Override
	                    public void customDialogEvent(Problem p) {
	                    	questionFragment.mNewFg.mAdapter.clearList();
	                    	questionFragment.mNewFg.mAdapter.updateList(questionFragment.mNewFg.newProblemList);
	                    	questionFragment.mNewFg.mAdapter.notifyDataSetChanged();
	                    }
	                });
	                UiHelper.showFragment(getSupportFragmentManager(), null, new WriteQuestionFragment());
				}
			});
			break;
		case R.id.btn_three:
			currentFragment = readingFragment;
			btnSearch.setVisibility(View.INVISIBLE);
			btnSearch.setClickable(false);
			break;
		case R.id.btn_four:
			currentFragment = testFragment;
			btnSearch.setVisibility(View.INVISIBLE);
			btnSearch.setClickable(false);
			break;
		}
		
		textViewTitle.setText(title);
		
		trx = getSupportFragmentManager().beginTransaction();	
		if(oldFragment != null){
			if(oldFragment == currentFragment)
				return;
			trx.hide(oldFragment);
		}
		
		if (!currentFragment.isAdded()) {
			trx.add(R.id.tabcontent, currentFragment, currentFragment.getClass().getName());
		}
		
		trx.show(currentFragment).commit();
		oldFragment = currentFragment;

	}

	// 更新标题
    @Subscribe
    public void refreshHints(Events.OnReceivedSettingRefreshEvent e){
    	if(currentFragment == consultFragment){
			//String title = CommonDao.getCourseTitle();
    		String title = "sdfas";
			textViewTitle.setText(title);
    	}
    }
}
