package com.deepexi.elasticsearch.demo.repository;

import com.deepexi.elasticsearch.demo.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 接口
 *
 * @author RenWei
 * @date 2019/06/19
 */
public interface EmployRepository extends ElasticsearchRepository<Employee, Long> {
    /**
     * 自定义方法，根据name属性查找
     *
     * @param name 属性
     * @return Employee
     */
    List<Employee> findByName(String name);
}
