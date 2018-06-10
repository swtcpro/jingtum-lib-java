package com.blink.jtblc.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.CaseFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * 据说Jackson 较 json-lib 性能要快很多
 * 地址：https://github.com/FasterXML/jackson
 */
public class JsonUtils {
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
	public static <T> T toEntity(String jsonStr, Class<T> cla) {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (jsonStr == null) {
			return null;
		}
		try {
			System.out.println("转换前： " + jsonStr);
			Map res = om.readValue(jsonStr, Map.class);
			String status = (String) res.get("status");
			if (status.equals("success")) {
				Map<String, Object> result = (Map) res.get("result");
				// 创建JavaBean对象
				T obj = cla.newInstance();
				// 获取指定类的BeanInfo对象
				BeanInfo beanInfo = Introspector.getBeanInfo(cla, Object.class);
				// 获取所有的属性描述器
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				Map<String, PropertyDescriptor> props = propDesc2Map(pds);
				transfer(result, obj, props);
				return obj;
			} else if (status.equals("error")) {
				String error = (String) res.get("error");
				String error_code = String.valueOf(res.get("error_code"));
				String error_message = (String) res.get("error_message");
				String msg = error_code + ":" + error + ". " + error_message;
				throw new RuntimeException(msg);
			} else {
				throw new RuntimeException("unknown error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 工具方法：
	 * 
	 * @param props
	 * @return
	 */
	private static Map<String, PropertyDescriptor> propDesc2Map(PropertyDescriptor[] props) {
		Map res = new HashMap<String, PropertyDescriptor>();
		if (props != null && props.length > 0) {
			for (PropertyDescriptor prop : props) {
				res.put(prop.getName().toUpperCase(), prop);
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @param result
	 * @param obj
	 * @param pds
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 * @throws ClassNotFoundException
	 */
	private static <T> void transfer(Map<String, Object> result, T obj, Map<String, PropertyDescriptor> props)
	        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException,
	        InstantiationException, IntrospectionException, ClassNotFoundException {
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			String attrKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
			PropertyDescriptor prop = props.get(attrKey.toUpperCase());
			if (val != null && prop != null) {
				if (val instanceof Map) {
					transferMap(prop, obj, attrKey, val);
				} else if (val instanceof List) {
					transferList(prop, obj, attrKey, val);
				} else if (val instanceof Integer || val instanceof Long || val instanceof Short || val instanceof Float
				        || val instanceof String || val instanceof Double || val instanceof Boolean) {
					transferValue(prop, obj, val);
				}
			}
		}
	}
	
	/**
	 * 根据范型将Map的内容转为到子对像中
	 * 
	 * @param prop
	 * @param obj
	 * @param attrKey
	 * @param val
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	private static <T> void transferMap(PropertyDescriptor prop, T obj, String attrKey, Object val)
	        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException,
	        ClassNotFoundException, InstantiationException, IntrospectionException {
		// Map->entity 一般对应一个实体
		Field mapField = obj.getClass().getDeclaredField(attrKey);
		Object chdObj = mapField.getType().newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(mapField.getType(), Object.class);
		PropertyDescriptor[] chdPds = beanInfo.getPropertyDescriptors();
		Map<String, PropertyDescriptor> chdProps = propDesc2Map(chdPds);
		transfer((Map<String, Object>) val, chdObj, chdProps);
		prop.getWriteMethod().invoke(obj, chdObj);
	}
	
	/**
	 * 根据范型将实体转换到List中
	 * 
	 * @param prop
	 * @param obj
	 * @param key
	 * @param val
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	private static <T> void transferList(PropertyDescriptor prop, T obj, String attrKey, Object val)
	        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException,
	        ClassNotFoundException, InstantiationException, IntrospectionException {
		// list内放置一组
		Field listField = obj.getClass().getDeclaredField(attrKey);
		ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
		Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();// List 只有一个参数
		if (listActualTypeArguments != null && listActualTypeArguments.length > 0) {
			List chdList = new ArrayList<>();
			prop.getWriteMethod().invoke(obj, chdList);
			Type actualType = listActualTypeArguments[0];
			List<Map> items = (List) val;
			for (Map map : items) {
				Class clazz = Class.forName(actualType.getTypeName());
				Object item = clazz.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
				PropertyDescriptor[] chdPds = beanInfo.getPropertyDescriptors();
				Map<String, PropertyDescriptor> chdProps = propDesc2Map(chdPds);
				transfer((Map<String, Object>) map, item, chdProps);
				chdList.add(item);
			}
		}
	}
	
	/**
	 * 将属性加入到实体中
	 * 
	 * @param prop
	 * @param obj
	 * @param key
	 * @param val
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static <T> void transferValue(PropertyDescriptor prop, T obj, Object val)
	        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (prop != null) {
			Method setter = prop.getWriteMethod();
			if (prop.getPropertyType().equals(Integer.class)) {
				setter.invoke(obj, Integer.parseInt(val.toString()));
			} else if (prop.getPropertyType().equals(Long.class)) {
				setter.invoke(obj, Long.parseLong(val.toString()));
			} else if (prop.getPropertyType().equals(Short.class)) {
				setter.invoke(obj, Short.parseShort(val.toString()));
			} else if (prop.getPropertyType().equals(String.class)) {
				setter.invoke(obj, val.toString());
			} else if (prop.getPropertyType().equals(Float.class)) {
				setter.invoke(obj, Float.parseFloat(val.toString()));
			} else if (prop.getPropertyType().equals(Double.class)) {
				setter.invoke(obj, Double.parseDouble(val.toString()));
			}else if (prop.getPropertyType().equals(Boolean.class)){
				setter.invoke(obj, Boolean.parseBoolean(val.toString()));
			}
		}
	}

}