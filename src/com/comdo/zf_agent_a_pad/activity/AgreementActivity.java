package com.comdo.zf_agent_a_pad.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

public class AgreementActivity  extends BaseActivity{

	WebView web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agreement);
		new TitleMenuUtil(AgreementActivity.this, "用户使用协议").show();
		web = (WebView) this.findViewById(R.id.webview);
//		web.setVisibility(View.VISIBLE);
		web.getSettings().setDefaultTextEncodingName("UTF-8");
		web.loadDataWithBaseURL(null,getResources().getString(R.string.agreement), "text/html", "utf-8",null);
//		web.loadData(getResources().getString(R.string.agreement), "text/htm", "UTF-8");
	}
	
}
