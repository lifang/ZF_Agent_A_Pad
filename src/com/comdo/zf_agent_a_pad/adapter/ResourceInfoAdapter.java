package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.AfterSaleDetailCancelEntity.ResourcesInfo;
import com.epalmpay.agentPad.R;

public class ResourceInfoAdapter extends BaseAdapter{

	private List<ResourcesInfo> lists;
	private LayoutInflater inflater;
	private Context mContext;
	private ViewHolder viewHolder;

	public ResourceInfoAdapter(Context context, List<ResourcesInfo> list) {
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
			convertView = inflater.inflate(R.layout.item_resource_info, parent,
					false);
			viewHolder.nameTextViews = (TextView) convertView.findViewById(R.id.nameTextViews);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.nameTextViews.setText(lists.get(position).getTitle());
		
		return convertView;
	}

	static class ViewHolder {
		TextView nameTextViews;
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
