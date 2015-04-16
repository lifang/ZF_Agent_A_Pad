package com.comdo.zf_agent_a_pad.entity;

public class DistributeEntity {
private long id;
private String with;
private String time;
private String amount;
public DistributeEntity(long id,String with,String time,String amount){
	super();
	this.id=id;
	this.with=with;
	this.time=time;
	this.amount=amount;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getWith() {
	return with;
}
public void setWith(String with) {
	this.with = with;
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
	return "distributeEntity [id=" + id + ", with=" + with + ", time=" + time
			+ ", amount=" + amount + "]";
}

}
