package com.deepexi.elasticsearch.demo.controller;

import com.deepexi.elasticsearch.demo.domain.Employee;
import com.deepexi.elasticsearch.demo.repository.EmployRepository;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * java api
 * ElasticRepository使用
 *
 * @author RenWei
 * @date 2019/06/19
 */
@RestController
@RequestMapping("/repository")
public class EmployeeRepositoryController {
    @Resource
    private EmployRepository employRepository;

    /**
     * 复杂查询的常用流程：分页+排序+匹配
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

        // 2.创建排序，根据姓名倒叙排序
        FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);

        // 3.创建布尔查询，content字段包含searchContent
        BoolQueryBuilder contentBuilder = QueryBuilders.boolQuery();
        /* contentBuilder.should(QueryBuilders.term("type", searchContent)); */
        contentBuilder.should(QueryBuilders.matchQuery("content", searchContent));

        // 4.创建DSL查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(contentBuilder).
                withSort(sort).
                withPageable(pageable).
                build();

        // 5.查询
        Page<Employee> employees = employRepository.search(searchQuery);
        return employees.getContent();
    }

    /**
     * 复杂查询的常用流程：范围查询+多条件
     * 查询员工信息
     *
     * @param searchContent 搜索内容
     * @return List
     */
    @GetMapping("/search1")
    public List<Employee> search1(@RequestParam(value = "searchContent") String searchContent) {

        // 1.条件一，范围查询，id大于等于2的
        BoolQueryBuilder contentBuilder = QueryBuilders.boolQuery();
        contentBuilder.must(QueryBuilders.rangeQuery("id").gte(2));

        // 2.增加条件二，content包含searchContent
        contentBuilder.must(QueryBuilders.matchQuery("content", searchContent));
        // 3.创建DSL查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(contentBuilder).
                build();

        // 4.查询
        Page<Employee> employees = employRepository.search(searchQuery);
        return employees.getContent();
    }

    /**
     * 复杂查询的常用流程：模糊查询
     * 查询员工信息
     *
     * @param searchContent 搜索内容
     * @return List
     */
    @GetMapping("/search2")
    public List<Employee> search2(@RequestParam(value = "searchContent") String searchContent) {

        BoolQueryBuilder contentBuilder = QueryBuilders.boolQuery();
        contentBuilder.must(QueryBuilders.wildcardQuery("type", "*" + searchContent + "*"));

        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(contentBuilder).
                build();

        Page<Employee> employees = employRepository.search(searchQuery);
        return employees.getContent();
    }

    /**
     * 复杂查询的常用流程：聚合查询
     * 对文档的name字段进行聚合查询
     *
     * @return List
     */
    @GetMapping("/search3")
    public List<String> search3() {
        List<String> list = new ArrayList<>();

        // 1.matchAllQuery查询所有，相当于不设置查询条件
        MatchAllQueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();

        // 2.聚合，对字段"name"进行聚合，聚合出来的名字叫"sum"
        TermsAggregationBuilder tab = AggregationBuilders.
                terms("sum").
                field("name").
                order(Terms.Order.count(false));

        // 3.构建查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(matchAllQuery).
                withSearchType(SearchType.QUERY_THEN_FETCH).
                // 指定index和type
                        withIndices("inf").
                        withTypes("employee").
                        addAggregation(tab).
                        build();

        Page<Employee> employees = employRepository.search(searchQuery);
        List<Employee> content = employees.getContent();
        for (Employee employee : content) {
            list.add(employee.getName());
        }
        return list;
    }

    /**
     * 复杂查询的常用流程：多字段匹配查询+高亮字段
     * 注，目前测试使用ElasticsearchRepository时返回的json中没有高亮
     *
     * @return List
     */
    @GetMapping("/search4")
    public Page<Employee> search4(@RequestParam String searchContent) {

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchContent,"type", "name");

        // 1.高亮提示
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("type")
                .preTags("<a>")
                .postTags("</a>");

        // 2.构建查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(multiMatchQueryBuilder).
                withHighlightFields(highlightField.preTags("</a>")).
                build();
        Page<Employee> employees = employRepository.search(searchQuery);
        return employees;
    }

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
