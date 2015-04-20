package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.OrderAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AdressEntity;
import com.comdo.zf_agent_a_pad.entity.OrderEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class OrderList extends Activity implements IXListViewListener{
	private XListView Xlistview;
	private boolean onRefresh_number = true;
	private OrderAdapter myAdapter;
	private int page=1;
	List<OrderEntity> myList = new ArrayList<OrderEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				if (myList.size() == 0) {

					// norecord_text_to.setText("锟斤拷没锟斤拷锟斤拷氐锟斤拷锟狡?);

					Xlistview.setVisibility(View.GONE);
					//eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true;
				
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlist);
		initView();
		getData();
	}
	private void initView() {
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
		Config.GetOrderList(OrderList.this, 80,"1", new HttpCallback<Page<OrderEntity>>(OrderList.this) {

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

}
