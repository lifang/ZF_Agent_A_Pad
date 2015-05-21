package com.comdo.zf_agent_a_pad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.StaffEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class StallmanagerDetail extends BaseActivity {
	private TextView tv_loginid, tv_name, tv_paw;
	private CheckBox cb_pigou, cb_daigou, cb_manager_zhongduan, cb_quary,
			cb_manager_agent, cb_manager_user, cb_manager_worker,
			cb_manager_Addres;
	private int customerId;
	private String rolesStr;
	private String[] str = new String[] {};
	private Handler myHandler;
	private Button btn_edit;
	private int agentId = MyApplication.NewUser.getAgentId();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staffdetail);
		init();

	}

	@Override
	protected void onStart() {
		super.onStart();
		getData();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					cb_pigou.setChecked(false);
					cb_daigou.setChecked(false);
					cb_manager_zhongduan.setChecked(false);
					cb_quary.setChecked(false);
					cb_manager_agent.setChecked(false);
					cb_manager_user.setChecked(false);
					cb_manager_worker.setChecked(false);
					cb_manager_Addres.setChecked(false);
					for (int i = 0; i < str.length; i++) {
						new TitleMenuUtil(StallmanagerDetail.this, tv_loginid
								.getText().toString()).show();
						if (str[i].equals("1")) {
							cb_pigou.setChecked(true);
						} else if (str[i].equals("2")) {
							cb_daigou.setChecked(true);
						} else if (str[i].equals("3")) {
							cb_manager_zhongduan.setChecked(true);
						} else if (str[i].equals("4")) {
							cb_quary.setChecked(true);
						} else if (str[i].equals("5")) {
							cb_manager_agent.setChecked(true);
						} else if (str[i].equals("6")) {
							cb_manager_user.setChecked(true);
						} else if (str[i].equals("7")) {
							cb_manager_worker.setChecked(true);
						} else if (str[i].equals("8")) {
							cb_manager_Addres.setChecked(true);
						}
					}
					break;

				default:
					break;
				}
			};
		};
	}

	private void getData() {
		Config.getStallDetail(StallmanagerDetail.this, customerId, agentId,
				new HttpCallback<StaffEntity>(StallmanagerDetail.this) {

					@Override
					public void onSuccess(StaffEntity data) {
						tv_name.setText(data.getName());
						tv_loginid.setText(data.getLoginId());
						tv_paw.setText(data.getCreatedAt());
						rolesStr = data.getRolesStr();
						str = rolesStr.split(",");
						myHandler.sendEmptyMessage(0);
					}

					@Override
					public TypeToken<StaffEntity> getTypeToken() {
						return new TypeToken<StaffEntity>() {
						};
					}
				});

	}

	private void init() {
		Intent intent = getIntent();
		customerId = intent.getIntExtra("idd", 0);
		tv_loginid = (TextView) findViewById(R.id.tv_loginid);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_paw = (TextView) findViewById(R.id.tv_paw);
		cb_pigou = (CheckBox) findViewById(R.id.cb_pigou);
		cb_daigou = (CheckBox) findViewById(R.id.cb_daigou);
		cb_manager_zhongduan = (CheckBox) findViewById(R.id.cb_manager_zhongduan);
		cb_quary = (CheckBox) findViewById(R.id.cb_quary);
		cb_manager_agent = (CheckBox) findViewById(R.id.cb_manager_agent);
		cb_manager_user = (CheckBox) findViewById(R.id.cb_manager_user);
		cb_manager_worker = (CheckBox) findViewById(R.id.cb_manager_worker);
		cb_manager_Addres = (CheckBox) findViewById(R.id.cb_manager_Addres);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(StallmanagerDetail.this,
						EditStallmanager.class);
				intent.putExtra("loginid", tv_loginid.getText().toString());
				intent.putExtra("name", tv_name.getText().toString());
				intent.putExtra("role", rolesStr);
				// Log.e("role", str);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}
}
