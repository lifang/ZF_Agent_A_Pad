package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.AlterEmali;
import com.comdo.zf_agent_a_pad.activity.AlterNEmali;
import com.comdo.zf_agent_a_pad.activity.AlterNPhone;
import com.comdo.zf_agent_a_pad.activity.AlterPhone;
import com.comdo.zf_agent_a_pad.activity.LoginActivity;
import com.comdo.zf_agent_a_pad.activity.MainActivity;
import com.comdo.zf_agent_a_pad.adapter.AddressManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AddressManager;
import com.comdo.zf_agent_a_pad.entity.BaseInfoEntity;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.trade.entity.Province;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;

public class MineMyinfo extends Fragment implements OnClickListener {
	public static int mRecordType = 0;
	private TextView tv_jichuinfo, tv_safe, tv_manageradress;
	private LinearLayout ll_baseinfo, ll_chpaw, ll_address;
	private Mchpaw m_chpaw;
	private Mine_adress m_adress;
	private Mybaseinfo m_baseinfo;
	private int type = 1;
	public static Handler myHandler;
	private View view;
	private JSONObject js;
	private Handler mHandler;
	private TextView tv_company_type, tv_company_name, tv_yingyezhizhao,
			tv_company_shuiwudengji, tv_fuzeren_name, tv_fuzeren_no, tv_phone,
			tv_email, tv_adress, tv_adressdetail, tv_id_detail;
	private ImageView iv_shenfenzheng, iv_yingyezhizhao, iv_shuiwu;
	private String shenfenzheng, yingyezhizhao, shuiwu;
	private int tag = 0;
	private String[] imgPath = new String[3];
	private Button btn_exit;
	private List<City> mCities = new ArrayList<City>();
	private int customerId = MyApplication.NewUser.getId();
	// change psw
	private EditText et_oldpaw, et_newpaw, et_confirmpaw;
	private Button btn_save_address;
	public static List<AddressManager> dataadress;
	// address
	private BaseAdapter addressadapter;
	private ListView list;
	private Button btn_add;
	public static boolean isclickitem = false;
	private int id = MyApplication.NewUser.getId();
	public static int[] idd;
	private EditText login_edit_name, mobile_phone, zip_code, detail_address;
	private TextView tv_title, deleteadress;
	public static TextView area;
	private Button btn_save;
	private CheckBox cb;
	private int isDefault;
	public static int cityId;
	private AlertDialog.Builder builder;
	private boolean isEdit = false;
	private AlertDialog dialog;
	private Button close;
	private Activity mActivity;
	private TextView tv_phone_xg;
	private TextView tv_email_xg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Config.city=null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// view = inflater.inflate(R.layout.f_main,container,false);
	
		Log.e("container", String.valueOf(container));
		/*if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}*/
		// if(container!=null)
		//
		// { container.getParent()..clearChildFocus(arg0); }

		try {
			view = inflater.inflate(R.layout.myinfo, container, false);
			init();
			
		} catch (InflateException e) {

		}
		return view;
	}

	/*
	 * @Override public void onAttach(Activity activity) {
	 * super.onAttach(activity); mActivity=activity; }
	 */
	@Override
	public void onStart() {
		super.onStart();
		if(Config.city!=null){
			area.setText(Config.city.getName());
			cityId = Config.city.getId();
			Log.e("cityId", cityId+"");
		}
		Log.e("get", String.valueOf(mActivity));
		
		switch (type) {
		case 1:
			tv_jichuinfo.setBackgroundResource(R.drawable.tab_bg);
			tv_safe.setBackgroundColor(getResources().getColor(R.color.bg));
			tv_manageradress.setBackgroundColor(getResources().getColor(
					R.color.bg));
			ll_baseinfo.setVisibility(View.VISIBLE);
			ll_chpaw.setVisibility(View.GONE);
			ll_address.setVisibility(View.GONE);
			baseinfoinit();
			baseinfogetData();
			// if (m_baseinfo == null)
			// m_baseinfo = new Mybaseinfo();
			// getChildFragmentManager().beginTransaction()
			// getActivity().getSupportFragmentManager().beginTransaction()
			// .replace(R.id.fm, m_baseinfo).commit();

			break;

		// case 2:
		// tv_safe.setBackgroundResource(R.drawable.tab_bg);
		// tv_jichuinfo
		// .setBackgroundColor(getResources().getColor(R.color.bg));
		// tv_manageradress.setBackgroundColor(getResources().getColor(
		// R.color.bg));
		//
		// ll_chpaw.setVisibility(View.VISIBLE);
		// ll_baseinfo.setVisibility(View.GONE);
		// ll_address.setVisibility(View.GONE);
		// chpawinit();
		// // if (m_chpaw == null)
		// // m_chpaw = new Mchpaw();
		// // getChildFragmentManager().beginTransaction()
		// // getActivity().getSupportFragmentManager().beginTransaction()
		// // .replace(R.id.fm, m_chpaw).commit();
		// break;
		// case 3:
		// tv_safe.setBackgroundColor(getResources().getColor(R.color.bg));
		// tv_jichuinfo
		// .setBackgroundColor(getResources().getColor(R.color.bg));
		// tv_manageradress.setBackgroundResource(R.drawable.tab_bg);
		//
		// ll_address.setVisibility(View.VISIBLE);
		// ll_baseinfo.setVisibility(View.GONE);
		// ll_chpaw.setVisibility(View.GONE);
		// // if (m_adress == null)
		// // m_adress = new Mine_adress();
		// // getChildFragmentManager().beginTransaction()
		// // getActivity().getSupportFragmentManager().beginTransaction()
		// // .replace(R.id.fm, m_adress).commit();
		// break;
		default:
			break;
		}
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					if(addressadapter!=null)
					list.setAdapter(addressadapter);
					break;
				case 1:

					if (dataadress.size() != 0) {
						dataadress.clear();
					}
					addressgetData();

					break;
				case 2:
					isEdit = true;
					opendialog();

					break;
				case 3:

					delect();
					break;
				default:
					break;
				}

			};
		};
	
	
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		type = 1;
		/*
		 * if(view!=null){ ViewGroup parent = (ViewGroup) view.getParent(); if
		 * (parent != null) parent.removeView(view); }
		 */
	
	}

	@Override
	public void onResume() {
		super.onResume();
		baseinfogetData();

		if(Config.is_editadress){
			tv_safe.setBackgroundResource(0);
			tv_jichuinfo.setBackgroundResource(0);
			tv_manageradress.setBackgroundResource(R.drawable.tab_bg);

			ll_address.setVisibility(View.VISIBLE);
			ll_baseinfo.setVisibility(View.GONE);
			ll_chpaw.setVisibility(View.GONE);
			addressinit();
			addressgetData();
			
		}
		Log.e("get1", String.valueOf(mActivity));
	

	}

	private void init() {
		tv_jichuinfo = (TextView) view.findViewById(R.id.tv_jichuinfo);
		tv_safe = (TextView) view.findViewById(R.id.tv_safe);
		tv_manageradress = (TextView) view.findViewById(R.id.tv_manageradress);
		ll_baseinfo = (LinearLayout) view.findViewById(R.id.ll_baseinfo);
		ll_chpaw = (LinearLayout) view.findViewById(R.id.ll_chpaw);
		ll_address = (LinearLayout) view.findViewById(R.id.ll_address);
		tv_jichuinfo.setOnClickListener(this);
		tv_safe.setOnClickListener(this);
		tv_manageradress.setOnClickListener(this);
		
		tv_phone_xg = (TextView)view.findViewById(R.id.tv_phone_xg);
		tv_email_xg = (TextView)view.findViewById(R.id.tv_email_xg);
		tv_phone_xg.setOnClickListener(this);
		tv_email_xg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_phone_xg:
			if(tv_phone.getText().toString().trim().equals("")){
				Intent i=new Intent(mActivity,AlterNPhone.class);
				i.putExtra("phone", tv_phone.getText().toString().trim());
				startActivity(i);	
			}else{
				Intent i=new Intent(mActivity,AlterPhone.class);
				i.putExtra("phone", tv_phone.getText().toString().trim());
				startActivity(i);
			}
		
			break;
		case R.id.tv_email_xg:
			if(tv_email.getText().toString().trim().equals("")){
				Intent i1=new Intent(mActivity,AlterNEmali.class);
				i1.putExtra("phone", tv_email.getText().toString().trim());
				startActivity(i1);	
			}else{
				Intent i1=new Intent(mActivity,AlterEmali.class);
				i1.putExtra("phone", tv_email.getText().toString().trim());
				startActivity(i1);	
			}
		
			break;
		case R.id.tv_safe:
			type = 2;
			tv_safe.setBackgroundResource(R.drawable.tab_bg);
			tv_jichuinfo.setBackgroundResource(0);
			tv_manageradress.setBackgroundResource(0);

			ll_chpaw.setVisibility(View.VISIBLE);
			ll_address.setVisibility(View.GONE);
			ll_baseinfo.setVisibility(View.GONE);
			chpawinit();
			// if (m_chpaw == null)
			// m_chpaw = new Mchpaw();
			// getChildFragmentManager().beginTransaction()
			// getActivity().getSupportFragmentManager().beginTransaction()
			// .replace(R.id.fm, m_chpaw).commit();
			break;
		case R.id.tv_manageradress:
			type = 3;
			tv_safe.setBackgroundResource(0);
			tv_jichuinfo.setBackgroundResource(0);
			tv_manageradress.setBackgroundResource(R.drawable.tab_bg);

			ll_address.setVisibility(View.VISIBLE);
			ll_baseinfo.setVisibility(View.GONE);
			ll_chpaw.setVisibility(View.GONE);
			addressinit();
			addressgetData();
			// if (m_adress == null)
			// m_adress = new Mine_adress();
			// getChildFragmentManager().beginTransaction()
			// getActivity().getSupportFragmentManager().beginTransaction()
			// .replace(R.id.fm, m_adress).commit();
			break;
		case R.id.tv_jichuinfo:
			type = 1;
			tv_jichuinfo.setBackgroundResource(R.drawable.tab_bg);
			tv_safe.setBackgroundResource(0);
			tv_manageradress.setBackgroundResource(0);

			ll_baseinfo.setVisibility(View.VISIBLE);
			ll_address.setVisibility(View.GONE);
			ll_chpaw.setVisibility(View.GONE);
			Config.is_editadress=false;
			baseinfoinit();
			baseinfogetData();
			// if (m_baseinfo == null)
			// m_baseinfo = new Mybaseinfo();
			// getChildFragmentManager().beginTransaction()
			// getActivity().getSupportFragmentManager().beginTransaction()
			// .replace(R.id.fm, m_baseinfo).commit();
			break;
		// base info
		case R.id.iv_shenfenzheng:
			tag = 1;
			imgPath[0] = shenfenzheng;
			openimg(tag);
			break;
		case R.id.iv_yingyezhizhao:
			tag = 2;
			imgPath[1] = yingyezhizhao;
			openimg(tag);
			break;
		case R.id.iv_shuiwu:
			tag = 3;
			imgPath[2] = shuiwu;
			openimg(tag);
			break;
		case R.id.btn_exit:
			startActivity(new Intent(mActivity, LoginActivity.class));
			mActivity.finish();
			break;
		// address
		case R.id.btn_add:
			isEdit = false;
			opendialog();
			break;
		case R.id.btn_save:
			if (login_edit_name.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "收件人不能为空");
				return;
			}
			if (mobile_phone.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "联系电话不能为空");
				return;
			}
			if (!RegText.isMobileNO(mobile_phone.getText().toString())) {
				CommonUtil.toastShort(mActivity, "手机号码格式不正确");
				return;
			}
			if (zip_code.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "邮政编码不能为空");
				return;
			}
			if (!RegText.isYouBian(zip_code.getText().toString())) {
				CommonUtil.toastShort(mActivity, "邮政编码格式不正确");
				return;
			}
			if (area.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "请选择所在地");
				return;
			}
			if (detail_address.getText().toString().equals("")) {
				CommonUtil.toastShort(mActivity, "详细地址不能为空");
				return;
			}
			dialog.dismiss();
			if (isEdit) {

				changeAddress();
				// dialog.dismiss();
			} else {
				addAddresss();
			}
			break;
		case R.id.area:
			Intent intent = new Intent(mActivity,
					CityProvinceActivity.class);
			// intent.putExtra(CITY_NAME, cityName);
			// startActivityForResult(intent, REQUEST_CITY);
			Config.is_editadress=true;
			mActivity.startActivityForResult(
					intent,
					com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
			break;
		case R.id.close:
			dialog.dismiss();
			break;
		case R.id.deleteadress:
			final com.comdo.zf_agent_a_pad.util.AlertDialog ad = new com.comdo.zf_agent_a_pad.util.AlertDialog(mActivity);
			ad.setTitle("提示");
			ad.setMessage("确认删除?");
			ad.setPositiveButton("取消", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
				}
			});
			ad.setNegativeButton("确定", new OnClickListener() {
				@Override
				public void onClick(final View arg0) {
					dialog.dismiss();
					
					myHandler.sendEmptyMessage(3);
					ad.dismiss();
				}	
			});

			break;
		default:
			break;
		}
	}

	private void baseinfoinit() {
		btn_exit = (Button) view.findViewById(R.id.btn_exit);
		tv_company_type = (TextView) view.findViewById(R.id.tv_company_type);
		tv_company_name = (TextView) view.findViewById(R.id.tv_company_name);
		tv_yingyezhizhao = (TextView) view.findViewById(R.id.tv_yingyezhizhao);
		tv_company_shuiwudengji = (TextView) view
				.findViewById(R.id.tv_company_shuiwudengji);
		tv_fuzeren_name = (TextView) view.findViewById(R.id.tv_fuzeren_name);
		tv_fuzeren_no = (TextView) view.findViewById(R.id.tv_fuzeren_no);
		tv_phone = (TextView) view.findViewById(R.id.tv_phone);
		tv_email = (TextView) view.findViewById(R.id.tv_email);
		tv_adress = (TextView) view.findViewById(R.id.tv_adress);
		tv_adressdetail = (TextView) view.findViewById(R.id.tv_adressdetail);
		iv_shenfenzheng = (ImageView) view.findViewById(R.id.iv_shenfenzheng);
		iv_yingyezhizhao = (ImageView) view.findViewById(R.id.iv_yingyezhizhao);
		iv_shuiwu = (ImageView) view.findViewById(R.id.iv_shuiwu);
		tv_id_detail = (TextView) view.findViewById(R.id.tv_id_detail);
		iv_shenfenzheng.setOnClickListener(this);
		iv_yingyezhizhao.setOnClickListener(this);
		iv_shuiwu.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
	}

	private void chpawinit() {
		et_oldpaw = (EditText) view.findViewById(R.id.et_oldpaw);
		et_newpaw = (EditText) view.findViewById(R.id.et_newpaw);
		et_confirmpaw = (EditText) view.findViewById(R.id.et_confirmpaw);
		btn_save_address = (Button) view.findViewById(R.id.btn_save);
	
		btn_save_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(StringUtil.isNull(et_oldpaw.getText().toString())){
					Toast.makeText(mActivity, "原始密码不能为空", 1000).show();
				}else if(StringUtil.isNull(et_newpaw.getText().toString())){
					Toast.makeText(mActivity, "新密码不能为空", 1000).show();
				}
				if(et_newpaw.getText().toString().trim().length()<6||et_newpaw.getText().toString().trim().length()>20){
					Toast.makeText(mActivity, "密码为6-20位！", 1000).show();
				}
				else if(StringUtil.isNull(et_confirmpaw.getText().toString())){
					Toast.makeText(mActivity, "确认密码不能为空", 1000).show();
				}else if(!et_newpaw.getText().toString().equals(et_confirmpaw.getText().toString())){
					Toast.makeText(mActivity, "两次密码不一致!", 1000).show();
				}else{
					changepaw();
				}
					
				

			}
		});

	}

	private void addressinit() {
		dataadress = new ArrayList<AddressManager>();
		addressadapter = new AddressManagerAdapter(dataadress, mActivity
				.getBaseContext());
		list = (ListView) view.findViewById(R.id.list);
		btn_add = (Button) view.findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
	}

	private void baseinfogetData() {
		Config.GetInfo(mActivity, customerId,
				new HttpCallback<BaseInfoEntity>(mActivity) {

					@Override
					public void onSuccess(BaseInfoEntity data) {
						// myHandler.sendEmptyMessage(0);
						if (data == null) {
							CommonUtil.toastShort(mActivity, "未获得数据");
							return;
						}
						if (data.getTypes().equals("1")) {
							tv_company_type.setText("公司");
						} else if (data.getTypes().equals("2")) {

							tv_company_type.setText("个人");
						}

						tv_company_name.setText(data.getCompany_name());
						tv_yingyezhizhao.setText(data.getBusiness_license());
						tv_company_shuiwudengji.setText(data
								.getTax_registered_no());
						tv_fuzeren_name.setText(data.getName());
						tv_fuzeren_no.setText(data.getCard_id());
						if(StringUtil.isNull(data.getPhone())){
							tv_phone_xg.setText("去提交");
						}
						tv_phone.setText(data.getPhone());
						tv_email.setText(data.getEmail());
						CommonUtil.findCityById(mActivity,
								Integer.parseInt(data.getCity_id()),
								new CommonUtil.OnCityFoundListener() {
									@Override
									public void onCityFound(Province province,
											City city) {
										tv_adress.setText(city.getName());
									}
								});

//						tv_adress.setText(findcity(Integer.parseInt(data
//								.getCity_id())));
						tv_adressdetail.setText(data.getAddress());
						shenfenzheng = data.getCard_id_photo_path();
						yingyezhizhao = data.getLicense_no_pic_path();
						shuiwu = data.getTax_no_pic_path();
						tv_id_detail.setText(data.getUsername());
					}

					@Override
					public TypeToken<BaseInfoEntity> getTypeToken() {
						return new TypeToken<BaseInfoEntity>() {
						};
					}
				});
	}

	private void addressgetData() {
		if (!Tools.isConnect(mActivity)) {
			CommonUtil.toastShort(mActivity, "网络异常");
			return;
		}
		Config.GetAdressLis(mActivity, id,
				new HttpCallback<List<AddressManager>>(mActivity) {

					@Override
					public void onSuccess(List<AddressManager> data) {

						if (dataadress.size() != 0 && data.size() == 0) {
							Toast.makeText(mActivity, "没有更多数据!", 1000)
									.show();
						} else {
							dataadress.addAll(data);
							idd = new int[data.size()];
							for (int i = 0; i < idd.length; i++) {
								idd[i] = (int) data.get(i).getId();
							}
							for (int i = 0; i < dataadress.size(); i++) {
								if (dataadress.get(i).getIsDefault()
										.equals("1")) {
									dataadress.get(i).setIsDefault("默认");
								} else {
									dataadress.get(i).setIsDefault("");
								}
							}
						}
						if (dataadress.size() != 0) {
							myHandler.sendEmptyMessage(0);
						}
					
					}

					@Override
					public TypeToken<List<AddressManager>> getTypeToken() {
						return new TypeToken<List<AddressManager>>() {
						};
					}
				});

	}

	protected void changepaw() {
		Config.changePaw(mActivity, customerId,
				StringUtil.Md5(et_oldpaw.getText().toString()),
				StringUtil.Md5(et_newpaw.getText().toString()),
				new HttpCallback(mActivity) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(mActivity, "密码修改成功");
						et_oldpaw.setText("");
						et_newpaw.setText("");
						et_confirmpaw.setText("");
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
	}

//	protected String findcity(int id) {
//		String a = "苏州";
//		List<Province> provinces = CommonUtil
//				.readProvincesAndCities(getActivity());
//		for (Province province : provinces) {
//			List<City> cities = province.getCities();
//
//			mCities.addAll(cities);
//
//		}
//		for (City cc : mCities) {
//			if (cc.getId() == id) {
//				a = cc.getName();
//			}
//		}
//		return a;
//	}
	private void openimg(int tag) {
		AlertDialog.Builder build = new AlertDialog.Builder(mActivity);
		LayoutInflater factory = LayoutInflater.from(mActivity);
		final View textEntryView = factory.inflate(R.layout.img, null);
		// build.setTitle("自定义输入框");
		build.setView(textEntryView);
		final ImageView view = (ImageView) textEntryView
				.findViewById(R.id.imag);
		int ppp = tag - 1;
		if (imgPath[ppp].equals("http://121.40.84.2:8888/")) {
			CommonUtil.toastShort(mActivity, "个人信息不完善，未上传照片");
			return;
		}
		ImageCacheUtil.IMAGE_CACHE.get(imgPath[ppp], view);
		// ImageCacheUtil.IMAGE_CACHE.get("http://www.sinaimg.cn/dy/slidenews/2_img/2015_16/789_1481006_298613.jpg",
		// view);
		build.create().show();
	}
	private void changeAddress() {
		cityId = Config.city.getId();
		Config.changeAdres(mActivity, idd[AddressManagerAdapter.pp], String
				.valueOf(cityId), login_edit_name.getText().toString(),
				mobile_phone.getText().toString(), zip_code.getText()
						.toString(), detail_address.getText().toString(), id,
				isDefault, new HttpCallback(mActivity) {
					@Override
					public void onSuccess(Object data) {
						isEdit = false;
						CommonUtil.toastShort(mActivity, "修改地址成功");
						if (dataadress.size() != 0) {
							dataadress.clear();
						}
						addressgetData();					
					}
					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}
					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
	}
	private void addAddresss() {
		Config.AddAdress(mActivity, String.valueOf(cityId), login_edit_name
				.getText().toString(), mobile_phone.getText().toString(),
				zip_code.getText().toString(), detail_address.getText()
						.toString(), isDefault, id,
				new HttpCallback(mActivity) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(mActivity, "添加地址成功");
						myHandler.sendEmptyMessage(1);					
					}
					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
	}

	private void opendialog() {
		builder = new AlertDialog.Builder(mActivity);
		LayoutInflater factory = LayoutInflater.from(mActivity);
		 final View textEntryView = factory.inflate(R.layout.addaddress, null);
		// builder.setTitle("自定义输入框");
		builder.setView(textEntryView);
		login_edit_name = (EditText) textEntryView
				.findViewById(R.id.login_edit_name);
		mobile_phone = (EditText) textEntryView.findViewById(R.id.mobile_phone);
		zip_code = (EditText) textEntryView.findViewById(R.id.zip_code);
		detail_address = (EditText) textEntryView
				.findViewById(R.id.detail_address);
		area = (TextView) textEntryView.findViewById(R.id.area);
		btn_save = (Button) textEntryView.findViewById(R.id.btn_save);
		cb = (CheckBox) textEntryView.findViewById(R.id.cb);
		deleteadress = (TextView) textEntryView.findViewById(R.id.deleteadress);
		tv_title = (TextView) textEntryView.findViewById(R.id.tv_title);
		close = (Button) textEntryView.findViewById(R.id.close);
		deleteadress.setOnClickListener(this);
		close.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		area.setOnClickListener(this);
		// final AlertDialog dialog = builder.show();
		// dialog=builder.show();
		// builder.create().show();
		if (isEdit) {
			tv_title.setText("编辑地址");
			deleteadress.setVisibility(View.VISIBLE);
			login_edit_name.setText(dataadress.get(AddressManagerAdapter.pp)
					.getReceiver());
			mobile_phone.setText(dataadress.get(AddressManagerAdapter.pp)
					.getMoblephone());
			zip_code.setText(dataadress.get(AddressManagerAdapter.pp)
					.getZipCode());
			detail_address.setText(dataadress.get(AddressManagerAdapter.pp)
					.getAddress());
			area.setText(dataadress.get(AddressManagerAdapter.pp).getCity());
			//cityId = dataadress.get(AddressManagerAdapter.pp).getCityId();
			if (dataadress.get(AddressManagerAdapter.pp).getIsDefault()
					.equals("默认")) {
				cb.setBackgroundResource(R.drawable.cb_y);
				isDefault = 1;
			} else {
				cb.setBackgroundResource(R.drawable.cb_n);
				isDefault = 2;
			}

		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
				if (isCheck) {
					isDefault = 1;
					cb.setBackgroundResource(R.drawable.cb_y);
				} else {
					isDefault = 2;
					cb.setBackgroundResource(R.drawable.cb_n);
				}

			}
		});
		dialog = builder.create();
		dialog.show();
	}

	protected void delect() {
		Config.delectAddress(mActivity, idd[AddressManagerAdapter.pp],
				new HttpCallback(mActivity) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(mActivity, "删除地址成功");
						if (dataadress.size() != 0) {
							dataadress.clear();
						}
						addressgetData();
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// case REQUEST_CITY:
		// com.example.zf_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
		case com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
			if (CityProvinceActivity.isClickconfirm) {
				City mMerchantCity = (City) data
						.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY);
				//cityId = mMerchantCity.getId();
				area.setText(mMerchantCity.getName());
				Log.e("1", area.getText().toString());
				CityProvinceActivity.isClickconfirm = false;
				MainActivity.isCity = true;
				Log.e("1", "1");
				type = 99;
				/*
				 * cityId = data.getIntExtra(CITY_ID, 0); cityName =
				 * data.getStringExtra(CITY_NAME);
				 * tv_city_select.setText(cityName);
				 */
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
@Override
public void onDestroy() {
	super.onDestroy();
}
	@Override
	public void onDestroyView() {
		
		try {
			/*
			 * if (view != null) { ViewGroup parent = (ViewGroup)
			 * view.getParent(); if (parent != null)
			 * parent.removeAllViewsInLayout(); }
			 */
			/*mRecordType = 0;
			FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			// getChildFragmentManager().beginTransaction();
			if (m_baseinfo != null)
				transaction.remove(m_baseinfo);
			if (m_chpaw != null)
				transaction.remove(m_chpaw);
			if (m_adress != null)
				transaction.remove(m_adress);
			transaction.commit();*/
		} catch (Exception e) {
		}
		super.onDestroyView();
	}
	 
	
	
}

