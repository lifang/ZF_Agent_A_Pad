package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplyCreateActivity extends Activity implements
		View.OnClickListener {

	private View mChooseChannel;
	private TextView mPayChannel, tv_code;
	private int mChannelId;
	private String mChannelName, mCode;
	private LinearLayout getCode,check;
	private EditText mTerminalNumber, setCode,checkCode;
	private EditText mShopName;
	private Button mSubmitBtn;
	public String vcode = "";
	private String terminalNum, shopName;
    private ImageView img_check_y,img_check_n;
	private Boolean isRun = true;
	private Boolean chenckcode = false;
	private ArrayAdapter<String> adapter;

	private Button close;

	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	final List<String> list = new ArrayList<String>();
	private int Countmun = 120;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_new_user);

		items = new ArrayList<Map<String, Object>>();

		// API.getChannelList(this, new
		// HttpCallback<List<TerminalChannel>>(this) {
		//
		// @Override
		// public void onSuccess(List<TerminalChannel> data) {
		// for (TerminalChannel channel : data) {
		// Map<String, Object> item = new HashMap<String, Object>();
		// item.put("id", channel.getId());
		// item.put("name", channel.getName());
		// list.add(channel.getName());
		// items.add(item);
		// }
		// myHandler.sendEmptyMessage(1);
		//
		// }
		//
		// @Override
		// public TypeToken<List<TerminalChannel>> getTypeToken() {
		// return new TypeToken<List<TerminalChannel>>() {
		// };
		// }
		// });

		// mChooseChannel.setOnClickListener(this);

		// mTerminalNumber = (EditText) findViewById(R.id.terminal_num);
		// mShopName = (EditText) findViewById(R.id.shop_name);
		// mSubmitBtn = (Button) findViewById(R.id.terminal_submit);
		//
		// mTerminalNumber.addTextChangedListener(mTextWatcher);
		// mShopName.addTextChangedListener(mTextWatcher);
		// mSubmitBtn.setOnClickListener(this);
		getCode = (LinearLayout) findViewById(R.id.get_code);
		getCode.setOnClickListener(this);
		check=(LinearLayout) findViewById(R.id.tv_check);
		check.setOnClickListener(this);
		tv_code = (TextView) findViewById(R.id.code);
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(this);
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

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:

				break;

			}

		};

	};

	private final TextWatcher mTextWatcher = new TextWatcherAdapter() {

		public void afterTextChanged(final Editable gitDirEditText) {
			updateUIWithValidation();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		updateUIWithValidation();
	}

	private void updateUIWithValidation() {
		final boolean enabled = mChannelId > 0 && mTerminalNumber.length() > 0
				&& mShopName.length() > 0;
		// mSubmitBtn.setEnabled(enabled);
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == RESULT_OK) {
	// mChannelId = data.getIntExtra(CHANNEL_ID, 0);
	// mChannelName = data.getStringExtra(CHANNEL_NAME);
	// mPayChannel.setText(mChannelName);
	// }
	// }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			// if (check()) {
			// API.addTerminal(TerminalApplyServiceActivity.this,
			// MyApplication.getInstance().NewUser.getId(), mChannelId,
			// mTerminalNumber.getText().toString(), mShopName.getText()
			// .toString(), new HttpCallback(
			// TerminalApplyServiceActivity.this) {
			// @Override
			// public void onSuccess(Object data) {
			// setResult(RESULT_OK);
			// finish();
			// }
			//
			// @Override
			// public TypeToken getTypeToken() {
			// return null;
			// }
			// });
			// }
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
				chenckcode = true;
			} else {
				img_check_y.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
				chenckcode = false;
			}

		}
	}

	private void getCode() {

		handler.postDelayed(runnable, 1000);
		mCode = StringUtil.replaceBlank(setCode.getText().toString());
		if (mCode.equals("") && mCode == null) {
			Toast.makeText(getApplicationContext(), "请输入邮箱或者手机号", 1000).show();
		} else {
			ggg(mCode);
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

		Config.AddAdres1(TerminalApplyCreateActivity.this, phonenumber,

		new HttpCallback(TerminalApplyCreateActivity.this) {

			@Override
			public void onSuccess(Object data) {
				// TODO Auto-generated method stub
				Toast.makeText(TerminalApplyCreateActivity.this, "验证码发送成功",
						1000).show();
				vcode = data.toString();
			}

			@Override
			public TypeToken getTypeToken() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
