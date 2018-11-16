package com.khy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Message;
import com.khy.entity.OnlineParame;
import com.khy.entity.Product;
import com.khy.mapper.MessageMapper;
import com.khy.service.MessageService;
import com.khy.utils.Constants;
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageMapper;
	@Override
	public PageInfo<Message> page(Message message) {
		PageHelper.startPage(message.getPageNum(), message.getPageSize());
		List<Message> list = messageMapper.list(message);
		PageInfo <Message>pageInfo = new PageInfo<Message>(list);
		return pageInfo;
	}
	@Override
	public JSONObject saveOnlineParame(Message message) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == message){
			json.put("msg", "参数不能为空");	
			return json;
		}
		message.setCreateTime(new Date());
		int flag = messageMapper.insert(message);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","保存成功");
		}
		return json;
	}
	@Override
	public JSONObject findById(Message message) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == message){
			json.put("msg", "参数不能为空");	
			return json;
		}
		Message messageDB = messageMapper.findByKey(message.getId());
		if(null == messageDB){
			json.put("msg", "当前记录不存在");	
			return json;
		}
		json.put("code",1000);
		json.put("entity", messageDB);
		return json;
	}
	@Override
	public JSONObject update(Message message) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == message){
			json.put("msg", "参数不能为空");	
			return json;
		}
		Message db = messageMapper.findByKey(message.getId());
		if(null == db){
			json.put("msg", "当前记录不存在");	
			return json;
		}
		int flag = messageMapper.update(message);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","修改成功");
		}
		return json;
	}
	@Override
	public JSONObject delete(Message message) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == message){
			json.put("msg", "参数不能为空");	
			return json;
		}
		int flag = messageMapper.delete(message.getId());
		if(flag>0){
			json.put("code",1000);
			json.put("msg","删除成功");
		}
		return json;
	}

}
