package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.AfterSaleEntity;
import com.epalmpay.agentPad.R;

public class AfterSaleAdapter extends BaseAdapter{

	private List<AfterSaleEntity> lists;
	private LayoutInflater inflater;
	private int mRecordType = 0;
	private Context mContext;
	private OnClickListener onClickListener;
	private ViewHolder viewHolder;

	public AfterSaleAdapter(Context context, List<AfterSaleEntity> list,int mRecordType,
			OnClickListener onClickListener) {
		this.lists = list;
		this.mContext = context;
		this.onClickListener = onClickListener;
		this.mRecordType = mRecordType;
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
			convertView = inflater.inflate(R.layout.item_list_aftersale, parent,
					false);
			viewHolder.update_data = (TextView) convertView.findViewById(R.id.update_data);
			viewHolder.terminal_no = (TextView) convertView.findViewById(R.id.terminal_no);
			viewHolder.apply_date = (TextView) convertView.findViewById(R.id.apply_date);
			viewHolder.update_state = (TextView) convertView.findViewById(R.id.update_state);
			viewHolder.cancel_apply = (Button) convertView.findViewById(R.id.cancel_apply);
			viewHolder.cancel_again = (Button) convertView.findViewById(R.id.cancel_again);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.update_data.setText(lists.get(position).getApply_num());
		viewHolder.terminal_no.setText(lists.get(position).getTerminal_num());
		viewHolder.apply_date.setText(lists.get(position).getCreate_time());

	
		if (mRecordType == 0) {
			//售后单
			if (lists.get(position).getStatus().equals("1")) {
				viewHolder.update_state.setText("待处理");

				viewHolder.cancel_apply.setVisibility(View.VISIBLE);
				viewHolder.cancel_again.setVisibility(View.GONE);
			}else if (lists.get(position).getStatus().equals("2")) {
				viewHolder.update_state.setText("处理中");

				viewHolder.cancel_again.setText("提交物流信息");
				viewHolder.cancel_again.setVisibility(View.VISIBLE);
				viewHolder.cancel_apply.setVisibility(View.GONE);
			}else if (lists.get(position).getStatus().equals("3")) {
				viewHolder.update_state.setText("处理完成");
			}else if (lists.get(position).getStatus().equals("4")) {
				viewHolder.update_state.setText("已取消");
				viewHolder.cancel_apply.setVisibility(View.GONE);
			}
		}else {
			//注销、更新资料
			if (lists.get(position).getStatus().equals("1")) {
				viewHolder.update_state.setText("待处理");

				viewHolder.cancel_apply.setVisibility(View.VISIBLE);
				viewHolder.cancel_again.setVisibility(View.GONE);
			}else if (lists.get(position).getStatus().equals("2")) {
				viewHolder.update_state.setText("处理中");
			}else if (lists.get(position).getStatus().equals("4")) {
				viewHolder.update_state.setText("处理完成");
			}else if (lists.get(position).getStatus().equals("5")) {
				viewHolder.update_state.setText("已取消");
				if (mRecordType == 1) {
					viewHolder.cancel_again.setVisibility(View.VISIBLE);
					viewHolder.cancel_apply.setVisibility(View.GONE);
				}else {
					viewHolder.cancel_again.setVisibility(View.GONE);
					viewHolder.cancel_apply.setVisibility(View.GONE);
				}
			}
		}

		viewHolder.cancel_apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onCancelApply(position,mRecordType);
			}

		});
		viewHolder.cancel_again.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onCancelAgain(position,mRecordType);
			}

		});
		return convertView;
	}

	static class ViewHolder {
		TextView update_data;
		TextView terminal_no;
		TextView apply_date;
		TextView update_state;
		Button cancel_apply;
		Button cancel_again;
	}
	public interface OnClickListener {
		public void onCancelAgain(int position,int mRecordType);
		public void onCancelApply(int position,int mRecordType);
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
