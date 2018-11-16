package com.khy.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khy.entity.UserBill;
import com.khy.mapper.dto.UserCommonDTO;

public interface UserBillMapper {
	List<UserBill> list(UserCommonDTO dto);

	void insert(UserBill bill);

	BigDecimal sumAmount(UserCommonDTO dto);

	UserBill getEntityById(UserCommonDTO dto);

}