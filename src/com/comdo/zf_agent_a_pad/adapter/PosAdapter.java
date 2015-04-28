package com.comdo.zf_agent_a_pad.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.comdo.zf_agent_a_pad.activity.PosListActivity;
import com.comdo.zf_agent_a_pad.entity.PosEntity;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.example.zf_agent_a_pad.R;


public class PosAdapter extends BaseAdapter {
	private Context context;
	private List<PosEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	int type=0;
	public PosAdapter(Context context, List<PosEntity> list) {
		this.context = context;
		this.list = list;
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

	public void change() {
		type=1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.pos_item, null);
			holder.tv_yj=(TextView)convertView.findViewById(R.id.tv_yj);
			holder.tv_least=(TextView)convertView.findViewById(R.id.tv_least);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.ys = (TextView) convertView.findViewById(R.id.ys);
			holder.content1 = (TextView) convertView
					.findViewById(R.id.content1);
			holder.tv_td = (TextView) convertView.findViewById(R.id.tv_td);
			holder.im=(ImageView)convertView.findViewById(R.id.evevt_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// volume_number":123,"id":2,"good_brand":"Ʒ��1","total_score":1,
		// "retail_price":72464,"pay_channe":"ͨ��2",
		// "Title":"̩ɽPos�콢��2","Model_number":"�ͺ�10"}
		ImageCacheUtil.IMAGE_CACHE.get(list.get(position).getUrl_path(),
 				holder.im);
		holder.title.setText(list.get(position).getTitle());
		holder.tv_price.setText("￥" + list.get(position).getRetail_price() / 100
				+ "");
		holder.content1.setText(list.get(position).getModel_number());
		holder.tv_td.setText(list.get(position).getPay_channe());
		if(PosListActivity.shoptype==1){
			holder.ys.setText("" + list.get(position).getPurchase_number());
		}else{
			holder.ys.setText("" + list.get(position).getVolume_number());
		}
		
		String string=" 原价:￥"+((double)list.get(position).getPurchase_price()/100)+" ";
		SpannableString sp = new SpannableString(string);
		sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.tv_yj.setText(sp);
		holder.tv_least.setText("最小起批量:"+list.get(position).getFloor_purchase_quantity());
		return convertView;
	}

	public final class ViewHolder {
		public TextView title, ys, tv_price, content1, tv_td,tv_least,tv_yj;
		public CheckBox item_cb;
		public ImageView im;

	}
}
