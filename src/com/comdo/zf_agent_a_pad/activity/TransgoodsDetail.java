package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.adapter.TerminalAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.TerminalEntity;
import com.comdo.zf_agent_a_pad.entity.TransEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class TransgoodsDetail extends BaseActivity {
	private List<TerminalEntity> datatermin;
	private BaseAdapter terminalAdapter;
	private ScrollViewWithListView lv_list;
	private Button btn_showAll;
	private String[] Terminal_listString;
	private TextView tv_distribute_object, tv_date, tv_creator;
	private int iddd;
	public static boolean isTra = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transgoodsdetail);
		new TitleMenuUtil(TransgoodsDetail.this, "调货详情").show();
		init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getData();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isTra = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

	private void getData() {
		Config.transDetail(TransgoodsDetail.this, iddd,
				new HttpCallback<TransEntity>(TransgoodsDetail.this) {

					@Override
					public void onSuccess(TransEntity data) {
						if (data == null) {
							CommonUtil.toastShort(TransgoodsDetail.this,
									"服务端返回数据为空");
							return;
						}
						tv_distribute_object.setText(data.getFromname() + ">"
								+ data.getToname());
						tv_date.setText(data.getCreated_at());
						tv_creator.setText(data.getCreator());
						
						Terminal_listString = data.getTerminals_list().split(",");
						if (Terminal_listString.length <= 5) {
							for (int i = 0; i < Terminal_listString.length; i++) {
								// datatermin.get(i).setNumber(str[i]);
								datatermin.add(new TerminalEntity(i, Terminal_listString[i]));
							}
							btn_showAll.setVisibility(View.GONE);
						}else {
							for (int i = 0; i < 5; i++) {
								datatermin.add(new TerminalEntity(i, Terminal_listString[i]));
							}
							btn_showAll.setVisibility(View.VISIBLE);
						}

						
//						for (int i = 0; i < str.length; i++) {
//							// datatermin.get(i).setNumber(str[i]);
//							datatermin.add(new TerminalEntity(i, str[i]));
//						}
						// datatermin.addAll(data.getTerminal_list());
						lv_list.setAdapter(terminalAdapter);

					}

					@Override
					public TypeToken<TransEntity> getTypeToken() {
						// TODO Auto-generated method stub
						return new TypeToken<TransEntity>() {
						};
					}
				});

	}

	private void init() {
		btn_showAll = (Button) findViewById(R.id.btn_showAll);
		btn_showAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datatermin.clear();
				for (int i = 0; i < Terminal_listString.length; i++) {
					datatermin.add(new TerminalEntity(i, Terminal_listString[i]));
				}
				btn_showAll.setVisibility(View.GONE);
				lv_list.setAdapter(terminalAdapter);
			}
		});
		
		Intent intent = getIntent();
		iddd = intent.getIntExtra("id", 0);
		tv_distribute_object = (TextView) findViewById(R.id.tv_distribute_object);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_creator = (TextView) findViewById(R.id.tv_creator);
		datatermin = new ArrayList<TerminalEntity>();
		terminalAdapter = new TerminalAdapter(datatermin,
				getApplicationContext());
		lv_list = (ScrollViewWithListView) findViewById(R.id.lv_list);
		/*
		 * for(int i=0;i<6;i++){ datatermin.add(new TerminalEntity(i,
		 * "12345678")); } lv_list.setAdapter(terminalAdapter);
		 */
	}
}
