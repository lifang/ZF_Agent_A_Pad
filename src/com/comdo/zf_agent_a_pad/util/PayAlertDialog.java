package com.comdo.zf_agent_a_pad.util;
import com.epalmpay.agentPad.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PayAlertDialog  {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	LinearLayout buttonLayout1;
	private LinearLayout buttonLayout2;
	private EditText pay;
	public PayAlertDialog(Context context) {
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		Window window = ad.getWindow();
		ad.show();
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);	
		//关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局		
		window.setContentView(R.layout.payalertdialog);
		titleView=(TextView)window.findViewById(R.id.title);
		//messageView=(TextView)window.findViewById(R.id.message);
		buttonLayout1=(LinearLayout)window.findViewById(R.id.buttonLayout1);
		buttonLayout2 = (LinearLayout)window.findViewById(R.id.buttonLayout2);
		pay = (EditText)window.findViewById(R.id.pay);
	}
	
	public void setTitle(int resId)
	{
		titleView.setText(resId);
	}
	public void setTitle(String title) {
		titleView.setText(title);
	}
	public  String getPay()
	{	
		return pay.getText().toString();
	}
 
	public void setMessage(String message)
	{
		messageView.setText(message);
	}
	/**
	 * 设置按钮
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text,final View.OnClickListener listener)
	{
		Button button=new Button(context);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.dialog);
		button.setText(text);
		button.setTextColor(Color.BLUE);
		button.setTextSize(14);
		button.setOnClickListener(listener);
		buttonLayout1.addView(button);
	}
	
	/**
	 * 设置按钮
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,final View.OnClickListener listener)
	{
		Button button=new Button(context);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.dialog);
		button.setText(text);
		button.setTextColor(Color.BLUE);
		button.setTextSize(16);
		button.setOnClickListener(listener);
		if(buttonLayout2.getChildCount()>0)
		{
			params.setMargins(20, 0, 0, 0);
			button.setLayoutParams(params);
			buttonLayout2.addView(button, 1);
		}else{
			button.setLayoutParams(params);
			buttonLayout2.addView(button);
		}
		
	}
	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}
	
}