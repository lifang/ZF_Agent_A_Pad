package com.comdo.zf_agent_a_pad.entity;

public class AdressEntity {
//"id":16,"customerId":80,"receiver":"123","isDefault":2,"address":"123",
	private int id;
	private int customerId;
    private int is_default;

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    private String receiver;
	private Boolean Ischeck;
	private String address;
	//,"cityId":123,"zipCode":"123","moblephone":"123","telphone":"123
	private int cityId;
	private int zipCode;
	private String moblephone;
	private String telphone;
	public Boolean getIscheck() {
		return Ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		Ischeck = ischeck;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public String getMoblephone() {
		return moblephone;
	}
	public void setMoblephone(String moblephone) {
		this.moblephone = moblephone;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
}