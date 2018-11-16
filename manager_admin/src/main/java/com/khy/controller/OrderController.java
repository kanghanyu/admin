package com.khy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.OnlineParame;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;
import com.khy.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@RequestMapping("/toOrderList")
	public ModelAndView toOnline(UserCommonDTO dto){
		ModelAndView model = new ModelAndView("order/list");
		PageInfo<UserOrderInfoDTO> page =  orderService.page(dto);
		JSONObject json = orderService.countOrderMoney(dto);
		model.addObject("page", page);
		model.addObject("count", json);
		return model;
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public String dateList(@RequestBody UserCommonDTO dto){
		JSONObject ret = new JSONObject();
		PageInfo<UserOrderInfoDTO> page =  orderService.page(dto);
		JSONObject json = orderService.countOrderMoney(dto);
		ret.put("page", page);
		ret.put("count", json);
		return ret.toString();
	}
	@RequestMapping("/getEntityById")
	@ResponseBody
	public String getEntityById(@RequestBody UserCommonDTO dto){
		JSONObject ret = new JSONObject();
		ret.put("code",2000);
		UserOrderInfoDTO order =  orderService.getEntityById(dto);
		if(null != order){
			ret.put("code",1000);
		}
		ret.put("order", order);
		return ret.toString();
	}
	
	@RequestMapping("/deleteByOrderId")
	@ResponseBody
	public String deleteByOrderId(@RequestBody UserCommonDTO dto){
		JSONObject ret = new JSONObject();
		ret.put("code",2000);
		JSONObject json = orderService.deleteOrderByOrderId(dto);
		return json.toString();
	}
	
	@RequestMapping("/isSend")
	@ResponseBody
	public String isSend(@RequestBody UserCommonDTO dto){
		JSONObject ret = new JSONObject();
		ret.put("code",2000);
		JSONObject json = orderService.isSend(dto);
		return json.toString();
	}
	
}

