package com.comdo.zf_agent_a_pad.trade;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.REQUEST_DETAIL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.PART_OPENED;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.UNOPENED;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.activity.GenerateSearch;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageApply;
import com.comdo.zf_agent_a_pad.trade.entity.TerminalManagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.video.VideoActivity;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class ApplyListActivity extends Activity implements
		XListView.IXListViewListener {

	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title;
	private ImageView titleback_image_back, searchView;

	private LayoutInflater mInflater;
	private XListView mApplyList;
	private List<TerminalManagerEntity> mTerminalItems;
	private ApplyListAdapter mAdapter;

	private int page = 0;
	private int total = 0;
	private final int rows = 10;
	private boolean noMoreData = false;

	private static final int REQUEST_APPLY_SEARCH = 1111;
	private String searchKey;

	private Boolean isLoadMore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_list);
		initViews();
		initBtnListeners();
		loadData();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mApplyList = (XListView) findViewById(R.id.apply_list);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		titleback_image_back = (ImageView) findViewById(R.id.titleback_image_back);
		searchView = (ImageView) findViewById(R.id.search);

		mTerminalItems = new ArrayList<TerminalManagerEntity>();
		mAdapter = new ApplyListAdapter();

		LinearLayout listHeader = (LinearLayout) mInflater.inflate(
				R.layout.apply_list_header, null);

		mApplyList.addHeaderView(listHeader);
		// View header = new View(this);
		// mApplyList.addHeaderView(header);
		// init the XListView
		mApplyList.initHeaderAndFooter();
		mApplyList.setXListViewListener(this);
		mApplyList.setPullLoadEnable(true);

		mApplyList.setAdapter(mAdapter);
	}

	private void initBtnListeners() {

		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplyListActivity.this.finish();
			}
		});

		titleback_image_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplyListActivity.this.finish();
			}
		});

		titleback_text_title.setText(getString(R.string.title_apply));

		searchView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(ApplyListActivity.this,
						GenerateSearch.class);
				it.putExtra(SELECTED_TERMINAL, searchKey);
				startActivityForResult(it, REQUEST_APPLY_SEARCH);
			}
		});
	}

	private void loadData() {

		if (!isLoadMore) {

			page = 0;
			mTerminalItems.clear();
		}
		if ((searchKey != null && !"".equals(searchKey))) {

			Config.searchApplyList(ApplyListActivity.this,
					MyApplication.NewUser.getAgentId(), page + 1, rows,
					searchKey,
					new HttpCallback<PageApply<TerminalManagerEntity>>(
							ApplyListActivity.this) {
						@Override
						public void onSuccess(
								PageApply<TerminalManagerEntity> data) {
							if (null == data || data.getList().size() <= 0)
								noMoreData = true;
							mTerminalItems.addAll(data.getList());
							page++;
							mAdapter.notifyDataSetChanged();
							loadFinished();
						}

						@Override
						public TypeToken<PageApply<TerminalManagerEntity>> getTypeToken() {
							return new TypeToken<PageApply<TerminalManagerEntity>>() {
							};
						}
					});

		} else {

			Config.getApplyList(this, MyApplication.NewUser.getAgentId(),
					page + 1, rows,
					new HttpCallback<PageApply<TerminalManagerEntity>>(this) {
						@Override
						public void onSuccess(
								PageApply<TerminalManagerEntity> data) {
							if (null == data || data.getList().size() <= 0)
								noMoreData = true;

							mTerminalItems.addAll(data.getList());
							page++;
							mAdapter.notifyDataSetChanged();
							loadFinished();
						}

						@Override
						public void onFailure(String message) {
							super.onFailure(message);
						}

						@Override
						public TypeToken<PageApply<TerminalManagerEntity>> getTypeToken() {
							return new TypeToken<PageApply<TerminalManagerEntity>>() {
							};
						}
					});
		}
	}

	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_APPLY_SEARCH: {
			searchKey = data.getStringExtra(SELECTED_TERMINAL);

			loadData();
		}
			break;
		}
	}

	class ApplyListAdapter extends BaseAdapter {
		ApplyListAdapter() {
		}

		@Override
		public int getCount() {
			return mTerminalItems.size();
		}

		@Override
		public TerminalManagerEntity getItem(int i) {
			return mTerminalItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.apply_list_item, null);
				holder = new ViewHolder();
				holder.tvTerminalNumber = (TextView) convertView
						.findViewById(R.id.apply_terminal_number);
				holder.tv_postype = (TextView) convertView
						.findViewById(R.id.apply_pos_number);
				holder.tvPayChannel = (TextView) convertView
						.findViewById(R.id.apply_pay_channel);
				holder.tvTerminalStatus = (TextView) convertView
						.findViewById(R.id.apply_terminal_status);
				holder.btnOpen = (Button) convertView
						.findViewById(R.id.apply_button_open);
				holder.btnVideo = (Button) convertView
						.findViewById(R.id.apply_button_video);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalManagerEntity item = getItem(i);
			holder.tvTerminalNumber.setText(item.getPosPortID());

			if (item.getPos() != null || item.getPosname() != null) {
				if (item.getPos() == null) {
					holder.tv_postype.setText(item.getPosname());
				} else if (item.getPosname() == null) {
					holder.tv_postype.setText(item.getPos());
				} else {
					holder.tv_postype
							.setText(item.getPos() + item.getPosname());
				}
			} else {
				holder.tv_postype.setText("-");
			}
			if (item.getPayChannel() == null) {

				holder.tvPayChannel.setText("-");
			} else {

				holder.tvPayChannel.setText(item.getPayChannel());
			}
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			holder.tvTerminalStatus.setText(status[item.getOpenState()]);

			if (item.getOpenState() == UNOPENED) {
				holder.btnOpen.setEnabled(true);
				if (!"".equals(item.getAppid())) {
					holder.btnOpen
							.setText(getString(R.string.apply_button_reopen));
				} else {
					holder.btnOpen
							.setText(getString(R.string.apply_button_open));
				}
			} else if (item.getOpenState() == PART_OPENED) {
				holder.btnOpen.setEnabled(true);
				holder.btnOpen.setText(getString(R.string.apply_button_reopen));
			} else {
				holder.btnOpen.setEnabled(false);
			}
			holder.btnOpen.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (item.getOpenState() == UNOPENED
							&& "".equals(item.getAppid())) {

						openDialog(item);

					} else {
						Intent intent = new Intent(ApplyListActivity.this,
								ApplyDetailActivity.class);
						intent.putExtra(TERMINAL_ID, item.getId());
						intent.putExtra(TERMINAL_NUMBER, item.getPosPortID());
						intent.putExtra(TERMINAL_STATUS, item.getOpenState());
						startActivityForResult(intent, REQUEST_DETAIL);
					}

				}
			});
			holder.btnVideo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// 添加视频审核
					Intent intent = new Intent(ApplyListActivity.this,
							VideoActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					startActivity(intent);
				}
			});
			return convertView;
		}
	}

	private void openDialog(final TerminalManagerEntity item) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ApplyListActivity.this);

		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.protocoldialog, null);
		builder.setView(view);

		final AlertDialog dialog = builder.create();
		dialog.show();
		final CheckBox cb = (CheckBox) view.findViewById(R.id.cb);

		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!cb.isChecked()) {

					CommonUtil.toastShort(ApplyListActivity.this,
							"请仔细阅读开通协议，并接受协议");

				} else {

					dialog.dismiss();
					Intent intent = new Intent(ApplyListActivity.this,
							ApplyDetailActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					intent.putExtra(TERMINAL_NUMBER, item.getPosPortID());
					intent.putExtra(TERMINAL_STATUS, item.getOpenState());
					startActivityForResult(intent, REQUEST_DETAIL);
				}

			}
		});
		// dialog.show();

	}

	static class ViewHolder {
		public TextView tvTerminalNumber;
		public TextView tvTerminalStatus;
		public TextView tv_postype;
		public TextView tvPayChannel;
		public Button btnOpen;
		public Button btnVideo;
	}

	@Override
	public void onRefresh() {
		page = 0;
		mTerminalItems.clear();
		mApplyList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (noMoreData) {
			mApplyList.setPullLoadEnable(false);
			mApplyList.stopLoadMore();
			CommonUtil.toastShort(this,
					getResources().getString(R.string.no_more_data));
		} else {
			isLoadMore = true;
			loadData();
		}
	}

	private void loadFinished() {
		mApplyList.stopRefresh();
		mApplyList.stopLoadMore();
		mApplyList.setRefreshTime(Tools.getHourAndMin());
	}
}
