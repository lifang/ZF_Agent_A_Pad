package com.comdo.zf_agent_a_pad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class CreatStaff extends Activity implements OnCheckedChangeListener{
	private EditText et_loginid,et_name,et_paw,et_confirm_paw;
	private CheckBox cb_pigou,cb_daigou,cb_manager_zhongduan,cb_quary,
	cb_manager_agent,cb_manager_user,cb_manager_worker,cb_manager_Addres;
	private String roles="";
	private Button btn_save;
	 private int agentId=MyApplication.NewUser.getAgentId();
	 private LinearLayout ll_creat;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.creatstaff);
	new TitleMenuUtil(CreatStaff.this, "员工创建").show();
	init();
}
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
}
private void init() {
	ll_creat=(LinearLayout) findViewById(R.id.ll_creat);
	btn_save=(Button) findViewById(R.id.btn_save);
	et_loginid=(EditText) findViewById(R.id.et_loginid);
	et_name=(EditText) findViewById(R.id.et_name);
	et_paw=(EditText) findViewById(R.id.et_paw);
	et_confirm_paw=(EditText) findViewById(R.id.et_confirm_paw);
	cb_pigou=(CheckBox) findViewById(R.id.cb_pigou);
	cb_daigou=(CheckBox) findViewById(R.id.cb_daigou);
	cb_manager_zhongduan=(CheckBox) findViewById(R.id.cb_manager_zhongduan);
	cb_quary=(CheckBox) findViewById(R.id.cb_quary);
	cb_manager_agent=(CheckBox) findViewById(R.id.cb_manager_agent);
	cb_manager_user=(CheckBox) findViewById(R.id.cb_manager_user);
	cb_manager_worker=(CheckBox) findViewById(R.id.cb_manager_worker);
	cb_manager_Addres=(CheckBox) findViewById(R.id.cb_manager_Addres);
	cb_pigou.setOnCheckedChangeListener(this);
	cb_daigou.setOnCheckedChangeListener(this);
	cb_manager_zhongduan.setOnCheckedChangeListener(this);
	cb_quary.setOnCheckedChangeListener(this);
	cb_manager_agent.setOnCheckedChangeListener(this);
	cb_manager_user.setOnCheckedChangeListener(this);
	cb_manager_worker.setOnCheckedChangeListener(this);
	cb_manager_Addres.setOnCheckedChangeListener(this);
	btn_save.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			checkPaw();
			save();
			
		}
	});
}
protected void checkPaw() {
	if(et_paw.getText().toString().length()<6){
		CommonUtil.toastShort(getApplicationContext(), "密码长度不能小于六位");
		return;
	}
}
protected void save() {
	String rolesnew=roles.substring(0, roles.length()-1);
	Config.insertCustomer(CreatStaff.this, et_name.getText().toString(), 
			et_loginid.getText().toString(), rolesnew, 
			agentId, et_paw.getText().toString(), et_confirm_paw.getText().toString(), 
			new HttpCallback(CreatStaff.this) {

				@Override
				public void onSuccess(Object data) {
					finish();
				}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
				@Override
				public TypeToken getTypeToken() {
					// TODO Auto-generated method stub
					return null;
				}
			});
	
}
@Override
public void onCheckedChanged(CompoundButton v, boolean isCheck) {
	switch (v.getId()) {
	case R.id.cb_pigou:
		roles=roles+"1,";
		break;
	case R.id.cb_daigou:
		roles=roles+"2,";
		break;
	case R.id.cb_manager_zhongduan:
		roles=roles+"3,";
		break;
	case R.id.cb_quary:
		roles=roles+"4,";
		break;
	case R.id.cb_manager_agent:
		roles=roles+"5,";
		break;
	case R.id.cb_manager_user:
		roles=roles+"6,";
		break;
	case R.id.cb_manager_worker:
		roles=roles+"7,";
		break;
	case R.id.cb_manager_Addres:
		roles=roles+"8,";
		break;

	default:
		break;
	}
	
}
}

