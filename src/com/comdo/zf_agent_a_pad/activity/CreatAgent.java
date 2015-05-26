package com.comdo.zf_agent_a_pad.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CreatAgent extends BaseActivity implements OnClickListener {
	private EditText et_company_type, et_company_name, et_business_license,
			et_tax_registered_no, et_fuze_name, et_fuze_id_no, et_phone,
			et_email, et_address_detail, et_login_id, et_paw, et_paw1;
	private TextView tv_address;
	private Button btn_uplode_card, btn_uplode_business, btn_uplode_tax,
			btn_save;
	private TableRow tr_company_name, tr_business_license,
			tr_tax_registered_no, tr_yyzz, tr_swdj;
	private ImageView iv_fenrun;
	private boolean isprofit = true;
	private int count = 0;
	private int agentType;
	private int tag;
	private boolean iscamera = false;
	private File file;
	private String localCameraPath = "";
	private String[] imgLocalPath = new String[3];
	private String localSelectPath;
	private String cardPhotoPath, licensePhotoPath, taxPhotoPath;
	private String picPath;
	private Handler myHandler;
	private JSONObject json;
	private int cityId;
	private int isProfit;
	private AlertDialog dialog;
	private int agentId = MyApplication.NewUser.getAgentId();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creatagent);
		new TitleMenuUtil(CreatAgent.this, "创建代理商详情").show();
		init();
	}

	@Override
	protected void onStart() {
		super.onStart();
		iv_fenrun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (count % 2 == 0) {
					iv_fenrun.setBackgroundResource(R.drawable.agent_on);
					isProfit = 2;
				} else {
					iv_fenrun.setBackgroundResource(R.drawable.agent_off);
					isProfit = 1;
				}
				count++;
			}
		});
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {

				case 0:

					switch (tag) {
					case 1:

						try {
							cardPhotoPath = json.getString("result");
							btn_uplode_card.setText("");
							btn_uplode_card
									.setBackgroundResource(R.drawable.check_it);
						} catch (JSONException e) {
							e.printStackTrace();
						}

						break;
					case 2:
						try {
							licensePhotoPath = json.getString("result");
							btn_uplode_business.setText("");
							btn_uplode_business
									.setBackgroundResource(R.drawable.check_it);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					case 3:
						try {
							taxPhotoPath = json.getString("result");
							btn_uplode_tax.setText("");
							btn_uplode_tax
									.setBackgroundResource(R.drawable.check_it);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
					break;

				default:
					break;
				}
			};

		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_uplode_card:
			tag = 1;
			showchooseDialog(btn_uplode_card, tag);
			break;
		case R.id.btn_uplode_business:
			tag = 2;
			showchooseDialog(btn_uplode_business, tag);
			break;
		case R.id.btn_uplode_tax:
			tag = 3;
			showchooseDialog(btn_uplode_tax, tag);
			break;
		case R.id.btn_save:
			submit();
			break;
		case R.id.tv_address:
			Intent intent = new Intent(CreatAgent.this,
					CityProvinceActivity.class);
			startActivityForResult(
					intent,
					com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
			break;
		default:
			break;
		}

	}

	private void submit() {

		if (agentType == 1) {
			if ("".equals(et_company_name.getText().toString())) {
				CommonUtil.toastShort(CreatAgent.this, "公司名称不能为空");
				return;

			} else if ("".equals(et_business_license.getText().toString())) {

				CommonUtil.toastShort(CreatAgent.this, "公司营业执照登记号不能为空");
				return;
			} else if ("".equals(et_tax_registered_no.getText().toString())) {

				CommonUtil.toastShort(CreatAgent.this, "公司税务登记证号不能为空");
				return;
			} else if ("".equals(licensePhotoPath) || null == licensePhotoPath) {

				CommonUtil.toastShort(CreatAgent.this, "请上传营业执照照片");
				return;
			} else if ("".equals(taxPhotoPath) || null == taxPhotoPath) {

				CommonUtil.toastShort(CreatAgent.this, "请上传税务登记证照片");
				return;
			}
		}
		if ("".equals(et_fuze_name.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "负责人姓名不能为空");
		} else if ("".equals(et_fuze_id_no.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "负责人身份证号不能为空");
		} else if ("".equals(et_phone.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "手机不能为空");
		} else if ("".equals(et_email.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "邮箱不能为空");
		} else if (cityId == 0) {

			CommonUtil.toastShort(CreatAgent.this, "未选择所在地");
		} else if ("".equals(et_address_detail.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "详细地址不能为空");
		} else if ("".equals(et_login_id.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "用户名不能为空");
		} else if ("".equals(et_paw.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "登录密码不能为空");
		} else if (et_paw.getText().toString().trim().length() < 6
				|| et_paw.getText().toString().trim().length() > 20) {

			CommonUtil.toastShort(CreatAgent.this, "密码应为6-20位");
		} else if ("".equals(et_paw1.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "确认密码不能为空");
		} else if ("".equals(cardPhotoPath) || null == cardPhotoPath) {

			CommonUtil.toastShort(CreatAgent.this, "请上传身份证照片");
		} else if (!et_paw.getText().toString()
				.equals(et_paw1.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "登录密码与确认密码不一致");
		} else if (!RegText.isMobileNO(et_phone.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "手机号码格式不正确");
		} else if (!RegText.isEmail(et_email.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "邮箱格式不正确");
		} else if (!RegText.isIdentityCard(et_fuze_id_no.getText().toString())) {

			CommonUtil.toastShort(CreatAgent.this, "负责人身份证号格式不正确");
		} else {
			Config.creatAgent(CreatAgent.this, agentId, et_login_id.getText()
					.toString(), agentType, et_fuze_name.getText().toString(),
					et_fuze_id_no.getText().toString(), et_company_name
							.getText().toString(), et_business_license
							.getText().toString(), et_phone.getText()
							.toString(), et_email.getText().toString(),
					et_address_detail.getText().toString(), et_paw.getText()
							.toString(), et_paw1.getText().toString(),
					isProfit, cityId, cardPhotoPath, licensePhotoPath,
					taxPhotoPath, et_tax_registered_no.getText().toString(),
					false, new HttpCallback(CreatAgent.this) {

						@Override
						public void onSuccess(Object data) {
							// CommonUtil.toastShort(getApplicationContext(),
							// "创建代理商成功");
							finish();

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

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
			if (CityProvinceActivity.isClickconfirm) {
				City mMerchantCity = (City) data
						.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY);
				cityId = mMerchantCity.getId();
				tv_address.setText(mMerchantCity.getName());
				Log.e("1", tv_address.getText().toString());
				CityProvinceActivity.isClickconfirm = false;
				/*
				 * cityId = data.getIntExtra(CITY_ID, 0); cityName =
				 * data.getStringExtra(CITY_NAME);
				 * tv_city_select.setText(cityName);
				 */
			}

			break;
		case 1001:
			switch (tag) {
			case 1:

				try {
					imgLocalPath[0] = localCameraPath;
					uploadFile(localCameraPath, tag, btn_uplode_card);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					imgLocalPath[1] = localCameraPath;
					uploadFile(localCameraPath, tag, btn_uplode_business);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					imgLocalPath[2] = localCameraPath;
					uploadFile(localCameraPath, tag, btn_uplode_tax);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			break;
		case 1002:
			if (data != null) {
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					Cursor cursor = getContentResolver().query(selectedImage,
							null, null, null, null);
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex("_data");
					localSelectPath = cursor.getString(columnIndex);
					Log.e("localSelectPath", localSelectPath);
					cursor.close();
					switch (tag) {
					case 1:

						try {
							imgLocalPath[0] = localSelectPath;
							uploadFile(localSelectPath, tag, btn_uplode_card);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case 2:
						try {
							imgLocalPath[1] = localSelectPath;
							uploadFile(localSelectPath, tag,
									btn_uplode_business);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case 3:
						try {
							imgLocalPath[2] = localSelectPath;
							uploadFile(localSelectPath, tag, btn_uplode_tax);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
				}
			}
			break;

		default:
			break;
		}
	}

	private void uploadFile(String path, final int tag, final Button btn)
			throws Exception {

		dialog.dismiss();
		File file = new File(path);
		if (file.exists() && file.length() > 0) {

			File img = new File(path);
			/*
			 * Config.uploadpic(CreatAgent.this, agentId, img, new
			 * HttpCallback(CreatAgent.this) {
			 * 
			 * @Override public void onSuccess(Object data) { String
			 * dd=String.valueOf(data); try { dialog.dismiss();
			 * CommonUtil.toastShort(getApplicationContext(), "上传成功") ; json=new
			 * JSONObject(dd); picPath=json.getString("result");
			 * myHandler.sendEmptyMessage(0); } catch (JSONException e) {
			 * e.printStackTrace(); }
			 * 
			 * }
			 * 
			 * @Override public void onFailure(String message) { }
			 * 
			 * @Override public TypeToken getTypeToken() { } });
			 */
			RequestParams params = new RequestParams();
			params.put("img", img);
			// params.put("agentsId", agentId);
			// params.setUseJsonStreamer(true);
			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(10000);// 设置超时时间
			client.setMaxConnections(10);
			client.post(Config.UPLOAD_FILE + agentId, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							try {
								dialog.dismiss();
								CommonUtil.toastShort(getApplicationContext(),
										"上传成功");
								String str = new String(responseBody);
								json = new JSONObject(str);
								picPath = json.getString("result");
								myHandler.sendEmptyMessage(0);

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							dialog.dismiss();
							// String str=new String(responseBody);
							// Log.e("str", str);
						}
					});
		} else {
			CommonUtil.toastShort(getApplicationContext(), "文件不存在");
		}

	}

	private void showchooseDialog(Button btn, final int tag) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				CreatAgent.this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.choosedialog, null);
		// builder.setTitle("自定义输入框");
		builder.setView(textEntryView);
		final Button seeimg = (Button) textEntryView.findViewById(R.id.seeimg);
		final View line_one = textEntryView.findViewById(R.id.line_one);
		final Button choosealbum = (Button) textEntryView
				.findViewById(R.id.choosealbum);
		final Button choosecamera = (Button) textEntryView
				.findViewById(R.id.choosecamera);

		// final AlertDialog dialog = builder.show();
		// dialog=builder.show();
		if (btn.getText().toString().equals("")) {
			seeimg.setVisibility(View.VISIBLE);
			line_one.setVisibility(View.VISIBLE);
		}
		choosealbum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				openAlbum();
				/*
				 * iscamera=false; choosealbum.setBackgroundColor(Color.BLUE);
				 * choosecamera.setBackgroundColor(Color.WHITE);
				 */
				// dialog.dismiss();
			}
		});
		choosecamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				opencamera();
				/*
				 * iscamera=true; choosecamera.setBackgroundColor(Color.BLUE);
				 * choosealbum.setBackgroundColor(Color.WHITE);
				 */
				// dialog.dismiss();
			}
		});
		seeimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(
						Uri.parse("file://" + imgLocalPath[tag - 1]), "image/*");
				startActivity(intent);
				// dialog.dismiss();
				// builder.show().dismiss();
			}
		});
		// builder.create().show();
		dialog = builder.create();
		dialog.show();
	}

	protected void openAlbum() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, 1002);

	}

	protected void opencamera() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File dir = new File(Environment.getExternalStorageDirectory()
				+ "/uploadFile/user/8/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		file = new File(dir, String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		localCameraPath = file.getPath();
		Log.e("", "getPath:" + file.getPath());
		// Log.e("localCameraPath22222", String.valueOf(file.getPath()));
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, 1001);

	}

	private void init() {
		btn_save = (Button) findViewById(R.id.btn_save);
		iv_fenrun = (ImageView) findViewById(R.id.iv_fenrun);
		et_company_name = (EditText) findViewById(R.id.et_company_name);
		et_business_license = (EditText) findViewById(R.id.et_business_license);
		et_tax_registered_no = (EditText) findViewById(R.id.et_tax_registered_no);
		et_fuze_name = (EditText) findViewById(R.id.et_fuze_name);
		et_fuze_id_no = (EditText) findViewById(R.id.et_fuze_id_no);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_email = (EditText) findViewById(R.id.et_email);
		tv_address = (TextView) findViewById(R.id.tv_address);
		et_address_detail = (EditText) findViewById(R.id.et_address_detail);
		btn_uplode_card = (Button) findViewById(R.id.btn_uplode_card);
		btn_uplode_business = (Button) findViewById(R.id.btn_uplode_business);
		btn_uplode_tax = (Button) findViewById(R.id.btn_uplode_tax);
		et_login_id = (EditText) findViewById(R.id.et_login_id);
		et_paw = (EditText) findViewById(R.id.et_paw);
		et_paw1 = (EditText) findViewById(R.id.et_paw1);
		tv_address.setOnClickListener(this);
		btn_uplode_card.setOnClickListener(this);
		btn_uplode_business.setOnClickListener(this);
		btn_uplode_tax.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		tr_company_name = (TableRow) findViewById(R.id.tr_company_name);
		tr_business_license = (TableRow) findViewById(R.id.tr_business_license);
		tr_tax_registered_no = (TableRow) findViewById(R.id.tr_tax_registered_no);
		tr_yyzz = (TableRow) findViewById(R.id.tr_yyzz);
		tr_swdj = (TableRow) findViewById(R.id.tr_swdj);
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				agentType = pos + 1;
				if (agentType == 1) {
					tr_company_name.setVisibility(View.VISIBLE);
					tr_business_license.setVisibility(View.VISIBLE);
					tr_tax_registered_no.setVisibility(View.VISIBLE);
					tr_yyzz.setVisibility(View.VISIBLE);
					tr_swdj.setVisibility(View.VISIBLE);
				} else if (agentType == 2) {

					tr_company_name.setVisibility(View.GONE);
					tr_business_license.setVisibility(View.GONE);
					tr_tax_registered_no.setVisibility(View.GONE);
					tr_yyzz.setVisibility(View.GONE);
					tr_swdj.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});

	}
}
