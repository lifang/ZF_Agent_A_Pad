package com.comdo.zf_agent_a_pad.activity;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends BaseActivity {
	private String Url="";
	private String Title="";
	private WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webview);
		Title=getIntent().getStringExtra("title");
		Url=getIntent().getStringExtra("url");
		new TitleMenuUtil(this,Title ).show();
		webview = (WebView)findViewById(R.id.webview);
		webview.setWebViewClient(new WebViewClient());
		if(!Url.contains("www")){
			Url=Url.replace("//", "//www.");
		}
		Log.i("www",Url);
		webview.loadUrl(Url); 
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack())
        {
            // 返回键退回
        	webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
class MyWebViewClient extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
    	  view.loadUrl(url);  
          return true;  
    }
}