package com.comdo.zf_agent_a_pad.fragment;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.BaseInfoEntity;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Mybaseinfo extends Fragment implements OnClickListener{
	private View view;
	private JSONObject js;
	private Handler myHandler;
	private TextView tv_company_type,tv_company_name,tv_yingyezhizhao,
	tv_company_shuiwudengji,tv_fuzeren_name,tv_fuzeren_no,
	tv_phone,tv_email,tv_adress,tv_adressdetail,tv_id_detail;
	private ImageView iv_shenfenzheng,iv_yingyezhizhao,iv_shuiwu;
	private String shenfenzheng,yingyezhizhao,shuiwu;
	private int tag=0;
	private String[] imgPath=new String[3];
	private Button btn_exit;
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
}
@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	
	//view = inflater.inflate(R.layout.f_main,container,false);
	


if (view != null) {
    ViewGroup parent = (ViewGroup) view.getParent();
    if (parent != null)
        parent.removeView(view);
}
try {
    view = inflater.inflate(R.layout.baseinfo, container, false);
    init();
    getData();
} catch (InflateException e) {
    
}
return view;
}
private void init() {
	btn_exit=(Button) view.findViewById(R.id.btn_exit);
	tv_company_type=(TextView) view.findViewById(R.id.tv_company_type);
	tv_company_name=(TextView) view.findViewById(R.id.tv_company_name);
	tv_yingyezhizhao=(TextView) view.findViewById(R.id.tv_yingyezhizhao);
	tv_company_shuiwudengji=(TextView) view.findViewById(R.id.tv_company_shuiwudengji);
	tv_fuzeren_name=(TextView) view.findViewById(R.id.tv_fuzeren_name);
	tv_fuzeren_no=(TextView) view.findViewById(R.id.tv_fuzeren_no);
	tv_phone=(TextView) view.findViewById(R.id.tv_phone);
	tv_email=(TextView) view.findViewById(R.id.tv_email);
	tv_adress=(TextView) view.findViewById(R.id.tv_adress);
	tv_adressdetail=(TextView) view.findViewById(R.id.tv_adressdetail);
	iv_shenfenzheng=(ImageView) view.findViewById(R.id.iv_shenfenzheng);
	iv_yingyezhizhao=(ImageView) view.findViewById(R.id.iv_yingyezhizhao);
	iv_shuiwu=(ImageView) view.findViewById(R.id.iv_shuiwu);
	tv_id_detail=(TextView) view.findViewById(R.id.tv_id_detail);
	iv_shenfenzheng.setOnClickListener(this);
	iv_yingyezhizhao.setOnClickListener(this);
	iv_shuiwu.setOnClickListener(this);
	btn_exit.setOnClickListener(this);
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				try {
					tv_fuzeren_name.setText(js.getString("username"));
					tv_phone.setText(js.getString("phone"));
					tv_email.setText("email");
					tv_company_name.setText(js.getString("company_name"));
					tv_company_type.setText(js.getString("types"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;

			default:
				break;
			}
		};
	};
}
private void getData() {
	Config.GetInfo(getActivity(), 80, new HttpCallback<BaseInfoEntity>(getActivity()) {

		@Override
		public void onSuccess(BaseInfoEntity data) {
			//myHandler.sendEmptyMessage(0);
			tv_company_type.setText(data.getTypes());
			tv_company_name.setText(data.getCompany_name());
			tv_yingyezhizhao.setText(data.getBusiness_license());
			tv_company_shuiwudengji.setText(data.getTax_registered_no());
			tv_fuzeren_name.setText(data.getUsername());
			tv_fuzeren_no.setText(data.getCard_id());
			tv_phone.setText(data.getPhone());
			tv_email.setText(data.getEmail());
			tv_adress.setText(data.getCity_id());
			tv_adressdetail.setText(data.getAddress());
			shenfenzheng=data.getCard_id_photo_path();
			yingyezhizhao=data.getLicense_no_pic_path();
			shuiwu=data.getTax_no_pic_path();
			tv_id_detail.setText(data.getId()+"");
		}

		@Override
		public TypeToken<BaseInfoEntity> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<BaseInfoEntity>() {
			};
		}
	});
	}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.iv_shenfenzheng:
		tag=1;
		imgPath[0]=shenfenzheng;
		openimg(tag);
		break;
    case R.id.iv_yingyezhizhao:
    	tag=2;
    	imgPath[1]=yingyezhizhao;
    	openimg(tag);
		break;
    case R.id.iv_shuiwu:
    	tag=3;
    	imgPath[2]=shuiwu;
    	openimg(tag);
	    break;
    case R.id.btn_exit:
    	getActivity().finish();
    	break;
	default:
		break;
	}
	
}
private void openimg(int tag) {

	AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.img, null);
	 //build.setTitle("自定义输入框");
     build.setView(textEntryView);
     final ImageView view=(ImageView) textEntryView.findViewById(R.id.imag);
     int ppp=tag-1;
     //ImageCacheUtil.IMAGE_CACHE.get(imgPath[ppp], view);
     ImageCacheUtil.IMAGE_CACHE.get("http://www.sinaimg.cn/dy/slidenews/2_img/2015_16/789_1481006_298613.jpg", view);
     build.create().show();

	
}
}
