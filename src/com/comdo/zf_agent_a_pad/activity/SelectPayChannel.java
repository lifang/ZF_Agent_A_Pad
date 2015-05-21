package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.FenRunEntity;
import com.comdo.zf_agent_a_pad.entity.FenRunEntity.FenRun;
import com.comdo.zf_agent_a_pad.entity.FenRunEntity.FenRun.Detail;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;
import com.unionpay.mobile.android.utils.i;

public class SelectPayChannel extends BaseActivity {

	private int sonAgentsId, payChannelId, target, defaultID;
	private ListView channelList;
	private LinearLayout ll_paychannel;
	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title, next_sure, tv_name;
	private EditText ed_rate;
	private ImageView titleback_image_back;
	private List<FenRun> list;
	private FenRunEntity fenRunEntity;
	private LayoutInflater mInflater;
	private ChannelListAdapter mAdapter;
	private float percent;
	private String tradeTypeId, profitPercent;
	private StringBuffer sb;
	private List<String> str = new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectpaychannel);
		init();
		loadData();
	}

	private void init() {
		mInflater = LayoutInflater.from(this);
		Intent intent = getIntent();
		sonAgentsId = intent.getIntExtra("id", 0);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		next_sure = (TextView) findViewById(R.id.next_sure);
		titleback_image_back = (ImageView) findViewById(R.id.titleback_image_back);
		channelList = (ListView) findViewById(R.id.paychannel);
		ll_paychannel = (LinearLayout) findViewById(R.id.ll_paychannel);
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SelectPayChannel.this.finish();
			}
		});

		titleback_image_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SelectPayChannel.this.finish();
			}
		});

		titleback_text_title.setText("选择支付通道");
		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SelectPayChannel.this,
						AddPayChannel.class);
				intent.putExtra("id", sonAgentsId);
				startActivity(intent);
				// finish();
			}
		});
		list = new ArrayList<FenRun>();
		mAdapter = new ChannelListAdapter();
		channelList.setAdapter(mAdapter);
		mAdapter.setMySelection(0);
		channelList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					final int position, long arg3) {
				for (int i = 0; i < mAdapter.getCount(); i++) {
					View v = adapterView.getChildAt(i);
					if (position == i) {
						payChannelId = Integer.parseInt(list.get(position)
								.getId());
						v.setBackgroundResource(R.color.white);
						v.findViewById(R.id.delete).setVisibility(View.VISIBLE);
						v.findViewById(R.id.delete).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										target = position;
										delChannel();
									}
								});
					} else {
						v.setBackgroundResource(R.color.paychannel_bg);
						v.findViewById(R.id.delete).setVisibility(View.GONE);
					}

				}
				// view.setBackgroundResource(R.color.white);
				// view.findViewById(R.id.delete).setVisibility(View.VISIBLE);

				ll_paychannel.removeAllViews();

				Log.e("YYYYYYYYYYYYYYYYYYYYYYYYYYY",
						"addEdit++++++++++++++++++++++++++++++++");
				if (list.get(position).getDetail().size() > 0)
					addEdit(ll_paychannel, list.get(position).getDetail());
			}
		});

	}

	private void loadData() {

		Config.getProfitlist(SelectPayChannel.this, sonAgentsId,
				new HttpCallback<FenRunEntity>(SelectPayChannel.this) {

					@Override
					public void onSuccess(FenRunEntity data) {
						fenRunEntity = data;
						list = fenRunEntity.getList();
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public TypeToken<FenRunEntity> getTypeToken() {
						return new TypeToken<FenRunEntity>() {
						};
					}
				});
	}

	private void addEdit(LinearLayout layout, List<Detail> details) {
		str = new ArrayList<String>();
		for (int i = 0; i < details.size(); i++) {

			LinearLayout linearLayout = (LinearLayout) mInflater.inflate(
					R.layout.selectpaychannelrightitem, null);

			tv_name = (TextView) linearLayout.findViewById(R.id.name);
			ed_rate = (EditText) linearLayout.findViewById(R.id.rate);
			tv_name.setText(details.get(i).getTradeTypeName());
			ed_rate.setHint(details.get(i).getPercent() + "%");
			ed_rate.setTag(details.get(i).getTradeTypeId());
			str.add(details.get(i).getTradeTypeId());

			layout.addView(linearLayout);
		}
		Button button = new Button(SelectPayChannel.this);
		button.setBackgroundResource(R.color.bgtitle);
		button.setText("保存");
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		button.setTextColor(getResources().getColor(R.color.white));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0);
		lp.setMargins(100, 20, 0, 20);
		button.setGravity(Gravity.CENTER);
		button.setPadding(40, 5, 40, 5);
		button.setLayoutParams(lp);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				saveFenRun();
			}

		});
		layout.addView(button);
	}

	private void saveFenRun() {
		sb = new StringBuffer();
		for (int i = 0; i < str.size(); i++) {

			EditText editText = (EditText) ll_paychannel.findViewWithTag(str
					.get(i));
			if (editText != null && !"".equals(editText.getText().toString())) {
				if (!RegText.isFloat(editText.getText().toString())) {
					CommonUtil.toastShort(SelectPayChannel.this, "请填写正确的分润");
					return;
				}
				if (Float.parseFloat(editText.getText().toString()) < 0
						|| Float.parseFloat(editText.getText().toString()) > 100) {
					CommonUtil.toastShort(SelectPayChannel.this,
							"请填写0-100之间正确的费率");
					return;
				}
				sb.append(Float.parseFloat(editText.getText().toString())
						* 1.0f + "_" + str.get(i) + "|");
			} else {
				CommonUtil.toastShort(SelectPayChannel.this, "请输入费率");
				return;
			}
		}
		profitPercent = sb.toString();
		Config.saveOrEdit(SelectPayChannel.this, sonAgentsId, payChannelId,
				profitPercent, MyApplication.NewUser.getAgentId(), 0,
				new HttpCallback(SelectPayChannel.this) {

					@Override
					public void onSuccess(Object data) {

						CommonUtil.toastShort(SelectPayChannel.this, "修改成功");
						loadData();
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});

	}

	private void delChannel() {

		Config.delChannel(SelectPayChannel.this,
				MyApplication.NewUser.getAgentId(), payChannelId, sonAgentsId,
				new HttpCallback(SelectPayChannel.this) {

					@Override
					public void onSuccess(Object data) {

						list.remove(target);
						if (list.size() == 0) {
							myHandler.sendEmptyMessage(1);
						}
						// mAdapter.notifyDataSetChanged();

						mAdapter.setMySelection(0);
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});

	}

	private class ChannelListAdapter extends BaseAdapter {
		ViewHolder holder;
		FenRun entity;
		int index;

		private ChannelListAdapter() {
		}

		/*
		 * 设置选中的项
		 * 
		 * @param index
		 */
		public void setMySelection(int index) {
			this.index = index;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public FenRun getItem(int i) {
			return list.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {

			holder = new ViewHolder();

			if (null == convertView) {
				convertView = LayoutInflater.from(SelectPayChannel.this)
						.inflate(R.layout.selectpaychannelitem, null);
				holder.paychannelname = (TextView) convertView
						.findViewById(R.id.paychannelname);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			entity = getItem(i);

			if (null != entity) {

				holder.paychannelname.setText(entity.getChannelName());

			}
			if (i == index) {// 判断当前position是否为选中项

				payChannelId = Integer.parseInt(list.get(index).getId());
				convertView.setBackgroundResource(R.color.white);
				convertView.findViewById(R.id.delete).setVisibility(
						View.VISIBLE);
				convertView.findViewById(R.id.delete).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								target = index;
								index = 999;
								delChannel();
							}
						});
				defaultID = i;
				// myHandler.sendEmptyMessage(0);
			} else {
				convertView.setBackgroundResource(R.color.paychannel_bg);
				convertView.findViewById(R.id.delete).setVisibility(View.GONE);
			}

			return convertView;
		}
	}

	private static class ViewHolder {
		TextView paychannelname;
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			ll_paychannel.removeAllViews();

			switch (msg.what) {
			case 0:
				Log.e("YYYYYYYYYYYYYYYYYYYYYYYYYYY",
						"addEdit++++++++++++++++++++++++++++++++");
				if (list.get(defaultID).getDetail().size() > 0)
					addEdit(ll_paychannel, list.get(defaultID).getDetail());

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	};

}