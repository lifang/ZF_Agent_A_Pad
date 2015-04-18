package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.ChooseAdressAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.AdressEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GoodConfirm extends BaseActivity implements OnClickListener {
	List<AdressEntity> myList = new ArrayList<AdressEntity>();
	List<AdressEntity> moreList = new ArrayList<AdressEntity>();
	private String Url = Config.ChooseAdress;
	private TextView tv_sjr, tv_tel, tv_adress;
	private LinearLayout ll_choose;
	private TextView tv_pop, tv_totle, title2, retail_price, showCountText,
			tv_pay, tv_count;
	private Button btn_pay;
	private ImageView reduce, add;
	PopupWindow menuWindow;
	private int pirce;
	private int goodId, paychannelId, quantity, addressId, is_need_invoice = 0;
	private EditText buyCountEdit, comment_et, et_titel;
	private CheckBox item_cb;
	private int invoice_type = 0;
	private String comment, invoice_info;
	private ScrollViewWithListView sclist;
	private ChooseAdressAdapter myAdapter;
	private EditText et_comment;
	private TextView tv_price;
	private int pg_price;
	private TextView tv_brand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_comfirm);
		new TitleMenuUtil(GoodConfirm.this, "批购订单确认").show();
		initView();
		title2.setText(getIntent().getStringExtra("getTitle"));
		pg_price = getIntent().getIntExtra("purchase_price", 1);
		pirce = getIntent().getIntExtra("price", 0);
		retail_price.setText("原价:￥" + pirce);
		goodId = getIntent().getIntExtra("goodId", 1);
		paychannelId = getIntent().getIntExtra("paychannelId", 1);
		tv_pay.setText("实付：￥ " + ((double)pirce)/100);
		tv_totle.setText("实付：￥ " + ((double)pirce)/100);
		tv_price.setText("￥"+ ((double)pg_price)/100);
		tv_brand.setText(getIntent().getStringExtra("brand"));
		System.out.println("=paychannelId==" + paychannelId);
		//getData1();
	
	}

	private void initView() {
		tv_price = (TextView)findViewById(R.id.tv_price);
		tv_brand = (TextView)findViewById(R.id.content2);
		sclist = (ScrollViewWithListView)findViewById(R.id.pos_lv1);
		myAdapter = new ChooseAdressAdapter(this, myList);
		sclist.setAdapter(myAdapter);
		sclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				addressId=myList.get(position).getId();
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
		//showCountText = (TextView) findViewById(R.id.showCountText);

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
		buyCountEdit.setText("100");
		item_cb = (CheckBox) findViewById(R.id.item_cb);
		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					is_need_invoice = 1;
				} else {
					is_need_invoice = 0;
				}
			}
		});
		buyCountEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				//showCountText.setText(arg0.toString());
				tv_count.setText("共计:   " + arg0 + "件");
				if (buyCountEdit.getText().toString().equals("")) {
					quantity = 0;
				} else {
					quantity = Integer.parseInt(buyCountEdit.getText()
							.toString());
				}

				tv_totle.setText("实付：￥ " + ((double)pirce)/100 * quantity);
				tv_pay.setText("实付：￥ " + ((double)pirce)/100 * quantity);
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
		Toast.makeText(
				getApplicationContext(),
				"main Thread"
						+ sp.getItemIdAtPosition(sp.getSelectedItemPosition()),
				Toast.LENGTH_LONG).show();

		
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				invoice_type=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}

	private void getData1() {
		Config.GetAdressList(GoodConfirm.this, 80, new HttpCallback<List<AdressEntity>>(GoodConfirm.this) {

			@Override
			public void onSuccess(List<AdressEntity> data) {
				myList.addAll(data);
				for(int i =0;i<myList.size();i++){
						if(myList.get(i).getIsDefault()==1) {
							//tv_name,tv_tel,tv_adresss;
							addressId=myList.get(i).getId();
							
						}
					}
				myAdapter.notifyDataSetChanged();
				
			}


			@Override
			public TypeToken getTypeToken() {
				return new TypeToken<List<AdressEntity>>(){
				};
			}
        });
	

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btn_pay:
			confirmGood();
			
			break;
		case R.id.add:
			quantity = Integer.parseInt(buyCountEdit.getText().toString()) + 1;
			buyCountEdit.setText(quantity + "");
			break;
		case R.id.reduce:
			if (quantity == 0) {
				break;
			}
			quantity = Integer.parseInt(buyCountEdit.getText().toString()) - 1;
			buyCountEdit.setText(quantity + "");
			break;
		default:
			break;
		}
	}



	private void confirmGood() {
		et_comment = (EditText)findViewById(R.id.ed_comment);
		comment=et_comment.getText().toString();
		quantity = Integer.parseInt(buyCountEdit.getText().toString());
		// comment=comment_et.getText().toString();
		RequestParams params = new RequestParams();
		params.put("customerId", 1);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("addressId", addressId);
		params.put("quantity", quantity);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", et_titel.getText().toString());
		params.setUseJsonStreamer(true);
		// int customerId,
		// int goodId,
		// int paychannelId,
		// int quantity,
		// int addressId,
		// String comment,
		// int is_need_invoice,
		// int invoice_type,
		// String invoice_info,
		invoice_info = et_titel.getText().toString();
		Log.e("goodId=" + goodId + "paychannelId=" + paychannelId + "quantity="
				+ quantity + "is_need_invoice=" + is_need_invoice, "ccccccccc");
		Config.GOODCONFIRM(GoodConfirm.this,1,goodId,paychannelId,
				quantity,addressId,comment,is_need_invoice,invoice_type,invoice_info,
        		
				
                new HttpCallback  (GoodConfirm.this) {

					@Override
					public void onSuccess(Object data) {
						//Intent i1 = new Intent(GoodConfirm.this, PayFromCar.class);
						//startActivity(i1);
					 
					}

					@Override
					public TypeToken getTypeToken() {
						// TODO Auto-generated method stub
						return  null;
					}
                });

	}
	@Override
	protected void onResume() {
	
		super.onResume();
		myList.clear();
		getData1();
	}
}
