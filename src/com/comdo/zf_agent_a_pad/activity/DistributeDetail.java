package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.comdo.zf_agent_a_pad.adapter.TerminalAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.DistributeDetailEntity;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.entity.TerminalEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DistributeDetail extends BaseActivity{
	private List<TerminalEntity> datatermin;
	private BaseAdapter terminalAdapter;
	private ListView lv_list;
	private int iddd;
	private TextView tv_distribute_object,tv_date,
	                 tv_creator;
	public static boolean isDri=false;
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.distributedetail);
	new TitleMenuUtil(DistributeDetail.this, "配货详情").show();
	init();
}
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	getData();
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	isDri=true;
}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	finish();
}
private void getData() {
	Config.getDistributeDetail(DistributeDetail.this, iddd, new HttpCallback<DistributeDetailEntity>(DistributeDetail.this) {

		@Override
		public void onSuccess(DistributeDetailEntity data) {
			if(data==null){
				CommonUtil.toastShort(DistributeDetail.this, "服务端返回数据为空");
				return;
			}
			tv_distribute_object.setText(data.getCompany_name());
			tv_date.setText(data.getCreated_at());
			tv_creator.setText(data.getCreator());
			String[] str=data.getTerminal_list().split(",");
			for(int i=0;i<str.length;i++){
				//datatermin.get(i).setNumber(str[i]);
				datatermin.add(new TerminalEntity(i, str[i]));
			}
			//datatermin.addAll(data.getTerminal_list());
			lv_list.setAdapter(terminalAdapter);
		}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
		@Override
		public TypeToken<DistributeDetailEntity> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<DistributeDetailEntity>() {
			};
		}
	});
	
}
private void init() {
	tv_distribute_object=(TextView) findViewById(R.id.tv_distribute_object);
	tv_date=(TextView) findViewById(R.id.tv_date);
	tv_creator=(TextView) findViewById(R.id.tv_creator);
	Intent intent=getIntent();
	iddd=intent.getIntExtra("id", 0);
	datatermin=new ArrayList<TerminalEntity>();
	terminalAdapter=new TerminalAdapter(datatermin, getApplicationContext());
	lv_list=(ListView) findViewById(R.id.lv_list);
}
}

