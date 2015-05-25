package com.comdo.zf_agent_a_pad.entity;

public class AgentDetailEntity {
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String types;
	private String is_have_profit;
	private String company_name;
	private String business_license;
	private String tax_registered_no;
	private String name;
	private String card_id;
	private String phone;
	private String email;
	private int cityId;
	private String cityName;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	private String address;
	private String cardpath;
	private String licensepath;
	private String taxpath;
    private String loginId;
    private String created_at;
    private String soldnum;
    private String opennum;
    private String allQty;
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getIs_have_profit() {
		return is_have_profit;
	}
	public void setIs_have_profit(String is_have_profit) {
		this.is_have_profit = is_have_profit;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCardpath() {
		return cardpath;
	}
	public void setCardpath(String cardpath) {
		this.cardpath = cardpath;
	}
	public String getLicensepath() {
		return licensepath;
	}
	public void setLicensepath(String licensepath) {
		this.licensepath = licensepath;
	}
	public String getTaxpath() {
		return taxpath;
	}
	public void setTaxpath(String taxpath) {
		this.taxpath = taxpath;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getSoldnum() {
		return soldnum;
	}
	public void setSoldnum(String soldnum) {
		this.soldnum = soldnum;
	}
	public String getOpennum() {
		return opennum;
	}
	public void setOpennum(String opennum) {
		this.opennum = opennum;
	}
	public String getAllQty() {
		return allQty;
	}
	public void setAllQty(String allQty) {
		this.allQty = allQty;
	}
	@Override
	public String toString() {
		return "AgentDetailEntity [types=" + types + ", is_have_profit="
				+ is_have_profit + ", company_name=" + company_name
				+ ", business_license=" + business_license
				+ ", tax_registered_no=" + tax_registered_no + ", name=" + name
				+ ", card_id=" + card_id + ", phone=" + phone + ", email="
				+ email + ", cityId=" + cityId + ", address=" + address
				+ ", cardpath=" + cardpath + ", licensepath=" + licensepath
				+ ", taxpath=" + taxpath + ", loginId=" + loginId
				+ ", created_at=" + created_at + ", soldnum=" + soldnum
				+ ", opennum=" + opennum + ", allQty=" + allQty + "]";
	}
	
}
