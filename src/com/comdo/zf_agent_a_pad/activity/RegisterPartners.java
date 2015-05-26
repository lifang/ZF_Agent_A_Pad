package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;

import com.comdo.zf_agent_a_pad.adapter.SelectStateAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.API;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.trade.entity.Province;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.MyToast;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class RegisterPartners extends BaseActivity implements OnClickListener{

	private EditText et_name,et_phone,et_vcode;
	private TextView partnersType,cityTextView,agreementTextView;
	private TextView tv_msg;
	private Button btn_save;
	private CheckBox checkboxAll;
	private ImageView img_check;

	private PopupWindow popuSelState;
	private SelectStateAdapter<String> selStateAdapter;
	//设置popupWindow弹出后背景的阴影
	private WindowManager.LayoutParams lp;
	private List<String> dataList = new ArrayList<String>();

	private int provinceId,cityId;
	private String getPhoneCode = "";

	private int Countmun=120;
	private Boolean isRun=true;
	private Runnable runnable;
	final Handler handler = new Handler(){          // handle  
		public void handleMessage(Message msg){  
			switch (msg.what) {  
			case 1:  
				if(Countmun==0){

					isRun=false;
					tv_msg.setClickable(true);

					tv_msg.setText("发送验证码");
					System.out.println("destroy`"+Countmun);
				}else{
					Countmun--;  
					tv_msg.setText(  Countmun+"秒后重新发送");  
					System.out.println("Countmun`D2`"+Countmun);
				}
			case 2:
				if (!StringUtil.isNull(et_vcode.getText().toString().trim())) {
					if (getPhoneCode.equals(et_vcode.getText().toString())) {
						img_check.setVisibility(View.VISIBLE);
						img_check.setBackgroundResource(R.drawable.check_y);
					}else {
						img_check.setVisibility(View.VISIBLE);
						img_check.setBackgroundResource(R.drawable.check_n);
					}
				}
			}  
			super.handleMessage(msg);  
		}  
	}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_partners);
		new TitleMenuUtil(RegisterPartners.this, getResources().getString(
				R.string.applyforagent)).show();

		lp = getWindow().getAttributes(); // 设置popupWindow弹出后背景的阴影

		dataList.add("个人");
		dataList.add("公司");
		init();

		runnable = new Runnable() {  
			@Override  
			public void run() {  
				if(Countmun==0){

					Countmun=120;
					tv_msg.setClickable(true);
					tv_msg.setText("发送验证码");
				}else{

					Countmun--;  
					tv_msg.setText( Countmun+"秒后重新发送");  

					handler.postDelayed(this, 1000);  
				}
			}  
		};
	}

	private void init() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_vcode = (EditText) findViewById(R.id.et_vcode);
		agreementTextView = (TextView) findViewById(R.id.agreementTextView);
		partnersType = (TextView) findViewById(R.id.partnersType);
		cityTextView = (TextView) findViewById(R.id.cityTextView);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		btn_save = (Button) findViewById(R.id.btn_save);
		checkboxAll = (CheckBox) findViewById(R.id.checkboxAll);
		img_check = (ImageView) findViewById(R.id.img_check);

		agreementTextView.setOnClickListener(this);
		cityTextView.setOnClickListener(this);
		partnersType.setOnClickListener(this);
		tv_msg.setOnClickListener(this);
		btn_save.setOnClickListener(this);

		checkboxAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				checkboxAll.setChecked(isChecked);
			}
		});

		et_vcode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
							handler.sendEmptyMessage(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}).start();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agreementTextView:
			Intent intent1 = new Intent(this,AgreementActivity.class);
			startActivity(intent1);
			break;
		case R.id.partnersType:
			popSelectState();
			break;
		case R.id.cityTextView:
			Intent intent = new Intent(this,CityProvinceActivity.class);
			startActivityForResult(intent,
					com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
			break;
		case R.id.tv_msg:
			if (!StringUtil.isNull(et_phone.getText().toString().trim())) {
				if (StringUtil.isMobile(et_phone.getText().toString().trim())) {
					Config.sendPhoneVerCodeReg(RegisterPartners.this,
							et_phone.getText().toString().trim(),
							new HttpCallback(RegisterPartners.this) {		       
						@Override
						public void onSuccess(Object data) {
							tv_msg.setClickable(false);
							getPhoneCode = data+"";
							handler.postDelayed(runnable, 1000); 
							MyToast.showToast(getApplicationContext(),"验证码发送成功");
						}
						@Override
						public TypeToken getTypeToken() {
							return null;
						}
					});
				}else {
					MyToast.showToast(getApplicationContext(),"请输入正确的手机号");
				}
			}else {
				MyToast.showToast(getApplicationContext(),"请输入手机号");
			}
			break;
		case R.id.btn_save:
			if (check()) {
				String address = provinceId+"_"+cityId;

				Config.getRegisterPartners(RegisterPartners.this,
						et_name.getText().toString().trim(),
						et_phone.getText().toString().trim(),
						partnersType.getText().toString(),address,
						new HttpCallback(RegisterPartners.this) {		       
					@Override
					public void onSuccess(Object data) {
						MyToast.showToast(RegisterPartners.this, "您的申请已经提交,静候我们的工作人员处理");
						finish();
					}
					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
			}
			break;

		default:
			break;
		}

	}

	private void popSelectState() {

		View selectView = LayoutInflater.from(this).inflate(
				R.layout.pop_selector_state, null);
		ListView listViewState = (ListView) selectView.findViewById(R.id.list_one);
		selStateAdapter = new SelectStateAdapter<String>(this, dataList);
		listViewState.setAdapter(selStateAdapter);
		listViewState.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				partnersType.setText(dataList.get(position));
				popuSelState.dismiss();
			}
		});

		popuSelState = new PopupWindow(selectView);
		popuSelState.setWidth(partnersType.getWidth());
		popuSelState.setHeight(LayoutParams.WRAP_CONTENT);
		popuSelState.setOutsideTouchable(true);
		popuSelState.setFocusable(true);
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popuSelState.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		popuSelState.setBackgroundDrawable(new ColorDrawable());
		popuSelState.showAsDropDown(partnersType);
	}
	private boolean check () {
		if (StringUtil.isNull(et_name.getText().toString())) {
			MyToast.showToast(getApplicationContext(),"请输入您的姓名");
			return false;
		}
		if (StringUtil.isNull(et_phone.getText().toString())) {
			MyToast.showToast(getApplicationContext(),"请输入您的手机号");
			return false;
		}
		if (StringUtil.isNull(et_vcode.getText().toString())) {
			MyToast.showToast(getApplicationContext(),"验证码不能为空");
			return false;
		}
		if (!getPhoneCode.equals(et_vcode.getText().toString().trim())) {
			MyToast.showToast(getApplicationContext(),"请输入正确的验证码");
			return false;
		}
		if (StringUtil.isNull(partnersType.getText().toString())) {
			MyToast.showToast(getApplicationContext(),"请选择合作伙伴类型");
			return false;
		}
		if (StringUtil.isNull(cityTextView.getText().toString())) {
			MyToast.showToast(getApplicationContext(),"请选择所在城市");
			return false;
		}
		if (checkboxAll.isChecked() == false) {
			MyToast.showToast(getApplicationContext(),"您尚未同意华尔街金融平台用户使用协议");
			return false;
		}
		return true;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
			Province mMerchantProvince = (Province) data
			.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_PROVINCE);
			City mMerchantCity = (City) data
					.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY);
			provinceId = mMerchantProvince.getId();
			cityId = mMerchantCity.getId();
			cityTextView.setText(mMerchantProvince.getName()+mMerchantCity.getName());

			break;

		default:
			break;
		}
	}

}
