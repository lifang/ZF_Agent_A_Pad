package com.comdo.zf_agent_a_pad.fragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.GoodinfoEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.epalmpay.agentPad.R;

public class Good_detail_zd extends Fragment implements OnClickListener{
	private View view;
	public GoodinfoEntity gfe=new GoodinfoEntity();
	private TextView tv1,tv2,tv3,tv4,tv5;
	private DecimalFormat df;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.act_leas_in, container, false);
		 df = (DecimalFormat) NumberFormat
					.getInstance();
			df.applyPattern("0.00");
		 initView();
		 
		return view;
	}
	private void initView() {
		gfe=Config.gfe;
		System.out.println("````"+gfe.getId());
		tv1=(TextView)view. findViewById(R.id.tv1);
		tv2=(TextView) view.findViewById(R.id.tv2);
		tv3=(TextView) view.findViewById(R.id.tv3);
		tv4=(TextView)view.findViewById(R.id.tv4);
		tv5=(TextView)view. findViewById(R.id.tv5);
		
		tv5.setText(gfe.getLease_agreement()+"");
		tv4.setText(gfe.getLease_description()+"");
		tv3.setText("￥" +df.format((double)gfe.getLease_price()/100)+"");
		tv2.setText(gfe.getReturn_time()+"月");
		tv1.setText(gfe.getLease_time()+"月");

	}

	@Override
	public void onClick(View arg0) {
		
	}

}
