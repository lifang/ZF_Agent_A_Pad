package com.comdo.zf_agent_a_pad.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.adapter.AgentManagerAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.AgentDetailEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.ImageCacheUtil;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class AgentDetail extends BaseActivity implements OnClickListener {
	private int sonAgentsId, isProfit;
	private TextView tv_company_type, tv_company_name, tv_yingyezhizhao,
			tv_company_shuiwudengji, tv_fuzeren_name, tv_fuzeren_no, tv_phone,
			tv_email, tv_adress, tv_adressdetail, tv_jointime, tv_has_amount,
			tv_left_amount, tv_terminal_amount;
	private TextView tv_id_detail;
	private String isprofit;
	private String cardPath, licensePath, taxPath;
	private ImageView iv_shenfenzheng, iv_yingyezhizhao, iv_shuiwu, iv_fenrun;
	private Handler myHandler;
	private int tag = 0;
	private String[] imgPath = new String[3];

	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title, next_sure;
	private ImageView titleback_image_back;
	private String type;
	private TableRow row_company_name, row_yingyezhizhao,
			row_company_shuiwudengji, row_yingyezhizhao_photo,
			row_shuiwu_photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agentdetail);
		// new TitleMenuUtil(AgentDetail.this, "下级代理商详情").show();
		init();

	}

	@Override
	protected void onStart() {
		super.onStart();
		getData();
		myHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					if (isprofit.equals("2")) {
						iv_fenrun.setBackgroundResource(R.drawable.agent_on);
						next_sure.setVisibility(View.VISIBLE);
						isProfit = 2;
					} else {
						iv_fenrun.setBackgroundResource(R.drawable.agent_off);
						next_sure.setVisibility(View.GONE);
						isProfit = 1;
					}

					if ("1".equals(type)) {

						row_company_name.setVisibility(View.VISIBLE);
						row_yingyezhizhao.setVisibility(View.VISIBLE);
						row_company_shuiwudengji.setVisibility(View.VISIBLE);
						row_yingyezhizhao_photo.setVisibility(View.VISIBLE);
						row_shuiwu_photo.setVisibility(View.VISIBLE);

					} else if ("2".equals(type)) {

						row_company_name.setVisibility(View.GONE);
						row_yingyezhizhao.setVisibility(View.GONE);
						row_company_shuiwudengji.setVisibility(View.GONE);
						row_yingyezhizhao_photo.setVisibility(View.GONE);
						row_shuiwu_photo.setVisibility(View.GONE);

					}
					break;

				default:
					break;
				}
			};
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shenfenzheng:
			tag = 1;
			imgPath[0] = cardPath;
			openimg(tag);
			break;
		case R.id.iv_yingyezhizhao:
			tag = 2;
			imgPath[1] = licensePath;
			openimg(tag);
			break;
		case R.id.iv_shuiwu:
			tag = 3;
			imgPath[2] = taxPath;
			openimg(tag);
			break;
		case R.id.next_sure:
			Intent intent = new Intent(AgentDetail.this, SelectPayChannel.class);
			intent.putExtra("id", sonAgentsId);
			startActivity(intent);
			break;
		case R.id.iv_fenrun:
			changeFenRun();
			break;
		default:
			break;
		}

	}

	private void changeFenRun() {
		if (isProfit == 1) {
			isProfit = 2;
		} else if (isProfit == 2) {
			isProfit = 1;
		}
		Config.setDefaultProfit(AgentDetail.this,
				MyApplication.NewUser.getAgentId(), sonAgentsId, isProfit,
				new HttpCallback(AgentDetail.this) {

					@Override
					public void onSuccess(Object data) {
						CommonUtil.toastShort(AgentDetail.this, "修改成功");
						if (isProfit == 2) {
							iv_fenrun
									.setBackgroundResource(R.drawable.agent_on);
							next_sure.setVisibility(View.VISIBLE);
						} else if (isProfit == 1) {

							iv_fenrun
									.setBackgroundResource(R.drawable.agent_off);
							next_sure.setVisibility(View.GONE);
						}
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
	}

	private void openimg(int tag) {

		AlertDialog.Builder build = new AlertDialog.Builder(AgentDetail.this);
		LayoutInflater factory = LayoutInflater.from(AgentDetail.this);
		final View textEntryView = factory.inflate(R.layout.img, null);
		// build.setTitle("自定义输入框");
		build.setView(textEntryView);
		final ImageView view = (ImageView) textEntryView
				.findViewById(R.id.imag);
		int ppp = tag - 1;
		ImageCacheUtil.IMAGE_CACHE.get(imgPath[ppp], view);
		// ImageCacheUtil.IMAGE_CACHE.get("http://www.sinaimg.cn/dy/slidenews/2_img/2015_16/789_1481006_298613.jpg",
		// view);
		build.create().show();

	}

	private void getData() {
		Config.GetAgentDetail(AgentDetail.this, sonAgentsId,
				new HttpCallback<AgentDetailEntity>(AgentDetail.this) {

					@Override
					public void onSuccess(AgentDetailEntity data) {
						type = data.getTypes();
						if (data.getTypes().equals("1")) {
							tv_company_type.setText("公司");
						} else if (data.getTypes().equals("2")) {
							tv_company_type.setText("个人");

						} else {
							tv_company_type.setText(data.getTypes());
						}
						isprofit = data.getIs_have_profit();
						tv_company_name.setText(data.getCompany_name());
						tv_yingyezhizhao.setText(data.getBusiness_license());
						tv_company_shuiwudengji.setText(data
								.getTax_registered_no());
						tv_fuzeren_name.setText(data.getName());
						tv_fuzeren_no.setText(data.getCard_id());
						tv_phone.setText(data.getPhone());
						tv_email.setText(data.getEmail());
						tv_adress.setText(data.getCityName());
						tv_adressdetail.setText(data.getAddress());
						cardPath = data.getCardpath();
						licensePath = data.getLicensepath();
						taxPath = data.getTaxpath();
						tv_id_detail.setText(data.getLoginId());
						tv_jointime.setText(data.getCreated_at());
						tv_has_amount.setText(data.getSoldnum());
						tv_left_amount.setText(Integer.parseInt(data
								.getAllQty())
								- Integer.parseInt(data.getSoldnum()) + "");
						tv_terminal_amount.setText(data.getAllQty());
						myHandler.sendEmptyMessage(0);
					}

					@Override
					public void onFailure(String message) {
						super.onFailure(message);
					}

					@Override
					public TypeToken<AgentDetailEntity> getTypeToken() {
						return new TypeToken<AgentDetailEntity>() {
						};
					}

				});

	}

	private void init() {
		Intent intent = getIntent();
		sonAgentsId = intent.getIntExtra("id", 0);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		next_sure = (TextView) findViewById(R.id.next_sure);
		titleback_image_back = (ImageView) findViewById(R.id.titleback_image_back);
		tv_company_type = (TextView) findViewById(R.id.tv_company_type);
		tv_company_name = (TextView) findViewById(R.id.tv_company_name);
		tv_yingyezhizhao = (TextView) findViewById(R.id.tv_yingyezhizhao);
		tv_company_shuiwudengji = (TextView) findViewById(R.id.tv_company_shuiwudengji);
		tv_fuzeren_name = (TextView) findViewById(R.id.tv_fuzeren_name);
		tv_fuzeren_no = (TextView) findViewById(R.id.tv_fuzeren_no);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_email = (TextView) findViewById(R.id.tv_email);
		tv_adress = (TextView) findViewById(R.id.tv_adress);
		tv_adressdetail = (TextView) findViewById(R.id.tv_adressdetail);
		tv_id_detail = (TextView) findViewById(R.id.tv_id_detail);
		tv_jointime = (TextView) findViewById(R.id.tv_jointime);
		tv_has_amount = (TextView) findViewById(R.id.tv_has_amount);
		tv_left_amount = (TextView) findViewById(R.id.tv_left_amount);
		tv_terminal_amount = (TextView) findViewById(R.id.tv_terminal_amount);
		iv_shenfenzheng = (ImageView) findViewById(R.id.iv_shenfenzheng);
		iv_yingyezhizhao = (ImageView) findViewById(R.id.iv_yingyezhizhao);
		iv_shuiwu = (ImageView) findViewById(R.id.iv_shuiwu);
		iv_fenrun = (ImageView) findViewById(R.id.iv_fenrun);
		row_company_name = (TableRow) findViewById(R.id.row_company_name);
		row_yingyezhizhao = (TableRow) findViewById(R.id.row_yingyezhizhao);
		row_company_shuiwudengji = (TableRow) findViewById(R.id.row_company_shuiwudengji);
		row_yingyezhizhao_photo = (TableRow) findViewById(R.id.row_yingyezhizhao_photo);
		row_shuiwu_photo = (TableRow) findViewById(R.id.row_shuiwu_photo);
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AgentDetail.this.finish();
			}
		});

		titleback_image_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AgentDetail.this.finish();
			}
		});

		titleback_text_title.setText("下级代理商详情");
		next_sure.setOnClickListener(this);
		iv_shenfenzheng.setOnClickListener(this);
		iv_yingyezhizhao.setOnClickListener(this);
		iv_shuiwu.setOnClickListener(this);
		iv_fenrun.setOnClickListener(this);
	}
}
