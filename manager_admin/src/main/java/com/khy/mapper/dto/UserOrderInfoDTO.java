package com.khy.mapper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserOrderInfoDTO implements Serializable{

	private static final long serialVersionUID = -3452886345205097080L;
	private String amountPhone;
	/**订单id*/
	private String orderId;

	/**1:标识稻粒 2标识 支付宝 3标识微信*/
	private Integer payType;
	
	/**uid*/
	private String uid;
	
	/**折扣前的商品总金额*/
	private BigDecimal totalMoney;
	
	/**应付款  商品的折扣前总金额+邮费*/
	private BigDecimal totalPayable;
	
	/**折扣*/
	private Float discount;
	
	/**折扣后的金额*/
	private BigDecimal discountMoney;
	
	/**总的付款金额*/
	private BigDecimal totalPay;
	
	/**付款的rmb/稻粒金额*/
	private BigDecimal rmb;
	
	/**余额抵扣的金额*/
	private BigDecimal cornMoney;
	
	/**详情商品的内容*/
	private List<UserOrderProductDTO>products;
	
	/**1订单未完成 2:订单完成*/
	private Integer status;
	
	/**付款状态 1未付款,2已付款 3:已取消 4: 佣金已经返还*/
	private Integer payStatus;
	
	/**订单创建时间*/
	private Date createTime;
	
	/**订单的付款时间*/
	private Date payTime;
	
	/**邮费*/
	private BigDecimal postage;
	
	/**收件人姓名*/
	private String userName;

	/**收件人地址*/
	private String address;

	/**收件人邮编*/
	private String postCode;

	/**收件人手机号*/
	private String phone;

	/**描述内容*/
	private String description;
	
	private String statusStr;

	private Integer isSend;
	
	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}
	public BigDecimal getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(BigDecimal totalPayable) {
		this.totalPayable = totalPayable;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public String getAmountPhone() {
		return amountPhone;
	}

	public void setAmountPhone(String amountPhone) {
		this.amountPhone = amountPhone;
	}

	public void setStatus(Integer status) {
		this.status = status;
		if(null != status && null != payStatus){
			this.statusStr = getStrStatus();
		}
	}

	private String getStrStatus() {
		String ret="无效订单";
		if(status == 1 && payStatus == 1){
			ret="未付款";
		}else if(status == 1 && payStatus == 2){
			ret="已付款";
		}else if(status == 2 && (payStatus == 2 || payStatus == 4)){
			ret="已完成";
		}else if(status == 2 && payStatus == 3){
			ret="已取消";
		}
		
		return ret;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
		if(null != status && null != payStatus){
			this.statusStr = getStrStatus();
		}
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getOrderId() {
		return orderId;
	}

	public Integer getPayType() {
		return payType;
	}

	public String getUid() {
		return uid;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public Float getDiscount() {
		return discount;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public BigDecimal getTotalPay() {
		return totalPay;
	}

	public BigDecimal getRmb() {
		return rmb;
	}

	public BigDecimal getCornMoney() {
		return cornMoney;
	}

	public List<UserOrderProductDTO> getProducts() {
		return products;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public String getUserName() {
		return userName;
	}

	public String getAddress() {
		return address;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getDescription() {
		return description;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
		if(null != totalMoney && null != postage){
			this.totalPayable = totalMoney.add(postage);
		}
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public void setTotalPay(BigDecimal totalPay) {
		this.totalPay = totalPay;
	}

	public void setRmb(BigDecimal rmb) {
		this.rmb = rmb;
	}

	public void setCornMoney(BigDecimal cornMoney) {
		this.cornMoney = cornMoney;
	}

	public void setProducts(List<UserOrderProductDTO> products) {
		this.products = products;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
		if(null != totalMoney && null != postage){
			this.totalPayable = totalMoney.add(postage);
		}
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
