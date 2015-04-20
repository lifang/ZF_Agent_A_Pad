package com.comdo.zf_agent_a_pad.entity;

/**
 * Created by holin on 4/13/15.
 */
public class TerminalPriceEntity {
    private int id;
    private int retail_price;
    private String serial_num;
    private Boolean isCheck;

    public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRetail_price() {
		return retail_price;
	}

	public void setRetail_price(int retail_price) {
		this.retail_price = retail_price;
	}

	public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }
}
