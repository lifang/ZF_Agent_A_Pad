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
import com.comdo.zf_agent_a_pad.trade.entity.TradeClient;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.TitleMenuUtil;
import com.epalmpay.agentPad.R;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeClientActivity extends ListActivity {

    public static final String CLIENT_NUMBER = "client_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_client);
        new TitleMenuUtil(this, "选择终端").show();
        final String selectedNumber = getIntent().getStringExtra(CLIENT_NUMBER);

        final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        final SimpleAdapter adapter = new SimpleAdapter(
                this, items,
                R.layout.trade_client_item,
                new String[]{"name", "selected"},
                new int[]{R.id.trade_client_name, R.id.trade_client_selected});
        setListAdapter(adapter);

        API.getTerminalList(this, MyApplication.NewUser.getAgentId(), new HttpCallback<List<TradeClient>>(this) {

			@Override
			public void onSuccess(List<TradeClient> data) {
				for (TradeClient client : data) {
					Map<String, Object> item = new HashMap<String, Object>();
					String clientNumber = client.getSerialNum();
					item.put("name", clientNumber);
					item.put("selected", TextUtils.isEmpty(clientNumber)
							|| !clientNumber.equals(selectedNumber) ? null : R.drawable.icon_selected);
					items.add(item);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<List<TradeClient>> getTypeToken() {
				return new TypeToken<List<TradeClient>>() {
				};
			}
		});
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView tv = (TextView) v.findViewById(R.id.trade_client_name);
        Intent intent = new Intent();
        intent.putExtra(CLIENT_NUMBER, tv.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
    
    @Override
   	protected void onResume() {
   		super.onResume();
   		MobclickAgent.onPageStart( this.toString() );
   		MobclickAgent.onResume(this);
   	}

   	@Override
   	protected void onPause() {
   		super.onPause();
   		MobclickAgent.onPageEnd(this.toString());
   		MobclickAgent.onPause(this);
   	}
}
