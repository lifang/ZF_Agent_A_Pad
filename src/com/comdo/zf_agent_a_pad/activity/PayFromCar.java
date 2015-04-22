package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

import android.os.Bundle;
public class PayFromCar extends BaseActivity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pay);
			new TitleMenuUtil(PayFromCar.this, "选择支付方式").show();
		}
}
