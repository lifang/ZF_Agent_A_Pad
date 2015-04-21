package com.comdo.zf_agent_a_pad.entity;

import java.io.Serializable;

public class SelectPOS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4457259861506111001L;

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
