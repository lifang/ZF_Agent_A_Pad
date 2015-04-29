package com.comdo.zf_agent_a_pad.activity;

import java.util.List;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AgentDetailEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AgentDetail extends BaseActivity implements OnClickListener{
	private int sonAgentsId;
	private TextView tv_company_type,tv_company_name,tv_yingyezhizhao,
	tv_company_shuiwudengji,tv_fuzeren_name,tv_fuzeren_no,
	tv_phone,tv_email,tv_adress,tv_adressdetail,tv_jointime,tv_has_amount,tv_left_amount,tv_terminal_amount;
	private TextView tv_id_detail;
	private String isprofit;
	private String cardPath,licensePath,taxPath;
	private ImageView iv_shenfenzheng,iv_yingyezhizhao,iv_shuiwu,iv_fenrun;
	private Handler myHandler;
	private int tag=0;
	private String[] imgPath=new String[3];
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.agentdetail);
	new TitleMenuUtil(AgentDetail.this, "下级代理商详情").show();
	init();
	
}
@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getData();
		myHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					if(isprofit.equals("1")){
						iv_fenrun.setBackgroundResource(R.drawable.agent_on);
					}
					else{
						iv_fenrun.setBackgroundResource(R.drawable.agent_off);
					}
					break;

				default:
					break;
				}
			};
		};
	}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.iv_shenfenzheng:
		tag=1;
		imgPath[0]=cardPath;
		openimg(tag);
		break;
	case R.id.iv_yingyezhizhao:
		tag=2;
		imgPath[1]=licensePath;
		openimg(tag);
		break;
	case R.id.iv_shuiwu:
		tag=3;
		imgPath[2]=taxPath;
		openimg(tag);
		break;
	default:
		break;
	}
	
}
private void openimg(int tag) {

	AlertDialog.Builder build = new AlertDialog.Builder(AgentDetail.this);
	LayoutInflater factory = LayoutInflater.from(AgentDetail.this);
	final View textEntryView = factory.inflate(R.layout.img, null);
	 //build.setTitle("自定义输入框");
     build.setView(textEntryView);
     final ImageView view=(ImageView) textEntryView.findViewById(R.id.imag);
     int ppp=tag-1;
     ImageCacheUtil.IMAGE_CACHE.get(imgPath[ppp], view);
     //ImageCacheUtil.IMAGE_CACHE.get("http://www.sinaimg.cn/dy/slidenews/2_img/2015_16/789_1481006_298613.jpg", view);
     build.create().show();

	
}
private void getData() {
	Config.GetAgentDetail(AgentDetail.this, sonAgentsId, new HttpCallback<AgentDetailEntity>(AgentDetail.this) {

		@Override
		public void onSuccess(AgentDetailEntity data) {
			if(data.getTypes().equals("1")){
				tv_company_type.setText("公司");
			}
			else if(data.getTypes().equals("2")){
				tv_company_type.setText("个人");
			}
			else{
				tv_company_type.setText(data.getTypes());
			}
			isprofit=data.getIs_have_profit();
			tv_company_name.setText(data.getCompany_name());
			tv_yingyezhizhao.setText(data.getBusiness_license());
			tv_company_shuiwudengji.setText(data.getTax_registered_no());
			tv_fuzeren_name.setText(data.getName());
			tv_fuzeren_no.setText(data.getCard_id());
			tv_phone.setText(data.getPhone());
			tv_email.setText(data.getEmail());
			tv_adress.setText(data.getCityId()+"");
			tv_adressdetail.setText(data.getAddress());
			cardPath=data.getCardpath();
			licensePath=data.getLicensepath();
			taxPath=data.getTaxpath();
			tv_id_detail.setText(data.getLoginId());
			tv_jointime.setText(data.getCreated_at());
			tv_has_amount.setText(data.getSoldnum());
			tv_left_amount.setText(Integer.parseInt(data.getAllQty())-Integer.parseInt(data.getSoldnum())+"");
			tv_terminal_amount.setText(data.getAllQty());
			myHandler.sendEmptyMessage(0);
		}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
		@Override
		public TypeToken<AgentDetailEntity> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<AgentDetailEntity>() {
			};
		}

		
	});
	
}
private void init() {
	Intent intent=getIntent();
	sonAgentsId=intent.getIntExtra("id", 0);
	tv_company_type=(TextView)findViewById(R.id.tv_company_type);
	tv_company_name=(TextView) findViewById(R.id.tv_company_name);
	tv_yingyezhizhao=(TextView) findViewById(R.id.tv_yingyezhizhao);
	tv_company_shuiwudengji=(TextView) findViewById(R.id.tv_company_shuiwudengji);
	tv_fuzeren_name=(TextView) findViewById(R.id.tv_fuzeren_name);
	tv_fuzeren_no=(TextView) findViewById(R.id.tv_fuzeren_no);
	tv_phone=(TextView) findViewById(R.id.tv_phone);
	tv_email=(TextView) findViewById(R.id.tv_email);
	tv_adress=(TextView) findViewById(R.id.tv_adress);
	tv_adressdetail=(TextView) findViewById(R.id.tv_adressdetail);
	tv_id_detail= (TextView) findViewById(R.id.tv_id_detail);
	tv_jointime=(TextView) findViewById(R.id.tv_jointime);
	tv_has_amount=(TextView) findViewById(R.id.tv_has_amount);
	tv_left_amount=(TextView) findViewById(R.id.tv_left_amount);
	tv_terminal_amount=(TextView) findViewById(R.id.tv_terminal_amount);
	iv_shenfenzheng=(ImageView) findViewById(R.id.iv_shenfenzheng);
	iv_yingyezhizhao=(ImageView) findViewById(R.id.iv_yingyezhizhao);
	iv_shuiwu=(ImageView) findViewById(R.id.iv_shuiwu);
	iv_fenrun=(ImageView) findViewById(R.id.iv_fenrun);
	iv_shenfenzheng.setOnClickListener(this);
	iv_yingyezhizhao.setOnClickListener(this);
	iv_shuiwu.setOnClickListener(this);
}
}
