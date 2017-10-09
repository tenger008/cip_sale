package com.cip.user.service;

import javax.jws.WebService;

import com.cip.sale.bean.User;


@WebService
public interface RegistServiceInf {

	 String regist(User user);
	 
		String checkName(String name);

		   //注册检查邮箱是否重复
		String checkEmail(String email);
		
		//找回密码时根据用户名和邮箱验证用户,查询uuid
	    String checkUser(String email,String name);
		
		void activation(String uuid, String formatNow);

		User queryUserStatus(String uuid);

		void setActivationStatus(String uuid);
}
