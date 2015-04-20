package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;
import com.comdo.zf_agent_a_pad.entity.TransgoodsEntity;
import com.comdo.zf_agent_a_pad.fragment.Transgoods;
import com.example.zf_agent_a_pad.R;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransgoodsAdapter extends BaseAdapter{

	private List<TransgoodsEntity> datatrans;
	private Context context;
	private LayoutInflater mInflater;
	public TransgoodsAdapter(List<TransgoodsEntity> datatrans,Context context){
		super();
		this.datatrans=datatrans;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datatrans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datatrans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return datatrans.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.distriitem, null);
			holdel=new ViewHoldel();
			holdel.tv_transferobject=(TextView) convertView.findViewById(R.id.tv_with);
			holdel.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holdel.tv_amount=(TextView) convertView.findViewById(R.id.tv_amount);
			convertView.setTag(holdel);
			}
		else{
			holdel=(ViewHoldel) convertView.getTag();
		}
		holdel.tv_transferobject.setText(datatrans.get(position).getTransferobject());
		holdel.tv_time.setText(datatrans.get(position).getTime());
		holdel.tv_amount.setText(datatrans.get(position).getAmount());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg=Transgoods.myHandler.obtainMessage();
				msg.what=0;
				msg.sendToTarget();
				
			}
		});
		return convertView;
	}
	public static class ViewHoldel{
		TextView tv_transferobject;
		TextView tv_time;
		TextView tv_amount;
	}

}
