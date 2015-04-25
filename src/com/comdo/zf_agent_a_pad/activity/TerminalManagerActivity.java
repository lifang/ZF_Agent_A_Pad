package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.REQUEST_ADD;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TYPE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.CANCELED;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.OPENED;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.PART_OPENED;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.STOPPED;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalStatus.UNOPENED;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageApply;
import com.comdo.zf_agent_a_pad.common.PageTerminal;
import com.comdo.zf_agent_a_pad.trade.ApplyDetailActivity;
import com.comdo.zf_agent_a_pad.trade.ApplyListActivity;
import com.comdo.zf_agent_a_pad.trade.entity.TerminalManagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.video.VideoActivity;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class TerminalManagerActivity extends Activity implements
		XListView.IXListViewListener {

	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title;
	private ImageView titleback_image_back, searchView;
	private Button service, bind;
	private LayoutInflater mInflater;
	private XListView mTerminalList;
	private List<TerminalManagerEntity> mTerminalItems;
	private TerminalListAdapter mAdapter;

	private int mStatus = -1;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private int page = 0;
	private int total = 0;
	private final int rows = 10;
	private Boolean isLoadMore = false;
	private static final int REQUEST_SEARCH = 1000;
	private BaseAdapter maAdapter;
	private String searchKey;
	private View.OnClickListener mSyncListener;
	private View.OnClickListener mOpenListener;
	private View.OnClickListener mPosListener;
	private View.OnClickListener mVideoListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_manage);

		initViews();
		initBtnListeners();
		loadData();

	}

	@SuppressLint("NewApi")
	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mTerminalList = (XListView) findViewById(R.id.terminal_list);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		titleback_image_back = (ImageView) findViewById(R.id.titleback_image_back);
		searchView = (ImageView) findViewById(R.id.search);
		service = (Button) findViewById(R.id.apply_button_service);
		spinner = (Spinner) findViewById(R.id.spinner);
		bind = (Button) findViewById(R.id.apply_button_bind);
		mTerminalItems = new ArrayList<TerminalManagerEntity>();
		mAdapter = new TerminalListAdapter();

		LinearLayout listHeader = (LinearLayout) mInflater.inflate(
				R.layout.terminal_list_header, null);

		mTerminalList.addHeaderView(listHeader);

		// init the XListView
		mTerminalList.initHeaderAndFooter();
		mTerminalList.setXListViewListener(this);
		mTerminalList.setPullLoadEnable(true);

		mTerminalList.setAdapter(mAdapter);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.terminalAllStatus));
		adapter.setDropDownViewResource(R.layout.drop_down_item_text);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int status, long arg3) {

				if (status != 0) {
					mStatus = status;
				} else {
					mStatus = -1;
				}

				isLoadMore = false;
				loadData();
				mAdapter.notifyDataSetChanged();
				mTerminalList.setAdapter(mAdapter);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}

	class TerminalListAdapter extends BaseAdapter {

		// private List<TerminalManagerEntity> list;
		//
		// TerminalListAdapter(List<TerminalManagerEntity> list) {
		//
		// this.list = list;
		//
		// }

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
				convertView = mInflater.inflate(R.layout.terminal_list, null);
				holder = new ViewHolder();
				holder.tv_terminal_id = (TextView) convertView
						.findViewById(R.id.tv_terminal_id);
				holder.tv_postype = (TextView) convertView
						.findViewById(R.id.tv_postype);
				holder.tv_paychannel = (TextView) convertView
						.findViewById(R.id.tv_paychannel);
				holder.tv_openstate = (TextView) convertView
						.findViewById(R.id.tv_openstate);
				holder.llButtonContainer = (LinearLayout) convertView
						.findViewById(R.id.terminal_button_container);
				holder.llButtons = (LinearLayout) convertView
						.findViewById(R.id.terminal_buttons);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalManagerEntity item = getItem(i);
			holder.tv_terminal_id.setText(item.getPosPortID());
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
			holder.tv_paychannel.setText(item.getPayChannel());
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			holder.tv_openstate.setText(status[item.getOpenState()]);

			// add buttons according to status
			holder.llButtons.removeAllViews();
			switch (item.getOpenState()) {
			case OPENED:
				holder.llButtonContainer.setVisibility(View.VISIBLE);

				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons, R.string.terminal_button_video,
						item, mVideoListener);
				addButton(holder.llButtons, R.string.terminal_button_pos, item,
						mPosListener);
				break;
			case PART_OPENED:
				holder.llButtonContainer.setVisibility(View.VISIBLE);
				addButton(holder.llButtons, R.string.terminal_button_sync,
						item, mSyncListener);
				addButton(holder.llButtons, R.string.terminal_button_reopen,
						item, mOpenListener);
				addButton(holder.llButtons, R.string.terminal_button_video,
						item, mVideoListener);
				addButton(holder.llButtons, R.string.terminal_button_pos, item,
						mPosListener);
				break;
			case UNOPENED:
				holder.llButtonContainer.setVisibility(View.VISIBLE);

				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons, R.string.terminal_button_open,
						item, mOpenListener);
				addButton(holder.llButtons, R.string.terminal_button_video,
						item, mVideoListener);
				break;
			case CANCELED:
				holder.llButtonContainer.setVisibility(View.GONE);
				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons);
				break;
			case STOPPED:
				holder.llButtonContainer.setVisibility(View.VISIBLE);
				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons);
				addButton(holder.llButtons, R.string.terminal_button_sync,
						item, mSyncListener);
				break;
			}
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(TerminalManagerActivity.this,
							TerminalManagerDetailActivity.class);

					// TODO:
					intent.putExtra(TERMINAL_ID, item.getId());
					intent.putExtra(TERMINAL_NUMBER, item.getPosPortID());
					intent.putExtra(TERMINAL_STATUS, item.getOpenState());

					startActivity(intent);
				}
			});
			return convertView;
		}

		private void addButton(LinearLayout ll, int res, Object tag,
				View.OnClickListener listener) {
			Button button = new Button(TerminalManagerActivity.this);
			button.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.shape_o));
			button.setText(res);
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			button.setTextColor(getResources().getColorStateList(
					R.color.mybutton));
			if (null != tag) {
				button.setTag(tag);
			}
			if (null != listener) {
				button.setOnClickListener(listener);
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 1);
			if (ll.getChildCount() > 0) {
				lp.setMargins(10, 0, 0, 0);
			}
			ll.addView(button, lp);
		}

		private void addButton(LinearLayout ll) {
			Button button = new Button(TerminalManagerActivity.this);
			button.setVisibility(View.INVISIBLE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 1);
			if (ll.getChildCount() > 0) {
				lp.setMargins(10, 0, 0, 0);
			}
			ll.addView(button, lp);
		}
	}

	static class ViewHolder {
		public TextView tv_terminal_id;
		public TextView tv_postype;
		public TextView tv_paychannel;
		public TextView tv_openstate;
		public LinearLayout llButtonContainer;
		public LinearLayout llButtons;
	}

	private void initBtnListeners() {

		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TerminalManagerActivity.this.finish();
			}
		});

		titleback_image_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TerminalManagerActivity.this.finish();
			}
		});

		titleback_text_title
				.setText(getString(R.string.title_terminal_management));

		searchView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent it = new Intent(TerminalManagerActivity.this,
						GenerateSearch.class);
				it.putExtra(SELECTED_TERMINAL, searchKey);
				startActivityForResult(it, REQUEST_SEARCH);

			}
		});
		service.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(TerminalManagerActivity.this,
						TerminalApplyServiceActivity.class), REQUEST_ADD);
			}
		});
		bind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(TerminalManagerActivity.this,
						TerminalApplyBindActivity.class), REQUEST_ADD);
			}
		});
		mSyncListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.toastShort(TerminalManagerActivity.this,
						"synchronising...");
			}
		};
		mOpenListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalManagerEntity item = (TerminalManagerEntity) view
						.getTag();
				Intent intent = new Intent(TerminalManagerActivity.this,
						ApplyDetailActivity.class);
				intent.putExtra(TERMINAL_ID, item.getId());
				intent.putExtra(TERMINAL_NUMBER, item.getPosPortID());
				intent.putExtra(TERMINAL_STATUS, item.getOpenState());
				startActivity(intent);
			}
		};
		mPosListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalManagerEntity item = (TerminalManagerEntity) view
						.getTag();
				Config.findPosPassword(TerminalManagerActivity.this, item
						.getId(),
						new HttpCallback(TerminalManagerActivity.this) {
							@Override
							public void onSuccess(Object data) {
								final String password = data.toString();
								final AlertDialog.Builder builder = new AlertDialog.Builder(
										TerminalManagerActivity.this);
								builder.setMessage(password);
								builder.setPositiveButton(
										getString(R.string.button_copy),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												CommonUtil
														.copy(TerminalManagerActivity.this,
																password);
												dialogInterface.dismiss();
												CommonUtil
														.toastShort(
																TerminalManagerActivity.this,
																getString(R.string.toast_copy_password));
											}
										});
								builder.setNegativeButton(
										getString(R.string.button_cancel),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												dialogInterface.dismiss();
											}
										});
								builder.show();
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
			}
		};
		mVideoListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//添加视频审核
				TerminalManagerEntity item = (TerminalManagerEntity) view
						.getTag();
				
				Intent intent = new Intent(TerminalManagerActivity.this, VideoActivity.class);
				intent.putExtra(TERMINAL_ID, item.getId());
				startActivity(intent);
				
			}
		};
	}

	private void loadData() {

		if (!isLoadMore) {

			page = 0;
		}
		if ((searchKey != null && !"".equals(searchKey)) || mStatus != -1) {

			Config.getTerminalList(TerminalManagerActivity.this,
					MyApplication.NewUser.getAgentId(), page + 1, rows,
					searchKey, mStatus,
					new HttpCallback<PageApply<TerminalManagerEntity>>(
							TerminalManagerActivity.this) {
						@Override
						public void onSuccess(
								PageApply<TerminalManagerEntity> data) {
							if (null != data.getList()) {
								if (!isLoadMore)
									mTerminalItems.clear();
								mTerminalItems.addAll(data.getList());
							}
							total = data.getTotal();
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
			Config.getTerminalApplyList(
					this,
					MyApplication.NewUser.getAgentId(),
					page + 1,
					rows,
					new HttpCallback<PageTerminal<TerminalManagerEntity>>(this) {
						@Override
						public void onSuccess(
								PageTerminal<TerminalManagerEntity> data) {
							if (null != data.getList()) {
								mTerminalItems.addAll(data.getList());
							}
							total = data.getTotal();
							page++;
							mAdapter.notifyDataSetChanged();
							loadFinished();
						}

						@Override
						public void onFailure(String message) {

							super.onFailure(message);
						}

						@Override
						public void preLoad() {
						}

						@Override
						public void postLoad() {
							loadFinished();
						}

						@Override
						public TypeToken<PageTerminal<TerminalManagerEntity>> getTypeToken() {
							return new TypeToken<PageTerminal<TerminalManagerEntity>>() {
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
		case REQUEST_SEARCH: {
			searchKey = data.getStringExtra(SELECTED_TERMINAL);
			loadData();
		}
			break;
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
		mTerminalItems.clear();
		mTerminalList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (mTerminalItems.size() >= total) {
			mTerminalList.setPullLoadEnable(false);
			mTerminalList.stopLoadMore();
			CommonUtil.toastShort(this, getResources().getString(R.string.no_more_data));
		} else {
			isLoadMore = true;
			loadData();
		}
	}

}
