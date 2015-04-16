package com.comdo.zf_agent_a_pad.entity;

public class AgentManager {
private long id;
private String agent_type;
private String agent_name;
private String join_time;
public AgentManager(long id,String agent_type,
		String agent_name,String join_time){
	super();
	this.id=id;
	this.agent_type=agent_type;
	this.agent_name=agent_name;
	this.join_time=join_time;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getAgent_type() {
	return agent_type;
}
public void setAgent_type(String agent_type) {
	this.agent_type = agent_type;
}
public String getAgent_name() {
	return agent_name;
}
public void setAgent_name(String agent_name) {
	this.agent_name = agent_name;
}
public String getJoin_time() {
	return join_time;
}
public void setJoin_time(String join_time) {
	this.join_time = join_time;
}
@Override
public String toString() {
	return "AgentManager [id=" + id + ", agent_type=" + agent_type
			+ ", agent_name=" + agent_name + ", join_time=" + join_time + "]";
}

}
