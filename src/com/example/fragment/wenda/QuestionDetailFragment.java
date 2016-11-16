package com.example.fragment.wenda;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.cxw.utils.Uris.NoticeType;
import com.example.HelpClass.Answer_Reply;
import com.example.HelpClass.Reply_Username;
import com.example.UIhelper.BackgroundTask;
import com.example.adapter.CommentAdapter;
import com.example.adapter.CommentReplyAdapter;
import com.example.core.Events.OnReceivedNoticesEvent;
import com.example.core.Events.OnReceivedRefreshNewsShownEvent;
import com.example.dao.AnswerDao;
import com.example.dao.UserDao;
import com.example.fragment.BasicFragment;
import com.example.model.Answer;
import com.example.model.Problem;
import com.example.model.Reply;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.uiwork.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;

import com.example.utils.NetWorkHelper;
import com.example.utils.UiHelper;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

public class QuestionDetailFragment extends BasicFragment {
	
	private Problem problem;
	private String username;
	
	private ListView lv_user_answers;
	private TextView btn_reply;
	private ImageView btn_good;
	private Animation animation;
	private Button btn_comment;
	private EditText edt_reply;

	private CommentAdapter commentAdapter;
	private CommentReplyAdapter commentReplyAdapter;

	private static final int ONE_COMMENT_CODE = -1;

	private volatile List<Answer> answerList;
	private volatile List<Reply> replyList;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... params) {
	    	AnswerDao dao = new AnswerDao();
	    	answerList = dao.findAnswers(problem);
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

	      lv_user_answers = (ListView) getActivity().findViewById(R.id.comment_lv);
	      TextView empty = (TextView) getActivity().findViewById(R.id.tv_empty);
	      lv_user_answers.setEmptyView(empty);
		  
	      //commentAdapter = new CommentAdapter(getContext(), newProblemList);
	      commentAdapter = new CommentAdapter(getActivity(), answerList,
					replyToCommentListener, commentReplyAdapter,
					goodToCommentListener, replyToReplyListener);
		  lv_user_answers.setAdapter(commentAdapter);
	      

	      if (answerList != null && !answerList.isEmpty()) {
	        empty.setVisibility(View.GONE);
	      } else {
	        empty.setVisibility(View.VISIBLE);
	      }
	      lv_user_answers.setOnScrollListener(new OnScrollListener() {
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
				empty.setText("网络未连接，请检查移动数据是否打开或点击标题栏刷新");
				empty.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mApp.postEvent(new OnReceivedRefreshNewsShownEvent(NoticeType.NOTIFICATION));
					}
				});
			} 
			setListViewHeightBasedOnChildren(lv_user_answers);
	    }
	}
	
	private boolean isLoadFinish = false;
	private Dialog dialog;

	@Subscribe
	public void refresh(OnReceivedRefreshNewsShownEvent e){
    	if(e.msgType == NoticeType.NOTIFICATION){
    		listProblemPageNow = 1;
    		answerList.clear();
    		addDataAtBackground();
    	}
	}

	@SuppressWarnings("unused")
	private int listProblemPageNow = 1;
	private void addDataAtBackground(){
		
	}
	
	@Subscribe
	public void onReceivedNews(OnReceivedNoticesEvent e){
		if(dialog != null && dialog.isShowing())
			dialog.dismiss();
		
		//newProblemLv.removeFooterView(layoutViewFootProgressBar);
		if(e.msgType == 1){
			listProblemPageNow++;
			commentAdapter.notifyDataSetChanged();
			isLoadFinish = true;
		}else if(e.msgType == 2){
			mApp.showMessage("后面没有数据了");
		}
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_w_questiondetailt);
    }
	
	@SuppressLint("NewApi")
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		Bundle bundle = getArguments();
		problem = (Problem) bundle.getSerializable("PROBLEM");
		new RemoteDataTask().execute();
		initView(v);
    }
	
	public void initView(View v) {
		final ImageView userIv = (ImageView) v.findViewById(R.id.user_iv);
		final AVUser user = problem.getUser();
	    user.fetchInBackground("userinfo", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
               
                UserInfo userinfo = avObject.getAVObject("userinfo");
                Picasso.with(getActivity()).load(userinfo.getUrl()).into(userIv);
            }
        });

		final TextView title = (TextView) v.findViewById(R.id.title_text);
		final TextView usernameTv = (TextView) v.findViewById(R.id.username_tv);
		
		if (problem.getAnonymous()) {
			usernameTv.setText("匿名用户");
    		title.setText("匿名用户的提问");
		}else {
			new BackgroundTask() {
				@Override
				protected void doInBack() throws Exception {
					UserDao dao = new UserDao();
					User temp = dao.getUserById(user.getObjectId());
					username = temp.getUsername();
					usernameTv.setText(username);
				}
				@Override
				protected void onPost(Exception e) {
				
				}
			}.execute();
			new BackgroundTask() {
				@Override
				protected void doInBack() throws Exception {
					UserDao dao = new UserDao();
					User temp = dao.getUserById(user.getObjectId());
					username = temp.getUsername();
					title.setText(username + "的提问");
				}
				@Override
				protected void onPost(Exception e) {
				
				}
			}.execute();
		}

		TextView timeTv = (TextView) v.findViewById(R.id.time_tv);
		timeTv.setText(problem.getCreatTime());
		
		TextView contentTv = (TextView) v.findViewById(R.id.content_tv);
		contentTv.setText(problem.getContent());
		
		TextView questionTypeTv = (TextView) v.findViewById(R.id.question_type_tv);
		questionTypeTv.setText(problem.getType());
		
		TextView answerCountTv = (TextView) v.findViewById(R.id.answer_count_tv);
		answerCountTv.setText("" + problem.getCommentCount());
		
		btn_comment = (Button) v.findViewById(R.id.main_send);
		btn_comment.setOnClickListener(addCommentListener);
		
	}
	
	/**
	 * 监听点赞的监听（回复楼主）
	 */
	private OnClickListener goodToCommentListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.welcome_loading);
			btn_good = (ImageView) v.findViewById(R.id.good_iv);
			btn_good.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btn_good.startAnimation(animation);
					int position = (Integer) v.getTag();
					answerList.get(position).setGoodCount(answerList.get(position).getGoodCount()+1);
					answerList.get(position).saveInBackground();
				}
			});
		}
	};
	/**
	 * 回复评论的监听（回复楼主）
	 */
	private OnClickListener replyToCommentListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			onCreateDialog(ONE_COMMENT_CODE, position);
			hintKbTwo();
		}
	};
	
	/**
	 * 发表评论的监听
	 */
	private OnClickListener addCommentListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onCreateDialog(ONE_COMMENT_CODE, ONE_COMMENT_CODE);
			hintKbTwo();
		}
	};
	
	
	/**
	 * 互相回复的监听（楼中楼）
	 */
	private OnClickListener replyToReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int parentPosition = (Integer) v.getTag(R.id.tag_first);
			int childPosition = (Integer) v.getTag(R.id.tag_second);
			onCreateDialog(parentPosition, childPosition);
			hintKbTwo();
		}
	};

	/**
	 * 弹出评论的对话框
	 * 
	 * @param parentPositon
	 *            父节点的position
	 * @param childPostion
	 *            子节点的position
	 * @return
	 */
	@SuppressLint("InflateParams")
	protected Dialog onCreateDialog(final int parentPositon,
			final int childPostion) {
		LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.answer_ll);
		ll.setVisibility(View.GONE);
		final Dialog customDialog = new Dialog(getActivity(),R.style.DialogStyle);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View mView = inflater.inflate(R.layout.dialog_comment, null);
		edt_reply = (EditText) mView.findViewById(R.id.answer_et);
		btn_reply = (TextView) mView.findViewById(R.id.send_tv);
		Window dialogWindow = customDialog.getWindow();
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogWindow.setGravity(Gravity.BOTTOM);
		customDialog.setContentView(mView);
		customDialog.show();
		
		edt_reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edt_reply.setHint(null);
				if (edt_reply.getText().toString()!=null) {
					btn_reply.setTextColor(getResources().getColor(R.color.blue));
				}
			}
		});

		btn_reply.setOnClickListener(new OnClickListener() {
			
			AVUser currentUser = AVUser.getCurrentUser();
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				switch (childPostion) {
				case ONE_COMMENT_CODE:
					if (TextUtils.isEmpty(edt_reply.getText().toString())) {
						Toast.makeText(getActivity(), "内容不能为空",
								Toast.LENGTH_SHORT).show();
					} else {
						Answer answer = new Answer();
						answer.setUser(currentUser);
						answer.setProblem(problem);
						answer.setGoodCount(0);
						answer.setContent(edt_reply.getText().toString());
						answer.saveInBackground();
						
						answerList.add(answer);
						commentAdapter.clearList();
						commentAdapter.updateList(answerList);
						commentAdapter.notifyDataSetChanged();
						customDialog.dismiss();
						edt_reply.setText("");
						Toast.makeText(getActivity(), "发表成功", Toast.LENGTH_SHORT).show();
						Problem p = answer.getProblem();
						p.setCommentCount(p.getCommentCount() + 1);
						p.saveInBackground();
					}
					break;
				default:
					if (TextUtils.isEmpty(edt_reply.getText().toString())) {
						Toast.makeText(getActivity(), "内容不能为空",
								Toast.LENGTH_SHORT).show();
					} else {
						Reply reply = new Reply();
						reply.setUser(currentUser);
						reply.setAnswer(answerList.get(childPostion));
						reply.setContent(edt_reply.getText().toString());
						
						if (parentPositon != -1) {
							Answer_Reply ar = new Answer_Reply();
							ar.getReplyList(answerList.get(parentPositon));
							try {
								List<Reply> ReplyList = (List<Reply>) ar.result.take();
								Reply_Username ru = new Reply_Username();
								ru.getUsername(ReplyList.get(childPostion));
								reply.setReplyTo(ru.result.take().toString());
								reply.saveInBackground();
								ReplyList.add(reply);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							Answer_Reply ar = new Answer_Reply();
							ar.getReplyList(answerList.get(childPostion));
							try {
								//reply.setReplyTo("");
								reply.saveInBackground();
								replyList = (List<Reply>) ar.result.take();
								replyList.add(reply);
								AnswerDao dao = new AnswerDao();
								dao.setReplyList(answerList.get(childPostion), replyList);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						Toast.makeText(getActivity(), "回复成功", Toast.LENGTH_SHORT).show();
						commentAdapter.clearList();
						commentAdapter.updateList(answerList);
						commentAdapter.notifyDataSetChanged();
						customDialog.dismiss();
						edt_reply.setText("");
					}
					break;
				}
			}
		});
		return customDialog;
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    if(listView == null) return;
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
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
	
	private void hintKbTwo() {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()) {
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}

