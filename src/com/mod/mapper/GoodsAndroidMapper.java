package com.mod.mapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Select;

import com.mod.bean.GoodsAndroid;

public interface GoodsAndroidMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goodsand
	 * @mbggenerated  Fri Dec 01 13:20:04 CST 2017
	 */
	int insert(GoodsAndroid record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goodsand
	 * @mbggenerated  Fri Dec 01 13:20:04 CST 2017
	 */
	int insertSelective(GoodsAndroid record);

	@Select("select * from goodsand")
	LinkedList<LinkedHashMap<String, Object>> getGoddsAnd();
}