package com.khy.mapper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserOrderProductDTO implements Serializable{

	private static final long serialVersionUID = -4331684172228740185L;
	/**商品的id*/
	private Long productId;

	/**商品名称*/
	private String productName;

	/**价格*/
	private BigDecimal productPrice;
	
	/**缩略图*/
	private String img;
	
	/**购买数量*/
	private Integer amount;
	
	/**该商品总金额*/
	private Double total;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
