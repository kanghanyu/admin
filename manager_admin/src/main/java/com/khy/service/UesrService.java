package com.khy.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.User;
import com.khy.entity.UserBillDTO;
import com.khy.entity.UserCash;
import com.khy.entity.UserPhoneRecord;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserCountDTO;
import com.khy.mapper.dto.UserInviterDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;
import com.khy.mapper.dto.UserRecordDTO;

public interface UesrService {

	User login(User user);

	JSONObject register(User user,HttpSession session);

	PageInfo<User> page(User user);

	JSONObject resetPassword(User user);

	JSONObject setUserStatus(User user);

	String getUserInfo(User user);

	PageInfo<UserCash> pageUserCash(UserCommonDTO dto);

	JSONObject auditUserCash(UserCommonDTO dto);

	List<UserCash> listUserCash(UserCommonDTO dto);

	List<UserInviterDTO> listUserInviter(UserCommonDTO dto);

	List<UserRecordDTO> listUserRecord(UserCommonDTO dto);

	List<UserBillDTO> listUserBill(UserCommonDTO dto);

	List<UserPhoneRecord> listUserPhoneRecord(UserCommonDTO dto);

	List<UserOrderInfoDTO> listUserOrderInfo(UserCommonDTO dto);

	UserCountDTO getUserCount(User user);


}
