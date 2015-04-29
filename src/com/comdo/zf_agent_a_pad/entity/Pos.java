package com.comdo.zf_agent_a_pad.entity;

import java.io.Serializable;

public class Pos implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -7390461574560424868L;
private int id;
private String goodname;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getGoodname() {
	return goodname;
}
public void setGoodname(String goodname) {
	this.goodname = goodname;
}
@Override
public String toString() {
	return "Pos [id=" + id + ", goodname=" + goodname + "]";
}

}
