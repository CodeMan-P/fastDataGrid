package com.service;

import java.util.LinkedList;

import com.dao.UsersDao;
import com.mod.bean.Address;
import com.mod.bean.Users;

public class UserService {

	public static Users findUser(Users user) {
		return UsersDao.findUser(user);
	}

	public static String addUser(Users user) {

		return UsersDao.addUser(user);
	}

	public static LinkedList<Address> getAdress(Integer uid) {
		return UsersDao.getAdress(uid);
	}

	public static boolean deleAddress(Integer addressId) {
		return UsersDao.deleAddress(addressId);
	}

	public static boolean editAddress(Address address) {
		return UsersDao.editAddress(address);
	}

	public static boolean addAddress(Address address) {
		return UsersDao.addAddress(address);
	}
}
