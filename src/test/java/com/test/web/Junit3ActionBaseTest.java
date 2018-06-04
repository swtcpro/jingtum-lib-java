package com.test.web;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

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
@ContextConfiguration({"classpath:spring/applicationContext.xml",
        "file:E:\\云存储\\Workspaces\\Workspaces_dem\\iris-parent\\iris-admin\\src\\test\\java\\com\\conf/spring-mvc.xml"})
@WebAppConfiguration
public class Junit3ActionBaseTest {
    private static HandlerMapping handlerMapping;
    private static HandlerAdapter handlerAdapter;

    /**
     * 读取配置文件
     */
    @BeforeClass
    public static void setUp() {
        if (handlerMapping == null) {
            String[] configs = getConfigs();
            XmlWebApplicationContext context = new XmlWebApplicationContext();
            MockServletContext msc = new MockServletContext();
            //context.getEnvironment().addActiveProfile("com/test");// 数据源
            context.setConfigLocations(configs);
            context.setServletContext(msc);
            context.refresh();
            msc.setAttribute(
                    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                    context); // TODO
            handlerMapping = (HandlerMapping) context
                    .getBean(DefaultAnnotationHandlerMapping.class);
            handlerAdapter = (HandlerAdapter) context
                    .getBean(context
                            .getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
        }
    }

    public static String[] getConfigs() {
        String[] configs = {"classpath:spring/applicationContext.xml",
                "file:E:\\云存储\\Workspaces\\Workspaces_dem\\iris-parent\\iris-admin\\src\\test\\java\\com\\conf/spring-mvc.xml"};
        for (int i = 0; i < configs.length; i++) {
            configs[i] = configs[i].replace("\\", "/");
        }
        return configs;
    }

    ;

    /**
     * 执行request请求的action
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView excuteAction(HttpServletRequest request,
                                     HttpServletResponse response) {
        // 这里需要声明request的实际类型，否则会报错
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
        ModelAndView model = null;
        try {
            HandlerExecutionChain chain = handlerMapping.getHandler(request);
            model = handlerAdapter
                    .handle(request, response, chain.getHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 执行request请求的action
     *
     * @return
     * @throws Exception
     */
    public ModelAndView excuteAction(String url, Map<String, String> params) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("post");
        // 1.请求地址
        request.setServletPath(url);
        // 2.请求参数
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                request.addParameter(key, params.get(key));
            }
        }

        return this.excuteAction(request, response);
    }
}
