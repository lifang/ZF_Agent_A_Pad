package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_BILLING;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ARRAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TOTAL;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.TerminalApplySelectActivity.TerminalListAdapter;
import com.comdo.zf_agent_a_pad.adapter.TerminalListagainAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.ApplyChannelagain;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.entity.SelectPOS;
import com.comdo.zf_agent_a_pad.entity.TerminalList;
import com.comdo.zf_agent_a_pad.entity.TerminalPriceEntity;
import com.comdo.zf_agent_a_pad.fragment.Transgoods;
import com.comdo.zf_agent_a_pad.trade.ApplyChannelActivity;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChannel;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TerminalSelectActivity extends BaseActivity implements OnClickListener,
                IXListViewListener{
	private TextView selectedpos,selectedchannel;
	public static final int REQUEST_CHOOSE_POS = 1000;
	private int posID;
	public static int checked = 0;
	private String posName;
	private ApplyChannelagain mChosenChannel;
	private ApplyChannel.Billing mChosenBilling;
	private int mChannelId;
	private View mChooseChannel;
	private Button terminal_commit,terminal_comfirm;
	private EditText zdh;
	private String zdhString;
	private int page=1;
	private int rows=Config.ROWS;
	private List<TerminalList> mTerminalItems;
	private XListView mTerminalList;
	public static boolean isfromDisOrTrans=false;
	private TextView terminalNum;
	public static Handler myHandler;
	private BaseAdapter terminaladapter;
	private boolean isrefersh=false;
	private int a=1;
	private CheckBox checkboxAll;
	public static boolean allCheck = false;
	public static boolean isFromTrans=false;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_tt);
	new TitleMenuUtil(TerminalSelectActivity.this, "选择终端").show();
	init();
}
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				terminalNum.setText(checked+"");
				break;
			case 1:
				onLoad( );
				mTerminalList.setAdapter(terminaladapter);
				break;
			default:
				break;
			}
		};
	};
}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	checked=0;
	isFromTrans=false;
}
protected void onLoad() {
	mTerminalList.stopRefresh();
	mTerminalList.stopLoadMore();
	mTerminalList.setRefreshTime(Tools.getHourAndMin());
	
}
private void init() {
	terminal_comfirm = (Button) findViewById(R.id.terminal_comfirm);
	terminal_comfirm.setOnClickListener(this);
	mTerminalItems=new ArrayList<TerminalList>();
	terminaladapter=new TerminalListagainAdapter(mTerminalItems, TerminalSelectActivity.this);
	terminalNum = (TextView) findViewById(R.id.terminalNum);
	mTerminalList = (XListView) findViewById(R.id.apply_list);
	
	zdh=(EditText) findViewById(R.id.zdh);
	terminal_commit = (Button) findViewById(R.id.terminal_commit);
	terminal_commit.setOnClickListener(this);
	selectedchannel=(TextView) findViewById(R.id.selectedchannel);
	selectedpos=(TextView) findViewById(R.id.selectedpos);
	checkboxAll = (CheckBox) findViewById(R.id.checkboxAll);
	selectedpos.setOnClickListener(this);
	selectedchannel.setOnClickListener(this);
	mTerminalList.setPullLoadEnable(true);
	mTerminalList.setXListViewListener(this);
	mTerminalList.setDivider(null);
	checkboxAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (isChecked) {
				allCheck = true;

			} else {

				allCheck = false;
			}
			terminaladapter.notifyDataSetChanged();

			terminalNum.setText(checked+"");
		}

	});
}
private final TextWatcher mTextWatcher = new TextWatcherAdapter() {

	public void afterTextChanged(final Editable gitDirEditText) {

		if (zdh.getText().toString() != null)
			zdhString = zdh.getText().toString();

	}
};
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.terminal_comfirm:
		StringBuffer string=new StringBuffer();
		for (int i = 0; i < mTerminalItems.size(); i++) {
			if (mTerminalItems.get(i).isCheck()) {
				string.append(mTerminalItems.get(i).getSerial_num());
				
				if (i != mTerminalItems.size()-1) {
					
					string.append(",");
				}
			}
		}
		Intent it = new Intent();
		it.putExtra(TERMINAL_TOTAL, checked);
		it.putExtra(TERMINAL_ARRAY, string.toString());
		it.putExtra("goodsid", posID);
		it.putExtra("paychannelId", mChannelId);
		setResult(RESULT_OK, it);
		finish();
		break;
	case R.id.selectedpos:
		Config.selectPOS(TerminalSelectActivity.this, 80,
				new HttpCallback<List<SelectPOS>>(
						TerminalSelectActivity.this) {
					@Override
					public void onSuccess(List<SelectPOS> data) {

						final ArrayList<SelectPOS> list = (ArrayList<SelectPOS>) data;
						Intent intent = new Intent(
								TerminalSelectActivity.this,
								TerminalSelectPOSActivity.class);
						intent.putExtra(CHOOSE_TITLE, getResources()
								.getString(R.string.title_pos_select));
						intent.putExtra(SELECTED_ID, 0);
						intent.putExtra(CHOOSE_ITEMS, list);
						startActivityForResult(intent, REQUEST_CHOOSE_POS);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken<List<SelectPOS>> getTypeToken() {
						return new TypeToken<List<SelectPOS>>() {
						};
					}
				});
		break;
	case R.id.selectedchannel:
		isfromDisOrTrans=true;
		Intent intent = new Intent(TerminalSelectActivity.this,
				ApplyChannelagainActivity.class);
		intent.putExtra(SELECTED_CHANNEL, mChosenChannel);
		intent.putExtra(SELECTED_BILLING, mChosenBilling);
		startActivityForResult(intent, REQUEST_CHOOSE_CHANNEL);
		break;
	case R.id.terminal_commit:
		if(isFromTrans){
			confirmUp_trans();
		}
		else{
			confirmUp();
		}
        
		
		break;
	default:
		break;
	}
	
}
private void confirmUp_trans() {
	//Log.e("iddddd", String.valueOf(Transgoods.sonAgentId[0])+String.valueOf(Transgoods.sonAgentId[1])+String.valueOf(Transgoods.sonAgentId[2]));
Config.getTerminalTranslist(TerminalSelectActivity.this, 
		Transgoods.sonAgentId[Transgoods.from], page, rows, new HttpCallback<Page<TerminalList>>(TerminalSelectActivity.this) {

			@Override
			public void onSuccess(Page<TerminalList> data) {
				if(isrefersh){
					page=a;
					rows=Config.ROWS;
					isrefersh=false;
				}
				if(mTerminalItems.size()!=0&&data.getList().size()==0){
					Toast.makeText(TerminalSelectActivity.this, "没有更多数据!", 1000).show();
				}
				else{
				mTerminalItems.addAll(data.getList());
				}
				myHandler.sendEmptyMessage(1);
				
			}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
			@Override
			public TypeToken<Page<TerminalList>> getTypeToken() {
				// TODO Auto-generated method stub
				return new TypeToken<Page<TerminalList>>() {
				};
			}
		});
	
}
private void confirmUp() {
	String[] str= new String[] {};
	//str = zdh.getText().toString().split("\n");
	Config.getTerminallist(TerminalSelectActivity.this, 
			1, 
			mChannelId, 
			posID, 
			str, 
			page, 
			rows, 
			new HttpCallback<Page<TerminalList>>(TerminalSelectActivity.this) {

				@Override
				public void onSuccess(Page<TerminalList> data) {
					if(isrefersh){
						page=a;
						rows=Config.ROWS;
						isrefersh=false;
					}
					if(mTerminalItems.size()!=0&&data.getList().size()==0){
						Toast.makeText(TerminalSelectActivity.this, "没有更多数据!", 1000).show();
					}
					else{
					mTerminalItems.addAll(data.getList());
					}
					myHandler.sendEmptyMessage(1);
					
				}

				@Override
				public TypeToken<Page<TerminalList>> getTypeToken() {
					// TODO Auto-generated method stub
					return new TypeToken<Page<TerminalList>>() {
					};
				}
			});
	
}
@Override
protected void onActivityResult(final int requestCode, int resultCode,
		final Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode != RESULT_OK)
		return;
	switch (requestCode) {
	case REQUEST_CHOOSE_POS: {

		//posID = data.getIntExtra(SELECTED_ID, 0);
		posID = data.getIntExtra(SELECTED_ID, 0);
		posName = data.getStringExtra(SELECTED_TITLE);
		selectedpos.setText(posName);
		Log.e("posID", posID+"");
		break;

	}
	case REQUEST_CHOOSE_CHANNEL: {
		mChosenChannel = (ApplyChannelagain) data
				.getSerializableExtra(SELECTED_CHANNEL);
		//mChosenBilling = (ApplyChannel.Billing) data
		//		.getSerializableExtra(SELECTED_BILLING);
		//mChannelId = mChooseChannel.getId();
		//selectedchannel.setText(mChosenChannel.getName());
		mChannelId = mChosenChannel.getId();
		selectedchannel.setText(mChosenChannel.getPaychannel());
	}
	}
}
@Override
public void onRefresh() {
	if(!Tools.isConnect(TerminalSelectActivity.this)){
		CommonUtil.toastShort(TerminalSelectActivity.this, "网络异常");
		return;
	}
	isrefersh=true;
	a=page;
	rows=a*rows;
	page=1;
	mTerminalItems.clear();
	confirmUp();
	
}
@Override
public void onLoadMore() {
	if(!Tools.isConnect(TerminalSelectActivity.this)){
		CommonUtil.toastShort(TerminalSelectActivity.this, "网络异常");
		return;
	}
	page+=1;
	confirmUp();
	
}
}