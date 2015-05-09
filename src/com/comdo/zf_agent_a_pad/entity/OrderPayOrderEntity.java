package com.comdo.zf_agent_a_pad.entity;

public class OrderPayOrderEntity {
	private String order_id;
	private String pay_status;
	private String order_totalPrice;
	private String price_dingjin;

	private String zhifu_dingjin;
	private String order_number;
	private String shengyu_price;
	private String body;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getOrder_totalPrice() {
		return order_totalPrice;
	}
	public void setOrder_totalPrice(String order_totalPrice) {
		this.order_totalPrice = order_totalPrice;
	}
	public String getPrice_dingjin() {
		return price_dingjin;
	}
	public void setPrice_dingjin(String price_dingjin) {
		this.price_dingjin = price_dingjin;
	}
	public String getZhifu_dingjin() {
		return zhifu_dingjin;
	}
	public void setZhifu_dingjin(String zhifu_dingjin) {
		this.zhifu_dingjin = zhifu_dingjin;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getShengyu_price() {
		return shengyu_price;
	}
	public void setShengyu_price(String shengyu_price) {
		this.shengyu_price = shengyu_price;
	}


}
