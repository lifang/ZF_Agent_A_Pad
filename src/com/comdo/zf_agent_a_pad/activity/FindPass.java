package com.comdo.zf_agent_a_pad.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class FindPass extends Activity implements OnClickListener {
	private TextView tv_code, tv_check;
	private EditText login_edit_email, login_edit_code, login_edit_pass,
			login_edit_pass2;
	private LinearLayout login_linear_deletemali,
			login_linear_deletpass, login_linear_deletpass2,
			login_linear_signin, ll_jy_type;
	private int Countmun = 120;
	private Thread myThread;
	private Boolean isRun = true;
	private ImageView img_check, img_check_n;
	public String vcode = "";
	private String url, email, pass;
	private Boolean chenckcode = false;
	private Runnable runnable;
	private boolean ismail = false;
	final Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (Countmun == 0) {
					Toast.makeText(getApplicationContext(), Countmun, 1000)
							.show();
					isRun = false;
					tv_code.setClickable(true);

					tv_code.setText("发送验证码");
					System.out.println("destroy`" + Countmun);
				} else {
					Toast.makeText(getApplicationContext(), "cc" + Countmun,
							1000).show();
					Countmun--;
					tv_code.setText(Countmun + "秒后重新发送");
					System.out.println("Countmun`D2`" + Countmun);
				}
			case 2:
				if (!StringUtil.isNull(login_edit_code.getText().toString().trim())) {
					if (login_edit_code.getText().toString().equals(vcode)) {
						img_check.setVisibility(View.VISIBLE);
						img_check_n.setVisibility(View.GONE);
						chenckcode = true;
					} else {
						img_check.setVisibility(View.GONE);
						img_check_n.setVisibility(View.VISIBLE);
						chenckcode = false;
					}
				}
			}
			super.handleMessage(msg);
		}
	};
	private LinearLayout ll_isshow;
	private LinearLayout ll_isshow1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpass);

		new TitleMenuUtil(FindPass.this, "找回密码").show();
		System.out.println("Register4phone---");
		initView();

		img_check.setVisibility(View.GONE);
		img_check_n.setVisibility(View.GONE);
		chenckcode = true;
		runnable = new Runnable() {
			@Override
			public void run() {
				if (Countmun == 0) {
					Countmun = 120;
					tv_code.setClickable(true);
					tv_code.setText("发送验证码");
				} else {
					Countmun--;
					tv_code.setText(Countmun + "秒后重新发送");
					handler.postDelayed(this, 1000);
				}

			}
		};

	}

	private void initView() {
		ll_isshow1 = (LinearLayout) findViewById(R.id.isshow1);
		ll_isshow = (LinearLayout) findViewById(R.id.isshow);
		tv_check = (TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		img_check = (ImageView) findViewById(R.id.img_check);
		img_check_n = (ImageView) findViewById(R.id.img_check_n);
		tv_code = (TextView) findViewById(R.id.tv_code);
		tv_code.setOnClickListener(this);

		login_linear_signin = (LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		login_linear_deletemali = (LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
		login_edit_email = (EditText) findViewById(R.id.login_edit_email);
		String s = getIntent().getStringExtra("phone");
		login_edit_email.setText(s);
		vcode = getIntent().getStringExtra("vcode");

		login_edit_email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0) {

					login_linear_deletemali.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemali.setVisibility(View.GONE);
				}
				if (s.toString().contains(".")) {
					ll_isshow.setVisibility(View.GONE);
					tv_code.setVisibility(View.VISIBLE);
					tv_code.setText(getResources()
							.getString(R.string.send_mail));
					ll_isshow1.setVisibility(View.GONE);
					ismail = true;
				} else {
					ll_isshow.setVisibility(View.VISIBLE);
					tv_code.setVisibility(View.VISIBLE);
					ll_isshow1.setVisibility(View.VISIBLE);
					ismail = false;
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

		// login_linear_deletpass

		login_edit_code = (EditText) findViewById(R.id.login_edit_code);
		login_edit_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
							handler.sendEmptyMessage(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}).start();
			}
		});
		// login_linear_deletpass
		login_linear_deletpass = (LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
		login_edit_pass = (EditText) findViewById(R.id.login_edit_pass);
		login_edit_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0) {

					login_linear_deletpass.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass.setVisibility(View.GONE);
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
		login_linear_deletpass2 = (LinearLayout) findViewById(R.id.login_linear_deletpass2);
		login_linear_deletpass2.setOnClickListener(this);
		login_edit_pass2 = (EditText) findViewById(R.id.login_edit_pass2);
		login_edit_pass2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0) {

					login_linear_deletpass2.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass2.setVisibility(View.GONE);
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

	}

	private boolean check() {

		email = StringUtil.replaceBlank(login_edit_email.getText().toString());
		if (email.length() == 0) {
			Toast.makeText(getApplicationContext(), "手机号不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!ismail) {
			if (StringUtil.replaceBlank(login_edit_code.getText().toString())
					.length() == 0) {
				Toast.makeText(getApplicationContext(), "请输入验证码",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		if (!ismail) {
			if (!login_edit_code.getText().toString().endsWith(vcode)) {
				Toast.makeText(getApplicationContext(), "验证码错误",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		pass = StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if (pass.length() == 0) {
			Toast.makeText(getApplicationContext(), "密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!login_edit_pass2.getText().toString().equals(pass)) {
			Toast.makeText(getApplicationContext(), "二次密码不一样",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		pass = StringUtil.Md5(pass);
		System.out.println("pass" + pass);
		return true;
	}

	private void getCode() {
		// tv_code.setText("Resent Code");
		// handler.postDelayed(runnable, 1000);
		email = StringUtil.replaceBlank(login_edit_email.getText().toString());

		if (RegText.isMobileNO(email)) {

			Config.getPhonePass(FindPass.this, email, new HttpCallback(
					FindPass.this) {

				@Override
				public void onSuccess(Object data) {

					vcode = data.toString();

					System.out.println("-vcode---" + vcode);
					handler.postDelayed(runnable, 1000);
					tv_code.setClickable(false);
					Toast.makeText(getApplicationContext(), "发送成功", 1000)
							.show();

				}

				@Override
				public TypeToken getTypeToken() {
					return null;
				}
			});

		} else if (RegText.isEmail(email)) {
			Config.getEmailPass(FindPass.this, login_edit_email.getText()
					.toString(),

			new HttpCallback(FindPass.this) {
				@Override
				public void onSuccess(Object data) {

					Intent i = new Intent(getApplicationContext(),
							FindpassmailSucces.class);
					i.putExtra("value", login_edit_email.getText().toString());
					if (ismail) {
						i.putExtra("type", "0");
					} else {
						i.putExtra("type", "1");
					}
					startActivity(i);
					FindPass.this.finish();
				}

				@Override
				public TypeToken getTypeToken() {
					return null;
				}
			});
		} else {
			CommonUtil.toastShort(FindPass.this,
					getResources().getString(R.string.format_error));
		}

	}

	public class MyThread implements Runnable { // thread
		@Override
		public void run() {

			while (isRun) {
				System.out.println("run``" + Countmun);

				try {
					Thread.sleep(1000); // sleep 1000ms
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				} catch (Exception e) {

				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_code:
			getCode();
			break;

		case R.id.tv_check:
			System.out.println("vcode" + vcode);

			if (login_edit_code.getText().toString().equals(vcode)) {
				img_check.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
				chenckcode = true;
			} else {
				img_check.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
				chenckcode = false;
			}

			break;
		case R.id.login_linear_signin: //

			if (check()) {
				sure();

			}

			break;
		case R.id.login_linear_deletemali:
			login_edit_email.setText("");
			break;

		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		default:
			break;
		}

	}

	private void sure() {

		RequestParams params = new RequestParams();
		params.put("password", pass);
		params.put("code", vcode);
		params.put("username", email);

		System.out.println(pass + "-------" + email + "----" + vcode);
		Config.updatePassword(FindPass.this,email, pass, vcode, 

		new HttpCallback(FindPass.this) {
			@Override
			public void onSuccess(Object data) {
				Toast.makeText(FindPass.this, "修改密码成功", 1000).show();
				Intent i = new Intent(getApplication(),
						FindpassmailSucces.class);
				i.putExtra("value", login_edit_email.getText().toString());
				if (ismail) {
					i.putExtra("type", "0");
				} else {
					i.putExtra("type", "1");
				}
				startActivity(i);
				FindPass.this.finish();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});

	}

}
