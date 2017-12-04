package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.mod.bean.Address;
import com.mod.mapper.AddressMapper;
import com.mod.mapper.UsersMapper;
import com.util.DbConn;

public class AddressDao{
	static Logger log = Logger.getLogger(AddressDao.class.getName());
	private static AddressMapper am;
	private static SqlSession session = null;
	
	static {
		try {
			session = DbConn.getFactory().openSession();
			am = session.getMapper(AddressMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
	}
	public AddressDao() {
	}

	public static Address insertSelective(Address address) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.insertSelective(address);
		if(i==1){
			return address;
		}
		return null;
	}

	public static int updateByObj(Address newT, Address oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.updateByPrimaryKeyChangePk(newT, oldT.getAdressid());
		if(i==1){
			return i;
		}
		return 0;
	}

	public static int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.deleteByPrimaryKey(Integer.parseInt(id));
		if(i==1){
			return i;
		}
		return 0;
	}

	public static int deleByObj(Address t) throws Exception {
		int i = 0;
		i = am.deleteByPrimaryKey(t.getAdressid());
		if(i==1){
			return i;
		}
		return 0;
	}

	
}
