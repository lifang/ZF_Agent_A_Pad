package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.fragment.Mwdxx;
import com.example.zf_agent_a_pad.R;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter{
	private List<MessageEntity> datamsg;
	private Context context;
	private LayoutInflater mInflater;
	public static int pp=0;
	public MessageAdapter(List<MessageEntity> datamsg,Context context){
		super();
		this.datamsg=datamsg;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datamsg.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datamsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return datamsg.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.message_item, null);
			holdel=new ViewHoldel();
			holdel.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holdel.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holdel.item_cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			convertView.setTag(holdel);
			}
		else{
			holdel=(ViewHoldel) convertView.getTag();
		}
		holdel.tv_title.setText(datamsg.get(position).getTitle());
		holdel.tv_time.setText(datamsg.get(position).getCreate_at());
		if(datamsg.get(position).isStatus()){
			holdel.item_cb.setBackgroundResource(R.drawable.cb_y);
		}
		else{
			holdel.item_cb.setBackgroundResource(R.drawable.cb_n);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pp=position;
				Message msg=Mwdxx.myHandler.obtainMessage();
				msg.what=1;
				msg.sendToTarget();
				
			}
		});
		holdel.item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean ischeck) {
				if(ischeck){
					pp=position;
					Message msg=Mwdxx.myHandler.obtainMessage();
					msg.what=2;
					msg.sendToTarget();
				}
				
			}
		});
		return convertView;
	}
	public static class ViewHoldel{
		public TextView tv_title, tv_time;
		public CheckBox item_cb;
	}

}
