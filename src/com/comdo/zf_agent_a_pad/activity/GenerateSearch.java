package com.comdo.zf_agent_a_pad.activity;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TERMINAL;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.TerminalIntent.TERMINAL_TYPE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.PageApply;
import com.comdo.zf_agent_a_pad.entity.TerminalPriceEntity;
import com.comdo.zf_agent_a_pad.trade.entity.TerminalManagerEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.StringUtil;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

;

public class GenerateSearch extends Activity implements OnClickListener,
		IXListViewListener, OnEditorActionListener {

	private LinearLayout titleback_linear_back;
	private EditText searchEditText;
	private LinearLayout linear_deletename;
	private TextView next_cancel;
	private LayoutInflater mInflater;
	private XListView mListView;
	private LinearLayout eva_nodata;
	private TerminalAdapter myAdapter;

	private SharedPreferences mySharedPreferences = null;
	private Editor editor;
	private String terminalStr = "", name;// 搜索记录
	private List<String> data = new ArrayList<String>();
	private int page = 1;
	private int total = 0;
	private final int rows = 10;

	private int mSearchType;
	private int mStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_generatesearch);
		// mSearchType = getIntent().getIntExtra(TERMINAL_TYPE, 1);
		// mStatus = getIntent().getIntExtra(TERMINAL_STATUS, -1);
		initView();
	}

	private void initView() {

		mInflater = LayoutInflater.from(this);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		linear_deletename = (LinearLayout) findViewById(R.id.linear_deletename);
		next_cancel = (TextView) findViewById(R.id.next_cancel);

		mListView = (XListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);

		mySharedPreferences = getSharedPreferences("terminal_search", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		terminalStr = mySharedPreferences.getString("terminalStr", "");
		if (terminalStr == "" || terminalStr == null) {	

			mListView.setVisibility(View.GONE);
			eva_nodata.setVisibility(View.VISIBLE);
			
		} else {

			mListView.setVisibility(View.VISIBLE);
			eva_nodata.setVisibility(View.GONE);
 
			if (terminalStr.contains(",")) {
				String[] serach = terminalStr.split(",");
				for (int i = (serach.length - 1); i >= 0; i--) {

					data.add(serach[i]);
				}
				

			} else {
				data.add(terminalStr);
		
			}
			data.add(getResources().getString(R.string.clear_history));
		}
		myAdapter = new TerminalAdapter();

		mListView.initHeaderAndFooter();
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);

		mListView.setAdapter(myAdapter);
		titleback_linear_back.setOnClickListener(this);
		linear_deletename.setOnClickListener(this);
		next_cancel.setOnClickListener(this);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if(position==data.size()-1){
					
					DeletaData();
				} else{
				
				Intent intent = new Intent();
				intent.putExtra(SELECTED_TERMINAL, data.get(position));
				setResult(RESULT_OK, intent);
				finish();
				}
			}
		});

		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					linear_deletename.setVisibility(View.VISIBLE);
				} else {
					linear_deletename.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			finish();
			break;
		case R.id.linear_deletename:
			searchEditText.setText("");
			break;
		case R.id.next_cancel:
			finish();
			break;
		default:
			break;
		}
	}

	class TerminalAdapter extends BaseAdapter {

		public TerminalAdapter() {
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public String getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.item_list_stock_search, parent, false);
				viewHolder.nameTextView = (TextView) convertView
						.findViewById(R.id.nameTextViews);
				viewHolder.type_pop = (RelativeLayout) convertView
						.findViewById(R.id.type_pop);
				convertView.setTag(viewHolder);

			}

			viewHolder = (ViewHolder) convertView.getTag();
//			viewHolder.nameTextView.setText(data.get(position)
//					.getId() + "");
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView nameTextView;
		public RelativeLayout type_pop;
	}

	// add
	public void addData(String name) {
		String[] serach = terminalStr.split(",");
		data.clear();
		for (int i = (serach.length - 1); i >= 0; i--) {
			data.add(serach[i]);
		}
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).equals(name)) {
				data.remove(i);
			}
		}
		data.add(name);
		terminalStr = "";
		for (String str : data) {
			if (terminalStr != null && !terminalStr.equals("")) {
				terminalStr += "," + str;
			} else {
				terminalStr += str;
			}
			editor.putString("terminalStr", terminalStr);
			editor.commit();
		}

	}

	// 删除记录
	public void DeletaData() {
		editor = mySharedPreferences.edit();
		editor.putString("terminalStr", "");
		editor.commit();// 提交
		data.clear();

		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh() {}

	@Override
	public void onLoadMore() {}



	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		name = searchEditText.getText().toString();
		
		switch (actionId) {
		case 0:
			name = searchEditText.getText().toString();
			addData(name);
			Intent intent = new Intent();
			intent.putExtra(SELECTED_TERMINAL, name);
			GenerateSearch.this.setResult(RESULT_OK, intent);
			finish();

			return true;

		}

		return false;

	}
}
