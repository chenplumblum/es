//package com.deepexi.elasticsearch.demo.controller;
//
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//
///**
// * rest high level api
// * 使用rest-high-level-client
// *
// * @author RenWei
// * @date 2019/07/02
// */
//@RestController
//@RequestMapping("/rest-high")
//public class RestHighLevelController {
//    @Resource
//    private RestHighLevelClient client;
//
//    /**
//     * 复杂查询的常用流程：分页+匹配
//     * 查询员工信息
//     *
//     * @param page          第几页
//     * @param size          每页多少条
//     * @param searchContent 搜索内容
//     * @return List
//     */
//    @GetMapping("/search")
//    public Object search(@RequestParam(value = "page") Integer page,
//                         @RequestParam(value = "size") Integer size,
//                         @RequestParam(value = "searchContent") String searchContent) throws IOException, JSONException {
//
//        // 1.匹配查询
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
//        boolBuilder.must(QueryBuilders.matchQuery("content", searchContent));
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(boolBuilder);
//
//        // 2.分页参数
//        sourceBuilder.from(page);
//        sourceBuilder.size(size);
//
//        // 3.指定索引和类型
//        SearchRequest searchRequest = new SearchRequest("inf");
//        searchRequest.types("employee");
//        searchRequest.source(sourceBuilder);
//
//        // 4.搜索
//        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//        return new JSONObject(String.valueOf(response));
//    }
//}
