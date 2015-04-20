package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;
import com.comdo.zf_agent_a_pad.adapter.StaffmanagerAdapter;
import com.comdo.zf_agent_a_pad.entity.StaffmanagerEntity;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;

import android.app.AlertDialog;
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

public class Staffmanagr extends Fragment implements OnClickListener{
	private View view;
	private List<StaffmanagerEntity> datastaff;
	private BaseAdapter staffmanageradapter;
	private XListView xxlistview;
	private Button btn_creatstaff;
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
private void init() {
	btn_creatstaff=(Button) view.findViewById(R.id.btn_creatstaff);
	datastaff=new ArrayList<StaffmanagerEntity>();
	staffmanageradapter=new StaffmanagerAdapter(datastaff, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	for(int i=0;i<6;i++){
		datastaff.add(new StaffmanagerEntity(i, 
				"11111111", "11111111", "11111111"));
	}
	xxlistview.setAdapter(staffmanageradapter);
	btn_creatstaff.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_creatstaff:
		opendialog();
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
}
