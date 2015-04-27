package com.comdo.zf_agent_a_pad.entity;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
	@SerializedName("customersId")
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
