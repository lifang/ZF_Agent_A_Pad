package com.comdo.zf_agent_a_pad.activity;


import com.comdo.zf_agent_a_pad.fragment.M_MianFragment;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.SetPopWindow;
import com.example.zf_agent_a_pad.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends FragmentActivity implements OnClickListener{
	private M_MianFragment f_sy;
	private ImageView im_sy,im_ghc,im_mess,im_wd;
	private TextView textsy,textghc,textmes,textwd;
	private RelativeLayout re_shopcar,re_myinfo,re_mine,re_sy;
	private LinearLayout set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

		re_sy = (RelativeLayout) findViewById(R.id.main_rl_sy);
		re_shopcar = (RelativeLayout) findViewById(R.id.main_rl_gwc);
		re_myinfo = (RelativeLayout) findViewById(R.id.main_rl_pos1);
		re_mine = (RelativeLayout) findViewById(R.id.main_rl_my);
		re_sy.setOnClickListener(this);
		re_shopcar.setOnClickListener(this);
		re_myinfo.setOnClickListener(this);
		re_mine.setOnClickListener(this);

		set = (LinearLayout) findViewById(R.id.set);
		set.setOnClickListener(this);
		
	}
	private void changTabBg() {
		im_sy.setBackgroundResource(R.drawable.home);
		im_ghc.setBackgroundResource(R.drawable.good);
		im_mess.setBackgroundResource(R.drawable.message);
		im_wd.setBackgroundResource(R.drawable.mine);
		textsy.setTextColor(getResources().getColor(R.color.white));
		textghc.setTextColor(getResources().getColor(R.color.white));
		textmes.setTextColor(getResources().getColor(R.color.white));
		textwd.setTextColor(getResources().getColor(R.color.white));

	}
	@Override
	public void onClick(View view) {
switch (view.getId()) {
		
		case R.id.main_rl_sy:
			changTabBg();
			im_sy.setBackgroundResource(R.drawable.home);
			textsy.setTextColor(getResources().getColor(R.color.o));
			//if (f_sy == null)
				f_sy = new M_MianFragment();

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.m_fragment, f_sy).commit();
			break;
		case R.id.main_rl_gwc:
		
			break;
		case R.id.main_rl_pos1:
		
			break;
		case R.id.main_rl_my:
		
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
}
