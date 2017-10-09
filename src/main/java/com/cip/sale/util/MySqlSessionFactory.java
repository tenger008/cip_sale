package com.cip.sale.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFactory {

	private static SqlSessionFactory mySqlSessionFactory = null;

	private static String resource = "mybatisConfig.xml";

	private MySqlSessionFactory() {
	}

	public static SqlSessionFactory getMyFactory() {

		if (mySqlSessionFactory == null) {
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

			InputStream resourceAsStream = MySqlSessionFactory.class.getClassLoader().getResourceAsStream(resource);

			// Resources.getResourceAsStream(resource);

			mySqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);

		}
		return mySqlSessionFactory;
	}

}
