package com.blink.jtblc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blink.jtblc.utils.JsonUtils;
import com.blink.jtblc.wallet.TestClass;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test2 {
	// can reuse, share globally
	private static ObjectMapper om = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		System.out.println(Map.class);
		TestClass t = new TestClass();
		TestClass t1 = new TestClass();
		t1.setName("t1");
		TestClass t2 = new TestClass();
		t2.setName("t2");
		TestClass t3 = new TestClass();
		t3.setName("t3");
		t.setList(new ArrayList<>());
		t.getList().add(t1);
		t.getList().add(t2);
		t.getList().add(t3);
		Map params = new HashMap<>();
		params.put("result", t);
		params.put("status", "success");
		String jsonStr = om.writeValueAsString(params);
		System.out.println(jsonStr);
		TestClass res = JsonUtils.toEntity(jsonStr, TestClass.class);
		jsonStr = om.writeValueAsString(res);
		System.out.println(jsonStr);
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
		String ledger_index = "8488670";
		String ledger_hash = "";
		boolean transactions = true;
	}
}