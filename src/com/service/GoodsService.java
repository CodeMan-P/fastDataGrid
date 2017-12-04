package com.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.dao.GoodsDao;
import com.mod.bean.Goods;

public class GoodsService {
	public static Goods getGoods(int gid) {

		return GoodsDao.getGoods(gid);
	}
	public static LinkedList<LinkedHashMap<String, Object>> getGoddsAnd(){
		return GoodsDao.getGoddsAnd();
	};
}
