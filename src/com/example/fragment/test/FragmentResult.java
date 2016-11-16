package com.example.fragment.test;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.example.dao.TestDao;
import com.example.fragment.BasicFragment;
import com.example.model.Test;
import com.example.model.TestScore;
import com.example.uiwork.R;
import com.example.utils.UiHelper;

public class FragmentResult extends BasicFragment implements OnClickListener{

	int the_score;
	String testId,state;
	char grade;
	Test test;
	private List<TestScore> list;
	private TextView testName,testState,goodTV,commentTV;
	private ImageButton bt_zan,bt_comment;
	private Button back;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    // Override this method to do custom remote calls
	    @Override
	    protected Void doInBackground(Void... params) {
	    	try {
				list = (new TestDao()).getTestResultByGrade(grade);
			} catch (AVException e) {
				// TODO Auto-generated catch block
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
	    	state = (list.get(0)).getTestState();
	    	initial();
	    }
	} 
	    
	private void initial() {
		// TODO Auto-generated method stub
		testName.setText(test.getTestTitle());
		testState.setText(state);
		goodTV.setText(test.getGoodNum()+"");
		commentTV.setText(test.getCommentNum()+"");
		
	}
	
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(v, savedInstanceState);
		
		Bundle bundle = getArguments();
		the_score = bundle.getInt("SCORE");
		testId = bundle.getString("TESTID");
		test = (Test) bundle.getSerializable("TEST");
		
		
		
		testName = (TextView) v.findViewById(R.id.test_name);
		testState = (TextView) v.findViewById(R.id.test_state);
		goodTV = (TextView) v.findViewById(R.id.test_dianzan_tv);		
		commentTV = (TextView) v.findViewById(R.id.test_commentcount_tv);
		bt_zan = (ImageButton) v.findViewById(R.id.test_dianzan_imageBt);
		bt_comment = (ImageButton) v.findViewById(R.id.test_comment_imageBt);
		
		bt_zan.setOnClickListener(this);
		bt_comment.setOnClickListener(this);
		//back = (Button)v.findViewById(R.id.btn_back);
		
		analysis(the_score);
		new RemoteDataTask().execute();
		
	}

	private void analysis(int score) {
		// TODO Auto-generated method stub
		if(score>=180) 
			grade = 'A';
		else if(140<=score && score<180)
			grade = 'B';
		else if(100<=score && score<140)
			grade = 'C';
		else if(70<=score && score<100)
			grade = 'D';
		else if(40<=score && score<70)
			grade = 'E';
		else
			grade = 'F';
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_test_result);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.test_dianzan_imageBt:
			Toast.makeText(getContext(), "您已点赞!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.test_comment_imageBt:
			Bundle commentBundle = new Bundle();
			commentBundle.putSerializable("TEST", test);
			UiHelper.showFragment(FragmentResult.this, commentBundle, new CommentFragment());
			break;
		
		}
	}

	
}
