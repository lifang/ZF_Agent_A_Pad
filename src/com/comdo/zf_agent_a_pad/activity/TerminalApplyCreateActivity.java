package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_PROVINCE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.trade.entity.Province;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplyCreateActivity extends Activity implements
		View.OnClickListener {

	private TextView selectedcity, tv_code;
	private int mChannelId;
	private LinearLayout getCode, check, selectcity;
	private EditText username, setCode, checkCode, pwd, confirmpwd, phonenum;
	public String vcode = "";
	private String name, password = "", mCode = "";
	private ImageView img_check_y, img_check_n;
	private Boolean isRun = true;
	private Boolean checkcode = false;
	private Boolean verification = false;
	private Province mMerchantProvince;
	private City mMerchantCity;
	private Button mSubmitBtn, close;

	final List<String> list = new ArrayList<String>();
	private int Countmun = 120;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_new_user);

		username = (EditText) findViewById(R.id.username);
		setCode = (EditText) findViewById(R.id.phonenum);
		checkCode = (EditText) findViewById(R.id.checkcode);
		checkCode.addTextChangedListener(mTextWatcher);
		pwd = (EditText) findViewById(R.id.pwd);
		confirmpwd = (EditText) findViewById(R.id.confirmpwd);
		confirmpwd.addTextChangedListener(mTextWatcher);
		getCode = (LinearLayout) findViewById(R.id.get_code);
		getCode.setOnClickListener(this);
		selectcity = (LinearLayout) findViewById(R.id.selectcity);
		selectcity.setOnClickListener(this);
		check = (LinearLayout) findViewById(R.id.tv_check);
		check.setOnClickListener(this);
		tv_code = (TextView) findViewById(R.id.code);
		selectedcity = (TextView) findViewById(R.id.selectedcity);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);
		mSubmitBtn.setOnClickListener(this);
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(this);
		img_check_y = (ImageView) findViewById(R.id.img_check_y);
		img_check_n = (ImageView) findViewById(R.id.img_check_n);
		checkcode = true;
		runnable = new Runnable() {
			@Override
			public void run() {
				if (Countmun == 0) {
					Countmun = 120;
					getCode.setClickable(true);
					tv_code.setText("重新发送验证码");
				} else {
					Countmun--;
					tv_code.setText(Countmun + "秒后重新发送");
					handler.postDelayed(this, 1000);
				}

			}
		};
	}

	private final TextWatcher mTextWatcher = new TextWatcherAdapter() {

		public void afterTextChanged(final Editable gitDirEditText) {
			if (confirmpwd.getText().toString()
					.equals(pwd.getText().toString())) {
				password = pwd.getText().toString();
				verification = true;
			}
			if (!"".equals(vcode)
					&& vcode.equals(checkCode.getText().toString())) {
				img_check_y.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
				checkcode = true;
			} else {
				img_check_y.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
				checkcode = false;
			}
			name = StringUtil.replaceBlank((username.getText().toString()));

			updateUIWithValidation();
		}
	};

	private void updateUIWithValidation() {
		final boolean enabled = mChannelId > 0
				&& username.getText().toString().length() > 0
				&& setCode.getText().toString().length() > 0
				&& pwd.getText().toString().length() > 0
				&& checkCode.getText().toString().length() > 0
				&& confirmpwd.getText().toString().length() > 0;
		mSubmitBtn.setEnabled(enabled);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			Config.addCustomer(this, setCode.getText().toString(), name,
					password, mChannelId, MyApplication.NewUser.getAgentId(),
					new HttpCallback(TerminalApplyCreateActivity.this) {

						@Override
						public void onSuccess(Object data) {

							CommonUtil.toastShort(
									TerminalApplyCreateActivity.this,
									getResources().getString(
											R.string.terminal_new_success));
							finish();
						}

						@Override
						public TypeToken getTypeToken() {

							return null;
						}
					});

			break;
		case R.id.selectcity:
			Intent intent = new Intent(TerminalApplyCreateActivity.this,
					CityProvinceActivity.class);
			intent.putExtra(SELECTED_PROVINCE, mMerchantProvince);
			intent.putExtra(SELECTED_CITY, mMerchantCity);
			startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			break;
		case R.id.close:
			this.finish();
			break;
		case R.id.get_code:
			getCode();
			break;
		case R.id.tv_check:
			System.out.println("vcode" + vcode);

			if (checkCode.getText().toString().equals(vcode)) {
				img_check_y.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
				checkcode = true;
			} else {
				img_check_y.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
				checkcode = false;
			}

		}
	}

	private void getCode() {

		mCode = StringUtil.replaceBlank(setCode.getText().toString());
		if (mCode.equals("") && mCode == null) {
			Toast.makeText(getApplicationContext(), "请输入邮箱或者手机号", 1000).show();
		} else {
			if (RegText.isMobileNO(mCode)) {
				ggg(mCode);
			} else {
				CommonUtil.toastShort(this,
						getResources().getString(R.string.phone_no_error));

			}
		}
	}

	final Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (Countmun == 0) {
					Toast.makeText(getApplicationContext(), Countmun, 1000)
							.show();
					isRun = false;
					getCode.setClickable(true);
					tv_code.setText("重新发送验证码");
					System.out.println("destroy`" + Countmun);
				} else {
					Toast.makeText(getApplicationContext(), "cc" + Countmun,
							1000).show();
					Countmun--;
					tv_code.setText(Countmun + "秒后重新发送");
					System.out.println("Countmun`D2`" + Countmun);
				}

			}
			super.handleMessage(msg);
		}
	};

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

	public void ggg(String phonenumber) {

		Config.sendPhoneVerificationCodeReg(TerminalApplyCreateActivity.this,
				phonenumber,

				new HttpCallback(TerminalApplyCreateActivity.this) {

					@Override
					public void onSuccess(Object data) {

						Toast.makeText(TerminalApplyCreateActivity.this,
								"验证码发送成功", 1000).show();
						handler.postDelayed(runnable, 1000);
						getCode.setClickable(false);
						vcode = data.toString();

					}

					@Override
					public TypeToken getTypeToken() {

						return null;
					}
				});
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CHOOSE_CITY: {
			mMerchantProvince = (Province) data
					.getSerializableExtra(SELECTED_PROVINCE);
			mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
			selectedcity.setText(mMerchantCity.getName());
			mChannelId = mMerchantCity.getId();
			break;
		}

		}
	}

	// 暂停：onStart()->onPause()
	@Override
	protected void onPause() {
		super.onPause();

		SharedPreferences.Editor saveData = getPreferences(0).edit();
		saveData.putString("vcode", vcode);
		saveData.putBoolean("check", checkcode);
		saveData.commit();
	}

	// 重启：onPause()->onResume()
	@Override
	protected void onResume() {
		super.onResume();
		updateUIWithValidation();
		// 从共享数据存储对象中获取所需的数据
		SharedPreferences getData = getPreferences(0);
		vcode = getData.getString("vcode", null);
		checkcode = getData.getBoolean("check", false);
		if (checkcode) {
			img_check_y.setVisibility(View.VISIBLE);
			img_check_n.setVisibility(View.GONE);
		} else {

			img_check_y.setVisibility(View.GONE);
			img_check_n.setVisibility(View.VISIBLE);
		}
	}
}
