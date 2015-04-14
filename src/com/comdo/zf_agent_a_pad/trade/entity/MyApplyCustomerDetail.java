package com.comdo.zf_agent_a_pad.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/6.
 */
public class MyApplyCustomerDetail {

	private String key;

	private String value;

	private int types;

	@SerializedName("target_id")
	private int targetId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
}
