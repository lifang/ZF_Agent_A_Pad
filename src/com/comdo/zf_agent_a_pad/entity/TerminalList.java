package com.comdo.zf_agent_a_pad.entity;

public class TerminalList {
private int id;
private int money;
private String serial_num;
private boolean isCheck;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getMoney() {
	return money;
}
public void setMoney(int money) {
	this.money = money;
}
public String getSerial_num() {
	return serial_num;
}
public void setSerial_num(String serial_num) {
	this.serial_num = serial_num;
}
public boolean isCheck() {
	return isCheck;
}
public void setCheck(boolean isCheck) {
	this.isCheck = isCheck;
}
@Override
public String toString() {
	return "TerminalList [id=" + id + ", money=" + money + ", serial_num="
			+ serial_num + ", isCheck=" + isCheck + "]";
}

}
