package com.util;

import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;


public class DbConn {
	private static SqlSessionFactory sqlSessionFactory = null;
	private static Reader reader;

	static Logger log = Logger.getLogger(DbConn.class.getName());

	public static SqlSessionFactory getFactory() {
		if (sqlSessionFactory == null) {
			try {
				reader = Resources.getResourceAsReader("mybatis-config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			} catch (Exception e) {
				log.warn(e.getLocalizedMessage());
			}
		}
		return sqlSessionFactory;
	}

	public static BasicDataSource bds;
	// jdbc:mysql://localhost:3306/mydb
	// jdbc:oracle:thin:@localhost:1521:orcl
	final String Url = "jdbc:mysql://localhost:3306/shop?characterEncoding=UTF-8";

	public DbConn() {
		bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl(Url);
		bds.setUsername("root");
		bds.setPassword("mysql");
		bds.setMaxActive(5);
		bds.setMaxIdle(5);
		bds.setMaxWait(1000);
	}

	public static Connection getCon() {
		Connection con = null;
		if (bds == null)
			new DbConn();
		try {
			con = bds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeBds() {
		try {
			if (bds != null) {
				bds.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
