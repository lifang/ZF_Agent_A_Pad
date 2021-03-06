package com.comdo.zf_agent_a_pad.fragment;

import java.security.MessageDigest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class Mchpaw extends Fragment {
	private View view;
	private EditText et_oldpaw, et_newpaw, et_confirmpaw;
	private Button btn_save;
	private int id = com.comdo.zf_agent_a_pad.util.MyApplication.NewUser
			.getId();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// view = inflater.inflate(R.layout.f_mine, container,false);
		// initView();

		if (view != null) {
			Log.i("222222", "11111111");
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.changepaw, container, false);
			init();
		} catch (InflateException e) {

		}

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changepaw();

			}
		});
	}

	protected void changepaw() {
		Config.changePaw(getActivity(), id,
				StringUtil.Md5(et_oldpaw.getText().toString()),
				StringUtil.Md5(et_newpaw.getText().toString()),
				new HttpCallback(getActivity()) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(getActivity(), "密码修改成功");

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

	private void init() {
		et_oldpaw = (EditText) view.findViewById(R.id.et_oldpaw);
		et_newpaw = (EditText) view.findViewById(R.id.et_newpaw);
		et_confirmpaw = (EditText) view.findViewById(R.id.et_confirmpaw);
		btn_save = (Button) view.findViewById(R.id.btn_save);

	}

	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
