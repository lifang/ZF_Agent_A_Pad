package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.OrderAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AdressEntity;
import com.comdo.zf_agent_a_pad.entity.OrderEntity;
import com.comdo.zf_agent_a_pad.entity.PostPortEntity;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class OrderList extends Activity implements IXListViewListener,
		OnClickListener, OnEditorActionListener {
	private XListView Xlistview;
	private boolean onRefresh_number = true;
	private OrderAdapter myAdapter;
	private int page = 1;
	public static String type = "5";
	private String search = "";
	private String q = "";
	List<OrderEntity> myList = new ArrayList<OrderEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				onRefresh_number = true;
				if (myList.size() == 0) {
					Toast.makeText(OrderList.this, "暂无数据", 1000).show();
				}
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(OrderList.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(OrderList.this, "网络连接错误", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(OrderList.this, " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private TextView tv_pg;
	private TextView tv_dg;
	private ImageView all_good;
	private LinearLayout ll_isshow;
	private TextView pg;
	private TextView dg;
	private Spinner sp;
	private EditText et;
	private LinearLayout ll_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist);
		initView();
		type = "5";
		q = "";
		// getData();
	}

	@SuppressLint("NewApi")

	private void initView() {
		ll_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		ll_back.setOnClickListener(this);
		et = (EditText) findViewById(R.id.ed_search);
		et.setOnEditorActionListener(this);
		pg = (TextView) findViewById(R.id.pg);
		pg.setOnClickListener(this);
		dg = (TextView) findViewById(R.id.dg);
		dg.setOnClickListener(this);
		ll_isshow = (LinearLayout) findViewById(R.id.ll_isshow);
		if (!type.equals("5")) {
			ll_isshow.setVisibility(View.VISIBLE);
		}
		all_good = (ImageView) findViewById(R.id.AllGood);
		all_good.setOnClickListener(this);
		sp = (Spinner) findViewById(R.id.spinner);
		final String arr[] = new String[] { "全部", "未付款", "已付款", "已发货", "已评价",
				"已取消", "交易关闭" };

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arr);
		sp.setAdapter(arrayAdapter);
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				q = position == 0 ? "" : position + "";
				page = 1;
				myList.clear();
				getData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		tv_pg = (TextView) findViewById(R.id.tv_pg);
		tv_pg.setOnClickListener(this);
		tv_dg = (TextView) findViewById(R.id.tv_dg);
		tv_dg.setOnClickListener(this);

		myAdapter = new OrderAdapter(OrderList.this, myList);
		Xlistview = (XListView) findViewById(R.id.x_listview);

		Xlistview.initHeaderAndFooter();
		Xlistview.setXListViewListener(this);
		Xlistview.setPullLoadEnable(true);
		Xlistview.setDivider(null);
		Xlistview.setAdapter(myAdapter);
		if (!CheckRights.RIGHT_1 && CheckRights.RIGHT_2) {
			sp.setSelection(0);
			tv_dg.setBackground(getResources().getDrawable(R.drawable.tab_bg));
			tv_pg.setBackgroundColor(getResources().getColor(R.color.bggray));
			type = "3";
			myList.clear();
			page = 1;
			q = "";
			ll_isshow.setVisibility(View.VISIBLE);
			Xlistview.setPullLoadEnable(true);
			getData();
		}
	}

	@Override
	public void onRefresh() {
		Xlistview.setPullLoadEnable(true);
		page = 1;
		myList.clear();
		getData();
	}

	public void DataChange() {
		page = 1;
		myList.clear();
		getData();

	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page + 1;

			if (Tools.isConnect(OrderList.this)) {
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

	private void getData() {

		Config.GetOrderList(OrderList.this, MyApplication.NewUser
				.getAgentUserId(), type, et.getText().toString(), q, page,
				Config.ROWS,
				new HttpCallback<Page<OrderEntity>>(OrderList.this) {

					@Override
					public void onSuccess(Page<OrderEntity> data) {
						if (myList.size() != 0 && data.getList().size() == 0) {
							Xlistview.getmFooterView().setState2(2);
							Toast.makeText(getApplicationContext(), "没有更多数据!",
									1000).show();
							Xlistview.setPullLoadEnable(false);
						}
						myList.addAll(data.getList());
						myAdapter.notifyDataSetChanged();
						handler.sendEmptyMessage(0);
					}

					@Override
					public TypeToken getTypeToken() {
						return new TypeToken<Page<OrderEntity>>() {
						};
					}
				});

	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pg:

			dg.setTextColor(getResources().getColor(R.color.text292929));
			pg.setTextColor(getResources().getColor(R.color.bgtitle));
			type = "5";
			myList.clear();
			page = 1;
			getData();
			break;
		case R.id.dg:
			dg.setTextColor(getResources().getColor(R.color.bgtitle));
			pg.setTextColor(getResources().getColor(R.color.text292929));
			type = "4";
			myList.clear();
			page = 1;
			getData();
			break;
		case R.id.tv_pg:
			if (!CheckRights.RIGHT_1) {

				CommonUtil.toastShort(OrderList.this, R.string.right_not_match);
			} else {
				sp.setSelection(0);
				tv_pg.setBackground(getResources().getDrawable(
						R.drawable.tab_bg));
				tv_dg.setBackgroundColor(getResources()
						.getColor(R.color.bggray));
				type = "5";
				ll_isshow.setVisibility(View.GONE);
				myList.clear();
				page = 1;
				q = "";
				Xlistview.setPullLoadEnable(true);
				getData();
			}
			break;
		case R.id.tv_dg:
			if (!CheckRights.RIGHT_2) {

				CommonUtil.toastShort(OrderList.this, R.string.right_not_match);
			} else {
				sp.setSelection(0);
				tv_dg.setBackground(getResources().getDrawable(
						R.drawable.tab_bg));
				tv_pg.setBackgroundColor(getResources()
						.getColor(R.color.bggray));
				type = "3";
				myList.clear();
				page = 1;
				q = "";
				ll_isshow.setVisibility(View.VISIBLE);
				Xlistview.setPullLoadEnable(true);
				getData();
			}
			break;
		case R.id.AllGood:
			Intent i = new Intent(OrderList.this, PosListActivity.class);
			startActivity(i);
			break;
		case R.id.titleback_linear_back:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			search = et.getText().toString();
			myList.clear();
			page = 1;
			getData();

			return false;
		}
		return false;

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onRestart() {

		super.onRestart();
		myList.clear();
		page = 1;
		getData();
	}
}
