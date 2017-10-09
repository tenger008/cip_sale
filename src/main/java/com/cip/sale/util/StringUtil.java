package com.cip.sale.util;

import java.util.StringTokenizer;
import java.util.UUID;

public class StringUtil {
	
	public static void main(String[] args) {
		String creatUUID = creatUUID();
		System.out.println(creatUUID);
	}

	public static String creatUUID(){
		UUID uuid = java.util.UUID.randomUUID();
		String temp = uuid.toString();
		//System.out.println(temp);
		StringTokenizer token = new StringTokenizer(temp,"-");
		StringBuilder rst = new StringBuilder("");
		while(token.hasMoreTokens()){
			rst.append(token.nextToken());
		}
		//System.out.println(rst.toString());
		return rst.toString();
	}

	public static boolean isEmpty( String s ) {
		// java中trim方法不能去掉全角空格
		return s == null || s.trim().equals("");
	}
	
	public static boolean isNotEmpty( String s ) {
//		return s != null && !s.trim().equals("");
		return !isEmpty(s);
	}
	
}
