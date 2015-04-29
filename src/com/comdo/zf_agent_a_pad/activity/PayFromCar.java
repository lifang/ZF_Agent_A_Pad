package com.comdo.zf_agent_a_pad.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.alipay.PayActivity;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.PayOrderEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.DialogUtil;
import com.comdo.zf_agent_a_pad.util.DialogUtil.CallBackChange;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class PayFromCar extends PayActivity implements OnClickListener{
	private TextView tv_pay;
	private LinearLayout titleback_linear_back, ll_request;
	private String orderId = "";
	private String outTradeNo;
	private String subject;
	private String body;
	private String price;
	
	private int type;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				tv_pay.setText("￥  "+price);
				break;
			}
		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);

		orderId = getIntent().getExtras().getString("orderId", "");
		type = getIntent().getIntExtra("type", 0);
		
		new TitleMenuUtil(PayFromCar.this, "选择支付方式").show();

		if (orderId.equals("")) {
			Toast.makeText(this, "没有传订单id", Toast.LENGTH_SHORT).show();
		}

		tv_pay=(TextView) findViewById(R.id.tv_pay);

		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
		ll_request = (LinearLayout) findViewById(R.id.ll_request);
		ll_request.setOnClickListener(this);
		
		getData();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
				dialogIntent();
			break;
		case R.id.ll_request:
				pay(outTradeNo, subject, body, price);
			break;
		default:
			break;
		}
	}

	private void dialogIntent() {
		Dialog dialog = new DialogUtil(PayFromCar.this,
				"是否放弃付款？").getCheck(new CallBackChange() {

					@Override
					public void change() {
						Intent intent = new Intent(PayFromCar.this,OrderDetail.class);
						intent.putExtra("status",1);
						intent.putExtra("id", orderId);
						intent.putExtra("type", type);
						startActivity(intent);
						finish();
					}
				});
		dialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
				dialogIntent();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void getData() {

		Config.shopPayOrder(this, Integer.parseInt(orderId), 
				new HttpCallback<PayOrderEntity>(this) {

			@Override
			public void onSuccess(PayOrderEntity data) {
				if (data.getGood().size()>0) {
					subject = data.getGood().get(0).getTitle();
					body = subject;
				}
				outTradeNo = data.getOrder_number();
				price = data.getTotal_price();
				price = String.format("%.2f", Integer.parseInt(price)/100f);
				handler.sendEmptyMessage(0);
			}
			@Override
			public TypeToken<PayOrderEntity> getTypeToken() {
				return new TypeToken<PayOrderEntity>() {
				};
			}
		});
	}

	
	@Override
	public void success() {
		Intent intent = new Intent(PayFromCar.this,OrderDetail.class);
		intent.putExtra("status",2);
		intent.putExtra("id", orderId);
		intent.putExtra("type", type);
		startActivity(intent);
		finish();
	}
	@Override
	public void handling() {
		Intent intent = new Intent(PayFromCar.this,OrderDetail.class);
		intent.putExtra("status",1);
		intent.putExtra("id", orderId);
		intent.putExtra("type", type);
		startActivity(intent);
		finish();
	}
	@Override
	public void fail() {
		Intent intent = new Intent(PayFromCar.this,OrderDetail.class);
		intent.putExtra("status",1);
		intent.putExtra("id", orderId);
		intent.putExtra("type", type);
		startActivity(intent);
		finish();
	}
}

