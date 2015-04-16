package com.comdo.zf_agent_a_pad.fragment;

import com.example.zf_agent_a_pad.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Mybaseinfo extends Fragment{
	private View view;
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
    view = inflater.inflate(R.layout.baseinfo, container, false);
    //init();
} catch (InflateException e) {
    
}
return view;
}
}
