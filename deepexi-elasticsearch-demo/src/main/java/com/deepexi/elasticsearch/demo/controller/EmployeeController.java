package com.deepexi.elasticsearch.demo.controller;

import com.deepexi.elasticsearch.demo.domain.Employee;
import com.deepexi.elasticsearch.demo.repository.EmployRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 员工类控制类
 *
 * @author RenWei
 * @date 2019/06/19
 */
@RestController
public class EmployeeController {
    @Resource
    private EmployRepository employRepository;

    /**
     * 索引一个文档
     *
     * @return Object
     */
    @PutMapping("/create")
    public Object create(@RequestBody Employee employee) {
        return employRepository.index(employee);
    }

    /**
     * 根据id判断文档是否存在
     *
     * @return Object
     */
    @GetMapping("/exists/{id}")
    public Object exists(@PathVariable String id) {
        return employRepository.existsById(Long.valueOf(id));
    }

    /**
     * 根据id查找文档
     *
     * @return Object
     */
    @GetMapping("/get/{id}")
    public Object get(@PathVariable String id) {
        return employRepository.findById(Long.valueOf(id));
    }

    /**
     * 自定义方法，根据name属性查找文档
     *
     * @return Object
     */
    @GetMapping("/get/name/{name}")
    public Object getByName(@PathVariable String name) {
        return employRepository.findByName(name);
    }

    /**
     * 更新文档
     *
     * @return Object
     */
    @PutMapping("/update")
    public Object update(@RequestBody Employee employee) {
        return employRepository.save(employee);
    }

    /**
     * 删除文档
     *
     * @return Object
     */
    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable String id) {
        employRepository.deleteById(Long.valueOf(id));
        return true;
    }

    /**
     * refresh
     *
     * @return Object
     */
    @PostMapping("/refresh")
    public Object refresh() {
        employRepository.refresh();
        return true;
    }
}
