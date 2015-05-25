package com.comdo.zf_agent_a_pad.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.events.Events;
import com.epalmpay.agentPad.R;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

public class BaseActivity extends Activity implements View.OnClickListener {

    protected String TAG = getClass().toString();
	protected Context mContext;

    HashMap<String, Class> clickableMap = new HashMap<String, Class>();
    private static final ArrayList<String> tabImages = new ArrayList<String>() {{
        add("home");
        add("product");
        add("message");
        add("mine");
    }};

    public HashMap<String, Class> getClickableMap() {
        return clickableMap;
    }

    public void setClickableMap(HashMap<String, Class> clickableMap) {
        this.clickableMap.putAll(clickableMap);
    }

    protected void bindClickListener() {

        if (clickableMap == null) {
            return;
        }

        for (String strId : clickableMap.keySet()) {
            int resouceId = resouceId(strId, "id");
            Log.d("BaseActivity", "" + resouceId + "" + strId);
            View view = (View) findViewById(resouceId);
            view.setOnClickListener(this);
        }

    }

    protected Class matchedClass(View v) {
        Class activity = null;
        if (null == clickableMap) {
            return activity;
        }
        for (Map.Entry<String, Class> entry : clickableMap.entrySet()) {
            String strId = entry.getKey();
            int resouceId = resouceId(strId, "id");
            if (v.getId() == resouceId) {
                activity = entry.getValue();
                break;
            }

        }
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        } catch (RuntimeException ex) {
            Log.d("UNCatchException", ex.getMessage());
        }
        
        
        mContext = this;
        
        
        MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
//		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		MobclickAgent.updateOnlineConfig(this);
		Log.e("------", getDeviceInfo(this));
    }

    @Override
	protected void onDestroy() {
		//getRequests().cancelAll(this);
        EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		MobclickAgent.onPageEnd( mContext.toString() );
		MobclickAgent.onPause(mContext);
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	StatService.onResume(this);
		MobclickAgent.onPageStart(mContext.toString());
		MobclickAgent.onResume(mContext);
		
	}

    @Override
    public void onClick(View v) {
        Class activity = this.matchedClass(v);
        if (activity != null) {
            startActivity(new Intent(this, activity));
//            overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);

        }
    }

    protected int resouceId(String name, String kind) {
        return this.getResources().getIdentifier(name, kind, this.getPackageName());
    }

    protected void focusTabAtIndex(int index) {
        int bottomTabContainerId = resouceId("bottom_tab_container", "id");
        if (bottomTabContainerId > 0) {
//            LinearLayout viewGroup = (LinearLayout)findViewById(R.id.bottom_tab_line);
//            View view = viewGroup.getChildAt(index);
//            view.setBackgroundResource(R.color.bgtitle);
//
//            int tabId = resouceId("tab_index" + index, "id");
//            viewGroup = (LinearLayout)findViewById(tabId);
//            ImageView imageView = (ImageView)viewGroup.getChildAt(0);
//
//            int resouceId;
//            resouceId = resouceId(tabImages.get(index), "drawable");
//            imageView.setBackgroundResource(resouceId);
//
//            TextView tv = (TextView)viewGroup.getChildAt(1);
//            tv.setTextColor(getResources().getColor(R.color.bgtitle));

        }
    }

    protected void setupCommonViews() {
        int bottomTabContainerId = resouceId("bottom_tab_container", "id");
        if (bottomTabContainerId > 0) {
//            HashMap<String, Class> clickableMap = new HashMap<String, Class>(){{
//                put("tab_index2", SystemMessage.class);
//                put("tab_index3", MenuMine.class);
//                put("tab_index0", Main.class);
//                put("tab_index1", AllProduct.class);
//            }};
//            this.setClickableMap(clickableMap);
//            this.bindClickListener();
        }
    }

    //event listener
    public void onEventMainThread(Events.NoConnectEvent event) {
        Toast.makeText(getApplicationContext(),
                "没有网络连接",
                Toast.LENGTH_SHORT).show();
    }
    public void onEventMainThread(Events.RefreshToMuch event) {
        Toast.makeText(getApplicationContext(),
                "刷新太频繁",
                Toast.LENGTH_SHORT).show();
    }

    //helper
    protected void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }
    
    public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
