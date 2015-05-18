package com.comdo.zf_agent_a_pad.util;



import com.example.zf_agent_a_pad.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class SetPopWindow extends PopupWindow implements OnClickListener {
	private View conentView;
	private ImageView img_on_off;
	private Boolean isOpen_mineset;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private LinearLayout ll_new, ll_clean;
	private TextView tv_clean;
	private Activity context;

	public SetPopWindow(final Activity context) {
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.popwin_setting, null);
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// ����SelectPicPopupWindow��View
		this.setContentView(conentView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth(LayoutParams.FILL_PARENT);
		// ����SelectPicPopupWindow��������ĸ�
		this.setHeight(LayoutParams.FILL_PARENT);
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// ˢ��״̬
		this.update();
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0000000000);
		// ��back���������ط�ʹ����ʧ,������������ܴ���OnDismisslistener �����������ؼ��仯�Ȳ���
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		// ����SelectPicPopupWindow�������嶯��Ч��
		// this.setAnimationStyle(R.style.AnimationPreview);
		initView();

	}

	private void initView() {
		Button bt_close = (Button) conentView.findViewById(R.id.close);
		bt_close.setOnClickListener(this);
		mySharedPreferences = context.getSharedPreferences("zf_set",
				Activity.MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		isOpen_mineset = mySharedPreferences.getBoolean("isOpen_mineset", true);
		img_on_off = (ImageView) conentView.findViewById(R.id.img_on_off);
		if (!isOpen_mineset) {
			img_on_off.setBackgroundResource(R.drawable.pos_off);

		}
		img_on_off.setOnClickListener(this);
		ll_new = (LinearLayout) conentView.findViewById(R.id.ll_new);
		ll_new.setOnClickListener(this);
		ll_clean = (LinearLayout) conentView.findViewById(R.id.ll_clean);
		ll_clean.setOnClickListener(this);

		tv_clean = (TextView) conentView.findViewById(R.id.tv_clean);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.close:
			this.dismiss();
			break;
		case R.id.ll_new:

			// ������

			break;
		case R.id.tv_clean:

			tv_clean.setText("");

			break;
		case R.id.img_on_off:

			if (isOpen_mineset) {
				isOpen_mineset = false;
				// img_on_off.setBackgroundDrawable(getResources().getDrawable(R.drawable.pos_off));
				img_on_off.setBackgroundResource(R.drawable.pos_off);
				editor.putBoolean("isOpen_mineset", false);
				editor.commit();

			} else {
				isOpen_mineset = true;
				img_on_off.setBackgroundResource(R.drawable.pos_on);
				editor.putBoolean("isOpen_mineset", true);
				editor.commit();
			}

			break;

		default:
			break;
		}
	}
}
