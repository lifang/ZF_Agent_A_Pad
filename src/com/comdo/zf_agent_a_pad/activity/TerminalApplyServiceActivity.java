package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.REQUEST_ADD;

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

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplyServiceActivity extends Activity implements
		View.OnClickListener {

	private View mChooseChannel;
	private TextView mPayChannel;
	private int mChannelId;
	private String mChannelName;
	private LinearLayout select_client;
	private EditText mTerminalNumber;
	private EditText mShopName;
	private Button mSubmitBtn;

	private String terminalNum, shopName;

	private ArrayAdapter<String> adapter;

	private Button close;

	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	final List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_apply_service);


		items = new ArrayList<Map<String, Object>>();

//		API.getChannelList(this, new HttpCallback<List<TerminalChannel>>(this) {
//
//			@Override
//			public void onSuccess(List<TerminalChannel> data) {
//				for (TerminalChannel channel : data) {
//					Map<String, Object> item = new HashMap<String, Object>();
//					item.put("id", channel.getId());
//					item.put("name", channel.getName());
//					list.add(channel.getName());
//					items.add(item);
//				}
//				myHandler.sendEmptyMessage(1);
//
//			}
//
//			@Override
//			public TypeToken<List<TerminalChannel>> getTypeToken() {
//				return new TypeToken<List<TerminalChannel>>() {
//				};
//			}
//		});



		// mChooseChannel.setOnClickListener(this);

//		mTerminalNumber = (EditText) findViewById(R.id.terminal_num);
//		mShopName = (EditText) findViewById(R.id.shop_name);
//		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);
//
//		mTerminalNumber.addTextChangedListener(mTextWatcher);
//		mShopName.addTextChangedListener(mTextWatcher);
//		mSubmitBtn.setOnClickListener(this);
		select_client=(LinearLayout) findViewById(R.id.select_client);
		select_client.setOnClickListener(this);
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(this);
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
//		mSubmitBtn.setEnabled(enabled);
	}



	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			// if (check()) {
//			API.addTerminal(TerminalApplyServiceActivity.this, MyApplication.getInstance().NewUser.getId(), mChannelId,
//					mTerminalNumber.getText().toString(), mShopName.getText()
//							.toString(), new HttpCallback(
//							TerminalApplyServiceActivity.this) {
//						@Override
//						public void onSuccess(Object data) {
//							setResult(RESULT_OK);
//							finish();
//						}
//
//						@Override
//						public TypeToken getTypeToken() {
//							return null;
//						}
//					});
			// }
			break;
		case R.id.close:
			this.finish();
			break;
		case R.id.select_client:
			startActivityForResult(new Intent(TerminalApplyServiceActivity.this,
					TerminalApplySelectActivity.class), REQUEST_ADD);
			break;
		}
	}

}
