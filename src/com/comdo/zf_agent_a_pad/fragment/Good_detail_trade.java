package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.HuilvAdapter;
import com.comdo.zf_agent_a_pad.adapter.JiaoyiHuilvAdapter;
import com.comdo.zf_agent_a_pad.entity.ChanelEntitiy;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.epalmpay.agentPad.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
public class Good_detail_trade extends Fragment implements OnClickListener{
	private View view;
	private ListView lv;
	private JiaoyiHuilvAdapter myAdapter;
	private HuilvAdapter lvAdapter1,lvAdapter2,lvAdapter3;
	private ScrollViewWithListView  pos_lv1,pos_lv2,pos_lv3;
	private List<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.act_tradereate, container, false);
		 initView();
		return view;
	}
	private void initView() {
		pos_lv1=(ScrollViewWithListView) view.findViewById(R.id.pos_lv1);
		pos_lv2=(ScrollViewWithListView) view.findViewById(R.id.pos_lv2);
		pos_lv3=(ScrollViewWithListView) view.findViewById(R.id.pos_lv3);
		lvAdapter1 = new HuilvAdapter(getActivity(),Config.celist);
		lvAdapter2 = new HuilvAdapter(getActivity(),Config.tDates,0);
		lvAdapter3 = new HuilvAdapter(getActivity(),Config.other_rate,1,"1");
		pos_lv1.setAdapter(lvAdapter1);
		pos_lv2.setAdapter(lvAdapter2);
		pos_lv3.setAdapter(lvAdapter3);

	}

	@Override
	public void onClick(View arg0) {
		
		
	}

}
