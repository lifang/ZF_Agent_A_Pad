package com.comdo.zf_agent_a_pad.activity;

import com.example.zf_agent_a_pad.R;

import android.app.Activity;
import android.os.Bundle;

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
