package com.test.service;

import java.util.UUID;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.utils.JsonUtils;

/**
 * @ ###################################################
 * @创建日期：2014-4-4 11:42:09
 *
 * @开发人员：李广彬
 * @功能描述：数据定义功能
 *              @修改日志：
 *              @###################################################
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
@ActiveProfiles("com/test")
public class ServiceBaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	/**
	 * 32位的uuid
	 */
	public static String UuidStr() {
		String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 19);
		return uuid;
	}

	public static void JsonStr(String prifix, Object src) {
		System.out.println("-------------------------------------");
		System.out.println(prifix + JsonUtils.toJsonString(src));
		System.out.println("-------------------------------------");
	}
}
