package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mod.bean.GoodsType;
import com.mod.mapper.GoodsTypeMapper;
import com.util.DbConn;

public class GoodsTypeDao{
	private static GoodsTypeMapper gtm;
	private static SqlSession session = null;
	static Logger log = Logger.getLogger(GoodsTypeDao.class.getName());
	static {
		try {
			session = DbConn.getFactory().openSession();
			gtm = session.getMapper(GoodsTypeMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static  GoodsType insertSelective(GoodsType t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}

	public static int updateByObj(GoodsType newT, GoodsType oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.updateByPrimaryKeyChangePk(newT, oldT.getTid());
		if(i==1){
			return i;
		}
		return 0;
	}

	public static int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.deleteByPrimaryKey(Integer.parseInt(id));;
		if(i==1){
			return i;
		}
		return 0;
	}

	public static int deleByObj(GoodsType t) throws Exception {
		log.warn("此类未设置deleByObj方法！");
		return 0;
	}

	
}
