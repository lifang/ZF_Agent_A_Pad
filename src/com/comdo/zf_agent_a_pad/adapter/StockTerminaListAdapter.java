package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.StockTerminalEntity;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.example.zf_agent_a_pad.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class StockTerminaListAdapter extends BaseAdapter{

	private List<StockTerminalEntity> lists;
	private LayoutInflater inflater;
	private Context mContext;
	private ViewHolder viewHolder;
	DisplayImageOptions options = MyApplication.getDisplayOption();

	public StockTerminaListAdapter(Context context, List<StockTerminalEntity> list) {
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
		viewHolder.company_name.setVisibility(View.GONE);
		viewHolder.hoitoryCount.setVisibility(View.GONE);
		
		if (!StringUtil.isNull(lists.get(position).getSerial_num())) 
			viewHolder.openCount.setText(lists.get(position).getSerial_num());
		else
			viewHolder.openCount.setText("-");
		
		viewHolder.lastPrepareTime.setText(lists.get(position).getGood_brand()+
				lists.get(position).getModel_number());

		if (lists.get(position).getStatus().equals("1")) {
			viewHolder.lastOpenTime.setText("已开通");
		}else if (lists.get(position).getStatus().equals("2")) {
			viewHolder.lastOpenTime.setText("部分开通");
		}else if (lists.get(position).getStatus().equals("3")) {
			viewHolder.lastOpenTime.setText("未开通");
		}else if (lists.get(position).getStatus().equals("4")) {
			viewHolder.lastOpenTime.setText("已注销");
		}else if (lists.get(position).getStatus().equals("5")) {
			viewHolder.lastOpenTime.setText("以停用");
		}

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
