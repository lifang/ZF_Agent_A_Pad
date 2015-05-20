package com.comdo.zf_agent_a_pad.trade;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TITLE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.activity.BaseActivity;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageMerchane;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChooseItem;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class ApplyChooseActivity extends BaseActivity implements
XListView.IXListViewListener{

	private int page = 0;
	private int total = 0;
	private final int rows = 10;
	private int mTerminalId,selectedId;
	private XListView mList;
	private List<ApplyChooseItem> mItems = new ArrayList<ApplyChooseItem>();
	private LayoutInflater mInflater;
	private boolean noMoreData = false;
	private MyListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list);

		mInflater = LayoutInflater.from(this);
		mList = (XListView) findViewById(R.id.list);
		String title = getIntent().getStringExtra(CHOOSE_TITLE);
		mTerminalId =  getIntent().getIntExtra(CHOOSE_ITEMS,0);
		selectedId = getIntent().getIntExtra(SELECTED_ID, 0);

		new TitleMenuUtil(this, title).show();
		mList.initHeaderAndFooter();
		mList.setXListViewListener(this);
		mList.setPullLoadEnable(true);
		mAdapter = new MyListAdapter();
		mList.setAdapter(mAdapter);
		loadData();

		
	}

	
	private void loadData() {
		
		Config.getMerchants(ApplyChooseActivity.this,
				mTerminalId, page+1, rows, "",
				new HttpCallback<PageMerchane<ApplyChooseItem>>(
						ApplyChooseActivity.this) {

					@Override
					public void onSuccess(PageMerchane<ApplyChooseItem> data) {
						if (null == data || data.getList().size() <= 0)
							noMoreData = true;

						mItems.addAll(data.getList());
						page++;
						mAdapter.notifyDataSetChanged();
						loadFinished();
						
					}

					@Override
					public TypeToken<PageMerchane<ApplyChooseItem>> getTypeToken() {

						return new TypeToken<PageMerchane<ApplyChooseItem>>() {
						};
					}
				});
	
	}
	
	class MyListAdapter extends BaseAdapter {
		MyListAdapter() {
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public ApplyChooseItem getItem(int i) {
			return mItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.simple_list_item, null);
				holder = new ViewHolder();
				holder.tvName = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.tvID = (TextView) convertView
						.findViewById(R.id.item_id);
				holder.ivSelected = (ImageView) convertView
						.findViewById(R.id.item_selected);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ApplyChooseItem item = getItem(i);
			holder.tvName.setText(item.getTitle());
			holder.tvID.setText(item.getId()+"");
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView tvId = (TextView) v.findViewById(R.id.item_id);
					TextView tvTitle = (TextView) v.findViewById(R.id.item_name);
					Intent intent = new Intent();
					intent.putExtra(SELECTED_ID,
							Integer.parseInt(tvId.getText().toString()));
					intent.putExtra(SELECTED_TITLE, tvTitle.getText().toString());
					setResult(RESULT_OK, intent);
					finish();
				}
			});
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView tvName;
		public TextView tvID;
		public ImageView ivSelected;
		}

	
	@Override
	public void onRefresh() {
		page = 0;
		mItems.clear();
		mList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (noMoreData) {
			mList.setPullLoadEnable(false);
			mList.stopLoadMore();
			CommonUtil.toastShort(this, getResources().getString(R.string.no_more_data));
		} else {
			loadData();
		}
	}

	private void loadFinished() {
		mList.stopRefresh();
		mList.stopLoadMore();
		mList.setRefreshTime(Tools.getHourAndMin());
	}
}
