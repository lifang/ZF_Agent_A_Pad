package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.entity.SelectPOS;
import com.comdo.zf_agent_a_pad.trade.entity.ApplyChooseItem;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;

import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_ID;
import static com.comdo.zf_agent_a_pad.fragment.Constants.ApplyIntent.SELECTED_TITLE;

public class TerminalSelectPOSActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list);

		String title = getIntent().getStringExtra(CHOOSE_TITLE);
		List<SelectPOS> posItems = (List<SelectPOS>) getIntent()
				.getSerializableExtra(CHOOSE_ITEMS);
		int selectedId = getIntent().getIntExtra(SELECTED_ID, 0);

		new TitleMenuUtil(this, title).show();

		final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (SelectPOS selectPOS : posItems) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", selectPOS.getId());
			item.put("name", selectPOS.getTitle());
			item.put("selected",
					selectPOS.getId() == selectedId ? R.drawable.icon_selected
							: null);
			items.add(item);
		}
		final SimpleAdapter adapter = new SimpleAdapter(this, items,
				R.layout.simple_list_item, new String[] { "id", "name",
						"selected" }, new int[] { R.id.item_id, R.id.item_name,
						R.id.item_selected });
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView tvId = (TextView) v.findViewById(R.id.item_id);
		TextView tvTitle = (TextView) v.findViewById(R.id.item_name);
		Intent intent = new Intent();
		intent.putExtra(SELECTED_ID,
				Integer.parseInt(tvId.getText().toString()));
		intent.putExtra(SELECTED_TITLE, tvTitle.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
