package com.comdo.zf_agent_a_pad.trade;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.entity.TradeStatistic;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class TradeStatisticActivity extends Activity {

    public static final String TRADE_TYPE = "trade_type";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String CLIENT_NUMBER = "client_number";
    public static final String SONAGEHNTID = "sonagentId";

    private int mTradeType;
    private String mStartDate;
    private String mEndDate;
    private String terminalNumber;
    private int tradeAgentId;

    private TextView statisticAmount;
    private TextView statisticCount;
    private TextView statisticTime;
    private TextView statisticClient;
    private TextView statisticChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTradeType = intent.getIntExtra(TRADE_TYPE, 1);
        mStartDate = intent.getStringExtra(START_DATE);
        mEndDate = intent.getStringExtra(END_DATE);
        terminalNumber = intent.getStringExtra(CLIENT_NUMBER);
        tradeAgentId = intent.getIntExtra(SONAGEHNTID,1);

        setContentView(R.layout.activity_trade_statistic);
        initViews();

        API.getTradeRecordStatistic(this, mTradeType,1,tradeAgentId, terminalNumber, mStartDate, mEndDate,1,
                new HttpCallback<TradeStatistic>(this) {
                    @Override
                    public void onSuccess(TradeStatistic data) {
                    	DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
            			df.applyPattern("0.00");
                        statisticAmount.setText(getString(R.string.notation_yuan) + df.format(data.getAmountTotal()*1.0f/100));
                        statisticCount.setText("" + data.getTradeTotal());
                        statisticTime.setText(mStartDate.replaceAll("-", "/") + " - " + mEndDate.replaceAll("-", "/"));
                        statisticClient.setText(data.getTerminalNumber());
                        statisticChannel.setText(data.getPayChannelName());
                    }

                    @Override
                    public TypeToken<TradeStatistic> getTypeToken() {
                        return new TypeToken<TradeStatistic>() {
                        };
                    }
                });
    }

    private void initViews() {
//        new TitleMenuUtil(this, getString(R.string.title_trade_statistic)).show();

        statisticAmount = (TextView) findViewById(R.id.trade_statistic_amount);
        statisticCount = (TextView) findViewById(R.id.trade_statistic_count);
        statisticTime = (TextView) findViewById(R.id.trade_statistic_time);
        statisticClient = (TextView) findViewById(R.id.trade_statistic_client);
        statisticChannel = (TextView) findViewById(R.id.trade_statistic_channel);
    }
}
