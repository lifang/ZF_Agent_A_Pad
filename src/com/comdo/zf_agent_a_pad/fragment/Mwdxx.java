package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.MessageAdapter;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Mwdxx extends Fragment implements IXListViewListener{
	private View view;
	private MessageAdapter myAdapter;
	private List<MessageEntity> datamsg ;
	private XListView xlistview;
	private int rows = Config.ROWS;
	private int page=1;
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	
		//view = inflater.inflate(R.layout.f_main,container,false);
		
	
	
	if (view != null) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
    }
    try {
        view = inflater.inflate(R.layout.mwdxx, container, false);
        initview();
        getData();
    } catch (InflateException e) {
        
    }
    return view;
}
private void getData() {/*

	// TODO Auto-generated method stub

	RequestParams params = new RequestParams();

	params.put("customer_id", 80);
	params.put("page", page);
	params.put("pageSize", rows);
	params.setUseJsonStreamer(true);
	MyApplication.getInstance().getClient()
			.post(Url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					System.out.println("-onSuccess---");
					String responseMsg = new String(responseBody)
							.toString();
					Log.e("LJP", responseMsg);
					Gson gson = new Gson();
					JSONObject jsonobject = null;
					int code = 0;
					try {
						jsonobject = new JSONObject(responseMsg);
						 
						 
						code = jsonobject.getInt("code");
						
						if(code==-2){
						 
						}else if(code==1){
							
							String res =jsonobject.getString("result");
							System.out.println("`res``"+res);
							jsonobject = new JSONObject(res);
							moreList.clear();
							
			 				moreList = gson.fromJson(jsonobject.getString("content") ,
								new TypeToken<List<MessageEntity>>() {
								}.getType());
			 					 	
			 						if (moreList.size()==0) {
			 							Toast.makeText(getApplicationContext(),
			 									"û�и�������", Toast.LENGTH_SHORT).show();
			 							Xlistview.getmFooterView().setState2(2);
			 					 
			 						} 
			 						 
			 				myList.addAll(moreList);
			 				handler.sendEmptyMessage(0);
	 
							
							
						}else{
							Toast.makeText(getActivity(), jsonobject.getString("message"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					error.printStackTrace();
				}
			});
	 

	
*/}
private void initview() {
	datamsg=new ArrayList<MessageEntity>();
	myAdapter=new MessageAdapter(datamsg, getActivity().getBaseContext());
	xlistview=(XListView) view.findViewById(R.id.x_listview);
	xlistview.setPullLoadEnable(true);
	xlistview.setXListViewListener(this);
	xlistview.setDivider(null);
	for(int i=0;i<6;i++){
		datamsg.add(new MessageEntity("ssssssss", i, "ddddddd", "ssssss", false));
	}
	xlistview.setAdapter(myAdapter);
}
@Override
public void onRefresh() {
	// TODO Auto-generated method stub
	
}
@Override
public void onLoadMore() {
	// TODO Auto-generated method stub
	
}

}
