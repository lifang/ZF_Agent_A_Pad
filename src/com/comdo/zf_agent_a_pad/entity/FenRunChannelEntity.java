package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class FenRunChannelEntity {

	public List<FenRunChannel> getList() {
		return list;
	}

	public void setList(List<FenRunChannel> list) {
		this.list = list;
	}

	private List<FenRunChannel> list;

	public class FenRunChannel {

		private String trade_value;

		private String name;

		private int id;

		private float percent;

		public float getPercent() {
			return percent;
		}

		public void setPercent(float percent) {
			this.percent = percent;
		}

		public String getTrade_value() {
			return trade_value;
		}

		public void setTrade_value(String trade_value) {
			this.trade_value = trade_value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

}
