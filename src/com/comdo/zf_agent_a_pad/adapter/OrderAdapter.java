package com.comdo.zf_agent_a_pad.adapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.GoodDeatail;
import com.comdo.zf_agent_a_pad.activity.OrderDetail;
import com.comdo.zf_agent_a_pad.activity.OrderList;
import com.comdo.zf_agent_a_pad.activity.PayFromCar;
import com.comdo.zf_agent_a_pad.entity.OrderEntity;
import com.comdo.zf_agent_a_pad.util.AlertDialog;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.PayAlertDialog;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OrderAdapter extends BaseAdapter{
	private Context context;
	private List<OrderEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	private OrderList dd;
	private int type;
	private DecimalFormat df;
	public OrderAdapter(Context context, List<OrderEntity> list,OrderList dd) {
		this.context = context;
		this.list = list;
		this.dd=dd;
		df = (DecimalFormat)NumberFormat.getInstance();
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
 		if(convertView == null){
			holder = new ViewHolder();
 			convertView = inflater.inflate(R.layout.order_item, null);
 			holder.isshow=(TextView)convertView.findViewById(R.id.tv_isshow);
 			
 			holder.content = (TextView) convertView.findViewById(R.id.content_pp);
 		holder.tv_ddbh = (TextView) convertView.findViewById(R.id.tv_ddbh);		 
 		holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);		
 		holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);	
 		holder.btn_comment=(Button)convertView.findViewById(R.id.btn_comment);
 		holder.ll_ishow = (LinearLayout) convertView.findViewById(R.id.ll_ishow);
 	
 		holder.tv_sum = (TextView) convertView.findViewById(R.id.tv_sum);		
 		holder.tv_psf = (TextView) convertView.findViewById(R.id.tv_psf);		
 		holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);		
 		
 		holder.tv_gtd = (TextView) convertView.findViewById(R.id.tv_gtd);
 		holder.content2 = (TextView) convertView.findViewById(R.id.content2);
 		holder.content_pp = (TextView) convertView.findViewById(R.id.content_pp);
 		holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
 		holder.tv_goodnum = (TextView) convertView.findViewById(R.id.tv_goodnum);
		holder.btn_cancle= (Button) convertView.findViewById(R.id.btn_cancle);
 		holder.btn_pay= (Button) convertView.findViewById(R.id.btn_pay);
 		holder.btn_pay.setId(position);
 		holder.im=(ImageView)convertView.findViewById(R.id.evevt_img);
 		holder.tv_yf=(TextView)convertView.findViewById(R.id.tv_yf);
 		holder.tv_sy=(TextView)convertView.findViewById(R.id.tv_sy);
 		holder.tv_yfh=(TextView)convertView.findViewById(R.id.tv_yfh);
 		holder.tv_hj=(TextView)convertView.findViewById(R.id.tv_hj);
 		holder.ll_pg=(LinearLayout)convertView.findViewById(R.id.ll_pg);
 		holder.ll_dg=(LinearLayout)convertView.findViewById(R.id.ll_dg);
 		//holder.btn_cancle.setTag(list.get(position).getOrder_id());
 		holder.btn_cancle.setId(Integer.parseInt(list.get(position).getOrder_id()));
 	
 		convertView.setId(position);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}	
 		if(OrderList.type.equals("5")){
 			holder.isshow.setVisibility(View.VISIBLE);
 			holder.ll_pg.setVisibility(View.VISIBLE);
 			holder.ll_dg.setVisibility(View.GONE);
 			holder.tv_yf.setText("已付定金：￥"+df.format(Integer.parseInt(list.get(position).getZhifu_dingjin())*1.0f/100));
 			holder.tv_sy.setText("剩余金额：￥"+df.format(Integer.parseInt(list.get(position).getShengyu_price())*1.0f/100));
 			holder.tv_yfh.setText("已发货数量："+list.get(position).getShipped_quantity());
 			holder.tv_hj.setText("合计：￥"+df.format(Integer.parseInt(list.get(position).getActual_price())*1.0f/100));
 		}else{
 			holder.tv_pay.setText("实付：￥"+df.format(Integer.parseInt(list.get(position).getOrder_totalPrice())*1.0f/100));
 	 		holder.tv_psf.setText("配送费：￥"+list.get(position).getOrder_psf()	);
 			holder.isshow.setVisibility(View.GONE);
 			holder.ll_pg.setVisibility(View.GONE);
 			holder.ll_dg.setVisibility(View.VISIBLE);
 		}
 		if(OrderList.type.equals("5")){
 			holder.btn_pay.setText("支付订金");
 		}else{
 			holder.btn_pay.setText("付款");
 		}
 		if(list.get(position).getOrder_goodsList().size()!=0){
 		
 		 ImageCacheUtil.IMAGE_CACHE.get(list.get(position).getOrder_goodsList().get(0).getGood_logo(), holder.im);
 		 if(OrderList.type.equals("5")){
 			holder.isshow.setVisibility(View.VISIBLE);
 			if (!StringUtil.isNull(list.get(position).getOrder_goodsList().get(0).getGood_price())) {
 				String string=" 原价:￥"+(double)Integer.parseInt(list.get(position).getOrder_goodsList().get(0).getGood_price())*1.0f/100+" ";
 				SpannableString sp = new SpannableString(string);
 				sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
 				holder.isshow.setText(sp);
			}else {
				holder.isshow.setText(" 原价:");
			}
 		 }
 		if(!StringUtil.isNull(list.get(position).getOrder_goodsList().get(0).getGood_id())){
 			holder.btn_comment.setId(Integer.parseInt(list.get(position).getOrder_goodsList().get(0).getGood_id()));
 			//holder.btn_comment.setId(position);
 		}else{
 			holder.btn_comment.setId(-1);
 		}
 		
 		holder.content2.setText(list.get(position).getOrder_goodsList().get(0).getGood_brand());
 		holder.tv_gtd.setText(list.get(position).getOrder_goodsList().get(0).getGood_channel());
 		holder.content_pp.setText(list.get(position).getOrder_goodsList().get(0).getGood_name()); 		 
 		holder.tv_goodnum.setText(list.get(position).getOrder_goodsList().get(0).getGood_num().equals("")?
 				"":"X   "+list.get(position).getOrder_goodsList().get(0).getGood_num());
 		}
 	
 		
 		holder.tv_ddbh.setText("订单编号: "+list.get(position).getOrder_number()	);
 		holder.tv_time.setText(list.get(position).getOrder_createTime()	);
 		
 		if(OrderList.type.equals("5")){
 			holder.tv_price.setText("￥"+list.get(position).getOrder_goodsList().get(0).getGood_actualprice()*1.0f/100);
 			 holder.tv_sum.setText(list.get(position).getOrder_goodsList().get(0).getGood_num().equals("")?"":"共计:   "+list.get(position).getOrder_goodsList().get(0).getGood_num()	+"件");
 		}else{
 			holder.tv_price.setText("￥"+Integer.parseInt(list.get(position).getOrder_goodsList().get(0).getGood_price())*1.0f/100);
 			holder.tv_sum.setText("归属用户:   "+list.get(position).getGuishu_user());
 		}
 		
 		switch (list.get(position).getOrder_status()) {
		case 1:
			holder.tv_status.setText("未付款");
			holder.ll_ishow.setVisibility(View.VISIBLE);
			holder.btn_comment.setVisibility(View.GONE);
			break;
		case 2:
			if(OrderList.type.equals("5")){
				holder.tv_status.setText("已付订金");
				holder.btn_pay.setText("付款");
				holder.ll_ishow.setVisibility(View.VISIBLE);
			}else{
				holder.tv_status.setText("已付款");
				holder.ll_ishow.setVisibility(View.GONE);
			}
		
			break;
		case 3:
			if(OrderList.type.equals("5")){
				holder.tv_status.setText("已完成");
				holder.ll_ishow.setVisibility(View.GONE);
			}else{
				holder.tv_status.setText("已发货");
				holder.ll_ishow.setVisibility(View.GONE);
			}

			break;
		case 4:
			holder.tv_status.setText("已评价");
			holder.ll_ishow.setVisibility(View.GONE);
			break;
		case 5:
			holder.tv_status.setText("已取消");
			holder.ll_ishow.setVisibility(View.GONE);
			holder.btn_comment.setVisibility(View.VISIBLE);
			if(OrderList.type.equals("5")){
				holder.btn_comment.setText("再次批购");
				
			}else{
				holder.btn_comment.setText("再次代购");
				
			}
			
			break;
		case 6:
			holder.tv_status.setText("交易关闭");
			holder.ll_ishow.setVisibility(View.GONE);
			break;
		default:
		 
			holder.ll_ishow.setVisibility(View.GONE);
			break;
		}
 	 
 		holder.tv_ddbh.setText("订单编号: "+list.get(position).getOrder_number()	);
 		holder.tv_ddbh.setText("订单编号: "+list.get(position).getOrder_number()	);
 		holder.tv_ddbh.setText("订单编号: "+list.get(position).getOrder_number()	);
	holder.btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				final AlertDialog ad = new AlertDialog(context);
				ad.setTitle("提示");
				ad.setMessage("确认取消?");
				ad.setPositiveButton("取消", new OnClickListener() {				
					@Override
					public void onClick(View arg0) {
						ad.dismiss();				
					}
				});
				ad.setNegativeButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(final View arg0) {
						ad.dismiss();
						RequestParams params = new RequestParams();
						params.put("id", v.getId());
						 System.out.println("`getTag``"+v.getId());
						 
						params.setUseJsonStreamer(true);

						MyApplication.getInstance().getClient()
								.post(Config.ORDERDELTE, params, new AsyncHttpResponseHandler() {

									@Override
									public void onSuccess(int statusCode, Header[] headers,
											byte[] responseBody) {
										String responseMsg = new String(responseBody)
												.toString();
										Log.e("print", responseMsg);			 
										Gson gson = new Gson();								
										JSONObject jsonobject = null;
										String code = null;
										try {
											jsonobject = new JSONObject(responseMsg);
											code = jsonobject.getString("code");
											int a =jsonobject.getInt("code");
											if(a==Config.CODE){  
												//String res =jsonobject.getString("result");
												//jsonobject = new JSONObject(res);
												//holder.isshow.setVisibility(View.GONE);
												//list.get((Integer) arg0.getTag()).setOrder_status(6);
												//OrderAdapter.this.notifyDataSetChanged();
												dd.DataChange();
											}else{
												code = jsonobject.getString("message");
												Toast.makeText(context, code, 1000).show();
											}
										} catch (JSONException e) {
											 ;	
											e.printStackTrace();									
										}
									}
									@Override
									public void onFailure(int statusCode, Header[] headers,
											byte[] responseBody, Throwable error) {
										System.out.println("-onFailure---");
										Log.e("print", "-onFailure---" + error);
									}
								});	
					}
				});
				
			}
		});
	holder.btn_comment.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//Toast.makeText(context, v.getId()+"", 1000).show();	
			if(v.getId()!=-1){
				Intent i=new Intent(context,GoodDeatail.class);
				i.putExtra("id", v.getId());
							
				context.startActivity(i);
			}else{
				Toast.makeText(context, "商品id为空!", 1000).show();	
			}
			
			
		}
	});
 		holder.btn_pay.setOnClickListener(new OnClickListener() {
			
			private PayAlertDialog ad;
			private Intent i;

			@Override
			public void onClick(final View v) {
				i = new Intent(context,PayFromCar.class);
				if(((Button)v).getText().equals("付款")&&OrderList.type.equals("5")){
					ad = new PayAlertDialog(context);   
					ad.setTitle("付款");				
					ad.setPositiveButton("取消", new OnClickListener() {				
						@Override
						public void onClick(View arg0) {
							ad.dismiss();				
						}
					});
					ad.setNegativeButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							String pay=ad.getPay();
							
							ad.dismiss();
							try {
								i.putExtra("orderId",Integer.parseInt(list.get(v.getId()).getOrder_id()) );
								i.putExtra("type", Integer.parseInt(OrderList.type));
								i.putExtra("pay",pay);
								Toast.makeText(context, pay, 1000).show();
							} catch (Exception e) {
								
							}								
							context.startActivity(i);					
						}
					});		
				}else{
					try {
						i.putExtra("orderId",Integer.parseInt(list.get(v.getId()).getOrder_id()) );
						i.putExtra("type", Integer.parseInt(OrderList.type));
					} catch (Exception e) {
						
					}								
					context.startActivity(i);
				}
				
			}
		});
 		convertView.setId(position);
		convertView.setOnClickListener(new OnClickListener() {			
			private int typeint;

			@Override
			public void onClick(View v) {
				int index=v.getId();
				Intent i = new Intent(context, OrderDetail.class);
				i.putExtra("status", list.get(index).getOrder_status());
				try {
					i.putExtra("id", Integer.parseInt(list.get(index).getOrder_id()));
				} catch (Exception e) {
					
				}
				
				/*if(list.get(index).getOrder_goodsList().get(0).getGood_id().equals("")){
					i.putExtra("goodid", list.get(index).getOrder_goodsList().get(0).getGood_id());
				}*/	
				i.putExtra("type",Integer.parseInt(OrderList.type));
				context.startActivity(i);
			}
		});
		
		return convertView;
	}

	public  class ViewHolder {
		public TextView tv_goodnum,tv_price,content,tv_ddbh,tv_time,tv_status,tv_sum,tv_psf,tv_pay,tv_gtd,content2,content_pp,isshow,tv_yf,tv_sy,tv_hj,tv_yfh;
		private LinearLayout ll_ishow,ll_pg,ll_dg;
		public Button btn_cancle,btn_pay,btn_comment;
		public ImageView im;
	}
	double check(String str) {
		try {

			double min = Double.valueOf(str);// 把字符串强制转换为数字
			return min;// 如果是数字，返回True
		} catch (Exception e) {
			
			return 0;// 如果抛出异常，返回False
		}
	}
}