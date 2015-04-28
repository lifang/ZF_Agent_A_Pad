package com.comdo.zf_agent_a_pad.activity;

import com.comdo.zf_agent_a_pad.adapter.StaffmanagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.fragment.Staffmanagr;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class EditStallmanager extends BaseActivity implements OnCheckedChangeListener{
	private EditText et_loginid,et_name,et_paw,et_confirm_paw;
	private CheckBox cb_pigou,cb_daigou,cb_manager_zhongduan,cb_quary,
	cb_manager_agent,cb_manager_user,cb_manager_worker,cb_manager_Addres;
	private String roles="";
	private Button btn_save;
	private String[] str=new String[]{};
	private boolean ispigou=false;
	private boolean isdaigou=false;
	private boolean ismanagerzhongduan=false;
	private boolean isquary=false;
	private boolean ismanageragent=false;
	private boolean ismanageruser=false;
	private boolean ismanagerworker=false;
	private boolean ismanagerAddres=false;
	private boolean[] is=new boolean[8];
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.editstaffmanager);
	new TitleMenuUtil(EditStallmanager.this, "员工详情修改").show();
	init();
}

private void init() {
	
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
	Intent intent=getIntent();
	et_loginid.setText(intent.getStringExtra("loginid"));
	et_name.setText(intent.getStringExtra("name"));
	roles=intent.getStringExtra("role");
	str=roles.split(",");
	for(int i=0;i<str.length;i++){
		if(str[i].equals("1")){
			cb_pigou.setChecked(true);
		}
		if(str[i].equals("2")){
			cb_daigou.setChecked(true);
		}
		if(str[i].equals("3")){
			cb_manager_zhongduan.setChecked(true);
		}
		if(str[i].equals("4")){
			cb_quary.setChecked(true);
		}
		if(str[i].equals("5")){
			cb_manager_agent.setChecked(true);
		}
		if(str[i].equals("6")){
			cb_manager_user.setChecked(true);
		}
		if(str[i].equals("7")){
			cb_manager_worker.setChecked(true);
		}
		if(str[i].equals("8")){
			cb_manager_Addres.setChecked(true);
		}
	}
	btn_save.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			roles="";
			getRole();
			editStaffff();
			
		}
	});
}
protected void getRole() {
	for(int i=0;i<8;i++){
		if(is[i]){
			int j=i+1;
			roles=roles+String.valueOf(j)+",";
		}
	}
	/*if(ispigou){
		roles=roles+"1,";
	}
	else if(isdaigou){
		roles=roles+"2,";
	}
	else if(ismanagerzhongduan){
		roles=roles+"3,";
	}
	else if(isquary){
		roles=roles+"4,";
	}
	else if(ismanageragent){
		roles=roles+"5,";
	}
	else if(ismanageruser){
		roles=roles+"6,";
	}
	else if(ismanagerworker){
		roles=roles+"7,";
	}
	else if(ismanagerAddres){
		roles=roles+"8,";
	}*/
}

@Override
public void onCheckedChanged(CompoundButton v, boolean isCheck) {
	switch (v.getId()) {
	case R.id.cb_pigou:
		if(isCheck){
			ispigou=true;
			is[0]=true;
		}
		else{
			is[0]=false;
			ispigou=false;
		}
	//		roles=roles+"1,";
		break;
	case R.id.cb_daigou:
		if(isCheck){
			is[1]=true;
			isdaigou=true;
		}
		else{
			is[1]=false;
			isdaigou=false;
		}
	//	roles=roles+"2,";
		break;
	case R.id.cb_manager_zhongduan:
		if(isCheck){
			is[2]=true;
			ismanagerzhongduan=true;
		}
		else{
			is[2]=false;
			ismanagerzhongduan=false;
		}
	//	roles=roles+"3,";
		break;
	case R.id.cb_quary:
		if(isCheck){
			is[3]=true;
			isquary=true;
		}
		else{
			is[3]=false;
			isquary=false;
		}
	//	roles=roles+"4,";
		break;
	case R.id.cb_manager_agent:
		if(isCheck){
			is[4]=true;
			ismanageragent=true;
		}
		else{
			is[4]=false;
			ismanageragent=false;
		}
	//	roles=roles+"5,";
		break;
	case R.id.cb_manager_user:
		if(isCheck){
			is[5]=true;
			ismanageruser=true;
		}
		else{
			is[5]=false;
			ismanageruser=false;
		}
	//	roles=roles+"6,";
		break;
	case R.id.cb_manager_worker:
		if(isCheck){
			is[6]=true;
			ismanagerworker=true;
		}
		else{
			is[6]=false;
			ismanagerworker=false;
		}
	//	roles=roles+"7,";
		break;
	case R.id.cb_manager_Addres:
		if(isCheck){
			is[7]=true;
			ismanagerAddres=true;
		}
		else{
			is[7]=false;
			ismanagerAddres=false;
		}
	//	roles=roles+"8,";
		break;

	default:
		break;
	}
	
}
protected void editStaffff() {
	String rolesnew=roles.substring(0, roles.length()-1);
	Config.editStaff(EditStallmanager.this, Staffmanagr.datastaff.get(StaffmanagerAdapter.pp).getId()+"", rolesnew, 
			et_paw.getText().toString(), new HttpCallback(EditStallmanager.this) {

				@Override
				public void onSuccess(Object data) {
					CommonUtil.toastShort(EditStallmanager.this, "修改成功");
					finish();
				}

				@Override
				public TypeToken getTypeToken() {
					// TODO Auto-generated method stub
					return null;
				}
			});
}
}
