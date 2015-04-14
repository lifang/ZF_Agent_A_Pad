package com.comdo.zf_agent_a_pad.trade.entity;

import com.google.gson.annotations.SerializedName;

public class TerminalManagerEntity {


	private int id;
	
	@SerializedName("serial_num")
	private String posPortID;// �ն˺�

	@SerializedName("brandsName")
	private String pos;// POS��

	@SerializedName("model_number")
	private String posname;// POS��
	
	@SerializedName("channelName")
	private String payChannel;// ֧��ͨ��

	@SerializedName("status")
	private int openState;// ��ͨ״̬

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosname() {
		return posname;
	}

	public void setPosname(String posname) {
		this.posname = posname;
	}

	public String getPosPortID() {
		return posPortID;
	}

	public void setPosPortID(String posPortID) {
		this.posPortID = posPortID;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public int getOpenState() {
		return openState;
	}

	public void setOpenState(int openState) {
		this.openState = openState;
	}

}
