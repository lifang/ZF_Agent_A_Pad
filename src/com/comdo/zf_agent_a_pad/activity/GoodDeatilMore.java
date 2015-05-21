package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.fragment.F_good_detail;
import com.comdo.zf_agent_a_pad.fragment.Good_detail_apply;
import com.comdo.zf_agent_a_pad.fragment.Good_detail_commet;
import com.comdo.zf_agent_a_pad.fragment.Good_detail_pic;
import com.comdo.zf_agent_a_pad.fragment.Good_detail_trade;
import com.comdo.zf_agent_a_pad.fragment.Good_detail_zd;
import com.comdo.zf_agent_a_pad.util.Config;
import com.example.zf_agent_a_pad.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoodDeatilMore extends FragmentActivity implements OnClickListener {

	private F_good_detail detail;
	private Good_detail_apply apply;
	private Good_detail_commet commet;
	private Good_detail_zd zd;
	private Good_detail_trade jy;
	private Good_detail_pic pic;
	private int type;
	private TextView tv_ms;
	private TextView tv_kt;
	private TextView tv_pl;
	private TextView tv_zd;
	private TextView tv_jy;
	private ImageView search2;
	private LinearLayout ll_back;
	private int comments;
	private LinearLayout ll_isshow;
	private TextView tv_pic;
	private TextView titleback_text_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goodmoremain);
		type = getIntent().getIntExtra("type", 0);
		comments = getIntent().getIntExtra("commets", 0);
		MobclickAgent.openActivityDurationTrack(false);
		initView();
		switch (type) {
		case 0:
			if (detail == null)
				detail = new F_good_detail();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, detail).commit();
			tv_ms.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case 1:
			if (apply == null)
				apply = new Good_detail_apply();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, apply).commit();
			tv_kt.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case 2:
			if (commet == null)
				commet = new Good_detail_commet();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, commet).commit();
			tv_pl.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case 3:
			if (zd == null)
				zd = new Good_detail_zd();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, zd).commit();
			tv_zd.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case 4:
			if (jy == null)
				jy = new Good_detail_trade();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, jy).commit();
			tv_jy.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case 5:
			if (pic == null)
				pic = new Good_detail_pic();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, pic).commit();
			tv_pic.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		default:
			break;
		}

	}

	private void initView() {
		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		ll_isshow = (LinearLayout) findViewById(R.id.ll_isshow);
		if (Config.iszd) {
			ll_isshow.setVisibility(View.GONE);
			titleback_text_title.setText("租赁说明");
		}

		ll_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		ll_back.setOnClickListener(this);
		tv_ms = (TextView) findViewById(R.id.tv_ms);
		tv_ms.setOnClickListener(this);
		tv_kt = (TextView) findViewById(R.id.tv_kt);
		tv_kt.setOnClickListener(this);
		tv_pl = (TextView) findViewById(R.id.tv_pl);
		tv_pl.setOnClickListener(this);
		tv_pl.setText("评论" + "(" + comments + ")");
		tv_zd = (TextView) findViewById(R.id.tv_zd);
		tv_zd.setOnClickListener(this);
		tv_jy = (TextView) findViewById(R.id.tv_jy);
		tv_jy.setOnClickListener(this);
		tv_pic = (TextView) findViewById(R.id.tv_pic);
		tv_pic.setOnClickListener(this);
		if (Config.canzl) {
			tv_zd.setVisibility(View.GONE);
		}

	}

	private void changColor() {
		tv_ms.setTextColor(getResources().getColor(R.color.text292929));
		tv_kt.setTextColor(getResources().getColor(R.color.text292929));
		tv_pl.setTextColor(getResources().getColor(R.color.text292929));
		tv_zd.setTextColor(getResources().getColor(R.color.text292929));
		tv_jy.setTextColor(getResources().getColor(R.color.text292929));
		tv_pic.setTextColor(getResources().getColor(R.color.text292929));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			this.finish();
			break;
		case R.id.tv_zd:
			if (zd == null)
				zd = new Good_detail_zd();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, zd).commit();
			changColor();
			tv_zd.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case R.id.tv_ms:
			if (detail == null)
				detail = new F_good_detail();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, detail).commit();
			changColor();
			tv_ms.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case R.id.tv_kt:
			if (apply == null)
				apply = new Good_detail_apply();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, apply).commit();
			changColor();
			tv_kt.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case R.id.tv_pl:
			// Config.goodId = gfe.getId();
			// Config.commentsCount = commentsCount + "";
			if (commet == null)
				commet = new Good_detail_commet();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, commet).commit();
			changColor();
			tv_pl.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case R.id.tv_jy:
			// Config.goodId = gfe.getId();
			// Config.commentsCount = commentsCount + "";
			if (jy == null)
				jy = new Good_detail_trade();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, jy).commit();
			changColor();
			tv_jy.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		case R.id.tv_pic:
			// Config.goodId = gfe.getId();
			// Config.commentsCount = commentsCount + "";
			if (pic == null)
				pic = new Good_detail_pic();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_good_detail, pic).commit();
			changColor();
			tv_pic.setTextColor(getResources().getColor(R.color.bgtitle));
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Config.iszd = false;
	}

	
	@Override
	protected void onResume() {
		super.onPause();
		MobclickAgent.onPageStart( this.toString() );
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onResume();
		MobclickAgent.onPageEnd(this.toString());
		MobclickAgent.onPause(this);
	}
}
