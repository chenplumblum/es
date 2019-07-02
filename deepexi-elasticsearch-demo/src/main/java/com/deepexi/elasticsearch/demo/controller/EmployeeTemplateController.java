package com.deepexi.elasticsearch.demo.controller;

import com.deepexi.elasticsearch.demo.domain.Employee;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * java api
 * ElasticsearchTemplate的使用和ElasticSearchRepository大同小异
 *
 * @author RenWei
 * @date 2019/07/02
 */
@RestController
@RequestMapping("/template")
public class EmployeeTemplateController {
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 复杂查询的常用流程：分页+匹配
     * 查询员工信息
     *
     * @param page          第几页
     * @param size          每页多少条
     * @param searchContent 搜索内容
     * @return List
     */
    @GetMapping("/search")
    public List<Employee> search(@RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size,
                                 @RequestParam(value = "searchContent") String searchContent) {

        // 1.创建分页参数，springboot2.0中通过构造方法创建的方式已经废弃
        Pageable pageable = PageRequest.of(page, size);

        // 2.匹配查询
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", searchContent);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(queryBuilder).
                withPageable(pageable).
                build();

        return elasticsearchTemplate.queryForList(searchQuery, Employee.class);
    }
}
