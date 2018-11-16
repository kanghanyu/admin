package com.khy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Message;

public interface MessageService {

	PageInfo<Message> page(Message message);

	JSONObject saveOnlineParame(Message message);

	JSONObject findById(Message message);

	JSONObject update(Message message);

	JSONObject delete(Message message);

}
