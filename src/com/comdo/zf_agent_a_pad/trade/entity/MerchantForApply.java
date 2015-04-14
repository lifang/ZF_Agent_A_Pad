package com.comdo.zf_agent_a_pad.trade.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MerchantForApply {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.id
     * 
     * @mbggenerated
     */
    private Integer id;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.credit_type_id
     * 
     * @mbggenerated
     */
    private Integer creditTypeId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.legal_person_name
     * 
     * @mbggenerated
     */
    @SerializedName("legal_person_name")
    private String legalPersonName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.legal_person_card_id
     * 
     * @mbggenerated
     */
    @SerializedName("legal_person_card_id")
    private String legalPersonCardId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.card_id_front_photo_path
     * 
     * @mbggenerated
     */
    private String cardIdFrontPhotoPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.card_id_back_photo_path
     * 
     * @mbggenerated
     */
    private String cardIdBackPhotoPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.title
     * 
     * @mbggenerated
     */
    private String title;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.business_license_no
     * 
     * @mbggenerated
     */
    private String businessLicenseNo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.tax_registered_no
     * 
     * @mbggenerated
     */
    @SerializedName("tax_registered_no")
    private String taxRegisteredNo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.organization_code_no
     * 
     * @mbggenerated
     */
    @SerializedName("organization_code_no")
    private String organizationCodeNo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.bank_open_account
     * 
     * @mbggenerated
     */
    @SerializedName("bank_open_account")
    private String bankOpenAccount;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.license_no_pic_path
     * 
     * @mbggenerated
     */
    private String licenseNoPicPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.tax_no_pic_path
     * 
     * @mbggenerated
     */
    private String taxNoPicPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.org_code_no_pic_path
     * 
     * @mbggenerated
     */
    private String orgCodeNoPicPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.account_pic_path
     * 
     * @mbggenerated
     */
    private String accountPicPath;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.account_bank_name
     * 
     * @mbggenerated
     */
    @SerializedName("account_bank_name")
    private String accountBankName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * merchants.account_bank_address
     * 
     * @mbggenerated
     */
    private String accountBankAddress;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.created_at
     * 
     * @mbggenerated
     */
    private Date createdAt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.updated_at
     * 
     * @mbggenerated
     */
    private Date updatedAt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.phone
     * 
     * @mbggenerated
     */
    private String phone;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column merchants.account_bank_num
     * 
     * @mbggenerated
     */
    @SerializedName("account_bank_num")
    private String accountBankNum;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column merchants.id
     * 
     * @return the value of merchants.id
     * @mbggenerated
     */
    @SerializedName("customer_id")
    private Integer customerId;

	@SerializedName("cityId")
    private Integer cityId;

    private String bodyPhotoPath;
    
    private Integer payChannelId;
    
    

    public Integer getPayChannelId() {
		return payChannelId;
	}

	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column merchants.id
     * 
     * @param id
     *            the value for merchants.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.credit_type_id
     * 
     * @return the value of merchants.credit_type_id
     * @mbggenerated
     */
    public Integer getCreditTypeId() {
        return creditTypeId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.credit_type_id
     * 
     * @param creditTypeId
     *            the value for merchants.credit_type_id
     * @mbggenerated
     */
    public void setCreditTypeId(Integer creditTypeId) {
        this.creditTypeId = creditTypeId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.legal_person_name
     * 
     * @return the value of merchants.legal_person_name
     * @mbggenerated
     */
    public String getLegalPersonName() {
        return legalPersonName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.legal_person_name
     * 
     * @param legalPersonName
     *            the value for merchants.legal_person_name
     * @mbggenerated
     */
    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.legal_person_card_id
     * 
     * @return the value of merchants.legal_person_card_id
     * @mbggenerated
     */
    public String getLegalPersonCardId() {
        return legalPersonCardId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.legal_person_card_id
     * 
     * @param legalPersonCardId
     *            the value for merchants.legal_person_card_id
     * @mbggenerated
     */
    public void setLegalPersonCardId(String legalPersonCardId) {
        this.legalPersonCardId = legalPersonCardId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.card_id_front_photo_path
     * 
     * @return the value of merchants.card_id_front_photo_path
     * @mbggenerated
     */
    public String getCardIdFrontPhotoPath() {
        return cardIdFrontPhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.card_id_front_photo_path
     * 
     * @param cardIdFrontPhotoPath
     *            the value for merchants.card_id_front_photo_path
     * @mbggenerated
     */
    public void setCardIdFrontPhotoPath(String cardIdFrontPhotoPath) {
        this.cardIdFrontPhotoPath = cardIdFrontPhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.card_id_back_photo_path
     * 
     * @return the value of merchants.card_id_back_photo_path
     * @mbggenerated
     */
    public String getCardIdBackPhotoPath() {
        return cardIdBackPhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.card_id_back_photo_path
     * 
     * @param cardIdBackPhotoPath
     *            the value for merchants.card_id_back_photo_path
     * @mbggenerated
     */
    public void setCardIdBackPhotoPath(String cardIdBackPhotoPath) {
        this.cardIdBackPhotoPath = cardIdBackPhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column merchants.title
     * 
     * @return the value of merchants.title
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column merchants.title
     * 
     * @param title
     *            the value for merchants.title
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.business_license_no
     * 
     * @return the value of merchants.business_license_no
     * @mbggenerated
     */
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.business_license_no
     * 
     * @param businessLicenseNo
     *            the value for merchants.business_license_no
     * @mbggenerated
     */
    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.tax_registered_no
     * 
     * @return the value of merchants.tax_registered_no
     * @mbggenerated
     */
    public String getTaxRegisteredNo() {
        return taxRegisteredNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.tax_registered_no
     * 
     * @param taxRegisteredNo
     *            the value for merchants.tax_registered_no
     * @mbggenerated
     */
    public void setTaxRegisteredNo(String taxRegisteredNo) {
        this.taxRegisteredNo = taxRegisteredNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.organization_code_no
     * 
     * @return the value of merchants.organization_code_no
     * @mbggenerated
     */
    public String getOrganizationCodeNo() {
        return organizationCodeNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.organization_code_no
     * 
     * @param organizationCodeNo
     *            the value for merchants.organization_code_no
     * @mbggenerated
     */
    public void setOrganizationCodeNo(String organizationCodeNo) {
        this.organizationCodeNo = organizationCodeNo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.bank_open_account
     * 
     * @return the value of merchants.bank_open_account
     * @mbggenerated
     */
    public String getBankOpenAccount() {
        return bankOpenAccount;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.bank_open_account
     * 
     * @param bankOpenAccount
     *            the value for merchants.bank_open_account
     * @mbggenerated
     */
    public void setBankOpenAccount(String bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.license_no_pic_path
     * 
     * @return the value of merchants.license_no_pic_path
     * @mbggenerated
     */
    public String getLicenseNoPicPath() {
        return licenseNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.license_no_pic_path
     * 
     * @param licenseNoPicPath
     *            the value for merchants.license_no_pic_path
     * @mbggenerated
     */
    public void setLicenseNoPicPath(String licenseNoPicPath) {
        this.licenseNoPicPath = licenseNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.tax_no_pic_path
     * 
     * @return the value of merchants.tax_no_pic_path
     * @mbggenerated
     */
    public String getTaxNoPicPath() {
        return taxNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.tax_no_pic_path
     * 
     * @param taxNoPicPath
     *            the value for merchants.tax_no_pic_path
     * @mbggenerated
     */
    public void setTaxNoPicPath(String taxNoPicPath) {
        this.taxNoPicPath = taxNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.org_code_no_pic_path
     * 
     * @return the value of merchants.org_code_no_pic_path
     * @mbggenerated
     */
    public String getOrgCodeNoPicPath() {
        return orgCodeNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.org_code_no_pic_path
     * 
     * @param orgCodeNoPicPath
     *            the value for merchants.org_code_no_pic_path
     * @mbggenerated
     */
    public void setOrgCodeNoPicPath(String orgCodeNoPicPath) {
        this.orgCodeNoPicPath = orgCodeNoPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.account_pic_path
     * 
     * @return the value of merchants.account_pic_path
     * @mbggenerated
     */
    public String getAccountPicPath() {
        return accountPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.account_pic_path
     * 
     * @param accountPicPath
     *            the value for merchants.account_pic_path
     * @mbggenerated
     */
    public void setAccountPicPath(String accountPicPath) {
        this.accountPicPath = accountPicPath;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.account_bank_name
     * 
     * @return the value of merchants.account_bank_name
     * @mbggenerated
     */
    public String getAccountBankName() {
        return accountBankName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.account_bank_name
     * 
     * @param accountBankName
     *            the value for merchants.account_bank_name
     * @mbggenerated
     */
    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.account_bank_address
     * 
     * @return the value of merchants.account_bank_address
     * @mbggenerated
     */
    public String getAccountBankAddress() {
        return accountBankAddress;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.account_bank_address
     * 
     * @param accountBankAddress
     *            the value for merchants.account_bank_address
     * @mbggenerated
     */
    public void setAccountBankAddress(String accountBankAddress) {
        this.accountBankAddress = accountBankAddress;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column merchants.created_at
     * 
     * @return the value of merchants.created_at
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column merchants.created_at
     * 
     * @param createdAt
     *            the value for merchants.created_at
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column merchants.updated_at
     * 
     * @return the value of merchants.updated_at
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column merchants.updated_at
     * 
     * @param updatedAt
     *            the value for merchants.updated_at
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column merchants.phone
     * 
     * @return the value of merchants.phone
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column merchants.phone
     * 
     * @param phone
     *            the value for merchants.phone
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * merchants.account_bank_num
     * 
     * @return the value of merchants.account_bank_num
     * @mbggenerated
     */
    public String getAccountBankNum() {
        return accountBankNum;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * merchants.account_bank_num
     * 
     * @param accountBankNum
     *            the value for merchants.account_bank_num
     * @mbggenerated
     */
    public void setAccountBankNum(String accountBankNum) {
        this.accountBankNum = accountBankNum;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the bodyPhotoPath
     */
    public String getBodyPhotoPath() {
        return bodyPhotoPath;
    }

    /**
     * @param bodyPhotoPath
     *            the bodyPhotoPath to set
     */
    public void setBodyPhotoPath(String bodyPhotoPath) {
        this.bodyPhotoPath = bodyPhotoPath;
    }

}