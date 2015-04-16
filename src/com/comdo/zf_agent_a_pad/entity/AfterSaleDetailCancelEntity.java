package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class AfterSaleDetailCancelEntity {
	private String id;
	private String terminal_num;
	private String apply_time;
	private String merchant_phone;
	private String brand_number;
	private String status;
	private String merchant_name;
	private String zhifu_pingtai;
	private String brand_name;
	
	private Comments comments;

	private List<ResourcesInfo> resource_info;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getMerchant_phone() {
		return merchant_phone;
	}
	public void setMerchant_phone(String merchant_phone) {
		this.merchant_phone = merchant_phone;
	}
	public String getBrand_number() {
		return brand_number;
	}
	public void setBrand_number(String brand_number) {
		this.brand_number = brand_number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getZhifu_pingtai() {
		return zhifu_pingtai;
	}
	public void setZhifu_pingtai(String zhifu_pingtai) {
		this.zhifu_pingtai = zhifu_pingtai;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public Comments getComments() {
		return comments;
	}
	public void setComments(Comments comments) {
		this.comments = comments;
	}
	public List<ResourcesInfo> getResource_info() {
		return resource_info;
	}
	public void setResource_info(List<ResourcesInfo> resource_info) {
		this.resource_info = resource_info;
	}
	
	public class Comments {
		private String total;
		private List<MarkEntity> list;
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public List<MarkEntity> getList() {
			return list;
		}
		public void setList(List<MarkEntity> list) {
			this.list = list;
		}
	}
	
	public class ResourcesInfo {
		private String id;
		private String title;
		private String upload_path;
		private String templet_path;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUpload_path() {
			return upload_path;
		}
		public void setUpload_path(String upload_path) {
			this.upload_path = upload_path;
		}
		public String getTemplet_path() {
			return templet_path;
		}
		public void setTemplet_path(String templet_path) {
			this.templet_path = templet_path;
		}
	}
}
