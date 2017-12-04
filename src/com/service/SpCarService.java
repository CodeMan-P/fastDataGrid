package com.service;

import java.util.HashMap;
import java.util.LinkedList;

import com.dao.SpCarDao;
import com.mod.bean.ShoppingCar;

public class SpCarService {
	public static boolean addGoods(ShoppingCar car) {
		return SpCarDao.addGoods(car);
	}

	public static int getCarNum(Integer uid) {
		return SpCarDao.getCarNum(uid);
	}

	public static boolean deleGoods(Integer cid) {
		return SpCarDao.deleGoods(cid);
	}

	public static LinkedList<ShoppingCar> getCarListByUid(Integer uid) {
		return SpCarDao.getCarListByUid(uid);
	}

	public static LinkedList<HashMap<String, Object>> getCarView(Integer uid) {
		return SpCarDao.getCarView(uid);
	}
}
