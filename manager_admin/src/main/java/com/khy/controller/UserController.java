package com.khy.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Msg;
import com.khy.entity.User;
import com.khy.entity.UserBillDTO;
import com.khy.entity.UserCash;
import com.khy.entity.UserPhoneRecord;
import com.khy.mapper.dto.UserCommonDTO;
import com.khy.mapper.dto.UserCountDTO;
import com.khy.mapper.dto.UserInviterDTO;
import com.khy.mapper.dto.UserOrderInfoDTO;
import com.khy.mapper.dto.UserRecordDTO;
import com.khy.service.UesrService;
import com.khy.utils.SmsUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final String YEAR_FORMTE="yyyy-MM-dd HH:mm:ss";
	private static final String MONTH_FORMTE="MM-dd HH:mm:ss";
	private static String msg = "手机号/密码不正确";
	private static String msg2 = "非管理员不能登录";
	private static String msg3 = "您的账户被冻结,请联系客服";
	@Autowired
	private UesrService uesrService;

	@Autowired
	private SmsUtils SmsUtils;

	@RequestMapping(value = "getMessageCode", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getMessageCode(@RequestBody Msg msg) {
		System.out.println("发送短信验证码的");
		JSONObject json = SmsUtils.sendMessage(msg);
		return json.toString();
	}

	/**
	 * 登录接口
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年9月30日下午1:27:17
	 * @param user
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/login")
	public String loginForm(User user, HttpSession session) throws UnsupportedEncodingException {
		user = uesrService.login(user);
		String ret = "";
		if (user != null) {
			if (user.getIsManager() == 0) {
				ret = URLEncoder.encode(msg2, "UTF-8");
				return "redirect:/login?msg=" + ret;
			}
			if (user.getStatus() == 1) {
				ret = URLEncoder.encode(msg3, "UTF-8");
				return "redirect:/login?msg=" + ret;
			}
			session.setAttribute("loginUser", user);
			return "redirect:/index";
		} else {
			ret = URLEncoder.encode(msg, "UTF-8");
			return "redirect:/login?msg=" + ret;
		}
	}

	/**
	 * 注册接口
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年9月30日下午1:27:26
	 * @param user
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/register")
	public String register(User user, HttpSession session) throws UnsupportedEncodingException {
		String ret = null;
		JSONObject json = uesrService.register(user, session);
		if (json.getInteger("code").intValue() == 1000) {
			ret = "redirect:/index";
		} else {
			ret = "redirect:/register?msg=" + URLEncoder.encode(json.getString("msg"), "UTF-8");
		}
		return ret;
	}

	@RequestMapping("/resetPassword")
	public String resetPassword(User user) throws UnsupportedEncodingException {
		String ret = null;
		JSONObject json = uesrService.resetPassword(user);
		if (json.getInteger("code").intValue() == 1000) {
			ret = "redirect:/login";
		} else {
			ret = "redirect:/resetPassword?msg=" + URLEncoder.encode(json.getString("msg"), "UTF-8");
		}
		return ret;
	}

	/***
	 * 设置用户的状态/管理员/vip/
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年10月6日下午1:12:55
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/setUserStatus")
	@ResponseBody
	public String setUserStatus(@RequestBody User user) throws UnsupportedEncodingException {
		JSONObject json = uesrService.setUserStatus(user);
		return json.toString();
	}

	/**
	 * 退出登录接口
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年9月30日下午1:27:32
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginOut")
	public String loginOut(HttpSession session) {
		session.setAttribute("loginUser", null);
		return "redirect:/index";
	}

	/***
	 * 获取用户
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年9月30日下午1:28:17
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUserList")
	public ModelAndView toUserList(User user) {
		ModelAndView model = new ModelAndView("user/list");
		PageInfo<User> page = uesrService.page(user);
		UserCountDTO count = uesrService.getUserCount(user);
		model.addObject("page", page);
		model.addObject("count", count);
		return model;
	}

	/**
	 * 根据条件查询用户
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年10月26日下午4:42:04
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataList")
	@ResponseBody
	public String dateList(@RequestBody User user) {
		PageInfo<User> page = uesrService.page(user);
		UserCountDTO count = uesrService.getUserCount(user);
		JSONObject json = new JSONObject();
		json.put("page", page);
		json.put("count", count);
		return json.toString();
	}

	/**
	 * 获取用户详情
	 * 
	 * @Description
	 * @author khy
	 * @date 2018年10月26日下午4:42:22
	 * @param user
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(@RequestBody User user) {
		String ret = uesrService.getUserInfo(user);
		return ret;
	}

	/**
	 * 获取当前用户所有的提现记录
	 * @Description
	 * @author khy
	 * @date  2018年10月26日下午5:05:05
	 * @param dto
	 * @return
	 */
	@RequestMapping("/listUserCash")
	@ResponseBody
	public String listUserCash(@RequestBody UserCommonDTO dto) {
		List<UserCash> list = uesrService.listUserCash(dto);
		return JSON.toJSONStringWithDateFormat(list, MONTH_FORMTE, new SerializerFeature[0]);
	}
	
	/**
	 * 查询全部的邀请列表数据
	 * @Description
	 * @author khy
	 * @date  2018年10月26日下午5:19:11
	 * @param dto
	 * @return
	 */
	@RequestMapping("/listUserInviter")
	@ResponseBody
	public String listUserInviter(@RequestBody UserCommonDTO dto) {
		List<UserInviterDTO> list = uesrService.listUserInviter(dto);
		return JSON.toJSONStringWithDateFormat(list, YEAR_FORMTE, new SerializerFeature[0]);
	}
	
	/**
	 * 佣金列表/转账记录
	 * @Description
	 * @author khy
	 * @date  2018年10月26日下午5:49:02
	 * @param dto
	 * @return
	 */
	@RequestMapping("/listUserRecord")
	@ResponseBody
	public String listUserRecord(@RequestBody UserCommonDTO dto) {
		List<UserRecordDTO> list = uesrService.listUserRecord(dto);
		return JSON.toJSONStringWithDateFormat(list, MONTH_FORMTE, new SerializerFeature[0]);
	}
	/**
	 * 我的账单内容
	 * @Description
	 * @author khy
	 * @date  2018年11月1日下午5:15:35
	 * @param dto
	 * @return
	 */
	@RequestMapping("/listUserBill")
	@ResponseBody
	public String listUserBill(@RequestBody UserCommonDTO dto) {
		List<UserBillDTO> list = uesrService.listUserBill(dto);
		return JSON.toJSONStringWithDateFormat(list, MONTH_FORMTE, new SerializerFeature[0]);
	}
	@RequestMapping("/listUserPhoneRecord")
	@ResponseBody
	public String listUserPhoneRecord(@RequestBody UserCommonDTO dto) {
		List<UserPhoneRecord> list = uesrService.listUserPhoneRecord(dto);
		return JSON.toJSONStringWithDateFormat(list, MONTH_FORMTE, new SerializerFeature[0]);
	}
	
	@RequestMapping("/listUserOrderInfo")
	@ResponseBody
	public String listUserOrderInfo(@RequestBody UserCommonDTO dto) {
		List<UserOrderInfoDTO> list = uesrService.listUserOrderInfo(dto);
		return JSON.toJSONStringWithDateFormat(list, MONTH_FORMTE, new SerializerFeature[0]);
	}
	
	
	/**
	 * 跳转到提现列表
	 * @Description
	 * @author khy
	 * @date 2018年10月26日下午4:42:30
	 * @param dto
	 * @return
	 */
	@RequestMapping("/toUserCash")
	public ModelAndView toUserCash(UserCommonDTO dto) {
		ModelAndView model = new ModelAndView("user/cash");
		PageInfo<UserCash> page = uesrService.pageUserCash(dto);
		model.addObject("page", page);
		return model;
	}

	@RequestMapping("/pageUserCash")
	@ResponseBody
	public String pageUserCash(@RequestBody UserCommonDTO dto) {
		PageInfo<UserCash> page = uesrService.pageUserCash(dto);
		return JSON.toJSONString(page);
	}
	
	@RequestMapping("/auditUserCash")
	@ResponseBody
	public String auditUserCash(@RequestBody UserCommonDTO dto) {
		JSONObject json = uesrService.auditUserCash(dto);
		return json.toJSONString();
	}
}
