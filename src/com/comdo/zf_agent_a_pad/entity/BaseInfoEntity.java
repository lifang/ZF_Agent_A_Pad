package com.comdo.zf_agent_a_pad.entity;

public class BaseInfoEntity {
private String types;
private String company_name;
private String business_license;
private String tax_registered_no;
private String username;
private String card_id;
private String phone;
private String email;
private String city_id;
private String address;
private String card_id_photo_path;
private String license_no_pic_path;
private String tax_no_pic_path;
private int id;
public BaseInfoEntity(String types,String company_name,String business_license,String tax_registered_no,
		String username,String card_id,String phone,String email,String city_id,
		String address,String card_id_photo_path,String license_no_pic_path,String tax_no_pic_path,
		String login_id){
	super();
	this.types=types;
	this.company_name=company_name;
	this.business_license=business_license;
	this.tax_registered_no=tax_registered_no;
	this.username=username;
	this.card_id=card_id;
	this.phone=phone;
	this.email=email;
	this.city_id=city_id;
	this.address=address;
	this.card_id_photo_path=card_id_photo_path;
	this.license_no_pic_path=license_no_pic_path;
	this.tax_no_pic_path=tax_no_pic_path;
	this.id=id;
	
}
public String getTypes() {
	return types;
}
public void setTypes(String types) {
	this.types = types;
}
public String getCompany_name() {
	return company_name;
}
public void setCompany_name(String company_name) {
	this.company_name = company_name;
}
public String getBusiness_license() {
	return business_license;
}
public void setBusiness_license(String business_license) {
	this.business_license = business_license;
}
public String getTax_registered_no() {
	return tax_registered_no;
}
public void setTax_registered_no(String tax_registered_no) {
	this.tax_registered_no = tax_registered_no;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getCard_id() {
	return card_id;
}
public void setCard_id(String card_id) {
	this.card_id = card_id;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCity_id() {
	return city_id;
}
public void setCity_id(String city_id) {
	this.city_id = city_id;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCard_id_photo_path() {
	return card_id_photo_path;
}
public void setCard_id_photo_path(String card_id_photo_path) {
	this.card_id_photo_path = card_id_photo_path;
}
public String getLicense_no_pic_path() {
	return license_no_pic_path;
}
public void setLicense_no_pic_path(String license_no_pic_path) {
	this.license_no_pic_path = license_no_pic_path;
}
public String getTax_no_pic_path() {
	return tax_no_pic_path;
}
public void setTax_no_pic_path(String tax_no_pic_path) {
	this.tax_no_pic_path = tax_no_pic_path;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
@Override
public String toString() {
	return "BaseInfoEntity [types=" + types + ", company_name=" + company_name
			+ ", business_license=" + business_license + ", tax_registered_no="
			+ tax_registered_no + ", username=" + username + ", card_id="
			+ card_id + ", phone=" + phone + ", email=" + email + ", city_id="
			+ city_id + ", address=" + address + ", card_id_photo_path="
			+ card_id_photo_path + ", license_no_pic_path="
			+ license_no_pic_path + ", tax_no_pic_path=" + tax_no_pic_path
			+ ", id=" + id + "]";
}

}
