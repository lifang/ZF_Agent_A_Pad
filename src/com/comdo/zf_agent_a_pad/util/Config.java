package com.comdo.zf_agent_a_pad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.HttpRequest;
import com.comdo.zf_agent_a_pad.entity.ApplyneedEntity;
import com.comdo.zf_agent_a_pad.entity.ChanelEntitiy;
import com.comdo.zf_agent_a_pad.entity.GoodinfoEntity;
import com.comdo.zf_agent_a_pad.entity.Goodlist;
import com.comdo.zf_agent_a_pad.entity.PosEntity;
import com.comdo.zf_agent_a_pad.entity.Posport;
import com.comdo.zf_agent_a_pad.entity.tDates;
import com.comdo.zf_agent_a_pad.entity.other_rate;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

import android.util.Log;

public class Config {

	public final static String PATHS = "http://114.215.149.242:28080/ZFAgent/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;

	public static int ScreenWidth=0;
	public static int ScreenHeight=0;

	public static final String POSLIST = PATHS + "good/list";
	public static final String GOODDETAIL = PATHS + "good/goodinfo";
	public static final String paychannel_info = PATHS + "paychannel/info";
	public static String goodcomment = PATHS + "comment/list";
	public static final String POSPORT = PATHS + "good/search";
	public static final String POSTHOT = PATHS + "index/pos_list";
	public static final String SHOPORDER = PATHS + "order/shop";
	public static final String ZDORDER = PATHS + "order/lease";
	public static final String ORDERLIST = PATHS + "order/orderSearch";
	
	public static GoodinfoEntity gfe = null;
	public static ArrayList<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	public static ArrayList<tDates> tDates = new ArrayList<tDates>();
	public static ArrayList<other_rate> other_rate = new ArrayList<other_rate>();
	public static List<ApplyneedEntity> pub = new LinkedList<ApplyneedEntity>();
	public static List<ApplyneedEntity> single = new LinkedList<ApplyneedEntity>();
	public static List<PosEntity> myList = new ArrayList<PosEntity>();
	public static List<Goodlist> list = new ArrayList<Goodlist>();
	public static final String ChooseAdress = PATHS
			+ "agents/getAddressList/";
	public static String suportare;
	public static String suportcl;
	public static String tv_sqkt;
	public static int GOODID = -1;
	public static int goodId;
	public static String getmes=PATHS+"message/receiver/getAll";

	// upload image url
	public static final String UPLOAD_IMAGE = PATHS
			+ "/comment/upload/tempImage";

	public static final String SHARED = "zfandroid";
	public static String CITY = "上海";
	public static boolean isFRIST = false;
	static Gson gson = new Gson();
	// Apply List
	public static final String APPLY_LIST = PATHS + "/apply/getApplyList";
	// Apply Detail
	public static final String APPLY_DETAIL = PATHS + "/apply/getApplyDetails";

	// Get the Merchant Detail
	public static final String APPLY_MERCHANT_DETAIL = PATHS
			+ "/apply/getMerchant";
	// upload image url
	// Get the Channel List
	public static final String APPLY_CHANNEL_LIST = PATHS
			+ "/apply/getChannels";

	// Get the Bank List
	public static final String APPLY_BANK_LIST = PATHS + "/apply/ChooseBank";

	// find pos password
	public static final String TERMINAL_FIND_POS = PATHS
			+ "/terminal/Encryption";

	// Terminal list
		public static final String TERMINAL_APPLY_LIST = PATHS
				+ "terminal/getApplyList";

		// Terminal detail
		public static final String TERMINAL_DETAIL = PATHS
				+ "terminal/getApplyDetails";

		// terminal marchants
		public static final String TERMINAL_MERCHANTS = PATHS
				+ "terminal/getMerchants";

		// terminal bind
		public static final String TERMINAL_BIND = PATHS
				+ "terminal/bindingTerminals";

		// terminal bind sms
		public static final String TERMINAL_BIND_SMS = PATHS
				+ "terminal/sendPhoneVerificationCodeReg";

		// terminal add customer
		public static final String ADD_CUSTOMER = PATHS + "terminal/addCustomer";

		// terminal get address
		public static final String TERMINAL_GET_ADDRESS = PATHS
				+ "terminal/getAddressee";


	// terminal submitAgent
	public static final String TERMINAL_SUBMIT = PATHS
			+ "terminal/submitAgent";
	
    // terminal batchTerminalNum
	public static final String TERMINAL_BATCH_TERMINALNUM = PATHS
			+ "terminal/batchTerminalNum";
	
	// terminal screeningTerminalNum
	public static final String TERMINAL_SCREEN_TERMINALNUM = PATHS
			+ "terminal/screeningTerminalNum";
	
	// Get msglist
	public static final String GET_MSGLIST = PATHS + "message/receiver/getAll";

	// 售后记录---售后单记录---列表
	public static final String AGENTS_SEARCH = PATHS + "cs/agents/search";
	// 售后记录---售后单记录---取消申请
	public static final String AGENTS_CANCELAPPLY = PATHS
			+ "cs/agents/cancelApply";
	// 售后记录--售后单记录--增加物流信息
	public static final String AGENTS_ADDMARK = PATHS + "cs/agents/addMark";
	// 售后记录---售后单记录---详情
	public static final String AGENTS_GETBYID = PATHS + "cs/agents/getById";
	// 售后记录---注销记录---列表
	public static final String CANCELS_GETALL = PATHS + "cs/cancels/search";
	// 售后记录---注销记录---取消申请
	public static final String CANCELS_CANCELAPPLY = PATHS
			+ "cs/cancels/cancelApply";
	// 售后记录---注销记录---重新提交注销
	public static final String CANCELS_RESUBMITCANCEL = PATHS
			+ "cs/cancels/resubmitCancel";
	// 售后记录---注销记录---详情
	public static final String CANCELS_GETCANCELBYID = PATHS
			+ "cs/cancels/getCanCelById";
	// 售后记录---更新资料记录---列表
	public static final String INFO_GETALL = PATHS + "update/info/search";
	// 售后记录---更新资料记录---取消申请
	public static final String INFO_CANCELAPPLY = PATHS
			+ "update/info/cancelApply";
	// 售后记录---更新资料记录---详情
	public static final String INFO_GETINFOBYID = PATHS
			+ "update/info/getInfoById";
	// 用户管理--获得该代理商下面所有用户
	public static final String USER_GETUSER = PATHS + "user/getUser";
	// 用户管理--删除用户
	public static final String USER_DELECTAGENTUSER = PATHS
			+ "user/delectAgentUser";
	// 用户管理--获得该代理商下面某个用户的相关终端列表
	public static final String USER_GETTERMINALS = PATHS + "user/getTerminals";
	//库存管理列表
		public static final String STOCK_LIST = PATHS + "stock/list";
		//库存管理详情（下级代理商列表）
		public static final String STOCK_INFO = PATHS + "stock/info";
		//库存管理商品重命名
		public static final String STOCK_RENAME = PATHS + "stock/rename";
		//库存管理详情（下级代理商终端列表）
		public static final String STOCK_TERMINALLIST = PATHS + "stock/terminallist";
	// get code
	public static final String GETCODE4PHONE = PATHS
			+ "/user/sendPhoneVerificationCodeReg";
	// select pos
		public static final String SELECTPOS = PATHS + "terminal/screeningPosName";
	// apply submit
	public static final String APPLY_SUBMIT = PATHS + "apply/addOpeningApply";

	public static boolean CheckIsLogin(Context c) {
		return true;

	}

	public static void PostSearch(Context context, int agentId, int type,
			String keys, int city_id, int rows, int page, int orderType,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("keys", keys);
		params.put("city_id", city_id);
		params.put("rows", rows);
		params.put("page", page);
		params.put("orderType", orderType);
		params.put("type", type);
		params.put("hasLease", Posport.has_purchase);
		params.put("minPrice", Posport.minPrice);
		params.put("maxPrice", Posport.maxPrice);
		try {
			params.put("brands_id",
					new JSONArray(gson.toJson(Posport.brands_id)));
			params.put("category", new JSONArray(gson.toJson(Posport.category)));
			params.put("pay_channel_id",
					new JSONArray(gson.toJson(Posport.pay_channel_id)));
			params.put("pay_card_id",
					new JSONArray(gson.toJson(Posport.pay_card_id)));
			params.put("trade_type_id",
					new JSONArray(gson.toJson(Posport.trade_type_id)));
			params.put("sale_slip_id",
					new JSONArray(gson.toJson(Posport.sale_slip_id)));
			params.put("tDate", new JSONArray(gson.toJson(Posport.tDate)));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new HttpRequest(context, callback).post(Config.POSLIST, params);

		System.out.println("参数--" + params.toString());
		System.out.println("url--" + Config.POSLIST);
	}
	public static void GOODCONFIRM(Context context, int customerId, int goodId,
			int paychannelId, int quantity, int addressId, String comment,
			int is_need_invoice, int invoice_type, String invoice_info,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("quantity", quantity);
		params.put("addressId", addressId);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", invoice_info);
		System.out.println("参数--" + params.toString());
		new HttpRequest(context, callback).post(Config.SHOPORDER, params);
		// new HttpRequest(context, callback).post(Config.ZDORDER, params);
	}

	public static void GOODCONFIRM1(Context context, int customerId,
			int goodId, int paychannelId, int quantity, int addressId,
			String comment, int is_need_invoice, int invoice_type,
			String invoice_info,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("quantity", quantity);
		params.put("addressId", addressId);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", invoice_info);
		System.out.println("参数--" + params.toString());
		// new HttpRequest(context, callback).post(Config.SHOPORDER, params);
		new HttpRequest(context, callback).post(Config.ZDORDER, params);
	}


	public static void getApplyList(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(APPLY_LIST, params);
	}

	public static void getApplyDetail(Context context, int customerId,
			int terminalId, int status, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("terminalsId", terminalId);
		params.put("status", status);
		new HttpRequest(context, callback).post(APPLY_DETAIL, params);
	}

	public static void getApplyMerchantDetail(Context context, int merchantId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantId", merchantId);
		new HttpRequest(context, callback).post(APPLY_MERCHANT_DETAIL, params);
	}

	public static void getApplyChannelList(Context context,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(APPLY_CHANNEL_LIST);
	}

	public static void getApplyBankList(Context context, String terminalNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serial_num", terminalNumber);
		new HttpRequest(context, callback).post(APPLY_BANK_LIST, params);
	}

	public static void findPosPassword(Context context, int terminalId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalid", terminalId);
		new HttpRequest(context, callback).post(TERMINAL_FIND_POS, params);
	}

	public static void getTerminalApplyList(Context context, int customerId,
			int page, int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(TERMINAL_APPLY_LIST, params);
	}

	public static void getTerminalDetail(Context context, int terminalId,
			int customerId, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalsId", terminalId);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(TERMINAL_DETAIL, params);
	}

	public static void getTerminalDetail(Context context, int terminalId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalsId", terminalId);
		new HttpRequest(context, callback).post(TERMINAL_DETAIL, params);
	}
	
	public static void getMsgList(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(GET_MSGLIST, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_MSGLIST);
	}

	public static void getAfterSaleList(Context context, int recordType,
			int customerId, String search, String q, int page, int rows,
			HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_SEARCH;
			break;
		case 1:
			url = CANCELS_GETALL;
			break;
		case 2:
			url = INFO_GETALL;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("search", search);
		params.put("q", q);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void cancelApply(Context context, int recordType, int id,
			HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_CANCELAPPLY;
			break;
		case 1:
			url = CANCELS_CANCELAPPLY;
			break;
		case 2:
			url = INFO_CANCELAPPLY;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void getAfterSaleDetail(Context context, int recordType,
			int recordId, HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_GETBYID;
			break;
		case 1:
			url = CANCELS_GETCANCELBYID;
			break;
		case 2:
			url = INFO_GETINFOBYID;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void resubmitCancel(Context context, int recordId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(CANCELS_RESUBMITCANCEL, params);
	}

	public static void userGetUser(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETUSER, params);
	}

	public static void userDelectAgentUser(Context context,
			JSONArray customerArrayId, int agentId, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerArrayId", customerArrayId);
		params.put("agentId", agentId);
		new HttpRequest(context, callback).post(USER_DELECTAGENTUSER, params);
	}

	public static void userGetTerminals(Context context, int customerId,
			int page, int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETTERMINALS, params);
	}

	public static void agentsAddMark(Context context, int id,
			String computer_name, String track_number, int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("computer_name", computer_name);
		params.put("track_number", track_number);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(AGENTS_ADDMARK, params);
	}


	public static void stockList(
			Context context,
			int agentId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(STOCK_LIST, params);
	}
	public static void stockInfo(
			Context context,
			int agentId,
			int paychannelId,
			int goodId,
			String agentname,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("paychannelId", paychannelId);
		params.put("goodId", goodId);
		params.put("agentname", agentname);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(STOCK_LIST, params);
	}
	public static void stockRename(
			Context context,
			int agentId,
			int goodId,
			String goodname,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("goodId", goodId);
		params.put("goodname", goodname);
		new HttpRequest(context, callback).post(STOCK_RENAME, params);
	}
	public static void stockTerminallist(
			Context context,
			int agentId,
			int paychannelId,
			int goodId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("paychannelId", paychannelId);
		params.put("goodId", goodId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(STOCK_TERMINALLIST, params);
	}
	
	public static void AddAdres1(Context context, String codeNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);

		new HttpRequest(context, callback).post(GETCODE4PHONE, params);
	}
	public static void GetAdressList(Context context, int  customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(ChooseAdress, params);
		Log.e("customerId", customerId+"");
		Log.e("GET_ADDRESS_LIST", ChooseAdress);
	}
	public static void GetOrderList(Context context, int  customerId,String type,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("customerId", customerId);
		params.put("p", type);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(ORDERLIST, params);
		Log.e("customerId", customerId+"");
		Log.e("GET_ADDRESS_LIST", ORDERLIST);
	}
	
	public static void selectPOS(Context context, int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);

		new HttpRequest(context, callback).post(SELECTPOS, params);
	}
	
	public static void submitApply(Context context, Map<String, Object> params,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(APPLY_SUBMIT, params);
	}
	public static void getMerchants(Context context, int agentId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(TERMINAL_MERCHANTS, params);
	}

	public static void bindingTerminal(Context context, String terminalsNum,
			int userId, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalsNum", terminalsNum);
		params.put("userId", userId);
		new HttpRequest(context, callback).post(TERMINAL_BIND, params);
	}

	public static void sendPhoneVerificationCodeReg(Context context,
			String codeNumber, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);
		new HttpRequest(context, callback).post(TERMINAL_BIND_SMS, params);
	}

	public static void addCustomer(Context context, String codeNumber,
			String name, String password, int cityId, int agentId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);
		params.put("name", name);
		params.put("password", password);
		params.put("cityId", cityId);
		params.put("agentId", agentId);
		new HttpRequest(context, callback).post(ADD_CUSTOMER, params);
	}

	public static void getAddressee(Context context, int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(TERMINAL_GET_ADDRESS, params);
	}

	public static void submitAgent(Context context, int customerId,
			int terminalsQuantity, String address, String reason,
			String terminalsList, String reciver, String phone,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("terminalsQuantity", terminalsQuantity);
		params.put("address", address);
		params.put("reason", reason);
		params.put("terminalsList", terminalsList);
		params.put("reciver", reciver);
		params.put("phone", phone);
		new HttpRequest(context, callback).post(TERMINAL_SUBMIT, params);
	}


	public static void batchTerminalNum(Context context, String[] serialNum,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serialNum", serialNum);
		new HttpRequest(context, callback).post(TERMINAL_BATCH_TERMINALNUM, params);
	}
	
	public static void screeningTerminalNum(Context context, String title,int channelsId,
			int minPrice,int maxPrice,
			int agentId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("channelsId", channelsId);
		params.put("minPrice", minPrice);
		params.put("maxPrice", maxPrice);
		params.put("agentId", agentId);
		new HttpRequest(context, callback).post(TERMINAL_SCREEN_TERMINALNUM, params);
	}

}
