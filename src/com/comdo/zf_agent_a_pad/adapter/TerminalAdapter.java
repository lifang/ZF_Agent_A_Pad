package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;
import com.comdo.zf_agent_a_pad.entity.TerminalEntity;
import com.epalmpay.agentPad.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TerminalAdapter extends BaseAdapter {
	private List<TerminalEntity> datatermin;
	private Context context;
	private LayoutInflater mInflater;

	public TerminalAdapter(List<TerminalEntity> datatermin, Context context) {
		super();
		this.datatermin = datatermin;
		this.context = context;
	}

	@Override
	public int getCount() {
		return datatermin.size();
	}

	@Override
	public Object getItem(int position) {
		return datatermin.get(position);
	}

	@Override
	public long getItemId(int position) {
		return datatermin.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoldel holdel;
		if (convertView == null) {
			mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.terminalitem, null);
			holdel = new ViewHoldel();
			holdel.tv_terminal = (TextView) convertView
					.findViewById(R.id.tv_terminal);
			convertView.setTag(holdel);
		} else {
			holdel = (ViewHoldel) convertView.getTag();
		}
		holdel.tv_terminal.setText(datatermin.get(position).getNumber());
		return convertView;
	}

	public static class ViewHoldel {
		TextView tv_terminal;
	}
}
