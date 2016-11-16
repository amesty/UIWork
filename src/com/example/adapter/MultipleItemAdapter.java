package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.chat.AVImClientManager;
import com.example.event.ImTypeMessageResendEvent;
import com.example.event.LeftChatItemClickEvent;
import com.example.model.Problem;
import com.example.uiwork.R;
import com.example.viewholder.AVCommonViewHolder;
import com.example.viewholder.LeftTextHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by wli on 15/8/13. 聊天的 Adapter，此处还有可优化的地方，稍后考虑一下提取出公共的 adapter
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final int ITEM_LEFT_TEXT = 0;
	private final int ITEM_RIGHT_TEXT = 1;

	// 时间间隔最小为十分钟
	private final long TIME_INTERVAL = 10 * 60 * 1000;

	private List<AVIMMessage> messageList = new ArrayList<AVIMMessage>();

	public MultipleItemAdapter() {
	}

	public void setMessageList(List<AVIMMessage> messages) {
		messageList.clear();
		if (null != messages) {
			messageList.addAll(messages);
		}
	}

	public void addMessageList(List<AVIMMessage> messages) {
		messageList.addAll(0, messages);
	}

	public void addMessage(AVIMMessage message) {
		messageList.addAll(Arrays.asList(message));
	}

	public AVIMMessage getFirstMessage() {
		if (null != messageList && messageList.size() > 0) {
			return messageList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == ITEM_LEFT_TEXT) {
			return new LeftTextHolder(parent.getContext(), parent);
		} else if (viewType == ITEM_RIGHT_TEXT) {
			return new RightTextHolder(parent.getContext(), parent);
		} else {
			// TODO
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((AVCommonViewHolder) holder).bindData(messageList.get(position));
		if (holder instanceof LeftTextHolder) {
			((LeftTextHolder) holder).showTimeView(shouldShowTime(position));
		} else if (holder instanceof RightTextHolder) {
			((RightTextHolder) holder).showTimeView(shouldShowTime(position));
		}
	}

	@Override
	public int getItemViewType(int position) {
		AVIMMessage message = messageList.get(position);
		if (message.getFrom().equals(AVImClientManager.getInstance().getClientId())) {
			return ITEM_RIGHT_TEXT;
		} else {
			return ITEM_LEFT_TEXT;
		}
	}

	@Override
	public int getItemCount() {
		return messageList.size();
	}

	private boolean shouldShowTime(int position) {
		if (position == 0) {
			return true;
		}
		long lastTime = messageList.get(position - 1).getTimestamp();
		long curTime = messageList.get(position).getTimestamp();
		return curTime - lastTime > TIME_INTERVAL;
	}

	/**
	 * Created by wli on 15/8/13. * 鑱婂ぉ鏃跺眳鍙崇殑鏂囨湰 holder
	 */
	public class RightTextHolder extends AVCommonViewHolder {

		TextView timeView;
		TextView contentView;
		TextView nameView;
		FrameLayout statusView;
		ProgressBar loadingBar;
		ImageView errorView;
		AVIMMessage message;

		public RightTextHolder(Context context, ViewGroup root) {
			super(context, root, R.layout.chat_right_text_view);
		}

		@OnClick(R.id.chat_right_text_tv_error)
		public void onErrorClick(View view) {
			ImTypeMessageResendEvent event = new ImTypeMessageResendEvent();
			event.message = message;
			EventBus.getDefault().post(event);
		}

		@Override
		public void bindData(Object o) {
			message = (AVIMMessage) o;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time = dateFormat.format(message.getTimestamp());

			String content = getContext().getString(R.string.unspport_message_type);
			;
			if (message instanceof AVIMTextMessage) {
				content = ((AVIMTextMessage) message).getText();
			}
			timeView = (TextView) itemView.findViewById(R.id.chat_right_text_tv_time);
			contentView = (TextView) itemView.findViewById(R.id.chat_right_text_tv_content);
			nameView = (TextView) itemView.findViewById(R.id.chat_right_text_tv_name);
			statusView = (FrameLayout) itemView.findViewById(R.id.chat_right_text_layout_status);
			loadingBar = (ProgressBar) itemView.findViewById(R.id.chat_right_text_progressbar);
			errorView = (ImageView) itemView.findViewById(R.id.chat_right_text_tv_error);

			if (contentView == null) {
				System.out.println("contentView null");
			}
			contentView.setText(content);
			timeView.setText(time);
			nameView.setText(message.getFrom());

			if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == message.getMessageStatus()) {
				errorView.setVisibility(View.VISIBLE);
				loadingBar.setVisibility(View.GONE);
				statusView.setVisibility(View.VISIBLE);
			} else if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSending == message.getMessageStatus()) {
				errorView.setVisibility(View.GONE);
				loadingBar.setVisibility(View.VISIBLE);
				statusView.setVisibility(View.VISIBLE);
			} else {
				statusView.setVisibility(View.GONE);
			}
		}

		public void showTimeView(boolean isShow) {
			timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
		}
	}

	public class LeftTextHolder extends AVCommonViewHolder {

		TextView timeView;
		TextView contentView;
		TextView nameView;

		public LeftTextHolder(Context context, ViewGroup root) {
			super(context, root, R.layout.chat_left_text_view);
		}

		@OnClick({ R.id.chat_left_text_tv_content, R.id.chat_left_text_tv_name })
		public void onNameClick(View view) {
			LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
			clickEvent.userId = nameView.getText().toString();
			EventBus.getDefault().post(clickEvent);
		}

		@Override
		public void bindData(Object o) {
			AVIMMessage message = (AVIMMessage) o;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			String time = dateFormat.format(message.getTimestamp());

			String content = getContext().getString(R.string.unspport_message_type);
			if (message instanceof AVIMTextMessage) {
				content = ((AVIMTextMessage) message).getText();
			}
			timeView = (TextView) itemView.findViewById(R.id.chat_left_text_tv_time);
			contentView = (TextView) itemView.findViewById(R.id.chat_left_text_tv_content);
			nameView = (TextView) itemView.findViewById(R.id.chat_left_text_tv_name);
			
			contentView.setText(content);
			timeView.setText(time);
			nameView.setText(message.getFrom());
		}

		public void showTimeView(boolean isShow) {
			timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
		}
	}
}