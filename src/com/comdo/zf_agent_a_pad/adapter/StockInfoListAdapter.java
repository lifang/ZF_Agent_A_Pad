package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.StockAgentEntity;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.example.zf_agent_a_pad.R;

public class StockInfoListAdapter extends BaseAdapter{

	private List<StockAgentEntity> lists;
	private LayoutInflater inflater;
	private Context mContext;
	private ViewHolder viewHolder;

	public StockInfoListAdapter(Context context, List<StockAgentEntity> list) {
		this.lists = list;
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_stockinfo, parent,
					false);
			viewHolder.company_name = (TextView) convertView.findViewById(R.id.company_name);
			viewHolder.hoitoryCount = (TextView) convertView.findViewById(R.id.hoitoryCount);
			viewHolder.openCount = (TextView) convertView.findViewById(R.id.openCount);
			viewHolder.lastPrepareTime = (TextView) convertView.findViewById(R.id.lastPrepareTime);
			viewHolder.lastOpenTime = (TextView) convertView.findViewById(R.id.lastOpenTime);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if (!StringUtil.isNull(lists.get(position).getCompany_name())) 
			viewHolder.company_name.setText(lists.get(position).getCompany_name()+"");
		else 
			viewHolder.company_name.setText("-");
		
		if (!StringUtil.isNull(lists.get(position).getHoitoryCount()+"")) 
			viewHolder.hoitoryCount.setText(lists.get(position).getHoitoryCount()+"");
		else 
			viewHolder.hoitoryCount.setText("-");
		
		if (!StringUtil.isNull(lists.get(position).getOpenCount()+"")) 
			viewHolder.openCount.setText(lists.get(position).getOpenCount()+"");
		else 
			viewHolder.openCount.setText("-");
		
		if (!StringUtil.isNull(lists.get(position).getLastPrepareTime()+"")) 
			viewHolder.lastPrepareTime.setText(lists.get(position).getLastPrepareTime()+"");
		else 
			viewHolder.lastPrepareTime.setText("-");
		
		if (!StringUtil.isNull(lists.get(position).getLastOpenTime()+"")) 
			viewHolder.lastOpenTime.setText(lists.get(position).getLastOpenTime()+"");
		else 
			viewHolder.lastOpenTime.setText("-");

		return convertView;
	}

	static class ViewHolder {
		TextView company_name;
		TextView hoitoryCount;
		TextView openCount;
		TextView lastPrepareTime;
		TextView lastOpenTime;
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
