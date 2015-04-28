package com.comdo.zf_agent_a_pad.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.CommentAdapter;
import com.comdo.zf_agent_a_pad.entity.Answer;
import com.comdo.zf_agent_a_pad.entity.Goodlist;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.ScrollViewWithListView;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Comment extends BaseActivity{
	private ScrollViewWithListView lv;
	private Button btn_pay;
	private TextView next_sure;
	//Answer
	List<Answer>  as = new ArrayList<Answer>();
	private CommentAdapter posAdapter;
	List<Goodlist>  goodlist = new ArrayList<Goodlist>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordercomment);
		lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		goodlist=Config.list;
		posAdapter=new CommentAdapter(Comment.this,goodlist);
		lv.setAdapter(posAdapter);
		//new TitleMenuUtil(Comment.this, "评价").show();
	
	
		btn_pay=(Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				submit();
			}	 
		});
	}
	public void submit(){
		for(int i=0;i<goodlist.size();i++){
			Answer aaa=new Answer();
 			aaa.setContent(goodlist.get(i).getContent());
 			aaa.setCustomer_id(MyApplication.NewUser.getId());
 			aaa.setGood_id(Integer.parseInt( goodlist.get(i).getGood_id()));
 			aaa.setScore(Integer.parseInt( goodlist.get(i).getScore()));
 			as.add(aaa);
			System.out.println(goodlist.get(i).getScore()+"---submit---"+goodlist.get(i).getContent()+"id-"+goodlist.get(i).getGood_id());
		}
		String url = Config.Comment;
		RequestParams params = new RequestParams();
		Gson gson = new Gson();
		try {
			params.put("json", new JSONArray(gson.toJson(as)));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	  
		params.setUseJsonStreamer(true);
		System.out.println("---"+params.toString());
		MyApplication.getInstance().getClient()
				.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("print", responseMsg);
					 
						Gson gson = new Gson();
						
						JSONObject jsonobject = null;
						String code = null;
						try {
							jsonobject = new JSONObject(responseMsg);
							code = jsonobject.getString("code");
							int a =jsonobject.getInt("code");
							if(a==Config.CODE){ 
								Toast.makeText(getApplicationContext(), "评论成功", 1000).show();
								finish();
							}else{ 
								
								Toast.makeText(getApplicationContext(), "评论失败", 1000).show();
								finish();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						System.out.println("-onFailure---");
						Log.e("print", "-onFailure---" + error);
					}
				});
 
		 
	
	}
}
