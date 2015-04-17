package com.comdo.zf_agent_a_pad.fragment;
import com.example.zf_agent_a_pad.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineMyinfo extends Fragment implements OnClickListener{
	private View view;
	 public static int mRecordType=0;
	 private TextView tv_jichuinfo,tv_safe,tv_manageradress;
	 private Mchpaw m_chpaw;
	 private Mine_adress m_adress;
	 private Mybaseinfo m_baseinfo;
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
}
@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
	
	//view = inflater.inflate(R.layout.f_main,container,false);
	


if (view != null) {
    ViewGroup parent = (ViewGroup) view.getParent();
    if (parent != null)
        parent.removeView(view);
}
try {
    view = inflater.inflate(R.layout.myinfo, container, false);
    init();
} catch (InflateException e) {
    
}
return view;
}
private void init() {
	tv_jichuinfo=(TextView) view.findViewById(R.id.tv_jichuinfo);
	tv_safe=(TextView) view.findViewById(R.id.tv_safe);
	tv_manageradress=(TextView) view.findViewById(R.id.tv_manageradress);
	tv_jichuinfo.setOnClickListener(this);
	tv_safe.setOnClickListener(this);
	tv_manageradress.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.tv_safe:
		tv_safe.setBackgroundResource(R.drawable.tab_bg);
		tv_jichuinfo.setBackgroundColor(getResources().getColor(R.color.bg));
		tv_manageradress.setBackgroundColor(getResources().getColor(R.color.bg));
		if(m_chpaw==null)
			m_chpaw=new Mchpaw();
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.fm, m_chpaw).commit();
		break;
	case R.id.tv_manageradress:
		tv_safe.setBackgroundColor(getResources().getColor(R.color.bg));
		tv_jichuinfo.setBackgroundColor(getResources().getColor(R.color.bg));
		tv_manageradress.setBackgroundResource(R.drawable.tab_bg);
		if(m_adress==null)
			m_adress=new Mine_adress();
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.fm, m_adress).commit();
		break;
	case R.id.tv_jichuinfo:
		tv_jichuinfo.setBackgroundResource(R.drawable.tab_bg);
		tv_safe.setBackgroundColor(getResources().getColor(R.color.bg));
		tv_manageradress.setBackgroundColor(getResources().getColor(R.color.bg));
		if(m_baseinfo==null)
			m_baseinfo=new Mybaseinfo();
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.fm, m_baseinfo).commit();
		break;
	default:
		break;
	}
	
}
}
