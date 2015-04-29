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
import com.comdo.zf_agent_a_pad.entity.OrderPayOrderEntity;
import com.comdo.zf_agent_a_pad.entity.ShopPayOrderEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.DialogUtil;
import com.comdo.zf_agent_a_pad.util.DialogUtil.CallBackChange;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class PayFromCar extends PayActivity implements OnClickListener{
	private TextView tv_pay;
	private LinearLayout titleback_linear_back, ll_request;
	private int orderId;
	private String outTradeNo;
	private String subject;
	private String body;
	private String price;
	
	private int type;
	
	private String pay_status;//定金支付状态
	private String price_dingjin;//定金总额
	private String order_totalPrice;//总共的钱
	private String shengyu_price;//剩余要付的钱
	
	private String priceEdit = "";//输入要付的金额
	
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

		orderId = getIntent().getIntExtra("orderId", -1);
		type = getIntent().getIntExtra("type", 5);
		if (type == 5) {
			priceEdit = getIntent().getStringExtra("pay");
		}else {
			priceEdit = "";
		}
		
		new TitleMenuUtil(PayFromCar.this, "选择支付方式").show();

		tv_pay=(TextView) findViewById(R.id.tv_pay);

		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
		ll_request = (LinearLayout) findViewById(R.id.ll_request);
		ll_request.setOnClickListener(this);
		
		if (type == 5) {
			getOrderPayOrder();//批购
		}else {
			getShopPayOrder();//代购
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
				dialogIntent();
			break;
		case R.id.ll_request:
			if (pay_status.equals("1")) {
				payShop(outTradeNo, subject, body, price);
			}else {
				if (!StringUtil.isNull(shengyu_price) && !StringUtil.isNull(priceEdit)) {
					if (Integer.valueOf(shengyu_price) > Integer.valueOf(shengyu_price)) {
						Toast.makeText(this, "付款金额大于剩余金额", Toast.LENGTH_SHORT).show();
					}else {
						payOrder(outTradeNo, subject, body, price);
					}
				}
			}
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
	
	private void getShopPayOrder() {

		Config.shopPayOrder(this, orderId, 
				new HttpCallback<ShopPayOrderEntity>(this) {
 
			@Override
			public void onSuccess(ShopPayOrderEntity data) {
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
			public TypeToken<ShopPayOrderEntity> getTypeToken() {
				return new TypeToken<ShopPayOrderEntity>() {
				};
			}
		});
	}
	private void getOrderPayOrder() {

		Config.orderPayOrder(this, orderId, 
				new HttpCallback<OrderPayOrderEntity>(this) {
 
			@Override
			public void onSuccess(OrderPayOrderEntity data) {
				pay_status = data.getPay_status();
				price_dingjin = data.getPrice_dingjin();
				order_totalPrice = data.getOrder_totalPrice();
				shengyu_price = data.getShengyu_price();
				
				if (!StringUtil.isNull(data.getBody())) {
					subject = data.getBody();
				}else {
					subject = "";
				}
				body = subject;
				outTradeNo = data.getOrder_number();
				System.out.println("pay_status:::orderId"+pay_status+":::"+orderId);
				if (pay_status.equals("1")) {
					price = price_dingjin;
					if (!StringUtil.isNull(price)) {
						price = String.format("%.2f", Integer.parseInt(price_dingjin)/100f);
					}
				}else {
					price = priceEdit;
					if (!StringUtil.isNull(price)) {
						price = String.format("%.2f", Float.parseFloat(price));
					}else {
						price = "0.00";
					}
				}
				handler.sendEmptyMessage(0);
			}
			@Override
			public TypeToken<OrderPayOrderEntity> getTypeToken() {
				return new TypeToken<OrderPayOrderEntity>() {
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

