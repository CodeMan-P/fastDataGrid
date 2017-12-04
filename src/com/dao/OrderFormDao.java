package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mod.bean.OrderForm;
import com.mod.mapper.OrderFormMapper;
import com.util.DbConn;

public class OrderFormDao{
	private static OrderFormMapper ofm;
	private static SqlSession session = null;
	static {
		try {
			session = DbConn.getFactory().openSession();
			ofm = session.getMapper(OrderFormMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static Logger log = Logger.getLogger(OrderFormDao.class.getName());
	
	public static OrderForm insertSelective(OrderForm t) throws Exception {
		// TODO Auto-generated method stub
		int i =0;
		i=ofm.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}

	public static int updateByObj(OrderForm newT,OrderForm oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = ofm.updateByPrimaryKeyChangePk(newT, oldT);
		return i;
	}

	public static int deleteByPrimaryKey(String id) throws Exception {
		log.warn("此类未设置deleteByPrimaryKey方法！");
		return 0;
	}

	public static int deleByObj(OrderForm t) throws Exception {
		Long oid = Long.parseLong(t.getOid());
		int i = 0;
		i = ofm.deleByOUid(oid, t.getUid());
		if(i==1){
			return i;
		}
		return 0;
	}

}
