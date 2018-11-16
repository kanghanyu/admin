package com.khy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.OnlineParame;

public interface OnlineParameService {

	PageInfo<OnlineParame> page(OnlineParame onlineParame);

	JSONObject saveOnlineParame(OnlineParame onlineParame);

	JSONObject updateOnlineParame(OnlineParame onlineParame);

	JSONObject deleteOnlineParame(OnlineParame onlineParame);

	JSONObject findByKey(OnlineParame onlineParame);

}
