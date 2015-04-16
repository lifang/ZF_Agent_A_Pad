package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

import android.os.Bundle;

public class CreatAgent extends BaseActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.creatagent);
	new TitleMenuUtil(CreatAgent.this, "创建代理商详情").show();
}
}
