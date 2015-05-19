package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.RecordAdapter;
import com.comdo.zf_agent_a_pad.adapter.ResourceInfoAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AfterSaleDetailCancelEntity;
import com.comdo.zf_agent_a_pad.entity.AfterSaleDetailOrderEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class AfterSaleDetailActivity extends Activity{
	//“终端号”、终端号字符串
	private TextView terminal_infoTextView,terminal_noTextView;
	//申请时间、申请单号、申请状态
	private TextView apply_time,apply_num,apply_state;
	private TextView update_operation;
	//终端信息
	private LinearLayout terminal_info_Layout;
	//终  端  号、POS品牌、POS型号、支付通道、商  户  名、商户电话
	private TextView terminal_no,terminal_brand,terminal_model,terminal_pay,
	terminal_name,terminal_phone;
	//注销申请资料
	private LinearLayout resource_info_layout;
	private TextView cancel_data;
	private ScrollViewWithListView resource_infoListView;
	//收货地址
	private LinearLayout addr_layout;
	private TextView addrTextView;
	//售后原因
	private LinearLayout reason_layout;
	private TextView sale_why;
	//追踪记录
	private ScrollViewWithListView his_lv;
	//提交物流信息的dialog
	private Dialog dialog;
	private Button dialog_button_cancel, dialog_button_ok;
	private EditText dialog_text;
	private EditText dialog_textNo;
	private String computer_name,track_number;

	private RecordAdapter reAdapter;
	private ResourceInfoAdapter reInfoAdapter;

	private AfterSaleDetailOrderEntity detailOrderEntity = new AfterSaleDetailOrderEntity();
	private AfterSaleDetailCancelEntity detailCancelEntity = new AfterSaleDetailCancelEntity();
	private int mRecordType = 0;
	private int id;
	private String apply_numString;
	private String isCancelUpdate = "";
	private LinearLayout titleback_linear_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_after_sale_detail);

		mRecordType = getIntent().getExtras().getInt("mRecordType");
		apply_numString = getIntent().getExtras().getString("apply_num");
		id = getIntent().getExtras().getInt("id");
		if (mRecordType == 0) {
			new TitleMenuUtil(this, "售后单详情").show();
		}else if (mRecordType == 1) {
			new TitleMenuUtil(this, "注销详情").show();
		}else if (mRecordType == 2) {
			new TitleMenuUtil(this, "更新资料详情").show();
		}
		initView();
		loadData();
	}

	private void initView() {
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);

		terminal_infoTextView = (TextView) findViewById(R.id.terminal_infoTextView);
		terminal_noTextView = (TextView) findViewById(R.id.terminal_noTextView);
		apply_time = (TextView) findViewById(R.id.apply_time);
		apply_num = (TextView) findViewById(R.id.apply_num);
		apply_state = (TextView) findViewById(R.id.apply_state);
		update_operation = (TextView) findViewById(R.id.update_operation);

		terminal_info_Layout = (LinearLayout) findViewById(R.id.terminal_info_Layout);
		terminal_no = (TextView) findViewById(R.id.terminal_no);
		terminal_brand = (TextView) findViewById(R.id.terminal_brand);
		terminal_model = (TextView) findViewById(R.id.terminal_model);
		terminal_pay = (TextView) findViewById(R.id.terminal_pay);
		terminal_name = (TextView) findViewById(R.id.terminal_name);
		terminal_phone = (TextView) findViewById(R.id.terminal_phone);

		resource_info_layout = (LinearLayout) findViewById(R.id.resource_info_layout);
		cancel_data = (TextView) findViewById(R.id.cancel_data);
		resource_infoListView = (ScrollViewWithListView) findViewById(R.id.resource_infoListView);

		addr_layout = (LinearLayout) findViewById(R.id.addr_layout);
		addrTextView = (TextView) findViewById(R.id.addrTextView);

		reason_layout = (LinearLayout) findViewById(R.id.reason_layout);
		sale_why = (TextView) findViewById(R.id.sale_why);

		his_lv = (ScrollViewWithListView) findViewById(R.id.his_lv);

		update_operation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (update_operation.getText().toString().equals("取消申请")) {

					final AlertDialog.Builder builder = new AlertDialog.Builder(
							AfterSaleDetailActivity.this);
					final AlertDialog dialog = builder.create();
					builder.setTitle("提示");
					builder.setMessage("确定要取消吗？");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									cancelApply();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									dialog.dismiss();
								}

							});

					builder.create().show();
					
				}else if (update_operation.getText().toString().equals("重新提交注销")) {
					cancelAgain();
				}else if (update_operation.getText().toString().equals("提交物流信息")) {
					setDialog();
				}
			}
		});
		titleback_linear_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				if (isCancelUpdate.equals("cancel")) {
					intent1.putExtra("isChange", "cancel");
				}else if (isCancelUpdate.equals("update")){
					intent1.putExtra("isChange", "update");
				}else {
					intent1.putExtra("isChange", "");
				}
				setResult(RESULT_OK, intent1);
				finish();
			}
		});;
		resource_infoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String upload_path = detailCancelEntity.getResource_info().get(position).getUpload_path();
				if (!StringUtil.isNull(upload_path)) {
					Uri uri = Uri.parse(upload_path);  
					Intent it = new Intent(Intent.ACTION_VIEW, uri);  
					startActivity(it);

					//					Intent intent = new Intent(AfterSaleDetailActivity.this,WebViewActivity.class);
					//					intent.putExtra("IMAGE_PATH", 
					//							detailCancelEntity.getResource_info().get(position).getUpload_path());
					//					startActivity(intent);
				}
			}
		});
	}
	//售后单
	protected void initOrderData() {
		if (detailOrderEntity.getStatus().equals("1")) {
			apply_state.setText("待处理");
			update_operation.setText("取消申请");
			update_operation.setTextColor(getResources().getColor(R.color.bgtitle));
			update_operation.setBackgroundResource(R.drawable.bg_right_white);
			update_operation.setVisibility(View.VISIBLE);
		}else if (detailOrderEntity.getStatus().equals("2")) {
			apply_state.setText("处理中");

			update_operation.setVisibility(View.VISIBLE);
			update_operation.setText("提交物流信息");
			update_operation.setTextColor(getResources().getColor(R.color.white));
			update_operation.setBackgroundResource(R.drawable.bg_right_bgtitle);

		}else if (detailOrderEntity.getStatus().equals("3")) {
			apply_state.setText("处理完成");
			update_operation.setVisibility(View.GONE);
		}else if (detailOrderEntity.getStatus().equals("4")) {
			apply_state.setText("已取消");
			update_operation.setVisibility(View.GONE);
		}

		apply_time.setText("申请时间："+detailOrderEntity.getApply_time());
		apply_num.setText("申请单号："+apply_numString);

		terminal_infoTextView.setText("终端号");
		terminal_info_Layout.setVisibility(View.GONE);
		terminal_noTextView.setVisibility(View.VISIBLE);
		terminal_noTextView.setText(detailOrderEntity.getTerminals_list().replace(",","\r\n"));

		resource_info_layout.setVisibility(View.GONE);
		addr_layout.setVisibility(View.VISIBLE);
		reason_layout.setVisibility(View.VISIBLE);
		addrTextView.setText(detailOrderEntity.getAddress());
		sale_why.setText(detailOrderEntity.getReason());

		reAdapter=new RecordAdapter(this, detailOrderEntity.getComments().getList());
		his_lv.setAdapter(reAdapter);
	}
	//注销、更新资料
	protected void initCancelData() {
		if (detailCancelEntity.getStatus().equals("1")) {
			apply_state.setText("待处理");
			update_operation.setText("取消申请");
			update_operation.setTextColor(getResources().getColor(R.color.bgtitle));
			update_operation.setBackgroundResource(R.drawable.bg_right_white);
			update_operation.setVisibility(View.VISIBLE);
		}else if (detailCancelEntity.getStatus().equals("2")) {
			apply_state.setText("处理中");
			update_operation.setVisibility(View.GONE);
		}else if (detailCancelEntity.getStatus().equals("4")) {
			apply_state.setText("处理完成");
			update_operation.setVisibility(View.GONE);
		}else if (detailCancelEntity.getStatus().equals("5")) {
			apply_state.setText("已取消");

			if (mRecordType == 1) {
				update_operation.setVisibility(View.VISIBLE);
				update_operation.setText("重新提交注销");
				update_operation.setTextColor(getResources().getColor(R.color.white));
				update_operation.setBackgroundResource(R.drawable.bg_right_bgtitle);
			}else {
				update_operation.setVisibility(View.GONE);
			}

		}
		apply_time.setText("申请时间："+detailCancelEntity.getApply_time());
		apply_num.setText("申请单号："+apply_numString);

		terminal_infoTextView.setText("终端信息");
		terminal_info_Layout.setVisibility(View.VISIBLE);
		terminal_noTextView.setVisibility(View.GONE);
		terminal_no.setText(detailCancelEntity.getTerminal_num());
		terminal_brand.setText(detailCancelEntity.getBrand_name());
		terminal_model.setText(detailCancelEntity.getBrand_number());

		terminal_pay.setText(detailCancelEntity.getZhifu_pingtai());
		terminal_name.setText(detailCancelEntity.getMerchant_name());
		terminal_phone.setText(detailCancelEntity.getMerchant_phone());

		resource_info_layout.setVisibility(View.VISIBLE);
		addr_layout.setVisibility(View.GONE);
		reason_layout.setVisibility(View.GONE);

		reInfoAdapter = new ResourceInfoAdapter(this, detailCancelEntity.getResource_info());
		resource_infoListView.setAdapter(reInfoAdapter);

		reAdapter=new RecordAdapter(this, detailCancelEntity.getComments().getList());
		his_lv.setAdapter(reAdapter);
	}
	//增加物流信息
	private void setDialog() {

		dialog = new Dialog(this, R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_edit_aftersale);
		dialog.setCancelable(false);
		dialog.show();

		dialog_button_ok = (Button) dialog.findViewById(R.id.dialog_button_ok);
		dialog_button_cancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);
		dialog_textNo = (EditText) dialog.findViewById(R.id.dialog_textNo);
		dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);

		dialog_button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				computer_name = "";
				track_number = "";
				dialog.dismiss();
				MyApplication.hideSoftKeyboard(AfterSaleDetailActivity.this);
			}
		});
		dialog_button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!dialog_text.getText().toString().equals("")) {
					if (!dialog_textNo.getText().toString().equals("")) {
						computer_name = dialog_text.getText().toString();
						track_number = dialog_textNo.getText().toString();
						MyApplication.hideSoftKeyboard(AfterSaleDetailActivity.this);
						agentsAddMark();
					}else {
						Toast.makeText(AfterSaleDetailActivity.this, "物流单号不能为空", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(AfterSaleDetailActivity.this, "物流公司名称不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void loadData() {
		if (mRecordType == 0) {
			//售后单
			Config.getAfterSaleDetail(this, mRecordType,id,
					new HttpCallback<AfterSaleDetailOrderEntity>(this) {
				@Override
				public void onSuccess(AfterSaleDetailOrderEntity data) {
					detailOrderEntity = data;
					initOrderData();
				}

				@Override
				public TypeToken<AfterSaleDetailOrderEntity> getTypeToken() {
					return new TypeToken<AfterSaleDetailOrderEntity>() {
					};
				}
			});
		}else {
			//注销、更新资料
			Config.getAfterSaleDetail(this, mRecordType,id,
					new HttpCallback<AfterSaleDetailCancelEntity>(this) {
				@Override
				public void onSuccess(AfterSaleDetailCancelEntity data) {
					detailCancelEntity = data;
					initCancelData();
				}

				@Override
				public TypeToken<AfterSaleDetailCancelEntity> getTypeToken() {
					return new TypeToken<AfterSaleDetailCancelEntity>() {
					};
				}
			});
		}
	}

	//取消申请
	public void cancelApply() {
		Config.cancelApply(this, mRecordType,id,new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				isCancelUpdate = "cancel";
				if (mRecordType == 0) {
					apply_state.setText("已取消");
					update_operation.setVisibility(View.GONE);
				}else if (mRecordType == 1){
					apply_state.setText("已取消");
					update_operation.setVisibility(View.VISIBLE);
					update_operation.setText("重新提交注销");
					update_operation.setTextColor(getResources().getColor(R.color.white));
					update_operation.setBackgroundResource(R.drawable.bg_right_bgtitle);
				}else if (mRecordType == 2) {
					apply_state.setText("已取消");
					update_operation.setVisibility(View.GONE);
				}
				Toast.makeText(AfterSaleDetailActivity.this, "取消申请成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}

	//重新提交注销
	public void cancelAgain() {
		Config.resubmitCancel(this, id,new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				isCancelUpdate = "update";
				apply_state.setText("待处理");
				update_operation.setVisibility(View.VISIBLE);
				update_operation.setText("取消申请");
				update_operation.setTextColor(getResources().getColor(R.color.bgtitle));
				update_operation.setBackgroundResource(R.drawable.bg_right_white);
				Toast.makeText(AfterSaleDetailActivity.this, "重新提交注销成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}
	//增加物流信息
	protected void agentsAddMark() {
		//代理商对应用户ID,MyApplication.NewUser.getAgentUserId()
		Config.agentsAddMark(this,id,computer_name,
				track_number,MyApplication.NewUser.getAgentUserId(),
				new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {
				dialog.dismiss();
				Toast.makeText(AfterSaleDetailActivity.this, "提交物流信息成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			if (isCancelUpdate.equals("cancel")) {
				intent.putExtra("isChange", "cancel");
			}else if (isCancelUpdate.equals("update")){
				intent.putExtra("isChange", "update");
			}else {
				intent.putExtra("isChange", "");
			}
			setResult(RESULT_OK, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
