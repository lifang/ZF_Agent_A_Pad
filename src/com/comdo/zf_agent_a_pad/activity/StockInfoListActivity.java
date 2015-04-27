package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.StockInfoListAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.StockAgentEntity;
import com.comdo.zf_agent_a_pad.entity.StockEntity;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StockInfoListActivity extends Activity implements OnClickListener,IXListViewListener{
	private LinearLayout titleback_linear_back;

	private ImageView evevt_img;
	private TextView title;
	private TextView brandNum;
	private TextView paychannel;
	private TextView hoitoryCount;
	private TextView openCount;
	private TextView agentCount;
	private TextView totalCount;
	private Button changeName;

	private LinearLayout search_layout;
	private XListView mListView;
	private LinearLayout eva_nodata;
	private StockInfoListAdapter stockInfoAdapter;
	private List<StockAgentEntity> mEntities = new ArrayList<StockAgentEntity>();

	private static StockEntity stockEntity = new StockEntity();
	DisplayImageOptions options = MyApplication.getDisplayOption();

	//商品更名的dialog
	private Dialog dialog;
	private Button dialog_button_cancel, dialog_button_ok;
	private EditText dialog_text;
	private String computer_name;

	private int page = 1;
	private int total = 0;
	private final int rows = 10;
	private boolean isChangeName = false;

	public static StockEntity getStockEntity() {
		return stockEntity;
	}

	public static void setStockEntity(StockEntity stockEntity) {
		StockInfoListActivity.stockEntity = stockEntity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stockinfo_list);

		stockEntity=(StockEntity) getIntent().getSerializableExtra("StockEntity");
		
		new TitleMenuUtil(this, "库存详情").show();
		initView();
		initData();
		loadData();
	}

	private void initView() {
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);

		evevt_img = (ImageView) findViewById(R.id.evevt_img);
		title = (TextView) findViewById(R.id.title);
		brandNum = (TextView) findViewById(R.id.brandNum);
		paychannel = (TextView) findViewById(R.id.paychannel);
		hoitoryCount = (TextView) findViewById(R.id.hoitoryCount);
		openCount = (TextView) findViewById(R.id.openCount);
		agentCount = (TextView) findViewById(R.id.agentCount);
		totalCount = (TextView) findViewById(R.id.totalCount);
		changeName = (Button) findViewById(R.id.changeName);

		search_layout = (LinearLayout) findViewById(R.id.search_layout);
		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		if (CheckRights.IS_ERJI) {
			changeName.setVisibility(View.GONE);
		}else {
			changeName.setVisibility(View.VISIBLE);
		}
		
		changeName.setOnClickListener(this);
		search_layout.setOnClickListener(this);
		titleback_linear_back.setOnClickListener(this);
		
		stockInfoAdapter = new StockInfoListAdapter(StockInfoListActivity.this,mEntities);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);
		mListView.setAdapter(stockInfoAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				StockTerminaListActivity.setStockAgentEntity(mEntities.get(position-2));

				Intent intent = new Intent(StockInfoListActivity.this,StockTerminaListActivity.class);
				intent.putExtra("goodId", stockEntity.getGood_id());
				intent.putExtra("paychannelId", stockEntity.getPaychannel_id());
				startActivity(intent);
			}
		});
	}

	private void initData() {
		ImageLoader.getInstance().displayImage(stockEntity.getPicurl(), evevt_img,options);

		title.setText(stockEntity.getGoodname()+"");
		brandNum.setText(stockEntity.getGood_brand() +
				stockEntity.getModel_number());
		paychannel.setText(stockEntity.getPaychannel()+"");
		hoitoryCount.setText(stockEntity.getHoitoryCount()+"");
		openCount.setText(stockEntity.getOpenCount()+"");
		agentCount.setText(stockEntity.getAgentCount()+"");
		totalCount.setText(stockEntity.getTotalCount()+"");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			Intent intent1 = new Intent();
			if (isChangeName == true) {
				intent1.putExtra("reName", computer_name);
			}else {
				intent1.putExtra("reName", "");
			}
			setResult(RESULT_OK, intent1);
			finish();
			break;
		case R.id.changeName:
			setDialog();
			break;
		case R.id.search_layout:
			Intent intent = new Intent(this,StockSearchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	private void setDialog() {

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
				MyApplication.hideSoftKeyboard(StockInfoListActivity.this);
			}
		});
		dialog_button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!dialog_text.getText().toString().equals("")) {
					computer_name = dialog_text.getText().toString();
					MyApplication.hideSoftKeyboard(StockInfoListActivity.this);
					stockRename();
				} else {
					Toast.makeText(StockInfoListActivity.this, "商品名称不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	//商品更名
	protected void stockRename() {
		//代理商id,MyApplication.NewUser.getAgentId()
		Config.stockRename(this,MyApplication.NewUser.getAgentId(),stockEntity.getGood_id(),
				computer_name,new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				dialog.dismiss();
				isChangeName = true;
				stockEntity.setGoodname(computer_name);
				title.setText(computer_name);
				Toast.makeText(StockInfoListActivity.this, "商品更名成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}
	//列表数据
	private void loadData() {
		//代理商id,MyApplication.NewUser.getAgentId()
		Config.stockInfo(this,MyApplication.NewUser.getAgentId(),stockEntity.getPaychannel_id(),
				stockEntity.getGood_id(),"", page , rows,
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
				stockInfoAdapter.notifyDataSetChanged();
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
			Toast.makeText(StockInfoListActivity.this, "no more data", Toast.LENGTH_SHORT).show();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			if (isChangeName == true) {
				intent.putExtra("reName", computer_name);
			}else {
				intent.putExtra("reName", "");
			}
			setResult(RESULT_OK, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
