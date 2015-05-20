package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_BILLING;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_CHANNEL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ARRAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TOTAL;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.SelectPOS;
import com.comdo.zf_agent_a_pad.entity.TerminalPriceEntity;
import com.comdo.zf_agent_a_pad.trade.ApplyChannelActivity;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChannel;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplySelectActivity extends BaseActivity implements
		View.OnClickListener, XListView.IXListViewListener {

	private TextView selectedpos, selectedchannel;
	private int mChannelId;
	// private int minPrice = 0, maxPrice = 0;

	private int posID, checked = 0;
	private EditText zdh;
	// private EditText lower, higher;
	// private Boolean allCheck = false;
	private String posName, zdhString, searchKey;
	private ImageView search;
	private CheckBox checkboxAll;
	private LinearLayout channelselect, posselect, searchLinear;
	private TextView terminalNum;
	private Button terminal_commit, terminal_comfirm;
	private LayoutInflater mInflater;
	private XListView mTerminalList;
	private List<TerminalPriceEntity> mTerminalItems;
	private List<TerminalPriceEntity> count;
	private TerminalListAdapter mAdapter;
	final List<String> list = new ArrayList<String>();
	private ApplyChannel mChosenChannel;
	private ApplyChannel.Billing mChosenBilling;
	public static final int REQUEST_CHOOSE_POS = 1000;
	private static final int REQUEST_SEARCH = 1001;

	private boolean isSelectAll = false;
	private Boolean isLoadMore = true;
	private int page = 0;
	private int total = 0;
	private final int rows = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_select);
		new TitleMenuUtil(this, getString(R.string.title_trade_client)).show();

		mInflater = LayoutInflater.from(this);
		mTerminalList = (XListView) findViewById(R.id.apply_list);
		mTerminalItems = new ArrayList<TerminalPriceEntity>();
		count = new ArrayList<TerminalPriceEntity>();
		mAdapter = new TerminalListAdapter();
		// init the XListView
		mTerminalList.initHeaderAndFooter();
		mTerminalList.setXListViewListener(this);
		mTerminalList.setPullLoadEnable(true);
		mTerminalList.getmFooterView().setVisibility(View.GONE);
		mTerminalList.setAdapter(mAdapter);
		posselect = (LinearLayout) findViewById(R.id.posselect);
		posselect.setOnClickListener(this);
		selectedpos = (TextView) findViewById(R.id.selectedpos);
		channelselect = (LinearLayout) findViewById(R.id.channelselect);
		channelselect.setOnClickListener(this);
		selectedchannel = (TextView) findViewById(R.id.selectedchannel);
		terminal_commit = (Button) findViewById(R.id.terminal_commit);
		terminal_commit.setOnClickListener(this);
		terminal_comfirm = (Button) findViewById(R.id.terminal_comfirm);
		terminal_comfirm.setOnClickListener(this);
		zdh = (EditText) findViewById(R.id.zdh);
		// lower = (EditText) findViewById(R.id.lower);
		// higher = (EditText) findViewById(R.id.higher);
		zdh.addTextChangedListener(mTextWatcher);
		// lower.addTextChangedListener(mTextWatcher);
		// higher.addTextChangedListener(mTextWatcher);
		terminalNum = (TextView) findViewById(R.id.terminalNum);
		searchLinear = (LinearLayout) findViewById(R.id.searchLinear);
		searchLinear.setOnClickListener(this);
		search = (ImageView) findViewById(R.id.search);
		search.setOnClickListener(this);
		checkboxAll = (CheckBox) findViewById(R.id.checkboxAll);
		checkboxAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSelectAll == false) {
					isSelectAll = true;
					checkboxAll.setChecked(true);
				} else {
					isSelectAll = false;
					checkboxAll.setChecked(false);
				}
				for (int index = 0; index < mTerminalItems.size(); index++) {
					mTerminalItems.get(index).setIsCheck(isSelectAll);
				}

				mAdapter.notifyDataSetChanged();
				myHandler.sendEmptyMessage(0);
			}
		});
		// checkboxAll.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		//
		// if (isChecked) {
		// allCheck = true;
		//
		// } else {
		// allCheck = false;
		// }
		// for (int i = 0; i < mTerminalItems.size(); i++) {
		//
		// mTerminalItems.get(i).setIsCheck(allCheck);
		// }
		// mAdapter.notifyDataSetChanged();
		// myHandler.sendEmptyMessage(0);
		// }
		//
		// });
	}

	private final TextWatcher mTextWatcher = new TextWatcherAdapter() {

		public void afterTextChanged(final Editable gitDirEditText) {

			if (zdh.getText().toString() != null)
				zdhString = zdh.getText().toString();
			// if (higher.getText().toString() != null
			// && !"".equals(higher.getText().toString()))
			// maxPrice = Integer.parseInt(higher.getText().toString());
			// if (lower.getText().toString() != null
			// && !"".equals(lower.getText().toString()))
			// minPrice = Integer.parseInt(lower.getText().toString());

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	class TerminalListAdapter extends BaseAdapter {

		TerminalListAdapter() {

			checked = 0;
		}

		@Override
		public int getCount() {
			return mTerminalItems.size();
		}

		@Override
		public TerminalPriceEntity getItem(int i) {
			return mTerminalItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.terminal_select_listitem, null);
				holder = new ViewHolder();
				holder.terminal_num = (TextView) convertView
						.findViewById(R.id.terminal_num);
				holder.price = (TextView) convertView.findViewById(R.id.price);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalPriceEntity item = getItem(i);
			holder.terminal_num.setText(item.getSerial_num());
			holder.price.setText("￥" + item.getRetail_price());

			holder.checkbox.setChecked(item.getIsCheck());
			holder.checkbox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							if (isChecked) {
								checked++;
							} else {
								if (checked != 0)
									checked--;
								isSelectAll = false;
							}
							if (checked == 0) {
								checkboxAll.setChecked(false);
							} else if (checked == list.size()) {
								checkboxAll.setChecked(true);
							}
							item.setIsCheck(isChecked);
							for (int i = 0; i < mTerminalItems.size(); i++) {

								if (!mTerminalItems.get(i).getIsCheck()) {
									checkboxAll.setChecked(false);
								}
							}
							// if (isChecked) {
							// checked++;
							// } else {
							// checked--;
							// allCheck = false;
							// }
							// if (!allCheck) {
							//
							// if (checked == count.size())
							// checkboxAll.setChecked(true);
							// else
							// checkboxAll.setChecked(false);
							// }
							// holder.checkbox.setChecked(isChecked);
							myHandler.sendEmptyMessage(0);
							// item.setIsCheck(isChecked);
						}
					});

			return convertView;
		}
	}

	static class ViewHolder {
		public TextView terminal_num;
		public TextView price;
		public CheckBox checkbox;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_comfirm:
			StringBuffer string = new StringBuffer();
			for (int i = 0; i < mTerminalItems.size(); i++) {
				if (mTerminalItems.get(i).getIsCheck()) {

					string.append(mTerminalItems.get(i).getSerial_num());

					if (i != mTerminalItems.size() - 1) {

						string.append(",");
					}
				}
			}
			Intent it = new Intent();
			it.putExtra(TERMINAL_TOTAL, checked);
			it.putExtra(TERMINAL_ARRAY, string.toString());
			setResult(RESULT_OK, it);
			finish();
			break;

		case R.id.terminal_commit:

			loadData();

			break;
		case R.id.posselect:

			Config.selectPOS(TerminalApplySelectActivity.this,
					MyApplication.NewUser.getAgentUserId(),
					new HttpCallback<List<SelectPOS>>(
							TerminalApplySelectActivity.this) {
						@Override
						public void onSuccess(List<SelectPOS> data) {

							final ArrayList<SelectPOS> list = (ArrayList<SelectPOS>) data;
							Intent intent = new Intent(
									TerminalApplySelectActivity.this,
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
		case R.id.channelselect:
			Intent intent = new Intent(TerminalApplySelectActivity.this,
					ApplyChannelActivity.class);
			intent.putExtra(SELECTED_CHANNEL, mChosenChannel);
			intent.putExtra(SELECTED_BILLING, mChosenBilling);
			startActivityForResult(intent, REQUEST_CHOOSE_CHANNEL);
			break;

		case R.id.close:
			this.finish();
			break;

		case R.id.search:
		case R.id.searchLinear:
			Intent iIntent = new Intent(TerminalApplySelectActivity.this,
					GenerateSearch.class);
			iIntent.putExtra(SELECTED_TERMINAL, 3);
			startActivityForResult(iIntent, REQUEST_SEARCH);
			break;
		}
	}

	private void loadData() {

		if (zdhString != null && !"".equals(zdhString)) {

			String[] str = new String[] {};

			str = zdhString.split("\n");

			Config.batchTerminalNum(TerminalApplySelectActivity.this, str,
					MyApplication.NewUser.getAgentId(),
					new HttpCallback<List<TerminalPriceEntity>>(this) {
						@Override
						public void onSuccess(List<TerminalPriceEntity> data) {

							if (data.size() < rows) {
								isLoadMore = false;
							} else {
								isLoadMore = true;
							}
							mTerminalItems.addAll(data);
							if (isSelectAll)
								for (int index = 0; index < mTerminalItems
										.size(); index++) {
									mTerminalItems.get(index).setIsCheck(
											isSelectAll);
								}

							count.addAll(data);
							page++;
							mAdapter.notifyDataSetChanged();
							loadFinished();
							myHandler.sendEmptyMessage(0);

						}

						@Override
						public void onFailure(String message) {
							super.onFailure(message);
						}

						@Override
						public TypeToken<List<TerminalPriceEntity>> getTypeToken() {
							return new TypeToken<List<TerminalPriceEntity>>() {
							};
						}
					});

		}
		// else if (maxPrice < minPrice) {
		//
		// CommonUtil.toastShort(TerminalApplySelectActivity.this,
		// getResources().getString(R.string.terminal_price_error));
		//
		// }
		else {

			Config.screeningTerminalNum(TerminalApplySelectActivity.this,
					posName, mChannelId, 0, 0,
					MyApplication.NewUser.getAgentId(), page + 1, rows,
					searchKey,
					new HttpCallback<List<TerminalPriceEntity>>(this) {
						@Override
						public void onSuccess(List<TerminalPriceEntity> data) {

							if (data.size() < rows) {
								isLoadMore = false;
							} else {
								isLoadMore = true;
							}
							mTerminalItems.addAll(data);
							if (isSelectAll)
								for (int index = 0; index < mTerminalItems
										.size(); index++) {
									mTerminalItems.get(index).setIsCheck(
											isSelectAll);
								}
							count.addAll(data);
							page++;
							mAdapter.notifyDataSetChanged();
							loadFinished();
							myHandler.sendEmptyMessage(0);
						}

						@Override
						public void onFailure(String message) {
							super.onFailure(message);
						}

						@Override
						public TypeToken<List<TerminalPriceEntity>> getTypeToken() {
							return new TypeToken<List<TerminalPriceEntity>>() {
							};
						}
					});

		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (isSelectAll)
					checked = count.size();
				terminalNum.setText(checked + "");
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CHOOSE_POS: {

			posID = data.getIntExtra(SELECTED_ID, 0);
			posName = data.getStringExtra(SELECTED_TITLE);
			selectedpos.setText(posName);
			break;

		}

		case REQUEST_SEARCH: {

			searchKey = data.getStringExtra(SELECTED_TERMINAL);
			break;
		}

		case REQUEST_CHOOSE_CHANNEL: {
			mChosenChannel = (ApplyChannel) data
					.getSerializableExtra(SELECTED_CHANNEL);
			mChosenBilling = (ApplyChannel.Billing) data
					.getSerializableExtra(SELECTED_BILLING);
			mChannelId = mChosenChannel.getId();
			selectedchannel.setText(mChosenChannel.getName());

			break;
		}
		}
	}

	private void loadFinished() {
		mTerminalList.stopRefresh();
		mTerminalList.stopLoadMore();
		mTerminalList.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onRefresh() {
		page = 0;
		// allCheck = false;
		checked = 0;
		mTerminalItems.clear();
		checkboxAll.setChecked(false);
		count.clear();
		mTerminalList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (!isLoadMore) {
			mTerminalList.setPullLoadEnable(false);
			mTerminalList.stopLoadMore();
			CommonUtil.toastShort(this,
					getResources().getString(R.string.no_more_data));
		} else {
			loadData();
		}
	}
}
