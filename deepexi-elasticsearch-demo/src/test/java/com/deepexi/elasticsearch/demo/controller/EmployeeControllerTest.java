package com.deepexi.elasticsearch.demo.controller;

import com.deepexi.elasticsearch.demo.DemoApplication;
import com.deepexi.elasticsearch.demo.domain.Employee;
import com.deepexi.elasticsearch.demo.util.RandomEmployeeUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerTest.class);
    @Resource
    private EmployeeController employeeController;

    private static Employee employee;

    @BeforeClass
    public static void setUp() {
        employee = RandomEmployeeUtil.random();
    }

    @Test
    public void test01add() {
        Employee created = (Employee) employeeController.create(employee);
        assertEquals(created.getId(), employee.getId());
    }

    @Test
    public void test02exists() {
        Object exists = employeeController.exists(String.valueOf(employee.getId()));
        LOGGER.info("根据id判断文档是否存在：" + exists);
    }

    @Test
    public void test03get() {
        Object obj = employeeController.get(String.valueOf(employee.getId()));
        LOGGER.info("根据id获取文档：" + obj);
    }

    @Test
    @Order(4)
    public void test04getByName() {
        Object obj = employeeController.getByName(employee.getName());
        LOGGER.info("自定义方法，根据name属性获取文档：" + obj);
    }

    @Test
    public void test05refresh() {
        Object refresh = employeeController.refresh();
        LOGGER.info("调用refresh方法：" + refresh);
    }

    @Test
    public void test06update() {
        employee.setContent("测试更新文档");
        Object obj = employeeController.update(employee);
        LOGGER.info("更新文档：" + obj);
    }

    @Test
    public void test07delete() {
        Object delete = employeeController.delete(String.valueOf(employee.getId()));
        LOGGER.info("删除文档：" + delete);
    }
}