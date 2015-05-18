package com.comdo.zf_agent_a_pad.entity;

import java.io.Serializable;

/**
 * Created by holin on 4/13/15.
 */
public class TerminalPriceEntity implements Serializable {
	private int id;
	private float retail_price;
	private String serial_num;
	private Boolean isCheck =false;

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getRetail_price() {
		return retail_price / 100.00f;
	}

	public void setRetail_price(float retail_price) {
		this.retail_price = retail_price;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
}
