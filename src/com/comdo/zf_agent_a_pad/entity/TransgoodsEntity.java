package com.comdo.zf_agent_a_pad.entity;

public class TransgoodsEntity {

private int id;
private String fromname;
private String created_at;
private String quantity;
private String toname;
public TransgoodsEntity(int id,String fromname,String created_at,String quantity,String toname){
	super();
	this.id=id;
	this.fromname=fromname;
	this.created_at=created_at;
	this.quantity=quantity;
	this.toname=toname;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFromname() {
	return fromname;
}
public void setFromname(String fromname) {
	this.fromname = fromname;
}
public String getCreated_at() {
	return created_at;
}
public void setCreated_at(String created_at) {
	this.created_at = created_at;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public String getToname() {
	return toname;
}
public void setToname(String toname) {
	this.toname = toname;
}
@Override
public String toString() {
	return "TransgoodsEntity [id=" + id + ", fromname=" + fromname
			+ ", created_at=" + created_at + ", quantity=" + quantity
			+ ", toname=" + toname + "]";
}


}
