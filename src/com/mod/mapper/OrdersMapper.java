package com.mod.mapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mod.bean.OrderForm;
import com.mod.bean.Orders;

public interface OrdersMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated  Tue Dec 05 14:45:23 CST 2017
	 */
	int insert(Orders record);


	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table orders
	 * @mbggenerated  Tue Dec 05 14:45:23 CST 2017
	 */
	int insertSelective(Orders record);


	@Select("select * from orders")
	LinkedList<Orders> getOrdersTable();
	
	
	int updateByPrimaryKeyChangePk(@Param("n")Orders n,@Param("old")Orders old);
	int updateByOUid(@Param("oid") String oid, @Param("uid") Integer uid);

	int updateByOUid(@Param("oid") Long oid, @Param("uid") Integer uid);

	int deleByOUid(@Param("oid") Long oid, @Param("uid") Integer uid);

	LinkedList<LinkedHashMap<String, Object>> getOGViewGoupByOid(@Param("uid") Integer uid);
	// LinkedList<HashMap<String,Object>>
	// getOGViewGoupByOid(@Param("uid")Integer uid);
	// LinkedHashMap<String, Object> getOGViewGoupByOid(@Param("uid")Integer
	// uid);
}