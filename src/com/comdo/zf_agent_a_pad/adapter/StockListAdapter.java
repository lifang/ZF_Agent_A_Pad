package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.StockEntity;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.example.zf_agent_a_pad.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StockListAdapter extends BaseAdapter{

	private List<StockEntity> lists;
	private LayoutInflater inflater;
	private Context mContext;
	private OnClickListener onClickListener;
	private ViewHolder viewHolder;
	DisplayImageOptions options = MyApplication.getDisplayOption();
	
	public StockListAdapter(Context context, List<StockEntity> list,
			OnClickListener onClickListener) {
		this.lists = list;
		this.mContext = context;
		this.onClickListener = onClickListener;
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
			convertView = inflater.inflate(R.layout.item_list_stocklist, parent,
					false);
			viewHolder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.brandNum = (TextView) convertView.findViewById(R.id.brandNum);
			viewHolder.paychannel = (TextView) convertView.findViewById(R.id.paychannel);
			viewHolder.hoitoryCount = (TextView) convertView.findViewById(R.id.hoitoryCount);
			viewHolder.openCount = (TextView) convertView.findViewById(R.id.openCount);
			viewHolder.agentCount = (TextView) convertView.findViewById(R.id.agentCount);
			viewHolder.totalCount = (TextView) convertView.findViewById(R.id.totalCount);
			viewHolder.changeName = (Button) convertView.findViewById(R.id.changeName);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(lists.get(position).getPicurl(), viewHolder.evevt_img,options);
		
		viewHolder.title.setText(lists.get(position).getGoodname()+"");
		viewHolder.brandNum.setText(lists.get(position).getGood_brand() +
				lists.get(position).getModel_number());
		viewHolder.paychannel.setText(lists.get(position).getPaychannel()+"");
		
		viewHolder.hoitoryCount.setText(lists.get(position).getHoitoryCount()+"");
		viewHolder.openCount.setText(lists.get(position).getOpenCount()+"");
		viewHolder.agentCount.setText(lists.get(position).getAgentCount()+"");
		viewHolder.totalCount.setText(lists.get(position).getTotalCount()+"");

		viewHolder.changeName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onChangeName(position);
			}

		});
		return convertView;
	}

	static class ViewHolder {
		ImageView evevt_img;
		TextView title;
		TextView brandNum;
		TextView paychannel;
		TextView hoitoryCount;

		TextView openCount;
		TextView agentCount;
		TextView totalCount;
		Button changeName;
	}
	public interface OnClickListener {
		public void onChangeName(int position);
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
