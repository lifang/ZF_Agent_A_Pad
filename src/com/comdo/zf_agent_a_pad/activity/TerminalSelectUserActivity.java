package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_USER;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageMerchane;
import com.comdo.zf_agent_a_pad.entity.User_Info;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalSelectUserActivity extends BaseActivity implements
		View.OnClickListener, XListView.IXListViewListener {

	private XListView mUserList;
	private int page = 0;
	private final int rows = 10;
	private String keyword = "";
	private EditText mUserInput;
	private ImageButton mUserSearch;
	private LinearLayout mResultContainer;
	private List<User_Info> mUserInfo = new ArrayList<User_Info>();
	private UserListAdapter mAdapter;
	private boolean noMoreData = false;
	private String pullType = "loadData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_user);
		new TitleMenuUtil(this, getString(R.string.title_terminal_choose_user))
				.show();

		mUserInput = (EditText) findViewById(R.id.apply_user_input);
		mUserSearch = (ImageButton) findViewById(R.id.apply_user_search);
		mUserSearch.setOnClickListener(this);
		mResultContainer = (LinearLayout) findViewById(R.id.apply_bank_result_container);
		mUserList = (XListView) findViewById(R.id.terminal_user_list);
		mAdapter = new UserListAdapter();
		mUserList.initHeaderAndFooter();
		mUserList.setXListViewListener(this);
		mUserList.setPullLoadEnable(true);
		mUserList.setAdapter(mAdapter);
		mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				User_Info userInfo = (User_Info) view.getTag(R.id.item_id);
				Intent intent = new Intent();
				intent.putExtra(SELECTED_USER, userInfo);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		// loadData();

	}

	private void loadData() {

		Config.userGetMerchants(TerminalSelectUserActivity.this, MyApplication
				.getAgentId(), page + 1, rows, keyword,
				new HttpCallback<PageMerchane<User_Info>>(
						TerminalSelectUserActivity.this) {

					@Override
					public void onSuccess(PageMerchane<User_Info> data) {

						if (null == data || data.getList().size() < rows)
							noMoreData = true;

						if (pullType.equals("onRefresh")) {
							mUserInfo.clear();
						}
						mResultContainer.setVisibility(View.VISIBLE);
						if (null != data && data.getList().size() > 0)
							mUserInfo.addAll(data.getList());

						page++;
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public void preLoad() {
						super.preLoad();
					}

					@Override
					public void postLoad() {
						loadFinished();
						super.postLoad();
					}

					@Override
					public TypeToken<PageMerchane<User_Info>> getTypeToken() {
						return new TypeToken<PageMerchane<User_Info>>() {
						};
					}
				});

		/*
		 * Config.userGetUser(this, MyApplication.NewUser.getAgentUserId(), page
		 * + 1, rows, new HttpCallback<List<UserInfo>>(this) {
		 * 
		 * @Override public void onSuccess(List<UserInfo> data) {
		 * 
		 * for (int i = 0; i < data.size(); i++) { UserInfo userInfo =
		 * data.get(i); Map<String, Object> item = new HashMap<String,
		 * Object>(); item.put("id", userInfo.getId()); item.put("name",
		 * userInfo.getName()); listString.add(userInfo.getName());
		 * items.add(item); } myHandler.sendEmptyMessage(1); }
		 * 
		 * @Override public void onFailure(String message) {
		 * super.onFailure(message); }
		 * 
		 * @Override public TypeToken<List<UserInfo>> getTypeToken() { return
		 * new TypeToken<List<UserInfo>>() { }; } });
		 */

	}

	private class UserListAdapter extends BaseAdapter {
		private UserListAdapter() {
		}

		@Override
		public int getCount() {
			return mUserInfo.size();
		}

		@Override
		public User_Info getItem(int i) {
			return mUserInfo.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(
						TerminalSelectUserActivity.this).inflate(
						R.layout.simple_list_item, null);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			User_Info userInfo = getItem(i);

			if (null != userInfo) {
				holder.id.setText(userInfo.getId() + "");
				holder.name.setText(userInfo.getUsername());

				convertView.setTag(R.id.item_id, userInfo);
			}

			return convertView;
		}
	}

	private static class ViewHolder {
		TextView name;
		TextView id;
	}

	@Override
	public void onClick(View view) {
		keyword = mUserInput.getText().toString();
		if ("".equals(keyword)) {
			CommonUtil.toastShort(TerminalSelectUserActivity.this,
					"请先输入用户名字,再进行搜索");
			return;
		}
		mUserInfo.clear();
		loadData();

	}

	private void loadFinished() {
		mUserList.stopRefresh();
		mUserList.stopLoadMore();
		mUserList.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onRefresh() {
		page = 0;
		pullType = "onRefresh";
		noMoreData = false;
		mUserList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		pullType = "onLoadMore";
		if (noMoreData) {
			mUserList.stopLoadMore();
			mUserList.setPullLoadEnable(false);
			CommonUtil.toastShort(this,
					getResources().getString(R.string.no_more_data));
		} else {
			loadData();
		}

	}
}
