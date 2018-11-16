package com.khy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;

public interface OrderService {

	PageInfo<UserOrderInfoDTO> page(UserCommonDTO dto);

	JSONObject countOrderMoney(UserCommonDTO dto);

	UserOrderInfoDTO getEntityById(UserCommonDTO dto);

	JSONObject deleteOrderByOrderId(UserCommonDTO dto);

	JSONObject isSend(UserCommonDTO dto);

}
