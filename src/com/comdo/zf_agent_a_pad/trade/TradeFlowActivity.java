package com.comdo.zf_agent_a_pad.trade;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.CONSUME;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.LIFE_PAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.PHONE_PAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.REPAYMENT;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeType.TRANSFER;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.comdo.zf_agent_a_pad.trade.widget.MyTabWidget;
import com.comdo.zf_agent_a_pad.trade.widget.MyViewPager;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.umeng.analytics.MobclickAgent;


public class TradeFlowActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

	private MyTabWidget mTabWidget;
	private MyViewPager mViewPager;

	private List<TradeFlowFragment> mFragments;

	private int isPrompt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_trade_flow);
		new TitleMenuUtil(this, "交易流水").show();
		MobclickAgent.openActivityDurationTrack(false);
		SharedPreferences share=getSharedPreferences("isPrompt", Context.MODE_PRIVATE);
		isPrompt = share.getInt("isPrompt",0);
		
		initViews();
		if (isPrompt == 0) {
			showTagDialog();
		}
		Log.e("", getWindowManager().getDefaultDisplay().getHeight()+"====="+getWindowManager().getDefaultDisplay().getWidth());
	}

	private void showTagDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				TradeFlowActivity.this);
		final AlertDialog dialog = builder.create();
		builder.setTitle("提示");
		builder.setCancelable(false);
		builder.setMessage("pad端交易流水查询仅供单台终端查询，完整查询功能请登陆PC端合作伙伴平台");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						SharedPreferences sharedPreferences = getSharedPreferences("isPrompt", Context.MODE_PRIVATE); //私有数据
						Editor editor = sharedPreferences.edit();//获取编辑器
						editor.putInt("isPrompt", 1);
						editor.commit();//提交修改
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						dialog.dismiss();
						finish();
					}

				});

		builder.create().show();
	
	}

	private void initViews() {
		//new TitleMenuUtil(this, getString(R.string.title_trade_flow)).show();
		mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
		mViewPager = (MyViewPager) findViewById(R.id.view_pager);
		mFragments = new ArrayList<TradeFlowFragment>();

		String[] tabs = getResources().getStringArray(R.array.trade_flow_tabs);
		for (int i = 0; i < tabs.length; i++) {
			TradeFlowFragment fragment = null;
			if (i == 0) {
				fragment = TradeFlowFragment.newInstance(TRANSFER);
			}else if (i == 1) {
				fragment = TradeFlowFragment.newInstance(CONSUME);
			}else if (i == 2) {
				fragment = TradeFlowFragment.newInstance(REPAYMENT);
			}else if (i == 3) {
				fragment = TradeFlowFragment.newInstance(LIFE_PAY);
			}else if (i == 4) {
				fragment = TradeFlowFragment.newInstance(PHONE_PAY);
			}

			// TradeFlowFragment fragment = TradeFlowFragment.newInstance(i + 1);
			mFragments.add(fragment);
			mTabWidget.addTab(tabs[i]);
		}
//		TradeFlowFragment transferFragment = TradeFlowFragment.newInstance(CONSUME);
//		TradeFlowFragment consumeFragment = TradeFlowFragment.newInstance(TRANSFER);
//		TradeFlowFragment repaymentFragment = TradeFlowFragment.newInstance(REPAYMENT);
//		TradeFlowFragment lifePayFragment = TradeFlowFragment.newInstance(LIFE_PAY);
//		TradeFlowFragment phonePayFragment = TradeFlowFragment.newInstance(PHONE_PAY);
//		mFragments.add(transferFragment);
//		mFragments.add(consumeFragment);
//		mFragments.add(repaymentFragment);
//		mFragments.add(lifePayFragment);
//		mFragments.add(phonePayFragment);

		mTabWidget.setViewPager(mViewPager);
		mViewPager.setAdapter(new TradeFlowPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(this);

		// init to select the first tab and fragment
		mTabWidget.updateTabs(0);
		mViewPager.setCurrentItem(0);

	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {
	}

	@Override
	public void onPageSelected(int i) {
		mTabWidget.updateTabs(i);
	}

	@Override
	public void onPageScrollStateChanged(int i) {
	}

	public class TradeFlowPagerAdapter extends FragmentPagerAdapter {

		public TradeFlowPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			return mFragments.get(i);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}
	}
	
	 @Override
		protected void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}
	    
	    @Override
		protected void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
		}

}
