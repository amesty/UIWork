package com.example.fragment.wenda;

import com.avos.avoscloud.AVUser;
import com.example.fragment.BasicFragment;
import com.example.model.Problem;
import com.example.uiwork.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.utils.UiHelper;

public class WriteQuestionFragment extends BasicFragment implements View.OnClickListener{
	public interface ICustomDialogEventListener {
        public void customDialogEvent(Problem p);
    }
    private ICustomDialogEventListener mCustomDialogEventListener;
	
	public ImageButton chooseType;
	public String questionContent;
	public String questionType;
	public boolean isAnonymous;
	public EditText questionEt;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_w_writequestion);
    }
	
	@SuppressLint("NewApi")
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		Button finish = (Button) v.findViewById(R.id.title_search);
		chooseType = (ImageButton) v.findViewById(R.id.choose_category);
		final TextView CategoryTv = (TextView) v.findViewById(R.id.category_tv);
		questionEt = (EditText) v.findViewById(R.id.question_et);
		ToggleButton isAnonymousTb = (ToggleButton) v.findViewById(R.id.isAnonymous_tb);
		
		
		title.setText("发布问题");
		finish.setBackground(getResources().getDrawable(R.drawable.finish));
		finish.setVisibility(View.VISIBLE);
		finish.setClickable(true);
		
		isAnonymousTb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					//选中
					isAnonymous = true;
				}else{
					//未选中
					isAnonymous = false;
				}
			}
		});
		
		questionEt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				questionEt.setHint(null);
			}
		});
		
		chooseType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				PopupMenu popup = new PopupMenu(getActivity(), chooseType);
				
				popup.getMenuInflater()
                .inflate(R.menu.popupmenu, popup.getMenu());

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
				        case R.id.item_1:
				        	questionType = "自我成长";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了自我成长", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_2:
				        	questionType = "情绪管理";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了情绪管理", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_3:
				        	questionType = "人际沟通";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了人际沟通", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_4:
				        	questionType = "恋爱婚姻";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了恋爱婚姻", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_5:
				        	questionType = "职场问题";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了职场问题", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_6:
				        	questionType = "亲子家庭";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了亲子家庭", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_7:
				        	questionType = "心理障碍";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了心理障碍", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_8:
				        	questionType = "学习困扰";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了学习困扰", Toast.LENGTH_SHORT).show();
				            return true;
				        case R.id.item_9:
				        	questionType = "其他";
				        	CategoryTv.setText(questionType);
				        	CategoryTv.setTextColor(getResources().getColor(R.color.blue));
				            Toast.makeText(getActivity(), "您选择了其他", Toast.LENGTH_SHORT).show();
				            return true;
				    }
				    return false;
					}
				});
				popup.show(); 
			}
		});
		
		finish.setOnClickListener(this);
		
		/*finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				questionContent = questionEt.getText().toString();
				AVUser currentUser = AVUser.getCurrentUser();
				if (currentUser != null) {
		            // 跳转到首页
					System.out.println("成功get当前用户");
		        } else {
		            //缓存用户对象为空时，可打开用户注册界面…
		        	System.out.println("不能get当前用户");
		        }
				Problem p = new Problem();
				p.put(Problem.ANONYMOUS_KEY, isAnonymous);
				p.put(Problem.CONTENT_KEY, questionContent);
				p.put(Problem.TYPE_KEY, questionType);
				p.put(Problem.USER_KEY, currentUser);
				p.saveInBackground();
				getFragmentManager().popBackStack();
			}
		});*/
    }
	
	public static WriteQuestionFragment newInstance(Problem p) {
        WriteQuestionFragment fragment = new WriteQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("NEWPROBLEM", p);
        fragment.setArguments(args);
        return fragment;
    }
	
	/**
     * 回调结果值
     * @param listener
     */
    public void setResultListener(ICustomDialogEventListener listener){
        mCustomDialogEventListener = listener;
    }
    
    @Override
    public void onClick(View view) {
    	questionContent = questionEt.getText().toString();
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
            // 跳转到首页
			System.out.println("成功get当前用户");
        } else {
            //缓存用户对象为空时，可打开用户注册界面…
        	System.out.println("不能get当前用户");
        }
		Problem p = new Problem();
		p.put(Problem.ANONYMOUS_KEY, isAnonymous);
		p.put(Problem.CONTENT_KEY, questionContent);
		p.put(Problem.TYPE_KEY, questionType);
		p.put(Problem.USER_KEY, currentUser);
		p.saveInBackground();
    	
		//mCustomDialogEventListener.customDialogEvent(p);
        getFragmentManager().popBackStack();
    }
}
