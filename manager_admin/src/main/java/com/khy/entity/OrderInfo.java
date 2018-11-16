package com.khy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo extends BaseEntity implements Serializable {
	private String amountPhone;
	
	private Long id;

	private String orderId;

	/** 1:标识vip 购买 2:标识稻粒购买 3:标识话费充值 4:标识购物内容 */
	private Integer orderType;

	/** 1:标识稻粒 2标识 支付宝 3标识微信 */
	private Integer payType;

	private String uid;
	/** 总金额 */
	private BigDecimal totalMoney;

	private Float discount;

	private String discountDetail;

	private BigDecimal discountMoney;
	/**总的付款金额*/
	private BigDecimal totalPay;
	/**其中微信/支付宝/稻粒的金额*/
	private BigDecimal rmb;
	/**余额抵扣的钱*/
	private BigDecimal cornMoney;
	
	/**总的成本价格*/
	private BigDecimal totalCost;
	
	private String productDetail;

	/**订单状态 1订单未完成 2:订单完成 */
	private Integer status;
	
	/**付款状态 1未付款,2已付款 3:已取消 4: 佣金已经返还 */
	private Integer payStatus;

	private Date createTime;

	private Date payTime;

	/**失效时间创建时间30分钟之后*/
	private Date invalidTime;

	// 邮费
	private BigDecimal postage;

	private String userName;

	private String address;

	private String postCode;

	private String phone;
	private String description;
	private String tradeNo;
	private Integer isBill;
	// 1:未发货 2:已发货
	private Integer isSend;
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public String getAmountPhone() {
		return amountPhone;
	}

	public void setAmountPhone(String amountPhone) {
		this.amountPhone = amountPhone;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public Integer getIsBill() {
		return isBill;
	}

	public void setIsBill(Integer isBill) {
		this.isBill = isBill;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public BigDecimal getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(BigDecimal totalPay) {
		this.totalPay = totalPay;
	}

	public BigDecimal getRmb() {
		return rmb;
	}

	public void setRmb(BigDecimal rmb) {
		this.rmb = rmb;
	}

	public BigDecimal getCornMoney() {
		return cornMoney;
	}

	public void setCornMoney(BigDecimal cornMoney) {
		this.cornMoney = cornMoney;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid == null ? null : uid.trim();
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getDiscountDetail() {
		return discountDetail;
	}

	public void setDiscountDetail(String discountDetail) {
		this.discountDetail = discountDetail == null ? null : discountDetail.trim();
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}