package com.khy.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.khy.entity.User;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserCountDTO;
import com.khy.mapper.dto.UserInviterDTO;
import com.khy.mapper.dto.UserRecordDTO;

public interface UserMapper {

	User getUserByPhone(String phone);

	int insert(User user);

	List<User> list(User user);

	int updateUser(User userDb);

	User getUserByUid(String uid);

	List<UserInviterDTO> listUserInviter(UserCommonDTO dto);

	List<UserRecordDTO> listUserRecord(JSONObject param);

	UserCountDTO getUserCount(User user);

}
