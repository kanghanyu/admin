package com.khy.mapper;

import com.khy.entity.UserAddress;

public interface UserAddressMapper {
	UserAddress getByUid(String uid);
}