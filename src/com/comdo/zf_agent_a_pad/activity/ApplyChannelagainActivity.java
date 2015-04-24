package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_BILLING;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_CHANNEL;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.ApplyChannelagain;
import com.comdo.zf_agent_a_pad.trade.ApplyChannelActivity;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChannel;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class ApplyChannelagainActivity extends Activity {

	private List<ApplyChannelagain> channels = new ArrayList<ApplyChannelagain>();
	private List<ApplyChannelagain.Billing> billings = new ArrayList<ApplyChannelagain.Billing>();
	private ListView channelList;
	private ListView billingList;
	private ChannelListAdapter channelAdapter;
	private BillingListAdapter billingAdapter;
	private ApplyChannelagain chosenChannel;
	private ApplyChannelagain.Billing chosenBilling;
	private int agentId=MyApplication.NewUser.getAgentId();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_channel);
		new TitleMenuUtil(this, getString(R.string.title_apply_choose_channel))
				.show();
		chosenChannel = (ApplyChannelagain) getIntent().getSerializableExtra(
				SELECTED_CHANNEL);
		chosenBilling = (ApplyChannelagain.Billing) getIntent()
				.getSerializableExtra(SELECTED_BILLING);

		channelList = (ListView) findViewById(R.id.apply_channel_list);
		channelList.addFooterView(new View(this));
		channelAdapter = new ChannelListAdapter();
		channelList.setAdapter(channelAdapter);

		billingList = (ListView) findViewById(R.id.apply_billing_list);
		billingList.addFooterView(new View(this));
		billingAdapter = new BillingListAdapter();
		billingList.setAdapter(billingAdapter);

		channelList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						chosenChannel = (ApplyChannelagain) view
								.getTag(R.id.item_id);
						billings.clear();
						/*if (null != chosenChannel.getBillings()) {
							for (ApplyChannelagain.Billing billing : chosenChannel
									.getBillings()) {
								if (null != billing) {
									billings.add(billing);
								}
							}
						}*/

						channelAdapter.notifyDataSetChanged();
						Intent intent = new Intent();
						intent.putExtra(SELECTED_CHANNEL, chosenChannel);
						setResult(RESULT_OK, intent);
						finish();
						//billingAdapter.notifyDataSetChanged();
					}
				});
		billingList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						chosenBilling = (ApplyChannelagain.Billing) view
								.getTag(R.id.item_id);

						Intent intent = new Intent();
						intent.putExtra(SELECTED_CHANNEL, chosenChannel);
						intent.putExtra(SELECTED_BILLING, chosenBilling);
						setResult(RESULT_OK, intent);
						finish();
					}
				});
            	Config.getTerminalPaylist(this,agentId,
        				new HttpCallback<List<ApplyChannelagain>>(this) {
        					@Override
        					public void onSuccess(List<ApplyChannelagain> data) {
        						channels.clear();
        						billings.clear();

        						if (null != data && data.size() > 0) {
        							channels.addAll(data);

        							if (null == chosenBilling) {
        								chosenChannel = channels.get(0);
        								if (null != chosenChannel.getBillings()
        										&& chosenChannel.getBillings().size() > 0) {
        									chosenBilling = chosenChannel.getBillings()
        											.get(0);
        								}
        							}

        							for (ApplyChannelagain channel : channels) {
        								if (channel.getId() == chosenChannel.getId()
        										&& null != chosenChannel.getBillings()) {
        									billings.addAll(chosenChannel.getBillings());
        									break;
        								}
        							}

        							channelAdapter.notifyDataSetChanged();
        							billingAdapter.notifyDataSetChanged();
        						}

        					}

        					@Override
        					public TypeToken<List<ApplyChannelagain>> getTypeToken() {
        						return new TypeToken<List<ApplyChannelagain>>() {
        						};
        					}
        				});
           // }
		
	}

	private class BillingListAdapter extends BaseAdapter {
		private BillingListAdapter() {
		}

		@Override
		public int getCount() {
			return billings.size();
		}

		@Override
		public ApplyChannelagain.Billing getItem(int i) {
			return billings.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyChannelagainActivity.this)
						.inflate(R.layout.simple_list_item, null);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_selected);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ApplyChannelagain.Billing billing = getItem(i);

			if (null != billing) {
				holder.id.setText(billing.id + "");
				holder.name.setText(billing.paychannel);
				if (null != chosenBilling && billing.id == chosenBilling.id) {
					holder.icon.setVisibility(View.VISIBLE);
					holder.icon.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_selected));
				} else {
					holder.icon.setVisibility(View.INVISIBLE);
				}
				convertView.setTag(R.id.item_id, billing);
			}

			return convertView;
		}
	}

	private class ChannelListAdapter extends BaseAdapter {
		private ChannelListAdapter() {
		}

		@Override
		public int getCount() {
			return channels.size();
		}

		@Override
		public ApplyChannelagain getItem(int i) {
			return channels.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyChannelagainActivity.this)
						.inflate(R.layout.simple_list_item, null);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_selected);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ApplyChannelagain channel = getItem(i);

			if (null != channel) {
				holder.icon.setVisibility(View.GONE);
				holder.id.setText(channel.getId() + "");
				holder.name.setText(channel.getPaychannel());
				if (null != chosenChannel
						&& channel.getId() == chosenChannel.getId()) {
					holder.name.setTextColor(getResources().getColor(
							R.color.bgtitle));
					convertView.setBackgroundColor(getResources().getColor(
							R.color.white));
				} else {
					holder.name.setTextColor(getResources().getColor(
							R.color.text292929));
					convertView.setBackgroundColor(getResources().getColor(
							R.color.F3F2F2));
				}
				convertView.setTag(R.id.item_id, channel);
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView id;
	}
}

