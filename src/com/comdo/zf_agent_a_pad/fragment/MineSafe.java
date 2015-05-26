package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.AlterEmali;
import com.comdo.zf_agent_a_pad.activity.AlterNEmali;
import com.comdo.zf_agent_a_pad.activity.AlterNPhone;
import com.comdo.zf_agent_a_pad.activity.AlterPhone;
import com.comdo.zf_agent_a_pad.activity.LoginActivity;
import com.comdo.zf_agent_a_pad.activity.MainActivity;
import com.comdo.zf_agent_a_pad.adapter.AddressManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.BaseInfoEntity;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.trade.entity.Province;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class MineSafe extends Fragment implements OnClickListener {

	public static Handler myHandler;
	private View view;
	// change psw
	private EditText et_oldpaw, et_newpaw, et_confirmpaw;
	private Button btn_save_address;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// view = inflater.inflate(R.layout.f_main,container,false);

		Log.e("container", String.valueOf(container));
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		// if(container!=null)
		//
		// { container.getParent()..clearChildFocus(arg0); }

		try {
			view = inflater.inflate(R.layout.mysafe, container, false);
			chpawinit();
		} catch (InflateException e) {

		}
		return view;
	}

	/*
	 * @Override public void onAttach(Activity activity) {
	 * super.onAttach(activity); mActivity=activity; }
	 */
	@Override
	public void onStart() {
		super.onStart();
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}


	private void chpawinit() {
		et_oldpaw = (EditText) view.findViewById(R.id.et_oldpaw);
		et_newpaw = (EditText) view.findViewById(R.id.et_newpaw);
		et_confirmpaw = (EditText) view.findViewById(R.id.et_confirmpaw);
		btn_save_address = (Button) view.findViewById(R.id.btn_save);
	
		btn_save_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(StringUtil.isNull(et_oldpaw.getText().toString())){
					Toast.makeText(getActivity(), "原始密码不能为空", 1000).show();
				}else if(StringUtil.isNull(et_newpaw.getText().toString())){
					Toast.makeText(getActivity(), "新密码不能为空", 1000).show();
				}
				if(et_newpaw.getText().toString().trim().length()<6||et_newpaw.getText().toString().trim().length()>20){
					Toast.makeText(getActivity(), "密码为6-20位！", 1000).show();
				}
				else if(StringUtil.isNull(et_confirmpaw.getText().toString())){
					Toast.makeText(getActivity(), "确认密码不能为空", 1000).show();
				}else if(!et_newpaw.getText().toString().equals(et_confirmpaw.getText().toString())){
					Toast.makeText(getActivity(), "两次密码不一致!", 1000).show();
				}else{
					changepaw();
				}
					
				

			}
		});

	}

	

	
	protected void changepaw() {
		Config.changePaw(getActivity(), MyApplication.NewUser.getId(),
				StringUtil.Md5(et_oldpaw.getText().toString()),
				StringUtil.Md5(et_newpaw.getText().toString()),
				new HttpCallback(getActivity()) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(getActivity(), "密码修改成功");
						et_oldpaw.setText("");
						et_newpaw.setText("");
						et_confirmpaw.setText("");
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
	}

}

