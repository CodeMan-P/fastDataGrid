package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mod.bean.ShoppingCar;
import com.mod.mapper.QrcheckMapper;
import com.mod.mapper.ShoppingCarMapper;
import com.util.DbConn;

public class ShoppingCarDao{
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

	static Logger log = Logger.getLogger(ShoppingCarDao.class.getName());

	public static ShoppingCar insertSelective(ShoppingCar t) throws Exception {
		// TODO Auto-generated method stub
		int i=0;
		i = scm.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}

	public static int updateByObj(ShoppingCar newT,ShoppingCar oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = scm.updateByPrimaryKeyChangePk(newT, oldT.getCid());
		return i; 
	}

	public static int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return scm.deleteByPrimaryKey(Integer.parseInt(id));
	}

	public static int deleByObj(ShoppingCar t) throws Exception {
		// TODO Auto-generated method stub
		return scm.deleteByPrimaryKey(t.getCid());
	}
	
}
