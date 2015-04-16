package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.UserMDEntity;
import com.example.zf_agent_a_pad.R;


public class UserMDAdapter extends BaseAdapter{
	private Context context;
	private List<UserMDEntity> lists;
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	public UserMDAdapter(Context context, List<UserMDEntity> list) {
		this.context = context;
		this.lists = list;
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
			convertView = inflater.inflate(R.layout.item_list_user_mdetail, parent,
					false);
			viewHolder.nameTextViews = (TextView) convertView.findViewById(R.id.nameTextViews);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.nameTextViews.setText(lists.get(position).getSerial_num());
		
		return convertView;
	}
	static class ViewHolder {
		TextView nameTextViews;
	}
}
