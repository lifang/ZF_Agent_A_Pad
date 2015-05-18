package com.comdo.zf_agent_a_pad.activity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.example.zf_agent_a_pad.R;

import android.app.Activity;
import android.os.Bundle;

public class AlterPhone extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterpass);
		//Config.AlterPhoneCode(getApplicationContext(), MyApplication.NewUser.getAgentUserId(), "18762091710", callback);
		initView();
	}

	private void initView() {
		
		
	}

}
