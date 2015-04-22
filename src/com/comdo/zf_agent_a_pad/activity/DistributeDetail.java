package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.comdo.zf_agent_a_pad.adapter.TerminalAdapter;
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

public class DistributeDetail extends BaseActivity{
	private List<TerminalEntity> datatermin;
	private BaseAdapter terminalAdapter;
	private ListView lv_list;
	private int iddd;
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
private void getData() {
	/*RequestParams params=new RequestParams();
	//Map<String, Object> params = new HashMap<String, Object>();
	params.put("ageidntId", iddd);
	params.setUseJsonStreamer(true);
	new AsyncHttpClient().post(Config.GET_DISTRIBUTE_DETAIL, params, new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			String str=new String(responseBody);
			
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			
		}
	});*/
	Config.getDistributeDetail(DistributeDetail.this, iddd, new HttpCallback<DistributeDetailEntity>(DistributeDetail.this) {

		@Override
		public void onSuccess(DistributeDetailEntity data) {
			// TODO Auto-generated method stub
			
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
	Intent intent=getIntent();
	iddd=intent.getIntExtra("id", 0);
	datatermin=new ArrayList<TerminalEntity>();
	terminalAdapter=new TerminalAdapter(datatermin, getApplicationContext());
	lv_list=(ListView) findViewById(R.id.lv_list);
	for(int i=0;i<6;i++){
		datatermin.add(new TerminalEntity(i, "12345678"));
	}
	lv_list.setAdapter(terminalAdapter);
}
}

