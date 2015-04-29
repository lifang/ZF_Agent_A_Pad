package com.comdo.zf_agent_a_pad.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplyChannelagain implements Serializable{
	private int id;
	private String paychannel;
	private List<Billing> billings = new ArrayList<Billing>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaychannel() {
		return paychannel;
	}

	public void setPaychannel(String paychannel) {
		this.paychannel = paychannel;
	}

	public List<Billing> getBillings() {
		return billings;
	}

	public void setBillings(List<Billing> billings) {
		this.billings = billings;
	}

	public class Billing implements Serializable {
		public int id;
		public String paychannel;
	}
}
