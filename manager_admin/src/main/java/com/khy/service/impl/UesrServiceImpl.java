package com.khy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.config.RedisUtils;
import com.khy.entity.OrderInfo;
import com.khy.entity.User;
import com.khy.entity.UserAddress;
import com.khy.entity.UserBank;
import com.khy.entity.UserBill;
import com.khy.entity.UserBillDTO;
import com.khy.entity.UserCash;
import com.khy.entity.UserPhoneRecord;
import com.khy.mapper.OrderInfoMapper;
import com.khy.mapper.UserAddressMapper;
import com.khy.mapper.UserBankMapper;
import com.khy.mapper.UserBillMapper;
import com.khy.mapper.UserCashMapper;
import com.khy.mapper.UserMapper;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserCountDTO;
import com.khy.mapper.dto.UserInviterDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;
import com.khy.mapper.dto.UserOrderProductDTO;
import com.khy.mapper.dto.UserRecordDTO;
import com.khy.service.UesrService;
import com.khy.utils.Constants;
import com.khy.utils.SessionHolder;
import com.khy.utils.Utils;
@Service
@Transactional
public class UesrServiceImpl implements UesrService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserBankMapper userBankMapper;
	@Autowired
	private UserAddressMapper userAddressMapper;
	@Autowired
	private UserCashMapper userCashMapper;
	@Autowired
	private UserBillMapper userBillMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private RedisUtils RedisUtils;
	@Override
	public User login(User user) {
		if(null == user){
			return null;
		}
		String phone = user.getPhone();
		String password = user.getPassword();
		if(StringUtils.isBlank(phone) || StringUtils.isBlank(password)){
			return null;
		}
		User userDb = userMapper.getUserByPhone(phone);
		if(null == userDb || !userDb.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return null;
		}
		userDb.hide(userDb);
		return userDb;
	}

	@Override
	public JSONObject register(User user, HttpSession session) {
 		JSONObject json = new JSONObject();
		json.put("code", 2000);
		String msg = null;
		if(null == user){
			msg = "参数为空";
			json.put("msg",msg);
			return json;
		}
		String key = Constants.ADMIN_SMS_REGISTER+user.getPhone();
		String redisCode = RedisUtils.STRINGS.get(key);
		if(StringUtils.isBlank(redisCode)){
			json.put("msg","验证码已失效");
			return json;
		}
		if(!user.getCode().equals(redisCode)){
			json.put("msg","验证码错误");
			return json;
		}
		
		if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getConfirmPassword())){
			msg = "密码不能为空";
			json.put("msg",msg);
			return json;
		}
		if(!user.getPassword().equals(user.getConfirmPassword())){
			msg = "两次密码不一致";
			json.put("msg",msg);
			return json;
		}
		User userDb = userMapper.getUserByPhone(user.getPhone());
		if(null != userDb){
			msg = "手机号已注册";
			json.put("msg",msg);
			return json;
		}
		if(StringUtils.isBlank(msg)){
			user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			user.setIsManager(0);
			user.setIsVip(0);
			user.setStatus(0);
			user.setUid(Utils.getUid());
			user.setMoney(new BigDecimal("0.00"));
			int flag = userMapper.insert(user);
			if(flag >= 1){
				userDb = userMapper.getUserByPhone(user.getPhone());
				userDb.hide(userDb);
				session.setAttribute("loginUser", userDb);
				SessionHolder.setUser(userDb);
				json.put("code",1000);
				json.put("msg","恭喜您注册成功啦");
				RedisUtils.KEYS.del(key);
			}
		}
		return json;
	}

	/**
	 * 分页查询用户信息
	 */
	@Override
	public PageInfo<User> page(User user) {
		PageHelper.startPage(user.getPageNum(), user.getPageSize());
		List<User> list = userMapper.list(user);
		PageInfo <User>pageInfo = new PageInfo<User>(list);
		return pageInfo;
	}

	@Override
	public UserCountDTO getUserCount(User user) {
		return userMapper.getUserCount(user);
	}

	@Override
	public JSONObject resetPassword(User user) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		json.put("msg","操作失败");
		if(null == user){
			json.put("msg","参数不为空");
			return json;
		}
		if(StringUtils.isAnyBlank(user.getPhone(),user.getPassword(),user.getConfirmPassword(),user.getCode())){
			json.put("msg","参数不为空");
			return json;
		}
		if(!user.getPassword().equals(user.getConfirmPassword())){
			json.put("msg","两次密码不一致");
			return json;
		}
		String key = Constants.ADMIN_SMS_FIND_PASSWORD+user.getPhone();
		String redisCode = RedisUtils.STRINGS.get(key);
		if(StringUtils.isBlank(redisCode)){
			json.put("msg","验证码已过期,重新获取");
			return json;
		}
		if(!user.getCode().equals(redisCode)){
			json.put("msg","验证码错误,请重新输入");
			return json;
		}
		User userDb = userMapper.getUserByPhone(user.getPhone());
		if(null == userDb){
			json.put("msg","当前用户不存在");
			return json;
		}
		userDb.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		int num = userMapper.updateUser(userDb);
		if(num>0){
			json.put("code",1000);
			json.put("msg","修改密码成功");
			RedisUtils.KEYS.del(key);
		}
		return json;
	}
	@Override
	public JSONObject setUserStatus(User user) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		json.put("msg","操作失败");
		if(null == user){
			json.put("msg","用户不为空");
			return json;
		}
		int num = userMapper.updateUser(user);
		if(num>0){
			//TODO 不能冻结自身;
			if(null != user.getStatus() && user.getStatus() ==1){
				//标识冻结用户
				Set<String> keys = RedisUtils.KEYS.keys(Constants.USER_LOGIN.concat(user.getUid()+":*"));
				if(null != keys && keys.size()>0){
					for (String key : keys) {
						RedisUtils.KEYS.del(key);
					}
				}
			}
			json.put("code",1000);
			json.put("msg","成功");
		}
		return json;
	}

	@Override
	public String getUserInfo(User user) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == user || null == user.getUid()){
			json.put("msg","请求参数不能为空");
			return json.toJSONString();
		}
		String uid = user.getUid();
		User userDb = userMapper.getUserByUid(uid);
		if(null == userDb){
			json.put("msg","当前用户状态异常");
			return json.toJSONString();
		}
		
		UserBank bank = userBankMapper.getByUid(uid);
		UserAddress address = userAddressMapper.getByUid(uid);
		json.put("user", userDb);
		json.put("bank", bank);
		json.put("address", address);
		json.put("code",1000);
		return json.toJSONString();
	}

	@Override
	public PageInfo<UserCash> pageUserCash(UserCommonDTO dto) {
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		List<UserCash> list = userCashMapper.listUserCash(dto);
		PageInfo <UserCash>pageInfo = new PageInfo<UserCash>(list);
		return pageInfo;
	}

	@Override
	public JSONObject auditUserCash(UserCommonDTO dto) {
		JSONObject json = new JSONObject();
		json.put("code", 2000);
		if(null == dto){
			json.put("msg","参数不为空");
			return json;
		}
		UserCash cash = userCashMapper.get(dto);
		if(null == cash){
			json.put("msg","当前记录不存在/已提现");
			return json;
		}
		Date now = new Date();
		
		cash.setStatus(1);
		cash.setUpdateTime(now);
		int flag = userCashMapper.update(cash);
		if(flag > 0){
			//设置账单内容
			UserBill bill = new UserBill();
			bill.setUid(cash.getUid());
			bill.setType(2);
			bill.setBillType(5);
			bill.setAmount(cash.getRealAmount());
			bill.setDescription("用户提现");
			bill.setCreateTime(now);
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("productName", "用户提现");
			map.put("productType", "提现");
			map.put("price", cash.getRealAmount());
			map.put("amount", "1");
			map.put("total", cash.getRealAmount());
			map.put("description", "用户提现内容");
			List<Map<String,Object>>list =new ArrayList<>();
			list.add(map);
			bill.setInfo(JSON.toJSONString(list));
			userBillMapper.insert(bill);
			json.put("code", 1000);
			json.put("msg","提现已完成");
			return json;
		}else{
			json.put("msg","提现审核失败");
		}
		return json;
	}

	@Override
	public List<UserCash> listUserCash(UserCommonDTO dto) {
		if(null == dto || null == dto.getUid()){
			return null;
		}
		List<UserCash> list = userCashMapper.listUserCashByUid(dto.getUid());
		return list;
	}

	@Override
	public List<UserInviterDTO> listUserInviter(UserCommonDTO dto) {
		if(null == dto || null == dto.getUid()){
			return null;
		}
		List<UserInviterDTO> list = userMapper.listUserInviter(dto);
		return list;
	}

	@Override
	public List<UserRecordDTO> listUserRecord(UserCommonDTO dto) {
		if(null == dto){
			return null;
		}
		JSONObject param = new JSONObject();
		param.put("uid",dto.getUid());
		Integer type = dto.getType();
		param.put("type",type);
		if(type == 2){
			param.put("payType", 2);
		}
		List<UserRecordDTO> list = userMapper.listUserRecord(param);
		return list;
	}

	@Override
	public List<UserBillDTO> listUserBill(UserCommonDTO dto) {
		if(null == dto || null == dto.getUid()){
			return null;
		}
		List<OrderInfo> list = orderInfoMapper.getUserBill(dto.getUid());
		List<UserBillDTO>ret = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			for (OrderInfo info : list) {
				UserBillDTO billDto = new UserBillDTO();
				Integer orderType = info.getOrderType();
				billDto.setTime(info.getPayTime());
				billDto.setDescription(info.getProductDetail());
				BigDecimal totalPay = info.getTotalPay();
				if(orderType == 1){
					billDto.setTotalDesc(info.getDescription());
				}else{
					billDto.setTotalDesc(totalPay.toString()+":元");
				}
				billDto.setAmount(totalPay);
				ret.add(billDto);
			}
		}
		return ret;
	}

	@Override
	public List<UserPhoneRecord> listUserPhoneRecord(UserCommonDTO dto) {
		if(null == dto || null == dto.getUid()){
			return null;
		}
		String uid = dto.getUid();
		OrderInfo info = new OrderInfo();
		info.setUid(uid);
		info.setOrderType(3);
		List<OrderInfo> list = orderInfoMapper.listOrderInfo(info);
		List<UserPhoneRecord> ret = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			for (OrderInfo orderInfo : list) {
				UserPhoneRecord phoneRecord = new UserPhoneRecord();
				ret.add(phoneRecord);
				phoneRecord.setPhone(orderInfo.getDescription());
				phoneRecord.setCreateTime(orderInfo.getCreateTime());
				phoneRecord.setTotalMoney(orderInfo.getTotalMoney());
				phoneRecord.setDiscountMoney(orderInfo.getTotalPay());
				Integer s = orderInfo.getPayStatus();
				phoneRecord.setStatus(s==1?"未付款":(s==3?"已取消":"已付款"));
			}
		}
		return ret;
	}

	@Override
	public List<UserOrderInfoDTO> listUserOrderInfo(UserCommonDTO dto) {
		if(null == dto || null == dto.getUid()){
			return null;
		}
		String uid = dto.getUid();
		OrderInfo info = new OrderInfo();
		info.setUid(uid);
		info.setOrderType(4);
		List<OrderInfo> list = orderInfoMapper.listOrderInfo(info);
		List<UserOrderInfoDTO>ret = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			UserOrderInfoDTO orderDto = null;
			for (OrderInfo orderInfo : list) {
				orderDto = new UserOrderInfoDTO();
				BeanUtils.copyProperties(orderInfo, orderDto);
				String productDetail = orderInfo.getProductDetail();
				if(StringUtils.isNotBlank(productDetail)){
					List<UserOrderProductDTO> products = JSONArray.parseArray(productDetail, UserOrderProductDTO.class);
					orderDto.setProducts(products);
				}
				ret.add(orderDto);
			}
		}
		return ret;
	}
}
