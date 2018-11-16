package com.khy.mapper;

import java.util.List;

import com.khy.entity.OnlineParame;

public interface OnlineParameMapper {
	List<OnlineParame> list(OnlineParame onlineParame);
	
	OnlineParame findByKey(String title);
	
	int insert(OnlineParame onlineParame);
	
	int update(OnlineParame onlineParame);
	
    int delete(String key);
}