package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;
import com.comdo.zf_agent_a_pad.entity.DistributeEntity;
import com.comdo.zf_agent_a_pad.fragment.Distribute;
import com.example.zf_agent_a_pad.R;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DistributeAdapter extends BaseAdapter{
	private List<DistributeEntity> datadistribut;
	private Context context;
	private LayoutInflater mInflater;
	public static int pp;
	public DistributeAdapter(List<DistributeEntity> datadistribut,Context context){
		super();
		this.datadistribut=datadistribut;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datadistribut.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datadistribut.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return datadistribut.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.distriitem, null);
			holdel=new ViewHoldel();
			holdel.tv_with=(TextView) convertView.findViewById(R.id.tv_with);
			holdel.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holdel.tv_amount=(TextView) convertView.findViewById(R.id.tv_amount);
			convertView.setTag(holdel);
			}
		else{
			holdel=(ViewHoldel) convertView.getTag();
		}
		holdel.tv_with.setText(datadistribut.get(position).getCompany_name());
		holdel.tv_time.setText(datadistribut.get(position).getCreated_at());
		holdel.tv_amount.setText(datadistribut.get(position).getQuantity());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pp=position;
				Message msg=Distribute.myHandler.obtainMessage();
				msg.what=0;
				msg.sendToTarget();
				
			}
		});
		return convertView;
	}
	public static class ViewHoldel{
		TextView tv_with;
		TextView tv_time;
		TextView tv_amount;
	}
}

