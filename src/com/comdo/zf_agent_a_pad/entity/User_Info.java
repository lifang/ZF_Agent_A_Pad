package com.comdo.zf_agent_a_pad.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class User_Info implements Serializable {
	
	private int id;
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
