package com.comdo.zf_agent_a_pad.activity;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class MyWebView extends Activity {
	private String Url="";
	private String Title="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webview);
		Title=getIntent().getStringExtra("title");
		Url=getIntent().getStringExtra("url");
		new TitleMenuUtil(this,Title ).show();
		WebView webview=(WebView)findViewById(R.id.webview);
		if(!Url.contains("www")){
			Url=Url.replace("//", "//www.");
		}
		Log.i("www",Url);
		webview.loadUrl(Url); 
	}

}
