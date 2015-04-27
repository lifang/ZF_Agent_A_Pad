package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.activity.MsgDetail;
import com.comdo.zf_agent_a_pad.adapter.MessageAdapter;
import com.comdo.zf_agent_a_pad.common.CommonUtil;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.Page;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.MyApplication;
import com.comdo.zf_agent_a_pad.util.Tools;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class Mwdxx extends Fragment implements IXListViewListener,OnClickListener{
	private View view;
	private MessageAdapter myAdapter;
	private List<MessageEntity> datamsg ;
	List<MessageEntity> moreList = new ArrayList<MessageEntity>();
	private XListView xlistview;
	private int rows = 2;
	private int page=1;
	public static Handler myHandler;
	private boolean isrefersh=false;
	private int a=1;
	private Button bt_del,bt_bj;
	private int size=0;
	private String[] ids;
	private int cc;
	private ArrayList list=new ArrayList();
	private int agentUserId=MyApplication.NewUser.getAgentUserId();
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
    	Log.e("inflater", String.valueOf(inflater));
    	Log.e("container", String.valueOf(container));
        view = inflater.inflate(R.layout.mwdxx, container, false);
        initview();
       
        
    } catch (InflateException e) {
        
    }
    return view;
}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	
	
}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	 if(datamsg.size()!=0){
 		datamsg.clear();
 	}
	 if(page!=1){
		 isrefersh=true;
			a=page;
			rows=a*rows;
			page=1;
			datamsg.clear();
	 }
 	getData();
	
	
	myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );
				xlistview.setAdapter(myAdapter);
				break;
			case 1:
				cc=MessageAdapter.pp;
				Intent intent=new Intent(getActivity(),MsgDetail.class);
				intent.putExtra("id", datamsg.get(cc).getId());
			/*	intent.putExtra("title", datamsg.get(cc).getTitle());
				intent.putExtra("time", datamsg.get(cc).getCreate_at());
				intent.putExtra("content", datamsg.get(cc).getContent());*/
				startActivity(intent);
				break;
			case 2:
				//list.add(String.valueOf(datamsg.get(MessageAdapter.pp).getId()));
				//ids[size]=String.valueOf(datamsg.get(MessageAdapter.pp).getId());
				//size++;
				//ids[0]=String.valueOf(datamsg.get(MessageAdapter.pp).getId());
				//size++;
				break;
			default:
				break;
			}
		};
	};
}
protected void onLoad() {

	xlistview.stopRefresh();
	xlistview.stopLoadMore();
	xlistview.setRefreshTime(Tools.getHourAndMin());
	

	
}
private void getData() {
	Config.getMsgList(getActivity(), agentUserId, page, rows, new HttpCallback<Page<MessageEntity>>(getActivity()) {

		@Override
		public void onSuccess(Page<MessageEntity> data) {
			if(isrefersh){
				page=a;
				rows=2;
				isrefersh=false;
			}
			
			if(datamsg.size()!=0&&data.getList().size()==0){
				Toast.makeText(getActivity(), "没有更多数据!", 1000).show();
			}
			else{
				ids=new String[data.getList().size()];
				//ids=new String[1];
				datamsg.addAll(data.getList());
			}
			myHandler.sendEmptyMessage(0);
			
		}

		@Override
		public TypeToken<Page<MessageEntity>> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<Page<MessageEntity>>() {
			};
		}
	});
}
private void initview() {
	bt_bj=(Button) view.findViewById(R.id.bt_bj);
	bt_del=(Button) view.findViewById(R.id.bt_del);
	datamsg=new ArrayList<MessageEntity>();
	myAdapter=new MessageAdapter(datamsg, getActivity().getBaseContext());
	xlistview=(XListView) view.findViewById(R.id.x_listview);
	xlistview.setPullLoadEnable(true);
	xlistview.setXListViewListener(this);
	xlistview.setDivider(null);
	bt_del.setOnClickListener(this);
	bt_bj.setOnClickListener(this);
	
}
@Override
public void onRefresh() {
	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	isrefersh=true;
	a=page;
	rows=a*rows;
	page=1;
	datamsg.clear();
	getData();
	
}
@Override
public void onLoadMore() {

	if(!Tools.isConnect(getActivity())){
		CommonUtil.toastShort(getActivity(), "网络异常");
		return;
	}
	page+=1;
	getData();
	
	

	
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.bt_del:
		delectMore();
		break;
	case R.id.bt_bj:
		if(datamsg.size()!=0){
			datamsg.clear();
		}
		isRead();
		break;
	default:
		break;
	}
	
}
private void isRead() {
	/*for(int i=0;i<datamsg.size();i++){
		if(datamsg.get(i).getIscheck()){
			ids[size]=String.valueOf(datamsg.get(i).getId());
			size++;
		}
	}*/
	for(int i=0;i<datamsg.size();i++){
		if(datamsg.get(i).isStatus()){
			ids[i]=String.valueOf(datamsg.get(i).getId());
			size++;
		}
	}
	Config.IsRead(getActivity(), ids,agentUserId, new HttpCallback(getActivity()) {

		@Override
		public void onSuccess(Object data) {
			CommonUtil.toastShort(getActivity(), "标注成功");
			//size=0;
			if(datamsg.size()!=0){
				datamsg.clear();
			}
			getData();
		}

		@Override
		public TypeToken getTypeToken() {
			// TODO Auto-generated method stub
			return null;
		}
	});
	
}
private void delectMore() {
	/*ids=new String[list.size()];
	for(int i=0;i<list.size();i++){
		ids[i]=String.valueOf(list.get(i));
	}*/
	for(int i=0;i<datamsg.size();i++){
		if(datamsg.get(i).isStatus()){
			ids[size]=String.valueOf(datamsg.get(i).getId());
			size++;
			Log.e("i", i+"");
			Log.e("size", size+"");
		}
	}
	Config.DelectMoreMsg(getActivity(), ids,agentUserId, new HttpCallback(getActivity()) {

		@Override
		public void onSuccess(Object data) {
			CommonUtil.toastShort(getActivity(), "删除成功");
			if(datamsg.size()!=0){
				datamsg.clear();
			}
			//size=0;
			getData();
			
		}

		@Override
		public TypeToken getTypeToken() {
			// TODO Auto-generated method stub
			return null;
		}
	});
	
}

}

