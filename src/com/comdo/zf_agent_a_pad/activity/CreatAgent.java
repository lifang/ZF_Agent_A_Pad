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

import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.CityProvinceActivity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.RegText;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreatAgent extends BaseActivity implements OnClickListener{
	private EditText et_company_type,et_company_name,
	et_business_license,et_tax_registered_no,et_fuze_name,et_fuze_id_no,
	et_phone,et_email,et_address_detail,et_login_id,et_paw,et_paw1;
	private TextView tv_address;
	private Button btn_uplode_card,btn_uplode_business,btn_uplode_tax,btn_save;
	private ImageView iv_fenrun;
	private boolean isprofit=true;
	private int count=0;
	private int agentType;
	private int tag;
	 private boolean iscamera=false;
	 private File file;
	 private String localCameraPath = "";
	 private String[] imgLocalPath=new String[3];
	 private String localSelectPath;
	 private String cardPhotoPath,licensePhotoPath,taxPhotoPath;
	 private String picPath;
	 private Handler myHandler;
	 private JSONObject json;
	 private int cityId;
	 private int isProfit;
	 private AlertDialog dialog;
	 private int agentId=MyApplication.NewUser.getAgentId();
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.creatagent);
	new TitleMenuUtil(CreatAgent.this, "创建代理商详情").show();
	init();
}
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
    iv_fenrun.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(count%2==0){
				iv_fenrun.setBackgroundResource(R.drawable.agent_on);
				isProfit=2;
			}
			else{
				iv_fenrun.setBackgroundResource(R.drawable.agent_off);
				isProfit=1;
			}
			count++;
		}
	});
    myHandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
    		
			case 0:
				
				switch (tag) {
				case 1:
					
					try {
						cardPhotoPath=json.getString("result");
						btn_uplode_card.setText("");
						btn_uplode_card.setBackgroundResource(R.drawable.check_it);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					try {
						licensePhotoPath=json.getString("result");
						btn_uplode_business.setText("");
						btn_uplode_business.setBackgroundResource(R.drawable.check_it);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						taxPhotoPath=json.getString("result");
						btn_uplode_tax.setText("");
						btn_uplode_tax.setBackgroundResource(R.drawable.check_it);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
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
	// TODO Auto-generated method stub
	super.onDestroy();
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_uplode_card:
		tag=1;
		showchooseDialog(btn_uplode_card,tag);
		break;
	case R.id.btn_uplode_business:
		tag=2;
		showchooseDialog(btn_uplode_business,tag);
		break;
	case R.id.btn_uplode_tax:
		tag=3;
		showchooseDialog(btn_uplode_tax,tag);
		break;
	case R.id.btn_save:
		try {
			
			boolean isId=checkId(et_fuze_id_no.getText().toString());
			boolean isPhone=RegText.isMobileNO(et_phone.getText().toString());
			 boolean isEmail=checkEmail();
			if(!isId){
				CommonUtil.toastShort(getApplicationContext(), "身份证号码格式不正确");
				return;
			}
			if(!isPhone){
				CommonUtil.toastShort(getApplicationContext(), "手机号码格式不正确");
				return;
			}
			if(!isEmail){
				CommonUtil.toastShort(getApplicationContext(), "邮箱格式不正确");
				return;
			}
			if(et_paw.getText().toString().length()<6){
				CommonUtil.toastShort(getApplicationContext(), "密码至少六位");
				return;
			}
			if(!et_paw1.getText().toString().equals(et_paw.getText().toString())){
				CommonUtil.toastShort(getApplicationContext(), "确认密码与原密码不一致");
				return;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  
		submit();
		break;
	case R.id.tv_address:
		Intent intent = new Intent(CreatAgent.this,
				CityProvinceActivity.class);
		//intent.putExtra(CITY_NAME, cityName);
		//startActivityForResult(intent, REQUEST_CITY);
		startActivityForResult(intent, com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY);
		break;
	default:
		break;
	}
	
}
private boolean  checkId(String IDStr) throws ParseException {
	String errorInfo = "";//记录错误信息
    String[] ValCodeArr = {"1","0","x","9","8","7","6","5","4","3","2"};
    String[] Wi = {"7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"};
    //String[] Checker = {"1","9","8","7","6","5","4","3","2","1","1"};
    String Ai="";

    //================ 号码的长度 15位或18位 ================
    if(IDStr.length()!=15 && IDStr.length()!=18)
    {
     errorInfo="号码长度应该为15位或18位。";
     System.out.println(errorInfo);
     return false;
    }
    //=======================(end)======================== 


    //================ 数字 除最后以为都为数字 ================
    if(IDStr.length()==18)
    {
     Ai=IDStr.substring(0,17);
    }
    else if(IDStr.length()==15)
   {
     Ai=IDStr.substring(0,6)+"19"+IDStr.substring(6,15);   
    }
    if(isNumeric(Ai)==false)
    {
     errorInfo="15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
     System.out.println(errorInfo);
     return false;
    }
    //=======================(end)========================


    //================ 出生年月是否有效 ================
    String strYear =Ai.substring(6 ,10);//年份
    String strMonth=Ai.substring(10,12);//月份
    String strDay        =Ai.substring(12,14);//月份

    if(this.isDate(strYear+"-"+strMonth+"-"+strDay)==false)
    {
     errorInfo="生日无效。";
     System.out.println(errorInfo);
     return false;
    }

    GregorianCalendar gc=new GregorianCalendar();  
    SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
    if((gc.get(Calendar.YEAR)-Integer.parseInt(strYear))>150 || (gc.getTime().getTime()-s.parse(strYear+"-"+strMonth+"-"+strDay).getTime())<0)
    {
     errorInfo="生日不在有效范围。";
     System.out.println(errorInfo);
     return false;
    }
    if(Integer.parseInt(strMonth)>12 || Integer.parseInt(strMonth)==0)
    {
     errorInfo="月份无效";
     System.out.println(errorInfo);
     return false;
    }
    if(Integer.parseInt(strDay)>31 || Integer.parseInt(strDay)==0)
    {
     errorInfo="日期无效";
     System.out.println(errorInfo);
     return false;
    }
    //=====================(end)=====================


    //================ 地区码时候有效 ================
    Hashtable h=GetAreaCode();
    if(h.get(Ai.substring(0,2))==null)
    {
     errorInfo="地区编码错误。";
     System.out.println(errorInfo);
     return false;
    }
    //==============================================


    //================ 判断最后一位的值 ================
    int TotalmulAiWi=0;
    for(int i=0 ; i<17 ; i++)
    {
     TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
    }
    int modValue=TotalmulAiWi % 11;
    String strVerifyCode=ValCodeArr[modValue];
    Ai=Ai+strVerifyCode;

    if(IDStr.length()==18)
    {   
     if(Ai.equals(IDStr)==false)
     {
      errorInfo="身份证无效，最后一位字母错误";
      System.out.println(errorInfo);
      return false;
     }
    }
    else
    { 
     System.out.println("所在地区:"+h.get(Ai.substring(0,2).toString()));
     System.out.println("新身份证号:"+Ai);
     return true;
    }
    //=====================(end)=====================
    System.out.println("所在地区:"+h.get(Ai.substring(0,2).toString()));
    return true;
	
}
private boolean isDate(String strDate) {
	Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
			"[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" +
			"((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))" +
			"[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|" +
			"(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
			"(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
    Matcher m=pattern.matcher(strDate);
    if(m.matches())
    {
     return true;
    }
    else
    {
     return false;
    }
}
private boolean isNumeric(String str) {
	Pattern pattern=Pattern.compile("[0-9]*");
    Matcher isNum=pattern.matcher(str);
    if(isNum.matches())
    {
     return true;
    }
    else
    {
     return false;
    }
    /**//*判断一个字符时候为数字
    if(Character.isDigit(str.charAt(0)))
    {
     return true;
    }
    else
    {
     return false;
    }*/ 
}
private Hashtable GetAreaCode() {
	 Hashtable hashtable=new Hashtable();
     hashtable.put("11","北京");
     hashtable.put("12","天津");
     hashtable.put("13","河北");
     hashtable.put("14","山西");
     hashtable.put("15","内蒙古");
     hashtable.put("21","辽宁");
     hashtable.put("22","吉林");
     hashtable.put("23","黑龙江");
     hashtable.put("31","上海");
     hashtable.put("32","江苏");
     hashtable.put("33","浙江");
     hashtable.put("34","安徽");
     hashtable.put("35","福建");
     hashtable.put("36","江西");
     hashtable.put("37","山东");
     hashtable.put("41","河南");
     hashtable.put("42","湖北");
     hashtable.put("43","湖南");
     hashtable.put("44","广东");
     hashtable.put("45","广西");
     hashtable.put("46","海南");
     hashtable.put("50","重庆");
     hashtable.put("51","四川");
     hashtable.put("52","贵州");
     hashtable.put("53","云南");
     hashtable.put("54","西藏");
     hashtable.put("61","陕西");
     hashtable.put("62","甘肃");
     hashtable.put("63","青海");
     hashtable.put("64","宁夏");
     hashtable.put("65","新疆");
     hashtable.put("71","台湾");
     hashtable.put("81","香港");
     hashtable.put("82","澳门");
     hashtable.put("91","国外");
     return hashtable;
}
private boolean checkEmail() {
	 Pattern pattern = Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
     Matcher matcher = pattern.matcher(et_email.getText().toString());
     if (matcher.matches()) {
         return true;
     }
     return false;
	
}
private boolean  checkPhone() {
	 Pattern pattern = Pattern.compile("^13\\d{9}||15[8,9]\\d{8}$");
     Matcher matcher = pattern.matcher(et_phone.getText().toString());
     
     if (matcher.matches()) {
         return true;
     }
     return false;
	
}
private void submit() {
	if(et_company_type.getText().toString().equals("公司")){
		agentType=1;
	}
	else{
		agentType=2;
	}
	
	Config.creatAgent(CreatAgent.this, 
			agentId, 
			et_login_id.getText().toString(), 
			agentType, 
			et_fuze_name.getText().toString(), 
			et_fuze_id_no.getText().toString(), 
			et_company_name.getText().toString(), 
			et_business_license.getText().toString(), 
			et_phone.getText().toString(), 
			et_email.getText().toString(), 
			et_address_detail.getText().toString(), 
			et_paw.getText().toString(), 
			et_paw1.getText().toString(), 
			isProfit, 
			cityId, 
			cardPhotoPath, 
			licensePhotoPath, 
			taxPhotoPath, 
			et_tax_registered_no.getText().toString(),false, new HttpCallback(CreatAgent.this) {

				@Override
				public void onSuccess(Object data) {
					//CommonUtil.toastShort(getApplicationContext(), "创建代理商成功");
					finish();
					
				}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
				@Override
				public TypeToken getTypeToken() {
					// TODO Auto-generated method stub
					return null;
				}
			});
	
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.REQUEST_CHOOSE_CITY:
		if(CityProvinceActivity.isClickconfirm){
			City mMerchantCity = (City) data.getSerializableExtra(com.comdo.zf_agent_a_pad.fragment.Constants.CityIntent.SELECTED_CITY);
			cityId=mMerchantCity.getId() ;
			tv_address.setText(mMerchantCity.getName());
			Log.e("1", tv_address.getText().toString());
			CityProvinceActivity.isClickconfirm=false;
			/*cityId = data.getIntExtra(CITY_ID, 0);
			cityName = data.getStringExtra(CITY_NAME);
			tv_city_select.setText(cityName);*/
		}
		
		break;
	case 1001:
		switch (tag) {
		case 1:
			
			try {
				imgLocalPath[0]=localCameraPath;
				uploadFile(localCameraPath,tag,btn_uplode_card);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
        case 2:
        	try {
				imgLocalPath[1]=localCameraPath;
				uploadFile(localCameraPath,tag,btn_uplode_business);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
        case 3:
        	try {
				imgLocalPath[2]=localCameraPath;
				uploadFile(localCameraPath,tag,btn_uplode_tax);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
                Cursor cursor = getContentResolver().query(  
                        selectedImage, null, null, null, null);  
                cursor.moveToFirst();  
                int columnIndex = cursor.getColumnIndex("_data");  
                localSelectPath = cursor.getString(columnIndex);  
                Log.e("localSelectPath", localSelectPath);
                cursor.close();
		switch (tag) {
		case 1:
			
			try {
				imgLocalPath[0]=localSelectPath;
				uploadFile(localSelectPath,tag,btn_uplode_card);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
        case 2:
        	try {
				imgLocalPath[1]=localSelectPath;
				uploadFile(localSelectPath,tag,btn_uplode_business);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
        case 3:
        	try {
				imgLocalPath[2]=localSelectPath;
				uploadFile(localSelectPath,tag,btn_uplode_tax);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
private void uploadFile(String path,final int tag,final Button btn) throws Exception{

	File file=new File(path);
	if (file.exists() && file.length() > 0) {
		
		File img = new File(path);
		/*Config.uploadpic(CreatAgent.this, agentId, img, new HttpCallback(CreatAgent.this) {

			@Override
			public void onSuccess(Object data) {
				// TODO Auto-generated method stub
			  String dd=String.valueOf(data);
			  try {
				  dialog.dismiss();
					CommonUtil.toastShort(getApplicationContext(), "上传成功") ;
				json=new JSONObject(dd);
				picPath=json.getString("result");
				myHandler.sendEmptyMessage(0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
@Override
public void onFailure(String message) {
	// TODO Auto-generated method stub
	super.onFailure(message);
}
			@Override
			public TypeToken getTypeToken() {
				// TODO Auto-generated method stub
				return null;
			}
		});*/
		RequestParams params=new RequestParams();
		params.put("img", img);
		//params.put("agentsId", agentId);
		//params.setUseJsonStreamer(true);
		AsyncHttpClient client=new AsyncHttpClient();
		client.setTimeout(10000);// 设置超时时间
    	client.setMaxConnections(10);
		client.post(Config.UPLOAD_FILE+agentId, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					dialog.dismiss();
					CommonUtil.toastShort(getApplicationContext(), "上传成功") ;
					String str=new String(responseBody);
					json=new JSONObject(str);
					picPath=json.getString("result");
					myHandler.sendEmptyMessage(0);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				//String str=new String(responseBody);
				//Log.e("str", str);
			}
		});
	}
	else{
		CommonUtil.toastShort(getApplicationContext(), "文件不存在");
	}
	

	
}
private void showchooseDialog(Button btn,final int tag) {
	// TODO Auto-generated method stub

	final AlertDialog.Builder builder = new AlertDialog.Builder(CreatAgent.this);
	LayoutInflater factory = LayoutInflater.from(this);
	final View textEntryView = factory.inflate(R.layout.choosedialog, null);
	// builder.setTitle("自定义输入框");
     builder.setView(textEntryView);
     final Button seeimg=(Button) textEntryView.findViewById(R.id.seeimg);
     final View line_one=textEntryView.findViewById(R.id.line_one);
     final Button choosealbum=(Button) textEntryView.findViewById(R.id.choosealbum);
     final Button choosecamera=(Button) textEntryView.findViewById(R.id.choosecamera);
    
     //final AlertDialog dialog = builder.show();
     //dialog=builder.show();
     if(btn.getText().toString().equals("")){
    	 seeimg.setVisibility(View.VISIBLE);
    	 line_one.setVisibility(View.VISIBLE);
     }
     choosealbum.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			openAlbum();
			// TODO Auto-generated method stub
		/*	iscamera=false;
			choosealbum.setBackgroundColor(Color.BLUE);
			choosecamera.setBackgroundColor(Color.WHITE);*/
			//dialog.dismiss();
		}
	});
     choosecamera.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 opencamera();
			/*iscamera=true;
			choosecamera.setBackgroundColor(Color.BLUE);
			choosealbum.setBackgroundColor(Color.WHITE);*/
			//dialog.dismiss();
		}
	});
     seeimg.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			dialog.dismiss();
 				Intent intent = new Intent(Intent.ACTION_VIEW);    
 	            intent.setDataAndType(Uri.parse("file://"+imgLocalPath[tag-1]), "image/*"); 
 	            startActivity(intent);
 			//dialog.dismiss();
 			//builder.show().dismiss(); 
 		}
 	});
    // builder.create().show();
     dialog=builder.create();
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
	 File dir = new File(Environment.getExternalStorageDirectory()+"/uploadFile/user/8/");  
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
   startActivityForResult(openCameraIntent,  
           1001);
	
}
private void init() {
	btn_save=(Button) findViewById(R.id.btn_save);
	iv_fenrun=(ImageView) findViewById(R.id.iv_fenrun);
	et_company_type=(EditText) findViewById(R.id.et_company_type);
	et_company_name=(EditText) findViewById(R.id.et_company_name);
	et_business_license=(EditText) findViewById(R.id.et_business_license);
	et_tax_registered_no=(EditText) findViewById(R.id.et_tax_registered_no);
	et_fuze_name=(EditText) findViewById(R.id.et_fuze_name);
	et_fuze_id_no=(EditText) findViewById(R.id.et_fuze_id_no);
	et_phone=(EditText) findViewById(R.id.et_phone);
	et_email=(EditText) findViewById(R.id.et_email);
	tv_address=(TextView) findViewById(R.id.tv_address);
	et_address_detail=(EditText) findViewById(R.id.et_address_detail);
	btn_uplode_card=(Button) findViewById(R.id.btn_uplode_card);
	btn_uplode_business=(Button) findViewById(R.id.btn_uplode_business);
	btn_uplode_tax=(Button) findViewById(R.id.btn_uplode_tax);
	et_login_id=(EditText) findViewById(R.id.et_login_id);
	et_paw=(EditText) findViewById(R.id.et_paw);
	et_paw1=(EditText) findViewById(R.id.et_paw1);
	tv_address.setOnClickListener(this);
	btn_uplode_card.setOnClickListener(this);
	btn_uplode_business.setOnClickListener(this);
	btn_uplode_tax.setOnClickListener(this);
	btn_save.setOnClickListener(this);
	
}
}


