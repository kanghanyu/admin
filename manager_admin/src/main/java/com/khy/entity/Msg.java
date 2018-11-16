package com.khy.entity;

import java.io.Serializable;

public class Msg implements Serializable{

	private static final long serialVersionUID = 8923975161697696013L;

	/**手机号*/
	private String phone;
	
	/**1注册 2找回手机密码*/
	private Integer type;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
