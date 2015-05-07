package com.comdo.zf_agent_a_pad.fragment;

import com.comdo.zf_agent_a_pad.activity.LoginActivity;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.example.zf_agent_a_pad.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Mmy extends Fragment implements OnClickListener {
	private View view;
	private TextView tv_myinfo, tv_manager_shopper, tv_distribute,
			tv_transgoods, tv_staffmanagr,tv_exit;
	private MineMyinfo f_info;
	private Agentmanager f_agentmanager;
	private Distribute f_distribute;
	private Transgoods f_transgood;
	private Staffmanagr f_staffmanagr;
	private ImageView im1,im2,im3,im4,im5,im6;
	private int type;
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
	Log.e("inflater", String.valueOf(inflater));
	Log.e("container", String.valueOf(container));
    view = inflater.inflate(R.layout.f_mine, container, false);

    init();
    if(!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_8){
    	
    	if (!CheckRights.RIGHT_5) {
    		setback();
    		im3.setVisibility(View.VISIBLE);
    		f_distribute=new Distribute();
    		getActivity().getSupportFragmentManager().beginTransaction().
    		replace(R.id.f_mine, f_distribute).commit();
    	}else {
    		setback();
    		im2.setVisibility(View.VISIBLE);
    		f_agentmanager=new Agentmanager();
    		getActivity().getSupportFragmentManager().beginTransaction().
    		replace(R.id.f_mine, f_agentmanager).commit();
		}
    	
    }else {

		setback();
		im1.setVisibility(View.VISIBLE);

    f_info = new MineMyinfo();

	getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.f_mine, f_info).commit();	
	}

   /* f_info = new MineMyinfo();

	getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.f_mine, f_info).commit();*/
//    init();

} catch (InflateException e) {
    type=1;
}
return view;
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	Log.e("tag", type+"");

}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	changeTap();
}
private void changeTap() {
	switch (type) {
	case 1:
		setback();
		im1.setVisibility(View.VISIBLE);
		if(f_info==null)
			f_info=new MineMyinfo();
		/*else{
			  Fragment p =  f_info.getParentFragment();
			  if(p!=null){
				  p.getFragmentManager().getFragments().remove(R.id.f_mine);
			  }
		}*/
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_info).commit();
		break;
case 2:
	setback();
	im2.setVisibility(View.VISIBLE);
	if(f_agentmanager==null)
		f_agentmanager=new Agentmanager();
	getActivity().getSupportFragmentManager().beginTransaction().
	replace(R.id.f_mine, f_agentmanager).commit();
	
		break;
case 3:
	setback();
	im3.setVisibility(View.VISIBLE);
	if(f_distribute==null)
		f_distribute=new Distribute();
	getActivity().getSupportFragmentManager().beginTransaction().
	replace(R.id.f_mine, f_distribute).commit();
	break;
case 4:
	setback();
	im4.setVisibility(View.VISIBLE);
	if(f_transgood==null)
		f_transgood=new Transgoods();
	getActivity().getSupportFragmentManager().beginTransaction().
	replace(R.id.f_mine, f_transgood).commit();
	break;
case 5:
	setback();
	im5.setVisibility(View.VISIBLE);
	if(f_staffmanagr==null)
		f_staffmanagr=new Staffmanagr();
	getActivity().
	getSupportFragmentManager().
	beginTransaction().
	replace(R.id.f_mine, f_staffmanagr).
	commit();
	break;
case 6:
	setback();
	im6.setVisibility(View.VISIBLE);
	if(f_staffmanagr==null)
		f_staffmanagr=new Staffmanagr();
	getActivity().
	getSupportFragmentManager().
	beginTransaction().
	replace(R.id.f_mine, f_staffmanagr).
	commit();
	break;
	default:
		break;
	}
	
}
private void init() {
	im1 = (ImageView) view.findViewById(R.id.im1);
	im2 = (ImageView) view.findViewById(R.id.im2);
	im3 = (ImageView) view.findViewById(R.id.im3);
	im4 = (ImageView) view.findViewById(R.id.im4);
	im5 = (ImageView) view.findViewById(R.id.im5);
	im6 = (ImageView) view.findViewById(R.id.im6);
	tv_myinfo=(TextView) view.findViewById(R.id.tv_myinfo);
	tv_manager_shopper=(TextView) view.findViewById(R.id.tv_manager_shopper);
	tv_distribute=(TextView) view.findViewById(R.id.tv_distribute);
	tv_transgoods=(TextView) view.findViewById(R.id.tv_transgoods);
	tv_staffmanagr=(TextView) view.findViewById(R.id.tv_staffmanagr);
	tv_exit=(TextView) view.findViewById(R.id.tv_exit);
	tv_myinfo.setOnClickListener(this);
	tv_manager_shopper.setOnClickListener(this);
	tv_distribute.setOnClickListener(this);
	tv_transgoods.setOnClickListener(this);
	tv_staffmanagr.setOnClickListener(this);
	tv_exit.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.tv_myinfo:
		if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_8) {
			CommonUtil.toastShort(getActivity(), R.string.right_not_match);
		} else {
		type=1;
		setback();
		im1.setVisibility(View.VISIBLE);
		if(f_info==null)
			f_info=new MineMyinfo();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_info).commit();
		}
		break;
	case R.id.tv_manager_shopper:
		if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_5) {
			CommonUtil.toastShort(getActivity(), R.string.right_not_match);
		} else {
		type=2;
		setback();
		im2.setVisibility(View.VISIBLE);
		if(f_agentmanager==null)
			f_agentmanager=new Agentmanager();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_agentmanager).commit();
		}
		break;
	case R.id.tv_distribute:
		type=3;
		setback();
		im3.setVisibility(View.VISIBLE);
		if(f_distribute==null)
			f_distribute=new Distribute();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_distribute).commit();
		break;
	case R.id.tv_transgoods:
		type=4;
		setback();
		im4.setVisibility(View.VISIBLE);
		if(f_transgood==null)
			f_transgood=new Transgoods();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_transgood).commit();
		break;
	case R.id.tv_staffmanagr:
		if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_8) {
			CommonUtil.toastShort(getActivity(), R.string.right_not_match);
		} else {
		type=5;
		setback();
		im5.setVisibility(View.VISIBLE);
		if(f_staffmanagr==null)
			f_staffmanagr=new Staffmanagr();
		getActivity().getSupportFragmentManager().beginTransaction().
		replace(R.id.f_mine, f_staffmanagr).commit();
		}
		break;
	case R.id.tv_exit:
		type=6;
		setback();
		im6.setVisibility(View.VISIBLE);
		startActivity(new Intent(getActivity(),LoginActivity.class));
    	getActivity().finish();
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
	im6.setVisibility(View.GONE);
	tv_myinfo.setTextColor(getResources().getColor(R.color.white));
	tv_manager_shopper.setTextColor(getResources().getColor(R.color.white));
	tv_distribute.setTextColor(getResources().getColor(R.color.white));
	tv_transgoods.setTextColor(getResources().getColor(R.color.white));
	tv_staffmanagr.setTextColor(getResources().getColor(R.color.white));
	
}
@Override
public void onDestroyView() {
	try {
		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		;
		if (f_info != null)
			transaction.remove(f_info);
		if (f_agentmanager != null)
			transaction.remove(f_agentmanager);
		if (f_distribute != null)
			transaction.remove(f_distribute);
		if (f_transgood != null)
			transaction.remove(f_transgood);
		if (f_staffmanagr != null)
			transaction.remove(f_staffmanagr);
		transaction.commit();
	} catch (Exception e) {
	}

	super.onDestroyView();
}

}
