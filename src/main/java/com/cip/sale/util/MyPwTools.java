package com.cip.sale.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPwTools {

	public static String getpw(String u, String p) {
		String a = u;

		String substring = a.substring(a.length() - 3, a.length());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

		String d = sdf.format(new Date());

		String s = substring + d + p;

		String md5 = MD5Util.digest(s);
		return md5;
	}

}
