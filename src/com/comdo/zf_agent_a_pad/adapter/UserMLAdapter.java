package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.UserMLEntity;
import com.example.zf_agent_a_pad.R;


public class UserMLAdapter extends BaseAdapter{
    private Context context;
    private List<UserMLEntity> lists;
    private LayoutInflater inflater;
	private ViewHolder viewHolder;
	private OnClickListener onClickListener;
    public UserMLAdapter(Context context, List<UserMLEntity> list,OnClickListener onClickListener) {
        this.context = context;
        this.lists = list;
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
			convertView = inflater.inflate(R.layout.item_list_aftersale, parent,
					false);
			viewHolder.update_data = (TextView) convertView.findViewById(R.id.update_data);
			viewHolder.terminal_no = (TextView) convertView.findViewById(R.id.terminal_no);
			viewHolder.apply_date = (TextView) convertView.findViewById(R.id.apply_date);
			viewHolder.update_state = (TextView) convertView.findViewById(R.id.update_state);
			viewHolder.cancel_apply = (TextView) convertView.findViewById(R.id.cancel_apply);
			viewHolder.cancel_again = (TextView) convertView.findViewById(R.id.cancel_again);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.update_data.setText(lists.get(position).getName());
		viewHolder.terminal_no.setText(lists.get(position).getPhone());
		viewHolder.apply_date.setText(lists.get(position).getEmail());
		viewHolder.update_state.setText(lists.get(position).getCreatedAt());
		
		viewHolder.cancel_apply.setVisibility(View.VISIBLE);
		viewHolder.cancel_again.setVisibility(View.GONE);
		viewHolder.cancel_apply.setText("删除");
		
		viewHolder.cancel_apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onDeleteItem(position);
			}

		});
		return convertView;
	}
	public interface OnClickListener {
		public void onDeleteItem(int position);
	}
	static class ViewHolder {
		TextView update_data;
		TextView terminal_no;
		TextView apply_date;
		TextView update_state;
		TextView cancel_apply;
		TextView cancel_again;
	}
}
