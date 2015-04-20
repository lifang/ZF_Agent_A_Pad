package com.comdo.zf_agent_a_pad.entity;

public class StaffmanagerEntity {
	private long id;
	private String username;
	private String name;
	private String createdAt;
	public StaffmanagerEntity(long id,String username,String name,String createdAt){
		super();
		this.id=id;
		this.username=username;
		this.name=name;
		this.createdAt=createdAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "StaffmanagerEntity [id=" + id + ", username=" + username
				+ ", name=" + name + ", createdAt=" + createdAt + "]";
	}

}
