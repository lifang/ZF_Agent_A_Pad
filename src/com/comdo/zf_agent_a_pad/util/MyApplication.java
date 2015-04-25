package com.comdo.zf_agent_a_pad.util;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.entity.MyShopCar.Good;
import com.comdo.zf_agent_a_pad.entity.UserEntity;
import com.comdo.zf_agent_a_pad.trade.entity.City;
import com.comdo.zf_agent_a_pad.trade.entity.Province;
import com.example.zf_agent_a_pad.R;
import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;




public class MyApplication extends Application{
	public TextView mLocationResult,logMsg;
	private static MyApplication  mInstance=null;
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;
	public Boolean isLogin=false;
	//private ArrayList<Order> orderList = new ArrayList<Order>();

	private static  String versionCode="";
	private static int notifyId=0;
	private static Boolean isSelect=false;
	private static int CITYID=1;
	private List<City> mCities = new ArrayList<City>();
	public static int getCITYID() {
		return CITYID;
	}
	public static void setCITYID(int cITYID) {
		CITYID = cITYID;
	}
	public static Boolean getIsSelect() {
		return isSelect;
	}
	public static void setIsSelect(Boolean isSelect) {
		MyApplication.isSelect = isSelect;
	}
	public static int getNotifyId() {
		return notifyId;
	}
	public static void setNotifyId(int notifyId) {
		MyApplication.notifyId = notifyId;
	}
	public static String getVersionCode() {
		return versionCode;
	}
	public static void setVersionCode(String versionCode) {
		MyApplication.versionCode = versionCode;
	}

	AsyncHttpClient client = new AsyncHttpClient(); //  

	public AsyncHttpClient getClient() {
		//client.setTimeout(6000);
		client.setTimeout(10000);// 设置超时时间
		client.setMaxConnections(10);
		return client;
	}
	public void setClient(AsyncHttpClient client) {
		this.client = client;
	}
	public static List<Good> comfirmList=new LinkedList<Good>();

	public static List<Good> getComfirmList() {
		return comfirmList;
	}
	public static void setComfirmList(List<Good> comfirmList) {
		MyApplication.comfirmList = comfirmList;
	}
	private static String token="";

	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		MyApplication.token = token;
	}


	/**
	 * �洢��ǰ�û�������Ϣ,����welcome��ʼ���û���Ϣ
	 */

	//����list��������ÿһ��activity�ǹؼ�   
	private List<Activity> mList = new LinkedList<Activity>();   
	// add Activity     
	public void addActivity(Activity activity) {    
		mList.add(activity);    
	}    
	//�ر�ÿһ��list�ڵ�activity   
	public void exit() {    
		try {    
			for (Activity activity:mList) {    
				if (activity != null)    
					activity.finish();    
			}    
		} catch (Exception e) {    
			e.printStackTrace();    
		} 

	}  


	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		initImageLoader(mInstance);

		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);

		if(mySharedPreferences.getBoolean("islogin", false)){
			UserEntity ue=new UserEntity();
			ue.setId(mySharedPreferences.getInt("id", -1));
			this.NewUser=ue;
		}

	}
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getDisplayOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.moren)			// 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.moren)	// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.moren)		// 设置图片加载或解码过程中发生错误显示的图片	
		.cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
		.build();									// 创建配置过得DisplayImageOption对象	
		return options;
	}

	public static MyApplication getInstance() {
		return mInstance;
	}
	public static UserEntity NewUser = null;
	private SharedPreferences mySharedPreferences;


	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			//			sb.append("time : ");
			//			sb.append(location.getTime());
			//			sb.append("\nerror code : ");
			//			sb.append(location.getLocType());
			//			sb.append("\nlatitude : ");
			//			sb.append(location.getLatitude());
			//			sb.append("\nlontitude : ");
			//			sb.append(location.getLongitude());
			//			sb.append("\nradius : ");
			//			sb.append(location.getRadius());
			//			if (location.getLocType() == BDLocation.TypeGpsLocation){
			//				sb.append("\nspeed : ");
			//				sb.append(location.getSpeed());
			//				sb.append("\nsatellite : ");
			//				sb.append(location.getSatelliteNumber());
			//				sb.append("\ndirection : ");
			//				sb.append("\naddr : ");
			//				sb.append(location.getAddrStr());
			//				sb.append(location.getDirection());
			//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			//				sb.append("\naddr : ");
			//				sb.append(location.getAddrStr());
			//				//��Ӫ����Ϣ
			//				sb.append("\noperationers : ");
			//				sb.append(location.getOperators());
			//			}
			sb.append(location.getAddrStr());
			if(location.getCity()!=null&&!location.getCity().equals("")){
				Config.CITY=location.getCity();
				logMsg(location.getCity());
				List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
				for (Province province : provinces) {
					List<City> cities = province.getCities();

					mCities.addAll(cities);

				}
				for(City cc:mCities ){
					if(location.getCity()!=null&&!location.getCity().equals("")){
						if(cc.getName().endsWith(location.getCity())){
							System.out.println("当前城市 ID----"+cc.getId());
							setCITYID(cc.getId());
						}
					} 
				}
				Config.isFRIST=true;
				Log.i("BaiduLocationApiDem", sb.toString());
			}
		}
	}
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
			mLocationClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//隐藏软键盘
	public static void hideSoftKeyboard(Activity activity){
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
