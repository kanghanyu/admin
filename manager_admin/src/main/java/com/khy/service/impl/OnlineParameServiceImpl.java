package com.khy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.config.RedisUtils;
import com.khy.entity.OnlineParame;
import com.khy.mapper.OnlineParameMapper;
import com.khy.service.OnlineParameService;
import com.khy.utils.Constants;

@Service
@Transactional
public class OnlineParameServiceImpl implements OnlineParameService {

	@Autowired
	private OnlineParameMapper onlineParameMapper;
	@Autowired
	private RedisUtils RedisUtils;

	@Override
	public PageInfo<OnlineParame> page(OnlineParame onlineParame) {
		PageHelper.startPage(onlineParame.getPageNum(), onlineParame.getPageSize());
		List<OnlineParame> list = onlineParameMapper.list(onlineParame);
		PageInfo <OnlineParame>pageInfo = new PageInfo<OnlineParame>(list);
		return pageInfo;
	}

	@Override
	public JSONObject saveOnlineParame(OnlineParame onlineParame) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == onlineParame){
			json.put("msg", "参数不能为空");	
			return json;
		}
		String title = onlineParame.getTitle();
		OnlineParame db = onlineParameMapper.findByKey(title);
		if(null != db){
			json.put("msg", "当前记录已存在");	
			return json;
		}
		onlineParame.setCreateTime(new Date());
		int flag = onlineParameMapper.insert(onlineParame);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","保存成功");
			RedisUtils.KEYS.del(Constants.ONLINE_PARARME);
		}
		return json;
	}

	@Override
	public JSONObject updateOnlineParame(OnlineParame onlineParame) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == onlineParame){
			json.put("msg", "参数不能为空");	
			return json;
		}
		OnlineParame db = onlineParameMapper.findByKey(onlineParame.getTitle());
		if(null == db){
			json.put("msg", "当前记录不存在");	
			return json;
		}
		int flag = onlineParameMapper.update(onlineParame);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","修改成功");
			RedisUtils.KEYS.del(Constants.ONLINE_PARARME);
		}
		return json;
	}

	@Override
	public JSONObject deleteOnlineParame(OnlineParame onlineParame) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == onlineParame){
			json.put("msg", "参数不能为空");	
			return json;
		}
		int flag = onlineParameMapper.delete(onlineParame.getTitle());
		if(flag>0){
			json.put("code",1000);
			json.put("msg","删除成功");
			RedisUtils.KEYS.del(Constants.ONLINE_PARARME);
		}
		return json;
	}

	@Override
	public JSONObject findByKey(OnlineParame onlineParame) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == onlineParame){
			json.put("msg", "参数不能为空");	
			return json;
		}
		OnlineParame onlineParameDB = onlineParameMapper.findByKey(onlineParame.getTitle());
		if(null == onlineParameDB){
			json.put("msg", "当前记录不存在");	
			return json;
		}
		json.put("code",1000);
		json.put("entity", onlineParameDB);
		return json;
	}
}
