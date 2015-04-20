package com.comdo.zf_agent_a_pad.entity;

public class DistributeEntity {
private int id;
private String company_name;
private String created_at;
private String quantity;
public DistributeEntity(int id,String company_name,String created_at,String quantity){
	super();
	this.id=id;
	this.company_name=company_name;
	this.created_at=created_at;
	this.quantity=quantity;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCompany_name() {
	return company_name;
}
public void setCompany_name(String company_name) {
	this.company_name = company_name;
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
@Override
public String toString() {
	return "DistributeEntity [id=" + id + ", company_name=" + company_name
			+ ", created_at=" + created_at + ", quantity=" + quantity + "]";
}

}
