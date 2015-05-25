package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.ApplyChannelagain;
import com.comdo.zf_agent_a_pad.entity.Pos;
import com.comdo.zf_agent_a_pad.trade.entity.TradeClient;
import com.epalmpay.agentPad.R;

public class SelectStateAdapter<T> extends BaseAdapter {

	private List<T> dictIndustries;

	private LayoutInflater inflater;
	private Context mContext;
	private int selectPosition = -1;

	public int getSelectPosition() {
		return selectPosition;
	}

	public void setSelectPosition(int selectPosition) {
		this.selectPosition = selectPosition;
	}

	public SelectStateAdapter(Context context, List<T> dictIndustries) {
		this.dictIndustries = dictIndustries;
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return dictIndustries.size();
	}

	@Override
	public T getItem(int position) {
		return dictIndustries.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_job_choice,
					parent, false);
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.nameTextViews);
			viewHolder.type_pop = (RelativeLayout) convertView
					.findViewById(R.id.type_pop);
			convertView.setTag(viewHolder);

		}

		viewHolder = (ViewHolder) convertView.getTag();

		if (dictIndustries.get(0) instanceof String) {
			viewHolder.nameTextView.setText(((String) dictIndustries
					.get(position)));
		}else if (dictIndustries.get(0) instanceof Pos) {
			viewHolder.nameTextView.setText(((Pos) dictIndustries
					.get(position)).getGoodname());
		}else if (dictIndustries.get(0) instanceof ApplyChannelagain) {
			viewHolder.nameTextView.setText(((ApplyChannelagain) dictIndustries
					.get(position)).getPaychannel());
		}else if (dictIndustries.get(0) instanceof TradeClient) {
			viewHolder.nameTextView.setText(((TradeClient) dictIndustries
					.get(position)).getSerialNum());
		}
		
		return convertView;
	}

	static class ViewHolder {

		TextView nameTextView;
		RelativeLayout type_pop;

	}
}
