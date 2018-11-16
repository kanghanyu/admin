package com.khy.mapper.dto;

import java.io.Serializable;

import com.khy.entity.BaseEntity;

public class UserCommonDTO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -1608424265164756308L;
	private Long id;
	private String uid;
	private String phone;
	private Integer status;
	private Integer type;
	private Integer billType;
	private String orderId;
	private Integer isSend;
	
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUid() {
		return uid;
	}
	public String getPhone() {
		return phone;
	}
	
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
