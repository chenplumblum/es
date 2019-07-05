package com.deepexi.elasticsearch.demo.util;

import com.deepexi.elasticsearch.demo.domain.Employee;

import java.util.Date;
import java.util.Random;

/**
 * @author renwei
 * @date 2019/5/23  11:56
 */
public final class RandomEmployeeUtil {

    private RandomEmployeeUtil() {
    }

    private static final Random RANDOM = new Random();
    private static final int REMAINDER = 2;

    public static Employee random() {
        Employee employee = new Employee();
        int id = RANDOM.nextInt(10000);
        if (id % REMAINDER == 0) {
            employee.setType("开发");
        } else {
            employee.setType("测试");
        }
        employee.setId((long) id);
        employee.setName("employ" + id);
        employee.setUpdateTime(new Date());
        employee.setCreateTime(new Date());
        employee.setContent("我是" + employee.getType() + "，我的id是" + id);
        return employee;
    }
}
