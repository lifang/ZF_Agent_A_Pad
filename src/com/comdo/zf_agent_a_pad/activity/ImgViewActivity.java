package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.example.zf_agent_a_pad.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImgViewActivity extends Activity{
	private String IMAGE_PATH;
	private ImageView imgview;
	DisplayImageOptions options = MyApplication.getDisplayOption();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.imgview);

		IMAGE_PATH = getIntent().getExtras().getString("IMAGE_PATH");

		imgview = (ImageView) findViewById(R.id.imgview);
		ImageLoader.getInstance().displayImage(IMAGE_PATH, imgview,options);

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}

}
