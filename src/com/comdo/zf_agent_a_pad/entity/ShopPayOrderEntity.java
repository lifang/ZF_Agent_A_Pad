package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class ShopPayOrderEntity {
	private String address;
	private String total_price;
	private String receiver;
	private String actual_price;
	private String order_number;
	private String paytype;

	private List<Good> good;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getActual_price() {
		return actual_price;
	}
	public void setActual_price(String actual_price) {
		this.actual_price = actual_price;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public List<Good> getGood() {
		return good;
	}
	public void setGood(List<Good> good) {
		this.good = good;
	}


	public static class Good {
		private int quantity;
		private String pcname;
		private String title;
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getPcname() {
			return pcname;
		}
		public void setPcname(String pcname) {
			this.pcname = pcname;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}


	}
}
