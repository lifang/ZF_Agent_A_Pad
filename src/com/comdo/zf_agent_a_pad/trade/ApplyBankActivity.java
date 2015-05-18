package com.comdo.zf_agent_a_pad.trade;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyBank;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_BANK;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ID;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyBankActivity extends Activity implements
		View.OnClickListener, XListView.IXListViewListener {

	private String mTerminalNumber, keyword = "";

	private EditText mBankInput;
	private ImageButton mBankSearch;
	private LinearLayout mResultContainer;

	private List<ApplyBank> mAllBanks = new ArrayList<ApplyBank>();
	private List<ApplyBank> mBanks = new ArrayList<ApplyBank>();
	private XListView mBankList;
	private BankListAdapter mAdapter;
	private ApplyBank mChosenBank;
	private int page = 0;
	private int pageSize = 20;
	private int mTerminalId;
	private Boolean isLoadMore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_bank);
		new TitleMenuUtil(this, getString(R.string.title_apply_choose_bank))
				.show();
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);
		mChosenBank = (ApplyBank) getIntent().getSerializableExtra(
				SELECTED_BANK);

		mBankInput = (EditText) findViewById(R.id.apply_bank_input);
		mBankSearch = (ImageButton) findViewById(R.id.apply_bank_search);
		mBankSearch.setOnClickListener(this);
		mResultContainer = (LinearLayout) findViewById(R.id.apply_bank_result_container);
		mBankList = (XListView) findViewById(R.id.apply_bank_list);
		mAdapter = new BankListAdapter();
		mBankList.initHeaderAndFooter();
		mBankList.setXListViewListener(this);
		mBankList.setPullLoadEnable(true);
		mBankList.setAdapter(mAdapter);

		mBankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				ApplyBank bank = (ApplyBank) view.getTag(R.id.item_id);
				Intent intent = new Intent();
				intent.putExtra(SELECTED_BANK, bank);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		 loadData();
	}

	private void loadData() {

		// TODO:
		Config.getApplyBankList(this, page + 1, keyword, pageSize, String.valueOf(mTerminalId),
				new HttpCallback<List<ApplyBank>>(this) {
					@Override
					public void onSuccess(List<ApplyBank> data) {
						mResultContainer.setVisibility(View.VISIBLE);
						mAllBanks = data;
						mBanks.clear();
						if (null != data && data.size() > 0)
							mBanks.addAll(data);
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public TypeToken<List<ApplyBank>> getTypeToken() {
						return new TypeToken<List<ApplyBank>>() {
						};
					}
				});
	}

	@Override
	public void onClick(View view) {
		keyword = mBankInput.getText().toString();
		mBanks.clear();
		loadData();
	}

	private class BankListAdapter extends BaseAdapter {
		private BankListAdapter() {
		}

		@Override
		public int getCount() {
			return mBanks.size();
		}

		@Override
		public ApplyBank getItem(int i) {
			return mBanks.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyBankActivity.this)
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

			ApplyBank bank = getItem(i);

			if (null != bank) {
				holder.id.setText(bank.getCode());
				holder.name.setText(bank.getName());
				if (null != mChosenBank
						&& bank.getCode().equals(mChosenBank.getCode())) {
					holder.icon.setVisibility(View.VISIBLE);
					holder.icon.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_selected));
				} else {
					holder.icon.setVisibility(View.INVISIBLE);
				}
				convertView.setTag(R.id.item_id, bank);
			}

			return convertView;
		}
	}

	private static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView id;
	}

	private void loadFinished() {
		mBankList.stopRefresh();
		mBankList.stopLoadMore();
		mBankList.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onRefresh() {
		page = 0;
		mBanks.clear();
		mBankList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (!isLoadMore) {
			mBankList.setPullLoadEnable(false);
			mBankList.stopLoadMore();
			CommonUtil.toastShort(this,
					getResources().getString(R.string.no_more_data));
		} else {
			loadData();
		}
	}
}
