package com.comdo.zf_agent_a_pad.trade;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.trade.common.DialogUtil;
import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.common.Page;
import com.comdo.zf_agent_a_pad.trade.entity.TradeRecord;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.trade.widget.XListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.TRADE_TYPE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.CLIENT_NUMBER;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.AGENT_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.AGENT_NAME;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.END_DATE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.SONAGEHNTID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.REQUEST_TRADE_CLIENT;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.REQUEST_TRADE_AGENT;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.START_DATE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.TRADE_RECORD_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.TRADE_TYPE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.CONSUME;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.LIFE_PAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.PHONE_PAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.REPAYMENT;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.TRANSFER;

public class TradeFlowFragment extends Fragment implements
		View.OnClickListener, XListView.IXListViewListener {

	private int mTradeType;

	private View mTradeClient;
	private View mTradeAgent;
	private TextView mTradeClientName;
	private TextView mTradeAgentName;

	private View mTradeStart;
	private TextView mTradeStartDate;
	private View mTradeEnd;
	private TextView mTradeEndDate;

	private Button mTradeSearch;
	private Button mTradeStatistic;
	private LinearLayout mTradeSearchContent;

	private String tradeClientName;
	private String tradeAgentName;
	private int tradeAgentId;
	private String tradeStartDate;
	private String tradeEndDate;

	private LayoutInflater mInflater;
	private XListView mRecordList;
	private TradeRecordListAdapter mAdapter;
	private List<TradeRecord> mRecords;
	private boolean hasSearched = false;

	private int page = 1;
	private boolean canLoadMore = true;
	private int total = 0;

	public static TradeFlowFragment newInstance(int mTradeType) {
		TradeFlowFragment fragment = new TradeFlowFragment();
		Bundle args = new Bundle();
		args.putInt(TRADE_TYPE, mTradeType);
		fragment.setArguments(args);
		return fragment;
	}

	public TradeFlowFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mTradeType = getArguments().getInt(TRADE_TYPE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_trade_flow_list, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initViews(view);

		// restore the saved state
		if (!TextUtils.isEmpty(tradeClientName)) {
			mTradeClientName.setText(tradeClientName);
		}
		if (!TextUtils.isEmpty(tradeAgentName)) {
			mTradeAgentName.setText(tradeAgentName);
		}
		if (!TextUtils.isEmpty(tradeStartDate)) {
			mTradeStartDate.setText(tradeStartDate);
		}
		if (!TextUtils.isEmpty(tradeEndDate)) {
			mTradeEndDate.setText(tradeEndDate);
		}
		if (hasSearched) {
			mTradeSearchContent.setVisibility(View.VISIBLE);
			mAdapter.notifyDataSetChanged();
		}
		toggleButtons();
	}

	private void initViews(View view) {
		mInflater = LayoutInflater.from(getActivity());
		View header = mInflater.inflate(R.layout.fragment_trade_flow, null);
		mTradeClient = header.findViewById(R.id.trade_client);
		mTradeAgent = header.findViewById(R.id.trade_agent);
		mTradeClientName = (TextView) header
				.findViewById(R.id.trade_client_name);
		mTradeAgentName = (TextView) header.findViewById(R.id.trade_agent_name);

		mTradeStart = header.findViewById(R.id.trade_start);
		mTradeStartDate = (TextView) header.findViewById(R.id.trade_start_date);
		mTradeEnd = header.findViewById(R.id.trade_end);
		mTradeEndDate = (TextView) header.findViewById(R.id.trade_end_date);

		mTradeSearch = (Button) header.findViewById(R.id.trade_search);
		mTradeStatistic = (Button) header.findViewById(R.id.trade_statistic);
		mTradeSearchContent = (LinearLayout) header
				.findViewById(R.id.trade_search_content);

		TextView payAccountTxt = (TextView) header
				.findViewById(R.id.payAccountTxt);
		TextView recieveAccountTxt = (TextView) header
				.findViewById(R.id.recieveAccountTxt);

		switch (mTradeType) {
		case TRANSFER:
		case CONSUME:
			break;
		case REPAYMENT:
			recieveAccountTxt.setText("转入账号");
			break;
		case LIFE_PAY:
			payAccountTxt.setText("账户名");
			recieveAccountTxt.setText("账户号码");
			break;
		case PHONE_PAY:
			payAccountTxt.setText("手机号码");
			recieveAccountTxt.setVisibility(View.GONE);
			break;

		default:
			break;
		}

		mTradeClient.setOnClickListener(this);
		mTradeStart.setOnClickListener(this);
		mTradeEnd.setOnClickListener(this);
		mTradeAgent.setOnClickListener(this);
		mTradeSearch.setOnClickListener(this);
		mTradeStatistic.setOnClickListener(this);

		if (null == mRecords) {
			mRecords = new ArrayList<TradeRecord>();
		}

		mRecordList = (XListView) view.findViewById(R.id.trade_record_list);
		mAdapter = new TradeRecordListAdapter();
		mRecordList.addHeaderView(header);
		mRecordList.setAdapter(mAdapter);
		mRecordList.initHeaderAndFooter();
		mRecordList.setXListViewListener(this);
		mRecordList.setPullRefreshEnable(false);
		mRecordList.setAdapter(mAdapter);

		mRecordList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						TradeRecord record = (TradeRecord) view
								.getTag(R.id.trade_status);
						Intent intent = new Intent(getActivity(),
								TradeDetailActivity.class);
						intent.putExtra(TRADE_RECORD_ID, record.getId());
						startActivity(intent);
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_TRADE_CLIENT:
			String clientName = data.getStringExtra(CLIENT_NUMBER);
			mTradeClientName.setText(clientName);
			tradeClientName = clientName;
			toggleButtons();
			break;
		case REQUEST_TRADE_AGENT:
			String agentName = data.getStringExtra(AGENT_NAME);
			int agentId = data.getIntExtra(AGENT_ID, 0);
			mTradeAgentName.setText(agentName);
			tradeAgentName = agentName;
			tradeAgentId = agentId;
			toggleButtons();
			break;
		}
	}

	@Override
	public void onClick(View v) {

		Intent i = null;
		switch (v.getId()) {
		case R.id.trade_client:
			i = new Intent(getActivity(), TradeClientActivity.class);
			i.putExtra(CLIENT_NUMBER, tradeClientName);
			startActivityForResult(i, REQUEST_TRADE_CLIENT);
			break;
		case R.id.trade_agent:
			i = new Intent(getActivity(), TradeAgentActivity.class);
			i.putExtra(AGENT_ID, tradeAgentId);
			startActivityForResult(i, REQUEST_TRADE_AGENT);
			break;
		case R.id.trade_start:
			showDatePicker(tradeStartDate, true);
			break;
		case R.id.trade_end:
			showDatePicker(tradeEndDate, false);
			break;
		case R.id.trade_search:
			hasSearched = true;
			mTradeSearchContent.setVisibility(View.VISIBLE);
			// 点击按钮重拉
			page = 1;
			canLoadMore = true;
			API.getTradeRecordList(getActivity(), MyApplication.NewUser
					.getAgentId(), tradeAgentId, mTradeType, tradeClientName,
					tradeStartDate, tradeEndDate, page, Config.ROWS,
					new HttpCallback<Page<TradeRecord>>(getActivity()) {

						@Override
						public void onSuccess(Page<TradeRecord> data) {
							if (data.getList().size() < Config.ROWS) {
								canLoadMore = false;
							}
							mRecords.clear();
							mRecords.addAll(data.getList());
							mAdapter.notifyDataSetChanged();
							total = data.getTotal();
							mRecordList.setPullLoadEnable(canLoadMore);
						}

						@Override
						public TypeToken<Page<TradeRecord>> getTypeToken() {
							return new TypeToken<Page<TradeRecord>>() {
							};
						}
					});
			break;
		case R.id.trade_statistic:
			Intent intent = new Intent(getActivity(),
					TradeStatisticActivity.class);
			intent.putExtra(TRADE_TYPE, mTradeType);
			intent.putExtra(CLIENT_NUMBER, tradeClientName);
			intent.putExtra(START_DATE, tradeStartDate);
			intent.putExtra(END_DATE, tradeEndDate);
			intent.putExtra(SONAGEHNTID, tradeAgentId);
			startActivity(intent);
			break;
		}
	}

	// 加载更多
	private void getMoreData() {
		page += 1;
		API.getTradeRecordList(getActivity(),  MyApplication.NewUser
				.getAgentId(), tradeAgentId, mTradeType,
				tradeClientName, tradeStartDate, tradeEndDate, page,
				Config.ROWS,
				new HttpCallback<Page<TradeRecord>>(getActivity()) {

					@Override
					public void onSuccess(Page<TradeRecord> data) {

						mRecordList.stopLoadMore();
						mRecords.addAll(data.getList());
						mAdapter.notifyDataSetChanged();
						total = data.getTotal();

					}

					@Override
					public TypeToken<Page<TradeRecord>> getTypeToken() {
						return new TypeToken<Page<TradeRecord>>() {
						};
					}
				});
	}

	/**
	 * enable or disable the buttons
	 * 
	 * @param
	 * @return
	 */
	private void toggleButtons() {
		boolean shouldEnable = !TextUtils.isEmpty(tradeClientName)
				&& !TextUtils.isEmpty(tradeStartDate)
				&& !TextUtils.isEmpty(tradeEndDate)
				&& !TextUtils.isEmpty(tradeAgentName);
		mTradeSearch.setEnabled(shouldEnable);
		mTradeStatistic.setEnabled(shouldEnable);
	}

	/**
	 * show the date picker
	 * 
	 * @param date
	 *            the chosen date
	 * @param isStartDate
	 *            if true to choose the start date, else the end date
	 * @return
	 */
	private void showDatePicker(final String date, final boolean isStartDate) {
		final Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int nowyear = now.get(Calendar.YEAR);
		;
		int nowmonth = now.get(Calendar.MONTH) + 1;
		int nowday = now.get(Calendar.DAY_OF_MONTH);
		final String nowStr = nowyear + "-"
				+ (nowmonth < 10 ? "0" + nowmonth : nowmonth) + "-"
				+ (nowday < 10 ? "0" + nowday : nowday);
		final Calendar c = Calendar.getInstance();
		if (TextUtils.isEmpty(date)) {
			c.setTime(new Date());
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				c.setTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				return new MyDatePickerDialog(getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker datePicker,
									int year, int month, int day) {
								month = month + 1;
								String dateStr = year + "-"
										+ (month < 10 ? "0" + month : month)
										+ "-" + (day < 10 ? "0" + day : day);
								if (isStartDate) {

									if (!TextUtils.isEmpty(tradeEndDate)
											&& dateStr.compareTo(tradeEndDate) > 0) {
										Toast.makeText(getActivity(),
												"开始时间不能大于结束时间",
												Toast.LENGTH_SHORT).show();
										return;
									} else if (dateStr.compareTo(nowStr) >= 0) {
										Toast.makeText(getActivity(),
												"开始时间不能大于当前时间",
												Toast.LENGTH_SHORT).show();
										return;
									}

									mTradeStartDate.setText(dateStr);
									tradeStartDate = dateStr;

								} else {
									if (!TextUtils.isEmpty(tradeStartDate)
											&& dateStr
													.compareTo(tradeStartDate) < 0) {
										Toast.makeText(
												getActivity(),
												getString(R.string.toast_end_date_error),
												Toast.LENGTH_SHORT).show();
										return;
									} else if (dateStr.compareTo(nowStr) > 0) {
										Toast.makeText(getActivity(),
												"截止时间不能大于当前时间",
												Toast.LENGTH_SHORT).show();
										return;
									}

									mTradeEndDate.setText(dateStr);
									tradeEndDate = dateStr;
								}
								toggleButtons();
							}
						}, year, month, day);
			}
		};
		// df.setCancelable(false);
		df.show(getActivity().getSupportFragmentManager(), "DatePicker");
	}

	class TradeRecordListAdapter extends BaseAdapter {
		TradeRecordListAdapter() {
		}

		@Override
		public int getCount() {
			return mRecords.size();
		}

		@Override
		public TradeRecord getItem(int i) {
			return mRecords.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.trade_flow_item, null);
				holder = new ViewHolder();
				holder.tvStatus = (TextView) convertView
						.findViewById(R.id.trade_status);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.trade_time);
				holder.tvAccount = (TextView) convertView
						.findViewById(R.id.trade_account);
				holder.tvReceiveAccount = (TextView) convertView
						.findViewById(R.id.trade_receive_account);
				holder.tvClientNumber = (TextView) convertView
						.findViewById(R.id.trade_client_number);
				holder.tvAmount = (TextView) convertView
						.findViewById(R.id.trade_amount);
				// this 2 text values are according to the trade type
				// holder.tvAccountKey = (TextView)
				// convertView.findViewById(R.id.trade_account_key);
				// holder.tvReceiveAccountKey = (TextView)
				// convertView.findViewById(R.id.trade_receive_account_key);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TradeRecord record = getItem(i);
			convertView.setTag(R.id.trade_status, record);

			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
			df.applyPattern("0.00");

			switch (mTradeType) {
			case TRANSFER:
			case REPAYMENT:
			case CONSUME:
				// holder.tvAccountKey.setText(getString(R.string.trade_pay_account));
				// holder.tvReceiveAccountKey.setText(getString(R.string.trade_receive_account));

				holder.tvAccount.setText(record.getPayFromAccount());
				holder.tvReceiveAccount.setText(record.getPayIntoAccount());
				break;
			case LIFE_PAY:
				// holder.tvAccountKey.setText(getString(R.string.trade_account_name));
				// holder.tvReceiveAccountKey.setText(getString(R.string.trade_account_number));

				holder.tvAccount.setText(record.getAccountName());
				holder.tvReceiveAccount.setText(record.getAccountNumber());
				break;
			case PHONE_PAY:
				// holder.tvAccountKey.setVisibility(View.INVISIBLE);
				// holder.tvReceiveAccountKey.setText(getString(R.string.trade_phone_number));
				holder.tvAccount.setVisibility(View.GONE);
				holder.tvReceiveAccount.setText(record.getPhone());
				break;
			}

			holder.tvStatus.setText(getResources().getStringArray(
					R.array.trade_status)[record.getTradedStatus()]);
			holder.tvTime.setText(record.getTradedTimeStr());
			holder.tvClientNumber.setText(record.getTerminalNumber());

			holder.tvAmount.setText(getString(R.string.notation_yuan)
					+ df.format(record.getAmount() * 1.0f / 100));

			return convertView;
		}
	}

	static class ViewHolder {
		public TextView tvStatus;
		public TextView tvTime;
		public TextView tvAccountKey;
		public TextView tvReceiveAccountKey;
		public TextView tvAccount;
		public TextView tvReceiveAccount;
		public TextView tvClientNumber;
		public TextView tvAmount;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (mRecords.size() >= total) {
			mRecordList.stopLoadMore();
			mRecordList.setPullLoadEnable(false);
			CommonUtil.toastShort(getActivity(), "没有更多数据");
		} else {
			getMoreData();
		}
	}

	class MyDatePickerDialog extends DatePickerDialog {

		public MyDatePickerDialog(Context context, OnDateSetListener callBack,
				int year, int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		@Override
		protected void onStop() {
			// super.onStop();
			// DatePickerDialog 源码当中，onStop()生命周期当中，会调用tryNotifyDateSet();
			// 然后调用onDateSet()会引以onDateSet()方法回调两次
		}
	}
}
