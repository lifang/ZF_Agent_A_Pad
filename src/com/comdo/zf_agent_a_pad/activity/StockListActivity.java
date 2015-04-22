package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.StockListAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.StockEntity;
import com.comdo.zf_agent_a_pad.fragment.Constants;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class StockListActivity extends Activity implements IXListViewListener{
	private XListView mListView;
	private LinearLayout eva_nodata;
	private StockListAdapter stockListAdapter;
	private List<StockEntity> mEntities = new ArrayList<StockEntity>();

	//商品更名的dialog
	private Dialog dialog;
	private Button dialog_button_cancel, dialog_button_ok;
	private EditText dialog_text;
	private String computer_name;

	private int page = 1;
	private int total = 0;
	private final int rows = 10;

	private int toInfoPosition = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_stock_list);
		new TitleMenuUtil(this, "库存管理").show();
		initView();

		loadData();
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		stockListAdapter = new StockListAdapter(StockListActivity.this,mEntities,
				new onClickListener());
		mListView.setAdapter(stockListAdapter);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				toInfoPosition = position-2;
				StockInfoListActivity.setStockEntity(mEntities.get(position-2));

				Intent intent = new Intent(StockListActivity.this,StockInfoListActivity.class);
				startActivityForResult(intent, 101);
			}
		});
	}
	private class onClickListener implements StockListAdapter.OnClickListener {

		@Override
		public void onChangeName(int position) {
			setDialog(position);
		}


	}
	private void setDialog(final int position) {

		dialog = new Dialog(this, R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_edit_stock);
		dialog.setCancelable(false);
		dialog.show();

		dialog_button_ok = (Button) dialog.findViewById(R.id.dialog_button_ok);
		dialog_button_cancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);
		dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);

		dialog.setTitle("商品更名");
		dialog_button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				computer_name = "";
				dialog.dismiss();
				MyApplication.hideSoftKeyboard(StockListActivity.this);
			}
		});
		dialog_button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!dialog_text.getText().toString().equals("")) {
					computer_name = dialog_text.getText().toString();
					MyApplication.hideSoftKeyboard(StockListActivity.this);
					stockRename(position);
				} else {
					Toast.makeText(StockListActivity.this, "商品名称不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	//商品更名
	protected void stockRename(final int position) {

		Config.stockRename(this,Constants.TEST_CUSTOMER,mEntities.get(position).getGood_id(),
				computer_name,new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				dialog.dismiss();
				mEntities.get(position).setGoodname(computer_name);
				stockListAdapter.notifyDataSetChanged();
				Toast.makeText(StockListActivity.this, "商品更名成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}
	//列表数据
	private void loadData() {

		Config.stockList(this, Constants.TEST_CUSTOMER, page , rows,
				new HttpCallback<Page<StockEntity>>(this) {
			@Override
			public void onSuccess(Page<StockEntity> data) {
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
				stockListAdapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<Page<StockEntity>> getTypeToken() {
				return new TypeToken<Page<StockEntity>>() {
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
			Toast.makeText(StockListActivity.this, "no more data", Toast.LENGTH_SHORT).show();
		} else {
			loadData();
		}

	}
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(Tools.getHourAndMin());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 101) {
				if (data != null) {
					String result = data.getExtras().getString("reName");
					if (!StringUtil.isNull(result)) {
						mEntities.get(toInfoPosition).setGoodname(result);
						stockListAdapter.notifyDataSetChanged();
					}
				}
			}
		}
	}
}
