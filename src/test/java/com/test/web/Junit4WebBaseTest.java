package com.test.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * ###################################################
 *
 * @创建日期：2014-4-4 11:42:09
 * @开发人员：李广彬
 * @功能描述：JUnit测试action时使用的基类
 * @修改日志：
 * @###################################################
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/applicationContext.xml",
        "file:E:\\云存储\\Workspaces\\Workspaces_dem\\iris-parent\\iris-admin\\src\\test\\java\\com\\conf/spring-mvc.xml" })
@WebAppConfiguration
// 当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
// @Transactional
public class Junit4WebBaseTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private static HandlerMapping handlerMapping;
    private static HandlerAdapter handlerAdapter;
    
    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
    
    // @Test
    public void UrlTest(String url, String method, Map<String, String> params) {
        MockHttpServletRequestBuilder req = post(url);
        // 1.请求地址
        switch (method) {
        case "get": {
            req = get(url);
            break;
        }
        case "post": {
            req = post(url);
            break;
        }
        case "put": {
            req = put(url);
            break;
        }
        case "delete": {
            req = delete(url);
            break;
        }
        }
        // 2.请求参数
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                req.param(key, params.get(key));
            }
        }
        // 3.执行请求
        try {
            mockMvc.perform(req).andExpect(status().isOk()).andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
