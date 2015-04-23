package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.AfterSaleAdapter;
import com.comdo.zf_agent_a_pad.adapter.SelectStateAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AfterSaleEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

@SuppressLint("InflateParams")
public class AfterSaleActivity extends Activity implements View.OnClickListener,IXListViewListener {

	private View selectView;
	private View orderView_x,cancelView_xl,cancelView_xr,updateView_x;
	private View orderView_y,cancelView_y,updateView_y;
	//售后单、注销、更新资料
	private RelativeLayout order_layout,cancel_layout,update_layout;
	private View orderView,cancelView,updateView;
	//选择状态
	private RelativeLayout selState_layout;
	private TextView state_tv;
	//搜索
	private RelativeLayout search_layout;
	private EditText searchEditText;
	//单号、状态
	private TextView dataTextView,stateTextView;

	private XListView mListView;
	private LinearLayout eva_nodata;

	//提交物流信息的dialog
	private Dialog dialog;
	private Button dialog_button_cancel, dialog_button_ok;
	private EditText dialog_text;
	private EditText dialog_textNo;
	private String computer_name,track_number;

	private PopupWindow popuSelState;

	private SelectStateAdapter<String> selStateAdapter;
	private AfterSaleAdapter afterSaleAdapter;
	private List<AfterSaleEntity> mEntities = new ArrayList<AfterSaleEntity>();
	private List<String> dataList = new ArrayList<String>();
	//设置popupWindow弹出后背景的阴影
	private WindowManager.LayoutParams lp;

	private int mRecordType = 0;
	private String selState = "";
	private int page = 1;
	private int total = 0;
	private final int rows = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_after_sale_grid);
		new TitleMenuUtil(this, "售后记录").show();
		initView();

		lp = getWindow().getAttributes(); // 设置popupWindow弹出后背景的阴影

		dataList.add("全部");
		dataList.add("待处理");
		dataList.add("处理中");
		dataList.add("处理完成");
		dataList.add("已取消");

		loadData();
	}

	private void initView() {
		orderView_x = findViewById(R.id.orderView_x);
		cancelView_xl = findViewById(R.id.cancelView_xl);
		cancelView_xr = findViewById(R.id.cancelView_xr);
		updateView_x = findViewById(R.id.updateView_x);
		orderView_y = findViewById(R.id.orderView_y);
		cancelView_y = findViewById(R.id.cancelView_y);
		updateView_y = findViewById(R.id.updateView_y);

		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		order_layout = (RelativeLayout) findViewById(R.id.order_layout);
		cancel_layout = (RelativeLayout) findViewById(R.id.cancel_layout);
		update_layout = (RelativeLayout) findViewById(R.id.update_layout);

		selState_layout = (RelativeLayout) findViewById(R.id.selState_layout);
		state_tv = (TextView) findViewById(R.id.state_tv);

		search_layout = (RelativeLayout) findViewById(R.id.search_layout);
		searchEditText = (EditText) findViewById(R.id.searchEditText);

		dataTextView = (TextView) findViewById(R.id.dataTextView);
		stateTextView = (TextView) findViewById(R.id.stateTextView);

		orderView = findViewById(R.id.orderView);
		cancelView = findViewById(R.id.cancelView);
		updateView = findViewById(R.id.updateView);

		order_layout.setOnClickListener(this);
		cancel_layout.setOnClickListener(this);
		update_layout.setOnClickListener(this);

		selState_layout.setOnClickListener(this);
		search_layout.setOnClickListener(this);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AfterSaleActivity.this,AfterSaleDetailActivity.class);
				intent.putExtra("mRecordType", mRecordType);
				intent.putExtra("apply_num", mEntities.get(position-2).getApply_num());
				intent.putExtra("id", mEntities.get(position-2).getId());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_layout:
			orderView_x.setVisibility(View.VISIBLE);
			cancelView_xl.setVisibility(View.VISIBLE);
			cancelView_xr.setVisibility(View.GONE);
			updateView_x.setVisibility(View.GONE);
			
			orderView_y.setVisibility(View.GONE);
			cancelView_y.setVisibility(View.VISIBLE);
			updateView_y.setVisibility(View.VISIBLE);
			
			order_layout.setBackgroundResource(R.color.white);
			cancel_layout.setBackgroundResource(R.color.e9e8e8);
			update_layout.setBackgroundResource(R.color.e9e8e8);
			orderView.setVisibility(View.VISIBLE);
			cancelView.setVisibility(View.GONE);
			updateView.setVisibility(View.GONE);

			mRecordType = 0;
			dataTextView.setText("售后单号");
			stateTextView.setText("售后状态");
			page = 1;
			mEntities.clear();
			loadData();
			break;
		case R.id.cancel_layout:
			orderView_x.setVisibility(View.GONE);
			cancelView_xl.setVisibility(View.VISIBLE);
			cancelView_xr.setVisibility(View.VISIBLE);
			updateView_x.setVisibility(View.GONE);
			
			orderView_y.setVisibility(View.VISIBLE);
			cancelView_y.setVisibility(View.GONE);
			updateView_y.setVisibility(View.VISIBLE);
			order_layout.setBackgroundResource(R.color.e9e8e8);
			cancel_layout.setBackgroundResource(R.color.white);
			update_layout.setBackgroundResource(R.color.e9e8e8);
			orderView.setVisibility(View.GONE);
			cancelView.setVisibility(View.VISIBLE);
			updateView.setVisibility(View.GONE);

			mRecordType = 1;
			dataTextView.setText("注销单号");
			stateTextView.setText("注销状态");
			page = 1;
			mEntities.clear();
			loadData();
			break;
		case R.id.update_layout:
			orderView_x.setVisibility(View.GONE);
			cancelView_xl.setVisibility(View.GONE);
			cancelView_xr.setVisibility(View.VISIBLE);
			updateView_x.setVisibility(View.VISIBLE);
			
			orderView_y.setVisibility(View.VISIBLE);
			cancelView_y.setVisibility(View.VISIBLE);
			updateView_y.setVisibility(View.GONE);
			cancel_layout.setBackgroundResource(R.color.e9e8e8);
			order_layout.setBackgroundResource(R.color.e9e8e8);
			update_layout.setBackgroundResource(R.color.white);
			cancelView.setVisibility(View.GONE);
			orderView.setVisibility(View.GONE);
			updateView.setVisibility(View.VISIBLE);

			mRecordType = 2;
			dataTextView.setText("更新资料单号");
			stateTextView.setText("更新状态");
			page = 1;
			mEntities.clear();
			loadData();
			break;
		case R.id.selState_layout:
			popSelectState();
			break;
		case R.id.search_layout:
			if (!searchEditText.getText().toString().trim().equals("")) {
				page = 1;
				mEntities.clear();
				loadData();
			}else {
				Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}	
	private class onClickListener implements AfterSaleAdapter.OnClickListener {

		@Override
		public void onCancelAgain(int position, int mRecordType) {
			if (mRecordType == 0) {
				//增加物流信息
				setDialog(position);
			}else {
				//重新提交注销
				cancelAgain(position);
			}
		}

		@Override
		public void onCancelApply(int position, int mRecordType) {
			//取消申请
			cancelApply(position,mRecordType);
		}

	}
	//增加物流信息
	private void setDialog(final int position) {

		dialog = new Dialog(this, R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_edit_aftersale);
		dialog.setCancelable(false);
		dialog.show();

		dialog_button_ok = (Button) dialog.findViewById(R.id.dialog_button_ok);
		dialog_button_cancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);
		dialog_textNo = (EditText) dialog.findViewById(R.id.dialog_textNo);
		dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);

		dialog_button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				computer_name = "";
				track_number = "";
				dialog.dismiss();
				MyApplication.hideSoftKeyboard(AfterSaleActivity.this);
			}
		});
		dialog_button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!dialog_text.getText().toString().equals("")) {
					if (!dialog_textNo.getText().toString().equals("")) {
						computer_name = dialog_text.getText().toString();
						track_number = dialog_textNo.getText().toString();
						MyApplication.hideSoftKeyboard(AfterSaleActivity.this);
						agentsAddMark(position);
					}else {
						Toast.makeText(AfterSaleActivity.this, "物流单号不能为空", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(AfterSaleActivity.this, "物流公司名称不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	//选择状态
	private void popSelectState() {

		selectView = LayoutInflater.from(this).inflate(
				R.layout.pop_selector_state, null);
		ListView listViewState = (ListView) selectView.findViewById(R.id.list_one);
		selStateAdapter = new SelectStateAdapter<String>(this, dataList);
		listViewState.setAdapter(selStateAdapter);
		listViewState.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mRecordType == 0) {
					//售后单
					if (dataList.get(position).equals("待处理")) {
						selState = "1";
					}else if (dataList.get(position).equals("处理中")) {
						selState = "2";
					}else if (dataList.get(position).equals("处理完成")) {
						selState = "3";
					} else if (dataList.get(position).equals("已取消")) {
						selState = "4";
					}  else if (dataList.get(position).equals("全部")) {
						selState = "";
					}
				}else {
					//注销、更新资料
					if (dataList.get(position).equals("待处理")) {
						selState = "1";
					}else if (dataList.get(position).equals("处理中")) {
						selState = "2";
					}else if (dataList.get(position).equals("处理完成")) {
						selState = "4";
					} else if (dataList.get(position).equals("已取消")) {
						selState = "5";
					}  else if (dataList.get(position).equals("全部")) {
						selState = "";
					}
				}

				state_tv.setText(dataList.get(position));
				popuSelState.dismiss();
				page = 1;
				mEntities.clear();
				loadData();
			}
		});

		popuSelState = new PopupWindow(selectView);
		popuSelState.setWidth(selState_layout.getWidth());
		popuSelState.setHeight(LayoutParams.WRAP_CONTENT);
		popuSelState.setOutsideTouchable(true);
		popuSelState.setFocusable(true);
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popuSelState.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		popuSelState.setBackgroundDrawable(new ColorDrawable());
		popuSelState.showAsDropDown(selState_layout);
	}
	//重新提交注销
	public void cancelAgain(final int position) {
		Config.resubmitCancel(this, mEntities.get(position).getId(),
				new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				mEntities.get(position).setStatus("1");
				afterSaleAdapter.notifyDataSetChanged();
				Toast.makeText(AfterSaleActivity.this, "重新提交注销成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}

	//取消申请
	public void cancelApply(final int position, final int mRecordType2) {
		Config.cancelApply(this, mRecordType2, mEntities.get(position).getId(),
				new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				if (mRecordType2 == 0) {
					mEntities.get(position).setStatus("4");
				}else {
					mEntities.get(position).setStatus("5");
				}
				afterSaleAdapter.notifyDataSetChanged();
				Toast.makeText(AfterSaleActivity.this, "取消申请成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}
	//列表数据
	private void loadData() {
		String search = searchEditText.getText().toString().trim();
		//代理商对应用户ID,MyApplication.NewUser.getAgentUserId()
		Config.getAfterSaleList(this, mRecordType, MyApplication.NewUser.getAgentUserId(),
				search,selState, page , rows,
				new HttpCallback<Page<AfterSaleEntity>>(this) {
			@Override
			public void onSuccess(Page<AfterSaleEntity> data) {
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

				afterSaleAdapter = new AfterSaleAdapter(AfterSaleActivity.this,mEntities,mRecordType,
						new onClickListener());
				mListView.setAdapter(afterSaleAdapter);
			}

			@Override
			public TypeToken<Page<AfterSaleEntity>> getTypeToken() {
				return new TypeToken<Page<AfterSaleEntity>>() {
				};
			}
		});
	}
	//增加物流信息
	protected void agentsAddMark(int position) {
		//代理商对应用户ID,MyApplication.NewUser.getAgentUserId()
		Config.agentsAddMark(this,mEntities.get(position).getId(),computer_name,
				track_number,MyApplication.NewUser.getAgentUserId(),
				new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				dialog.dismiss();
				Toast.makeText(AfterSaleActivity.this, "提交物流信息成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
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
			Toast.makeText(AfterSaleActivity.this, "no more data", Toast.LENGTH_SHORT).show();
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
