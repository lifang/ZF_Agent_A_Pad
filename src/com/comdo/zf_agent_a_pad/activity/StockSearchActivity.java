package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.StockSearchAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.StockAgentEntity;
import com.comdo.zf_agent_a_pad.entity.StockEntity;
import com.comdo.zf_agent_a_pad.fragment.Constants;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class StockSearchActivity extends Activity implements OnClickListener,IXListViewListener{

	private LinearLayout titleback_linear_back;
	private EditText searchEditText;
	private LinearLayout linear_deletename;
	private TextView next_cancel;

	private XListView mListView;
	private LinearLayout eva_nodata;
	private StockSearchAdapter<StockAgentEntity> myAdapter;
	private List<StockAgentEntity> mEntities = new ArrayList<StockAgentEntity>();
	private static StockEntity stockEntity = new StockEntity();

	private int page = 1;
	private int total = 0;
	private final int rows = 10;

	public static StockEntity getStockEntity() {
		return stockEntity;
	}

	public static void setStockEntity(StockEntity stockEntity) {
		StockSearchActivity.stockEntity = stockEntity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stocksearch_list);

		initView();
	}

	private void initView() {
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		linear_deletename = (LinearLayout) findViewById(R.id.linear_deletename);
		next_cancel = (TextView) findViewById(R.id.next_cancel);

		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		myAdapter = new StockSearchAdapter<StockAgentEntity>(StockSearchActivity.this,mEntities);
		mListView.setAdapter(myAdapter);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);

		titleback_linear_back.setOnClickListener(this);
		linear_deletename.setOnClickListener(this);
		next_cancel.setOnClickListener(this);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				StockTerminaListActivity.setStockAgentEntity(mEntities.get(position-2));

				Intent intent = new Intent(StockSearchActivity.this,StockTerminaListActivity.class);
				intent.putExtra("goodId", stockEntity.getGood_id());
				intent.putExtra("paychannelId", stockEntity.getPaychannel_id());
				startActivity(intent);
			}
		});
		
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					linear_deletename.setVisibility(View.VISIBLE);
				} else {
					linear_deletename.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!StringUtil.isNull(searchEditText.getText().toString())) {
					page = 1;
					mEntities.clear();
					mListView.setPullLoadEnable(true);
					loadData();	
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			finish();
			break;
		case R.id.linear_deletename:
			searchEditText.setText("");
			break;
		case R.id.next_cancel:
			finish();
			break;
		default:
			break;
		}
	}
	//列表数据
	private void loadData() {
		String searchKey = searchEditText.getText().toString().trim();
		Config.stockInfo(this,Constants.TEST_CUSTOMER,stockEntity.getPaychannel_id(),
				stockEntity.getGood_id(),searchKey, page , rows,
				new HttpCallback<Page<StockAgentEntity>>(this) {
			@Override
			public void onSuccess(Page<StockAgentEntity> data) {
				if (null != data.getList()) {
					mEntities.addAll(data.getList());
				}
				page++;
				total = data.getTotal();
				onLoad();

				if(mEntities.size()==0){
					mListView.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					mListView.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				myAdapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<Page<StockAgentEntity>> getTypeToken() {
				return new TypeToken<Page<StockAgentEntity>>() {
				};
			}
		});
	}
	@Override
	public void onRefresh() {
		page = 1;
		mEntities.clear();
		mListView.setPullLoadEnable(true);
		loadData();		
	}

	@Override
	public void onLoadMore() {
		if (mEntities.size() >= total) {
			mListView.setPullLoadEnable(false);
			mListView.stopLoadMore();
			Toast.makeText(StockSearchActivity.this, "no more data", Toast.LENGTH_SHORT).show();
		} else {
			loadData();
		}		
	}
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(Tools.getHourAndMin());
	}
}
