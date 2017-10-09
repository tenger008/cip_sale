package com.cip.sale.bean;

public class User_auth {
 private String uuid;
 private String user_id;
 private String identity_type;//登录类型（手机号 邮箱 用户名）或第三方应用名称（微信 微博等）
 private String identifier;//标识（手机号 邮箱 用户名或第三方应用的唯一标识）
 private String credential;//密码凭证（站内的保存密码，站外的不保存或保存token）
public String getUuid() {
	return uuid;
}
public void setUuid(String uuid) {
	this.uuid = uuid;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getIdentity_type() {
	return identity_type;
}
public void setIdentity_type(String identity_type) {
	this.identity_type = identity_type;
}
public String getIdentifier() {
	return identifier;
}
public void setIdentifier(String identifier) {
	this.identifier = identifier;
}
public String getCredential() {
	return credential;
}
public void setCredential(String credential) {
	this.credential = credential;
}
 
}
