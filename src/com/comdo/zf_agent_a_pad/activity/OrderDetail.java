package com.comdo.zf_agent_a_pad.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import android.R.integer;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.OrderDetail_PosAdapter;
import com.comdo.zf_agent_a_pad.adapter.RecordAdapter;
import com.comdo.zf_agent_a_pad.common.DialogUtil;
import com.comdo.zf_agent_a_pad.entity.Goodlist;
import com.comdo.zf_agent_a_pad.entity.MarkEntity;
import com.comdo.zf_agent_a_pad.entity.OrderDetailEntity;
import com.comdo.zf_agent_a_pad.entity.OrderDetailPG;
import com.comdo.zf_agent_a_pad.util.AlertDialog;
import com.comdo.zf_agent_a_pad.util.AlertMessDialog;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.PayAlertDialog;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OrderDetail extends BaseActivity implements OnClickListener {
	private ScrollViewWithListView pos_lv, his_lv;
	List<Goodlist> goodlist = new ArrayList<Goodlist>();
	List<MarkEntity> relist = new ArrayList<MarkEntity>();
	private OrderDetail_PosAdapter posAdapter;
	private RecordAdapter reAdapter;
	private LinearLayout ll_ishow;
	private Button btn_ishow;
	private OrderDetailEntity ode;
	 private Dialog loadingDialog;
	private TextView tv_status, tv_sjps, tv_psf, tv_reperson, tv_tel,
			tv_adress, tv_ly, tv_fplx, fptt, tv_ddbh, tv_pay, tv_time, tv_gj,
			tv_money;
	private int status, id;
	private OrderDetailEntity entity;
	private Handler handler = new Handler() {
		

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				entity = ode;
				tv_sjps.setText("实际配送金额(含配送费) ：￥ "
						+ check(entity.getOrder_totalPrice()) / 100);
				//tv_psf.setText("配送费 ：￥ " + entity.getOrder_psf());
				tv_reperson.setText("收件人  ：   " + entity.getOrder_receiver());
				tv_tel.setText(entity.getOrder_receiver_phone());
				tv_adress.setText("收货地址  ：   " + entity.getOrder_address());
				tv_ly.setText("留言  ：   " + entity.getOrder_comment());
				tv_fplx.setText(entity.getOrder_invoce_type().equals("1") ? "发票类型 : 个人"
						: "发票类型 : 公司");
				fptt.setText("发票抬头  ：   " + entity.getOrder_invoce_info());
				tv_ddbh.setText("订单编号  ：   " + entity.getOrder_number());
				tv_pay.setText("支付方式  ：   " + entity.getOrder_payment_type());
				tv_time.setText("实付金额  ：   ￥"
						+ check(entity.getOrder_totalPrice()) / 100);
				tv_money.setText("订单日期  ：   " + entity.getOrder_createTime());
				tv_gj.setText("共计  ：   " + entity.getTotal_quantity() + "件商品");
				if (!OrderList.type.equals("5")) {
					tv_user.setText(entity.getGuishu_user());
				}else{
					tv_yf.setText("已发货数量："+entity.getShipped_quantity());
					tv_sy.setText("剩余货品总数："+(Integer.parseInt(entity.getTotal_quantity())-Integer.parseInt(entity.getShipped_quantity())));
				}
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2:
				Toast.makeText(getApplicationContext(),
						"no 3g or wifi content", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private Button bt_pj;
	private TextView tv_price;
	private TextView tv_sl;
	private TextView tv_comment;
	private AlertMessDialog amd;
	private int type;
	private Button bt_pay;
	private Button bt_cancel;
	private String url;
	private LinearLayout ll_user;
	private TextView tv_user;
	private int goodid;
	private View line;
	private LinearLayout ll_pg;
	private TextView tv_yf;
	private TextView tv_sy;
	private PayAlertDialog ad;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		//status = getIntent().getIntExtra("status", 0);
		id = getIntent().getIntExtra("id", 0);
		type = getIntent().getIntExtra("type",5);
		//goodid = getIntent().getIntExtra("goodid", -1);
		if (type == 5) {
			new TitleMenuUtil(OrderDetail.this, "批购订单详情").show();
		} else {
			new TitleMenuUtil(OrderDetail.this, "代购订单详情").show();
		}

		getData();
	}

	private void getData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		System.out.println("id```" + id);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		
			return;
		}

		if (type==5) {
			url = Config.ORDERDETAIL;
		} else {
			url = Config.ORDERDETAIL1;
		}
		Log.i("url",url);
		MyApplication.getInstance().getClient()
				.post(getApplicationContext(),url, null,entity,"application/json", new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {

						super.onStart();
						 loadingDialog = DialogUtil.getLoadingDialg(OrderDetail.this);
						 loadingDialog.show();
					}

					@Override
					public void onFinish() {

						super.onFinish();
						loadingDialog.dismiss();
					}

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
							int a = jsonobject.getInt("code");
							if (a == Config.CODE) {
								
								String res = jsonobject.getString("result");
								// jsonobject = new JSONObject(res);
								System.out.println("````" + res);
								ode = gson.fromJson(res,
										new TypeToken<OrderDetailEntity>() {
										}.getType());
								status=ode.getOrder_status();
								initView();
								// jsonobject = new JSONObject(res);
								goodlist = ode.getOrder_goodsList();
								relist = ode.getComments().getContent();

								posAdapter = new OrderDetail_PosAdapter(
										OrderDetail.this, goodlist, status);
								pos_lv.setAdapter(posAdapter);
								if (relist == null) {
									tv_comment.setVisibility(View.GONE);
								} else {
									reAdapter = new RecordAdapter(
											OrderDetail.this, relist);
									his_lv.setAdapter(reAdapter);
								}

								handler.sendEmptyMessage(0);

							} else {
								code = jsonobject.getString("message");
								Toast.makeText(getApplicationContext(), code,
										1000).show();
							}
						} catch (JSONException e) {

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

	private void initView() {
		line = (View)findViewById(R.id.line);
		tv_user = (TextView) findViewById(R.id.tv_user);
		ll_user = (LinearLayout) findViewById(R.id.ll_user);
		if (!OrderList.type.equals("5")) {
			ll_user.setVisibility(View.VISIBLE);
			line.setVisibility(View.VISIBLE);
		}else{
			ll_pg = (LinearLayout)findViewById(R.id.ll_pg);
			ll_pg.setVisibility(View.VISIBLE);
			tv_yf = (TextView)findViewById(R.id.tv_yf);
			tv_sy = (TextView)findViewById(R.id.tv_sy);
		}
		bt_pay = (Button) findViewById(R.id.bt_pay);
		bt_pay.setOnClickListener(this);
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		bt_cancel.setOnClickListener(this);
		tv_money = (TextView) findViewById(R.id.tv_money);
		bt_pj = (Button) findViewById(R.id.btn_pj);
		bt_pj.setOnClickListener(this);
		tv_gj = (TextView) findViewById(R.id.tv_gj);
		btn_ishow = (Button) findViewById(R.id.btn_ishow);
		btn_ishow.setOnClickListener(this);
		tv_ddbh = (TextView) findViewById(R.id.tv_ddbh);
		fptt = (TextView) findViewById(R.id.fptt);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		tv_fplx = (TextView) findViewById(R.id.tv_fplx);
		tv_ly = (TextView) findViewById(R.id.tv_ly);
		tv_adress = (TextView) findViewById(R.id.tv_adress);
		tv_reperson = (TextView) findViewById(R.id.tv_reperson);
		tv_tel = (TextView) findViewById(R.id.tv_tel);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_psf = (TextView) findViewById(R.id.tv_psf);
		tv_sjps = (TextView) findViewById(R.id.tv_sjps);
		tv_status = (TextView) findViewById(R.id.tv_status);
		ll_ishow = (LinearLayout) findViewById(R.id.ll_ishow);
		ll_ishow.setVisibility(status == 1 ? View.VISIBLE : View.INVISIBLE); // 只有状态是1
																				// 才有下面的按钮
		pos_lv = (ScrollViewWithListView) findViewById(R.id.pos_lv1);
		his_lv = (ScrollViewWithListView) findViewById(R.id.his_lv);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_sl = (TextView) findViewById(R.id.tv_quantity);

		switch (status) {
		case 1:
			tv_status.setText("未付款");
			bt_pay.setText("支付订金");
			break;
		case 2:
			if (type==5) {
				
				ll_ishow.setVisibility(View.VISIBLE);
				bt_pay.setText("付款");
				tv_status.setText("已付订金");
				btn_ishow.setVisibility(View.VISIBLE);
			} else {
				
				
				tv_status.setText("已付款");
				btn_ishow.setVisibility(View.VISIBLE);
			}

			break;
		case 3:
			if (OrderList.type.equals("5")) {
				tv_status.setText("已完成");
				btn_ishow.setVisibility(View.VISIBLE);
				bt_pj.setVisibility(View.VISIBLE);
			} else {
				tv_status.setText("已发货");
				btn_ishow.setVisibility(View.VISIBLE);
				bt_pj.setVisibility(View.VISIBLE);
			}

			break;
		case 4:
			tv_status.setText("已评价");
			btn_ishow.setVisibility(View.VISIBLE);
			break;
		case 5:
			tv_status.setText("已取消");
			bt_pj.setVisibility(View.VISIBLE);
			if (OrderList.type.equals("5")) {
				bt_pj.setText("再次批购");

			} else {
				bt_pj.setText("再次代购");

			}
			break;
		case 6:

			tv_status.setText("交易关闭");
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_pj:
			if (status == 5) {
				
					Intent i = new Intent(OrderDetail.this, GoodDeatail.class);
					if(entity!=null){
						if(!StringUtil.isNull(entity.getOrder_goodsList().get(0).getGood_id()))
						i.putExtra("id", Integer.parseInt(entity.getOrder_goodsList().get(0).getGood_id()));
						startActivity(i);
					
				}

			} else if (status == 3) {
				Config.list = ode.getOrder_goodsList();
				if (Config.list.size() != 0) {
				
					startActivity(new Intent(OrderDetail.this, Comment.class));
				}

			}
		
			break;
		case R.id.btn_ishow:
			amd = new AlertMessDialog(OrderDetail.this);
			amd.setTitle("查看终端号");
			amd.setMessage(ode.getTerminals());
			amd.setNegativeButton("确认", new OnClickListener() {

				@Override
				public void onClick(View v) {
					amd.dismiss();

				}
			});
			break;
		case R.id.bt_pay:
			i = new Intent(OrderDetail.this,
					PayFromCar.class);
			if(type==5){
				if(status==2){
					ad = new PayAlertDialog(OrderDetail.this);   
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
								i.putExtra("orderId",id );
								i.putExtra("type",type);
								i.putExtra("pay",pay);
								OrderDetail.this.finish();
								OrderDetail.this.startActivity(i);
								
							/*	if(Float.parseFloat(pay)<(float)entity.getShengyu_price()){
									OrderDetail.this.finish();
									OrderDetail.this.startActivity(i);	
								}else{
									Toast.makeText(OrderDetail.this, "金额不能大于剩余金额！", 1000).show();	
								}*/
							} catch (Exception e) {
								
							}								
												
						}
					});	
				}else{
					i.putExtra("orderId",id );
					i.putExtra("type",type);
					OrderDetail.this.finish();
					startActivity(i);
				}
				
				
			}else{
				i.putExtra("orderId",id );
				i.putExtra("type",type);
				startActivity(i);
			}
			
			break;
		case R.id.bt_cancel:
			final AlertDialog ad = new AlertDialog(OrderDetail.this);
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
					params.put("id", id);
					System.out.println("`getTag``" + id);
					params.setUseJsonStreamer(true);
					MyApplication
							.getInstance()
							.getClient()
							.post(Config.ORDERDELTE, params,
									new AsyncHttpResponseHandler() {

										@Override
										public void onSuccess(int statusCode,
												Header[] headers,
												byte[] responseBody) {
											String responseMsg = new String(
													responseBody).toString();
											Log.e("print", responseMsg);
											Gson gson = new Gson();
											JSONObject jsonobject = null;
											String code = null;
											try {
												jsonobject = new JSONObject(
														responseMsg);
												code = jsonobject
														.getString("code");
												int a = jsonobject
														.getInt("code");
												if (a == Config.CODE) {
													Toast.makeText(
															OrderDetail.this,
															jsonobject
																	.getString("message"),
															1000).show();
													ll_ishow.setVisibility(View.INVISIBLE);
												} else {
													code = jsonobject
															.getString("message");
													Toast.makeText(
															OrderDetail.this,
															code, 1000).show();
												}
											} catch (JSONException e) {
												;
												e.printStackTrace();
											}
										}

										@Override
										public void onFailure(int statusCode,
												Header[] headers,
												byte[] responseBody,
												Throwable error) {
											System.out.println("-onFailure---");
											Log.e("print", "-onFailure---"
													+ error);
										}
									});
				}
			});
			break;
		default:
			break;
		}
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