package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.fragment.Mwdxx;
import com.comdo.zf_agent_a_pad.util.Config;

import com.comdo.zf_agent_a_pad.util.DialogUtil1;
import com.comdo.zf_agent_a_pad.util.DialogUtil1.CallBackChange;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MsgDetail extends BaseActivity {
	private TextView tv_title, tv_time, tv_content, next_sure;
	private int id = 0;
	private String title = "";
	private String time = "";
	private String content = "";
	private Handler myHandler;
	private int agentUserId = MyApplication.NewUser.getAgentUserId();

	// public static boolean isdelect=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msgdetail);
		new TitleMenuUtil(MsgDetail.this, "消息详情").show();
		init();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_content = (TextView) findViewById(R.id.tv_content);
		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setText("");
		next_sure.setBackgroundResource(R.drawable.delete_sure);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		getMsgdetail();
		/*
		 * String title=intent.getStringExtra("title"); String
		 * time=intent.getStringExtra("time"); String
		 * content=intent.getStringExtra("content");
		 */
		/*
		 * tv_title.setText(title); tv_time.setText(time);
		 * tv_content.setText(content);
		 */
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 0) {
					tv_title.setText(title);
					tv_time.setText(time);
					tv_content.setText(content);
				}
			};
		};
		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog ddd = new DialogUtil1(MsgDetail.this, "确认删除？")
						.getCheck(new CallBackChange() {

							@Override
							public void change() {
								delectOne();
							}

						});
				ddd.show();

			}
		});
	}

	private void getMsgdetail() {
		Config.GetMsgDetail(MsgDetail.this, id, agentUserId,
				new HttpCallback<MessageEntity>(MsgDetail.this) {

					@Override
					public void onSuccess(MessageEntity data) {
						title = data.getTitle();
						time = data.getCreate_at();
						content = data.getContent();
						myHandler.sendEmptyMessage(0);
					}

					@Override
					public TypeToken<MessageEntity> getTypeToken() {
						// TODO Auto-generated method stub
						return new TypeToken<MessageEntity>() {
						};
					}
				});

	}

	protected void delectOne() {
		Config.DelectOneMsg(MsgDetail.this, id, agentUserId, new HttpCallback(
				MsgDetail.this) {

			@Override
			public void onSuccess(Object data) {
				CommonUtil.toastShort(MsgDetail.this, "删除成功");
				finish();

			}

			@Override
			public TypeToken getTypeToken() {

				return null;
			}
		});

	}
}
