package com.cip.user.service;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import com.cip.sale.util.MyPwTools;


public class MyPwdCallbackClient implements CallbackHandler {

	@Override
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback wsp = (WSPasswordCallback) arg0[0];
		
		wsp.setIdentifier("baiducxf");
		String getpw = MyPwTools.getpw("baiducxf", "wss4j");
		
		wsp.setPassword(getpw);
	}

}
