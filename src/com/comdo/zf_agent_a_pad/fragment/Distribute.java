package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.DistributeDetail;
import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.adapter.DistributeAdapter;
import com.comdo.zf_agent_a_pad.entity.DistributeEntity;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;

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

public class Distribute extends Fragment implements OnClickListener{
	private View view;
	private List<DistributeEntity> datadistribut;
	private BaseAdapter distributeAdapter;
	private XListView xxlistview;
	private Button btn_distrib;
	public static Handler myHandler;
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
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent=new Intent(getActivity(),DistributeDetail.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		};
	};
}
private void init() {
	btn_distrib=(Button) view.findViewById(R.id.btn_distrib);
	datadistribut=new ArrayList<DistributeEntity>();
	distributeAdapter=new DistributeAdapter(datadistribut, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	for(int i=0;i<6;i++){
		datadistribut.add(new DistributeEntity(i, 
				"11111111", "11111111", "11111111"));
	}
	xxlistview.setAdapter(distributeAdapter);
	btn_distrib.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_distrib:
		opendialog();
		break;

	default:
		break;
	}
	
}
private void opendialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.distributdialog, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     builder.create().show();
	
}
}
