package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.CreatStaff;
import com.comdo.zf_agent_a_pad.adapter.StaffmanagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.entity.StaffmanagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
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
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class Staffmanagr extends Fragment implements OnClickListener,IXListViewListener{
	private View view;
	private List<StaffmanagerEntity> datastaff;
	private BaseAdapter staffmanageradapter;
	private XListView xxlistview;
	private Button btn_creatstaff;
	private Handler myHandler;
	private boolean isrefersh=false;
	 private int a=1;
	 private int page=1;
	 private int rows=Config.ROWS;
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
    view = inflater.inflate(R.layout.staffmanagr, container, false);
    init();
} catch (InflateException e) {
    
}
return view;
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	getData();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				xxlistview.setAdapter(staffmanageradapter);
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
private void getData() {
	if(datastaff.size()!=0){
		datastaff.clear();
	}
	Config.getStaffList(getActivity(), 1, 1, 10, new HttpCallback<Page<StaffmanagerEntity>>(getActivity()) {

		@Override
		public void onSuccess(Page<StaffmanagerEntity> data) {
			if(isrefersh){
				page=a;
				rows=Config.ROWS;
				isrefersh=false;
			}
			if(datastaff.size()!=0&&data.getList().size()==0){
				Toast.makeText(getActivity(), "没有更多数据!", 1000).show();
			}
			else{
				datastaff.addAll(data.getList());
			}
			
			Log.e("aa", String.valueOf(data.getList()));
			Log.e("ss", String.valueOf(datastaff));
			myHandler.sendEmptyMessage(0);
			
			
		}

		@Override
		public TypeToken<Page<StaffmanagerEntity>> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<Page<StaffmanagerEntity>>() {
			};
		}
	});
	
}
private void init() {
	btn_creatstaff=(Button) view.findViewById(R.id.btn_creatstaff);
	datastaff=new ArrayList<StaffmanagerEntity>();
	staffmanageradapter=new StaffmanagerAdapter(datastaff, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	xxlistview.setPullLoadEnable(true);
	xxlistview.setXListViewListener(this);
	xxlistview.setDivider(null);
	/*for(int i=0;i<6;i++){
		datastaff.add(new StaffmanagerEntity(i, 
				"11111111", "11111111", "11111111"));
	}
	xxlistview.setAdapter(staffmanageradapter);*/
	btn_creatstaff.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_creatstaff:
		Intent intent=new Intent(getActivity(),CreatStaff.class);
		startActivity(intent);
		//opendialog();
		break;

	default:
		break;
	}
	
}
private void opendialog() {
	final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.creatstaff, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
    
     //final AlertDialog dialog = builder.show();
     //dialog=builder.show();
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
	datastaff.clear();
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
