package com.comdo.zf_agent_a_pad.fragment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.AfterSaleActivity;
import com.comdo.zf_agent_a_pad.activity.GoodDeatail;
import com.comdo.zf_agent_a_pad.activity.OrderList;
import com.comdo.zf_agent_a_pad.activity.PosListActivity;
import com.comdo.zf_agent_a_pad.activity.StockListActivity;
import com.comdo.zf_agent_a_pad.activity.TerminalManagerActivity;
import com.comdo.zf_agent_a_pad.activity.UserManageListActivity;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.JsonParser;
import com.comdo.zf_agent_a_pad.common.Response;
import com.comdo.zf_agent_a_pad.entity.PicEntity;
import com.comdo.zf_agent_a_pad.entity.VersionEntity;
import com.comdo.zf_agent_a_pad.trade.ApplyListActivity;
import com.comdo.zf_agent_a_pad.trade.TradeFlowActivity;
import com.comdo.zf_agent_a_pad.trade.common.CustomDialog;
import com.comdo.zf_agent_a_pad.util.CheckRights;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.DownloadUtils;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.epalmpay.agentPad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class M_MianFragment extends Fragment implements OnClickListener {
	private View view;
	private RelativeLayout main_rl_pos, main_rl_renzhen, main_rl_zdgl,
			main_rl_jyls, main_rl_Forum, main_rl_wylc, main_rl_xtgg,
			main_rl_lxwm;
	private static final int time = 5000;
	private TextView LocationResult;
	// vp
	private ArrayList<String> mal = new ArrayList<String>();
	private ArrayList<PicEntity> myList = new ArrayList<PicEntity>();
	private ViewPager view_pager;
	private ImageView[] indicator_imgs;
	private View item;
	private LayoutInflater inflater;
	private int index_ima = 0;
	private ArrayList<String> ma = new ArrayList<String>();
	List<View> list = new ArrayList<View>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				for (int i = 0; i < myList.size(); i++) {
					item = inflater.inflate(R.layout.item, null);
					list.add(item);
					ma.add(myList.get(i).getPicture_url());
				}
				indicator_imgs = new ImageView[ma.size()];
				initIndicator();
				adapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getActivity(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:

				break;
			case 4:
				index_ima++;
				index_ima = index_ima > list.size() - 1 ? 0 : index_ima;
				view_pager.setCurrentItem(index_ima);
				break;
			}
		}
	};
	private MyAdapter adapter;
	private Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = inflater.inflate(R.layout.f_main,container,false);
		if (view != null) {

			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {

			view = inflater.inflate(R.layout.f_main, container, false);

			initView();

			getdata();
		} catch (InflateException e) {

		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.main_rl_jyls: // 交易流水
			if (Config.CheckIsLogin(getActivity())) {


				if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_4)
					CommonUtil.toastShort(getActivity(),
							R.string.right_not_match);
				else
					startActivity(new Intent(getActivity(),
							TradeFlowActivity.class));

			}
			break;
		case R.id.main_rl_pos: // 我要进货

			if (CheckRights.IS_ERJI
					|| (!CheckRights.IS_YIJI && !CheckRights.RIGHT_1 && !CheckRights.RIGHT_2)) {
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			} else {

				startActivity(new Intent(getActivity(), PosListActivity.class));

			}

			break;

		case R.id.main_rl_renzhen: // 订单管理
			if (Config.CheckIsLogin(getActivity())) {

				
				if (CheckRights.IS_ERJI|| (!CheckRights.IS_YIJI && !CheckRights.RIGHT_1 && !CheckRights.RIGHT_2)) {
					CommonUtil.toastShort(getActivity(),
							R.string.right_not_match);
				} else {
					startActivity(new Intent(getActivity(), OrderList.class));
				}
			}
			break;
		case R.id.main_rl_xtgg: // 售后记录
			if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_3)
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			else
				startActivity(new Intent(getActivity(), AfterSaleActivity.class));
			break;
		case R.id.main_rl_wylc: // 用户管理
			if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_6)
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			else
				startActivity(new Intent(getActivity(),
						UserManageListActivity.class));
			break;
		case R.id.main_rl_Forum: // 库存管理
			if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_9)
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			else
				startActivity(new Intent(getActivity(), StockListActivity.class));

			
			break;

		case R.id.main_rl_lxwm://开通认证
			startActivity(new Intent(getActivity(), ApplyListActivity.class));
			break;
		case R.id.main_rl_zdgl: // 终端管理

			// if (Config.CheckIsLogin(getActivity())) {
			if (!CheckRights.IS_YIJI&&!CheckRights.IS_ERJI&&!CheckRights.RIGHT_3)
				CommonUtil.toastShort(getActivity(), R.string.right_not_match);
			else
				startActivity(new Intent(getActivity(),
						TerminalManagerActivity.class));

			break;
		default:
			break;
		}

	}

	private void initView() {

		main_rl_pos = (RelativeLayout) view.findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen = (RelativeLayout) view
				.findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl = (RelativeLayout) view.findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls = (RelativeLayout) view.findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum = (RelativeLayout) view.findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc = (RelativeLayout) view.findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm = (RelativeLayout) view.findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg = (RelativeLayout) view.findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);

		view_pager = (ViewPager) view.findViewById(R.id.view_pager);

		inflater = LayoutInflater.from(getActivity());
		adapter = new MyAdapter(list);

		view_pager.setAdapter(adapter);
		// 绑定动作监听器：如翻页的动画
		view_pager.setOnPageChangeListener(new MyListener());

	}

	private void getdata() {

		MyApplication.getInstance().getClient()
				.post(Config.INDEXIMG, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						System.out.println("-onSuccess---");
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("LJP", responseMsg);

						Gson gson = new Gson();

						JSONObject jsonobject = null;
						String code = null;
						try {
							jsonobject = new JSONObject(responseMsg);
							code = jsonobject.getString("code");
							int a = jsonobject.getInt("code");
							if (a == Config.CODE) {
								String res = jsonobject.getString("result");
								jsonobject = new JSONObject(res);
								String list = jsonobject.getString("list");
								// jsonobject = new JSONObject(res);
								myList = gson.fromJson(list,
										new TypeToken<List<PicEntity>>() {
										}.getType());
								handler.sendEmptyMessage(0);
							} else {
								code = jsonobject.getString("message");
								Toast.makeText(getActivity(), code, 1000)
										.show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						error.printStackTrace();
					}
				});
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("types", 8);
		JSONObject jsonParams = new JSONObject(params);
		StringEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		MyApplication
		.getInstance()
		.getClient()
		.post(getActivity(), Config.URL_GET_VERSION,entity,null,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					Header[] headers, byte[] responseBody) {
				System.out.println("-onSuccess---");
				
				String responseString = new String(responseBody)
				.toString();
				Response data;
				try {
					data = JsonParser.fromJson(responseString,new TypeToken<VersionEntity>() {
					}) ;
				} catch (Exception e) {
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.parse_data_failed), 1000).show();
					return;
				}
				if (data.getCode() == 1) {
					VersionEntity result = (VersionEntity) data.getResult();
					String version = result.getVersions();
					String url = result.getDown_url();
					Integer nowVersion = Tools.getVerCode(getActivity());
					if(Integer.parseInt(version) > nowVersion){
						
						showCustomDialog(url);
						
					}
					
				} 
				
			}

			@Override
			public void onFailure(int statusCode,
					Header[] headers, byte[] responseBody,
					Throwable error) {
				error.printStackTrace();
			}
		});
		
	}

	private void initIndicator() {

		// ImageView imgView;
		View v = view.findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标

		for (int i = 0; i < ma.size(); i++) {
			try {
				ImageView imgView = new ImageView(getActivity());

				LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(
						10, 10);
				params_linear.setMargins(7, 10, 7, 10);
				imgView.setLayoutParams(params_linear);
				indicator_imgs[i] = imgView;
				if (i == 0) { // 初始化第一个为选中状态

					indicator_imgs[i]
							.setBackgroundResource(R.drawable.indicator_focused);
				} else {
					indicator_imgs[i]
							.setBackgroundResource(R.drawable.indicator);
				}
				((ViewGroup) v).addView(indicator_imgs[i]);
			} catch (Exception e) {
			}

		}

	}

	/**
	 * 适配器，负责装配 、销毁 数据 和 组件 。
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;
		private int index;
		DisplayImageOptions options = MyApplication.getDisplayOption();
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
		 * Remove a page for the given position. 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position) This method was
		 * deprecated in API level . Use instantiateItem(ViewGroup, int)
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
			ImageView image = ((ImageView) view.findViewById(R.id.image));
			   image.setScaleType(ScaleType.CENTER_INSIDE);
			ImageLoader.getInstance().displayImage(ma.get(position), image, options);
			//ImageCacheUtil.IMAGE_CACHE.get(ma.get(position), image);
			image.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
		      	Intent i =new Intent (getActivity(),GoodDeatail.class);
				i.putExtra("id", myList.get(position).getGoodid());
				startActivity(i);
				/*Intent i = new Intent(getActivity(), MyWebView.class);
				i.putExtra("title", "详情");
				i.putExtra("url", myList.get(position).getWebsite_url());
				startActivity(i);*/
				}
			});
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			// image.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // Toast.makeText(getApplicationContext(), index_ima+"----",
			// 1000).show();
			// Intent i=new Intent(AroundDetail.this,VPImage.class);
			// // i.putExtra("image_url", ma.get(index_ima));
			// i.putExtra("index", index_ima);
			// i.putExtra("mal", mal);
			// startActivityForResult(i, 9);
			// }
			// });

			return mList.get(position);
		}
	}

	/**
	 * 动作监听器，可异步加载图片
	 * 
	 */
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

			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {

				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);

			}

			// 改变当前背景图片为：选中
			index_ima = position;
			indicator_imgs[position]
					.setBackgroundResource(R.drawable.indicator_focused);
		}

	}
	

	@Override
	public void onResume() {
		super.onResume();
		timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				handler.sendEmptyMessage(4);
			}
		};
		timer.schedule(task, 0, time);
	}

	@Override
	public void onStop() {

		super.onStop();

		timer.cancel();
	}
	
	private void showCustomDialog(final String url) {
		final CustomDialog dialog = new CustomDialog(getActivity());
		dialog.setSoftKeyValue("取消", "确认");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContent("检测到新版本立即更新？");
		dialog.setLeftListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});
		dialog.setRightListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				upgrading(url);
			}
		});
		dialog.show();
	}
	
	private void upgrading(String apkUrl) {
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getResources().getString(R.string.updata_check));
		fileDownLoad(pd, apkUrl);
		pd.show();
	}

	private void fileDownLoad(ProgressDialog dialog, final String url) {
		try {
			final DownloadListener listener = new DownloadListener(getActivity(), dialog);
			final File file = new File(DownloadUtils.getFilePath(url));
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						DownloadUtils.download(url,
								file, false, listener);
					} catch (Exception e) {
						Log.e("userPhone", "", e);
					}
				}
			}.start();
		} catch (Exception e) {
			dialog.dismiss();
		}
	}

	private static class DownloadListener implements
	DownloadUtils.DownloadListener {
		private Context context;
		private ProgressDialog pd;

		public DownloadListener(Context context, ProgressDialog pd) {
			super();
			this.context = context;
			pd.setMax(100);
			pd.setProgress(0);
			this.pd = pd;
		}

		@Override
		public void downloading(int progress) {
			pd.setProgress(progress);
		}

		@Override
		public void downloaded(File dest) {
			pd.dismiss();
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent.setDataAndType(Uri.fromFile(dest),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}

		@Override
		public void exception(Exception e) {
		}
	}
}
