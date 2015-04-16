package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.TerminalAdapter;
import com.comdo.zf_agent_a_pad.entity.TerminalEntity;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class TransgoodsDetail extends BaseActivity{
	private List<TerminalEntity> datatermin;
	private BaseAdapter terminalAdapter;
	private ListView lv_list;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.transgoodsdetail);
	new TitleMenuUtil(TransgoodsDetail.this, "调货详情").show();
	init();
}
private void init() {
	datatermin=new ArrayList<TerminalEntity>();
	terminalAdapter=new TerminalAdapter(datatermin, getApplicationContext());
	lv_list=(ListView) findViewById(R.id.lv_list);
	for(int i=0;i<6;i++){
		datatermin.add(new TerminalEntity(i, "12345678"));
	}
	lv_list.setAdapter(terminalAdapter);
}
}
