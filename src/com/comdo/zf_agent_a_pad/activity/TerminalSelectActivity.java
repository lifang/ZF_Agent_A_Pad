package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_BILLING;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ARRAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TOTAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TYPE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.SelectStateAdapter;
import com.comdo.zf_agent_a_pad.adapter.TerminalListagainAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.ApplyChannelagain;
import com.comdo.zf_agent_a_pad.entity.Pos;
import com.comdo.zf_agent_a_pad.entity.TerminalList;
import com.comdo.zf_agent_a_pad.fragment.Transgoods;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChannel;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class TerminalSelectActivity extends BaseActivity implements
OnClickListener, IXListViewListener {
	private TextView selectedpos, selectedchannel,countTextView;
	public static final int REQUEST_CHOOSE_POS = 12543;
	private int posID;
	public static int checked = 0;
	private String posName;
	private ApplyChannelagain mChosenChannel;
	private ApplyChannel.Billing mChosenBilling;
	private int mChannelId;
	private View mChooseChannel;
	private Button terminal_commit, terminal_comfirm;
	private EditText zdh;
	private String zdhString;
	private int page = 1;
	private int rows = Config.ROWS;
	private List<TerminalList> mTerminalItems;
	private XListView mTerminalList;
	public static boolean isfromDisOrTrans = false;
	private TextView terminalNum;
	public static Handler myHandler;
	private BaseAdapter terminaladapter;
	private boolean isrefersh = false;
	private int a = 1;
	private CheckBox checkboxAll;
	public static boolean allCheck = false;
	public static boolean isFromTrans = false;
	private int agentId = MyApplication.NewUser.getAgentId();
	private ImageView search;
	private static final int REQUEST_SEARCH = 1001;
	private LinearLayout searchLinear;
	private ArrayList<Pos> posList = new ArrayList<Pos>();
	private ArrayList<ApplyChannelagain> channelList = new ArrayList<ApplyChannelagain>();
	private WindowManager.LayoutParams lp;
	private PopupWindow popuSelPos,popuSelChannel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_tt);
		new TitleMenuUtil(TerminalSelectActivity.this, "选择终端").show();
		lp = getWindow().getAttributes(); // 设置popupWindow弹出后背景的阴影
		
		String mflag = getIntent().getExtras().getString("flag");
		if (mflag.equals("Distribute")) {
			agentId = MyApplication.NewUser.getAgentId();
		}else {
			agentId = getIntent().getIntExtra("sonAgentId", 0);
		}
		init();
		getPosList();
		getPaylist();
	}

	@Override
	protected void onStart() {
		super.onStart();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					terminalNum.setText(checked + "");
					break;
				case 1:
					countTextView.setText("可配货的机器共"+mTerminalItems.size()+"台");
					onLoad();
					mTerminalList.setAdapter(terminaladapter);
					break;
				default:
					break;
				}
			};
		};
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFromTrans) {
			TransgoodsDetail.isTra = true;
		} else {
			DistributeDetail.isDri = true;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		checked = 0;
		isFromTrans = false;
	}

	protected void onLoad() {
		mTerminalList.stopRefresh();
		mTerminalList.stopLoadMore();
		mTerminalList.setRefreshTime(Tools.getHourAndMin());

	}

	private void init() {
		countTextView = (TextView) findViewById(R.id.countTextView);
		searchLinear = (LinearLayout) findViewById(R.id.searchLinear);
		search = (ImageView) findViewById(R.id.search);
		terminal_comfirm = (Button) findViewById(R.id.terminal_comfirm);
		terminal_comfirm.setOnClickListener(this);
		mTerminalItems = new ArrayList<TerminalList>();
		terminaladapter = new TerminalListagainAdapter(mTerminalItems,
				TerminalSelectActivity.this);
		terminalNum = (TextView) findViewById(R.id.terminalNum);
		mTerminalList = (XListView) findViewById(R.id.apply_list);

		zdh = (EditText) findViewById(R.id.zdh);
		terminal_commit = (Button) findViewById(R.id.terminal_commit);
		terminal_commit.setOnClickListener(this);
		selectedchannel = (TextView) findViewById(R.id.selectedchannel);
		selectedpos = (TextView) findViewById(R.id.selectedpos);
		checkboxAll = (CheckBox) findViewById(R.id.checkboxAll);
		searchLinear.setOnClickListener(this);
		search.setOnClickListener(this);
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

				terminalNum.setText(checked + "");
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
			StringBuffer string = new StringBuffer();
			for (int i = 0; i < mTerminalItems.size(); i++) {
				if (mTerminalItems.get(i).isCheck()) {
					string.append(mTerminalItems.get(i).getSerial_num());

					if (i != mTerminalItems.size() - 1) {

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
			popSelectPos();

			break;
		case R.id.selectedchannel:
			popSelectChannel();
			break;
		case R.id.terminal_commit:
			if (mTerminalItems.size() != 0) {
				mTerminalItems.clear();
			}
			if (isFromTrans) {
				confirmUp_trans();
			} else {
				confirmUp();
			}

			break;
		case R.id.search:
			/*
			 * Intent iIntent = new Intent(TerminalSelectActivity.this,
			 * GenerateSearch.class); iIntent.putExtra(TERMINAL_TYPE, 3);
			 * iIntent.putExtra(TERMINAL_STATUS, -1);
			 * startActivityForResult(iIntent, REQUEST_SEARCH); break;
			 */
		case R.id.searchLinear:
			Intent iIntent = new Intent(TerminalSelectActivity.this,
					GenerateSearch.class);
			iIntent.putExtra(TERMINAL_TYPE, 3);
			iIntent.putExtra(TERMINAL_STATUS, -1);
			startActivityForResult(iIntent, REQUEST_SEARCH);
			break;
		default:
			break;
		}

	}
	private void getPaylist() {
		Config.getTerminalPaylist(this,agentId,
				new HttpCallback<List<ApplyChannelagain>>(this) {
			@Override
			public void onSuccess(List<ApplyChannelagain> data) {
				channelList = (ArrayList<ApplyChannelagain>) data;
			}

			@Override
			public TypeToken<List<ApplyChannelagain>> getTypeToken() {
				return new TypeToken<List<ApplyChannelagain>>() {
				};
			}
		});
	}
	private void getPosList() {
		Config.geTerminalPosList(TerminalSelectActivity.this, agentId,
				new HttpCallback<List<Pos>>(TerminalSelectActivity.this) {

			@Override
			public void onSuccess(List<Pos> data) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i) == null) {
						data.remove(i);
					}
				}
				posList = (ArrayList<Pos>) data;

			}

			@Override
			public TypeToken<List<Pos>> getTypeToken() {
				return new TypeToken<List<Pos>>() {
				};
			}
		});
	}

	private void confirmUp_trans() {
		// Log.e("iddddd",
		// String.valueOf(Transgoods.sonAgentId[0])+String.valueOf(Transgoods.sonAgentId[1])+String.valueOf(Transgoods.sonAgentId[2]));
		Config.getTerminalTranslist(TerminalSelectActivity.this,
				Transgoods.sonAgentId[Transgoods.from], page, rows,
				new HttpCallback<Page<TerminalList>>(
						TerminalSelectActivity.this) {

			@Override
			public void onSuccess(Page<TerminalList> data) {
				if (isrefersh) {
					page = a;
					rows = Config.ROWS;
					isrefersh = false;
				}
				if (mTerminalItems.size() != 0
						&& data.getList().size() == 0) {
					Toast.makeText(TerminalSelectActivity.this,
							"没有更多数据!", 1000).show();
				} else {
					mTerminalItems.addAll(data.getList());
				}
				myHandler.sendEmptyMessage(1);

			}

			@Override
			public void onFailure(String message) {
				super.onFailure(message);
			}

			@Override
			public TypeToken<Page<TerminalList>> getTypeToken() {
				return new TypeToken<Page<TerminalList>>() {
				};
			}
		});

	}

	private void confirmUp() {
		String[] str = new String[] {};
		// str = zdh.getText().toString().split("\n");
		Config.getTerminallist(TerminalSelectActivity.this, agentId,
				mChannelId, posID, str, page, rows,
				new HttpCallback<Page<TerminalList>>(
						TerminalSelectActivity.this) {

			@Override
			public void onSuccess(Page<TerminalList> data) {
				if (isrefersh) {
					page = a;
					rows = Config.ROWS;
					isrefersh = false;
				}
				if (mTerminalItems.size() != 0
						&& data.getList().size() == 0) {
					Toast.makeText(TerminalSelectActivity.this,
							"没有更多数据!", 1000).show();
				} else {
					mTerminalItems.addAll(data.getList());
				}
				myHandler.sendEmptyMessage(1);

			}

			@Override
			public TypeToken<Page<TerminalList>> getTypeToken() {
				return new TypeToken<Page<TerminalList>>() {
				};
			}
		});

	}
	private void popSelectPos() {

		View selectView = LayoutInflater.from(this).inflate(
				R.layout.pop_selector_state, null);
		ListView listViewState = (ListView) selectView.findViewById(R.id.list_one);
		SelectStateAdapter selStateAdapter = new SelectStateAdapter<Pos>(this, posList);
		listViewState.setAdapter(selStateAdapter);

		listViewState.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				posID = posList.get(position).getId();
				posName = posList.get(position).getGoodname();
				selectedpos.setText(posName);
				popuSelPos.dismiss();
				if (mTerminalItems.size() != 0) {
					mTerminalItems.clear();
				}
				confirmUp();
			}
		});

		popuSelPos = new PopupWindow(selectView);
		popuSelPos.setWidth(selectedpos.getWidth());
		popuSelPos.setHeight(LayoutParams.WRAP_CONTENT);
		popuSelPos.setOutsideTouchable(true);
		popuSelPos.setFocusable(true);
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popuSelPos.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		popuSelPos.setBackgroundDrawable(new ColorDrawable());
		popuSelPos.showAsDropDown(selectedpos);
	}
	private void popSelectChannel() {

		View selectView = LayoutInflater.from(this).inflate(
				R.layout.pop_selector_state, null);
		ListView listViewState = (ListView) selectView.findViewById(R.id.list_one);
		SelectStateAdapter selStateAdapter = new SelectStateAdapter<ApplyChannelagain>(this, channelList);
		listViewState.setAdapter(selStateAdapter);
		listViewState.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mChannelId = channelList.get(position).getId();
				selectedchannel.setText(channelList.get(position).getPaychannel());
				popuSelChannel.dismiss();
				if (mTerminalItems.size() != 0) {
					mTerminalItems.clear();
				}
				confirmUp();
			}
		});

		popuSelChannel = new PopupWindow(selectView);
		popuSelChannel.setWidth(selectedchannel.getWidth());
		popuSelChannel.setHeight(LayoutParams.WRAP_CONTENT);
		popuSelChannel.setOutsideTouchable(true);
		popuSelChannel.setFocusable(true);
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popuSelChannel.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		popuSelChannel.setBackgroundDrawable(new ColorDrawable());
		popuSelChannel.showAsDropDown(selectedchannel);


	}
	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CHOOSE_POS: {

			// posID = data.getIntExtra(SELECTED_ID, 0);
			posID = data.getIntExtra(SELECTED_ID, 0);
			posName = data.getStringExtra(SELECTED_TITLE);
			selectedpos.setText(posName);
			Log.e("posID", posID + "");
			break;

		}
		case REQUEST_CHOOSE_CHANNEL: {
			mChosenChannel = (ApplyChannelagain) data
					.getSerializableExtra(SELECTED_CHANNEL);
			// mChosenBilling = (ApplyChannel.Billing) data
			// .getSerializableExtra(SELECTED_BILLING);
			// mChannelId = mChooseChannel.getId();
			// selectedchannel.setText(mChosenChannel.getName());
			mChannelId = mChosenChannel.getId();
			selectedchannel.setText(mChosenChannel.getPaychannel());
		}
		case REQUEST_SEARCH:
			if (data != null) {
				String str=data.getStringExtra(SELECTED_TERMINAL); 
				for (int i = 0; i < mTerminalItems.size(); i++) {
					if (!mTerminalItems.get(i).getSerial_num().equals(str)) {
						mTerminalItems.remove(i);
					}
				}
			}

			break;
		}
	}

	@Override
	public void onRefresh() {
		if (!Tools.isConnect(TerminalSelectActivity.this)) {
			CommonUtil.toastShort(TerminalSelectActivity.this, "网络异常");
			return;
		}
		isrefersh = true;
		a = page;
		rows = a * rows;
		page = 1;
		mTerminalItems.clear();
		confirmUp();

	}

	@Override
	public void onLoadMore() {
		if (!Tools.isConnect(TerminalSelectActivity.this)) {
			CommonUtil.toastShort(TerminalSelectActivity.this, "网络异常");
			return;
		}
		page += 1;
		confirmUp();

	}
}
