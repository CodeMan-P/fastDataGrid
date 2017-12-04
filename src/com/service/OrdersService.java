package com.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.dao.OrdersDao;
import com.mod.bean.OrderForm;
import com.mod.bean.Orders;

public class OrdersService {
	public static boolean addOrders(Orders orders, LinkedList<OrderForm> orderlist, LinkedList<Integer> buyCids) {
		return OrdersDao.addOrders(orders, orderlist, buyCids);
	}

	public static LinkedList<HashMap<String, Object>> getOrderList(Long oid, Integer uid) {
		return OrdersDao.getOrderList(oid, uid);
	}

	public static LinkedList<HashMap<String, Object>> getOrderList(String oid, Integer uid) {
		return OrdersDao.getOrderList(oid, uid);
	}

	public static boolean updateByOUid(String oid, Integer uid) {
		return OrdersDao.updateByOUid(oid, uid);
	}

	public static boolean updateByOUid(Long oid, Integer uid) {
		return OrdersDao.updateByOUid(oid, uid);
	}

	public static boolean addOrders(Orders orders, OrderForm orderform) {
		return OrdersDao.addOrders(orders, orderform);
	}

	public static LinkedList<LinkedHashMap<String, Object>> getOGViewGoupByOid(Integer uid) {

		return OrdersDao.getOGViewGoupByOid(uid);
	}
	public static LinkedList<Orders> getOrdersTable(){
		return OrdersDao.getOrdersTable();
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> jsonFactory(LinkedList<HashMap<String, Object>> orderslist) {
		// 提取公共字段
		HashMap<String, Object> m = new HashMap<String, Object>();
		LinkedList<String> fileds = new LinkedList<String>();
		fileds.add("address");
		fileds.add("sum");
		fileds.add("oid");
		fileds.add("uid");
		fileds.add("state");

		HashMap<String, Object> hm = null;
		HashMap<String, Object> tempm = null;
		// 移除公共字段存入 m
		for (int i = 0; i < orderslist.size(); i++) {
			hm = orderslist.get(i);
			tempm = (HashMap<String, Object>) orderslist.get(i).clone();
			if (i == 0) {
				for (String s : tempm.keySet()) {
					if (fileds.contains(s)) {
						m.put(s, hm.remove(s));
					}
				}
			} else {
				for (String s : tempm.keySet()) {
					if (fileds.contains(s)) {
						hm.remove(s);
					}
				}
			}
			// 商品依次加入m
			m.put((i + 1) + "_goods", hm);
		}
		;
		// ObjectMapper mapper = new ObjectMapper();
		// try {
		// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return m;
	}
}
