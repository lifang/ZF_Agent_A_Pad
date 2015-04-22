package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TYPE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageApply;
import com.comdo.zf_agent_a_pad.entity.TerminalPriceEntity;
import com.comdo.zf_agent_a_pad.trade.entity.TerminalManagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

;

public class GenerateSearch extends Activity implements OnClickListener,
		IXListViewListener {

	private LinearLayout titleback_linear_back;
	private EditText searchEditText;
	private LinearLayout linear_deletename;
	private TextView next_cancel;
	private LayoutInflater mInflater;
	private XListView mListView;
	private LinearLayout eva_nodata;
	private TerminalAdapter myAdapter;
	private List<TerminalManagerEntity> mEntities = new ArrayList<TerminalManagerEntity>();
	private List<TerminalManagerEntity> mEntities1 = new ArrayList<TerminalManagerEntity>();
	private List<TerminalPriceEntity> mEntities2 = new ArrayList<TerminalPriceEntity>();

	private List<TerminalManagerEntity> mAdapTerminalItems = new ArrayList<TerminalManagerEntity>();

	private int page = 1;
	private int total = 0;
	private final int rows = 10;

	private int mSearchType;
	private int mStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_generatesearch);
		mSearchType = getIntent().getIntExtra(TERMINAL_TYPE, 1);
		mStatus = getIntent().getIntExtra(TERMINAL_STATUS, -1);
		initView();
	}

	private void initView() {

		mInflater = LayoutInflater.from(this);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		linear_deletename = (LinearLayout) findViewById(R.id.linear_deletename);
		next_cancel = (TextView) findViewById(R.id.next_cancel);

		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		myAdapter = new TerminalAdapter();
		mListView.setAdapter(myAdapter);

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);

		titleback_linear_back.setOnClickListener(this);
		linear_deletename.setOnClickListener(this);
		next_cancel.setOnClickListener(this);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				intent.putExtra(SELECTED_TERMINAL, mAdapTerminalItems.get(position)
						.getPosPortID());
				if(mSearchType==3){
					intent.putExtra(SELECTED_TERMINAL, mEntities2.get(position));
				}
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					linear_deletename.setVisibility(View.VISIBLE);
				} else {
					linear_deletename.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!StringUtil.isNull(searchEditText.getText().toString())) {
					page = 1;
					mEntities.clear();
					mListView.setPullLoadEnable(true);
					loadData();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			finish();
			break;
		case R.id.linear_deletename:
			searchEditText.setText("");
			break;
		case R.id.next_cancel:
			finish();
			break;
		default:
			break;
		}
	}

	// 列表数据
	private void loadData() {
		String searchKey = searchEditText.getText().toString().trim();
		
		mAdapTerminalItems = new ArrayList<TerminalManagerEntity>();

		switch (mSearchType) {

		case 1:

			Config.searchApplyList(GenerateSearch.this, 1, page, rows,
					searchKey, new HttpCallback<PageApply<TerminalManagerEntity>>(
							GenerateSearch.this) {
						@Override
						public void onSuccess(PageApply<TerminalManagerEntity> data) {
							if (null != data.getList()) {
								mEntities.addAll(data.getList());
								mAdapTerminalItems.addAll(mEntities);
							}
							page++;
							total = data.getTotal();
							onLoad();

							if (mEntities.size() == 0) {
								mListView.setVisibility(View.GONE);
								eva_nodata.setVisibility(View.VISIBLE);
							} else {
								mListView.setVisibility(View.VISIBLE);
								eva_nodata.setVisibility(View.GONE);
							}
							myAdapter.notifyDataSetChanged();
						}

						@Override
						public TypeToken<PageApply<TerminalManagerEntity>> getTypeToken() {
							return new TypeToken<PageApply<TerminalManagerEntity>>() {
							};
						}
					});

			break;

		case 2:

			Config.getTerminalList(GenerateSearch.this, 1, page, rows,
					searchKey, mStatus,
					new HttpCallback<PageApply<TerminalManagerEntity>>(
							GenerateSearch.this) {
						@Override
						public void onSuccess(PageApply<TerminalManagerEntity> data) {
							if (null != data.getList()) {
								mEntities1.addAll(data.getList());

								mAdapTerminalItems.addAll(mEntities1);
							}
							page++;
							total = data.getTotal();
							onLoad();

							if (mEntities1.size() == 0) {
								mListView.setVisibility(View.GONE);
								eva_nodata.setVisibility(View.VISIBLE);
							} else {
								mListView.setVisibility(View.VISIBLE);
								eva_nodata.setVisibility(View.GONE);
							}
							myAdapter.notifyDataSetChanged();
						}

						@Override
						public TypeToken<PageApply<TerminalManagerEntity>> getTypeToken() {
							return new TypeToken<PageApply<TerminalManagerEntity>>() {
							};
						}
					});

			break;
		case 3:

			String[] str = new String[] { searchKey };

			Config.batchTerminalNum(GenerateSearch.this, str,
					new HttpCallback<List<TerminalPriceEntity>>(
							GenerateSearch.this) {
						@Override
						public void onSuccess(List<TerminalPriceEntity> data) {
							if (null != data) {
								mEntities2.addAll(data);
								
								for(TerminalPriceEntity entity :mEntities2){
									
									TerminalManagerEntity item = new TerminalManagerEntity();
									item.setId(entity.getId());
									item.setPosPortID(entity.getSerial_num());
									mAdapTerminalItems.add(item);
								}
							}
							page++;
							total = mAdapTerminalItems.size();
							onLoad();

							if (mAdapTerminalItems.size() == 0) {
								mListView.setVisibility(View.GONE);
								eva_nodata.setVisibility(View.VISIBLE);
							} else {
								mListView.setVisibility(View.VISIBLE);
								eva_nodata.setVisibility(View.GONE);
							}
							myAdapter.notifyDataSetChanged();
						}

						@Override
						public TypeToken<List<TerminalPriceEntity>> getTypeToken() {
							return new TypeToken<List<TerminalPriceEntity>>() {
							};
						}
					});
			break;
		}

	}

	class TerminalAdapter extends BaseAdapter {

		public TerminalAdapter() {
		}

		@Override
		public int getCount() {
			return mAdapTerminalItems.size();
		}

		@Override
		public TerminalManagerEntity getItem(int position) {
			return mAdapTerminalItems.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.item_list_stock_search, parent, false);
				viewHolder.nameTextView = (TextView) convertView
						.findViewById(R.id.nameTextViews);
				viewHolder.type_pop = (RelativeLayout) convertView
						.findViewById(R.id.type_pop);
				convertView.setTag(viewHolder);

			}

			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.nameTextView.setText(mAdapTerminalItems.get(position).getId()+"");
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView nameTextView;
		public RelativeLayout type_pop;
	}

	@Override
	public void onRefresh() {
		page = 1;
		mAdapTerminalItems.clear();
		mListView.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (mAdapTerminalItems.size() >= total) {
			mListView.setPullLoadEnable(false);
			mListView.stopLoadMore();
			Toast.makeText(GenerateSearch.this, "no more data",
					Toast.LENGTH_SHORT).show();
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
