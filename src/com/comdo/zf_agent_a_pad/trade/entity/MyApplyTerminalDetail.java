package com.comdo.zf_agent_a_pad.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/6.
 */
public class MyApplyTerminalDetail {

	private String brandName;

	@SerializedName("model_number")
	private String modelNumber;

	@SerializedName("serial_num")
	private String serialNumber;

	private String channelName;

	private int supportRequirementType;

	private int channelId;

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getSupportRequirementType() {
		return supportRequirementType;
	}

	public void setSupportRequirementType(int supportRequirementType) {
		this.supportRequirementType = supportRequirementType;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
