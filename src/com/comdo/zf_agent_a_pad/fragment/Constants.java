package com.comdo.zf_agent_a_pad.fragment;

public class Constants {

	/**
	 * The intent data among city selection
	 */
	public static int TEST_CUSTOMER = 1;
	public static final int SUCCESS_CODE = 1;

	public static class CityIntent {
		public static final String SELECTED_PROVINCE = "selected_province";
		public static final String SELECTED_CITY = "selected_city";

		public static final String CITY_ID = "city_id";
		public static final String CITY_NAME = "city_name";
	}

	/**
	 * The type of trade
	 */
	public static class TradeType {
		public static final int TRANSFER = 1;
		public static final int CONSUME = 2;
		public static final int REPAYMENT = 3;
		public static final int LIFE_PAY = 5;
		public static final int PHONE_PAY = 4;
	}

	/**
	 * The intent data among trade activities
	 */
	public static class TradeIntent {
		public static final int REQUEST_TRADE_CLIENT = 0;
		public static final int REQUEST_TRADE_AGENT = 1;

		public static final String AGENT_ID = "agent_id";
		public static final String AGENT_NAME = "agent_name";
		public static final String SONAGEHNTID = "sonagentId";
		public static final String TRADE_TYPE = "trade_type";
		public static final String TRADE_RECORD_ID = "trade_record_id";
		public static final String CLIENT_NUMBER = "client_number";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
	}

	/**
	 * The type of after-sale records
	 */
	public static class AfterSaleType {
		public static final int MAINTAIN = 0;
		public static final int RETURN = 1;
		public static final int CANCEL = 2;
		public static final int CHANGE = 3;
		public static final int UPDATE = 4;
		public static final int LEASE = 5;
	}

	/**
	 * The intent data among after-sale activities
	 */
	public static class AfterSaleIntent {
		public static final int REQUEST_DETAIL = 0;
		public static final int REQUEST_MARK = 1;

		public static final String RECORD_TYPE = "record_type";
		public static final String RECORD_ID = "record_id";
		public static final String RECORD_STATUS = "record_status";
		public static final String MATERIAL_URL = "material_url";
	}

	/**
	 * The status of terminal
	 */
	public static class TerminalStatus {
		public static final int OPENED = 1;
		public static final int PART_OPENED = 2;
		public static final int UNOPENED = 3;
		public static final int CANCELED = 4;
		public static final int STOPPED = 5;
	}

	/**
	 * The intent data among terminal activities
	 */
	public static class TerminalIntent {
		public static final int REQUEST_ADD = 0;
		public static final int REQUEST_DETAIL = 1;

		public static final String TERMINAL_ID = "terminal_id";
		public static final String TERMINAL_NUMBER = "terminal_number";
		public static final String TERMINAL_STATUS = "terminal_status";
		public static final String CHANNEL_ID = "channel_id";
		public static final String CHANNEL_NAME = "channel_name";

		public static final String TERMINAL_CLIENT = "terminal_client";
		public static final String TERMINAL_TOTAL = "terminal_total";
		public static final String TERMINAL_ARRAY = "terminal_array";

		public static final String TERMINAL_TYPE = "terminal_type";
		public static final String HAVE_VIDEO = "have_video";
	}

	/**
	 * The intent data among apply activities
	 */
	public static class ApplyIntent {
		public static final int REQUEST_CHOOSE_MERCHANT = 0x1001;
		public static final int REQUEST_CHOOSE_CITY = 0x1002;
		public static final int REQUEST_CHOOSE_CHANNEL = 0x1003;
		public static final int REQUEST_CHOOSE_BANK = 0x1004;
		public static final int REQUEST_UPLOAD_IMAGE = 0x1005;
		public static final int REQUEST_TAKE_PHOTO = 0x1006;

		public static final String CHOOSE_TITLE = "choose_title";
		public static final String CHOOSE_ITEMS = "choose_items";
		public static final String SELECTED_ID = "selected_id";
		public static final String SELECTED_TITLE = "selected_title";

		public static final String SELECTED_CHANNEL_ID = "selected_channel_id";
		public static final String SELECTED_CHANNEL = "selected_channel";
		public static final String SELECTED_BILLING = "selected_billing";
		public static final String SELECTED_BANK = "selected_bank";
		public static final String SELECTED_TERMINAL = "selected_terminal";
		public static final String SELECTED_USER = "selected_user";
	}

}