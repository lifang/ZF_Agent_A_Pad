package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class OrderDetailPG {
	private String order_id;

	private String shipped_quantity;

	private String pay_status;

	private String order_totalPrice;

	private String order_oldPrice;

	private String total_dingjin;

	private String zhifu_dingjin;

	private String shengyu_price;

	private String haspayed_price;

	private String total_quantity;

	private String order_receiver;

	private String order_receiver_phone;

	private String order_address;

	private String order_comment;

	private String order_invoce_type;

	private String order_invoce_info;

	private String order_number;

	private String order_payment_type;

	private String order_createTime;

	private String order_status;

	private String order_goods_size;

	private String good_merchant;

	private String terminals;

	private List<Goodlist> order_goodsLists ;

	private String comments;

	public void setOrder_id(String order_id){
	this.order_id = order_id;
	}
	public String getOrder_id(){
	return this.order_id;
	}
	public void setShipped_quantity(String shipped_quantity){
	this.shipped_quantity = shipped_quantity;
	}
	public String getShipped_quantity(){
	return this.shipped_quantity;
	}
	public void setPay_status(String pay_status){
	this.pay_status = pay_status;
	}
	public String getPay_status(){
	return this.pay_status;
	}
	public void setOrder_totalPrice(String order_totalPrice){
	this.order_totalPrice = order_totalPrice;
	}
	public String getOrder_totalPrice(){
	return this.order_totalPrice;
	}
	public void setOrder_oldPrice(String order_oldPrice){
	this.order_oldPrice = order_oldPrice;
	}
	public String getOrder_oldPrice(){
	return this.order_oldPrice;
	}
	public void setTotal_dingjin(String total_dingjin){
	this.total_dingjin = total_dingjin;
	}
	public String getTotal_dingjin(){
	return this.total_dingjin;
	}
	public void setZhifu_dingjin(String zhifu_dingjin){
	this.zhifu_dingjin = zhifu_dingjin;
	}
	public String getZhifu_dingjin(){
	return this.zhifu_dingjin;
	}
	public void setShengyu_price(String shengyu_price){
	this.shengyu_price = shengyu_price;
	}
	public String getShengyu_price(){
	return this.shengyu_price;
	}
	public void setHaspayed_price(String haspayed_price){
	this.haspayed_price = haspayed_price;
	}
	public String getHaspayed_price(){
	return this.haspayed_price;
	}
	public void setTotal_quantity(String total_quantity){
	this.total_quantity = total_quantity;
	}
	public String getTotal_quantity(){
	return this.total_quantity;
	}
	public void setOrder_receiver(String order_receiver){
	this.order_receiver = order_receiver;
	}
	public String getOrder_receiver(){
	return this.order_receiver;
	}
	public void setOrder_receiver_phone(String order_receiver_phone){
	this.order_receiver_phone = order_receiver_phone;
	}
	public String getOrder_receiver_phone(){
	return this.order_receiver_phone;
	}
	public void setOrder_address(String order_address){
	this.order_address = order_address;
	}
	public String getOrder_address(){
	return this.order_address;
	}
	public void setOrder_comment(String order_comment){
	this.order_comment = order_comment;
	}
	public String getOrder_comment(){
	return this.order_comment;
	}
	public void setOrder_invoce_type(String order_invoce_type){
	this.order_invoce_type = order_invoce_type;
	}
	public String getOrder_invoce_type(){
	return this.order_invoce_type;
	}
	public void setOrder_invoce_info(String order_invoce_info){
	this.order_invoce_info = order_invoce_info;
	}
	public String getOrder_invoce_info(){
	return this.order_invoce_info;
	}
	public void setOrder_number(String order_number){
	this.order_number = order_number;
	}
	public String getOrder_number(){
	return this.order_number;
	}
	public void setOrder_payment_type(String order_payment_type){
	this.order_payment_type = order_payment_type;
	}
	public String getOrder_payment_type(){
	return this.order_payment_type;
	}
	public void setOrder_createTime(String order_createTime){
	this.order_createTime = order_createTime;
	}
	public String getOrder_createTime(){
	return this.order_createTime;
	}
	public void setOrder_status(String order_status){
	this.order_status = order_status;
	}
	public String getOrder_status(){
	return this.order_status;
	}
	public void setOrder_goods_size(String order_goods_size){
	this.order_goods_size = order_goods_size;
	}
	public String getOrder_goods_size(){
	return this.order_goods_size;
	}
	public void setGood_merchant(String good_merchant){
	this.good_merchant = good_merchant;
	}
	public String getGood_merchant(){
	return this.good_merchant;
	}
	public void setTerminals(String terminals){
	this.terminals = terminals;
	}
	public String getTerminals(){
	return this.terminals;
	}

	public List<Goodlist> getOrder_goodsLists() {
		return order_goodsLists;
	}
	public void setOrder_goodsLists(List<Goodlist> order_goodsLists) {
		this.order_goodsLists = order_goodsLists;
	}
	public void setComments(String comments){
	this.comments = comments;
	}
	public String getComments(){
	return this.comments;
	}
}
