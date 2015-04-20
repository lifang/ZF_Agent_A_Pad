package com.comdo.zf_agent_a_pad.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comdo.zf_agent_a_pad.adapter.MessageAdapter;
import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.entity.MessageEntity;
import com.comdo.zf_agent_a_pad.util.Config;
import com.comdo.zf_agent_a_pad.util.XListView;
import com.comdo.zf_agent_a_pad.util.XListView.IXListViewListener;
import com.example.zf_agent_a_pad.R;
import com.google.gson.reflect.TypeToken;

public class Mwdxx extends Fragment implements IXListViewListener{
	private View view;
	private MessageAdapter myAdapter;
	private List<MessageEntity> datamsg ;
	List<MessageEntity> moreList = new ArrayList<MessageEntity>();
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
private void getData() {
	
	Config.getMsgList(getActivity(), 80, page, rows, new HttpCallback<MessageEntity>(getActivity()) {

		@Override
		public void onSuccess(MessageEntity data) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), String.valueOf(data), Toast.LENGTH_SHORT).show();
		}

		@Override
		public TypeToken<MessageEntity> getTypeToken() {
			// TODO Auto-generated method stub
			return new TypeToken<MessageEntity>() {
			};
		}
	});
}
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
