package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.comdo.zf_agent_a_pad.activity.AgentDetail;
import com.comdo.zf_agent_a_pad.activity.CreatAgent;
import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.AgentManager;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

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
import android.widget.TextView;
import android.widget.Toast;

public class Agentmanager extends Fragment implements OnClickListener,IXListViewListener{
	private View view;
	private List<AgentManager> dataadagent;
	private BaseAdapter agentadapter;
	private XListView xxlistview;
	private TextView tv_default;
	private Button btn_reset,btn_creat_agent,btn_save;
	private int page=1;
	public static Handler myHandler;
	private boolean isrefersh=false;
	private int rows=Config.ROWS;
	private int a=1;
	private EditText et_profit;
	private int id=MyApplication.NewUser.getAgentId();
	private String str="";
	private AlertDialog dialog;
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
}
@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	
	//view = inflater.inflate(R.layout.f_main,container,false);
	


if (view != null) {
    ViewGroup parent = (ViewGroup) view.getParent();
    if (parent != null)
        parent.removeView(view);
}
try {
    view = inflater.inflate(R.layout.agentmanager, container, false);
    init();
    //getDefaultProfit();
} catch (InflateException e) {
    
}
return view;
}
private void getDefaultProfit() {
	Config.getDefaultProfit(getActivity(), id, new HttpCallback(getActivity()) {

		@Override
		public void onSuccess(Object data) {
			str=String.valueOf(data);
			myHandler.sendEmptyMessage(2);
				
			
		}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
		@Override
		public TypeToken getTypeToken() {
			// TODO Auto-generated method stub
			return null;
		}
	});
	
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	if(dataadagent.size()!=0){
		dataadagent.clear();
	}
	getData();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				onLode();
				xxlistview.setAdapter(agentadapter);
				break;
			case 1:
				Intent intent=new Intent(getActivity(),AgentDetail.class);
				intent.putExtra("id", dataadagent.get(AgentManagerAdapter.pp).getId());
				startActivity(intent);
				break;
			case 2:
				tv_default.setText(str);
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
	Config.GetAgentList(getActivity(), 1, page, rows, 
			new HttpCallback<Page<AgentManager>>(getActivity()) {

				@Override
				public void onSuccess(Page<AgentManager> data) {
					if(isrefersh){
						page=a;
						rows=Config.ROWS;
						isrefersh=false;
					}
					if(dataadagent.size()!=0&&data.getList().size()==0){
						Toast.makeText(getActivity(), "没有更多数据!", 1000).show();
					}
					else{
						dataadagent.addAll(data.getList());
					}
					myHandler.sendEmptyMessage(0);
				}

				@Override
				public TypeToken<Page<AgentManager>> getTypeToken() {
					// TODO Auto-generated method stub
					return new TypeToken<Page<AgentManager>>() {
					};
				}
			});
	
}
private void init() {
	btn_creat_agent=(Button) view.findViewById(R.id.btn_creat_agent);
	btn_reset=(Button) view.findViewById(R.id.btn_reset);
	tv_default=(TextView) view.findViewById(R.id.tv_default);
	dataadagent=new ArrayList<AgentManager>();
	agentadapter=new AgentManagerAdapter(dataadagent, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	xxlistview.setPullLoadEnable(true);
	xxlistview.setXListViewListener(this);
	xxlistview.setDivider(null);
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
		startActivity(new Intent(getActivity(),CreatAgent.class));
		break;
	case R.id.btn_save:
		dialog.dismiss();
		resetProfit();
		break;
	default:
		break;
	}
	
}
private void resetProfit() {
	Config.resetprofit(getActivity(), 
			Integer.parseInt(et_profit.getText().toString()), 
			1, new HttpCallback(getActivity()) {

				@Override
				public void onSuccess(Object data) {
					// TODO Auto-generated method stub
					CommonUtil.toastShort(getActivity(), "设置成功");
					getDefaultProfit();
					//tv_default.setText(et_profit.getText().toString());
				}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
				@Override
				public TypeToken getTypeToken() {
					// TODO Auto-generated method stub
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
     btn_save=(Button) textEntryView.findViewById(R.id.btn_save);
     et_profit=(EditText) textEntryView.findViewById(R.id.et_profit);
     btn_save.setOnClickListener(this);
     dialog = builder.create();
     dialog.show();
     //builder.create().show();
	
}
@Override
public void onRefresh() {
	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	isrefersh=true;
	a=page;
	rows=a*rows;
	page=1;
	dataadagent.clear();
	getData();
	
}
@Override
public void onLoadMore() {
	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	page+=1;
	getData();
	
}
}

