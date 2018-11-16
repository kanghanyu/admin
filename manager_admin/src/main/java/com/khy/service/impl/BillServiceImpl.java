package com.khy.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.entity.UserBill;
import com.khy.mapper.UserBillMapper;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.service.BillService;
@Service
@Transactional
public class BillServiceImpl implements BillService {

	@Autowired
	private UserBillMapper userBillMapper;

	@Override
	public PageInfo<UserBill> page(UserCommonDTO dto) {
		if(null == dto){
			return null;
		}
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		List<UserBill> list = userBillMapper.list(dto);
		PageInfo <UserBill>pageInfo = new PageInfo<UserBill>(list);
		return pageInfo;
	}

	@Override
	public BigDecimal sumAmount(UserCommonDTO dto) {
		return userBillMapper.sumAmount(dto);
	}

	@Override
	public UserBill getEntityById(UserCommonDTO dto) {
		return userBillMapper.getEntityById(dto);
	}
}
