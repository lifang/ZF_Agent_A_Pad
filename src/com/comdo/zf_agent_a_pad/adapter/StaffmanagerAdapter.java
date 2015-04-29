package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import com.comdo.zf_agent_a_pad.adapter.DistributeAdapter.ViewHoldel;
import com.comdo.zf_agent_a_pad.entity.DistributeEntity;
import com.comdo.zf_agent_a_pad.entity.StaffmanagerEntity;
import com.comdo.zf_agent_a_pad.fragment.Distribute;
import com.comdo.zf_agent_a_pad.fragment.Staffmanagr;
import com.example.zf_agent_a_pad.R;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StaffmanagerAdapter extends BaseAdapter{
	private List<StaffmanagerEntity> datastaff;
	private Context context;
	private LayoutInflater mInflater;
	public static int pp;
	public StaffmanagerAdapter(List<StaffmanagerEntity> datastaff,Context context){
		super();
		this.datastaff=datastaff;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datastaff.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datastaff.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return datastaff.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.staffmanageritem, null);
			holdel=new ViewHoldel();
			holdel.tv_loginid=(TextView) convertView.findViewById(R.id.tv_loginid);
			holdel.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holdel.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holdel.tv_delect=(TextView) convertView.findViewById(R.id.tv_delect);
			convertView.setTag(holdel);
			}
		else{
			holdel=(ViewHoldel) convertView.getTag();
		}
		holdel.tv_loginid.setText(datastaff.get(position).getUsername());
		holdel.tv_name.setText(datastaff.get(position).getName());
		holdel.tv_time.setText(datastaff.get(position).getCreatedAt());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pp=position;
				Message msg=Staffmanagr.myHandler.obtainMessage();
				msg.what=1;
				msg.sendToTarget();
				
			}
		});
		holdel.tv_delect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pp=position;
				Message msg=Staffmanagr.myHandler.obtainMessage();
				msg.what=2;
				msg.sendToTarget();
				
			}
		});
		return convertView;
	}
	public static class ViewHoldel{
		TextView tv_loginid;
		TextView tv_name;
		TextView tv_time;
		TextView tv_delect;
	}
}
