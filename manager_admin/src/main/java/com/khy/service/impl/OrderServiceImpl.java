package com.khy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.entity.OrderInfo;
import com.khy.mapper.OrderInfoMapper;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;
import com.khy.mapper.dto.UserOrderProductDTO;
import com.khy.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Override
	public PageInfo<UserOrderInfoDTO> page(UserCommonDTO dto) {
		if(null == dto){
			return null;
		}
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		List<OrderInfo> list = orderInfoMapper.list(dto);
		PageInfo<OrderInfo>pageOrderInfo = new PageInfo<OrderInfo>(list);
		List<UserOrderInfoDTO>ret = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			UserOrderInfoDTO orderDto = null;
			for (OrderInfo orderInfo : list) {
				orderDto = new UserOrderInfoDTO();
				BeanUtils.copyProperties(orderInfo, orderDto);
				String productDetail = orderInfo.getProductDetail();
				if(StringUtils.isNotBlank(productDetail)){
					List<UserOrderProductDTO> products = JSONArray.parseArray(productDetail, UserOrderProductDTO.class);
					orderDto.setProducts(products);
				}
				ret.add(orderDto);
			}
		}
		PageInfo <UserOrderInfoDTO>pageInfo = new PageInfo<UserOrderInfoDTO>();
		BeanUtils.copyProperties(pageOrderInfo, pageInfo);
		pageInfo.setList(ret);
		return pageInfo;
	}

	@Override
	public JSONObject countOrderMoney(UserCommonDTO dto) {
		if(null == dto){
			return null;
		}
		return orderInfoMapper.countOrderMoney(dto);
	}

	@Override
	public UserOrderInfoDTO getEntityById(UserCommonDTO dto) {
		UserOrderInfoDTO ret = new UserOrderInfoDTO();
		OrderInfo info = orderInfoMapper.getEntityById(dto);
		if(null != info){
			BeanUtils.copyProperties(info, ret);
			String productDetail = info.getProductDetail();
			if(StringUtils.isNotBlank(productDetail)){
				List<UserOrderProductDTO> products = JSONArray.parseArray(productDetail, UserOrderProductDTO.class);
				ret.setProducts(products);
			}
		}
		return ret;
	}

	@Override
	public JSONObject deleteOrderByOrderId(UserCommonDTO dto) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		json.put("msg","操作失败");
		if(null == dto){
			json.put("msg","参数不能为空");
			return json;
		}
		int flag = orderInfoMapper.deleteOrderByOrderId(dto.getOrderId());
		if(flag > 0){
			json.put("code",1000);
			json.put("msg","删除订单操作成功");
		}
		return json;
	}

	@Override
	public JSONObject isSend(UserCommonDTO dto) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		json.put("msg","操作失败");
		if(null == dto){
			json.put("msg","参数不能为空");
			return json;
		}
		int flag = orderInfoMapper.isSend(dto);
		if(flag > 0){
			json.put("code",1000);
			json.put("msg",dto.getIsSend()==2?"设置订单为已发货操作成功":"设置订单为未发货操作成功");
		}
		return json;
	}
}
