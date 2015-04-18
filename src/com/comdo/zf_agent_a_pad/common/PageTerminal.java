package com.comdo.zf_agent_a_pad.common;

import java.util.List;

public class PageTerminal<T> {

	private int total;

	private List<T> terminalList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return terminalList;
	}

	public void setList(List<T> list) {
		this.terminalList = list;
	}
}
