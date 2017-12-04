package com.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mod.bean.Goods;
import com.mod.bean.OrderForm;
import com.mod.bean.Orders;
import com.mod.mapper.GoodsMapper;
import com.mod.mapper.OrderFormMapper;
import com.mod.mapper.OrdersMapper;
import com.mod.mapper.ShoppingCarMapper;
import com.util.DbConn;

public class OrdersDao{
	private static OrdersMapper om;
	private static OrderFormMapper ofm;
	private static ShoppingCarMapper sc;
	private static GoodsMapper gm = null;
	private static SqlSession session = null;

	static {
		try {
			// 此持久层不自动提交
			// 一批操作完成后再提交
			session = DbConn.getFactory().openSession(false);
			om = session.getMapper(OrdersMapper.class);
			ofm = session.getMapper(OrderFormMapper.class);
			sc = session.getMapper(ShoppingCarMapper.class);
			gm = session.getMapper(GoodsMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static LinkedList<Orders> getOrdersTable(){
		LinkedList<Orders> list = null;
		try {
			list = om.getOrdersTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	};
	
	
	@Test
	public void testMethod() {
		// LinkedList<HashMap<String, Object>> list=null;
		// updateByOUid(20171112233536L,1);
		// deleOrders("20171113000907", 1);

		ObjectMapper mapper = new ObjectMapper();
		LinkedList<LinkedHashMap<String, Object>> list = null;
		//list = getOGViewGoupByOid(1);
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// try {
		// String json = mapper.writeValueAsString(list.get("goods"));
		// HashMap<String, Object> hm = mapper.readValue(json, HashMap.class);
		// System.out.println("===============================");
		// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hm));
		// } catch (JsonProcessingException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static LinkedList<LinkedHashMap<String, Object>> getOGViewGoupByOid(Integer uid) {

		LinkedList<LinkedHashMap<String, Object>> list = null;
		list = om.getOGViewGoupByOid(uid);

		return list;
	}

	public static LinkedList<HashMap<String, Object>> getOrderList(Long oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = null;
		list = ofm.getOrderList(oid, uid);
		return list;
	}

	public static LinkedList<HashMap<String, Object>> getOrderList(String oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = null;
		list = ofm.getOrderList(oid, uid);
		return list;
	}

	public static boolean updateByOUid(String oid, Integer uid) {
		Long oidTemp = 0L;
		try {
			oidTemp = Long.parseLong(oid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return updateByOUid(oidTemp, uid);
	}

	public static boolean updateByOUid(Long oid, Integer uid) {
		int i = om.updateByOUid(oid, uid);
		if (i > 0) {
			session.commit();
			return true;
		}
		return false;
	}

	static Logger log = Logger.getLogger(OrdersDao.class.getName());

	public static boolean addOrders(Orders orders, OrderForm orderform) {
		int i = 0;
		try {
			i = om.insertSelective(orders);// 添加orders表
			if (i > 0) {
				i = ofm.insertSelective(orderform);// 添加orderForm表
				if (i <= 0) {
					session.rollback();
					return false;
				}
				int gid = orderform.getGid();
				Goods goods = GoodsDao.getGoods(gid);
				int gnum = orderform.getGnum();
				int stock = goods.getStock() - gnum;
				goods.setStock(stock);
				i = gm.updateByPrimaryKeySelective(goods);
				if (i <= 0) {
					session.rollback();
					return false;
				}
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
			return false;
		}
		// 正常插入完成，提交事务
		if (i > 0) {
			try {
				session.commit();
			} catch (Exception e) {
				session.rollback();
				e.printStackTrace();
				log.warn(e.getLocalizedMessage());
				return false;
			}
			orderLog(orders, orderform);
			return true;
		}

		return false;
	}

	public static boolean deleOrders(Long oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = ofm.getOrderList(oid, uid);
		Iterator<HashMap<String, Object>> it = list.iterator();
		int i = 0;
		Goods goods = null;
		int gid, gnum, stock;
		while (it.hasNext()) {
			HashMap<String, Object> temp = it.next();
			gid = (int) temp.get("gid");
			stock = (int) temp.get("stock");
			gnum = (int) temp.get("gnum");
			stock = stock + gnum;
			goods = new Goods();
			goods.setGid(gid);
			goods.setStock(stock);
			i = gm.updateByPrimaryKeySelective(goods);
			if (i <= 0) {
				session.rollback();
				return false;
			}
		}

		try {
			i = om.deleByOUid(oid, uid);
			if (i > 0) {
				i = ofm.deleByOUid(oid, uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			return false;
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public static boolean deleOrders(String oid, Integer uid) {
		Long oidTemp = 0L;
		try {
			oidTemp = Long.parseLong(oid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return deleOrders(oidTemp, uid);
	}

	public static boolean addOrders(Orders orders, LinkedList<OrderForm> orderlist, LinkedList<Integer> buyCids) {
		int i = 0;
		try {
			i = om.insertSelective(orders);
			if (i > 0) {
				for (OrderForm orderform : orderlist) {
					i = ofm.insertSelective(orderform);
					if (i <= 0) {
						session.rollback();
						return false;
					}
					// 删减数据库对应商品
					int gid = orderform.getGid();
					Goods goods = GoodsDao.getGoods(gid);
					int gnum = orderform.getGnum();
					int stock = goods.getStock() - gnum;
					goods.setStock(stock);
					i = gm.updateByPrimaryKeySelective(goods);
					if (i <= 0) {
						session.rollback();
						return false;
					}
				}
				for (Integer cid : buyCids) {
					i = sc.deleteByPrimaryKey(cid);
					if (i <= 0) {
						session.rollback();
						return false;
					}
				}
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
			return false;
		}
		// 正常插入完成，提交事务
		if (i > 0) {
			try {
				session.commit();
			} catch (Exception e) {
				session.rollback();
				e.printStackTrace();
				log.warn(e.getLocalizedMessage());
				return false;
			}
			orderLog(orders, orderlist);
			return true;
		}
		return false;
	}

	private static void orderLog(Orders orders, OrderForm orderform) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			log.info("添加新订单(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders));
			log.info("订单详情(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderform));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private static void orderLog(Orders orders, LinkedList<OrderForm> orderlist) {
		ObjectMapper mapper = new ObjectMapper();
		SerializerProvider sp = mapper.getSerializerProvider();
		// sp.setAttribute(list2, te);

		sp.setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
					throws IOException, JsonProcessingException {
				arg1.writeString("待付款");
			}
		});
		try {
			log.info("添加新订单(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders));
			log.info("订单详情(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderlist));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}


	public static Orders insertSelective(Orders t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = om.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}


	public static int updateByObj(Orders newT,Orders oldT) throws Exception {
		int i = 0;
		i = om.updateByPrimaryKeyChangePk(newT, oldT);
		return i;
	}


	public static int deleteByPrimaryKey(String id) throws Exception {
		log.warn("此类未设置deleteByPrimaryKey方法！");
		
		throw new Exception("此类未设置deleteByPrimaryKey方法！"); 
	}


	public static int deleByObj(Orders t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = om.deleByOUid(Long.parseLong(t.getOid()), t.getUid());
		return i;
	}
}
