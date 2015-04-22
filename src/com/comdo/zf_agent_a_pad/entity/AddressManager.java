package com.comdo.zf_agent_a_pad.entity;

public class AddressManager {

private long id;
private String receiver;
private String city;
private String address;
private String zipCode;
private String moblephone;
private String isDefault;
public AddressManager(long id,String receiver,String city,String address,
		String zipCode,String moblephone,String isDefault){
	super();
	this.id=id;
	this.receiver=receiver;
	this.city=city;
	this.address=address;
	this.zipCode=zipCode;
	this.moblephone=moblephone;
	this.isDefault=isDefault;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getReceiver() {
	return receiver;
}
public void setReceiver(String receiver) {
	this.receiver = receiver;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getZipCode() {
	return zipCode;
}
public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}
public String getMoblephone() {
	return moblephone;
}
public void setMoblephone(String moblephone) {
	this.moblephone = moblephone;
}
public String getIsDefault() {
	return isDefault;
}
public void setIsDefault(String isDefault) {
	this.isDefault = isDefault;
}
@Override
public String toString() {
	return "AddressManager [id=" + id + ", receiver=" + receiver + ", city="
			+ city + ", address=" + address + ", zipCode=" + zipCode
			+ ", moblephone=" + moblephone + ", isDefault=" + isDefault + "]";
}



}

