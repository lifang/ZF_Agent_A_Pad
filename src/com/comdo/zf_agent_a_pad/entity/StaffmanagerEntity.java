package com.comdo.zf_agent_a_pad.entity;

public class StaffmanagerEntity {
	private long id;
	private String loginid;
	private String name;
	private String time;
	public StaffmanagerEntity(long id,String loginid,String name,String time){
		super();
		this.id=id;
		this.loginid=loginid;
		this.name=name;
		this.time=time;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "StaffmanagerEntity [id=" + id + ", loginid=" + loginid
				+ ", name=" + name + ", time=" + time + "]";
	}
	
}
