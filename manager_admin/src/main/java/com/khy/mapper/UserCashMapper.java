package com.khy.mapper;

import java.util.List;

import com.khy.entity.UserCash;
import com.khy.mapper.dto.UserCommonDTO;

public interface UserCashMapper {
	
	List<UserCash> listUserCash(UserCommonDTO dto);

	UserCash get(UserCommonDTO dto);

	int update(UserCash cash);

	List<UserCash> listUserCashByUid(String uid);
	
}