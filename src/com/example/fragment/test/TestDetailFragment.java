package com.example.fragment.test;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.avos.avoscloud.AVException;
import com.cxw.utils.Uris.NoticeType;
import com.example.adapter.ReadingAdapter;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.dao.ArticleDao;
import com.example.dao.TestDao;
import com.example.fragment.BasicFragment;
import com.example.fragment.reading.AllTopicFragment;
import com.example.fragment.reading.ArticleDetailFragment;
import com.example.model.Answer;
import com.example.model.Article;
import com.example.model.Question;
import com.example.model.Test;
import com.example.model.TestAnswer;
import com.example.uiwork.R;
import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;

public class TestDetailFragment extends BasicFragment implements OnClickListener{

	private Test test;
	private Question question;
	private TestAnswer answer;
	private RadioButton bt_a,bt_b,bt_c,bt_d,bt_e;
	private int sumScore=0;
	private int questionNum,answerNum;
	private TextView questionContent,questionNum_tv,answered_tv;
	private Button nextOne;
	private int counter = 0;
	Bundle bundle;
	private volatile List<Question> newQuestionList;
	private volatile List<TestAnswer> newAnswerList;
	private int bt_aS,bt_bS,bt_cS,bt_dS,bt_eS;
	String testKey,questionKey;
	RadioGroup radiogroup;
	LinearLayout two_bt;
	
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    // Override this method to do custom remote calls
	    @Override
	    protected Void doInBackground(Void... params) {
	    	try {
				newQuestionList = (new TestDao()).findQuestionByType(testKey);
			} catch (AVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			questionNum = newQuestionList.size();
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
	    		question = newQuestionList.get(counter);
	    		questionKey = question.getQuestionKey();
	    		new answerTask().execute();	    				
	    }
		
	} 
	    
	
	private class answerTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				newAnswerList = (new TestDao()).findAnswerByType(questionKey);
			} catch (AVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			answerNum = newAnswerList.size();
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
		    	initial();
		}

	}
	
	private void initial() {
		// TODO Auto-generated method stub
		questionContent.setText(question.getQuestionContent());
		
		bt_a.setText((newAnswerList.get(0)).getAnswerContent());
		bt_aS = (newAnswerList.get(0)).getScore();
		bt_a.setChecked(false);
		
		bt_b.setText((newAnswerList.get(1)).getAnswerContent());
		bt_bS = (newAnswerList.get(1)).getScore();
		bt_b.setChecked(false);
		
		bt_c.setText((newAnswerList.get(2)).getAnswerContent());
		bt_cS = (newAnswerList.get(2)).getScore();
		bt_c.setChecked(false);
		
		bt_d.setText((newAnswerList.get(3)).getAnswerContent());
		bt_dS = (newAnswerList.get(3)).getScore();
		bt_d.setChecked(false);
		
		bt_e.setText((newAnswerList.get(4)).getAnswerContent());
		bt_eS = (newAnswerList.get(4)).getScore();
		bt_e.setChecked(false);
		
		radiogroup.setVisibility(View.VISIBLE);
		nextOne.setVisibility(View.VISIBLE);
		two_bt.setVisibility(View.VISIBLE);
		
		questionNum_tv.setText(questionNum+"");
		answered_tv.setText(counter + "");
	}
	
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_test_five_detail);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		
		//设置标题栏的提示文字
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("测试详情");
		
		
		bundle = getArguments();
		test = (Test) bundle.getSerializable("TEST");
		testKey = test.getKey();
		
		questionContent = (TextView)v.findViewById(R.id.test_question);
		bt_a = (RadioButton) v.findViewById(R.id.radioBt_A);
		bt_b = (RadioButton) v.findViewById(R.id.radioBt_B);
		bt_c = (RadioButton) v.findViewById(R.id.radioBt_C);
		bt_d = (RadioButton) v.findViewById(R.id.radioBt_D);
		bt_e = (RadioButton) v.findViewById(R.id.radioBt_E);
		nextOne = (Button)v.findViewById(R.id.next_Question);
		questionNum_tv = (TextView)v.findViewById(R.id.test_questionNum);
		answered_tv =(TextView)v.findViewById(R.id.test_answerNum);
		two_bt = (LinearLayout) v.findViewById(R.id.two_button);
		radiogroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
		
		bt_a.setOnClickListener(this);
		bt_b.setOnClickListener(this);
		bt_c.setOnClickListener(this);
		bt_d.setOnClickListener(this);
		bt_e.setOnClickListener(this);
		nextOne.setOnClickListener(this);
		
		new RemoteDataTask().execute();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.radioBt_A:
			sumScore += bt_aS; 			
			break;
		case R.id.radioBt_B:
			sumScore += bt_bS;			
			break;
		case R.id.radioBt_C:
			sumScore += bt_cS;			
			break;
		case R.id.radioBt_D:
			sumScore += bt_dS;
			break;
		case R.id.radioBt_E:
			sumScore += bt_eS;
			break;
		case R.id.next_Question:
			if(counter<questionNum-1){
				 if(counter == questionNum-2){
						nextOne.setText("查看结果");
					}
				counter++;
				new RemoteDataTask().execute();
				
			}
			
			else{	
				Bundle resultBundle = new Bundle();
				resultBundle.putInt("SCORE", sumScore);
				resultBundle.putString("TESTID", test.getTestID());
				resultBundle.putSerializable("TEST", test);
				UiHelper.showFragment(TestDetailFragment.this, resultBundle, new FragmentResult());
			}
			
		
			break;
		}
		
	}

	
}
