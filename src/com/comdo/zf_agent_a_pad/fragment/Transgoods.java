package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.DistributeDetail;
import com.comdo.zf_agent_a_pad.activity.TransgoodsDetail;
import com.comdo.zf_agent_a_pad.adapter.TransgoodsAdapter;
import com.comdo.zf_agent_a_pad.entity.TransgoodsEntity;
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

public class Transgoods extends Fragment implements OnClickListener{
	private View view;
	private List<TransgoodsEntity> datatrans;
	private BaseAdapter transAdapter;
	private XListView xxlistview;
	private Button btn_transferobject;
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
    view = inflater.inflate(R.layout.transgoods, container, false);
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
				Intent intent=new Intent(getActivity(),TransgoodsDetail.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		};
	};
}
private void init() {
	btn_transferobject=(Button) view.findViewById(R.id.btn_transferobject);
	datatrans=new ArrayList<TransgoodsEntity>();
	transAdapter=new TransgoodsAdapter(datatrans, getActivity().getBaseContext());
	xxlistview=(XListView) view.findViewById(R.id.list);
	for(int i=0;i<6;i++){
		datatrans.add(new TransgoodsEntity(i, 
				"111111", "111111", "111111"));
	}
	xxlistview.setAdapter(transAdapter);
	btn_transferobject.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_transferobject:
		opendialog();
		break;

	default:
		break;
	}
	
}
private void opendialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.transferobjectdialog, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     builder.create().show();
	
}
}
