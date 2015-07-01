package com.comdo.zf_agent_a_pad.entity;

import java.util.List;


public class OrderDetailEntity {
	public String getShipped_quantity() {
		return shipped_quantity;
	}

	public void setShipped_quantity(String shipped_quantity) {
		this.shipped_quantity = shipped_quantity;
	}
	private int shengyu_price;
	public int getShengyu_price() {
		return shengyu_price;
	}
	private int bd_cost;
	private int order_type;
	
	
	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}

	public int getBd_cost() {
		return bd_cost;
	}

	public void setBd_cost(int bd_cost) {
		this.bd_cost = bd_cost;
	}

	public void setShengyu_price(int shengyu_price) {
		this.shengyu_price = shengyu_price;
	}
	public int getNeed_invoice() {
		return need_invoice;
	}

	public void setNeed_invoice(int need_invoice) {
		this.need_invoice = need_invoice;
	}
	private String logistics_name;
	private String logistics_number;
	public String getLogistics_name() {
		return logistics_name;
	}

	public void setLogistics_name(String logistics_name) {
		this.logistics_name = logistics_name;
	}

	public String getLogistics_number() {
		return logistics_number;
	}

	public void setLogistics_number(String logistics_number) {
		this.logistics_number = logistics_number;
	}
	private int need_invoice;
	private String total_dingjin;
	private String pay_status; 
	private String guishu_user;
	private String total_quantity;
	private String order_totalPrice;
	private String shipped_quantity;
	private String zhifu_dingjin;
	public String getZhifu_dingjin() {
		return zhifu_dingjin;
	}

	public void setZhifu_dingjin(String zhifu_dingjin) {
		this.zhifu_dingjin = zhifu_dingjin;
	}

	public String getOrder_totalPrice() {
		return order_totalPrice;
	}

	public void setOrder_totalPrice(String order_totalPrice) {
		this.order_totalPrice = order_totalPrice;
	}

	public String getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(String total_quantity) {
		this.total_quantity = total_quantity;
	}

	public String getTotal_dingjin() {
		return total_dingjin;
	}

	public void setTotal_dingjin(String total_dingjin) {
		this.total_dingjin = total_dingjin;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getGuishu_user() {
		return guishu_user;
	}

	public void setGuishu_user(String guishu_user) {
		this.guishu_user = guishu_user;
	}

	// "order_number": "020150206104427420",
	private String order_number;
	// "order_receiver_phone": "18352445051",\
	private String order_receiver_phone;
	// "order_payment_type": "",
	private String order_payment_type;// 
	private String terminals;

	public String getTerminals() {
		return terminals;
	}

	public void setTerminals(String terminals) {
		this.terminals = terminals;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	private String order_comment;

	private String order_address;
	
	private String order_invoce_type;

	private String order_oldprice;

	private int order_id;
	private String order_totalNum;

	private int order_status;

	private String order_receiver;

	private String order_createTime;

	private String order_psf;

	private String good_merchant;
	private String order_invoce_info;
	
	private CommentG comments;
	private List<Goodlist> order_goodsList;

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_receiver_phone() {
		return order_receiver_phone;
	}

	public void setOrder_receiver_phone(String order_receiver_phone) {
		this.order_receiver_phone = order_receiver_phone;
	}

	public String getOrder_payment_type() {
		return order_payment_type;
	}

	public void setOrder_payment_type(String order_payment_type) {
		this.order_payment_type = order_payment_type;
	}

	public String getOrder_comment() {
		return order_comment;
	}

	public void setOrder_comment(String order_comment) {
		this.order_comment = order_comment;
	}

	public String getOrder_address() {
		return order_address;
	}

	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}

	public String getOrder_invoce_type() {
		return order_invoce_type;
	}

	public void setOrder_invoce_type(String order_invoce_type) {
		this.order_invoce_type = order_invoce_type;
	}

	public String getOrder_oldprice() {
		return order_oldprice;
	}

	public void setOrder_oldprice(String order_oldprice) {
		this.order_oldprice = order_oldprice;
	}

	public int getId() {
		return order_id;
	}

	public void setId(int id) {
		this.order_id = id;
	}

	public String getOrder_totalNum() {
		return order_totalNum;
	}

	public void setOrder_totalNum(String order_totalNum) {
		this.order_totalNum = order_totalNum;
	}

	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getOrder_receiver() {
		return order_receiver;
	}

	public void setOrder_receiver(String order_receiver) {
		this.order_receiver = order_receiver;
	}

	public String getOrder_createTime() {
		return order_createTime;
	}

	public void setOrder_createTime(String order_createTime) {
		this.order_createTime = order_createTime;
	}

	public String getOrder_psf() {
		return order_psf;
	}

	public void setOrder_psf(String order_psf) {
		this.order_psf = order_psf;
	}

	public String getGood_merchant() {
		return good_merchant;
	}

	public void setGood_merchant(String good_merchant) {
		this.good_merchant = good_merchant;
	}

	public String getOrder_invoce_info() {
		return order_invoce_info;
	}

	public void setOrder_invoce_info(String order_invoce_info) {
		this.order_invoce_info = order_invoce_info;
	}



	public CommentG getComments() {
		return comments;
	}

	public void setComments(CommentG comments) {
		this.comments = comments;
	}

	public List<Goodlist> getOrder_goodsList() {
		return order_goodsList;
	}

	public void setOrder_goodsList(List<Goodlist> order_goodsList) {
		this.order_goodsList = order_goodsList;
	}

	public class CommentG {
		public List<MarkEntity> content;
		public int total;

		public List<MarkEntity> getContent() {
			return content;
		}

		public void setContent(List<MarkEntity> content) {
			this.content = content;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

	}
}