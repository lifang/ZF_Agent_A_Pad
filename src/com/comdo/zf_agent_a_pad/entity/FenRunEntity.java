package com.comdo.zf_agent_a_pad.entity;

import java.util.List;

public class FenRunEntity {

	private List<FenRun> list;

	public List<FenRun> getList() {
		return list;
	}

	public void setList(List<FenRun> list) {
		this.list = list;
	}

	public class FenRun {

		private String channelName;
		private String id;
		private int nums;
		private List<Detail> detail;

		public class Detail {

			private String tradeTypeName;
			private String tradeTypeId;
			private float percent;

			public String getTradeTypeName() {
				return tradeTypeName;
			}

			public void setTradeTypeName(String tradeTypeName) {
				this.tradeTypeName = tradeTypeName;
			}

			public String getTradeTypeId() {
				return tradeTypeId;
			}

			public void setTradeTypeId(String tradeTypeId) {
				this.tradeTypeId = tradeTypeId;
			}

			public float getPercent() {
				return percent;
			}

			public void setPercent(float percent) {
				this.percent = percent;
			}

		}

		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public String getId() {
			return id;
		}

		public void setId(String Id) {
			this.id = Id;
		}

		public int getNums() {
			return nums;
		}

		public void setNums(int nums) {
			this.nums = nums;
		}

		public List<Detail> getDetail() {
			return detail;
		}

		public void setDetail(List<Detail> detail) {
			this.detail = detail;
		}

	}
}
