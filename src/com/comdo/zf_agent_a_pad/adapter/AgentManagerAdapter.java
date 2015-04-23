package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import com.comdo.zf_agent_a_pad.entity.AgentManager;
import com.comdo.zf_agent_a_pad.fragment.Agentmanager;
import com.example.zf_agent_a_pad.R;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AgentManagerAdapter extends BaseAdapter{
	private List<AgentManager> dataadagent;
	private Context context;
	private LayoutInflater mInflater;
	public static int pp;
	public AgentManagerAdapter(List<AgentManager> dataadagent,Context context){
		super();
		this.dataadagent=dataadagent;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataadagent.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataadagent.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return dataadagent.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.agentitem, null);
			holdel=new ViewHoldel();
			holdel.tv_agent_type=(TextView) convertView.findViewById(R.id.tv_agent_type);
			holdel.tv_agent_name=(TextView) convertView.findViewById(R.id.tv_agent_name);
			holdel.tv_join_time=(TextView) convertView.findViewById(R.id.tv_join_time);
			convertView.setTag(holdel);
			}
		else{
			holdel=(ViewHoldel) convertView.getTag();
		}
		if(dataadagent.get(position).getTypes()==1){
			holdel.tv_agent_type.setText("公司");
		}
		else if(dataadagent.get(position).getTypes()==2){
			holdel.tv_agent_type.setText("个人");
		}
		else{
			holdel.tv_agent_type.setText(dataadagent.get(position).getTypes()+"");
		}
		holdel.tv_agent_name.setText(dataadagent.get(position).getCompany_name());
		holdel.tv_join_time.setText(dataadagent.get(position).getCreated_at());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pp=position;
				Message msg=Agentmanager.myHandler.obtainMessage();
				msg.what=1;
				msg.sendToTarget();
				
			}
		});
		return convertView;
	}
	public static class ViewHoldel{
		TextView tv_agent_type;
		TextView tv_agent_name;
		TextView tv_join_time;
	}

}
