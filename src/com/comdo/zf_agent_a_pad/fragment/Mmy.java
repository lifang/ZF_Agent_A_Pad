package com.comdo.zf_agent_a_pad.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.activity.LoginActivity;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.epalmpay.agentPad.R;
import com.umeng.analytics.MobclickAgent;

public class Mmy extends Fragment implements OnClickListener {
	private View view;
	private TextView tv_myinfo, tv_manager_shopper, tv_distribute,
			tv_transgoods, tv_staffmanagr, tv_exit;
	private RelativeLayout ll_dd, ll_shjl, ll_myinfo, ll_mysh, ll_plan,
			ll_exit;
	private MineMyinfo f_info;
	private Agentmanager f_agentmanager;
	private Distribute f_distribute;
	private Transgoods f_transgood;
	private Staffmanagr f_staffmanagr;
	private ImageView im1, im2, im3, im4, im5, im6;
	private int type=1;
	private ImageView im7;
	private RelativeLayout ll_safe;
	private TextView tv_safe;
	private MineSafe f_safe;

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
			Log.e("inflater", String.valueOf(inflater));
			Log.e("container", String.valueOf(container));
			view = inflater.inflate(R.layout.f_mine, container, false);

			init();
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_8) {

				if (!CheckRights.RIGHT_5) {
					setback();
					im3.setVisibility(View.VISIBLE);
					tv_distribute.setTextColor(getResources().getColor(
							R.color.bgtitle));
					f_distribute = new Distribute();
					// getChildFragmentManager().beginTransaction()
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.f_mine, f_distribute).commit();
				} else {
					setback();
					im2.setVisibility(View.VISIBLE);
					tv_manager_shopper.setTextColor(getResources().getColor(
							R.color.bgtitle));
					f_agentmanager = new Agentmanager();
					// getChildFragmentManager().beginTransaction()
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.f_mine, f_agentmanager).commit();
				}

			} else {

				setback();
				im1.setVisibility(View.VISIBLE);
				tv_myinfo
						.setTextColor(getResources().getColor(R.color.bgtitle));
				f_info = new MineMyinfo();

				// getChildFragmentManager().beginTransaction()
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.f_mine, f_info).commit();
			}

			/*
			 * f_info = new MineMyinfo();
			 * 
			 * getActivity().getSupportFragmentManager().beginTransaction()
			 * .replace(R.id.f_mine, f_info).commit();
			 */
			// init();

		} catch (InflateException e) {
			type = 1;
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e("tag", type + "");
		if (type == 1) {
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_8) {

				if (!CheckRights.RIGHT_5) {
					type = 3;
				} else {
					type = 2;
				}

			} else {

				type = 1;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.toString());
		changeTap();
	}

	private void changeTap() {
		
		switch (type) {
		case 1:
			setback();
			im1.setVisibility(View.VISIBLE);
			tv_myinfo.setTextColor(getResources().getColor(R.color.bgtitle));
			 //if (f_info == null)
			f_info = new MineMyinfo();
			/*
			 * else{ Fragment p = f_info.getParentFragment(); if(p!=null){
			 * p.getFragmentManager().getFragments().remove(R.id.f_mine); } }
			 */
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_info).commit();
			break;
		case 2:
			setback();
			im2.setVisibility(View.VISIBLE);
			tv_manager_shopper.setTextColor(getResources().getColor(
					R.color.bgtitle));
			// if (f_agentmanager == null)
			f_agentmanager = new Agentmanager();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_agentmanager).commit();

			break;
		case 3:
			setback();
			im3.setVisibility(View.VISIBLE);
			tv_distribute
					.setTextColor(getResources().getColor(R.color.bgtitle));
			// if (f_distribute == null)
			f_distribute = new Distribute();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_distribute).commit();
			break;
		case 4:
			setback();
			im4.setVisibility(View.VISIBLE);
			tv_transgoods
					.setTextColor(getResources().getColor(R.color.bgtitle));
			// if (f_transgood == null)
			f_transgood = new Transgoods();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_transgood).commit();
			break;
		case 5:
			setback();
			im5.setVisibility(View.VISIBLE);
			tv_staffmanagr.setTextColor(getResources()
					.getColor(R.color.bgtitle));
			// if (f_staffmanagr == null)
			f_staffmanagr = new Staffmanagr();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_staffmanagr).commit();
			break;
		case 6:
			setback();
			im6.setVisibility(View.VISIBLE);
			tv_exit.setTextColor(getResources().getColor(R.color.bgtitle));
			if (f_staffmanagr == null)
				f_staffmanagr = new Staffmanagr();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_staffmanagr).commit();
			break;
		default:
			break;
		}

	}

	private void init() {
		im1 = (ImageView) view.findViewById(R.id.im1);
		im2 = (ImageView) view.findViewById(R.id.im2);
		im3 = (ImageView) view.findViewById(R.id.im3);
		im4 = (ImageView) view.findViewById(R.id.im4);
		im5 = (ImageView) view.findViewById(R.id.im5);
		im6 = (ImageView) view.findViewById(R.id.im6);
		im7 = (ImageView) view.findViewById(R.id.im7);
		tv_myinfo = (TextView) view.findViewById(R.id.tv_myinfo);
		tv_manager_shopper = (TextView) view
				.findViewById(R.id.tv_manager_shopper);
		tv_distribute = (TextView) view.findViewById(R.id.tv_distribute);
		tv_transgoods = (TextView) view.findViewById(R.id.tv_transgoods);
		tv_staffmanagr = (TextView) view.findViewById(R.id.tv_staffmanagr);
		tv_exit = (TextView) view.findViewById(R.id.tv_exit);
		tv_safe = (TextView) view.findViewById(R.id.tv_safe);

		ll_dd = (RelativeLayout) view.findViewById(R.id.ll_dd);
		ll_shjl = (RelativeLayout) view.findViewById(R.id.ll_shjl);
		ll_myinfo = (RelativeLayout) view.findViewById(R.id.ll_myinfo);
		ll_mysh = (RelativeLayout) view.findViewById(R.id.ll_mysh);
		ll_plan = (RelativeLayout) view.findViewById(R.id.ll_plan);
		ll_exit = (RelativeLayout) view.findViewById(R.id.ll_exit);
		ll_safe = (RelativeLayout) view.findViewById(R.id.ll_safe);

		tv_myinfo.setOnClickListener(this);
		tv_manager_shopper.setOnClickListener(this);
		tv_distribute.setOnClickListener(this);
		tv_transgoods.setOnClickListener(this);
		tv_staffmanagr.setOnClickListener(this);
		tv_exit.setOnClickListener(this);
		ll_dd.setOnClickListener(this);
		ll_shjl.setOnClickListener(this);
		ll_myinfo.setOnClickListener(this);
		ll_mysh.setOnClickListener(this);
		ll_plan.setOnClickListener(this);
		ll_exit.setOnClickListener(this);
		tv_safe.setOnClickListener(this);
		ll_safe.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_myinfo:
		case R.id.ll_dd:
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_8) {
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			} else {
				Config.issafe=false;
				type = 1;
				setback();
				im1.setVisibility(View.VISIBLE);
				tv_myinfo
						.setTextColor(getResources().getColor(R.color.bgtitle));
				// if (f_info == null)
				f_info = new MineMyinfo();

				// getChildFragmentManager().beginTransaction()
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.f_mine, f_info).commit();
			}
			break;
		case R.id.ll_safe:
	
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_8) {
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			} else {
				Config.issafe=true;
				type = 1;
				setback();
				im7.setVisibility(View.VISIBLE);
				tv_safe
						.setTextColor(getResources().getColor(R.color.bgtitle));
				f_safe = new MineSafe();

				// getChildFragmentManager().beginTransaction()
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.f_mine, f_safe).commit();
			}
			break;
		case R.id.tv_manager_shopper:
		case R.id.ll_shjl:
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_5) {
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			} else {
				type = 2;
				setback();
				im2.setVisibility(View.VISIBLE);
				tv_manager_shopper.setTextColor(getResources().getColor(
						R.color.bgtitle));
				// if (f_agentmanager == null)
				f_agentmanager = new Agentmanager();
				// getChildFragmentManager().beginTransaction()
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.f_mine, f_agentmanager).commit();
			}
			break;
		case R.id.tv_distribute:
		case R.id.ll_myinfo:
			type = 3;
			setback();
			im3.setVisibility(View.VISIBLE);
			tv_distribute
					.setTextColor(getResources().getColor(R.color.bgtitle));
		//if (f_distribute == null)
			f_distribute = new Distribute();
		// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_distribute).commit();
			break;
		case R.id.tv_transgoods:
		case R.id.ll_mysh:
			type = 4;
			setback();
			im4.setVisibility(View.VISIBLE);
			tv_transgoods
					.setTextColor(getResources().getColor(R.color.bgtitle));
			// if (f_transgood == null)
			f_transgood = new Transgoods();
			// getChildFragmentManager().beginTransaction()
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.f_mine, f_transgood).commit();
			break;
		case R.id.tv_staffmanagr:
		case R.id.ll_plan:
			if (!CheckRights.IS_YIJI && !CheckRights.IS_ERJI
					&& !CheckRights.RIGHT_8) {
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			} else {
				type = 5;
				setback();
				tv_staffmanagr.setTextColor(getResources().getColor(
						R.color.bgtitle));
				im5.setVisibility(View.VISIBLE);
				// if (f_staffmanagr == null)
				f_staffmanagr = new Staffmanagr();
				// getChildFragmentManager().beginTransaction()
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.f_mine, f_staffmanagr).commit();
			}
			break;
		case R.id.tv_exit:
		case R.id.ll_exit:
			type = 6;
			setback();
			im6.setVisibility(View.VISIBLE);
			tv_exit.setTextColor(getResources().getColor(R.color.bgtitle));
			startActivity(new Intent(getActivity(), LoginActivity.class));
			getActivity().finish();
			break;
		default:
			break;
		}

	}

	private void setback() {
		im1.setVisibility(View.GONE);
		im2.setVisibility(View.GONE);
		im3.setVisibility(View.GONE);
		im4.setVisibility(View.GONE);
		im5.setVisibility(View.GONE);
		im6.setVisibility(View.GONE);
		im7.setVisibility(View.GONE);
		tv_myinfo.setTextColor(getResources().getColor(R.color.white));
		tv_manager_shopper.setTextColor(getResources().getColor(R.color.white));
		tv_distribute.setTextColor(getResources().getColor(R.color.white));
		tv_transgoods.setTextColor(getResources().getColor(R.color.white));
		tv_staffmanagr.setTextColor(getResources().getColor(R.color.white));
		tv_exit.setTextColor(getResources().getColor(R.color.white));
		tv_safe.setTextColor(getResources().getColor(R.color.white));
	}

	@Override
	public void onDestroyView() {
	
		try {
			FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction()

			// getChildFragmentManager().beginTransaction();
			;
			if (f_info != null)
				transaction.remove(f_info);
			if (f_agentmanager != null)
				transaction.remove(f_agentmanager);
			if (f_distribute != null)
				transaction.remove(f_distribute);
			if (f_transgood != null)
				transaction.remove(f_transgood);
			if (f_staffmanagr != null)
				transaction.remove(f_staffmanagr);
			if (f_safe != null)
				transaction.remove(f_safe);
			transaction.commit();
		} catch (Exception e) {
		}

		super.onDestroyView();
	}

	// @Override
	// public void onDetach() {
	// super.onDetach();
	// try {
	// Field childFragmentManager = Fragment.class
	// .getDeclaredField("mChildFragmentManager");
	// childFragmentManager.setAccessible(true);
	// childFragmentManager.set(this, null);
	// } catch (NoSuchFieldException e) {
	// throw new RuntimeException(e);
	// } catch (IllegalAccessException e) {
	// throw new RuntimeException(e);
	// }
	// }

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.toString());
	}
	@Override
	public void onDestroy() {
		Config.is_editadress=false;
		super.onDestroy();
	}
}
