package com.dao;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.ShoppingCar;
import com.mod.mapper.ShoppingCarMapper;
import com.util.DbConn;

public class SpCarDao {
	private static ShoppingCarMapper scm;
	private static SqlSession session = null;

	static {
		try {
			session = DbConn.getFactory().openSession();
			scm = session.getMapper(ShoppingCarMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Logger log = Logger.getLogger(SpCarDao.class.getName());

	@Test
	public void testMethhod() {
		LinkedList<HashMap<String, Object>> list = getCarView(1);
		// LinkedList<ShoppingCar> list = getCarListByUid(1);
		System.out.println(list.size());
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public static boolean deleGoods(Integer cid) {
		int i = 0;

		try {
			i = scm.deleteByPrimaryKey(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public static LinkedList<HashMap<String, Object>> getCarView(Integer uid) {
		LinkedList<HashMap<String, Object>> list = null;
		try {
			list = scm.getCarView(uid);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
		return list;
	}

	public static LinkedList<ShoppingCar> getCarListByUid(Integer uid) {
		LinkedList<ShoppingCar> list = null;
		try {
			list = scm.getCarListByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
		return list;
	}

	public static int getCarNum(Integer uid) {
		int i = 0;
		try {
			i = scm.queryCarNum(uid);
		} catch (Exception e) {
			log.warn(e.getLocalizedMessage());
		}
		return i;
	}

	public static boolean addGoods(ShoppingCar car) {
		int i = 0;
		ShoppingCar temp = null;
		try {
			temp = scm.selectByUGid(car.getUid(), car.getGid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (temp != null && temp.getGnum() != 0) {
				// 购物车表去重复
				car.setGnum(car.getGnum() + temp.getGnum());
				// 设置主键，主键自增，增加时不用设置
				car.setCid(temp.getCid());
				i = scm.updateByPrimaryKey(car);
			} else {
				i = scm.insertSelective(car);
			}
		} catch (Exception e) {
			log.warn(e.getLocalizedMessage());
		}
		if (i > 0) {
			return true;
		}
		return false;
	}
}
