package com.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Orders;
import com.myAnnotation.JdbcType;

public class AnnotionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Test
	public void testMe2(){
		ObjectMapper mapper = new ObjectMapper();
		String json = "{\"id\":12}";
		try {
			String js = mapper.reader().readTree(json).get("id").asText();
			
			System.out.println(Integer.parseInt(js));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	@Test
	public void testMe(){
//		FileReader fr;
//		FileInputStream fis = null;
//		InputStreamReader isr = new InputStreamReader(fis);
//		BufferedReader bfr = new BufferedReader(isr);
		Class<?> c = Orders.class;
//		try {
//			Class.forName("com.myAnnotation.JdbcType");
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
			Field[] field = c.getDeclaredFields();
			Annotation[] a = c.getAnnotations();
			JdbcType jd =(JdbcType) field[0].getAnnotation(JdbcType.class);
			
			System.out.println(jd.type()+""+jd.required()+"--"+(jd.type()+"").equals("number"));
			ObjectMapper mapper = new ObjectMapper();
			try {
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(field));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
