package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class AfterSaleDetailOrderEntity {
	private String id;
	private String apply_time;
	private String terminals_list;
	private String reason;
	private String address;
	private String status;
	private String terminals_quantity;
	
	private Comments comments;

	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getTerminals_list() {
		return terminals_list;
	}
	public void setTerminals_list(String terminals_list) {
		this.terminals_list = terminals_list;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTerminals_quantity() {
		return terminals_quantity;
	}
	public void setTerminals_quantity(String terminals_quantity) {
		this.terminals_quantity = terminals_quantity;
	}
	public Comments getComments() {
		return comments;
	}
	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public class Comments {
		private String total;
		private List<MarkEntity> list;
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public List<MarkEntity> getList() {
			return list;
		}
		public void setList(List<MarkEntity> list) {
			this.list = list;
		}
	}
}
