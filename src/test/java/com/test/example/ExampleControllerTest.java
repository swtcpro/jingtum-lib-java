package com.test.example;

import com.ideadata.core.task.entity.Task;
import com.ideadata.core.task.entity.TaskBatch;
import com.ideadata.web.controller.task.TaskController;
import com.idp.pub.entity.Pager;
import com.test.web.Junit4ControllerBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 写描述信息
 *
 * @author : liguangbin (liguangbin@ideadata.com.cn)
 * @createDate: 2016/6/13 17:16
 * @upateLog: Name Date Reason/Contents
 * @log: ---------------------------------------
 * @log: *** **** ****
 */
public class ExampleControllerTest extends Junit4ControllerBaseTest {
    // 注入Controller
    @Autowired
    private TaskController taskController;

    //    @Test
    public void saveTask() {
        Task task = new Task();
        task.setName("testName");
        task.setOwner("liguangbin");
        task.setCreateTime(new Date().toString());
        task.setPid("ppid");
        //task.setId("4d398bff2adf4a56b193d25971a26d5c");
        res = taskController.saveTask(task, "testOwner");
        this.printf(res);
    }

    //    @Test
    public void findByPid() {
        res = taskController.findByPid("ppid", "liguangbin");
        this.printf(res);
    }

    //    @Test
    public void saveTaskGroup() {
        Task task = new Task();
        task.setName("testName");
        task.setOwner("liguangbin");
        task.setCreateTime(new Date().toString());
        task.setPid("ppid");
        task.setId("032a97af73ce41a5ada362c9da46fcd0");
        task.setGroup(Boolean.TRUE);
        res = taskController.saveTaskGroup(task);
        this.printf(res);
    }

    @Test
    public void saveTaskBatch() {
        TaskBatch tb = new TaskBatch();
        tb.setTaskId("ppid");
        // tb.setBatchNo(UUIDUtils.getDefPk());
        res = taskController.saveTaskBatch(tb);
        this.printf(res);
    }

    //@Test
    public void findById() {
        res = taskController.findById("032a97af73ce41a5ada362c9da46fcd0");
        this.printf(res);
    }

    @Test
    public void findByPager() {
        Pager pager = new Pager();
        pager.setLimit(5);
        pager.setCurrent(0);
        pager.setReload(Boolean.TRUE);
        res = taskController.findByPager(pager, "{\"taskId\":\"ppid\"}");
        this.printf(res);
    }

    @Test
    public void findRecent() {
        res = taskController.findRecent("{\"taskId\":\"ppid\"}");
        this.printf(res);
    }

    @Test
    public void removeTask() {
        res = taskController.removeTask("ppid");
        this.printf(res);
    }

}
