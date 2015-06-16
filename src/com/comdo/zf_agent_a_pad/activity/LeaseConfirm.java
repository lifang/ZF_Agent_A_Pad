package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_USER;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.AddressManagerAdapter;
import com.comdo.zf_agent_a_pad.adapter.ChooseAdressAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageTerminal;
import com.comdo.zf_agent_a_pad.entity.AdressEntity;
import com.comdo.zf_agent_a_pad.entity.UserEntity;
import com.comdo.zf_agent_a_pad.entity.UserInfo;
import com.comdo.zf_agent_a_pad.entity.User_Info;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LeaseConfirm extends BaseActivity implements OnClickListener {
	List<AdressEntity> myList = new ArrayList<AdressEntity>();
	List<AdressEntity> moreList = new ArrayList<AdressEntity>();
	private String Url = Config.ChooseAdress;
	private TextView tv_sjr, tv_tel, tv_adress;
	private TextView tv_pop, tv_totle, title2, retail_price, showCountText,
			tv_pay, tv_count;
	private Button btn_pay;
	private ImageView reduce, add;
	PopupWindow menuWindow;
	private int pirce;
	private int goodId, paychannelId, quantity, addressId=-1, is_need_invoice = 0;
	private CheckBox item_cb;
	private int invoice_type = 0;
	private String comment, invoice_info;
	private ScrollViewWithListView sclist;
	private ChooseAdressAdapter myAdapter;
	private TextView tv_zc;
	private TextView tv_zd;
	private boolean flag = false;
	private EditText et_comment;
	private TextView tv_price;
	private TextView tv_brand;
	private int pg_price;
	private Spinner spinner_user;
	private int mChannelId;
	private int page = 0;
	private final int rows = 10;
	final List<String> listString = new ArrayList<String>();
	private ArrayAdapter<String> adapter_user;
	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	private Button bt_add;
	private int type;
	private ImageView event_img;
	private LinearLayout zl_isshow;
	private EditText buyCountEdit, comment_et, et_titel;
	private Button bt_addadress;
	private UserEntity ue;
	private int ordertype;
	private TextView tv_zl;
	private int comments;
	private Intent i;
	private BaseAdapter maAdapter;
	private LayoutInflater mInflater;
	private DecimalFormat df;
	private TextView tv_chanel;
	private LinearLayout ll_pf;
	private LinearLayout ll_select_user;
	private int userid;
	private TextView tv1;
	private TextView tv2;
	public static final int REQUEST_USER = 1009;
	private TextView userselected;
	private User_Info userInfo = new User_Info();
	public static final int REQUEST_CREATE_USER = 1000;
	private TextView hpsf;
	private TextView ktf;
	private TextView tv_isshow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_comfirm1);
		df = (DecimalFormat) NumberFormat
				.getInstance();
		df.applyPattern("0.00");
		ue = MyApplication.NewUser;
		userid = ue.getAgentUserId();
		type = getIntent().getIntExtra("type", 1);
		ll_select_user = (LinearLayout)findViewById(R.id.ll_select_user);
		if (type == 1) {
			new TitleMenuUtil(LeaseConfirm.this, "采购订单确认").show();
			ordertype = 3;
			ll_select_user.setVisibility(View.GONE);
		} else {
			new TitleMenuUtil(LeaseConfirm.this, "租赁订单确认").show();
			ordertype = 4;
		}

		initView();
		if (type == 1) {
			zl_isshow.setVisibility(View.GONE);
			tv_zl.setText("我要发票");
			retail_price.setVisibility(View.GONE);
			tv_zl.setTextColor(getResources().getColor(R.color.text292929));

		}
		comments = getIntent().getIntExtra("comments", 0);
		title2.setText(getIntent().getStringExtra("getTitle"));
		pg_price = getIntent().getIntExtra("purchase_price", 1);
		pirce = getIntent().getIntExtra("price", 0);
		retail_price.setText("原价:￥" +  df.format(pirce*1.0f/100));
		goodId = getIntent().getIntExtra("goodId", 1);
		paychannelId = getIntent().getIntExtra("paychannelId", 1);
		tv_pay.setText("实付：￥ " +   df.format((pirce)*1.0f/100));
		tv_totle.setText("实付：￥ " +   df.format((pirce)*1.0f/100));
		tv_price.setText("￥" +   df.format(pirce *1.0f/100));
		tv_brand.setText(getIntent().getStringExtra("brand"));
		String img_url = getIntent().getStringExtra("piclist");
		ImageCacheUtil.IMAGE_CACHE.get(img_url, event_img);
		System.out.println("=paychannelId==" + paychannelId);
		tv_chanel.setText(getIntent().getStringExtra("chanel"));
		hpsf.setText("含开通费:￥"+df.format(getIntent().getIntExtra("hpsf", 0)*1.0f/100));
		ktf.setText("含开通费:￥"+df.format(getIntent().getIntExtra("hpsf", 0)*1.0f/100));
		tv_isshow.setText("(含开通费:￥"+df.format(getIntent().getIntExtra("hpsf", 0)*1.0f/100)+")");
		//GetUser();
		getData1();

	}

	private void GetUser() {
		items = new ArrayList<Map<String, Object>>();

		Config.userGetUser(this, MyApplication.NewUser.getAgentUserId(), 0, rows,
				new HttpCallback<List<UserInfo>>(this) {
					@Override
					public void onSuccess(List<UserInfo> data) {

						for (UserInfo userInfo :data) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("id", userInfo.getId());
							item.put("name", userInfo.getName());
							listString.add(userInfo.getName());
							items.add(item);
						}
						maAdapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken<List<UserInfo>> getTypeToken() {
						return new TypeToken<List<UserInfo>>() {
						};
					}
				});
	}

	private void initView() {
		tv_isshow = (TextView)findViewById(R.id.tv_isshow);
		
		
		if (type == 1) {
			tv_isshow.setVisibility(View.VISIBLE);
		}else{
			tv_isshow.setVisibility(View.GONE);
		}
		ktf = (TextView)findViewById(R.id.ktf);
		hpsf = (TextView)findViewById(R.id.hpsf);
		userselected = (TextView) findViewById(R.id.user_selected);
		userselected.setOnClickListener(this);
		tv1 = (TextView)findViewById(R.id.tv1);
		tv2 = (TextView)findViewById(R.id.tv2);
		if(type==1){
			tv1.setText("价格");
			tv2.setText("购买数量");
		}
		ll_pf = (LinearLayout)findViewById(R.id.ll_fp);
		tv_chanel = (TextView)findViewById(R.id.wayName);
		mInflater = LayoutInflater.from(this);
		tv_zl = (TextView) findViewById(R.id.tv_zl);
		tv_zl.setOnClickListener(this);
		bt_addadress = (Button) findViewById(R.id.bt_addadress);
		bt_addadress.setOnClickListener(this);
		zl_isshow = (LinearLayout) findViewById(R.id.zl_isshow);
		event_img = (ImageView) findViewById(R.id.evevt_img);
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_add.setOnClickListener(this);
		//spinner_user = (Spinner) findViewById(R.id.spinner_user);
		maAdapter = new BaseAdapter() {

			@Override
			public int getCount() {
				return listString.size();
			}

			@Override
			public Object getItem(int arg0) {
				return listString.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout layout = (LinearLayout) mInflater.inflate(
						R.layout.drop_down_item1, null);
				TextView tv = (TextView) layout.findViewById(R.id.text);
				tv.setText((String) getItem(position));
				return layout;
			}

		};
		//adapter_user = new ArrayAdapter<String>(this,
		//		android.R.layout.simple_spinner_item, listString);
		// adapter.setDropDownViewResource(R.layout.drop_down_item);
		//spinner_user.setAdapter(maAdapter);
	
		
		/*spinner_user
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						addressId=-1;
						mChannelId = Integer.parseInt(items.get(arg2).get("id").toString()) ;
						//ue.setAgentUserId(mChannelId);
						userid=mChannelId;
						myList.clear();
						getData1();

					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});*/
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_brand = (TextView) findViewById(R.id.content2);
		tv_zc = (TextView) findViewById(R.id.tv_zc);
		tv_zd = (TextView) findViewById(R.id.tv_zd);
		if (Config.gfe != null) {
			tv_zc.setText("最长租赁时间：" + Config.gfe.getReturn_time()+ "月");
			tv_zd.setText("最短租赁时间：" + Config.gfe.getLease_time()  + "月");
		}
		sclist = (ScrollViewWithListView) findViewById(R.id.pos_lv1);
		myAdapter = new ChooseAdressAdapter(this, myList);
		sclist.setAdapter(myAdapter);
		sclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				addressId = myList.get(position).getId();
				myAdapter.chang();
				myList.get(position).setIscheck(true);
				myAdapter.notifyDataSetChanged();
			}
		});
		add = (ImageView) findViewById(R.id.add);
		reduce = (ImageView) findViewById(R.id.reduce);
		reduce.setOnClickListener(this);
		add.setOnClickListener(this);

		tv_totle = (TextView) findViewById(R.id.tv_totle);
		// showCountText = (TextView) findViewById(R.id.showCountText);

		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_tel = (TextView) findViewById(R.id.tv_tel);
		tv_adress = (TextView) findViewById(R.id.tv_adress);
		title2 = (TextView) findViewById(R.id.title);
		retail_price = (TextView) findViewById(R.id.retail_price);
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(this);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		et_titel = (EditText) findViewById(R.id.et_titel);
		buyCountEdit = (EditText) findViewById(R.id.buyCountEdit);

		// comment_et=(EditText) findViewById(R.id.comment_et);
		item_cb = (CheckBox) findViewById(R.id.item_cb);
		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					flag = true;
					if (type == 1){
						is_need_invoice = 1;
						et_titel.setEnabled(true);
						ll_pf.setVisibility(View.VISIBLE);
					}
						
				} else {
					flag = false;
					if (type == 1){
						is_need_invoice = 0;
						et_titel.setEnabled(false);
						ll_pf.setVisibility(View.GONE);
					}
						
				}
			}
		});
		buyCountEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// showCountText.setText(arg0.toString());
				if(buyCountEdit.getText().toString().equals("0"))
					buyCountEdit.setText("");
				tv_count.setText("共计:   " + arg0 + "件");
				if (buyCountEdit.getText().toString().equals("")) {
					quantity = 0;
				} else {
					quantity = Integer.parseInt(buyCountEdit.getText()
							.toString());
				}
			if(type==1){
				tv_totle.setText("实付：￥ " + df.format(((double) pirce) / 100 * quantity));
				tv_pay.setText("实付：￥ " + df.format(((double) pirce) / 100 * quantity));
			}else{
				tv_totle.setText("实付：￥ " + df.format(((double) pirce+getIntent().getIntExtra("hpsf", 0)) / 100 * quantity));
				tv_pay.setText("实付：￥ " + df.format(((double) pirce+getIntent().getIntExtra("hpsf", 0)) / 100 * quantity));
			}
				
				
				ktf.setText("含开通费￥"+df.format(getIntent().getIntExtra("hpsf", 0)*quantity*1.0f/100));
				hpsf.setText("含开通费￥"+df.format(getIntent().getIntExtra("hpsf", 0)*quantity*1.0f/100));
			
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		Spinner sp = (Spinner) findViewById(R.id.spinner);
		final String arr[] = new String[] { "公司", "个人" };

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arr);
		sp.setAdapter(arrayAdapter);

		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				invoice_type = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
	}

	private void getData1() {
		Config.GetAdressLis(LeaseConfirm.this, userid,
				new HttpCallback<List<AdressEntity>>(LeaseConfirm.this) {

					@Override
					public void onSuccess(List<AdressEntity> data) {
						myList.addAll(data);
						for (int i = 0; i < myList.size(); i++) {
							if (myList.get(i).getIsDefault() == 1) {
								// tv_name,tv_tel,tv_adresss;
								addressId = myList.get(i).getId();

							}
						}
						if(addressId==-1&&myList.size()!=0){
							myList.get(0).setIsDefault(1);
							addressId = myList.get(0).getId();
						}
						myAdapter.notifyDataSetChanged();

					}

					@Override
					public TypeToken getTypeToken() {
						return new TypeToken<List<AdressEntity>>() {
						};
					}
				});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.user_selected:
			startActivityForResult(new Intent(LeaseConfirm.this,
					TerminalSelectUserActivity.class), REQUEST_USER);
			break;
		case R.id.tv_zl:
			if (type != 1) {
				i = new Intent(LeaseConfirm.this, GoodDeatilMore.class);
				i.putExtra("type", 3);
				i.putExtra("commets", comments);
				startActivity(i);
			}
			break;
		case R.id.bt_add:
			startActivityForResult(new Intent(LeaseConfirm.this,
					TerminalApplyCreateActivity.class), REQUEST_CREATE_USER);
			break;
		case R.id.btn_pay:
			if (type == 1) {
				if(addressId!=-1){
					confirmGood();
				}else{
					Toast.makeText(getApplicationContext(), "请选择地址", 1000).show();
				}
			} else {
				if (flag) {
					if(addressId!=-1){
						
						confirmGood();
					}else{
						Toast.makeText(getApplicationContext(), "请选择地址", 1000).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请同意租赁协议", 1000)
							.show();
				}
			}
			break;
		case R.id.add:
			quantity = Integer.parseInt(buyCountEdit.getText().toString()) + 1;
			buyCountEdit.setText(quantity + "");
			break;
		case R.id.reduce:
			if (quantity <=1) {
				break;
			}
			quantity = Integer.parseInt(buyCountEdit.getText().toString()) - 1;
			buyCountEdit.setText(quantity + "");
			break;
		case R.id.bt_addadress:
			Intent i=new Intent(LeaseConfirm.this, AddAdress.class);
			i.putExtra("userid", userid);
			startActivityForResult(i,0);

			break;
		default:
			break;
		}
	}

	private void confirmGood() {
		et_comment = (EditText) findViewById(R.id.ed_comment);
		comment = et_comment.getText().toString();
		if(!buyCountEdit.getText().toString().trim().equals("")){
			quantity = Integer.parseInt(buyCountEdit.getText().toString().trim());
		}else{
			quantity = 1;
		}


		invoice_info = et_titel.getText().toString();
		Config.GOODCONFIRM1(LeaseConfirm.this, userid,
				ue.getAgentId(), ue.getId(), ue.getAgentUserId(), ordertype,
				goodId, paychannelId, quantity, addressId, comment,
				is_need_invoice, invoice_type, invoice_info,
				new HttpCallback(LeaseConfirm.this) {
					@Override
					public void onSuccess(Object data) {
						Intent i1 = new Intent(LeaseConfirm.this,
								PayFromCar.class);
						try {
							i1.putExtra("orderId", Integer.parseInt(data.toString()));
						} catch (Exception e) {
							
						}
						if(PosListActivity.shoptype==1){
							i1.putExtra("type", 5);
						}else{
							i1.putExtra("type", 3);
						}
						startActivity(i1);
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});

	}

	@Override
	protected void onResume() {

		super.onResume();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case 0:
			myList.clear();
			addressId=-1;
			getData1();		
			break;
		case REQUEST_CREATE_USER: {

			userInfo = (User_Info) data.getSerializableExtra(SELECTED_USER);
			userselected.setText(userInfo.getUsername());
			userid=userInfo.getId();
			myList.clear();
			addressId=-1;
			getData1();			
			break;		}
		case REQUEST_USER: {
			userInfo = (User_Info) data.getSerializableExtra(SELECTED_USER);
			userselected.setText(userInfo.getUsername());
			userid=userInfo.getId();
			myList.clear();
			addressId=-1;
			getData1();
			break;
			
		}
		default:
			break;
		}
	}
}
