package com.khy.mapper.dto;

import java.math.BigDecimal;

public class BillInfoDTO {

	/**商品的名称*/
	private String productName;
	/**商品的类别*/
	private String productType;
	/**商品的价格*/
	private BigDecimal price;
	/**商品的数量*/
	private Integer amount;
	/**商品的总价*/
	private BigDecimal total;
	/**描述信息*/
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductType() {
		return productType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public Integer getAmount() {
		return amount;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
