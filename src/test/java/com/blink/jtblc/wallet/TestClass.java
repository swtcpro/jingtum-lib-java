package com.blink.jtblc.wallet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestClass {
	private String name;
	private List<TestClass> list;
	private Map<String, Integer> map;
	
	@Test
	public void test() throws NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Field listField = TestClass.class.getDeclaredField("list");
		Field mapField = TestClass.class.getDeclaredField("map");
		// 对比 Field 类的 getType() 和 getGenericType()
		System.out.println(listField.getType()); // interface java.util.List
		System.out.println(listField.getGenericType()); // java.util.List<java.lang.Character>
		System.out.println(mapField.getType()); // interface java.util.Map
		System.out.println(mapField.getGenericType()); // java.util.Map<java.lang.String, java.lang.Integer>
		// 获取 list 字段的泛型参数
		ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
		Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
		for (int i = 0; i < listActualTypeArguments.length; i++) {
			Class clazz = Class.forName(listActualTypeArguments[i].getTypeName());
			Object obj = clazz.newInstance();
			obj = "adfadsfadf";
			System.out.println(obj);
			System.out.println(listActualTypeArguments[i].getTypeName());
		}
		// class java.lang.Character
		// 获取 map 字段的泛型参数
		ParameterizedType mapGenericType = (ParameterizedType) mapField.getGenericType();
		Type[] mapActualTypeArguments = mapGenericType.getActualTypeArguments();
		for (int i = 0; i < mapActualTypeArguments.length; i++) {
			System.out.println(mapActualTypeArguments[i]);
		}
		// class java.lang.String
		// class java.lang.Integer
	}
	
	@Test
	public void main() {
		// TODO Auto-generated method stub
		String str = " 123 ";
		byte[] bits = str.getBytes();
		int[] inta = new int[] { 190, 145, 99, 160, 58, 70, 214, 178, 128, 91, 192, 20, 127, 223, 238, 94, 178, 221, 254, 146, 233, 57, 218,
		        75, 148, 178, 170, 253, 181, 66, 118, 177 };
		;
		byte[] nbytes = new byte[inta.length];
		int ind = 0;
		for (int i : inta) {
			nbytes[ind++] = (byte) i;
		}
		BigInteger bigInteger = new BigInteger(nbytes);
		System.out.println("bigInteger=" + bigInteger.toString(16));
		bits = Arrays.copyOfRange(bits, 0, bits.length - 1);
		for (byte bit : bits) {
			System.out.println(bit);
		}
		// Wallet w = new Wallet();
		// w.generate();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<TestClass> getList() {
		return list;
	}
	
	public void setList(List<TestClass> list) {
		this.list = list;
	}
	
	public Map<String, Integer> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
}