package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.os.Bundle;

import com.epalmpay.agentPad.R;

public class EditAdress extends Activity {
	private boolean isEdit = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addaddress);
		isEdit=getIntent().getBooleanExtra("isedit", false);
		initView();
		
	}
	private void initView() {
		
	}

}
