package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.AgentDetail;
import com.comdo.zf_agent_a_pad.activity.CreatAgent;
import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AgentManager;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class Agentmanager extends Fragment implements OnClickListener,
		IXListViewListener {
	private View view;
	private List<AgentManager> dataadagent;
	private BaseAdapter agentadapter;
	private XListView xxlistview;
	private TextView tv_default;
	private Button btn_reset, btn_creat_agent, btn_save, close;
	private int page = 0;
	public static Handler myHandler;
	private boolean isrefersh = false;
	private int rows = 10;
	private EditText et_profit;
	private int id = MyApplication.NewUser.getAgentId();
	private float str = 0.0f;
	private AlertDialog dialog;
	private int agentsId = MyApplication.NewUser.getAgentId();
	private LinearLayout eva_nodata;
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
			view = inflater.inflate(R.layout.agentmanager, container, false);
			init();
			getDefaultProfit();
		} catch (InflateException e) {

		}
		return view;
	}

	private void getDefaultProfit() {
		Config.getDefaultProfit(getActivity(), id, new HttpCallback(
				getActivity()) {

			@Override
			public void onSuccess(Object data) {
				str = Float.parseFloat(String.valueOf(data));
				myHandler.sendEmptyMessage(2);

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

	@Override
	public void onStart() {
		super.onStart();
		if (dataadagent.size() != 0) {
			dataadagent.clear();
		}
		getData();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					if (dataadagent.size() != 0) {
						xxlistview.setVisibility(View.VISIBLE);
						eva_nodata.setVisibility(View.GONE);
						onLode();
					} else {
						xxlistview.setVisibility(View.GONE);
						eva_nodata.setVisibility(View.VISIBLE);
					}

					break;
				case 1:
					Intent intent = new Intent(getActivity(), AgentDetail.class);
					intent.putExtra("id",
							dataadagent.get(AgentManagerAdapter.pp).getId());
					startActivity(intent);
					break;
				case 2:
					tv_default.setText(str * 1.0f + "%");
					break;
				default:
					break;
				}
			}

		};
	}

	protected void onLode() {
		xxlistview.stopRefresh();
		xxlistview.stopLoadMore();
		xxlistview.setRefreshTime(Tools.getHourAndMin());

	}

	private void getData() {
		Config.GetAgentList(getActivity(), agentsId, page + 1, rows,
				new HttpCallback<Page<AgentManager>>(getActivity()) {

					@Override
					public void onSuccess(Page<AgentManager> data) {

						if (null == data || data.getList().size() <= 0)
							noMoreData = true;
						if (pullType.equals("onRefresh")) {
							dataadagent.clear();
						}
						dataadagent.addAll(data.getList());
						page++;
						agentadapter.notifyDataSetChanged();
						myHandler.sendEmptyMessage(0);

					}

					@Override
					public void preLoad() {
					}

					@Override
					public void postLoad() {
						onLode();
					}

					@Override
					public TypeToken<Page<AgentManager>> getTypeToken() {
						return new TypeToken<Page<AgentManager>>() {
						};
					}
				});

	}

	private void init() {
		eva_nodata = (LinearLayout) view.findViewById(R.id.eva_nodata);
		btn_creat_agent = (Button) view.findViewById(R.id.btn_creat_agent);
		btn_reset = (Button) view.findViewById(R.id.btn_reset);
		tv_default = (TextView) view.findViewById(R.id.tv_default);
		dataadagent = new ArrayList<AgentManager>();
		agentadapter = new AgentManagerAdapter(dataadagent, getActivity()
				.getBaseContext());
		xxlistview = (XListView) view.findViewById(R.id.list);
		xxlistview.setPullLoadEnable(true);
		xxlistview.setXListViewListener(this);
		xxlistview.setDivider(null);
		xxlistview.setAdapter(agentadapter);
		btn_reset.setOnClickListener(this);
		btn_creat_agent.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset:

			openDialog();
			break;
		case R.id.btn_creat_agent:
			startActivity(new Intent(getActivity(), CreatAgent.class));
			break;
		case R.id.btn_save:

			float f = 0;
			boolean result = et_profit
					.getText()
					.toString()
					.matches(
							"^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
			if (result) {

				f = Float.parseFloat(et_profit.getText().toString());
			} else {

				CommonUtil.toastShort(getActivity(), "请输入正确的费率格式");
				return;
			}
			if (!(f > 0 && f < 100)) {
				CommonUtil.toastShort(getActivity(), "请输入0-100以内的数字");
				return;
			}
			/*
			 * if(!(Integer.parseInt(et_profit.getText().toString())<100&&Integer
			 * .parseInt(et_profit.getText().toString())>0)){
			 * CommonUtil.toastShort(getActivity(), "请输入0-100以内的数字"); return; }
			 */

			resetProfit();
			break;
		case R.id.close:
			dialog.dismiss();
			break;
		default:
			break;
		}

	}

	private void resetProfit() {
		Config.resetprofit(getActivity(),
				Float.parseFloat(et_profit.getText().toString()), agentsId,
				new HttpCallback(getActivity()) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(getActivity(), "设置成功");
						dialog.dismiss();
						getDefaultProfit();
						// tv_default.setText(et_profit.getText().toString());
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

	private void openDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater factory = LayoutInflater.from(getActivity());
		final View textEntryView = factory.inflate(R.layout.reset, null);
		// builder.setTitle("自定义输入框");
		builder.setView(textEntryView);
		btn_save = (Button) textEntryView.findViewById(R.id.btn_save);
		et_profit = (EditText) textEntryView.findViewById(R.id.et_profit);
		close = (Button) textEntryView.findViewById(R.id.close);
		btn_save.setOnClickListener(this);
		close.setOnClickListener(this);
		dialog = builder.create();
		dialog.show();
		// builder.create().show();

	}

	@Override
	public void onRefresh() {
		if (!Tools.isConnect(getActivity())) {
			CommonUtil.toastShort(getActivity(), "网络异常");
			return;
		}
		page = 0;
		pullType = "onRefresh";
		dataadagent.clear();
		noMoreData = false;
		xxlistview.setPullLoadEnable(true);
		getData();

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
