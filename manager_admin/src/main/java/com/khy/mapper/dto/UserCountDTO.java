package com.khy.mapper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserCountDTO implements Serializable{

	private static final long serialVersionUID = 7724202794527412764L;
	private String uid;
	private BigDecimal totalMoney;
	private BigDecimal totalCardMoney;
	private BigDecimal totalCommission;
	private Integer vipNum;
	private Integer amount;
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getUid() {
		return uid;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public BigDecimal getTotalCardMoney() {
		return totalCardMoney;
	}
	public BigDecimal getTotalCommission() {
		return totalCommission;
	}
	public Integer getVipNum() {
		return vipNum;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public void setTotalCardMoney(BigDecimal totalCardMoney) {
		this.totalCardMoney = totalCardMoney;
	}
	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}
	public void setVipNum(Integer vipNum) {
		this.vipNum = vipNum;
	}
	
}
