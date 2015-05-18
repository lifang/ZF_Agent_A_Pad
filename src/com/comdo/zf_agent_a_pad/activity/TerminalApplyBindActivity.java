package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.view.ViewGroup;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageTerminal;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.UserInfo;
import com.comdo.zf_agent_a_pad.entity.User_Info;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_USER;

public class TerminalApplyBindActivity extends Activity implements
		View.OnClickListener {

	private Spinner spinner;
	private LinearLayout createUser, getspinnerdata;
	private int page = 0;
	private final int rows = 10;
	private EditText mTerminalNumber;
	private Button mSubmitBtn;
	private TextView userselected;
	private User_Info userInfo = new User_Info();

	public static final int REQUEST_CREATE_USER = 1000;
	public static final int REQUEST_USER = 1009;
	private Button close;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	private List<String> listString = new ArrayList<String>();
	private BaseAdapter maAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_bind);
		mInflater = LayoutInflater.from(this);
		items = new ArrayList<Map<String, Object>>();

		getspinnerdata = (LinearLayout) findViewById(R.id.getspinnerdata);
		getspinnerdata.setOnClickListener(this);

		userselected = (TextView) findViewById(R.id.user_selected);

		createUser = (LinearLayout) findViewById(R.id.create_user);
		createUser.setOnClickListener(this);

		mTerminalNumber = (EditText) findViewById(R.id.terminal_num);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);

		mTerminalNumber.addTextChangedListener(mTextWatcher);
		mSubmitBtn.setOnClickListener(this);

		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(this);
	}

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
		final boolean enabled = userInfo.getId() > 0
				&& mTerminalNumber.length() > 0;
		mSubmitBtn.setEnabled(enabled);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			Config.bindingTerminal(this, mTerminalNumber.getText().toString(),
					userInfo.getId(), MyApplication.NewUser.getAgentId(),
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
			startActivityForResult(new Intent(TerminalApplyBindActivity.this,
					TerminalApplyCreateActivity.class), REQUEST_CREATE_USER);
			break;

		case R.id.getspinnerdata:
			startActivityForResult(new Intent(TerminalApplyBindActivity.this,
					TerminalSelectUserActivity.class), REQUEST_USER);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case REQUEST_CREATE_USER: {

			userInfo = (User_Info) data.getSerializableExtra(SELECTED_USER);
			userselected.setText(userInfo.getUsername());
			break;
		}

		case REQUEST_USER: {
			userInfo = (User_Info) data.getSerializableExtra(SELECTED_USER);
			userselected.setText(userInfo.getUsername());
			break;
		}

		default:
			break;
		}
	}
}
