package com.comdo.zf_agent_a_pad.fragment;





import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.AfterSaleActivity;
import com.comdo.zf_agent_a_pad.activity.UserManageListActivity;
import com.comdo.zf_agent_a_pad.entity.PicEntity;
import com.comdo.zf_agent_a_pad.trade.ApplyListActivity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.example.zf_agent_a_pad.R;

public class M_MianFragment extends Fragment implements OnClickListener {
	private View view;
	private RelativeLayout main_rl_pos, main_rl_renzhen, main_rl_zdgl,
			main_rl_jyls, main_rl_Forum, main_rl_wylc, main_rl_xtgg,
			main_rl_lxwm;

	private TextView LocationResult;
	// vp
	private ArrayList<String> mal = new ArrayList<String>();
	private ArrayList<PicEntity> myList = new ArrayList<PicEntity>();
	private ViewPager view_pager;
	private ImageView[] indicator_imgs;// 存放引到图片数组
	private View item;
	private LayoutInflater inflater;
	private int index_ima = 0;
	private ArrayList<String> ma = new ArrayList<String>();
	List<View> list = new ArrayList<View>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
		
				break;
			case 1:
				Toast.makeText(getActivity(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:

				break;
			case 4:

				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = inflater.inflate(R.layout.f_main,container,false);
		if (view != null) {
			
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			
			view = inflater.inflate(R.layout.f_main, container, false);
	
			initView();
		
		
		} catch (InflateException e) {

		}
		return view;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.main_rl_jyls: // 交易流水
			if (Config.CheckIsLogin(getActivity())) {
				//startActivity(new Intent(getActivity(), TradeFlowActivity.class));
			}
			break;
		case R.id.main_rl_pos: //我要进货

			//startActivity(new Intent(getActivity(), PosListActivity.class));
			
			break;
		case R.id.main_rl_renzhen: // 订单管理
			if (Config.CheckIsLogin(getActivity())) {
			//startActivity(new Intent(getActivity(), ApplyListActivity.class));
			}
			break;
		case R.id.main_rl_xtgg: // 售后记录
			startActivity(new Intent(getActivity(), AfterSaleActivity.class));
			break;
		case R.id.main_rl_wylc: // 售后记录
			startActivity(new Intent(getActivity(), UserManageListActivity.class));
			
			break;
		case R.id.main_rl_lxwm://开通认证
			startActivity(new Intent(getActivity(), ApplyListActivity.class));
			break;
		case R.id.main_rl_zdgl: // 终端管理
			if (Config.CheckIsLogin(getActivity())) {
			//startActivity(new Intent(getActivity(),
			//		TerminalManagerActivity.class));
			}
			break;
		default:
			break;
		}

	}

	private void initView() {

		main_rl_pos = (RelativeLayout) view.findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen = (RelativeLayout) view
				.findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl = (RelativeLayout) view.findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls = (RelativeLayout) view.findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum = (RelativeLayout) view.findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc = (RelativeLayout) view.findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm = (RelativeLayout) view.findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg = (RelativeLayout) view.findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);



	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
