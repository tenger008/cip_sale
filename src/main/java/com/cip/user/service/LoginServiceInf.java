package com.cip.user.service;

import javax.jws.WebService;

import com.cip.sale.bean.User;

@WebService
public interface LoginServiceInf {

	public User login(User user);


}
