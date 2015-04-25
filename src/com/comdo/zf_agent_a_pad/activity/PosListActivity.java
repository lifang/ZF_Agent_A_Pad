package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.comdo.zf_agent_a_pad.adapter.PosAdapter;
import com.comdo.zf_agent_a_pad.adapter.PosAdapter1;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.PosEntity;
import com.comdo.zf_agent_a_pad.entity.Posport;
import com.comdo.zf_agent_a_pad.fragment.M_MianFragment;
import com.comdo.zf_agent_a_pad.fragment.Mwdxx;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.SetPopWindow;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PosListActivity extends Activity implements OnClickListener,
		IXListViewListener {
	private ImageView pos_select, img3;
	private Parcelable listState;
	private XListView Xlistview;
	private int page = 1;
	private int page1 = 1;
	private int rows = Config.ROWS;
	private LinearLayout eva_nodata, ll_xxyx, ll_mr, ll_updown, ll_pj;
	private boolean onRefresh_number = true;
	private PosAdapter myAdapter;
	private String keys = null;
	private TextView next_sure, tv_mr, tv_2, tv_3, tv_4;
	private Boolean isDown = true;
	private int orderType = 0;
	private EditText et_search;
	private int list_port = 1;
	private double maxPrice = 0, minPrice = 0;
	private boolean isSearch = false;
	private String keyword = "";
	List<PosEntity> myList = new ArrayList<PosEntity>();
	List<PosEntity> moreList = new ArrayList<PosEntity>();
	private ImageView im_sy, im_ghc, im_mess, im_wd;
	private TextView textsy, textghc, textmes, textwd;
	private LinearLayout set;
	public static int shoptype;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();

				if (myList.size() == 0) {
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				} else {
					Xlistview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				onRefresh_number = true;
				myAdapter.notifyDataSetChanged();
				myAdapter1.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: //
				Toast.makeText(getApplicationContext(),
						"no 3g or wifi content", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	private ImageView port1;
	private ImageView port2;
	private PosAdapter1 myAdapter1;
	private Intent i;
	private LinearLayout ll_listflag;
	private TextView tv_pg;
	private TextView tv_dg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poslist_activity);
		shoptype = 1;
		initView();
		Search();
	}

	private void initView() {

		tv_pg = (TextView) findViewById(R.id.tv_pg);
		tv_pg.setOnClickListener(this);
		tv_dg = (TextView) findViewById(R.id.tv_dg);
		tv_dg.setOnClickListener(this);

		if (!CheckRights.RIGHT_1 && CheckRights.RIGHT_2) {
			shoptype = 2;
			tv_pg.setTextColor(getResources().getColor(R.color.text292929));
			tv_dg.setTextColor(getResources().getColor(R.color.bgtitle));
			page = 1;
			myList.clear();
			Search();
		}
		textsy = (TextView) findViewById(R.id.textsy);
		textghc = (TextView) findViewById(R.id.textghc);
		textmes = (TextView) findViewById(R.id.textmes);
		textwd = (TextView) findViewById(R.id.textwd);

		im_sy = (ImageView) findViewById(R.id.laa1);
		im_sy.setOnClickListener(this);
		im_ghc = (ImageView) findViewById(R.id.igw);
		im_mess = (ImageView) findViewById(R.id.im_mess);
		im_mess.setOnClickListener(this);
		im_wd = (ImageView) findViewById(R.id.im_wd);
		im_wd.setOnClickListener(this);
		set = (LinearLayout) findViewById(R.id.set);

		set.setOnClickListener(this);
		ll_listflag = (LinearLayout) findViewById(R.id.ll_listflag);
		ll_mr = (LinearLayout) findViewById(R.id.ll_mr);
		ll_mr.setOnClickListener(this);
		ll_xxyx = (LinearLayout) findViewById(R.id.ll_xxyx);
		ll_xxyx.setOnClickListener(this);
		ll_updown = (LinearLayout) findViewById(R.id.ll_updown);
		ll_updown.setOnClickListener(this);
		ll_pj = (LinearLayout) findViewById(R.id.ll_pj);
		ll_pj.setOnClickListener(this);
		tv_mr = (TextView) findViewById(R.id.tv_mr);
		tv_2 = (TextView) findViewById(R.id.tv_2);
		tv_3 = (TextView) findViewById(R.id.tv_3);
		tv_4 = (TextView) findViewById(R.id.tv_4);
		img3 = (ImageView) findViewById(R.id.img3);
		et_search = (EditText) findViewById(R.id.et_search);
		et_search.setOnClickListener(this);
		pos_select = (ImageView) findViewById(R.id.pos_select);
		pos_select.setOnClickListener(this);

		myAdapter = new PosAdapter(PosListActivity.this, myList);
		myAdapter1 = new PosAdapter1(PosListActivity.this, myList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
		Xlistview.initHeaderAndFooter();
		Xlistview.setXListViewListener(this);
		Xlistview.setPullLoadEnable(true);
		Xlistview.setDivider(null);
		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (list_port == 1) {

				} else {
					Intent i = new Intent(PosListActivity.this,
							GoodDeatail.class);
					i.putExtra("id", myList.get(position - 1).getId());

					startActivity(i);
				}

			}
		});
		Xlistview.setAdapter(myAdapter1);
		changList();
	}

	private void changTabBg() {
		/*
		 * im_sy.setBackgroundResource(R.drawable.home);
		 * im_ghc.setBackgroundResource(R.drawable.good);
		 * im_mess.setBackgroundResource(R.drawable.message);
		 * im_wd.setBackgroundResource(R.drawable.mine);
		 * textsy.setTextColor(getResources().getColor(R.color.white));
		 * textghc.setTextColor(getResources().getColor(R.color.white));
		 * textmes.setTextColor(getResources().getColor(R.color.white));
		 * textwd.setTextColor(getResources().getColor(R.color.white));
		 */
		im_sy.setBackgroundResource(R.drawable.home2);
		im_ghc.setBackgroundResource(R.drawable.good1);
		im_mess.setBackgroundResource(R.drawable.message2);
		im_wd.setBackgroundResource(R.drawable.mine2);
		textsy.setTextColor(getResources().getColor(R.color.white));
		textghc.setTextColor(getResources().getColor(R.color.white));
		textmes.setTextColor(getResources().getColor(R.color.white));
		textwd.setTextColor(getResources().getColor(R.color.white));

	}

	private void changList() {
		port1 = (ImageView) findViewById(R.id.port_list1);
		port2 = (ImageView) findViewById(R.id.port_list2);
		port2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (list_port == 1) {
					listState = Xlistview.onSaveInstanceState();
					port2.setBackgroundResource(R.drawable.pos_px1);
					port1.setBackgroundResource(R.drawable.pos_pxf);
					Xlistview.setAdapter(myAdapter);
					Xlistview.onRestoreInstanceState(listState);
				}
				list_port = 0;
				ll_listflag.setVisibility(View.VISIBLE);
			}
		});
		port1.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				if (list_port == 0) {
					listState = Xlistview.onSaveInstanceState();
					port2.setBackground(getResources().getDrawable(
							R.drawable.pos_px));
					port1.setBackground(getResources().getDrawable(
							R.drawable.pos_pxf1));
					myAdapter1 = new PosAdapter1(PosListActivity.this, myList);
					Xlistview.setAdapter(myAdapter1);
					Xlistview.onRestoreInstanceState(listState);
				}
				list_port = 1;
				ll_listflag.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			PosListActivity.this.finish();
			break;
		case R.id.pos_select:
			i = new Intent(PosListActivity.this, PosPortActivity.class);
			startActivityForResult(i, 1);
			break;
		case R.id.et_search:
			Intent i = new Intent(PosListActivity.this, PosSearch1.class);
			startActivityForResult(i, 2);
			break;
		case R.id.ll_mr:
			orderType = 0;
			tv_mr.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			// getData();
			Search();
			Xlistview.setSelection(0);
			break;
		case R.id.ll_xxyx:
			orderType = 1;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			// getData();
			Search();
			Xlistview.setSelection(0);
			break;
		case R.id.ll_updown:
			if (isDown) {
				orderType = 2;
				isDown = false;
				img3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.ti_down));
			} else {
				orderType = 3;
				isDown = true;
				img3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.ti_up));
			}

			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			Search();
			Xlistview.setSelection(0);
			break;
		case R.id.ll_pj:
			orderType = 4;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bgtitle));
			myList.clear();
			Search();
			Xlistview.setSelection(0);
			break;
		case R.id.tv_pg:
			if (!CheckRights.RIGHT_1) {

				CommonUtil.toastShort(PosListActivity.this,
						R.string.right_not_match);
			} else {

				shoptype = 1;
				tv_pg.setTextColor(getResources().getColor(R.color.bgtitle));
				tv_dg.setTextColor(getResources().getColor(R.color.text292929));
				page = 1;
				myList.clear();
				Search();
			}
			break;
		case R.id.tv_dg:
			if (!CheckRights.RIGHT_2) {

				CommonUtil.toastShort(PosListActivity.this,
						R.string.right_not_match);
			} else {
				shoptype = 2;
				tv_pg.setTextColor(getResources().getColor(R.color.text292929));
				tv_dg.setTextColor(getResources().getColor(R.color.bgtitle));
				page = 1;
				myList.clear();
				Search();
			}
			break;
		case R.id.im_mess:
			Config.TABID = 3;
			PosListActivity.this.finish();
			break;
		case R.id.im_wd:
			Config.TABID = 4;
			PosListActivity.this.finish();
			break;
		case R.id.set:
			showSet();
			break;
		case R.id.laa1:
			Config.TABID = 1;
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		Xlistview.setPullLoadEnable(true);
		page = 1;
		myList.clear();
		Search();
	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page + 1;

			onRefresh_number = false;
			// getData();
			Search();

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
		// getData();
		Search();
	}

	private void showSet() {
		SetPopWindow set = new SetPopWindow(this);
		set.showAtLocation(findViewById(R.id.main), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 0:
			myList.clear();
			page = 1;
			// getData();
			Search();

			break;
		case 1:
			if (data != null) {
				System.out.println("进入条件选择回调···");
				minPrice = data.getIntExtra("minPrice", 0);
				maxPrice = data.getIntExtra("maxPrice", 1000000);
				System.out.println(maxPrice + "进入条件选择回调···" + minPrice);
				myList.clear();
				// getData();
				Search();
			}

			break;
		case 2:
			if (data != null) {
				String a = data.getStringExtra("text");
				// keys=a;
				et_search.setText(a);
				page = 1;
				keyword = a;
				myList.clear();
				Search();
				Xlistview.setSelection(0);
			}

			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void Search() {

		Config.PostSearch(getApplicationContext(),
				MyApplication.NewUser.getAgentId(), shoptype, keyword, 1, 12,
				page, orderType, new HttpCallback<Page<PosEntity>>(this) {
					@Override
					public void onSuccess(Page<PosEntity> data) {
						if (myList.size() != 0 && data.getList().size() == 0) {
							Xlistview.getmFooterView().setState2(2);
							Toast.makeText(getApplicationContext(), "没有更多数据!",
									1000).show();
							Xlistview.setPullLoadEnable(false);
						}

						myList.addAll(data.getList());

						handler.sendEmptyMessage(0);

					}

					@Override
					public TypeToken<Page<PosEntity>> getTypeToken() {
						return new TypeToken<Page<PosEntity>>() {
						};
					}

				});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Posport.brands_id = null;
		Posport.category = null;
		Posport.pay_channel_id = null;
		Posport.pay_card_id = null;
		Posport.trade_type_id = null;
		Posport.sale_slip_id = null;
		Posport.tDate = null;
		Posport.has_purchase = 0;
		Posport.minPrice = 0;
		Posport.maxPrice = 0;
	}
}
