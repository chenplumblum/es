//package com.deepexi.elasticsearch.demo.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//
///**
// * HighLevelRestApi配置
// *
// * @author RenWei
// * @date 2019/07/02
// */
//@Configuration
//public class ElasticsearchHighRestConfig {
//    /**
//     * 集群地址，多个用,隔开
//     **/
//    private static final String HOSTS = "39.100.79.132:9200,39.100.79.132:9201,39.100.79.132:9202";
//    /**
//     * 使用的协议
//     **/
//    private static final String SCHEMA = "http";
//    private static ArrayList<HttpHost> hostList;
//    /**
//     * 连接超时时间
//     **/
//    private static int connectTimeOut = 1000;
//    /**
//     * 连接超时时间
//     **/
//    private static int socketTimeOut = 30000;
//    /**
//     * 获取连接的超时时间
//     **/
//    private static int connectionRequestTimeOut = 500;
//    /**
//     * 最大连接数
//     **/
//    private static int maxConnectNum = 100;
//    /**
//     * 最大路由连接数
//     **/
//    private static int maxConnectPerRoute = 100;
//
//    static {
//        hostList = new ArrayList<>();
//        String[] addresses = HOSTS.split(",");
//        for (String address : addresses) {
//            String[] host = address.split(":");
//            hostList.add(new HttpHost(host[0], Integer.valueOf(host[1]), SCHEMA));
//        }
//    }
//
//    @Bean
//    public RestHighLevelClient client() {
//        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
//        // 异步httpclient连接延时配置
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(connectTimeOut);
//            requestConfigBuilder.setSocketTimeout(socketTimeOut);
//            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
//            return requestConfigBuilder;
//        });
//        // 异步httpclient连接数配置
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            httpClientBuilder.setMaxConnTotal(maxConnectNum);
//            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
//            return httpClientBuilder;
//        });
//        return new RestHighLevelClient(builder);
//    }
//}
