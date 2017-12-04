package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.mod.bean.Qrcheck;
import com.mod.mapper.QrcheckMapper;
import com.util.DbConn;

public class QrcheckDao{
	
	private static QrcheckMapper qm;
	private static SqlSession session = null;
	static {
		try {
			session = DbConn.getFactory().openSession();
			qm = session.getMapper(QrcheckMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Logger log = Logger.getLogger(QrcheckDao.class.getName());
	public static Qrcheck getQrcheck(String UUID){
		
		Qrcheck qrc = null;
		try {
			qrc=qm.selectByUUID(UUID);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
		
		return qrc;
	}
	
	public static boolean deleQrcheck(String UUID){
		int i  = 0;
		i=qm.deleteByUUID(UUID);
		if(i>0){
			return true;
		}
		return false;
	}
	public static boolean insertQrcheck(Qrcheck qrc){
		int i  = 0;
		i=qm.insertSelective(qrc);
		if(i>0){
			return true;
		}
		return false;
	}
	public static boolean changeStatus(String UUID,Integer status){
		int i  = 0;
		i=qm.changeStatus(UUID, status);
		if(i>0){
			return true;
		}
		return false;
	}
	public static boolean update(Qrcheck qrc){
		int i  = 0;
		i=qm.update(qrc);
		if(i>0){
			return true;
		}
		return false;
	}
	@Test
	public void testMethod(){
		// Qrcheck qr = getQrcheck("20171121095151059-8ce3d6ddd4a5431e9f814a0d8db011e9");
		// System.out.println(qr.getStatus());
//		Qrcheck q = new Qrcheck("20171121095151059-8ce3d6ddd4a5431e9f814a0d8db011e9", 1);
//		insertQrcheck(q);
//		boolean b = deleQrcheck("20171121095151059-8ce3d6ddd4a5431e9f814a0d8db011e9");
//		System.out.println(b);
		String flag = "";
		System.out.println((flag == null));
		System.out.println((flag.equals(null)));
		System.out.println((flag.equals("")));
	}

	public static Qrcheck insertSelective(Qrcheck t) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("此类未设置insertSelective方法！"); 
	}

	public static int updateByObj(Qrcheck newT, Qrcheck oldT) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("此类未设置updateByObj方法！"); 
	}

	public static int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		//throw new Exception("此类未设置deleteByPrimaryKey方法！");
		
		int i = 0;
		i = qm.deleteByUUID(id);
		return i;
	}

	public static int deleByObj(Qrcheck t) throws Exception {
		int i = 0;
		i = qm.deleteByUUID(t.getUuid());
		return i;
	}
	
	
}
