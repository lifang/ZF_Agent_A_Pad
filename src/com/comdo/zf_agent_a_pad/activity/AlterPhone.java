package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AlterPhoneentity;
import com.comdo.zf_agent_a_pad.entity.BaseInfoEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AlterPhone extends Activity implements OnClickListener {
	private EditText ed_phone;
	private EditText ed_code;
	private EditText ed_nphone;
	private String phone;
	private AlterPhoneentity ape;
	private LinearLayout ll_next;
	private Button bt_yzm;
	private Button bt_check;
	private int Countmun = 120;
	private Thread myThread;
	private Boolean isRun = true;
	private Runnable runnable;
	private Boolean flag = false;
	final Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (Countmun == 0) {
					Toast.makeText(getApplicationContext(), Countmun, 1000)
							.show();
					isRun = false;
					bt_yzm.setClickable(true);

					bt_yzm.setText("发送验证码");
					System.out.println("destroy`" + Countmun);
				} else {
					Toast.makeText(getApplicationContext(), "cc" + Countmun,
							1000).show();
					Countmun--;
					bt_yzm.setText(Countmun + "秒后重新发送");
					System.out.println("Countmun`D2`" + Countmun);
				}

			}
			super.handleMessage(msg);
		}
	};
	private ImageView img_check1;
	private ImageView img_check_n1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterpass);
		// Config.AlterPhoneCode(getApplicationContext(),
		// MyApplication.NewUser.getAgentUserId(), "18762091710", callback);
		new TitleMenuUtil(AlterPhone.this, "修改手机").show();
		phone = getIntent().getStringExtra("phone");

		initView();
		runnable = new Runnable() {
			@Override
			public void run() {
				if (Countmun == 0) {
					Countmun = 120;
					bt_yzm.setClickable(true);
					bt_yzm.setText("发送验证码");
				} else {
					Countmun--;
					bt_yzm.setText(Countmun + "秒后重新发送");
					handler.postDelayed(this, 1000);
				}

			}
		};
	}

	private void initView() {
		ed_phone = (EditText) findViewById(R.id.ed_phone);
		ed_code = (EditText) findViewById(R.id.ed_code);
		ed_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
		
				if (ape != null) {
					if (s.toString().equals(ape.getDentcode())) {
						
						img_check1.setVisibility(View.VISIBLE);
						img_check_n1.setVisibility(View.GONE);
						flag = true;
					} else {
						img_check1.setVisibility(View.GONE);
						img_check_n1.setVisibility(View.VISIBLE);
						flag = false;
					}
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		ll_next = (LinearLayout) findViewById(R.id.ll_next);
		bt_yzm = (Button) findViewById(R.id.bt_yzm);
		bt_check = (Button) findViewById(R.id.bt_check);
		img_check1 = (ImageView) findViewById(R.id.img_check1);
		img_check_n1 = (ImageView) findViewById(R.id.img_check_n1);
		bt_yzm.setOnClickListener(this);
		bt_check.setOnClickListener(this);
		ll_next.setOnClickListener(this);
		ed_phone.setText(phone);
		ed_phone.setEnabled(false);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_yzm:
			getcode(phone);
			break;
		case R.id.bt_check:
			if (check())
				if (ed_code.getText().toString().trim()
						.equals(ape.getDentcode())) {
					img_check1.setVisibility(View.VISIBLE);
					img_check_n1.setVisibility(View.GONE);
					flag = true;
				} else {
					img_check1.setVisibility(View.GONE);
					img_check_n1.setVisibility(View.VISIBLE);
					flag = false;
				}

			break;
		case R.id.ll_next:
			if (check())
				if(ape==null){
					
				}else {
					if (ed_code.getText().toString().trim()
							.equals(ape.getDentcode())) {
						img_check1.setVisibility(View.VISIBLE);
						img_check_n1.setVisibility(View.GONE);
						flag = true;
					} else {
						img_check1.setVisibility(View.GONE);
						img_check_n1.setVisibility(View.VISIBLE);
						flag = false;
				}
				
				}
			if (check() && flag) {
				AlterPhone.this.finish();
				Intent i = new Intent(AlterPhone.this, AlterNPhone.class);
				startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(), "验证码错误！", 1000).show();
			}

			break;
		default:
			break;
		}

	}

	void getcode(String phone) {
		bt_yzm.setEnabled(false);
		handler.postDelayed(runnable, 1000);

		if (!StringUtil.isNull(phone)) {
			Config.getPhoneCode(AlterPhone.this, phone,
					MyApplication.NewUser.getAgentUserId(),
					new HttpCallback<AlterPhoneentity>(AlterPhone.this) {
						@Override
						public void onSuccess(AlterPhoneentity data) {
							ape = data;
							// Toast.makeText(getApplicationContext(),data+"验证码发送成功",
							// 1000).show();
						}

						@Override
						public TypeToken<AlterPhoneentity> getTypeToken() {
							return new TypeToken<AlterPhoneentity>() {
							};
						}
					});
		} else {

		}
	}

	boolean check() {
		if (StringUtil.isNull(ed_phone.getText().toString())) {

			Toast.makeText(getApplicationContext(), "原手机号不能为空！", 1000).show();
			return false;
		}

		if (StringUtil.isNull(ed_code.getText().toString())) {
			Toast.makeText(getApplicationContext(), "验证码不能为空！", 1000).show();
			return false;
		}
		return true;
	}
}
