package com.dao;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mod.bean.RegionInfo;
import com.mod.mapper.RegionMapper;
import com.util.DbConn;

public class RegionDao {
	
	static Logger log = Logger.getLogger(RegionDao.class.getName());
	private static RegionMapper gm;
	private static SqlSession session = null;

	static {
		try {
			session = DbConn.getFactory().openSession();
			gm = session.getMapper(RegionMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn(e.getLocalizedMessage());
		}
	}
	@Test
	public void testM(){
		ArrayList<RegionInfo> list = null;
		list = getRegionInfo();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<RegionInfo> getRegionInfo(){
		ArrayList<RegionInfo> list = null;
		list = gm.getRegionInfo();
		return list;
	};
	
}
