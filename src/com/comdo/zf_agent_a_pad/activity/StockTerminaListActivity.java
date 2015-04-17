package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.StockTerminaListAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.StockAgentEntity;
import com.comdo.zf_agent_a_pad.entity.StockTerminalEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class StockTerminaListActivity extends Activity implements IXListViewListener{
	private TextView hoitoryCount;
	private TextView openCount;
	private TextView lastPrepareTime;
	private TextView lastOpenTime;

	private XListView mListView;
	private LinearLayout eva_nodata;
	private StockTerminaListAdapter sTerminaListAdapter;
	private List<StockTerminalEntity> mEntities = new ArrayList<StockTerminalEntity>();
	private int goodId,paychannelId;

	private int page = 1;
	private int total = 0;
	private final int rows = 10;

	private static StockAgentEntity stockAgentEntity = new StockAgentEntity();

	public static StockAgentEntity getStockAgentEntity() {
		return stockAgentEntity;
	}
	public static void setStockAgentEntity(StockAgentEntity stockAgentEntity) {
		StockTerminaListActivity.stockAgentEntity = stockAgentEntity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stockterminal_list);

		goodId = getIntent().getExtras().getInt("goodId");
		paychannelId = getIntent().getExtras().getInt("paychannelId");

		new TitleMenuUtil(this, stockAgentEntity.getCompany_name()).show();
		initView();
		initData();
		loadData();
	}

	private void initView() {
		hoitoryCount = (TextView) findViewById(R.id.hoitoryCount);
		openCount = (TextView) findViewById(R.id.openCount);
		lastPrepareTime = (TextView) findViewById(R.id.lastPrepareTime);
		lastOpenTime = (TextView) findViewById(R.id.lastOpenTime);

		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		sTerminaListAdapter = new StockTerminaListAdapter(StockTerminaListActivity.this,mEntities);
		mListView.setAdapter(sTerminaListAdapter);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);
	}

	private void initData() {
		if (!StringUtil.isNull(stockAgentEntity.getHoitoryCount()+"")) 
			hoitoryCount.setText("配货总量"+"    "+stockAgentEntity.getHoitoryCount()+"");
		else 
			hoitoryCount.setText("配货总量"+"    "+"-");

		if (!StringUtil.isNull(stockAgentEntity.getOpenCount()+"")) 
			openCount.setText("已开通量"+"    "+stockAgentEntity.getOpenCount());
		else 
			openCount.setText("已开通量"+"    "+"-");

		if (!StringUtil.isNull(stockAgentEntity.getLastPrepareTime()+"")) 
			lastPrepareTime.setText("上次配货日期"+"    "+stockAgentEntity.getLastPrepareTime());
		else 
			lastPrepareTime.setText("上次配货日期"+"    "+"-");

		if (!StringUtil.isNull(stockAgentEntity.getLastOpenTime()+"")) 
			lastOpenTime.setText("上次开通日期"+"    "+stockAgentEntity.getLastOpenTime());
		else 
			lastOpenTime.setText("上次开通日期"+"    "+"-");
	}
	//列表数据
	private void loadData() {

		Config.stockTerminallist(this,stockAgentEntity.getAgent_id(),paychannelId,
				goodId, page , rows,
				new HttpCallback<Page<StockTerminalEntity>>(this) {
			@Override
			public void onSuccess(Page<StockTerminalEntity> data) {
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
				sTerminaListAdapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<Page<StockTerminalEntity>> getTypeToken() {
				return new TypeToken<Page<StockTerminalEntity>>() {
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
			Toast.makeText(StockTerminaListActivity.this, "no more data", Toast.LENGTH_SHORT).show();
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
