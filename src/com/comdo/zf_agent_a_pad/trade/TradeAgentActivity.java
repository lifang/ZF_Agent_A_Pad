package com.comdo.zf_agent_a_pad.trade;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.entity.TradeAgent;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeAgentActivity extends ListActivity {

	public static final String AGENT_ID = "agent_id";
	public static final String AGENT_NAME = "agent_name";
	List<TradeAgent> agents = new ArrayList<TradeAgent>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_client);
        new TitleMenuUtil(this, "选择代理商").show();
        final int selectedId = getIntent().getIntExtra(AGENT_ID,0);

        final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        final SimpleAdapter adapter = new SimpleAdapter(
                this, items,
                R.layout.trade_client_item,
                new String[]{"name", "selected"},
                new int[]{R.id.trade_client_name, R.id.trade_client_selected});
        setListAdapter(adapter);

        API.getAgentList(this, 1, new HttpCallback<List<TradeAgent>>(this) {

			@Override
			public void onSuccess(List<TradeAgent> data) {
				agents.addAll(data);
				for (TradeAgent agent : data) {
					Map<String, Object> item = new HashMap<String, Object>();
					int agentId = agent.getAgentId();
					String agentName = agent.getAgentName();
					item.put("name", agentName);
					item.put("selected", agentId != selectedId? null : R.drawable.icon_selected);
					items.add(item);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<List<TradeAgent>> getTypeToken() {
				return new TypeToken<List<TradeAgent>>() {
				};
			}
		});
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent();
        intent.putExtra(AGENT_ID, agents.get(position).getAgentId());
        intent.putExtra(AGENT_NAME, agents.get(position).getAgentName());
        setResult(RESULT_OK, intent);
        finish();
    }
}
