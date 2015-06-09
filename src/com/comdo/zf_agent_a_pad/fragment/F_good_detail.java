package com.comdo.zf_agent_a_pad.fragment;

import java.util.List;

import com.comdo.zf_agent_a_pad.activity.GoodDeatail;
import com.comdo.zf_agent_a_pad.adapter.GridviewAdapter;
import com.comdo.zf_agent_a_pad.adapter.HuilvAdapter;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithGView;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class F_good_detail extends Fragment implements OnClickListener{
	private View view;

	private TextView ppxx;
	private TextView wkxx;
	private TextView tv_qgd;
	private TextView tv_jm,tvc_zx,tvc_qy;
	private TextView tv_spxx;
	private TextView tv_sqkt;
	private ScrollViewWithGView gview;
	private GridviewAdapter gadapter;
	private TextView is_zc;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.goodmore, container, false);
		 initView();
		return view;
	}
	private void initView() {
		is_zc = (TextView)view.findViewById(R.id.is_zc);
		if(Config.support_type){
			is_zc.setText("支 持 支 付 区 域");
		}else{
			is_zc.setText("不支持支付区域");
		}
		gview=(ScrollViewWithGView)view.findViewById(R.id.gview);
		tv_sqkt = (TextView)view.findViewById(R.id.tv_sqkt);
		tv_spxx = (TextView)view.findViewById(R.id.tv_spxx);
		tvc_zx=(TextView) view.findViewById(R.id.tvc_zx);
		tvc_qy=(TextView)view.findViewById(R.id.tvc_qy);
		ppxx = (TextView) view.findViewById(R.id.ppxx);
		wkxx = (TextView) view.findViewById(R.id.wkxx);
		TextView dcxx=(TextView) view.findViewById(R.id.dcxx);
		tv_qgd = (TextView)view. findViewById(R.id.tv_qgd);
		tv_jm = (TextView) view.findViewById(R.id.tv_jm);

		ppxx.setText(Config.gfe.getGood_brand()+Config.gfe.getModel_number());
		wkxx.setText(Config.gfe.getShell_material() );
		dcxx.setText(Config.gfe.getBattery_info());
		tv_qgd.setText(Config.gfe.getSign_order_way());
		tv_jm.setText(Config.gfe.getEncrypt_card_way());
		tv_spxx.setText(Config.gfe.getDescription() );
		if(Config.suportare!=null)
		 tvc_qy.setText(Config.suportare);
		 tvc_zx.setText(Config.suportcl);
		 tv_sqkt.setText(Config.tv_sqkt);
		 tv_spxx.setText(Config.gfe.getDescription());	
		 gadapter=new GridviewAdapter(getActivity(), Config.myList);
		 gview.setAdapter(gadapter);
		 gview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {			
				Intent i =new Intent (getActivity(),GoodDeatail.class);
				i.putExtra("id", Config.myList.get(arg2).getId());
			 
				startActivity(i);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		
	}

}
