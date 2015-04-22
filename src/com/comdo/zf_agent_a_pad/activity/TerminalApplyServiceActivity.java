package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_ARRAY;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TOTAL;

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
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.TerminalAddressEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplyServiceActivity extends Activity implements
		View.OnClickListener {
	private TextView client_names;
	private int mTerminalNum=0;
	private String mTerminalArray="";
	private LinearLayout select_client;
	private EditText reason;
	private Button mSubmitBtn;

	private String mReceiver="", mPhoneNum="", mAddress="", mReason="";

	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	public static final int REQUEST_SELECT_CLIENT = 1000;
	private Button close;

	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	final List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_service);

		items = new ArrayList<Map<String, Object>>();

		Config.getAddressee(this, 80,
				new HttpCallback<List<TerminalAddressEntity>>(this) {
					@Override
					public void onSuccess(List<TerminalAddressEntity> data) {

						for (TerminalAddressEntity entity : data) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("receiver", entity.getReceiver());
							item.put("address", entity.getAddress());
							item.put("moblephone", entity.getMoblephone());
							list.add(entity.getAddress());
							items.add(item);
						}

						myHandler.sendEmptyMessage(1);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken<List<TerminalAddressEntity>> getTypeToken() {
						return new TypeToken<List<TerminalAddressEntity>>() {
						};
					}
				});

		spinner = (Spinner) findViewById(R.id.spinner);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(R.layout.drop_down_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				mAddress = String.valueOf(items.get(arg2).get("address"));
				mReceiver = String.valueOf(items.get(arg2).get("receiver"));
				mPhoneNum = String.valueOf(items.get(arg2).get("moblephone"));

			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		client_names = (TextView) findViewById(R.id.client_names);
		reason = (EditText) findViewById(R.id.reason);
		reason.addTextChangedListener(mTextWatcher);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);
		mSubmitBtn.setOnClickListener(this);
		select_client = (LinearLayout) findViewById(R.id.select_client);
		select_client.setOnClickListener(this);
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
			mReason = reason.getText().toString();
			updateUIWithValidation();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		updateUIWithValidation();
	}

	private void updateUIWithValidation() {
		final boolean enabled = mAddress.length() > 0 && mReceiver.length() > 0
				&& mPhoneNum.length() > 0 && mReason.length() > 0
				&& mTerminalArray.length() > 0 && mTerminalNum > 0;
		mSubmitBtn.setEnabled(enabled);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			Config.submitAgent(this, 1, mTerminalNum, mAddress, mReason,
					mTerminalArray, mReceiver, mPhoneNum,
					new HttpCallback(this) {
						@Override
						public void onSuccess(Object data) {

							CommonUtil.toastShort(
									TerminalApplyServiceActivity.this,
									getResources().getString(
											R.string.terminal_apply_success));
							startActivity(new Intent(TerminalApplyServiceActivity.this,AfterSaleActivity.class));
							TerminalApplyServiceActivity.this.finish();
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
		case R.id.select_client:
			Intent intent = new Intent(TerminalApplyServiceActivity.this,
					TerminalApplySelectActivity.class);
			intent.putExtra(TERMINAL_TOTAL, mTerminalNum);
			intent.putExtra(TERMINAL_ARRAY, mTerminalArray);
			startActivityForResult(intent, REQUEST_SELECT_CLIENT);
			break;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_SELECT_CLIENT: {
			mTerminalNum = data.getIntExtra(TERMINAL_TOTAL, 0);
			mTerminalArray = data.getStringExtra(TERMINAL_ARRAY);
			client_names.setText(mTerminalArray);
			break;
		}

		}
	}

}
