package com.khy.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.khy.entity.OrderInfo;
import com.khy.mapper.dto.UserCommonDTO;

public interface OrderInfoMapper {
	List<OrderInfo> getUserBill(String uid);

	List<OrderInfo> listOrderInfo(OrderInfo info);

	List<OrderInfo> list(UserCommonDTO dto);

	JSONObject countOrderMoney(UserCommonDTO dto);

	OrderInfo getEntityById(UserCommonDTO dto);

	int deleteOrderByOrderId(String orderId);

	int isSend(UserCommonDTO dto);
}