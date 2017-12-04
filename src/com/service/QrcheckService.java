package com.service;

import com.dao.QrcheckDao;
import com.mod.bean.Qrcheck;

public class QrcheckService {
	public static Qrcheck getQrcheck(String UUID){
		return QrcheckDao.getQrcheck(UUID);
	}
	
	public static boolean deleQrcheck(String UUID){
		return QrcheckDao.deleQrcheck(UUID);
	}
	
	public static boolean insertQrcheck(Qrcheck qrc){
		return QrcheckDao.insertQrcheck(qrc);
	}
	
	public static boolean changeStatus(String UUID,Integer status){
		return QrcheckDao.changeStatus(UUID, status);
	}
	public static boolean update(Qrcheck qrc){
		return QrcheckDao.update(qrc);
	}
}
