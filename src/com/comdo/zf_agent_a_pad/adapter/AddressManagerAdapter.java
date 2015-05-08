package com.comdo.zf_agent_a_pad.adapter;

import java.util.List;

import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.fragment.Mine_adress;
import com.example.zf_agent_a_pad.R;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressManagerAdapter extends BaseAdapter {
	private List<AddressManager> dataadress;
	private Context context;
	private LayoutInflater mInflater;
	public static int pp;

	public AddressManagerAdapter(List<AddressManager> dataadress,
			Context context) {
		super();
		this.dataadress = dataadress;
		this.context = context;
	}

	@Override
	public int getCount() {
		return dataadress.size();
	}

	@Override
	public Object getItem(int position) {
		return dataadress.get(position);
	}

	@Override
	public long getItemId(int position) {
		return dataadress.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHoldel holdel;
		if (convertView == null) {
			mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.manageradressitem, null);
			holdel = new ViewHoldel();
			holdel.consignee = (TextView) convertView
					.findViewById(R.id.consignee);
			holdel.area = (TextView) convertView.findViewById(R.id.area);
			holdel.detailadress = (TextView) convertView
					.findViewById(R.id.detailadress);
			holdel.zipcode = (TextView) convertView.findViewById(R.id.zipcode);
			holdel.phone = (TextView) convertView.findViewById(R.id.phone);
			holdel.defau = (TextView) convertView.findViewById(R.id.defau);
			holdel.change = (TextView) convertView.findViewById(R.id.change);
//			holdel.delect = (TextView) convertView.findViewById(R.id.delect);
			convertView.setTag(holdel);
		} else {
			holdel = (ViewHoldel) convertView.getTag();
		}
		holdel.consignee.setText(dataadress.get(position).getReceiver());
		holdel.area.setText(dataadress.get(position).getCity());
		holdel.detailadress.setText(dataadress.get(position).getAddress());
		holdel.zipcode.setText(dataadress.get(position).getZipCode());
		holdel.phone.setText(dataadress.get(position).getMoblephone());
		holdel.defau.setText(dataadress.get(position).getIsDefault());
		holdel.change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pp = position;
				Message msg = Mine_adress.myHandler.obtainMessage();
				msg.what = 2;
				msg.sendToTarget();

			}
		});
//		holdel.delect.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				pp = position;
//				Message msg = Mine_adress.myHandler.obtainMessage();
//				msg.what = 3;
//				msg.sendToTarget();
//
//			}
//		});
		return convertView;
	}

	public static class ViewHoldel {
		TextView consignee;
		TextView area;
		TextView detailadress;
		TextView zipcode;
		TextView phone;
		TextView defau;
		TextView change;
//		TextView delect;
	}
}
