package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.DistributeDetail;
import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.adapter.DistributeAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AgentEntity;
import com.comdo.zf_agent_a_pad.entity.DistributeEntity;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.trade.ApplyDetailActivity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Distribute extends Fragment implements OnClickListener,IXListViewListener{
	private View view;
	private List<DistributeEntity> datadistribut;
	private BaseAdapter distributeAdapter;
	private XListView xxlistview;
	private Button btn_distrib,btn_quary;
	public static Handler myHandler;
	private TextView tv_time_left,tv_time_right;
	private String mMerchantBirthday,left_time,right_time;
	private String[] mMerchantKeys;
	 private List<String> list = new ArrayList<String>();
	 private List<AgentEntity> datt=new ArrayList<AgentEntity>();
	 private Spinner mySpinner;
	 private ArrayAdapter<String> adapter;
	 private int page=1;
	 private int rows=Config.ROWS;
	 private int[] sonAgentId;
	 private int cc=0;
	 private boolean isrefersh=false;
	 private int a=1;
	 private int[] idd;
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
    view = inflater.inflate(R.layout.distribute, container, false);
    init();
} catch (InflateException e) {
    
}
return view;
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	getAgentList();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent=new Intent(getActivity(),DistributeDetail.class);
				intent.putExtra("id", datadistribut.get(DistributeAdapter.pp).getId());
				startActivity(intent);
				break;
			case 1:
				for(int i=0;i<datt.size();i++){
					list.add(datt.get(i).getCompany_name());
					sonAgentId[i]=datt.get(i).getId();
				}
				mySpinner = (Spinner)view.findViewById(R.id.mySpinner);
			    adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			    mySpinner.setAdapter(adapter); 
			    mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position,
							long id) {
						//tv_agent.setText(adapter.getItem(position));
						mySpinner.setSelection(position);
						cc=position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
				});  
				break;
			case 2:
				onLoad();
				xxlistview.setAdapter(distributeAdapter);
				break;
			default:
				break;
			}
		};
	};
	
}
protected void onLoad() {
	xxlistview.stopRefresh();
	xxlistview.stopLoadMore();
	xxlistview.setRefreshTime(Tools.getHourAndMin());
	
	
}
private void getAgentList() {
	Config.getlowerAgentList(getActivity(), 1, new HttpCallback<List<AgentEntity>>(getActivity()) {

		@Override
		public void onSuccess(List<AgentEntity> data) {
			
			datt.addAll(data);
			sonAgentId=new int[data.size()];
			myHandler.sendEmptyMessage(1);
		}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
		@Override
		public TypeToken<List<AgentEntity>> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<List<AgentEntity>>() {
			};
		}});
	
}
private void init() {
	//tv_agent=(TextView) view.findViewById(R.id.tv_agent);
	btn_quary=(Button) view.findViewById(R.id.btn_quary);
	mMerchantKeys = getResources().getStringArray(
			R.array.my_apply_detail_merchant_keys);
	tv_time_left=(TextView) view.findViewById(R.id.tv_time_left);
	tv_time_right=(TextView) view.findViewById(R.id.tv_time_right);
	btn_distrib=(Button) view.findViewById(R.id.btn_distrib);
	datadistribut=new ArrayList<DistributeEntity>();
	distributeAdapter=new DistributeAdapter(datadistribut, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	xxlistview.setPullLoadEnable(true);
	xxlistview.setXListViewListener(this);
	xxlistview.setDivider(null);
	btn_distrib.setOnClickListener(this);
	tv_time_left.setOnClickListener(this);
	tv_time_right.setOnClickListener(this);
	btn_quary.setOnClickListener(this);
	/*mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			tv_agent.setText(adapter.getItem(position));
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	});  */
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_distrib:
		opendialog();
		break;
	case R.id.tv_time_left:

		CommonUtil.showDatePicker(getActivity(),
				mMerchantBirthday, new CommonUtil.OnDateSetListener() {
					@Override
					public void onDateSet(String date) {
					  tv_time_left.setText(date);
					  left_time=date;
					}
				});
	
		break;
	case R.id.tv_time_right:
		CommonUtil.showDatePicker(getActivity(),
				mMerchantBirthday, new CommonUtil.OnDateSetListener() {
					@Override
					public void onDateSet(String date) {
						tv_time_right.setText(date);
						right_time=date;
					}
				});
		break;
	case R.id.btn_quary:
		quary();
		
		break;
	default:
		break;
	}
	
}
private void quary() {
	Config.getDistributeList(getActivity(), 1, sonAgentId[cc], left_time, right_time, page, rows, new HttpCallback<Page<DistributeEntity>>(getActivity()) {

		@Override
		public void onSuccess(Page<DistributeEntity> data) {
			if(isrefersh){
				page=a;
				rows=Config.ROWS;
				isrefersh=false;
			}
			if(datadistribut.size()!=0&&data.getList().size()==0){
				Toast.makeText(getActivity(), "没有更多数据!", 1000).show();
			}
			else{
				idd=new int[data.getList().size()];
				datadistribut.addAll(data.getList());
			}
			
			myHandler.sendEmptyMessage(2);
			
		}

		@Override
		public TypeToken<Page<DistributeEntity>> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<Page<DistributeEntity>>() {
			};
		}
	});
	
}
private void opendialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.distributdialog, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     builder.create().show();
	
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
	datadistribut.clear();
	quary();
	
}
@Override
public void onLoadMore() {
	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	page+=1;
	quary();
	
}
}

