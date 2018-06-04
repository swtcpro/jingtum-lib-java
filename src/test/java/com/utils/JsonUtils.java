package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 据说Jackson 较 json-lib 性能要快很多
 * 地址：https://github.com/FasterXML/jackson
 */
public class JsonUtils {
	// can reuse, share globally
	private static ObjectMapper om = new ObjectMapper();

	/**
	 * 把对象转换成Json格式，支持JavaBean、Map、List
	 *
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把Json字符串读取成对象格式
	 *
	 * @param <T>
	 * @param str
	 * @param cla
	 * @return
	 */
	public static <T> T toObject(String jsonStr, Class<T> cla) {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (jsonStr == null) {
			return null;
		}
		try {
			return om.readValue(jsonStr, cla);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// java数组转换成json串
		String simpleArray[] = { "英国", "法国", "德国" };
		String arrJson = JsonUtils.toJsonString(simpleArray);
		System.out.println(arrJson);
		// ["英国","法国","德国"]
		// 简单list转换成json串
		List simpleList = new ArrayList();
		simpleList.add("中国");
		simpleList.add("美国");
		simpleList.add("俄罗斯");
		String listJson = JsonUtils.toJsonString(simpleList);
		System.out.println(listJson);
		// ["中国","美国","俄罗斯"]
		// 简单Map转换成json串
		Map simpleMap = new HashMap();
		simpleMap.put("name", "张三");
		simpleMap.put("birthday", "1983");
		String mapJson = JsonUtils.toJsonString(simpleMap);
		System.out.println(mapJson);
		// {"birthday":"1983","name":"张三"}
		// 复杂List转换成json串
		List<Object> fuzaList = new ArrayList<>();
		fuzaList.add(simpleMap);
		fuzaList.add(simpleArray);
		fuzaList.add(simpleList);
		String fuzaListJson = JsonUtils.toJsonString(fuzaList);
		System.out.println(fuzaListJson);
		// [{"birthday":"1983","name":"张三"},["英国","法国","德国"],["中国","美国","俄罗斯"]]
		// 复杂Map转成json串
		Map fuzaMap = new HashMap();
		fuzaMap.put("count", 100);
		fuzaMap.put("page_size", 10);
		fuzaMap.put("data", fuzaList);
		String json2 = JsonUtils.toJsonString(fuzaMap);
		System.out.println(json2);
		// {"page_size":10,"count":100,"data":[{"birthday":"1983","name":"张三"},["英国","法国","德国"],["中国","美国","俄罗斯"]]}
		// json传还原成java数组
		simpleArray = JsonUtils.toObject(arrJson, String[].class);
		System.out.println(simpleArray);
		for (int i = 0; i < simpleArray.length; i++) {
			System.out.println(simpleArray[i]);// 英国 法国 德国
		}
		// json还原成list
		simpleList = JsonUtils.toObject(listJson, List.class);
		System.out.println(simpleList);
		// [中国, 美国, 俄罗斯]
		// json还原成简单map
		simpleMap = JsonUtils.toObject(mapJson, Map.class);
		System.out.println(simpleMap);
		// {birthday=1983, name=张三}
		// json还原成复杂List
		fuzaList = JsonUtils.toObject(fuzaListJson, List.class);
		simpleMap = (Map) fuzaList.get(0);
		System.out.println(simpleMap);
		// {birthday=1983, name=张三}
		simpleList = (List) fuzaList.get(1);// array -> list
		System.out.println(simpleList);
		// [英国, 法国, 德国]
		simpleList = (List) fuzaList.get(2);
		System.out.println(simpleList);
		// [中国, 美国, 俄罗斯]
		// json还原成复杂map
		fuzaMap = JsonUtils.toObject(json2, Map.class);
		System.out.println(fuzaMap);
		// {page_size=10, count=100, data=[{birthday=1983, name=张三}, [英国, 法国, 德国], [中国, 美国, 俄罗斯]]}
	}
}