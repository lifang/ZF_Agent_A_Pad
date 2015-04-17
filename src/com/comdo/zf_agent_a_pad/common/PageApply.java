package com.comdo.zf_agent_a_pad.common;

import java.util.List;

public class PageApply<T> {

	private int total;

	private List<T> applyList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return applyList;
	}

	public void setList(List<T> list) {
		this.applyList = list;
	}
}
