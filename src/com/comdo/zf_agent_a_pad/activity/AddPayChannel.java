package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.TerminalManagerActivity.ViewHolder;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.FenRunChannelEntity;
import com.comdo.zf_agent_a_pad.entity.FenRunChannelEntity.FenRunChannel;
import com.comdo.zf_agent_a_pad.entity.FenRunEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddPayChannel extends BaseActivity implements OnClickListener {

	private LayoutInflater mInflater;
	private BaseAdapter mAdapter;
	private Spinner spinner;
	private LinearLayout ll_selectpaychannel, ll_addpaychannel;
	private Button close, confirm;
	private List<FenRunChannelEntity.FenRunChannel> list = new ArrayList<FenRunChannelEntity.FenRunChannel>();
	private FenRunChannelEntity fenRunChannelEntity;
	private List<FenRunChannelEntity.FenRunChannel> listType = new ArrayList<FenRunChannelEntity.FenRunChannel>();
	private FenRunChannelEntity fenRunChannelEntityType;
	private PayChannelAdapter payChannelAdapter;
	private ListView paylist;
	private TextView tv_paychannel;
	private int id, sonAgentsId;
	private Button btn_commit;
	private String profitPercent;
	private StringBuffer sb;
	private List<String> str = new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpaychannel);
		init();
		loadData();
	}

	private void loadData() {

		Config.getChannellist(AddPayChannel.this,
				new HttpCallback<FenRunChannelEntity>(AddPayChannel.this) {

					@Override
					public void onSuccess(FenRunChannelEntity data) {
						fenRunChannelEntity = data;
						list = fenRunChannelEntity.getList();
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public TypeToken<FenRunChannelEntity> getTypeToken() {
						return new TypeToken<FenRunChannelEntity>() {
						};
					}
				});
	}

	private void init() {
		mInflater = LayoutInflater.from(this);
		Intent intent = getIntent();
		sonAgentsId = intent.getIntExtra("id", 0);
		close = (Button) findViewById(R.id.close);
		ll_selectpaychannel = (LinearLayout) findViewById(R.id.ll_selectpaychannel);
		ll_selectpaychannel.setVisibility(View.VISIBLE);
		ll_addpaychannel = (LinearLayout) findViewById(R.id.ll_addpaychannel);
		ll_addpaychannel.setVisibility(View.GONE);
		confirm = (Button) findViewById(R.id.confirm);
		spinner = (Spinner) findViewById(R.id.spinner);
		// 自定义适配器
		mAdapter = new BaseAdapter() {

			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public FenRunChannelEntity.FenRunChannel getItem(int arg0) {
				return list.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				return arg0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout layout = (LinearLayout) mInflater.inflate(
						R.layout.drop_down_item, null);
				TextView tv = (TextView) layout.findViewById(R.id.text);
				tv.setText((String) getItem(position).getName());
				return layout;
			}

		};
		spinner.setAdapter(mAdapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				id = list.get(arg2).getId();
				tv_paychannel.setText(list.get(arg2).getName());
				getTradeList(id);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		close.setOnClickListener(this);
		confirm.setOnClickListener(this);

		paylist = (ListView) findViewById(R.id.paylist);
		payChannelAdapter = new PayChannelAdapter();
		paylist.setAdapter(payChannelAdapter);
		tv_paychannel = (TextView) findViewById(R.id.tv_paychannel);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		btn_commit.setOnClickListener(this);

	}

	private void getTradeList(int id) {

		Config.getTradelist(AddPayChannel.this, id,
				new HttpCallback<FenRunChannelEntity>(AddPayChannel.this) {

					@Override
					public void onSuccess(FenRunChannelEntity data) {
						fenRunChannelEntityType = data;
						listType = data.getList();
						payChannelAdapter.notifyDataSetChanged();
					}

					@Override
					public TypeToken<FenRunChannelEntity> getTypeToken() {
						return new TypeToken<FenRunChannelEntity>() {
						};
					}
				});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.close:
			finish();
			break;

		case R.id.confirm:
			ll_selectpaychannel.setVisibility(View.GONE);
			ll_addpaychannel.setVisibility(View.VISIBLE);
			new TitleMenuUtil(AddPayChannel.this, "添加分润").show();
			break;
		case R.id.btn_commit:
			commit();
			break;
		default:
			break;
		}
	}

	private void commit() {
		sb = new StringBuffer();
		for (int i = 0; i < listType.size(); i++) {
			// if (!RegText.isFloat(listType.get(i).getPercent())) {
			// CommonUtil.toastShort(AddPayChannel.this, "请填写正确的费率格式");
			// return;
			// }
			if (listType.get(i).getPercent() < 0
					|| listType.get(i).getPercent() > 100) {
				CommonUtil.toastShort(AddPayChannel.this, "请填写0-100之间正确的费率");
				return;
			}

			sb.append(listType.get(i).getPercent() * 1.0f + "_"
					+ listType.get(i).getId() + "|");
		}
		profitPercent = sb.toString();

		Config.saveOrEdit(AddPayChannel.this, sonAgentsId, id, profitPercent,
				MyApplication.NewUser.getAgentId(), 1, new HttpCallback(
						AddPayChannel.this) {

					@Override
					public void onSuccess(Object data) {

						CommonUtil.toastShort(AddPayChannel.this, "添加成功");
						finish();
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});

	}

	class PayChannelAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listType.size();
		}

		@Override
		public FenRunChannel getItem(int i) {
			return listType.get(i);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int i, View convertView, ViewGroup arg2) {
			ViewHolder holder;
			// if (convertView == null) {
			convertView = mInflater.inflate(R.layout.paylistitem, null);
			holder = new ViewHolder();
			holder.paychannel = (TextView) convertView
					.findViewById(R.id.paychannel);
			holder.paypercent = (EditText) convertView
					.findViewById(R.id.paypercent);
			// holder.paypercent.setTag(R.id.paypercent);
			// convertView.setTag(holder);
			// } else {
			// holder = (ViewHolder) convertView.getTag();
			// }

			FenRunChannel item = getItem(i);

			holder.paychannel.setText(item.getTrade_value());
			holder.paypercent.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int arg1, int arg2,
						int arg3) {
					if (!StringUtil.isNull(s.toString())) {
						listType.get(i).setPercent(
								Float.parseFloat(s.toString()));
					}

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (!RegText.isFloat(s.toString())) {
						CommonUtil.toastShort(AddPayChannel.this, "请填写正确的费率格式");
						return;
					}
				}
			});

			return convertView;
		}
	};

	static class ViewHolder {

		private TextView paychannel;
		private EditText paypercent;
	}
}
