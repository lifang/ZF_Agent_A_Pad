package com.comdo.zf_agent_a_pad.trade.entity;

import java.io.Serializable;

public class ApplyChooseItem implements Serializable {

	private static final long serialVersionUID = -8498896186807080462L;
	private int id;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
