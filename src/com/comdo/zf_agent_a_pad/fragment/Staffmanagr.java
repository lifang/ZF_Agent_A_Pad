package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.comdo.zf_agent_a_pad.activity.CreatStaff;
import com.comdo.zf_agent_a_pad.activity.StallmanagerDetail;
import com.comdo.zf_agent_a_pad.adapter.StaffmanagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.StaffmanagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class Staffmanagr extends Fragment implements OnClickListener,
		IXListViewListener {
	private View view;
	public static List<StaffmanagerEntity> datastaff;
	private BaseAdapter staffmanageradapter;
	private XListView xxlistview;
	private Button btn_creatstaff;
	public static Handler myHandler;
	private boolean isrefersh = false;
	private int page = 0;
	private int rows = 10;
	private int agentId = MyApplication.NewUser.getAgentId();
	private Activity mActivity;
	private boolean noMoreData = false;
	private String pullType = "loadData";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// view = inflater.inflate(R.layout.f_main,container,false);

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.staffmanagr, container, false);
			init();

		} catch (InflateException e) {

		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (datastaff.size() != 0) {
			datastaff.clear();
		}
		getData();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					break;
				case 1:
					Intent intent = new Intent(getActivity(),
							StallmanagerDetail.class);
					intent.putExtra("idd", datastaff
							.get(StaffmanagerAdapter.pp).getId());
					startActivity(intent);
					break;
				case 2:

					delectone();
					break;
				default:
					break;
				}
			}
		};

	}

	protected void delectone() {

		final com.comdo.zf_agent_a_pad.util.AlertDialog ad = new com.comdo.zf_agent_a_pad.util.AlertDialog(
				getActivity());
		ad.setTitle("提示");
		ad.setMessage("确认删除?");
		ad.setPositiveButton("取消", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ad.dismiss();
			}
		});
		ad.setNegativeButton("确定", new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				Config.delectStaff(getActivity(),
						datastaff.get(StaffmanagerAdapter.pp).getId(), agentId,
						new HttpCallback(getActivity()) {

							@Override
							public void onSuccess(Object data) {
								CommonUtil.toastShort(getActivity(), "删除成功");
								page = 0;
								datastaff.clear();
								getData();
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
				ad.dismiss();
			}

		});

	}

	private void getData() {

		Config.getStaffList(mActivity, agentId, page + 1, rows,
				new HttpCallback<Page<StaffmanagerEntity>>(mActivity) {

					@Override
					public void onSuccess(Page<StaffmanagerEntity> data) {
						if (null == data || data.getList().size() <= 0)
							noMoreData = true;
						if (pullType.equals("onRefresh")) {
							datastaff.clear();
						}
						datastaff.addAll(data.getList());
						page++;
						staffmanageradapter.notifyDataSetChanged();

					}

					@Override
					public void preLoad() {
					}

					@Override
					public void postLoad() {
						loadFinished();
					}

					@Override
					public TypeToken<Page<StaffmanagerEntity>> getTypeToken() {
						return new TypeToken<Page<StaffmanagerEntity>>() {
						};
					}
				});

	}

	private void init() {
		btn_creatstaff = (Button) view.findViewById(R.id.btn_creatstaff);
		datastaff = new ArrayList<StaffmanagerEntity>();
		staffmanageradapter = new StaffmanagerAdapter(datastaff, getActivity()
				.getBaseContext());
		xxlistview = (XListView) view.findViewById(R.id.list);
		xxlistview.setPullLoadEnable(true);
		xxlistview.setXListViewListener(this);
		xxlistview.setDivider(null);
		xxlistview.setAdapter(staffmanageradapter);
		btn_creatstaff.setOnClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_creatstaff:
			Intent intent = new Intent(getActivity(), CreatStaff.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void onRefresh() {
		if (!Tools.isConnect(getActivity())) {
			CommonUtil.toastShort(getActivity(), "网络异常");
			return;
		}
		page = 0;
		pullType = "onRefresh";
		noMoreData = false;
		xxlistview.setPullLoadEnable(true);
		getData();

	}

	private void loadFinished() {
		xxlistview.stopRefresh();
		xxlistview.stopLoadMore();
		xxlistview.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onLoadMore() {
		if (!Tools.isConnect(getActivity())) {
			CommonUtil.toastShort(getActivity(), "网络异常");
			return;
		}
		pullType = "onLoadMore";
		if (noMoreData) {
			xxlistview.setPullLoadEnable(false);
			xxlistview.stopLoadMore();
			CommonUtil.toastShort(getActivity(),
					getResources().getString(R.string.no_more_data));
		} else {
			getData();
		}
	}
}
