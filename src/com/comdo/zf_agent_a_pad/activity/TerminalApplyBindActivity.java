package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageTerminal;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.UserInfo;
import com.comdo.zf_agent_a_pad.util.Config;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplyBindActivity extends Activity implements
		View.OnClickListener {

	private int mChannelId;
	private Spinner spinner;
	private LinearLayout createUser;
	private int page = 0;
	private final int rows = 10;
	private EditText mTerminalNumber;
	private Button mSubmitBtn;

	public static final int REQUEST_CREATE_USER = 1000;
	private ArrayAdapter<String> adapter;

	private Button close;

	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	final List<String> listString = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_bind);

		items = new ArrayList<Map<String, Object>>();

		Config.getMerchants(this, 1, page + 1, rows,
				new HttpCallback<PageTerminal<UserInfo>>(this) {
					@Override
					public void onSuccess(PageTerminal<UserInfo> data) {

						for (UserInfo userInfo : data.getList()) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("id", userInfo.getId());
							item.put("name", userInfo.getName());
							listString.add(userInfo.getName());
							items.add(item);
						}

						myHandler.sendEmptyMessage(1);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken<PageTerminal<UserInfo>> getTypeToken() {
						return new TypeToken<PageTerminal<UserInfo>>() {
						};
					}
				});

		spinner = (Spinner) findViewById(R.id.spinner);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listString);
		// adapter.setDropDownViewResource(R.layout.drop_down_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				mChannelId = (Integer) items.get(arg2).get("id");

			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		createUser = (LinearLayout) findViewById(R.id.create_user);
		createUser.setOnClickListener(this);

		mTerminalNumber = (EditText) findViewById(R.id.terminal_num);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);

		mTerminalNumber.addTextChangedListener(mTextWatcher);
		mSubmitBtn.setOnClickListener(this);

		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(this);
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:

				adapter.notifyDataSetChanged();

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
		final boolean enabled = mChannelId > 0 && mTerminalNumber.length() > 0;
		mSubmitBtn.setEnabled(enabled);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			Config.bindingTerminal(this, mTerminalNumber.getText().toString(),
					mChannelId,
					new HttpCallback(TerminalApplyBindActivity.this) {
						@Override
						public void onSuccess(Object data) {

							CommonUtil.toastShort(
									TerminalApplyBindActivity.this,
									getResources().getString(
											R.string.terminal_add_success));
							finish();
						}

						@Override
						public TypeToken getTypeToken() {
							return null;
						}
					});

			break;
		case R.id.close:
			this.finish();
			break;
		case R.id.create_user:
			// TODO:
			startActivityForResult(new Intent(TerminalApplyBindActivity.this,
					TerminalApplyCreateActivity.class), REQUEST_CREATE_USER);
			break;
		}
	}
}
