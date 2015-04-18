package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.OrderAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AdressEntity;
import com.comdo.zf_agent_a_pad.entity.OrderEntity;
import com.comdo.zf_agent_a_pad.entity.PostPortEntity;
import com.comdo.zf_agent_a_pad.util.Config;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderList extends Activity implements IXListViewListener,OnClickListener{
	private XListView Xlistview;
	private boolean onRefresh_number = true;
	private OrderAdapter myAdapter;
	private int page=1;
	private String type="1";
	private String search="";
	private String q="";
	List<OrderEntity> myList = new ArrayList<OrderEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				onRefresh_number = true;
				if(myList.size()==0){
					Toast.makeText(OrderList.this, "暂无数据", 1000).show();
				}
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(OrderList.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2:
				Toast.makeText(OrderList.this, "网络连接错误",
						Toast.LENGTH_SHORT).show();
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist);
		initView();
		getData();
	}
	private void initView() {
		all_good = (ImageView)findViewById(R.id.AllGood);
		all_good.setOnClickListener(this);
		Spinner sp = (Spinner) findViewById(R.id.spinner);
		final String arr[] = new String[] { "全部", "未付款" ,"已付款","已发货","已评价","已取消","交易关闭"};

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arr);
		sp.setAdapter(arrayAdapter);
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				q=position==0?"":position+"";		
				page=1;
				myList.clear();
				getData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		tv_pg = (TextView)findViewById(R.id.tv_pg);
		tv_pg.setOnClickListener(this);
		tv_dg = (TextView)findViewById(R.id.tv_dg);
		tv_dg.setOnClickListener(this);
		myAdapter=new OrderAdapter(OrderList.this, myList);
		Xlistview = (XListView)findViewById(R.id.x_listview);
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);
		Xlistview.setAdapter(myAdapter);
		
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
		Config.GetOrderList(OrderList.this, 80,type,search,q,page,Config.ROWS, new HttpCallback<Page<OrderEntity>>(OrderList.this) {

			@Override
			public void onSuccess(Page<OrderEntity> data) {
				myList.addAll(data.getList());				
				myAdapter.notifyDataSetChanged();	
				handler.sendEmptyMessage(0);
			}
			
			@Override
			public TypeToken getTypeToken() {
				return new TypeToken<Page<OrderEntity>>(){
				};
			}
        });
	
		
	}
	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}
	@SuppressLint("NewApi") @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_pg:
			tv_pg.setBackground(getResources().getDrawable(R.drawable.tab_bg));
			tv_dg.setBackgroundColor(getResources().getColor(R.color.bggray));
			type="1";
			myList.clear();
			page=1;
			getData();
			break;
		case R.id.tv_dg:
			tv_dg.setBackground(getResources().getDrawable(R.drawable.tab_bg));
			tv_pg.setBackgroundColor(getResources().getColor(R.color.bggray));
			type="3";
			myList.clear();
			page=1;
			getData();
			break;
			case R.id.AllGood:
				Intent i=new Intent(OrderList.this,PosListActivity.class);
				startActivity(i);
				break;
		default:
			break;
		}
		
	}

}
