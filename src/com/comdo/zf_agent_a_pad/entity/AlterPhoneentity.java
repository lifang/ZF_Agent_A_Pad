package com.comdo.zf_agent_a_pad.entity;

public class AlterPhoneentity {
	private String dentcode;
	private int customerId;
	private String phone;
	private String email;
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDentcode() {
		return dentcode;
	}

	public void setDentcode(String dentcode) {
		this.dentcode = dentcode;
	}

}
