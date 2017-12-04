package com.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Users;
import com.mod.mapper.UsersMapper;
import com.util.DbConn;
import com.util.LoadBean;

public class TableDao {
	static Logger log = Logger.getLogger(TableDao.class.getName());
	private static UsersMapper um;
	private static SqlSession session = null;

	static {
		try {
			session = DbConn.getFactory().openSession();
			um = session.getMapper(UsersMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
	}
	static ObjectMapper mapper = new ObjectMapper();

	public static List getTable(String tableName) {
		// Class<?> table = null;
		// try {
		// table = Class.forName(tableName);
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		List<?> list = null;
		try {
			LinkedList<LinkedHashMap<String, Object>>  temp = um.getTable(tableName);
			list = temp;
		} catch (Exception e) {
			//e.printStackTrace();
			log.warn(e.getMessage());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Object reflect(String flag, String path, String json, String method) throws InvocationTargetException,Exception {
		ObjectMapper mapper = new ObjectMapper();
		json = json.replaceAll("\\[|\\]", "");
		

		HashMap<String, String> beanMap = LoadBean.getBeanMap(path);
	//	HashMap<String, String> mapperMap = LoadBean.getMapperMap(path);
		Class<?> c = null;
		Class<?> mp = null;
		Class<?> dao = null;
		HashMap<String, String> daoMap = LoadBean.getDaoMap(path);

		if (beanMap.containsKey(flag)) {
			c = Class.forName("com.mod.bean." + beanMap.get(flag));
		} else {
			return null;
		}
//		if (mapperMap.containsKey(flag)) {
//			mp = Class.forName("com.mod.mapper." + mapperMap.get(flag));
//		} else {
//			return null;
//		}
		if (daoMap.containsKey(flag)) {
			dao = Class.forName("com.dao." + daoMap.get(flag));
		} else {
			return null;
		}

		Object temp = null;
		Method meth;
		switch (method) {
		case "insertSelective":
			log.info("insert request:------------------------------>");
			log.info(json);
			temp = mapper.readValue(json, c);
			meth = dao.getMethod("insertSelective", c);
			temp = meth.invoke(dao, c.cast(temp));
			break;
		case "deleByPk":
			log.info("deleByPk request:------------------------------>");
			log.info(json);
			meth = dao.getMethod("deleteByPrimaryKey", String.class);
			String id = mapper.reader().readTree(json).get("id").asText();
			temp = meth.invoke(dao, id);
			break;
		case "deleByObj":
			log.info("deleByObj request:------------------------------>");
			log.info(json);
			temp = mapper.readValue(json, c);
			meth = dao.getMethod("deleByObj", c);
			temp = meth.invoke(dao, c.cast(temp));
			break;
		case "updateByObj":
			log.info("updateByObj request:------------------------------>");
			log.info(json);
			HashMap<String,String> hmTemp =mapper.readValue(json, HashMap.class); 
			Object newRow = mapper.readValue(hmTemp.get("new"),c);
			Object oldRow = mapper.readValue(hmTemp.get("old"),c);
			meth = dao.getMethod("updateByObj", c,c);
			temp = meth.invoke(dao, c.cast(newRow),c.cast(oldRow));
			break;
		}

		// temp =
		// session.insert("com.mod.mapper."+mapperList.get(flag)+".insertSelective",
		// c.cast(temp));

		return temp;
	}

	@Test
	public void testMe() {
		String path = "D:\\MyEclipse\\apache-tomcat-9.0.1\\webapps\\easyUI2\\WEB-INF\\classes\\com\\mod\\";
		Users user = new Users("ADAasAD", "ASDSADSAD");
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String flag = "users";
//		try {
//			user = (Users) insert(flag, path, json);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			System.out.println(mapper.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// List list =getTable("users");
		// DeserializationConfig cfg = mapper.getDeserializationConfig();
		// //设置JSON时间格式
		// SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		//
		// mapper.setDateFormat(myDateFormat);
		// try {
		// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
