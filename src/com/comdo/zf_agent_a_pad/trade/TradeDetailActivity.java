package com.comdo.zf_agent_a_pad.trade;

import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.TRADE_RECORD_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TradeIntent.TRADE_TYPE;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.activity.BaseActivity;
import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.entity.TradeDetail;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class TradeDetailActivity extends BaseActivity {

	private LinearLayout mCommercialKeyContainer;
	private LinearLayout mCommercialValueContainer;

	private LinearLayout mBankKeyContainer;
	private LinearLayout mBankValueContainer;
	
	private String[] bankKeys;
	
	private TextView mTradeStatus;
	private TextView mTradeAmount;
	private TextView mTradePoundage;
	private TextView mTradeTime;
	private int typeId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_item);
		new TitleMenuUtil(this, "交易详情").show();

		typeId = getIntent().getIntExtra(TRADE_TYPE, 0);
		int recordId = getIntent().getIntExtra(TRADE_RECORD_ID, 0);
		
		initialViews();
		
		API.getTradeRecordDetail(this, recordId,
				MyApplication.NewUser.getAgentId(),
				MyApplication.NewUser.getIs_have_profit(),
				new HttpCallback<TradeDetail>(this) {
					@Override
					public void onSuccess(TradeDetail data) {

						DecimalFormat df = (DecimalFormat) NumberFormat
								.getInstance();
						df.applyPattern("0.00");

						Resources resources = getResources();
						String[] tradeStatuses = resources
								.getStringArray(R.array.trade_status);
						mTradeStatus.setText(tradeStatuses[data
								.getTradedStatus()]);
						mTradeAmount.setText(getString(R.string.notation_yuan)
								+ df.format(data.getAmount() * 1.0f / 100));
						mTradePoundage.setText(getString(R.string.notation_yuan)
								+ df.format(data.getPoundage() * 1.0f / 100));
						mTradeTime.setText(data.getTradedTimeStr());

						String[] commercialKeys = resources
								.getStringArray(R.array.trade_item_commercial);
//						String[] bankKeys = resources
//								.getStringArray(R.array.trade_item_bank);

						for (int i = 0; i < commercialKeys.length; i++) {
							TextView value = new TextView(
									TradeDetailActivity.this);
							value.setGravity(Gravity.LEFT);
							value.setPadding(0, 5, 0, 5);
							value.setTextColor(getResources().getColor(
									R.color.text6c6c6c6));
							value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
							value.setText(i == 0 ? data.getMerchant_number()
									    : i == 1 ? data.getAgentName() + ""
									    : i == 2 ? data.getMerchant_name()
									     : "");
							mCommercialValueContainer.addView(value);
						}
						if (typeId == 2 || typeId == 3){ // 转账 。还款 
							if (MyApplication.NewUser.getIs_have_profit() == 1) {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_transfer);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceNum(data.getPayFromAccount())//付款账号
											: i == 2 ? StringUtil.replaceNum(data.getPayIntoAccount())//转入账号||收款账号？
											: i == 3 ? data.getPaychannel()//支付通道
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 5 ? data.getTradedTimeStr()//交易时间
											: i == 6 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 7 ? data.getBatchNumber()//交易批次号
											: i == 8 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}else {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_transfer2);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceNum(data.getPayFromAccount())//付款账号
											: i == 2 ? StringUtil.replaceNum(data.getPayIntoAccount())//转入账号||收款账号？
											: i == 3 ? data.getPaychannel()//支付通道
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getGet()*1.0f/100)//产出分润
											: i == 5 ? getString(R.string.notation_yuan)+df.format(data.getPay()*1.0f/100)//支付分润
											: i == 6 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 7 ? data.getTradedTimeStr()//交易时间
											: i == 8 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 9 ? data.getBatchNumber()//交易批次号
											: i == 10 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}

						}else if (typeId == 1) {//消费
							if (MyApplication.NewUser.getIs_have_profit() == 1) {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_consume);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? getString(R.string.notation_yuan) + df.format(data.getPoundage()*1.0f/100)//手续费
											: i == 2 ? data.getPaychannel()//支付通道
											: i == 3 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 4 ? data.getTradedTimeStr()//交易时间
											: i == 5 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 6 ? data.getBatchNumber()//交易批次号
											: i == 7 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}else {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_consume2);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? getString(R.string.notation_yuan) + df.format(data.getPoundage()*1.0f/100)//手续费
											: i == 2 ? data.getPaychannel()//支付通道
											: i == 3 ? getString(R.string.notation_yuan)+df.format(data.getGet()*1.0f/100)//产出分润
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getPay()*1.0f/100)//支付分润
											: i == 5 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 6 ? data.getTradedTimeStr()//交易时间
											: i == 7 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 8 ? data.getBatchNumber()//交易批次号
											: i == 9 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}
						
						}else if (typeId == 5) {//生活充值
							if (MyApplication.NewUser.getIs_have_profit() == 1) {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_life_pay);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceName(data.getAccount_name())//账户名
											: i == 2 ? StringUtil.replaceNum(data.getAccount_number())//账户号码
											: i == 3 ? data.getPaychannel()//支付通道
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 5 ? data.getTradedTimeStr()//交易时间
											: i == 6 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 7 ? data.getBatchNumber()//交易批次号
											: i == 8 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}else {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_life_pay2);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceName(data.getAccount_name())//账户名
											: i == 2 ? StringUtil.replaceNum(data.getAccount_number())//账户号码
											: i == 3 ? data.getPaychannel()//支付通道
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getGet()*1.0f/100)//产出分润
											: i == 5 ? getString(R.string.notation_yuan)+df.format(data.getPay()*1.0f/100)//支付分润
											: i == 6 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 7 ? data.getTradedTimeStr()//交易时间
											: i == 8 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 9 ? data.getBatchNumber()//交易批次号
											: i == 10 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}
						}else if (typeId == 4) {//话费充值
							if (MyApplication.NewUser.getIs_have_profit() == 1) {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_phone_pay);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceNum(data.getPhone())//手机号码
											: i == 2 ? data.getPaychannel()//支付通道
											: i == 3 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 4 ? data.getTradedTimeStr()//交易时间
											: i == 5 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 6 ? data.getBatchNumber()//交易批次号
											: i == 7 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}else {
								bankKeys = resources.getStringArray(R.array.trade_item_bank_phone_pay2);
								for (int i = 0; i < bankKeys.length; i++) {
									TextView value = new TextView(TradeDetailActivity.this);
									value.setGravity(Gravity.LEFT);
									value.setPadding(0, 5, 0, 5);
									value.setTextColor(resources.getColor(R.color.text6c6c6c6));
									value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
									value.setText(i == 0 ? data.getTerminalNumber()//终 端 号
											: i == 1 ? StringUtil.replaceNum(data.getPhone())//手机号码
											: i == 2 ? data.getPaychannel()//支付通道
											: i == 3 ? getString(R.string.notation_yuan)+df.format(data.getGet()*1.0f/100)//产出分润
											: i == 4 ? getString(R.string.notation_yuan)+df.format(data.getPay()*1.0f/100)//支付分润
											: i == 5 ? getString(R.string.notation_yuan)+df.format(data.getAmount()*1.0f/100)//交易金额
											: i == 6 ? data.getTradedTimeStr()//交易时间
											: i == 7 ? tradeStatuses[data.getTradedStatus()]//交易状态
											: i == 8 ? data.getBatchNumber()//交易批次号
											: i == 9 ? data.getTradeNumber()//交易流水号
											: "");
									mBankValueContainer.addView(value);
								}
							}
							
						}
					}

					@Override
					public TypeToken<TradeDetail> getTypeToken() {
						return new TypeToken<TradeDetail>() {
						};
					}
				});
	}

	private void initialViews() {
		mTradeStatus = (TextView) findViewById(R.id.trade_detail_status);
		mTradeAmount = (TextView) findViewById(R.id.trade_detail_amount);
		mTradePoundage = (TextView) findViewById(R.id.trade_detail_poundage);
		mTradeTime = (TextView) findViewById(R.id.trade_detail_time);

		mCommercialKeyContainer = (LinearLayout) findViewById(R.id.trade_commercial_key_container);
		mCommercialValueContainer = (LinearLayout) findViewById(R.id.trade_commercial_value_container);
		mBankKeyContainer = (LinearLayout) findViewById(R.id.trade_bank_key_container);
		mBankValueContainer = (LinearLayout) findViewById(R.id.trade_bank_value_container);

		Resources resources = getResources();
		String[] commercialKeys = resources
				.getStringArray(R.array.trade_item_commercial);
		//String[] bankKeys = resources.getStringArray(R.array.trade_item_bank);

		for (int i = 0; i < commercialKeys.length; i++) {
			TextView key = new TextView(this);
			key.setGravity(Gravity.RIGHT);
			key.setPadding(0, 5, 0, 5);
			key.setTextColor(resources.getColor(R.color.text6c6c6c6));
			key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			key.setText(commercialKeys[i]);
			mCommercialKeyContainer.addView(key);
		}
		if (typeId == 2 || typeId == 3){ // 转账 。还款 
			if (MyApplication.NewUser.getIs_have_profit() == 1) 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_transfer);
			else 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_transfer2);
			
		}else if (typeId == 1) {//消费
			if (MyApplication.NewUser.getIs_have_profit() == 1) 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_consume);
			else 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_consume2);
			
		}else if (typeId == 5) {//生活充值
			if (MyApplication.NewUser.getIs_have_profit() == 1) 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_life_pay);
			else 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_life_pay2);
			
		}else if (typeId == 4) {//话费充值
			if (MyApplication.NewUser.getIs_have_profit() == 1) 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_phone_pay);
			else 
				bankKeys = resources.getStringArray(R.array.trade_item_bank_phone_pay2);
			
		}
		for (int i = 0; i < bankKeys.length; i++) {
			TextView key = new TextView(this);
			key.setGravity(Gravity.RIGHT);
			key.setPadding(0, 5, 0, 5);
			key.setTextColor(resources.getColor(R.color.text6c6c6c6));
			key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			key.setText(bankKeys[i]);
			mBankKeyContainer.addView(key);
		}
	}
}
