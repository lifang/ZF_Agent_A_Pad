package com.comdo.zf_agent_a_pad.entity;

public class AgentEntity {
private int id;
private String company_name;
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
@Override
public String toString() {
	return "AgentEntity [id=" + id + ", company_name=" + company_name + "]";
}

}
