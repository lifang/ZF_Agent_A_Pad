package com.comdo.zf_agent_a_pad.entity;

public class AgentManager {
private int id;
private int types;
private String company_name;
private String created_at;
public AgentManager(int id,int types,
		String company_name,String created_at){
	super();
	this.id=id;
	this.types=types;
	this.company_name=company_name;
	this.created_at=created_at;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getTypes() {
	return types;
}
public void setTypes(int types) {
	this.types = types;
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
@Override
public String toString() {
	return "AgentManager [id=" + id + ", types=" + types + ", company_name="
			+ company_name + ", created_at=" + created_at + "]";
}

}
