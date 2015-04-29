package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.comdo.zf_agent_a_pad.activity.TerminalSelectActivity;
import com.comdo.zf_agent_a_pad.adapter.AddressManagerAdapter.ViewHoldel;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.TerminalList;
import com.comdo.zf_agent_a_pad.entity.TerminalPriceEntity;
import com.example.zf_agent_a_pad.R;

public class TerminalListagainAdapter extends BaseAdapter{
	private List<TerminalList> mTerminalItems;
	private Context context;
	private LayoutInflater mInflater;
	public TerminalListagainAdapter(List<TerminalList> mTerminalItems,
			Context context){
		super();
		this.mTerminalItems=mTerminalItems;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTerminalItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTerminalItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mTerminalItems.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;;
		if(convertView == null){
			mInflater=LayoutInflater.from(context);
			convertView=mInflater.inflate(R.layout.terminal_select_listitem, null);
			holder=new ViewHolder();
			holder.terminal_num = (TextView) convertView
					.findViewById(R.id.terminal_num);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.terminal_num.setText(mTerminalItems.get(position).getSerial_num());
		holder.price.setText(mTerminalItems.get(position).getMoney()+"");
		//final TerminalPriceEntity item = getItem(position);
		holder.checkbox.setChecked(TerminalSelectActivity.allCheck);
		holder.checkbox
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					TerminalSelectActivity.checked++;
					mTerminalItems.get(position).setCheck(true);
				} else {
					TerminalSelectActivity.checked--;
					mTerminalItems.get(position).setCheck(false);
				}
				Message msg=TerminalSelectActivity.myHandler.obtainMessage();
				msg.what=0;
				msg.sendToTarget();
				//terminalNum.setText(TerminalSelectActivity.checked+"");
				//item.setIsCheck(isChecked);
			}
		});
		return convertView;
	}
	public static class ViewHolder {
		public TextView terminal_num;
		public TextView price;
		public CheckBox checkbox;
	}
}
