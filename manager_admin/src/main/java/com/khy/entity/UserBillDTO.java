package com.khy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserBillDTO implements Serializable{

	private static final long serialVersionUID = 5812596389552522123L;

	private String uid;
	
	private Date time;
	
	private String description;
	
	private BigDecimal amount;
	private String totalDesc;

	public String getUid() {
		return uid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTotalDesc() {
		return totalDesc;
	}

	public void setTotalDesc(String totalDesc) {
		this.totalDesc = totalDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
