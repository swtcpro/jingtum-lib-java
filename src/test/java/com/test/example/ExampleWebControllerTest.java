package com.test.example;

import com.test.web.Junit4WebBaseTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 写描述信息
 *
 * @author : liguangbin (liguangbin@ideadata.com.cn)
 * @createDate: 2016/6/13 17:16
 * @upateLog: Name Date Reason/Contents
 * @log: ---------------------------------------
 * @log: *** **** ****
 */
class ExampleWebControllerTest extends Junit4WebBaseTest {
    public static String ENABLED="1";
    @Test
    public void delete() {
        String url="/task";
        //1.请求地址
        //2.请求参数
        Map<String,String> params=new HashMap();
        params.put("findList", "true");//
        params.put("pid", "0");//
        params.put("owner", "0");//
        this.UrlTest(url,"get",params);
//        final ModelAndView mav = this.excuteAction(url, params);
//        //3.结果校验
//        String resultStatus=(String) mav.getModel().get("status");
       // Assert.assertEquals(ENABLED,resultStatus);
    };

}
