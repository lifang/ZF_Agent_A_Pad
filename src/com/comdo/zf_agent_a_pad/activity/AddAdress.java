package com.comdo.zf_agent_a_pad.activity;


import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddAdress extends BaseActivity implements OnClickListener{
	private AlertDialog.Builder builder;
	private TextView area,tv_title;
	private Button btn_save,close;
	private EditText login_edit_name,mobile_phone,zip_code,detail_address;
	private CheckBox cb;
	private boolean isEdit=false;
	private int isDefault;
	private AlertDialog dialog;
	private Button bt_addadress;
	private int cityId;
	private int userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.maddaddress);
		userid = getIntent().getIntExtra("userid", 0);
		initView();
	}

	private void initView() {
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
	     mobile_phone=(EditText) findViewById(R.id.mobile_phone);
	     zip_code=(EditText) findViewById(R.id.zip_code);
	     detail_address=(EditText) findViewById(R.id.detail_address);
	     area=(TextView) findViewById(R.id.area);
	     btn_save=(Button) findViewById(R.id.btn_save);
	     cb=(CheckBox) findViewById(R.id.cb);
	     tv_title=(TextView) findViewById(R.id.tv_title);
	     close=(Button) findViewById(R.id.close);
	     close.setOnClickListener(this);
	     btn_save.setOnClickListener(this);
	     area.setOnClickListener(this);
	     cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 		
	 		@Override
	 		public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
	 			if(isCheck){
	 				isDefault=1;
	 				 cb.setBackgroundResource(R.drawable.cb_y);
	 			}
	 			else{
	 				isDefault=2;
	 				cb.setBackgroundResource(R.drawable.cb_n);
	 			}
	 			
	 		}
	 	});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
	
		case R.id.btn_save:
			if(check()){
				if(cb.isChecked()){
					isDefault=1;
				}else{
					isDefault=2;
				}
				addAddresss();
			}
						
			break;
		case R.id.area:
			Intent intent = new Intent(AddAdress.this,
					CityProvinceActivity.class);
			//intent.putExtra(CITY_NAME, cityName);
			//startActivityForResult(intent, REQUEST_CITY);
			startActivityForResult(intent, com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
			break;
		case R.id.close:
			this.finish();
			break;
		default:
			break;
		}
	}
	private void addAddresss() {
		Config.AddAdress(AddAdress.this, String.valueOf(cityId), login_edit_name.getText().toString(), 
				mobile_phone.getText().toString(), 
				zip_code.getText().toString(), 
				detail_address.getText().toString(), isDefault, userid, new HttpCallback(AddAdress.this) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(AddAdress.this, "添加地址成功");
						setResult(RESULT_OK);
						AddAdress.this.finish();
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		//case REQUEST_CITY:
		//case com.example.zf_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
		case com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
			if(CityProvinceActivity.isClickconfirm){
				City mMerchantCity = (City) data.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY);
				cityId=mMerchantCity.getId() ;
				area.setText(mMerchantCity.getName());
				Log.e("1", area.getText().toString());
				CityProvinceActivity.isClickconfirm=false;
				/*cityId = data.getIntExtra(CITY_ID, 0);
				cityName = data.getStringExtra(CITY_NAME);
				tv_city_select.setText(cityName);*/
			}
			
			break;
		
		default:
			break;
		}
	}
	private boolean check() {
		String name = StringUtil.replaceBlank(login_edit_name.getText().toString());
		if (name.length() == 0) {
			Toast.makeText(getApplicationContext(), "收件人不能为空！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		String phone = StringUtil.replaceBlank(mobile_phone.getText()
				.toString());
		if (mobile_phone.length() == 0) {
			Toast.makeText(getApplicationContext(), "联系电话不能为空！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
}
