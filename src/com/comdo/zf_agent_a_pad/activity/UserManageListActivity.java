package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.UserMLAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.UserMLEntity;
import com.comdo.zf_agent_a_pad.fragment.Constants;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 用户列表
 */
public class UserManageListActivity extends BaseActivity implements IXListViewListener {

	private XListView Xlistview;
	private int page = 1;
	private int rows = Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private String ids[]=new String []{};
	private UserMLAdapter myAdapter;
	List<UserMLEntity> myList = new ArrayList<UserMLEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();

				if (myList.size() == 0) {
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true;
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	//�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		initView();
		getData();
	}

	private void initView() {

		new TitleMenuUtil(UserManageListActivity.this, "用户管理").show();
		myAdapter = new UserMLAdapter(UserManageListActivity.this, myList,new onClickListener());
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);
		Xlistview.setAdapter(myAdapter);
		
		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(UserManageListActivity.this,UserManageDetailActivity.class);
//				intent.putExtra("customersId", "80");
//				intent.putExtra("customersName", "dfaf");
				intent.putExtra("customersId", myList.get(position-2).getCustomersId());
				intent.putExtra("customersName", myList.get(position-2).getName());
				startActivity(intent);
			}
		});
	}
	private class onClickListener implements UserMLAdapter.OnClickListener {

		@Override
		public void onDeleteItem(int position) {
			deleteItem(position);
		} 
	}
	@Override
	public void onRefresh() {
		page = 1;
		myList.clear();
		getData();
	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page + 1;


			if (Tools.isConnect(getApplicationContext())) {
				onRefresh_number = false;
				getData();
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}
	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		page = 1;
		myList.clear();
		getData();
	}

	private void getData() {
		Config.userGetUser(this, Constants.TEST_CUSTOMER, page, rows,
				new HttpCallback<List<UserMLEntity>>(this) {
			@Override
			public void onSuccess(List<UserMLEntity> data) {
				if (null != data) {
					myList.addAll(data);
				}
				handler.sendEmptyMessage(0);
			}

			@Override
			public TypeToken<List<UserMLEntity>> getTypeToken() {
				return new TypeToken<List<UserMLEntity>>() {
				};
			}
		});
	}

	public void deleteItem(final int position) {
		ids=new String[1];
		ids[0]= myList.get(position).getCustomersId();
		Gson gson = new Gson();
		try {
			Config.userDelectAgentUser(this, new JSONArray(gson.toJson(ids)), Constants.TEST_CUSTOMER,
					new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					myList.remove(position);
					Toast.makeText(UserManageListActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
					myAdapter.notifyDataSetChanged();
				}

				@Override
				public TypeToken getTypeToken() {
					return null;
				}

			});
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
