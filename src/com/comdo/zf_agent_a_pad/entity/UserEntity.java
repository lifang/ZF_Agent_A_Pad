package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class UserEntity {



	private int id;
	private String phone;
	private String username;
	private int status;
	private int cityId;
	private int accountType;
	private int types;
	private int is_have_profit;
	private List<Machtigingen> machtigingen;
	private int agentId;
	private int agentCityId;
	private String createdAt;	
	private int agentUserId;
	private int integral;
	private int parent_id;
	private String name;
	private String lastLoginedAt;
	private String email;
	private String updatedAt;
	public int getIs_have_profit() {
		return is_have_profit;
	}
	public void setIs_have_profit(int is_have_profit) {
		this.is_have_profit = is_have_profit;
	}
	public List<Machtigingen> getMachtigingen() {
		return machtigingen;
	}
	public void setMachtigingen(List<Machtigingen> machtigingen) {
		this.machtigingen = machtigingen;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public int getAgentCityId() {
		return agentCityId;
	}
	public void setAgentCityId(int agentCityId) {
		this.agentCityId = agentCityId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getAgentUserId() {
		return agentUserId;
	}
	public void setAgentUserId(int agentUserId) {
		this.agentUserId = agentUserId;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastLoginedAt() {
		return lastLoginedAt;
	}
	public void setLastLoginedAt(String lastLoginedAt) {
		this.lastLoginedAt = lastLoginedAt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	
}
