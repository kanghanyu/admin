package com.khy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserPhoneRecord {
	
	private BigDecimal totalMoney;
	
	private BigDecimal discountMoney;

	private Date createTime;
	
	private String phone;
	
	private String status;

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getPhone() {
		return phone;
	}

	public String getStatus() {
		return status;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
