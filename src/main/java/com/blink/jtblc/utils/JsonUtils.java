package com.blink.jtblc.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.CaseFormat;

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
			// 避免属性值为空转换异常的问题
			om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
	
	/**
	 * 将json内容按驼峰方式写到对像中
	 * 
	 * @param jsonStr
	 * @param cla
	 * @return
	 */
	public static <T> T toObj(String jsonStr, Class<T> cla) {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (jsonStr == null) {
			return null;
		}
		try {
			Map res = om.readValue(jsonStr, Map.class);
			String status = (String) res.get("status");
			if(status.equals("success")) {
				Map<String, Object> result = (Map) res.get("result");
				//创建JavaBean对象  
		        T obj = cla.newInstance();
		        //获取指定类的BeanInfo对象  
		        BeanInfo beanInfo = Introspector.getBeanInfo(cla, Object.class);
		        //获取所有的属性描述器  
		        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		        transfer(result,obj,pds);
		        return obj;  
			}else if(status.equals("error")) {
				String error = (String) res.get("error");
				String error_code = String.valueOf(res.get("error_code"));
				String error_message = (String) res.get("error_message");
				String msg = error_code + ":" + error + ". " + error_message;
				throw new RuntimeException(msg);
			}else {
				throw new RuntimeException("unknown error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static <T> void transfer(Map<String, Object> result, T obj, PropertyDescriptor[] pds) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof Map) {
				transfer((Map<String, Object>)value,obj,pds);
			}else if(value instanceof List){
				
			}else{
				String _key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
		        for(PropertyDescriptor pd:pds){
		        	if(_key.equals("ledgerIndex")) {
		        		System.out.println("-----");
		        	}
		        	System.out.println(_key + "--" + pd.getName());
		        	if(_key.equals(pd.getName())) {
		        		Method setter = pd.getWriteMethod();
		        		if(value instanceof Integer && _key.equals("ledgerIndex")) {
		        			setter.invoke(obj, String.valueOf(value));
		        		}else {
		        			 setter.invoke(obj, value);
		        		}
			            break;
		        	}
		        }
			}
		}
	}

	// 递归value如果是map一直遍历，并将值映射到javabean中
	private static <T> void test(PropertyDescriptor pd, Object res, T obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(res instanceof Map) {
			Object res_temp = ((Map)res).get(pd.getName());
			if(res_temp != null) {
				Method setter = pd.getWriteMethod();  
	            setter.invoke(obj, res);
			}else {
				test(pd,res_temp,obj);
			}
		}else {
         	Method setter = pd.getWriteMethod();  
            setter.invoke(obj, res);
		}
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
		String ledger_index = "8488670";
		String ledger_hash = "";
		boolean transactions = true;
		Map params = new HashMap();
		params.put("ledger_index", ledger_index);
		params.put("ledger_hash", ledger_hash);
		params.put("transactions", transactions);
		String data = JsonUtils.toJsonString(params);
		System.out.println(data);
	}
}