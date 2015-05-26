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

import com.comdo.zf_agent_a_pad.activity.TerminalSelectActivity;
import com.comdo.zf_agent_a_pad.activity.TransgoodsDetail;
import com.comdo.zf_agent_a_pad.adapter.TransgoodsAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AgentEntity;
import com.comdo.zf_agent_a_pad.entity.TransgoodsEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.MyToast;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class Transgoods extends Fragment implements OnClickListener,
IXListViewListener {
	private View view;
	private List<TransgoodsEntity> datatrans;
	private BaseAdapter transAdapter;
	private XListView xxlistview;
	private Button btn_transferobject, btn_quary, btn_confirm;
	public static Handler myHandler;
	private List<String> list = new ArrayList<String>();
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;
	private List<AgentEntity> datt = new ArrayList<AgentEntity>();
	public static int[] sonAgentId;
	private int cc = 0;
	public static int from = 0;
	private int to = 0;
	private TextView tv_time_left, tv_time_right, tv_choose_terminal;
	private String mMerchantBirthday, left_time, right_time;
	private int page = 1;
	private int rows = Config.ROWS;
	private int[] idd;
	private boolean isrefersh = false;
	private int a = 1;
	private Spinner my_diaSpinner_from, my_diaSpinner_to;
	public static final int REQUEST_SELECT_CLIENT = 1000;
	private int mTerminalNum = 0;
	private String mTerminalArray = "";
	private int paychannelId, goodId;
	private String[] serialNums = new String[] {};
	private int agent = MyApplication.NewUser.getAgentId();
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
			view = inflater.inflate(R.layout.transgoods, container, false);
			init();
			getAgentList();
		
		} catch (InflateException e) {

		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					Intent intent = new Intent(mActivity,
							TransgoodsDetail.class);
					intent.putExtra("id", datatrans.get(TransgoodsAdapter.pp)
							.getId());
					mActivity.startActivity(intent);
					break;
				case 1:
					for (int i = 0; i < datt.size(); i++) {
						Log.e("lenth", sonAgentId.length + "");
						Log.e("i", i + "");
						Log.e("datt", String.valueOf(datt));
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
					xxlistview.setAdapter(transAdapter);

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
	public void onResume() {
		super.onResume();
		if (TransgoodsDetail.isTra) {
			isrefersh = true;
			a = page;
			rows = a * rows;
			page = 1;
			datatrans.clear();
			quary();
		}
		if (TerminalSelectActivity.isTerconfirm == true) {
			TerminalSelectActivity.isTerconfirm = false;
			opendialog();
			mTerminalNum = TerminalSelectActivity.mTerminalNum;
			mTerminalArray = TerminalSelectActivity.mTerminalArray;
			paychannelId = TerminalSelectActivity.paychannelId;
			goodId = TerminalSelectActivity.goodId;
			if (!StringUtil.isNull(mTerminalArray)) {
				tv_choose_terminal.setText(mTerminalArray);
			}
		}
	}

	protected void onLoad() {
		xxlistview.stopRefresh();
		xxlistview.stopLoadMore();
		xxlistview.setRefreshTime(Tools.getHourAndMin());

	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(getActivity());
		mActivity = getActivity();
	}
	private void getAgentList() {
		if (datt.size() != 0) {
			datt.clear();
		}
		if (list.size() != 0) {
			list.clear();
		}
		Config.getlowerAgentList(mActivity, agent,
				new HttpCallback<List<AgentEntity>>(mActivity) {

			@Override
			public void onSuccess(List<AgentEntity> data) {
				datt.addAll(data);
				sonAgentId = new int[data.size()];
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*
		 * if (resultCode != RES) return;
		 */
		switch (requestCode) {
		case REQUEST_SELECT_CLIENT: {
			if (data == null) {
				return;
			}

			mTerminalNum = data.getIntExtra(TERMINAL_TOTAL, 0);
			mTerminalArray = data.getStringExtra(TERMINAL_ARRAY);
			//tv_choose_terminal.setText(mTerminalArray);
			paychannelId = data.getIntExtra("paychannelId", 0);
			goodId = data.getIntExtra("goodId", 0);

			break;
		}

		}
	}

	private void init() {

		btn_quary = (Button) view.findViewById(R.id.btn_quary);
		tv_time_left = (TextView) view.findViewById(R.id.tv_time_left);
		tv_time_right = (TextView) view.findViewById(R.id.tv_time_right);
		btn_transferobject = (Button) view
				.findViewById(R.id.btn_transferobject);

		datatrans = new ArrayList<TransgoodsEntity>();
		transAdapter = new TransgoodsAdapter(datatrans, mActivity
				.getBaseContext());
		xxlistview = (XListView) view.findViewById(R.id.list);
		xxlistview.setPullLoadEnable(true);
		xxlistview.setXListViewListener(this);
		xxlistview.setDivider(null);
		btn_transferobject.setOnClickListener(this);
		tv_time_left.setOnClickListener(this);
		tv_time_right.setOnClickListener(this);
		btn_quary.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_transferobject:
			to = 0;
			from = 0;
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
			if (datatrans.size() != 0) {
				datatrans.clear();
			}
			quary();

			break;
		case R.id.tv_choose_terminal:
			dialog.dismiss();
			TerminalSelectActivity.isFromTrans = true;
			Intent intent = new Intent(mActivity,
					TerminalSelectActivity.class);
			intent.putExtra("flag", "Transgoods");
			intent.putExtra("sonAgentId", sonAgentId[from]);
			intent.putExtra(TERMINAL_TOTAL, mTerminalNum);
			intent.putExtra(TERMINAL_ARRAY, mTerminalArray);

			mActivity.startActivityForResult(intent, REQUEST_SELECT_CLIENT);
			break;
		case R.id.btn_confirm:

			if (tv_choose_terminal.getText().toString().equals("")) {
				MyToast.showToast(mActivity, "请选择终端号");
			}else {
				if (from == to){ 
					MyToast.showToast(mActivity, "请选择不同的代理商进行调货");
				}else{ 
					trans();
					dialog.dismiss();
				}
			}
			break;
		case R.id.close:
			dialog.dismiss();
			break;
		default:
			break;
		}

	}

	private void trans() {
		serialNums = mTerminalArray.split(",");
		Config.transGoods(mActivity, sonAgentId[to], sonAgentId[from], id,
				serialNums, new HttpCallback(mActivity) {

			@Override
			public void onSuccess(Object data) {
				CommonUtil.toastShort(mActivity, "调货成功");
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
			datatrans.clear();
		}
		Config.getTranslist(mActivity, agent, sonAgentId[cc], left_time,
				right_time, page, rows,
				new HttpCallback<Page<TransgoodsEntity>>(mActivity) {

			@Override
			public void onSuccess(Page<TransgoodsEntity> data) {
				TransgoodsDetail.isTra = false;
				isLoadMore = false;
				/*
				 * if(isrefersh){ page=a; rows=Config.ROWS;
				 * isrefersh=false; }
				 */
				if (datatrans.size() != 0 && data.getList().size() == 0) {
					Toast.makeText(mActivity, "没有更多数据!", 1000)
					.show();
					xxlistview.setPullLoadEnable(false);
				} else {
					idd = new int[data.getList().size()];
					if (datatrans.size() == 0) {
						datatrans.addAll(data.getList());
					}

				}
				isrefersh = false;
				myHandler.sendEmptyMessage(2);

			}

			@Override
			public TypeToken<Page<TransgoodsEntity>> getTypeToken() {
				return new TypeToken<Page<TransgoodsEntity>>() {
				};
			}
		});
		/*
		 * Config.getDistributeList(mActivity, 1, sonAgentId[cc], left_time,
		 * right_time, page, rows, new
		 * HttpCallback<Page<DistributeEntity>>(mActivity) {
		 * 
		 * @Override public void onSuccess(Page<DistributeEntity> data) {
		 * if(isrefersh){ page=a; rows=Config.ROWS; isrefersh=false; }
		 * if(datatrans.size()!=0&&data.getList().size()==0){
		 * Toast.makeText(mActivity, "没有更多数据!", 1000).show(); } else{
		 * idd=new int[data.getList().size()]; datatrans.addAll(data.getList());
		 * }
		 * myHandler.sendEmptyMessage(2);
		 * }
		 * @Override public TypeToken<Page<DistributeEntity>> getTypeToken() {
		 * return new TypeToken<Page<DistributeEntity>>() { }; } });
		 */}

	private void opendialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		LayoutInflater factory = LayoutInflater.from(mActivity);
		View textEntryView = factory.inflate(
				R.layout.transferobjectdialog, null);
		// builder.setTitle("自定义输入框");
		builder.setView(textEntryView);
		// builder.create().show();
		btn_confirm = (Button) textEntryView.findViewById(R.id.btn_confirm);
		my_diaSpinner_from = (Spinner) textEntryView
				.findViewById(R.id.sp_chooseagent_from);
		my_diaSpinner_to = (Spinner) textEntryView
				.findViewById(R.id.sp_chooseagent_to);
		adapter = new ArrayAdapter<String>(mActivity,
				android.R.layout.simple_spinner_item, list);
		tv_choose_terminal = (TextView) textEntryView
				.findViewById(R.id.tv_choose_terminal);
		close = (Button) textEntryView.findViewById(R.id.close);
		close.setOnClickListener(this);
		tv_choose_terminal.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		my_diaSpinner_from.setAdapter(adapter);
		my_diaSpinner_to.setAdapter(adapter);
		
		my_diaSpinner_from.setSelection(from,true);
		my_diaSpinner_to.setSelection(to,true);
		
		my_diaSpinner_from
		.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				// tv_agent.setText(adapter.getItem(position));
				my_diaSpinner_from.setSelection(position);
				from = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		my_diaSpinner_to
		.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				// tv_agent.setText(adapter.getItem(position));
				my_diaSpinner_to.setSelection(position);
				to = position;
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
		if (isrefersh) {
			CommonUtil.toastShort(mActivity, "请勿重复刷新");
			return;
		}
		/*
		 * isrefersh=true; a=page; rows=a*rows;
		 */
		page = 1;
		datatrans.clear();
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
