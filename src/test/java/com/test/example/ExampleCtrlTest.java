package com.test.example;

import com.ideadata.core.plan.entity.Plan;
import com.ideadata.web.controller.plan.PlanController;
import com.idp.pub.entity.Pager;
import com.test.web.Junit4ControllerBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 写描述信息
 *
 * @author : liguangbin (liguangbin@ideadata.com.cn)
 * @createDate: 2016/6/13 17:16
 * @upateLog: Name Date Reason/Contents
 * @log: ---------------------------------------
 * @log: *** **** ****
 */
public class ExampleCtrlTest extends Junit4ControllerBaseTest {
    // 注入Controller
    @Autowired
    private PlanController planCtrl;
    String id="575fb3f5de9e1f0a0092ddc8";

    @Test
    public void saveTask() {
        Plan obj = new Plan();
        List<String> items=new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");
        obj.setOwner("liguangbin");
        obj.setCreateTime(new Date());
        obj.setName("name");
        obj.setId(id);
        //obj.setTaskIds(items);
        res = planCtrl.save(obj);
        obj.setId(null);
        res = planCtrl.save(obj);
        this.printf(res);
    }

    @Test
    public void findById() {
        res = planCtrl.findById(id);
        this.printf(res);
    }

        @Test
    public void findByOwner() {
        res = planCtrl.findByOwner("liguangbin");
        this.printf(res);
    }

     @Test
    public void delete() {
        res = planCtrl.delete("liguangbin",id);
        this.printf(res);
    }


    @Test
    public void findByPager() {
        Pager pager = new Pager();
        pager.setLimit(5);
        pager.setCurrent(0);
        pager.setReload(Boolean.TRUE);
         res = planCtrl.findByPager(pager, "{\"name\":\"name\"}");
        this.printf(res);
    }


}
