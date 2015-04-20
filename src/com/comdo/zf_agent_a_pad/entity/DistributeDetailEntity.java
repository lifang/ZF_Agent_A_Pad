package com.comdo.zf_agent_a_pad.entity;

public class DistributeDetailEntity {
private int id;
private String company_name;
private String terminal_list;
private String created_at;
private String creator;
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
public String getTerminal_list() {
	return terminal_list;
}
public void setTerminal_list(String terminal_list) {
	this.terminal_list = terminal_list;
}
public String getCreated_at() {
	return created_at;
}
public void setCreated_at(String created_at) {
	this.created_at = created_at;
}
public String getCreator() {
	return creator;
}
public void setCreator(String creator) {
	this.creator = creator;
}
@Override
public String toString() {
	return "DistributeDetailEntity [id=" + id + ", company_name="
			+ company_name + ", terminal_list=" + terminal_list
			+ ", created_at=" + created_at + ", creator=" + creator + "]";
}

}
