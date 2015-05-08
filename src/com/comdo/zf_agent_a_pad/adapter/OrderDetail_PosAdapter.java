package com.comdo.zf_agent_a_pad.adapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.comdo.zf_agent_a_pad.activity.OrderList;
import com.comdo.zf_agent_a_pad.entity.Goodlist;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.example.zf_agent_a_pad.R;

import android.R.integer;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderDetail_PosAdapter extends BaseAdapter {
	private Context context;
	private List<Goodlist> list;
	private LayoutInflater inflater;
	private int state;
	private ViewHolder holder = null;
	private DecimalFormat df;

	public OrderDetail_PosAdapter(Context context, List<Goodlist> list,
			int state) {
		this.context = context;
		this.list = list;
		this.state = state;
		df = (DecimalFormat) NumberFormat
				.getInstance();
		df.applyPattern("0.00");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.order_detail_positem, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.content_pp);
			holder.content2 = (TextView) convertView
					.findViewById(R.id.content2);
			holder.btn_ishow = (Button) convertView
					.findViewById(R.id.btn_ishow);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_x = (TextView) convertView.findViewById(R.id.tv_x);
			holder.chanel = (TextView) convertView.findViewById(R.id.chanel);
			holder.im = (ImageView) convertView.findViewById(R.id.evevt_img);
			holder.pg_price=(TextView)convertView.findViewById(R.id.pg_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content2.setText(list.get(position).getGood_brand());
		holder.content.setText(list.get(position).getGood_name());
		if(OrderList.type.equals("5")){
			String string=" 原价:￥"+df.format(Double.parseDouble(list.get(position).getGood_price())*1.0f/100);
			SpannableString sp = new SpannableString(string);
			sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.pg_price.setText(sp);
			holder.tv_price.setText("￥ "
					+ df.format(( list.get(position).getGood_actualprice())*1.0f / 100));
		}else{
			holder.tv_price.setText("￥ "
					+ ((double) Integer.parseInt(list.get(position).getGood_price()))*1.0f / 100);
			
		}
		holder.tv_x.setText("X"+list.get(position).getGood_num() + "");
		holder.btn_ishow.setVisibility(state == 3 ? View.GONE : View.GONE);
		ImageCacheUtil.IMAGE_CACHE.get(list.get(position).getGood_logo(),
				holder.im);
		holder.chanel.setText(list.get(position).getGood_channel());
		return convertView;
	}

	public final class ViewHolder {
		public TextView content, tv_price, tv_x, content2,chanel,pg_price;
		public Button btn_ishow;
		public ImageView im;
	}

}
