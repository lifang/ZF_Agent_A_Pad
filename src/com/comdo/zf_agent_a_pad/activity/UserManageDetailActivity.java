package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.UserMDAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.UserMDEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class UserManageDetailActivity extends Activity implements IXListViewListener{
	private LinearLayout eva_nodata;
	private XListView Xlistview;
	private UserMDAdapter myAdapter;
	private int page = 1;
	private int rows = Config.ROWS;
	private String userName;
	private int customerId;
	private boolean onRefresh_number = true;
	List<UserMDEntity> myList = new ArrayList<UserMDEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_manage_detail);

		userName = getIntent().getExtras().getString("customersName");
		customerId = Integer.valueOf(getIntent().getExtras().getString("customersId"));
		new TitleMenuUtil(this, userName).show();
		initView();
		loadData();
	}

	private void initView() {

		myAdapter = new UserMDAdapter(this, myList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);
		Xlistview.setAdapter(myAdapter);
	}

	private void loadData() {
		Config.userGetTerminals(this, customerId, page, rows,
				new HttpCallback<List<UserMDEntity>>(this) {
			@Override
			public void onSuccess(List<UserMDEntity> data) {
				if (null != data) {
					myList.addAll(data);
				}

				if (myList.size() == 0) {
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					Xlistview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				if (data.size()==0) {
					onRefresh_number = false;
				}else {
					onRefresh_number = true;
				}
				onLoad();
				myAdapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<List<UserMDEntity>> getTypeToken() {
				return new TypeToken<List<UserMDEntity>>() {
				};
			}
		});
	}

	@Override
	public void onRefresh() {
		page = 1;
		myList.clear();
		Xlistview.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page + 1;

			if (Tools.isConnect(getApplicationContext())) {
				onRefresh_number = false;
				loadData();
			} else {
				onRefresh_number = true;
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Xlistview.setPullLoadEnable(false);
			Xlistview.stopLoadMore();
			Toast.makeText(getApplicationContext(), "no more data",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}
}
