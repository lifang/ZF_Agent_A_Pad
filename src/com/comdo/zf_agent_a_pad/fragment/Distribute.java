package com.comdo.zf_agent_a_pad.fragment;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ARRAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TOTAL;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.DistributeDetail;
import com.comdo.zf_agent_a_pad.activity.TerminalSelectActivity;
import com.comdo.zf_agent_a_pad.adapter.DistributeAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AgentEntity;
import com.comdo.zf_agent_a_pad.entity.DistributeEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class Distribute extends Fragment implements OnClickListener,
IXListViewListener {
	private View view;
	private List<DistributeEntity> datadistribut;
	private BaseAdapter distributeAdapter;
	private XListView xxlistview;
	private Button btn_distrib, btn_quary, btn_confirm;
	public static Handler myHandler;
	private TextView tv_time_left, tv_time_right, tv_choose_terminal;
	private String mMerchantBirthday, left_time, right_time;
	private String[] mMerchantKeys;
	private List<String> list = new ArrayList<String>();
	private List<AgentEntity> datt = new ArrayList<AgentEntity>();
	private Spinner mySpinner, my_diaSpinner;
	private ArrayAdapter<String> adapter;
	private int page = 1;
	private int rows = Config.ROWS;
	private int[] sonAgentId;
	private int cc = 0;
	private boolean isrefersh = false;
	private int a = 1;
	private int[] idd;
	private int mTerminalNum = 0;
	private String mTerminalArray = "";
	public static final int REQUEST_SELECT_CLIENT = 1000;
	private int paychannelId, goodId;
	private String[] serialNums = new String[] {};
	private int agentId = MyApplication.NewUser.getAgentId();
	private int id = MyApplication.NewUser.getId();
	private AlertDialog dialog;
	private Button close;
	private boolean isLoadMore = false;
	private Activity mActivity;
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
			view = inflater.inflate(R.layout.distribute, container, false);
			init();
			getAgentList();
		} catch (InflateException e) {

		}
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("left_time", left_time);
		outState.putString("right_time", right_time);
		tv_time_left.setText(left_time);
		tv_time_right.setText(right_time);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("data", String.valueOf(datadistribut));
		if (datadistribut.size() != 0) {

		}
		if (DistributeDetail.isDri) {
			isrefersh = true;
			a = page;
			rows = a * rows;
			page = 1;
			datadistribut.clear();
			quary();
		}

		if (TerminalSelectActivity.isTerconfirm == true) {
			TerminalSelectActivity.isTerconfirm = false;
			
			mTerminalNum = TerminalSelectActivity.mTerminalNum;
			mTerminalArray = TerminalSelectActivity.mTerminalArray;
			tv_choose_terminal.setText(mTerminalArray);
			paychannelId = TerminalSelectActivity.paychannelId;
			goodId = TerminalSelectActivity.goodId;
		}
		// xxlistview.setAdapter(distributeAdapter);
		/*
		 * if(datadistribut.size()!=0){ datadistribut.clear(); } if(page!=1){
		 * isrefersh=true; a=page; rows=a*rows; page=1; datadistribut.clear(); }
		 * quary();
		 */
	}

	protected void onLoad() {
		xxlistview.stopRefresh();
		xxlistview.stopLoadMore();
		xxlistview.setRefreshTime(Tools.getHourAndMin());

	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	private void getAgentList() {
		Config.getlowerAgentList(mActivity, agentId,
				new HttpCallback<List<AgentEntity>>(mActivity) {

			@Override
			public void onSuccess(List<AgentEntity> data) {

				datt.addAll(data);

				myHandler.sendEmptyMessage(1);
			}

			@Override
			public void onFailure(String message) {
				super.onFailure(message);
			}

			@Override
			public TypeToken<List<AgentEntity>> getTypeToken() {
				return new TypeToken<List<AgentEntity>>() {
				};
			}
		});

	}

	private void init() {

		// tv_agent=(TextView) view.findViewById(R.id.tv_agent);
		btn_quary = (Button) view.findViewById(R.id.btn_quary);
		mMerchantKeys = getResources().getStringArray(
				R.array.my_apply_detail_merchant_keys);
		tv_time_left = (TextView) view.findViewById(R.id.tv_time_left);
		tv_time_right = (TextView) view.findViewById(R.id.tv_time_right);
		btn_distrib = (Button) view.findViewById(R.id.btn_distrib);
		datadistribut = new ArrayList<DistributeEntity>();
		distributeAdapter = new DistributeAdapter(datadistribut, mActivity
				.getBaseContext());
		xxlistview = (XListView) view.findViewById(R.id.list);
		xxlistview.setPullLoadEnable(true);
		xxlistview.setXListViewListener(this);
		xxlistview.setDivider(null);
		btn_distrib.setOnClickListener(this);
		tv_time_left.setOnClickListener(this);
		tv_time_right.setOnClickListener(this);
		btn_quary.setOnClickListener(this);
		/*
		 * mySpinner.setOnItemSelectedListener(new
		 * Spinner.OnItemSelectedListener(){
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) {
		 * tv_agent.setText(adapter.getItem(position)); }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { }
		 * });
		 */
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					Intent intent = new Intent(mActivity,
							DistributeDetail.class);
					intent.putExtra("id",
							datadistribut.get(DistributeAdapter.pp).getId());
					mActivity.startActivity(intent);
					break;
				case 1:
					sonAgentId = new int[datt.size()];
					for (int i = 0; i < datt.size(); i++) {
						list.add(datt.get(i).getCompany_name());
						sonAgentId[i] = datt.get(i).getId();
					}
					mySpinner = (Spinner) view.findViewById(R.id.mySpinner);
					adapter = new ArrayAdapter<String>(mActivity,
							android.R.layout.simple_spinner_item, list);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					mySpinner.setAdapter(adapter);
					mySpinner
					.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
							// tv_agent.setText(adapter.getItem(position));
							mySpinner.setSelection(position);
							cc = position;
						}

						@Override
						public void onNothingSelected(
								AdapterView<?> parent) {

						}

					});
					break;
				case 2:
					onLoad();
					xxlistview.setAdapter(distributeAdapter);
					break;
				case 3:
					quary();
					break;
				default:
					break;
				}
			};
		};
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*
		 * if (resultCode != RESULT_OK) return;
		 */
		System.out.println("");
		switch (requestCode) {
		case REQUEST_SELECT_CLIENT: {
			if (data == null) {
				return;
			}
			mTerminalNum = data.getIntExtra(TERMINAL_TOTAL, 0);
			mTerminalArray = data.getStringExtra(TERMINAL_ARRAY);
			tv_choose_terminal.setText(mTerminalArray);
			paychannelId = data.getIntExtra("paychannelId", 0);
			goodId = data.getIntExtra("goodId", 0);

			break;
		}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_distrib:
			opendialog();
			break;
		case R.id.tv_time_left:

			CommonUtil.showDatePicker(getActivity(), mMerchantBirthday,
					new CommonUtil.OnDateSetListener() {
				@Override
				public void onDateSet(String date) {
					tv_time_left.setText(date);
					left_time = date;
				}
			});

			break;
		case R.id.tv_time_right:
			CommonUtil.showDatePicker(getActivity(), mMerchantBirthday,
					new CommonUtil.OnDateSetListener() {
				@Override
				public void onDateSet(String date) {
					tv_time_right.setText(date);
					right_time = date;
				}
			});
			break;
		case R.id.btn_quary:
			if (tv_time_left.getText().equals("开始日期")
					|| tv_time_right.getText().toString().equals("结束日期")) {
				CommonUtil.toastShort(mActivity, "请选择日期");
				return;
			}
			quary();
			break;
		case R.id.tv_choose_terminal:
			Intent intent = new Intent(mActivity,
					TerminalSelectActivity.class);
			intent.putExtra("flag", "Distribute");
			intent.putExtra(TERMINAL_TOTAL, mTerminalNum);
			intent.putExtra(TERMINAL_ARRAY, mTerminalArray);
			mActivity.startActivityForResult(intent, REQUEST_SELECT_CLIENT);
			break;
		case R.id.btn_confirm:

			if (tv_choose_terminal.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "请选择终端号");
				return;
			}
			distribute();
			dialog.dismiss();

			break;
		case R.id.close:
			dialog.dismiss();
			break;
		default:
			break;
		}

	}

	private void distribute() {
		serialNums = mTerminalArray.split(",");
		Config.distributeGoods(mActivity, sonAgentId[cc], id, paychannelId,
				goodId, serialNums, new HttpCallback(mActivity) {

			@Override
			public void onSuccess(Object data) {
				CommonUtil.toastShort(mActivity, "配货成功");
				myHandler.sendEmptyMessage(3);
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});

	}

	private void quary() {
		if (sonAgentId == null) {
			return;
		}
		if (!isLoadMore) {
			datadistribut.clear();
		}
		Config.getDistributeList(mActivity, agentId, sonAgentId[cc],
				left_time, right_time, page, rows,
				new HttpCallback<Page<DistributeEntity>>(mActivity) {
			@Override
			public void onSuccess(Page<DistributeEntity> data) {
				DistributeDetail.isDri = false;
				isLoadMore = false;
				if (isrefersh) {
					page = a;
					rows = Config.ROWS;
					isrefersh = false;
				}
				if (datadistribut.size() != 0
						&& data.getList().size() == 0) {
					Toast.makeText(mActivity, "没有更多数据!", 1000)
					.show();
				} else {
					idd = new int[data.getList().size()];
					datadistribut.addAll(data.getList());
				}

				myHandler.sendEmptyMessage(2);

			}

			@Override
			public TypeToken<Page<DistributeEntity>> getTypeToken() {
				return new TypeToken<Page<DistributeEntity>>() {
				};
			}
		});

	}

	private void opendialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		LayoutInflater factory = LayoutInflater.from(mActivity);
		final View textEntryView = factory.inflate(R.layout.distributdialog,
				null);
		// builder.setTitle("自定义输入框");
		builder.setView(textEntryView);
		// builder.create().show();
		my_diaSpinner = (Spinner) textEntryView
				.findViewById(R.id.sp_chooseagent);
		tv_choose_terminal = (TextView) textEntryView
				.findViewById(R.id.tv_choose_terminal);
		btn_confirm = (Button) textEntryView.findViewById(R.id.btn_confirm);
		close = (Button) textEntryView.findViewById(R.id.close);
		close.setOnClickListener(this);
		tv_choose_terminal.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		my_diaSpinner.setAdapter(adapter);
		my_diaSpinner
		.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				// tv_agent.setText(adapter.getItem(position));
				my_diaSpinner.setSelection(position);
				// cc=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onRefresh() {
		if (!Tools.isConnect(mActivity)) {
			CommonUtil.toastShort(mActivity, "网络异常");
			return;
		}
		isrefersh = true;
		a = page;
		rows = a * rows;
		page = 1;
		datadistribut.clear();
		quary();

	}

	@Override
	public void onLoadMore() {
		if (!Tools.isConnect(mActivity)) {
			CommonUtil.toastShort(mActivity, "网络异常");
			return;
		}
		isLoadMore = true;
		page += 1;
		quary();

	}
}
