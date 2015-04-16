package com.comdo.zf_agent_a_pad.entity;

public class TransgoodsEntity {

private long id;
private String transferobject;
private String time;
private String amount;
public TransgoodsEntity(long id,String transferobject,String time,String amount){
	super();
	this.id=id;
	this.transferobject=transferobject;
	this.time=time;
	this.amount=amount;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getTransferobject() {
	return transferobject;
}
public void setTransferobject(String transferobject) {
	this.transferobject = transferobject;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
@Override
public String toString() {
	return "TransgoodsEntity [id=" + id + ", transferobject=" + transferobject
			+ ", time=" + time + ", amount=" + amount + "]";
}



}
