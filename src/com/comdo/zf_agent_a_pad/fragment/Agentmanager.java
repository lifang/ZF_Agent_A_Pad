package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.CreatAgent;
import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.entity.AgentManager;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Agentmanager extends Fragment implements OnClickListener{
	private View view;
	private List<AgentManager> dataadagent;
	private BaseAdapter agentadapter;
	private XListView xxlistview;
	private TextView tv_default;
	private Button btn_reset,btn_creat_agent;
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
} catch (InflateException e) {
    
}
return view;
}
private void init() {
	btn_creat_agent=(Button) view.findViewById(R.id.btn_creat_agent);
	btn_reset=(Button) view.findViewById(R.id.btn_reset);
	tv_default=(TextView) view.findViewById(R.id.tv_default);
	dataadagent=new ArrayList<AgentManager>();
	agentadapter=new AgentManagerAdapter(dataadagent, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	for(int i=0;i<5;i++){
		dataadagent.add(new AgentManager(i,
				"1111", "1111", "1111"));
	}
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
		startActivity(new Intent(getActivity(),CreatAgent.class));
		break;
	default:
		break;
	}
	
}
private void openDialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.reset, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     builder.create().show();
	
}
}
