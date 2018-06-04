package com.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 配置文件载入类
 *
 * @author yusj
 * @ClassName: BaseSpringTestCase
 * @Description: 要想实现Spring自动注入，必须继承此类
 * @date 2014年6月9日 下午3:16:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml",
        "classpath:spring-mvc.xml"})
// 添加注释@Transactional 回滚对数据库操作
@WebAppConfiguration
public class Junit4ControllerBaseTest {
    public static ObjectMapper mapper = new ObjectMapper();
    public static Object res = null;
    @Autowired
    private ApplicationContext aC;
    // 模拟request,response
    public static MockHttpServletRequest request;
    public static MockHttpServletResponse response;

    // 执行测试方法之前初始化模拟request,response
    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
        mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    }

    /**
     * 输出对像的json内容
     * @param obj
     * @return
     */
    public String printf(Object obj) {
        System.out.println("==================结果==================");
        String json = "";
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        System.out.println("=======================================");
        return json;
    }
}