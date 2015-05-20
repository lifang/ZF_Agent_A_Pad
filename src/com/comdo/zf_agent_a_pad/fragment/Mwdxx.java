package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.comdo.zf_agent_a_pad.activity.MainActivity;
import com.comdo.zf_agent_a_pad.activity.MsgDetail;
import com.comdo.zf_agent_a_pad.activity.OrderDetail;
import com.comdo.zf_agent_a_pad.adapter.MessageAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.DialogUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.util.AlertDialog;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

public class Mwdxx extends Fragment implements OnClickListener,
		IXListViewListener {
	private View view;
	private XListView Xlistview;
	private int page = 1;
	private int rows = Config.ROWS;
	private String Url = Config.getmes;
	private Dialog loadingDialog;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private MessageAdapter myAdapter;
	private TextView next_sure;
	private MainActivity mActivity;
	private List<MessageEntity> myList = new ArrayList<MessageEntity>();
	private List<MessageEntity> moreList = new ArrayList<MessageEntity>();
	private List<MessageEntity> selList = new ArrayList<MessageEntity>();
	private String ids[] = new String[] {};
	private boolean isFrist;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();

				if (myList.size() == 0) {
					// norecord_text_to.setText("��û����ص���Ʒ");
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true;
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(mActivity, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();

				break;
			case 2: // ����������
				Toast.makeText(mActivity, "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(mActivity, " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private Button bt_bj;
	private Button bt_del;
	private CheckBox ckall;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isFrist=true;
		loadingDialog = DialogUtil.getLoadingDialg(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// view = inflater.inflate(R.layout.f_main,container,false);

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.m_wdxx, container, false);
			initView();
			page = 1;
			myList.clear();
			getData(0);

		} catch (InflateException e) {

		}
		return view;
	}

	private void initView() {
		// next_sure=(TextView) findViewById(R.id.next_sure);
		// next_sure.setVisibility(View.VISIBLE);
		// next_sure.setText("�༭");
		// new TitleMenuUtil(MyMessage.this, "ϵͳ����").show();
		ckall = (CheckBox) view.findViewById(R.id.cb_all);
		ckall.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					for (MessageEntity item : myList) {
						item.setIscheck(true);
						myAdapter.notifyDataSetChanged();
					}
				} else {
					for (MessageEntity item : myList) {
						item.setIscheck(false);
						myAdapter.notifyDataSetChanged();
					}
				}

			}
		});
		myAdapter = new MessageAdapter(mActivity, myList,ckall);
		eva_nodata = (LinearLayout) view.findViewById(R.id.eva_nodata);
		Xlistview = (XListView) view.findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û��������");
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(mActivity, MsgDetail.class);
				i.putExtra("id", Integer.parseInt(myList.get(position - 1).getId()));
				startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);
		bt_bj = (Button) view.findViewById(R.id.bt_bj);
		bt_bj.setOnClickListener(this);
		bt_del = (Button) view.findViewById(R.id.bt_del);
		bt_del.setOnClickListener(this);
		
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		page = 1;
		myList.clear();
		getData(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_bj:
			read_bj();
			break;
		case R.id.bt_del:
			del_msg();
		default:
			break;
		}
	}

	private void del_msg() {
		selList.clear();
		for (MessageEntity me : myList) {
			if (me.getIscheck()) {
				selList.add(me);
			}
		}
		ids = new String[selList.size()];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = selList.get(i).getId();
		}
		if(ids.length==0){
			Toast.makeText(getActivity(), "请选择消息后进行操作！", 1000).show();
		}else{
			final AlertDialog ad = new AlertDialog(getActivity());
			ad.setTitle("提示");
			ad.setMessage("确认取消?");
			ad.setPositiveButton("取消", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
				}
			});
			ad.setNegativeButton("确定", new OnClickListener() {
				@Override
				public void onClick(final View arg0) {
					getData(1);
					ad.dismiss();
				}	
			});
		
		
		}
		
	}

	private void read_bj() {
		selList.clear();
		for (MessageEntity me : myList) {
			if (me.getIscheck()) {
				selList.add(me);
			}
		}
		ids = new String[selList.size()];

		for (int i = 0; i < ids.length; i++) {
			ids[i] = selList.get(i).getId();
		}
		if(ids.length==0){
			Toast.makeText(getActivity(), "请选择消息后进行操作！", 1000).show();
		}else{
			getData(2);
		}
	}

	@Override
	public void onRefresh() {
		page = 1;
		System.out.println("onRefresh1");
		myList.clear();
		System.out.println("onRefresh2");
		getData(0);
		Xlistview.setPullLoadEnable(true);
	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page + 1;

			onRefresh_number = false;
			getData(0);
			// if (Tools.isConnect(getApplicationContext())) {
			// onRefresh_number = false;
			// getData();
			// } else {
			// onRefresh_number = true;
			// handler.sendEmptyMessage(2);
			// }
		} else {
			handler.sendEmptyMessage(3);
		}
	}

	/*
	 * ��������
	 */
	private void getData(final int type) {
		RequestParams params = new RequestParams();
		Gson gson = new Gson();
		params.put("customerId", MyApplication.NewUser.getAgentUserId());
		if (type == 0) {
			Url = Config.getmes;
			params.put("page", page);
			params.put("pageSize", 20);
		} else if (type == 1) {
			Url = Config.DELECT_MORE;
			try {
				params.put("ids", new JSONArray(gson.toJson(ids)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if (type == 2) {
			Url = Config.IS_READ;
			try {
				params.put("ids", new JSONArray(gson.toJson(ids)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}	
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient()
				.post(Url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						loadingDialog.show();
						super.onStart();	
					}
					@Override
					public void onFinish() {
						loadingDialog.dismiss();
						super.onFinish();
					}
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						System.out.println("-onSuccess---");
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("LJP", responseMsg);
						Gson gson = new Gson();
						JSONObject jsonobject = null;
						int code = 0;
						try {
							jsonobject = new JSONObject(responseMsg);
							String res = jsonobject.getString("result");
							code = jsonobject.getInt("code");
							if (code == -2) {
							} else if (code == 1) {
								if (type == 0) {									
									System.out.println("`res``" + res);
									jsonobject = new JSONObject(res);
									moreList.clear();
									moreList = gson.fromJson(
											jsonobject.getString("list"),
											new TypeToken<List<MessageEntity>>() {
											}.getType());

									if (moreList.size() == 0&&myList.size()!=0) {
										Toast.makeText(mActivity, "没有更多数据！",
												Toast.LENGTH_SHORT).show();
										Xlistview.getmFooterView().setState2(2);
										Xlistview.setPullLoadEnable(false);
									}

									myList.addAll(moreList);
									handler.sendEmptyMessage(0);

								}else if(type==1){
									myList.clear();
									page = 1;
									getData(0);
									Toast.makeText(getActivity(),
											jsonobject.getString("message"),
											Toast.LENGTH_SHORT).show();
								}else if(type==2){
									page = 1;
									myList.clear();
									getData(0);
									Toast.makeText(getActivity(),
											jsonobject.getString("message"),
											Toast.LENGTH_SHORT).show();									
								}
							} else {								
								Toast.makeText(getActivity(),
										jsonobject.getString("message"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						
						error.printStackTrace();
					}

				});
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.toString());
		if(!isFrist){
			page = 1;
			myList.clear();
			getData(0);
			myAdapter.notifyDataSetChanged();
		}
		isFrist=false;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(this.toString());
	}

}
