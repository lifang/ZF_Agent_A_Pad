package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

public class RegSucceed extends Activity {
	private LinearLayout ll_mail;
	private LinearLayout ll_tel;
	private TextView tv_tel;
	private TextView tv_mail;
	private LinearLayout ll_land;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.regsucceed);
		new TitleMenuUtil(RegSucceed.this, getResources().getString(
				R.string.register));
		initView();
	}

	private void initView() {
		ll_land = (LinearLayout) findViewById(R.id.login_linear_signin);

		ll_land.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(RegSucceed.this, LoginActivity.class);
				startActivity(i);
				RegSucceed.this.finish();
			}
		});

	}

}
