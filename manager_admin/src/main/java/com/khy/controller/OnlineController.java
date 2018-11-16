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
import com.khy.service.OnlineParameService;

@Controller
@RequestMapping("/online")
public class OnlineController {
	
	@Autowired
	private OnlineParameService onlineParameService;
	@RequestMapping("/toOnline")
	public ModelAndView toOnline(OnlineParame onlineParame){
		ModelAndView model = new ModelAndView("online/list");
		PageInfo<OnlineParame> page =  onlineParameService.page(onlineParame);
		model.addObject("page", page);
		return model;
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public String dateList(@RequestBody OnlineParame onlineParame){
		PageInfo<OnlineParame> page =  onlineParameService.page(onlineParame);
		return JSON.toJSONString(page);
	}
	@RequestMapping("/findByKey")
	@ResponseBody
	public String findByKey(@RequestBody OnlineParame onlineParame){
		JSONObject json =  onlineParameService.findByKey(onlineParame);
		return json.toString();
	}
	
	@RequestMapping("/saveOnlineParame")
	@ResponseBody
	public String saveOnlineParame(@RequestBody OnlineParame onlineParame){
		JSONObject json = onlineParameService.saveOnlineParame(onlineParame);
		return json.toString();
	}
	
	@RequestMapping("/updateOnlineParame")
	@ResponseBody
	public String updateOnlineParame(@RequestBody OnlineParame onlineParame){
		JSONObject json = onlineParameService.updateOnlineParame(onlineParame);
		return json.toString();
	}
	
	@RequestMapping("/deleteOnlineParame")
	@ResponseBody
	public String deleteOnlineParame(@RequestBody OnlineParame onlineParame){
		JSONObject json = onlineParameService.deleteOnlineParame(onlineParame);
		return json.toString();
	}
	
	
}

