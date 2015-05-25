package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.comdo.zf_agent_a_pad.util.URLImageParser;
import com.epalmpay.agentPad.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class WebViewActivity extends BaseActivity{
	private String IMAGE_PATH;
	private WebView webview;
	private TextView content;
	private URLImageParser ReviewImgGetter;
	DisplayImageOptions options = MyApplication.getDisplayOption();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		new TitleMenuUtil(this, "查看详情").show();
		IMAGE_PATH = getIntent().getExtras().getString("IMAGE_PATH");

//		webview = (WebView) findViewById(R.id.webview);
//		webview.loadUrl(IMAGE_PATH);
		
		content = (TextView) findViewById(R.id.content);
		content.setAutoLinkMask(Linkify.ALL);
		content.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
		ReviewImgGetter = new URLImageParser(content,this);//实例化URLImageGetter类
		content.setText(Html.fromHtml(IMAGE_PATH,ReviewImgGetter,null));
	}

}
