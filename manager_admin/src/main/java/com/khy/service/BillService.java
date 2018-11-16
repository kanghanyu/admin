package com.khy.service;

import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;
import com.khy.entity.UserBill;
import com.khy.mapper.dto.UserCommonDTO;

public interface BillService {

	PageInfo<UserBill> page(UserCommonDTO dto);

	BigDecimal sumAmount(UserCommonDTO dto);

	UserBill getEntityById(UserCommonDTO dto);

}
