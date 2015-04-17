package com.comdo.zf_agent_a_pad.entity;

public class TerminalEntity {
private long id;
private String number;
public TerminalEntity(long id,String number){
	super();
	this.id=id;
	this.number=number;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
@Override
public String toString() {
	return "TerminalEntity [id=" + id + ", number=" + number + "]";
}

}
