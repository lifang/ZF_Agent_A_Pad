package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.fragment.M_MianFragment;
import com.comdo.zf_agent_a_pad.fragment.Mmy;
import com.comdo.zf_agent_a_pad.fragment.Mwdxx;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.SetPopWindow;
import com.example.zf_agent_a_pad.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private M_MianFragment f_sy;
	private Mwdxx f_xx;
	private ImageView im_sy, im_ghc, im_mess, im_wd;
	private TextView textsy, textghc, textmes, textwd;
	private RelativeLayout re_shopcar, re_myinfo, re_mine, re_sy;
	private LinearLayout set;
	private Mmy f_my;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Display display = getWindowManager().getDefaultDisplay();
		Config.ScreenWidth = display.getWidth();
		Config.ScreenHeight = display.getHeight();
		f_sy = new M_MianFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.m_fragment, f_sy).commit();
		initView();
	}

	private void initView() {
		textsy = (TextView) findViewById(R.id.textsy);
		textghc = (TextView) findViewById(R.id.textghc);
		textmes = (TextView) findViewById(R.id.textmes);
		textwd = (TextView) findViewById(R.id.textwd);

		im_sy = (ImageView) findViewById(R.id.laa1);
		im_ghc = (ImageView) findViewById(R.id.igw);
		im_mess = (ImageView) findViewById(R.id.im_mess);
		im_wd = (ImageView) findViewById(R.id.im_wd);
		set = (LinearLayout) findViewById(R.id.set);
		set.setOnClickListener(this);
		re_sy = (RelativeLayout) findViewById(R.id.main_rl_sy);
		re_shopcar = (RelativeLayout) findViewById(R.id.main_rl_gwc);
		re_myinfo = (RelativeLayout) findViewById(R.id.main_rl_pos1);
		re_mine = (RelativeLayout) findViewById(R.id.main_rl_my);
		re_sy.setOnClickListener(this);
		re_shopcar.setOnClickListener(this);
		re_myinfo.setOnClickListener(this);
		re_mine.setOnClickListener(this);

	}

	private void changTabBg() {
		/*
		 * im_sy.setBackgroundResource(R.drawable.home);
		 * im_ghc.setBackgroundResource(R.drawable.good);
		 * im_mess.setBackgroundResource(R.drawable.message);
		 * im_wd.setBackgroundResource(R.drawable.mine);
		 * textsy.setTextColor(getResources().getColor(R.color.white));
		 * textghc.setTextColor(getResources().getColor(R.color.white));
		 * textmes.setTextColor(getResources().getColor(R.color.white));
		 * textwd.setTextColor(getResources().getColor(R.color.white));
		 */
		im_sy.setBackgroundResource(R.drawable.home2);
		im_ghc.setBackgroundResource(R.drawable.good1);
		im_mess.setBackgroundResource(R.drawable.message2);
		im_wd.setBackgroundResource(R.drawable.mine2);
		textsy.setTextColor(getResources().getColor(R.color.white));
		textghc.setTextColor(getResources().getColor(R.color.white));
		textmes.setTextColor(getResources().getColor(R.color.white));
		textwd.setTextColor(getResources().getColor(R.color.white));

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.main_rl_sy:
			Config.TABID = 1;
			changTabBg();
			im_sy.setBackgroundResource(R.drawable.home);
			textsy.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_sy == null)
				f_sy = new M_MianFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_sy).commit();
			break;
		case R.id.main_rl_gwc:

			if (CheckRights.RIGHT_1 || CheckRights.RIGHT_2) {

				if (MyApplication.NewUser.getTypes() == 6
						|| MyApplication.NewUser.getParent_id() == 0) {
					Config.TABID = 2;
					Intent i = new Intent(MainActivity.this,
							PosListActivity.class);
					startActivity(i);
				} else {
					CommonUtil.toastShort(MainActivity.this,
							R.string.right_not_match);
				}

			} else {
				CommonUtil.toastShort(MainActivity.this,
						R.string.right_not_match);
			}

			break;
		case R.id.main_rl_pos1:
			Config.TABID = 3;
			changTabBg();
			im_mess.setBackgroundResource(R.drawable.message);
			textmes.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_xx == null)
				f_xx = new Mwdxx();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_xx).commit();
			break;
		case R.id.main_rl_my:
			Log.e("4", "4");
			Config.TABID = 4;
			changTabBg();
			im_wd.setBackgroundResource(R.drawable.mine);
			textwd.setTextColor(getResources().getColor(R.color.o));
			if (f_my == null)
				f_my = new Mmy();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_my).commit();
			break;
		case R.id.set:
			showSet();
			break;

		default:
			break;
		}
	}

	private void showSet() {
		SetPopWindow set = new SetPopWindow(this);
		set.showAtLocation(findViewById(R.id.main), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		switch (Config.TABID) {
		case 1:
			changTabBg();
			im_sy.setBackgroundResource(R.drawable.home);
			textsy.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_sy == null)
				f_sy = new M_MianFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_sy).commit();
			break;
		case 2:

			break;
		case 3:
			changTabBg();
			im_mess.setBackgroundResource(R.drawable.message);
			textmes.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_xx == null)
				f_xx = new Mwdxx();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_xx).commit();
			break;
		case 4:
			changTabBg();
			im_wd.setBackgroundResource(R.drawable.mine);
			textwd.setTextColor(getResources().getColor(R.color.o));
			if (f_my == null)
				f_my = new Mmy();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_my).commit();

			break;

		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		switch (Config.TABID) {/*
		case 1:
			changTabBg();
			im_sy.setBackgroundResource(R.drawable.home);
			textsy.setTextColor(getResources().getColor(R.color.bgtitle));
			 if (f_sy == null)
			f_sy = new M_MianFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_sy).commit();
			break;
		case 2:
			
			break;
		case 3:
			changTabBg();
			im_mess.setBackgroundResource(R.drawable.message);
			textmes.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_xx == null)
				f_xx = new Mwdxx();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_xx).commit();
			break;
		case 4:
			changTabBg();
			im_wd.setBackgroundResource(R.drawable.mine);
			textwd.setTextColor(getResources().getColor(R.color.o));
			if(f_my==null)
				f_my=new Mmy();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.m_fragment, f_my).commit();
			
			break;

		default:
			break;
		*/}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Config.TABID = 1;
	}
}
