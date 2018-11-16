package com.khy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.khy.mapper.dto.BillInfoDTO;

public class UserBill implements Serializable {
    private Long id;

    private String uid;

    /**
     * 1:标识收入2:支出
     */
    private Integer type;

    /**
     * 1:标识vip 购买 2:标识稻粒购买 3:标识话费充值 4:标识购物内容/5 用户提现
     */
    private Integer billType;

    private String orderId;

    private BigDecimal amount;

    private Float discount;
    
    private BigDecimal postage;
    
    private String description;

    private String info;

    private Date createTime;
    
    private String phone;
    
    private List<BillInfoDTO>bills;
    

    //1:标识稻粒 2:标识余额(针对稻粒)  3标识 支付宝 4标识微信
    private Integer payType;
    
    public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public List<BillInfoDTO> getBills() {
		return bills;
	}
	public void setBills(List<BillInfoDTO> bills) {
		this.bills = bills;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }
    public Float getDiscount() {
		return discount;
	}

	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
        if(StringUtils.isNotBlank(info)){
        	this.bills = JSONArray.parseArray(info, BillInfoDTO.class);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", type=").append(type);
        sb.append(", billType=").append(billType);
        sb.append(", orderId=").append(orderId);
        sb.append(", amount=").append(amount);
        sb.append(", createTime=").append(createTime);
        sb.append(", description=").append(description);
        sb.append(", info=").append(info);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}