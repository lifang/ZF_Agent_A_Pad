package com.comdo.zf_agent_a_pad.activity;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.comdo.zf_agent_a_pad.adapter.ButtonGridviewAdapter;
import com.comdo.zf_agent_a_pad.adapter.HuilvAdapter;
import com.comdo.zf_agent_a_pad.common.DialogUtil;
import com.comdo.zf_agent_a_pad.entity.ApplyneedEntity;
import com.comdo.zf_agent_a_pad.entity.ChanelEntitiy;
import com.comdo.zf_agent_a_pad.entity.FactoryEntity;
import com.comdo.zf_agent_a_pad.entity.GoodPic;
import com.comdo.zf_agent_a_pad.entity.GoodinfoEntity;
import com.comdo.zf_agent_a_pad.entity.GriviewEntity;
import com.comdo.zf_agent_a_pad.entity.PosEntity;
import com.comdo.zf_agent_a_pad.entity.other_rate;
import com.comdo.zf_agent_a_pad.entity.tDates;
import com.comdo.zf_agent_a_pad.popwindow.FactoryPopWindow;
import com.comdo.zf_agent_a_pad.trade.API;
import com.comdo.zf_agent_a_pad.trade.common.HttpRequest;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithGView;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.StringUtil;

import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

public class GoodDeatail extends FragmentActivity implements OnClickListener {
	private Button setting_btn_clear;
	private int id;
	private LinearLayout titleback_linear_back;
	private ImageView image, fac_img;
	private ArrayList<String> ma = new ArrayList<String>();
	private ViewPager view_pager;
	private MyAdapter adapter;
	private ImageView[] indicator_imgs;
	private View item;
	private LayoutInflater inflater;
	private RelativeLayout rl_imgs, rela_loc;
	private int index_ima = 0;
	private GoodinfoEntity gfe;
	private ArrayList<String> arelist = new ArrayList<String>();
	private int commentsCount = 0;
	FactoryEntity factoryEntity;
	FactoryEntity factoryEntity1;
	private TextView tv_bug, tv_lea, tv_title, content1, tv_pp, tv_xh, tv_ys,
			tv_price, tv_lx, tv_sjhttp, tv_spxx, fac_detai, ppxx, wkxx, dcxx,
			tv_qgd, tv_jm;
	private ScrollViewWithListView pos_lv1;
	private HuilvAdapter lvAdapter;
	private ArrayList<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	private ArrayList<ChanelEntitiy> celist2 = new ArrayList<ChanelEntitiy>();
	private String phoneNumber, locName;
	private int paychannelId, goodId, quantity;
	List<View> list = new ArrayList<View>();
	private ScrollViewWithGView gview, gview1;
	private ButtonGridviewAdapter buttonAdapter;
	List<GriviewEntity> User_button = new ArrayList<GriviewEntity>();
	private Boolean islea = false;
	private List<String> piclist;
	private int opening_cost;
	private String tdname;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				for (int i = 0; i < ma.size(); i++) {
					item = inflater.inflate(R.layout.item, null);
					list.add(item);
				}
				indicator_imgs = new ImageView[ma.size()];
				initIndicator();
				
				adapter.notifyDataSetChanged();
		/*		if(PosListActivity.shoptype != 1){
					setting_btn_clear.setText("代购");
					tv_dp.setText("代购价格");
					tv_j_isshow.setVisibility(View.GONE);
					tv_yj.setVisibility(View.GONE);
				}*/
				tv_title.setText(gfe.getTitle());
				content1.setText(gfe.getSecond_title());
				tv_pp.setText(gfe.getGood_brand()+gfe.getModel_number());
				//tv_price.setText("￥" + ((double) (gfe.getPurchase_price())+opening_cost) / 100);
				tv_lx.setText(gfe.getCategory());
				tv_sjhttp.setText(factoryEntity.getWebsite_url());
				fac_detai.setText(factoryEntity.getDescription());
				tv_pl.setText("评论" + "(" + commentsCount + ")");
				String string=" ￥" + df.format((double)(gfe.getPrice()+opening_cost) / 100)+" ";
				SpannableString sp = new SpannableString(string);
				sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv_yj.setText(sp);
				System.out.println("-Xlistview--" +gfe.getPurchase_price());
				if (islea == false) {
					//购买
					if(PosListActivity.shoptype != 1){
						all_price = gfe.getRetail_price()+opening_cost;
						tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
					}else{
						all_price = gfe.getRetail_price()+opening_cost;
						tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getPurchase_price()+opening_cost));
					}
					
				}else {
					//租赁
					all_price = gfe.getLease_deposit()+opening_cost;
					tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
				}
				if (PosListActivity.shoptype != 1&&gfe.isHas_lease()) {
					ll_shoptype = (LinearLayout) findViewById(R.id.ll_shoptype);
					ll_shoptype.setVisibility(View.VISIBLE);
					setting_btn_clear.setText("代购");
				}
				
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			}
		}
	};
	private Intent i;
	private LinearLayout ll_Factory;
	private TextView tv_pl;
	private LinearLayout ll_shoptype;
	private TextView tv_yj;
	private int all_price;
	private TextView tv_dp;
	private TextView tv_j_isshow;
	private DecimalFormat df;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_detail);
		id = getIntent().getIntExtra("id", 0);
		getdata();
		df = (DecimalFormat) NumberFormat
				.getInstance();
		df.applyPattern("0.00");
		System.out.println("-Xlistview--" + id);
	}

	private void innitView() {
		tv_j_isshow = (TextView)findViewById(R.id.tv_j_isshow);
		tv_dp = (TextView)findViewById(R.id.tv_dp);
		tv_yj = (TextView) findViewById(R.id.tv_yj);
		setting_btn_clear = (Button) findViewById(R.id.btn_buy);
		setting_btn_clear.setOnClickListener(this);
		tv_lea = (TextView) findViewById(R.id.tv_lea);
		tv_lea.setOnClickListener(this);
		tv_bug = (TextView) findViewById(R.id.tv_bug);
		tv_bug.setOnClickListener(this);
		gview1 = (ScrollViewWithGView) findViewById(R.id.gv1);
		rl_imgs = (RelativeLayout) findViewById(R.id.rl_imgs);
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl_imgs
				.getLayoutParams();
		linearParams.width = Config.ScreenWidth / 2;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		//linearParams.height = (int) (Config.ScreenHeight - density*180);
		linearParams.height = (int) (Config.ScreenWidth / 2);
		rl_imgs.setLayoutParams(linearParams);
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		view_pager.setAdapter(adapter);
		view_pager.setOnPageChangeListener(new MyListener());

		// pos_lv1 = (ScrollViewWithListView) findViewById(R.id.pos_lv1);

		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);

		// tv_ys=(TextView) findViewById(R.id.tv_y1s);
		// tv_xh=(TextView) findViewById(R.id.tv_xh);
		tv_title = (TextView) findViewById(R.id.tv_title);
		content1 = (TextView) findViewById(R.id.content1);
		tv_pp = (TextView) findViewById(R.id.tv_PP);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_lx = (TextView) findViewById(R.id.tv_lx);
		tv_sjhttp = (TextView) findViewById(R.id.tv_sjhttp);
		tv_sjhttp.setOnClickListener(this);
		// tv_spxx=(TextView) findViewById(R.id.tv_spxx);
		fac_detai = (TextView) findViewById(R.id.fac_detai);
		fac_img = (ImageView) findViewById(R.id.fac_img);

		TextView tv_ms = (TextView) findViewById(R.id.tv_ms);
		tv_ms.setOnClickListener(this);
		TextView tv_kt = (TextView) findViewById(R.id.tv_kt);
		tv_kt.setOnClickListener(this);
		tv_pl = (TextView) findViewById(R.id.tv_pl);
		tv_pl.setOnClickListener(this);
		TextView tv_zd = (TextView) findViewById(R.id.tv_zd);
		tv_zd.setOnClickListener(this);
		TextView tv_jy = (TextView) findViewById(R.id.tv_jy);
		tv_jy.setOnClickListener(this);
		TextView tv_pic = (TextView) findViewById(R.id.tv_pic);
		tv_pic.setOnClickListener(this);
		if(PosListActivity.shoptype != 1){
			setting_btn_clear.setText("采购");
			tv_dp.setText("采购价格");
			tv_j_isshow.setVisibility(View.GONE);
			tv_yj.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.mer_detail:
			FactoryPopWindow fact = new FactoryPopWindow(this,
					factoryEntity.getLogo_file_path(), factoryEntity.getName(),
					factoryEntity.getDescription());
			fact.showAtLocation(findViewById(R.id.main), Gravity.CENTER
					| Gravity.CENTER, 0, 0);
			break;
		case R.id.tv_bug:
			all_price = gfe.getRetail_price()+opening_cost;
			tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
			islea = false;
			setting_btn_clear.setText("采购");
			tv_dp.setText("采购价格");
			tv_lea.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.send_out_goods_shape));
			tv_bug.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.bg_shape));
			tv_bug.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_lea.setTextColor(getResources().getColor(R.color.text292929));
			tv_lea.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.send_out_goods_shape));
			tv_bug.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.bg_shape));
			break;
		case R.id.tv_lea:
			// tv_bug
			all_price = gfe.getLease_deposit()+opening_cost;
			tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
			islea = true;
			setting_btn_clear.setText("租赁");
			tv_dp.setText("租赁价格");
			tv_bug.setTextColor(getResources().getColor(R.color.text292929));
			tv_lea.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_lea.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.bg_shape));
			tv_bug.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.send_out_goods_shape));
			break;
		case R.id.tv_zd:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 3);
			i.putExtra("commets", commentsCount);
			startActivity(i);
			break;
		case R.id.tv_ms:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 0);
			i.putExtra("commets", commentsCount);
			startActivity(i);
			break;
		case R.id.tv_kt:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 1);
			i.putExtra("commets", commentsCount);
			startActivity(i);
			break;
		case R.id.tv_pl:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 2);
			i.putExtra("commets", commentsCount);
			Config.goodId = id;
			startActivity(i);
			break;
		case R.id.tv_jy:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 4);
			i.putExtra("commets", commentsCount);
			startActivity(i);
			break;
		case R.id.tv_pic:
			i = new Intent(GoodDeatail.this, GoodDeatilMore.class);
			i.putExtra("type", 5);
			i.putExtra("commets", commentsCount);
			startActivity(i);
			break;
		case R.id.titleback_linear_back:
			finish();
			break;

		case R.id.tv_sjhttp:
			/*Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(tv_sjhttp.getText().toString());
			intent.setData(content_url);
			startActivity(intent);*/
			Intent i = new Intent(GoodDeatail.this, MyWebView.class);
			i.putExtra("title", "支付通道");
			i.putExtra("url", tv_sjhttp.getText().toString());
			startActivity(i);
			break;
		case R.id.btn_buy: // tv_comment
			if (PosListActivity.shoptype!=1) {
				if(islea){
					Intent i21 = new Intent(GoodDeatail.this, LeaseConfirm.class);
					i21.putExtra("getTitle", gfe.getTitle());
					i21.putExtra("price", gfe.getPrice()+opening_cost);
					i21.putExtra("model", gfe.getModel_number());
					i21.putExtra("purchase_price", gfe.getLease_deposit()+opening_cost);
					i21.putExtra("brand", gfe.getGood_brand()+gfe.getModel_number());
					i21.putExtra("paychannelId", paychannelId);
					i21.putExtra("goodId", gfe.getId());
					i21.putExtra("commets", commentsCount);
					i21.putExtra("type",2);
					i21.putExtra("chanel", tdname);
					Config.iszd=true;
					if(piclist.size()!=0){
						i21.putExtra("piclist",piclist.get(0));
					}					
					startActivity(i21);
				}else{
					Intent i21 = new Intent(GoodDeatail.this, LeaseConfirm.class);
				
					i21.putExtra("getTitle", gfe.getTitle());
					i21.putExtra("price", gfe.getPrice()+opening_cost);
					i21.putExtra("model", gfe.getModel_number());
					i21.putExtra("purchase_price", gfe.getPurchase_price()+opening_cost);
					i21.putExtra("brand", gfe.getGood_brand()+gfe.getModel_number());
					i21.putExtra("paychannelId", paychannelId);
					i21.putExtra("goodId", gfe.getId());
					i21.putExtra("type", 1);
					i21.putExtra("chanel", tdname);
					if(piclist.size()!=0){
						i21.putExtra("piclist",piclist.get(0));
					}	
					startActivity(i21);
				}
				
			} else {
				
				Intent i2 = new Intent(GoodDeatail.this, GoodConfirm.class);
				i2.putExtra("floor_purchase_quantity", gfe.getFloor_purchase_quantity());
				i2.putExtra("getTitle", gfe.getTitle());
				i2.putExtra("price", gfe.getPrice()+opening_cost);
				i2.putExtra("model", gfe.getModel_number());
				i2.putExtra("purchase_price", gfe.getPurchase_price()+opening_cost);
				i2.putExtra("paychannelId", paychannelId);
				i2.putExtra("brand", gfe.getGood_brand()+gfe.getModel_number());
				i2.putExtra("goodId", gfe.getId());
				i2.putExtra("chanel", tdname);
				if(piclist.size()!=0){
					i2.putExtra("piclist",piclist.get(0));
				}	
				startActivity(i2);
			}
			break;
		default:
			break;
		}
	}

	private void getdata() {
		
		Map<String, Object> params = new HashMap<String, Object>();		
		params.put("goodId", id);
		params.put("agentId", MyApplication.NewUser.getAgentId());
		params.put("type", PosListActivity.shoptype);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {				
			return;
		}		
		//(Context context, String url, Header[] headers, RequestParams params, String contentType,
          //      ResponseHandlerInterface responseHandler) 
		//client.post(context, url, null, entity, "application/json", responseHandler);
		new AsyncHttpClient().post(getApplicationContext(),Config.GOODDETAIL, null,entity,"application/json",
				new AsyncHttpResponseHandler() {
					private Dialog loadingDialog;									
					@Override
					public void onStart() {

						super.onStart();
						loadingDialog = DialogUtil
								.getLoadingDialg(GoodDeatail.this);
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
			
						String userMsg = new String(responseBody).toString();
						innitView();
						Log.i("ljp", userMsg);
						Gson gson = new Gson();
						// EventEntity
						JSONObject jsonobject = null;
						int code = 0;
						try {
							jsonobject = new JSONObject(userMsg);
							code = jsonobject.getInt("code");
							if (code == -2) {
								
								  Intent i = new Intent(getApplication(),
								  LoginActivity.class); startActivity(i);
								 
							} else if (code == 1) {

								String res = jsonobject.getString("result");
								jsonobject = new JSONObject(res);
								Config.piclist= gson.fromJson(
										jsonobject.getString("picList"),
										new TypeToken<List<GoodPic>>() {
										}.getType());
								piclist = gson.fromJson(
										jsonobject.getString("goodPics"),
										new TypeToken<List<String>>() {
										}.getType());
								
								User_button = gson.fromJson(
										jsonobject.getString("payChannelList"),
										new TypeToken<List<GriviewEntity>>() {
										}.getType());
								if(User_button==null)
									User_button = new ArrayList<GriviewEntity>();
								buttonAdapter = new ButtonGridviewAdapter(
										GoodDeatail.this, User_button, 0);

								gview1.setAdapter(buttonAdapter);
								gview1.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {

										buttonAdapter.setIndex(arg2);
										getdataByChanel(User_button.get(arg2)
												.getId());
										buttonAdapter.notifyDataSetChanged();
									}
								});
								
								 Config.myList = gson.fromJson( jsonobject
								 .getString("relativeShopList"), new
								TypeToken<List<PosEntity>>() { }.getType());
								 
								gfe = gson.fromJson(
										jsonobject.getString("goodinfo"),
										new TypeToken<GoodinfoEntity>() {
										}.getType());
					

								commentsCount = jsonobject
										.getInt("commentsCount");
								Config.gfe = gfe;
							
								String res2 = jsonobject
										.getString("paychannelinfo");
								jsonobject = new JSONObject(res2);
				
							/*	factoryEntity1=gson.fromJson(
										jsonobject.getString("pcfactory"),
										new TypeToken<FactoryEntity>() {
										}.getType());*/
								paychannelId = jsonobject.getInt("id");
								factoryEntity=gson.fromJson(jsonobject.getString("pcfactory"), new TypeToken<FactoryEntity>() {
								}.getType());
								ImageCacheUtil.IMAGE_CACHE.get(
										factoryEntity
												.getLogo_file_path(),
										fac_img);
								Config.celist = gson.fromJson(
										jsonobject.getString("standard_rates"),
										new TypeToken<List<ChanelEntitiy>>() {
										}.getType());
								Config.tDates = gson.fromJson(
										jsonobject.getString("tDates"),
										new TypeToken<List<tDates>>() {
										}.getType());
								Config.other_rate = gson.fromJson(
										jsonobject.getString("other_rate"),
										new TypeToken<List<other_rate>>() {
										}.getType());
								Config.pub = gson.fromJson(jsonobject
										.getString("requireMaterial_pub"),
										new TypeToken<List<ApplyneedEntity>>() {
										}.getType());
								;
								Config.single = gson.fromJson(jsonobject
										.getString("requireMaterial_pra"),
										new TypeToken<List<ApplyneedEntity>>() {
										}.getType());

								System.out.println("``celist`" + celist.size());
								lvAdapter = new HuilvAdapter(GoodDeatail.this,
										celist);
								// pos_lv1.setAdapter(lvAdapter);

								for (int i = 0; i < piclist.size(); i++) {
									ma.add(piclist.get(i));
								}
								// User_button=gson.fromJson(jsonobject.getString("payChannelList"),
								// new TypeToken<List<GriviewEntity>>()
								// {
								// }.getType());
								// buttonAdapter=new
								// ButtonGridviewAdapter(GoodDeatail.this,
								// User_button,0);
								// gview1.setAdapter(buttonAdapter);
								if (jsonobject.getBoolean("support_type")) {
									arelist = gson.fromJson(
											jsonobject.getString("supportArea"),
											new TypeToken<List<String>>() {
											}.getType());
									String a = "";
									for (int i = 0; i < arelist.size(); i++) {
										a = a + arelist.get(i);
									}

									Config.suportare = a;
								} else {
									Config.suportare = "不支持";

								}
								if (jsonobject
										.getBoolean("support_cancel_flag")) {
									Config.suportcl = "支持";

								} else {
									Config.suportcl = "不支持";
								}
								Config.apply=jsonobject.getString("opening_datum");
								opening_cost = jsonobject.getInt("opening_cost");
								tdname = jsonobject.getString("name");
								celist2 = gson.fromJson(
										jsonobject.getString("tDates"),
										new TypeToken<List<ChanelEntitiy>>() {
										}.getType());
								Config.celist2 = celist2;
								Config.tv_sqkt = jsonobject
										.getString("opening_requirement");
								ll_Factory = (LinearLayout) findViewById(R.id.mer_detail);
								ll_Factory.setOnClickListener(GoodDeatail.this);
								handler.sendEmptyMessage(0);
							} else {
								Toast.makeText(getApplicationContext(),
										jsonobject.getString("message"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(getApplicationContext(), statusCode+"", 1000).show();
					}
				});
	

	}

	private void initIndicator() {

		ImageView imgView;
		View v = findViewById(R.id.indicator);// 绾挎?ф按骞冲竷灞?锛岃礋璐ｅ姩鎬佽皟鏁村鑸浘鏍?

		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(
					10, 10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;

			if (i == 0) { // 鍒濆鍖栫涓?涓负閫変腑鐘舵??

				indicator_imgs[i]
						.setBackgroundResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}
			((ViewGroup) v).addView(indicator_imgs[i]);
		}

	}

	/**
	 * 閫傞厤鍣紝璐熻矗瑁呴厤 銆侀攢姣? 鏁版嵁 鍜? 缁勪欢 銆?
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;
		private int index;

		public MyAdapter(List<View> list) {
			mList = list;

		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {

			return mList.size();
		}

		/**
		 * Remove a page for the given position. 婊戝姩杩囧悗灏遍攢姣?
		 * 锛岄攢姣佸綋鍓嶉〉鐨勫墠涓?涓殑鍓嶄竴涓殑椤碉紒 instantiateItem(View container, int
		 * position) This method was deprecated in API level . Use
		 * instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView(mList.get(position));

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {

			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
		/*	ViewGroup.LayoutParams lp = image.getLayoutParams();
			lp.width = Config.ScreenWidth/2;
			lp.height = Config.ScreenWidth/2;			
			image.setLayoutParams(lp);
			image.setMaxWidth(Config.ScreenWidth);
			image.setMaxHeight(Config.ScreenWidth);
			DisplayImageOptions options = MyApplication.getDisplayOption();
			ImageLoader.getInstance().displayImage(ma.get(position), image, options);*/
			ImageCacheUtil.IMAGE_CACHE.get(ma.get(position), image);

			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(GoodDeatail.this,GoodDetailImgs.class);
					intent.putStringArrayListExtra("ma", (ArrayList<String>) ma);
					intent.putExtra("position_detail", position);
					startActivity(intent);
				}
			});

			return mList.get(position);
		}

	}

	private void getdataByChanel(int pcid) {

		RequestParams params = new RequestParams();
		params.put("pcid", pcid);
		System.out.println("---支付通道ID--" + pcid);

		params.setUseJsonStreamer(true);
		MyApplication
				.getInstance()
				.getClient()
				.post(Config.paychannel_info, params,
						new AsyncHttpResponseHandler() {

							

							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody) {

								String userMsg = new String(responseBody)
										.toString();

								Log.i("ljp", userMsg);
								Gson gson = new Gson();
								// EventEntity
								JSONObject jsonobject = null;
								int code = 0;
								try {
									jsonobject = new JSONObject(userMsg);
									code = jsonobject.getInt("code");
									if (code == -2) {
										/*
										 * Intent i = new
										 * Intent(getApplication(),
										 * LoginActivity.class);
										 * startActivity(i);
										 */
									} else if (code == 1) {
										String res = jsonobject
												.getString("result");
										jsonobject = new JSONObject(res);

										Config.tv_sqkt = jsonobject
												.getString("opening_requirement");
										 ImageCacheUtil.IMAGE_CACHE.get(
												 factoryEntity.getLogo_file_path(),
												 fac_img); 
													factoryEntity = gson.fromJson(jsonobject.getString("pcfactory"), new TypeToken<FactoryEntity>() {
													}.getType());
													if(factoryEntity.getLogo_file_path() != null){
														ImageCacheUtil.IMAGE_CACHE.get(factoryEntity.getLogo_file_path(),
																fac_img);
													}
													tv_sjhttp.setText(factoryEntity.getWebsite_url() );
													fac_detai.setText(factoryEntity.getDescription() );
										
										Config.celist = gson.fromJson(
												jsonobject
														.getString("standard_rates"),
												new TypeToken<List<ChanelEntitiy>>() {
												}.getType());
										
										  Config.tDates = gson.fromJson(
										  jsonobject.getString("tDates"), new
										  TypeToken<List<tDates>>() {
										 }.getType()); Config.other_rate =
										 gson.fromJson( jsonobject
										  .getString("other_rate"), new
										  TypeToken<List<other_rate>>() {
										  }.getType());
										 
										Config.pub = gson.fromJson(
												jsonobject
														.getString("requireMaterial_pub"),
												new TypeToken<List<ApplyneedEntity>>() {
												}.getType());
										;
										Config.single = gson.fromJson(
												jsonobject
														.getString("requireMaterial_pra"),
												new TypeToken<List<ApplyneedEntity>>() {
												}.getType());

										System.out.println("``celist`"
												+ celist.size());
										lvAdapter = new HuilvAdapter(
												GoodDeatail.this, celist);
										// pos_lv1.setAdapter(lvAdapter);

										// User_button=gson.fromJson(jsonobject.getString("payChannelList"),
										// new TypeToken<List<GriviewEntity>>()
										// {
										// }.getType());
										// buttonAdapter=new
										// ButtonGridviewAdapter(GoodDeatail.this,
										// User_button,0);
										// gview1.setAdapter(buttonAdapter);
										if (jsonobject
												.getBoolean("support_type")) {
											arelist = gson.fromJson(
													jsonobject
															.getString("supportArea"),
													new TypeToken<List<String>>() {
													}.getType());
											String a = "";
											for (int i = 0; i < arelist.size(); i++) {
												a = a + arelist.get(i);
											}

											Config.suportare = a;
										} else {
											Config.suportare = "不支持";

										}
										if (jsonobject
												.getBoolean("support_cancel_flag")) {
											Config.suportcl = "支持";

										} else {
											Config.suportcl = "不支持";
										}
									/*	if (islea == false) {
											//购买
											all_price = gfe.getRetail_price()+opening_cost;
											tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
										}else {
											//租赁
											all_price = gfe.getLease_deposit()+opening_cost;
											tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
										}*/
										opening_cost=jsonobject.getInt("opening_cost");
										tdname = jsonobject.getString("name");
										if (islea == false) {
											//购买
											if(PosListActivity.shoptype != 1){
												all_price = gfe.getRetail_price()+opening_cost;
												tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
											}else{
												all_price = gfe.getRetail_price()+opening_cost;
												tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getPurchase_price()+opening_cost));
											}
											
										}else {
											//租赁
											all_price = gfe.getLease_deposit()+opening_cost;
											tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
										}
									} else {
										Toast.makeText(
												getApplicationContext(),
												jsonobject.getString("message"),
												Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {

									e.printStackTrace();
								}

							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error) {

							}
						});

	}

	private class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {

			if (state == 0) {
				// new MyAdapter(null).notifyDataSetChanged();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {

			// 鏀瑰彉鎵?鏈夊鑸殑鑳屾櫙鍥剧墖涓猴細鏈?変腑
			for (int i = 0; i < indicator_imgs.length; i++) {

				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);

			}

			// 鏀瑰彉褰撳墠鑳屾櫙鍥剧墖涓猴細閫変腑
			index_ima = position;
			indicator_imgs[position]
					.setBackgroundResource(R.drawable.indicator_focused);
		}

	}

	
	@Override
	protected void onResume() {
		super.onPause();
		MobclickAgent.onPageStart( this.toString() );
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onResume();
		MobclickAgent.onPageEnd(this.toString());
		MobclickAgent.onPause(this);
	}
	
}
