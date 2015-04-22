package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.comdo.zf_agent_a_pad.adapter.AddressManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Mine_adress extends Fragment implements OnClickListener{
	private View view;
	public static List<AddressManager> dataadress;
	
	private BaseAdapter addressadapter;
	private ListView list;
	private Context context;
	private Button btn_add;
	public static boolean isclickitem=false;
	public static LinearLayout ll_address;
	public static Handler myHandler;
	private int j=0;
	//private int id=MyApplication.NewUser.getId();
	public static int[] idd;
	public static int type=0;
	private EditText login_edit_name,mobile_phone,zip_code,detail_address;
	private TextView area,tv_title;
	private Button btn_save;
	private CheckBox cb;
	private int isDefault;
	private int cityId;
	private AlertDialog.Builder builder;
	private boolean isEdit=false;
	//private TextView info,safe,manageradress,score;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	if (view != null) {
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null)
			 parent.removeView(view);
	}
	try {
		view = inflater.inflate(R.layout.manageradress, container, false);
		init();
	} catch (InflateException e) {
	
	}
	return view;
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	getData();
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				list.setAdapter(addressadapter);
				break;
			case 1:

				if(dataadress.size()!=0){
					dataadress.clear();
				}
				getData();
			
				break;
			case 2:
				isEdit=true;
				opendialog();
				
				break;
			default:
				break;
			}
			
		};
	};
}
private void getData() {
	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	Config.GetAdressLis(getActivity(), 80, new HttpCallback<List<AddressManager>>(getActivity()) {

		@Override
		public void onSuccess(List<AddressManager> data) {
			
			if(dataadress.size()!=0&&data.size()==0){
				Toast.makeText(getActivity(), "没有更多数据!", 1000).show();
			}
			else{
				dataadress.addAll(data);
				idd=new int[data.size()];
				for(int i=0;i<idd.length;i++){
					idd[i]=(int) data.get(i).getId();
				}
				for(int i=0;i<dataadress.size();i++){
					if(dataadress.get(i).getIsDefault().equals("1")){
						dataadress.get(i).setIsDefault("默认");
					}
					else{
						dataadress.get(i).setIsDefault("");
					}
				}
			}
			if(dataadress.size()!=0){
				myHandler.sendEmptyMessage(0);	
			}
			
		}

		@Override
		public TypeToken<List<AddressManager>> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<List<AddressManager>>() {
			};
		}
	});
		
	}
private void init() {
	ll_address=(LinearLayout) view.findViewById(R.id.ll_address);
	dataadress=new ArrayList<AddressManager>();
	addressadapter=new AddressManagerAdapter(dataadress, getActivity().getBaseContext());
	list=(ListView) view.findViewById(R.id.list);
	btn_add=(Button) view.findViewById(R.id.btn_add);
	btn_add.setOnClickListener(this);
	}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_add:
	/*Intent intent=new Intent(getActivity(),AdressEdit.class);	
	startActivity(intent);*/
		opendialog();
		break;
	case R.id.btn_save:
		if(isEdit){
			changeAddress();
		}
		else{
			addAddresss();
		}
		
		break;
	case R.id.area:
		Intent intent = new Intent(getActivity(),
				CityProvinceActivity.class);
		//intent.putExtra(CITY_NAME, cityName);
		//startActivityForResult(intent, REQUEST_CITY);
		startActivityForResult(intent, com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
		break;
	default:
		break;
	}
	
}
private void changeAddress() {
	Config.changeAdres(getActivity(), idd[AddressManagerAdapter.pp],
			String.valueOf(cityId), login_edit_name.getText().toString(), 
			mobile_phone.getText().toString(), 
			zip_code.getText().toString(), 
			detail_address.getText().toString(),80, isDefault, new HttpCallback(getActivity()) {

				@Override
				public void onSuccess(Object data) {
					CommonUtil.toastShort(getActivity(), "修改地址成功");
					getData();
					
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
private void addAddresss() {
	Config.AddAdress(getActivity(), String.valueOf(cityId), login_edit_name.getText().toString(), 
			mobile_phone.getText().toString(), 
			zip_code.getText().toString(), 
			detail_address.getText().toString(), isDefault, 80, new HttpCallback(getActivity()) {

				@Override
				public void onSuccess(Object data) {
					CommonUtil.toastShort(getActivity(), "添加地址成功");
					myHandler.sendEmptyMessage(1);
				}

				@Override
				public TypeToken getTypeToken() {
					// TODO Auto-generated method stub
					return null;
				}
			});
	
}
private void opendialog() {
	builder = new AlertDialog.Builder(getActivity());
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.addaddress, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     login_edit_name=(EditText) textEntryView.findViewById(R.id.login_edit_name);
     mobile_phone=(EditText) textEntryView.findViewById(R.id.mobile_phone);
     zip_code=(EditText) textEntryView.findViewById(R.id.zip_code);
     detail_address=(EditText) textEntryView.findViewById(R.id.detail_address);
     area=(TextView) textEntryView.findViewById(R.id.area);
     btn_save=(Button) textEntryView.findViewById(R.id.btn_save);
     cb=(CheckBox) textEntryView.findViewById(R.id.cb);
     tv_title=(TextView) textEntryView.findViewById(R.id.tv_title);
     btn_save.setOnClickListener(this);
     area.setOnClickListener(this);
     //final AlertDialog dialog = builder.show();
     //dialog=builder.show();
     builder.create().show();
     if(isEdit){
    	 tv_title.setText("编辑地址") ;
    	 login_edit_name.setText(dataadress.get(AddressManagerAdapter.pp).getReceiver());
    	 mobile_phone.setText(dataadress.get(AddressManagerAdapter.pp).getMoblephone());
    	 zip_code.setText(dataadress.get(AddressManagerAdapter.pp).getZipCode());
    	 detail_address.setText(dataadress.get(AddressManagerAdapter.pp).getAddress());
    	 area.setText(dataadress.get(AddressManagerAdapter.pp).getCity());
    	 if(dataadress.get(AddressManagerAdapter.pp).getIsDefault().equals("默认")){
    		 cb.setBackgroundResource(R.drawable.cb_y);
    		 isDefault=1;
    	 }
    	 else{
    		 cb.setBackgroundResource(R.drawable.cb_n);
    		 isDefault=2;
    	 }
    	 
     }
     cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
			if(isCheck){
				isDefault=1;
			}
			else{
				isDefault=2;
			}
			
		}
	});
}
@Override
public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
}
@Override
public void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
}
@Override
public void onDestroyView() {
	// TODO Auto-generated method stub
	super.onDestroyView();
}
}
