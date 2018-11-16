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
import com.khy.entity.Message;
import com.khy.entity.OnlineParame;
import com.khy.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/toMessageList")
	public ModelAndView toMessageList(Message message){
		ModelAndView model = new ModelAndView("message/list");
		PageInfo<Message> page =  messageService.page(message);
		model.addObject("page", page);
		return model;
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public String dateList(@RequestBody Message message){
		PageInfo<Message> page =  messageService.page(message);
		return JSON.toJSONString(page);
	}
	
	@RequestMapping("/saveMessage")
	@ResponseBody
	public String saveMessage(@RequestBody Message message){
		JSONObject json = messageService.saveOnlineParame(message);
		return json.toString();
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(@RequestBody Message message){
		JSONObject json =  messageService.findById(message);
		return json.toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestBody Message message){
		JSONObject json = messageService.update(message);
		return json.toString();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody Message message){
		JSONObject json = messageService.delete(message);
		return json.toString();
	}
	
}

