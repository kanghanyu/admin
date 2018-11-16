package com.khy.mapper;

import java.util.List;

import com.khy.entity.Message;

public interface MessageMapper {

	List<Message> list(Message message);

	int insert(Message message);

	Message findByKey(Long id);

	int update(Message message);

	int delete(Long id);

}
