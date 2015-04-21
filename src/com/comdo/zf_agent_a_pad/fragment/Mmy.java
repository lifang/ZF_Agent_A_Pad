package com.comdo.zf_agent_a_pad.fragment;

import com.example.zf_agent_a_pad.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Mmy extends Fragment implements OnClickListener{
	private View view;
	private TextView tv_myinfo,tv_manager_shopper,tv_distribute,tv_transgoods,
	                tv_staffmanagr;
	private MineMyinfo f_info;
	private Agentmanager f_agentmanager;
	private Distribute f_distribute;
	private Transgoods f_transgood;
	private Staffmanagr f_staffmanagr;
	private ImageView im1,im2,im3,im4,im5;
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
    view = inflater.inflate(R.layout.f_mine, container, false);
    init();
} catch (InflateException e) {
    
}
return view;
}
private void init() {
	im1 = (ImageView) view.findViewById(R.id.im1);
	im2 = (ImageView) view.findViewById(R.id.im2);
	im3 = (ImageView) view.findViewById(R.id.im3);
	im4 = (ImageView) view.findViewById(R.id.im4);
	im5 = (ImageView) view.findViewById(R.id.im5);
	tv_myinfo=(TextView) view.findViewById(R.id.tv_myinfo);
	tv_manager_shopper=(TextView) view.findViewById(R.id.tv_manager_shopper);
	tv_distribute=(TextView) view.findViewById(R.id.tv_distribute);
	tv_transgoods=(TextView) view.findViewById(R.id.tv_transgoods);
	tv_staffmanagr=(TextView) view.findViewById(R.id.tv_staffmanagr);
	tv_myinfo.setOnClickListener(this);
	tv_manager_shopper.setOnClickListener(this);
	tv_distribute.setOnClickListener(this);
	tv_transgoods.setOnClickListener(this);
	tv_staffmanagr.setOnClickListener(this);
}
@SuppressLint("Recycle")
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.tv_myinfo:
		setback();
		im1.setVisibility(View.VISIBLE);
		if(f_info==null)
			f_info=new MineMyinfo();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_info).commit();
		break;
	case R.id.tv_manager_shopper:
		setback();
		im2.setVisibility(View.VISIBLE);
		if(f_agentmanager==null)
			f_agentmanager=new Agentmanager();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_agentmanager).commit();
		break;
	case R.id.tv_distribute:
		setback();
		im3.setVisibility(View.VISIBLE);
		if(f_distribute==null)
			f_distribute=new Distribute();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_distribute).commit();
		break;
	case R.id.tv_transgoods:
		setback();
		im4.setVisibility(View.VISIBLE);
		if(f_transgood==null)
			f_transgood=new Transgoods();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_transgood).commit();
		break;
	case R.id.tv_staffmanagr:
		setback();
		im5.setVisibility(View.VISIBLE);
		if(f_staffmanagr==null)
			f_staffmanagr=new Staffmanagr();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_staffmanagr).commit();
		break;
	default:
		break;
	}
	
}
private void setback() {
	im1.setVisibility(View.GONE);
	im2.setVisibility(View.GONE);
	im3.setVisibility(View.GONE);
	im4.setVisibility(View.GONE);
	im5.setVisibility(View.GONE);
	tv_myinfo.setTextColor(getResources().getColor(R.color.white));
	tv_manager_shopper.setTextColor(getResources().getColor(R.color.white));
	tv_distribute.setTextColor(getResources().getColor(R.color.white));
	tv_transgoods.setTextColor(getResources().getColor(R.color.white));
	tv_staffmanagr.setTextColor(getResources().getColor(R.color.white));
	
}
}