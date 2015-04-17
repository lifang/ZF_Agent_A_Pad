package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.TextWatcherAdapter;
import com.comdo.zf_agent_a_pad.entity.SelectPOS;
import com.comdo.zf_agent_a_pad.trade.entity.TerminalManagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

/**

 */
public class TerminalApplySelectActivity extends Activity implements
		View.OnClickListener {

	private View mChooseChannel;
	private TextView mPayChannel;
	private int mChannelId;
	private String mChannelName;

	private EditText mTerminalNumber;
	private EditText mShopName;
	private Button mSubmitBtn;

	private String terminalNum, shopName;

	private ArrayAdapter<String> adapter;

	private Button close;
	private LinearLayout titleback_linear_back, posselect;
	private TextView titleback_text_title;
	private ImageView titleback_image_back, searchView;
	private Button service, bind;
	private LayoutInflater mInflater;
	private XListView mTerminalList;
	private List<TerminalManagerEntity> mTerminalItems;
	private TerminalListAdapter mAdapter;
	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	final List<String> list = new ArrayList<String>();
	private List<SelectPOS> selectPOS;

	public static final int REQUEST_CHOOSE_POS = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terminal_select);
		// new TitleMenuUtil(this,
		// getString(R.string.title_trade_client)).show();

		items = new ArrayList<Map<String, Object>>();

		posselect = (LinearLayout) findViewById(R.id.posselect);
		posselect.setOnClickListener(this);
		// close = (Button) findViewById(R.id.close);
		// close.setOnClickListener(this);
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

	class TerminalListAdapter extends BaseAdapter {
		TerminalListAdapter() {
		}

		@Override
		public int getCount() {
			return mTerminalItems.size();
		}

		@Override
		public TerminalManagerEntity getItem(int i) {
			return mTerminalItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.terminal_list, null);
				holder = new ViewHolder();
				holder.tv_terminal_id = (TextView) convertView
						.findViewById(R.id.tv_terminal_id);
				holder.tv_postype = (TextView) convertView
						.findViewById(R.id.tv_postype);
				holder.tv_paychannel = (TextView) convertView
						.findViewById(R.id.tv_paychannel);
				holder.tv_openstate = (TextView) convertView
						.findViewById(R.id.tv_openstate);
				holder.llButtonContainer = (LinearLayout) convertView
						.findViewById(R.id.terminal_button_container);
				holder.llButtons = (LinearLayout) convertView
						.findViewById(R.id.terminal_buttons);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalManagerEntity item = getItem(i);
			holder.tv_terminal_id.setText(item.getPosPortID());
			if (item.getPos() != null || item.getPosname() != null) {
				if (item.getPos() == null) {
					holder.tv_postype.setText(item.getPosname());
				} else if (item.getPosname() == null) {
					holder.tv_postype.setText(item.getPos());
				} else {
					holder.tv_postype
							.setText(item.getPos() + item.getPosname());
				}
			} else {
				holder.tv_postype.setText("-");
			}
			holder.tv_paychannel.setText(item.getPayChannel());
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			holder.tv_openstate.setText(status[item.getOpenState()]);

			// add buttons according to status
			holder.llButtons.removeAllViews();

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tv_terminal_id;
		public TextView tv_postype;
		public TextView tv_paychannel;
		public TextView tv_openstate;
		public LinearLayout llButtonContainer;
		public LinearLayout llButtons;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.terminal_submit:

			break;
		case R.id.posselect:

			// RequestParams params = new RequestParams();
			// params.put("customerId", 1);
			// params.setUseJsonStreamer(true);
			// MyApplication
			// .getInstance()
			// .getClient()
			// .post(Config.SELECTPOS, params,
			// new AsyncHttpResponseHandler() {
			//
			// @Override
			// public void onSuccess(int statusCode,
			// Header[] headers, byte[] responseBody) {
			// String responseMsg = new String(
			// responseBody).toString();
			//
			// Log.e("LJP", responseMsg);
			// Gson gson = new Gson();
			// JSONObject jsonobject = null;
			// int code = 0;
			//
			// try {
			// jsonobject = new JSONObject(responseMsg);
			//
			// code = jsonobject.getInt("code");
			//
			// if (code == -2) {
			// Toast.makeText(
			// getApplicationContext(),
			// jsonobject
			// .getString("message"),
			// Toast.LENGTH_SHORT).show();
			// } else if (code == 1) {
			//
			// String res = jsonobject
			// .getString("result");
			// System.out.println("`res``" + res);
			// selectPOS = gson
			// .fromJson(
			// res,
			// new TypeToken<List<SelectPOS>>() {
			// }.getType());
			// Intent intent = new Intent(
			// TerminalApplySelectActivity.this,
			// ApplyChooseActivity.class);
			// intent.putExtra(CHOOSE_TITLE, getResources()
			// .getString(R.string.title_pos_select));
			// intent.putExtra(SELECTED_ID, 0);
			// intent.putExtra(CHOOSE_ITEMS, selectPOS);
			// startActivityForResult(intent, 10000);
			//
			// } else {
			// Toast.makeText(
			// getApplicationContext(),
			// jsonobject
			// .getString("message"),
			// Toast.LENGTH_SHORT).show();
			// }
			//
			// } catch (JSONException e) {
			// e.printStackTrace();
			// }
			//
			// }
			//
			// @Override
			// public void onFailure(int statusCode,
			// Header[] headers, byte[] responseBody,
			// Throwable error) {
			// error.printStackTrace();
			//
			// }
			//
			// });

			Config.selectPOS(TerminalApplySelectActivity.this, 1,
					new HttpCallback<List<SelectPOS>>(
							TerminalApplySelectActivity.this) {
						@Override
						public void onSuccess(List<SelectPOS> data) {

							final ArrayList<SelectPOS> list = (ArrayList<SelectPOS>) data;
							Intent intent = new Intent(
									TerminalApplySelectActivity.this,
									TerminalSelectPOSActivity.class);
							intent.putExtra(CHOOSE_TITLE, getResources()
									.getString(R.string.title_pos_select));
							intent.putExtra(SELECTED_ID, 0);
							intent.putExtra(CHOOSE_ITEMS, list);
							startActivityForResult(intent, REQUEST_CHOOSE_POS);
						}

						@Override
						public void onFailure(String message) {
							super.onFailure(message);
						}

						@Override
						public TypeToken<List<SelectPOS>> getTypeToken() {
							return new TypeToken<List<SelectPOS>>() {
							};
						}
					});
			break;
		case R.id.close:
			this.finish();
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
		case REQUEST_CHOOSE_POS: {
			

//			mMerchantId = data.getIntExtra(SELECTED_ID, 0);
//			mMerchantId = data.getIntExtra(SELECTED_ID, 0);
			break;

		}
		}
	}
}
