package com.comdo.zf_agent_a_pad.entity;

public class UserMLEntity {
	private String createdAt;
	private String name;
	private String agentId;
	private String customersId;
	
	private String phone;
	private String email;
	
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getCustomersId() {
		return customersId;
	}
	public void setCustomersId(String customersId) {
		this.customersId = customersId;
	}
	
	
}
